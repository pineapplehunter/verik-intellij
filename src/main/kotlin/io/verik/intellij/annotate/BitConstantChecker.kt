/*
 * SPDX-License-Identifier: Apache-2.0
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
