/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.arguments

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.jetbrains.kotlin.psi.KtValueArgument
import org.jetbrains.kotlin.psi.KtValueArgumentList

class VerikArgumentsHelperQuickFix : LocalQuickFix {

    override fun getName(): String {
        return "Add missing call arguments"
    }

    override fun getFamilyName(): String {
        return name
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val list = descriptor.psiElement as? KtValueArgumentList ?: return
        val arguments = list.arguments
        val argumentNames = arguments.mapNotNull { it.getArgumentName()?.asName?.identifier }
        val parameters = VerikArgumentsHelperUtil.getDescriptor(list)?.valueParameters ?: return
        val factory = KtPsiFactory(list)
        parameters.forEach {
            val argument = getArgument(it, arguments, argumentNames, factory)
            if (argument != null) {
                list.addArgument(argument)
            }
        }
    }

    private fun getArgument(
        parameter: ValueParameterDescriptor,
        arguments: List<KtValueArgument>,
        argumentNames: List<String>,
        factory: KtPsiFactory
    ): KtValueArgument? {
        val index = parameter.index
        if (arguments.size > index && !arguments[index].isNamed()) return null
        if (parameter.name.identifier in argumentNames) return null
        return factory.createArgument(factory.createExpression(parameter.name.identifier), parameter.name)
    }
}
