package com.visualjava.data;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class TableSwitchInstruction extends Instruction {
    private final List<Integer> jumpOffsets;

    public TableSwitchInstruction(InputStream dataStream, List<Integer> jumpOffsets) {
        super(dataStream);
        this.jumpOffsets = jumpOffsets;
    }

    public List<Integer> getJumpOffsets() {
        return Collections.unmodifiableList(jumpOffsets);
    }
}
