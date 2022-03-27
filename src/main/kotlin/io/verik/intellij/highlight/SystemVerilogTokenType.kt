/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.highlight

import com.intellij.psi.tree.IElementType

class SystemVerilogTokenType(debugName: String) : IElementType(debugName, SystemVerilogLanguage.INSTANCE)
