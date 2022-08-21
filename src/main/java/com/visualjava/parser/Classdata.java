package com.visualjava.parser;

import java.util.List;

public class Classdata {
    private int version_minor;
    private int version_major;
    private List<Constant> constant_pool;
    private int access_flags;
    private int class_name;
    private int super_name;
    private List<Interface> interfaces;
    private List<Field> fields;
    private List<Method> methods;
    private List<Attribute> attributes;

    public static Classdata parse(String name) {

        return null;
    }

    public abstract class Constant {}
    public class Interface {}
    public class Field {}
    public class Method {
        int access_flags;
        int name_index;
        int desc_index;
        List<Attribute> attributes;

        public static Method parse(BytesStream stream) {
            return null;
        }
    }
    public abstract class Attribute {}
}
