/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.intellij.sdk.language;

import com.intellij.lexer.FlexAdapter;

public class SystemVerilogLexerAdapter extends FlexAdapter  {

    public SystemVerilogLexerAdapter() {
        super(new SystemVerilogLexer(null));
    }
}
