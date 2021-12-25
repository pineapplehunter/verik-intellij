/*
 * Copyright (c) 2021 Francis Wang
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

package io.verik.intellij.inspection.inspection

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import io.verik.intellij.inspection.common.AbstractVerikInspection
import org.jetbrains.kotlin.psi.KtClassLiteralExpression
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration
import org.jetbrains.kotlin.psi.KtDoubleColonExpression
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtImportAlias
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression
import org.jetbrains.kotlin.psi.KtPropertyDelegate
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression
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
        return "Reports elements that are not supported by Verik"
    }

    override fun getDefaultLevel(): HighlightDisplayLevel {
        return HighlightDisplayLevel.ERROR
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return UnsupportedElementVisitor(holder)
    }

    private class UnsupportedElementVisitor(
        private val holder: ProblemsHolder
    ) : KtVisitorVoid() {

        private fun registerProblem(element: KtElement, name: String) {
            holder.registerProblem(element, "$name is not supported")
        }

        override fun visitDestructuringDeclaration(destructuringDeclaration: KtDestructuringDeclaration) {
            registerProblem(destructuringDeclaration, "Destructuring declaration")
        }

        override fun visitImportAlias(importAlias: KtImportAlias) {
            registerProblem(importAlias, "Import alias")
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

        override fun visitSafeQualifiedExpression(expression: KtSafeQualifiedExpression) {
            registerProblem(expression, "Safe qualified expression")
        }

        override fun visitObjectLiteralExpression(expression: KtObjectLiteralExpression) {
            registerProblem(expression, "Object literal expression")
        }
    }
}
