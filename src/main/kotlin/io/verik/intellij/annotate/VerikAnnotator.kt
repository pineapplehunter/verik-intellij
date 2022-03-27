/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.annotate

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.highlighter.KotlinHighlightingColors
import org.jetbrains.kotlin.lexer.KtSingleValueToken
import org.jetbrains.kotlin.psi.KtBinaryExpression
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression
import org.jetbrains.kotlin.psi.KtReferenceExpression
import org.jetbrains.kotlin.psi.KtSimpleNameExpression
import org.jetbrains.kotlin.psi.KtStringTemplateExpression
import org.jetbrains.kotlin.psi.KtTypeElement
import org.jetbrains.kotlin.psi.psiUtil.endOffset
import org.jetbrains.kotlin.psi.psiUtil.referenceExpression
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class VerikAnnotator : Annotator {

    private val ANNOTATED_INFIX_FUNCTIONS = listOf(
        "add",
        "mul",
        "and",
        "or",
        "xor",
        "shl",
        "shr",
        "sshr",
        "ushr"
    )

    private val ANNOTATED_KEYWORDS = listOf("unknown", "floating")

    private val NUMBER_INVALID = TextAttributes(
        KotlinHighlightingColors.NUMBER.defaultAttributes.foregroundColor,
        KotlinHighlightingColors.NUMBER.defaultAttributes.backgroundColor,
        KotlinHighlightingColors.INVALID_STRING_ESCAPE.defaultAttributes.effectColor,
        KotlinHighlightingColors.INVALID_STRING_ESCAPE.defaultAttributes.effectType,
        KotlinHighlightingColors.NUMBER.defaultAttributes.fontType
    )

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        when (element) {
            is KtOperationReferenceExpression -> annotateInfixFunctions(element, holder)
            is KtTypeElement -> annotateCardinalTypeElements(element, holder)
            is KtBinaryExpression -> annotateSeqAssignments(element, holder)
            is KtSimpleNameExpression -> annotateKeywords(element, holder)
            is KtStringTemplateExpression -> annotateBitLiterals(element, holder)
        }
    }

    private fun annotateInfixFunctions(
        operationReferenceExpression: KtOperationReferenceExpression,
        holder: AnnotationHolder
    ) {
        if (!operationReferenceExpression.isConventionOperator()) {
            val operationSignTokenType = operationReferenceExpression.operationSignTokenType
            if (operationSignTokenType !is KtSingleValueToken) {
                val text = operationReferenceExpression.text
                if (text in ANNOTATED_INFIX_FUNCTIONS) {
                    holder
                        .newSilentAnnotation(HighlightSeverity.INFORMATION)
                        .textAttributes(KotlinHighlightingColors.EXTENSION_FUNCTION_CALL)
                        .create()
                }
            }
        }
    }

    private fun annotateCardinalTypeElements(typeElement: KtTypeElement, holder: AnnotationHolder) {
        val text = typeElement.text
        if (text.startsWith("`") && text.endsWith("`")) {
            val trimmedText = text.substring(1, text.length - 1)
            if (trimmedText.toIntOrNull() != null || trimmedText == "*") {
                splitAnnotate(typeElement, holder, KotlinHighlightingColors.NUMBER.defaultAttributes)
            }
        }
    }

    private fun annotateSeqAssignments(binaryExpression: KtBinaryExpression, holder: AnnotationHolder) {
        if (SeqAssignmentChecker.isSeqAssignment(binaryExpression)) {
            holder
                .newSilentAnnotation(HighlightSeverity.INFORMATION)
                .textAttributes(KotlinHighlightingColors.EXTENSION_FUNCTION_CALL)
                .range(binaryExpression.operationReference)
                .create()
        }
    }

    private fun annotateKeywords(simpleNameExpression: KtSimpleNameExpression, holder: AnnotationHolder) {
        if (simpleNameExpression.getReferencedName() in ANNOTATED_KEYWORDS) {
            splitAnnotate(simpleNameExpression, holder, KotlinHighlightingColors.KEYWORD.defaultAttributes)
        }
    }

    private fun annotateBitLiterals(stringTemplateExpression: KtStringTemplateExpression, holder: AnnotationHolder) {
        val callExpression = stringTemplateExpression.parent.parent.parent
        if (callExpression !is KtCallExpression)
            return
        val referenceExpression = callExpression.referenceExpression()
        if (referenceExpression is KtReferenceExpression && referenceExpression.text in listOf("u", "s")) {
            val value = stringTemplateExpression.text.substring(1, stringTemplateExpression.text.length - 1)
            if (BitConstantChecker.check(value)) {
                splitAnnotate(stringTemplateExpression, holder, KotlinHighlightingColors.NUMBER.defaultAttributes)
            } else {
                splitAnnotate(stringTemplateExpression, holder, NUMBER_INVALID)
            }
        }
    }

    private fun splitAnnotate(element: PsiElement, holder: AnnotationHolder, textAttributes: TextAttributes) {
        val startOffset = element.startOffset
        val endOffset = element.endOffset
        val startTextRange = TextRange(startOffset, startOffset)
        val endTextRange = TextRange(startOffset + 1, endOffset)
        holder
            .newSilentAnnotation(HighlightSeverity.INFORMATION)
            .enforcedTextAttributes(textAttributes)
            .range(startTextRange)
            .create()
        holder
            .newSilentAnnotation(HighlightSeverity.INFORMATION)
            .enforcedTextAttributes(textAttributes)
            .range(endTextRange)
            .create()
    }
}
