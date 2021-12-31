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

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return if (isEnabled(holder)) {
            buildEnabledVisitor(holder)
        } else NULL_VISITOR
    }

    open fun buildEnabledVisitor(holder: ProblemsHolder): PsiElementVisitor {
        return NULL_VISITOR
    }

    internal fun isEnabled(holder: ProblemsHolder): Boolean {
        val file = holder.file
        if (file !is KtFile)
            return false
        return file.annotationEntries.any {
            it.shortName.toString() == "Verik"
        }
    }

    companion object {

        internal val NULL_VISITOR = object : KtVisitorVoid() {}
    }
}
