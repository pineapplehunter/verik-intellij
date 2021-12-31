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
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.KtVisitorVoid

class FileAnnotationInspection : AbstractVerikInspection() {

    override fun getID(): String {
        return "VerikFileAnnotation"
    }

    override fun getDisplayName(): String {
        return "Missing file annotation"
    }

    override fun getStaticDescription(): String {
        return "Reports files that import from the Verik core library but are not annotated as Verik."
    }

    override fun getDefaultLevel(): HighlightDisplayLevel {
        return HighlightDisplayLevel.WARNING
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return if (isEnabled(holder)) {
            NULL_VISITOR
        } else FileAnnotationVisitor(holder)
    }

    private class FileAnnotationVisitor(
        private val holder: ProblemsHolder
    ) : KtVisitorVoid() {

        override fun visitImportDirective(importDirective: KtImportDirective) {
            val importedFqName = importDirective.importedFqName
            if (importedFqName != null) {
                val importedFqNameString = if (importDirective.isAllUnder) {
                    importedFqName.toString()
                } else {
                    importedFqName.parent().toString()
                }
                if (importedFqNameString == "io.verik.core") {
                    holder.registerProblem(importDirective, "File should be annotated as Verik")
                }
            }
        }
    }
}
