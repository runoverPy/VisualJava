package com.visualjava;

import com.visualjava.data.attributes.AttribLineNumberTable;

public class CodeLineMapper {
    AttribLineNumberTable lineNumberTable;

    public CodeLineMapper(AttribLineNumberTable lineNumberTable) {
        this.lineNumberTable = lineNumberTable;
    }

    public int getPCLineNumber(int pc) {
        for (AttribLineNumberTable.CodeLineNumber lineNumber : lineNumberTable.getTable()) {
            if (pc <= lineNumber.getStartPC()) return lineNumber.getLineNum();
        }
        return -1;
    }
}
