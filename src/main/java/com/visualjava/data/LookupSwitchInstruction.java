package com.visualjava.data;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

public class LookupSwitchInstruction extends Instruction {
    private final Map<Integer, Integer> matchOffsetPairs;

    public LookupSwitchInstruction(InputStream dataStream, Map<Integer, Integer> matchOffsetPairs) {
        super(dataStream);
        this.matchOffsetPairs = matchOffsetPairs;
    }

    public Map<Integer, Integer> getMatchOffsets() {
        return Collections.unmodifiableMap(matchOffsetPairs);
    }
}
