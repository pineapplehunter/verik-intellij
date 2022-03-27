/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.highlight

import com.intellij.lang.Language

class SystemVerilogLanguage : Language(NAME) {

    override fun getDisplayName(): String {
        return NAME
    }

    companion object {

        const val NAME = "SystemVerilog"

        val INSTANCE = SystemVerilogLanguage()
    }
}
