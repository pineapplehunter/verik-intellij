/*
 * Copyright (c) 2022 Francis Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.verik.intellij.annotate

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.highlighter.KotlinHighlightingColors
import org.jetbrains.kotlin.lexer.KtSingleValueToken
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression
import org.jetbrains.kotlin.psi.KtTypeElement
import org.jetbrains.kotlin.psi.psiUtil.endOffset
import org.jetbrains.kotlin.psi.psiUtil.startOffset

class VerikAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is KtOperationReferenceExpression && !element.isConventionOperator()) {
            val operationSignTokenType = element.operationSignTokenType
            if (operationSignTokenType !is KtSingleValueToken) {
                holder
                    .newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .textAttributes(KotlinHighlightingColors.EXTENSION_FUNCTION_CALL)
                    .create()
            }
        }
        if (element is KtTypeElement) {
            val text = element.text
            if (text.startsWith("`") && text.endsWith("`")) {
                val int = text.substring(1, text.length - 1).toIntOrNull()
                if (int != null) {
                    val startOffset = element.startOffset
                    val endOffset = element.endOffset
                    val startTextRange = TextRange(startOffset, startOffset)
                    val endTextRange = TextRange(startOffset + 1, endOffset)
                    holder
                        .newSilentAnnotation(HighlightSeverity.INFORMATION)
                        .textAttributes(KotlinHighlightingColors.NUMBER)
                        .range(startTextRange)
                        .create()
                    holder
                        .newSilentAnnotation(HighlightSeverity.INFORMATION)
                        .textAttributes(KotlinHighlightingColors.NUMBER)
                        .range(endTextRange)
                        .create()
                }
            }
        }
    }
}
