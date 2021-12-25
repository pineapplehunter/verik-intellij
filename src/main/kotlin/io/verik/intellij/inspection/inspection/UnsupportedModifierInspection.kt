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
import io.verik.intellij.inspection.common.InspectionUtil
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
        return "Reports modifiers that are not supported by Verik"
    }

    override fun getDefaultLevel(): HighlightDisplayLevel {
        return HighlightDisplayLevel.ERROR
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        val visitor = modifierListVisitor { modifierList ->
            unsupportedModifiers.forEach {
                if (modifierList.hasModifier(it)) {
                    val modifier = modifierList.getModifier(it)!!
                    holder.registerProblem(modifier, "Modifier ${modifier.text} not supported")
                }
            }
        }
        return if (InspectionUtil.isEnabled(holder)) {
            visitor
        } else InspectionUtil.NULL_VISITOR
    }
}
