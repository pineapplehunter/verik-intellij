/*
 * Copyright (c) 2022 Francis Wang
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
