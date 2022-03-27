/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.arguments

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.psi.KtValueArgumentList
import org.jetbrains.kotlin.psi.KtVisitorVoid

class VerikArgumentsHelperInspection : AbstractKotlinInspection() {

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return VerikArgumentsHelperVisitor(holder)
    }

    override fun getStaticDescription(): String {
        return "Add missing call arguments"
    }


    private class VerikArgumentsHelperVisitor(
        private val holder: ProblemsHolder
    ): KtVisitorVoid() {

        override fun visitValueArgumentList(list: KtValueArgumentList) {
            val descriptor = VerikArgumentsHelperUtil.getDescriptor(list) ?: return
            if (descriptor.valueParameters.size > list.arguments.size) {
                val quickFix = VerikArgumentsHelperQuickFix()
                holder.registerProblem(list, quickFix.name, quickFix)
            }
        }
    }
}
