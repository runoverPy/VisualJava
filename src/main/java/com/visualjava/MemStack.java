package com.visualjava;

import java.util.Stack;

public class MemStack {
    private Stack<Byte> stack;

    public Byte pop() {
        return stack.pop();
    }

    public void push(Byte b) {
        stack.push(b);
    }

    private static class Entry {
        private final Entry prev;
        private final Byte value;

        public Entry(Entry prev, Byte value) {
            this.prev = prev;
            this.value = value;
        }

        private Byte[] getStackValues(int len) {
            if (prev == null) {

            } else {
                Byte[] values = prev.getStackValues(len + 1);
                values[values.length - len] = value;
                return values;
            }
            return null;
        }
    }
}
