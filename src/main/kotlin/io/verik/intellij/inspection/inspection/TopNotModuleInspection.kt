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
import org.jetbrains.kotlin.psi.classOrObjectVisitor

class TopNotModuleInspection : AbstractVerikInspection() {

    override fun getID(): String {
        return "VerikTopNotModule"
    }

    override fun getDisplayName(): String {
        return "Top not module"
    }

    override fun getStaticDescription(): String {
        return "Reports declarations that are annotated as top but are not modules."
    }

    override fun getDefaultLevel(): HighlightDisplayLevel {
        return HighlightDisplayLevel.ERROR
    }

    override fun buildEnabledVisitor(holder: ProblemsHolder): PsiElementVisitor {
        return classOrObjectVisitor { classOrObject ->
            val isSynthTop = classOrObject.annotationEntries.any { it.shortName.toString() == "SynthTop" }
            val isSimTop = classOrObject.annotationEntries.any { it.shortName.toString() == "SimTop" }
            val isModule = classOrObject.superTypeListEntries.any { it.text.removeSuffix("()") == "Module" }
            if ((isSynthTop || isSimTop) && !isModule) {
                val declarationKeyword = classOrObject.getDeclarationKeyword()
                if (declarationKeyword != null) {
                    holder.registerProblem(declarationKeyword, "Declaration annotated as top must be a module")
                }
            }
        }
    }
}
