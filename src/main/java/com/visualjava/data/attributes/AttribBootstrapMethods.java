package com.visualjava.data.attributes;

import com.visualjava.data.ClassData;
import com.visualjava.data.constants.ConstMethodHandle;
import com.visualjava.data.constants.LoadableConst;

import java.io.DataInputStream;
import java.io.IOException;

public class AttribBootstrapMethods extends Attribute {
    private BootstrapMethod[] bootstrapMethods;

    protected AttribBootstrapMethods(ClassData classData) {
        super(classData);
    }

    public static AttribBootstrapMethods read(ClassData classData, DataInputStream dis) throws IOException {
        AttribBootstrapMethods bootstrapMethods = new AttribBootstrapMethods(classData);
        int numBootstrapMethods = dis.readUnsignedShort();
        bootstrapMethods.bootstrapMethods = new BootstrapMethod[numBootstrapMethods];
        for (int i = 0; i < numBootstrapMethods; i++)
            bootstrapMethods.bootstrapMethods[i] = BootstrapMethod.read(classData, dis);
        return bootstrapMethods;
    }

    @Override
    public String toString(int depth, boolean onNewLine) {
        StringBuilder output = new StringBuilder();

        if (onNewLine) output.append("\t".repeat(depth));
        output.append("AttribBootstrapMethods ");
        if (bootstrapMethods.length == 0) output.append("[]");
        else {
            output.append("[\n");
            for (BootstrapMethod bootstrapMethod : bootstrapMethods) {
                output.append(bootstrapMethod.toString(depth + 1)).append(",\n");
            }
            output.append("\t".repeat(depth)).append("]");
        }
        return output.toString();
    }

    public static class BootstrapMethod {
        private ConstMethodHandle bootstrapMethodRef;
        private int bootstrapMethodRefIndex;
        private LoadableConst[] bootstrapMethodArgs;

        private static BootstrapMethod read(ClassData classData, DataInputStream dis) throws IOException {
            BootstrapMethod bootstrapMethod = new BootstrapMethod();
            bootstrapMethod.bootstrapMethodRefIndex = dis.readUnsignedShort();
            bootstrapMethod.bootstrapMethodRef = classData.resolveConstPoolIndex(bootstrapMethod.bootstrapMethodRefIndex, ConstMethodHandle.class);
            int numBootstrapArgs = dis.readUnsignedShort();
            bootstrapMethod.bootstrapMethodArgs = new LoadableConst[numBootstrapArgs];
            for (int i = 0; i < numBootstrapArgs; i++) {
                int bootstrapArgIndex = dis.readUnsignedShort();
                bootstrapMethod.bootstrapMethodArgs[i] = (LoadableConst) classData.resolveConstPoolIndex(bootstrapArgIndex);
            }
            return bootstrapMethod;
        }

        public String toString(int depth) {
            String indent = "\t".repeat(depth + 1);
            StringBuilder output = new StringBuilder();
            output.append("\t".repeat(depth)).append("BootstrapMethod {\n");
            output.append(indent).append("bootstrapMethodRef: ").append(bootstrapMethodRef.toString(depth+1, false)).append(",\n");
            output.append(indent).append("bootstrapMethodArgs: ");
            if (bootstrapMethodArgs.length == 0) output.append("[]\n");
            else {
                output.append("[\n");
                for (LoadableConst loadableConst : bootstrapMethodArgs) {
                    output.append("\t".repeat(depth + 2)).append(loadableConst).append(",\n");
                }
                output.append(indent).append("]\n");
            }
            output.append("\t".repeat(depth)).append("}");
            return output.toString();
        }
    }
}
