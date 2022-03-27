/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.inspection.common

import com.intellij.codeInspection.InspectionToolProvider
import com.intellij.codeInspection.LocalInspectionTool
import io.verik.intellij.inspection.inspection.ComAssignmentNotVarInspection
import io.verik.intellij.inspection.inspection.UnsupportedElementInspection
import io.verik.intellij.inspection.inspection.UnsupportedModifierInspection

class VerikInspectionProvider : InspectionToolProvider {

    override fun getInspectionClasses(): Array<Class<out LocalInspectionTool>> {
        return arrayOf(
            UnsupportedElementInspection::class.java,
            UnsupportedModifierInspection::class.java,
            ComAssignmentNotVarInspection::class.java
        )
    }
}
