/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.inspection.inspection

import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import io.verik.intellij.inspection.common.AbstractVerikInspection
import org.jetbrains.kotlin.psi.propertyVisitor

class ComAssignmentNotVarInspection : AbstractVerikInspection() {

    override fun getID(): String {
        return "VerikComAssignmentNotVar"
    }

    override fun getDisplayName(): String {
        return "Combinational assignment not var"
    }

    override fun getStaticDescription(): String {
        return "Reports combinationally assigned properties that are not declared as var."
    }

    override fun getDefaultLevel(): HighlightDisplayLevel {
        return HighlightDisplayLevel.ERROR
    }

    override fun buildEnabledVisitor(holder: ProblemsHolder): PsiElementVisitor {
        return propertyVisitor { property ->
            val isVar = property.isVar
            val isCom = property.annotationEntries.any { it.shortName.toString() == "Com" }
            if (!isVar && isCom) {
                holder.registerProblem(
                    property.valOrVarKeyword,
                    "Combinationally assigned property should be declared as var"
                )
            }
        }
    }
}
