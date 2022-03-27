/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.inspection.inspection

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import io.verik.intellij.inspection.common.AbstractVerikInspection
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.modifierListVisitor

class UnsupportedModifierInspection : AbstractVerikInspection() {

    private val unsupportedModifiers = listOf(
        KtTokens.ANNOTATION_KEYWORD,
        KtTokens.CROSSINLINE_KEYWORD,
        KtTokens.DATA_KEYWORD,
        KtTokens.EXTERNAL_KEYWORD,
        KtTokens.IN_KEYWORD,
        KtTokens.NOINLINE_KEYWORD,
        KtTokens.OPERATOR_KEYWORD,
        KtTokens.OUT_KEYWORD,
        KtTokens.REIFIED_KEYWORD,
        KtTokens.SEALED_KEYWORD,
        KtTokens.SUSPEND_KEYWORD,
        KtTokens.TAILREC_KEYWORD,
        KtTokens.VARARG_KEYWORD
    )

    override fun getID(): String {
        return "VerikUnsupportedModifier"
    }

    override fun getDisplayName(): String {
        return "Unsupported modifier"
    }

    override fun getStaticDescription(): String {
        return "Reports modifiers that are not supported by Verik."
    }

    override fun getDefaultLevel(): HighlightDisplayLevel {
        return HighlightDisplayLevel.ERROR
    }

    override fun buildEnabledVisitor(holder: ProblemsHolder): PsiElementVisitor {
        return modifierListVisitor { modifierList ->
            unsupportedModifiers.forEach {
                if (modifierList.hasModifier(it)) {
                    val modifier = modifierList.getModifier(it)!!
                    holder.registerProblem(modifier, "Modifier ${modifier.text} not supported")
                }
            }
        }
    }
}
