/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.arguments

import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.resolveToCall
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtValueArgumentList
import org.jetbrains.kotlin.psi.psiUtil.getStrictParentOfType

object VerikArgumentsHelperUtil {

    fun getDescriptor(list: KtValueArgumentList): CallableDescriptor? {
        val calleeExpression = list.getStrictParentOfType<KtCallExpression>()?.calleeExpression ?: return null
        return when (val descriptor = calleeExpression.resolveToCall()?.resultingDescriptor) {
            is ClassConstructorDescriptor -> descriptor
            is SimpleFunctionDescriptor -> descriptor
            else -> null
        }
    }
}