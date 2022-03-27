/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.inspection.inspection

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import io.verik.intellij.inspection.common.AbstractVerikInspection
import org.jetbrains.kotlin.psi.KtClassLiteralExpression
import org.jetbrains.kotlin.psi.KtDoubleColonExpression
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression
import org.jetbrains.kotlin.psi.KtPropertyDelegate
import org.jetbrains.kotlin.psi.KtThrowExpression
import org.jetbrains.kotlin.psi.KtTryExpression
import org.jetbrains.kotlin.psi.KtVisitorVoid

class UnsupportedElementInspection : AbstractVerikInspection() {

    override fun getID(): String {
        return "VerikUnsupportedElement"
    }

    override fun getDisplayName(): String {
        return "Unsupported element"
    }

    override fun getStaticDescription(): String {
        return "Reports elements that are not supported by Verik."
    }

    override fun getDefaultLevel(): HighlightDisplayLevel {
        return HighlightDisplayLevel.ERROR
    }

    override fun buildEnabledVisitor(holder: ProblemsHolder): PsiElementVisitor {
        return UnsupportedElementVisitor(holder)
    }

    private class UnsupportedElementVisitor(
        private val holder: ProblemsHolder
    ) : KtVisitorVoid() {

        private fun registerProblem(element: KtElement, name: String) {
            holder.registerProblem(element, "$name is not supported")
        }

        override fun visitPropertyDelegate(delegate: KtPropertyDelegate) {
            registerProblem(delegate, "Property delegate")
        }

        override fun visitThrowExpression(expression: KtThrowExpression) {
            registerProblem(expression, "Throw expression")
        }

        override fun visitTryExpression(expression: KtTryExpression) {
            registerProblem(expression, "Try expression")
        }

        override fun visitDoubleColonExpression(expression: KtDoubleColonExpression) {
            registerProblem(expression, "Double colon expression")
        }

        override fun visitClassLiteralExpression(expression: KtClassLiteralExpression) {
            registerProblem(expression, "Class literal expression")
        }

        override fun visitObjectLiteralExpression(expression: KtObjectLiteralExpression) {
            registerProblem(expression, "Object literal expression")
        }
    }
}
