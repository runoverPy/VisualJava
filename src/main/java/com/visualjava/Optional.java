package com.visualjava;

public class Optional<T> {
    private final T value;

    private Optional(T value) {
        this.value = value;
    }

    public static <T> Optional<T> some(T value) {
        return new Optional<>(value);
    }

    public static <T> Optional<T> none() {
        return new Optional<>(null);
    }

    public static void main(String[] args) {
        Optional<String> option = Optional.none();
    }

    public T expect(String err) throws Panic {
        if (value != null) return value;
        else throw new Panic(err);
    }

    public T unwrap() throws Panic {
        if (value != null) return value;
        else throw new Panic();
    }

    public T unwrap_or_else(T other) {
        if (value != null) return value;
        else return other;
    }

    public boolean is_some() {
        return value != null;
    }

    public boolean is_none() {
        return value == null;
    }

    /**
     * This Throwable is a subclass of Error because every other error should be able to be caught before this one.
     * That is to say: DO NOT CATCH THIS
     */
    public static class Panic extends Error {
        public Panic() {
        }

        public Panic(String message) {
            super(message);
        }

        public Panic(String message, Throwable cause) {
            super(message, cause);
        }

        public Panic(Throwable cause) {
            super(cause);
        }
    }
}
