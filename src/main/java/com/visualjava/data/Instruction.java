package com.visualjava.data;

import com.visualjava.parser.Eval;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.*;

public class Instruction {
    private static final JSONArray opcodesJSON;

    static {
        try (InputStream jsonStream = Instruction.class.getResourceAsStream("/com/visualjava/opcodes.json")) {
            assert jsonStream != null;
            InputStreamReader jsonReader = new InputStreamReader(jsonStream);
            opcodesJSON = new JSONArray(new JSONTokener(jsonReader));
        } catch (IOException ioe) {
            throw new InternalError("Exceptional condition when loading opcodes.json", ioe);
        }
    }

    private int argc;
    private int index;
    private int opcode;
    private String mnemonic;
    private Map<String, Object> params;

    private Instruction() {
        params = new HashMap<>();
    }

    public static Instruction read(DataInputStream stream, int index) {
        class Helper {
            private int argc;
            private int index;

            public Helper(int index) {
                this.index = index;
            }

            void incArgc(int amount) {
                argc += amount;
            }

            void incArgc() {
                incArgc(1);
            }

            int getOffset() {
                return index + argc;
            }

            void parseParam(DataInputStream stream, JSONObject param, Map<String, Object> params) throws IOException {
                String paramName = param.getString("name");
                params.put(paramName, readInt(stream, param.getInt("argc"), param.getBoolean("sign")));
            }

            int readInt(DataInputStream stream, int bytes, boolean signed) throws IOException {
                incArgc(bytes);
                if (bytes == 1 && !signed) {
                    return stream.readUnsignedByte();
                } else if (bytes == 1) {
                    return stream.readByte();
                } else if (bytes == 2 && !signed) {
                    return stream.readUnsignedShort();
                } else if (bytes == 2) {
                    return stream.readShort();
                } else if (bytes == 4 && signed) {
                    return stream.readInt();
                } else throw new NumberFormatException();
            }
        }

        Helper helper = new Helper(index);

        try {
            Instruction instruction = new Instruction();
            instruction.index = index;
            int opcode = instruction.opcode = stream.readUnsignedByte();
            helper.incArgc();
            JSONObject opDef = null;
            for (Object entry : opcodesJSON) {
                if (entry instanceof JSONObject && ((JSONObject) entry).getInt("code") == opcode) {
                    opDef = (JSONObject) entry;
                    break;
                }
            }
            if (opDef == null) throw new InternalError("Following Opcode not found in opcodes.json: " + opcode);
            instruction.mnemonic = opDef.getString("mnem");

            Map<String, Object> params = instruction.params;
            for (Object arg : opDef.getJSONArray("args")) {
                JSONObject param = (JSONObject) arg;

                String paramName = param.getString("name");
                switch (paramName) {
                    case "_" -> {
                        int skipBytes = param.getInt("argc");
                        stream.skipNBytes(skipBytes);
                        helper.incArgc(skipBytes);
                    }
                    case "%" -> {
                        int argc = param.getInt("argc");
                        int skipBytes = (argc - helper.getOffset() % argc) % argc;
                        stream.skipNBytes(skipBytes);
                        helper.incArgc(skipBytes);
                    }
                    case "/" -> {
                        int match = Eval.eval(param.getString("match").replace("$", ""), params);
                        for (Object branch : param.getJSONArray("cases")) {
                            if (branch instanceof JSONObject) {
                                JSONObject branchObject = (JSONObject) branch;
                                if (!branchObject.getJSONArray("cond").toList().contains(match)) continue;
                                for (Object arg2 : branchObject.getJSONArray("args")) {
                                    JSONObject param2 = (JSONObject) arg2;
                                    helper.parseParam(stream, param2, params);
                                }
                            }
                        }
                    }
                    case "*" -> {
                        JSONObject varargs = param.getJSONObject("varargs");
                        int argc = Eval.eval(varargs.getString("count").replace("$", ""), params);
                        List<Map<String, Object>> argv = new LinkedList<>();
                        instruction.params.put(varargs.getString("name"), argv);
                        for (int i = 0; i < argc; i++) {
                            Map<String, Object> params2 = new HashMap<>();
                            argv.add(params2);
                            for (Object arg2 : varargs.getJSONArray("args")) {
                                JSONObject param2 = (JSONObject) arg2;
                                helper.parseParam(stream, param2, params2);
                            }
                        }
                    }
                    default -> helper.parseParam(stream, param, params);
                }
            }
            instruction.argc = helper.argc;
            return instruction;
        } catch (EOFException eofExc) {
            return null;
        } catch (IOException e) {
            throw new InternalError();
        }
    }

    public static Map<Integer, Instruction> readAll(DataInputStream stream) {
        Map<Integer, Instruction> instructionMap = new TreeMap<>(Integer::compare);
        int programIndex = 0;
        while (true) {
            Instruction instruction = read(stream, programIndex);
            if (instruction == null) break;
            instructionMap.put(programIndex, instruction);
            programIndex += instruction.argc;
        }
        return instructionMap;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public int getArgc() {
        return argc;
    }

//    public Object getParam(String paramName) {
//        return params.get(paramName);
//    }

    public <T> T getParam(String paramName) {
        return (T) params.get(paramName);
    }

    @Override
    public String toString() {
        return index + "    " + mnemonic;
    }
}
