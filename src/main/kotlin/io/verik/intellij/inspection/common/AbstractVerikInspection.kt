/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.inspection.common

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtVisitorVoid

abstract class AbstractVerikInspection : AbstractKotlinInspection() {

    override fun getGroupDisplayName(): String {
        return "Verik"
    }

    override fun isEnabledByDefault(): Boolean {
        return true
    }

    final override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return if (isEnabled(holder)) {
            buildEnabledVisitor(holder)
        } else NULL_VISITOR
    }

    abstract fun buildEnabledVisitor(holder: ProblemsHolder): PsiElementVisitor

    private fun isEnabled(holder: ProblemsHolder): Boolean {
        val file = holder.file
        if (file !is KtFile)
            return false
        return file.annotationEntries.any {
            it.shortName.toString() == "Verik"
        }
    }

    companion object {

        private val NULL_VISITOR = object : KtVisitorVoid() {}
    }
}
