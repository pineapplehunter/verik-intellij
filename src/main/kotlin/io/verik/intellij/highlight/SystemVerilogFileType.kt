/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.highlight

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

class SystemVerilogFileType : LanguageFileType(SystemVerilogLanguage.INSTANCE) {

    override fun getName(): String {
        return SystemVerilogLanguage.NAME
    }

    override fun getDescription(): String {
        return SystemVerilogLanguage.NAME
    }

    override fun getDefaultExtension(): String {
        return "sv"
    }

    override fun getIcon(): Icon {
        return IconLoader.getIcon("/META-INF/pluginIconFileType.svg", javaClass)
    }

    companion object {

        @Suppress("unused")
        val INSTANCE = SystemVerilogFileType()
    }
}
