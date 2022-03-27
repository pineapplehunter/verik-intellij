/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.annotate

import org.jetbrains.kotlin.psi.KtBinaryExpression
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtNamedFunction

object SeqAssignmentChecker {

    private val TOKEN_TYPES = listOf("EQ", "PLUSEQ", "MINUSEQ")

    fun isSeqAssignment(binaryExpression: KtBinaryExpression): Boolean {
        val tokenType = binaryExpression.operationReference.operationSignTokenType
        if (tokenType.toString() !in TOKEN_TYPES) return false
        var parent = binaryExpression.parent
        while (parent != null) {
            when (parent) {
                is KtClassOrObject -> return false
                is KtNamedFunction -> return parent.annotationEntries.any { it.shortName.toString() == "Seq" }
                else -> parent = parent.parent
            }
        }
        return false
    }
}
