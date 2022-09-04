package com.visualjava;

import com.visualjava.data.ExceptionInfo;

public class ExceptionMapper {
    private final ExceptionInfo[] exceptionInfos;

    public ExceptionMapper(ExceptionInfo[] exceptionInfos) {
        this.exceptionInfos = exceptionInfos;
    }

    public Integer getHandlerPC(int curPC) {
        for (ExceptionInfo excInfo : exceptionInfos) {
            if (excInfo.containsPC(curPC)) return excInfo.getHandlerPC();
        }
        return null;
    }
}
