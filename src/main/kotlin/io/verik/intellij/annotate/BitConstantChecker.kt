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

import java.math.BigInteger

object BitConstantChecker {

    fun check(value: String): Boolean {
        val compactedValue = value.replace("_", "")
        val tickCount = compactedValue.count { it == '\'' }
        if (tickCount != 1) {
            return false
        }
        val tickIndex = compactedValue.indexOf('\'')
        val width = compactedValue.substring(0, tickIndex).toIntOrNull() ?: return false

        if (compactedValue.length <= tickIndex + 2)
            return false
        val trimmedValue = compactedValue.substring(tickIndex + 2)
        return when (compactedValue[tickIndex + 1].lowercase()) {
            "d" -> checkDecBitConstant(width, trimmedValue)
            "h" -> checkHexBitConstant(width, trimmedValue)
            "b" -> checkBinBitConstant(width, trimmedValue)
            else -> false
        }
    }

    private fun checkDecBitConstant(width: Int, value: String): Boolean {
        return try {
            BigInteger(value) < BigInteger.ONE.shiftLeft(width)
        } catch (exception: NumberFormatException) {
            false
        }
    }

    private fun checkHexBitConstant(width: Int, value: String): Boolean {
        if ((width + 3) / 4 < value.length) {
            return false
        }
        value.forEach {
            if (it !in ('0' .. '9') &&
                it !in ('a' .. 'f') &&
                it !in ('A' .. 'F') &&
                it !in listOf('x', 'X', 'z', 'Z')
            ) {
                return false
            }
        }
        return true
    }

    private fun checkBinBitConstant(width: Int, value: String): Boolean {
        if (width < value.length) {
            return false
        }
        value.forEach {
            if (it !in listOf('0', '1', 'x', 'X', 'z', 'Z')) {
                return false
            }
        }
        return true
    }
}
