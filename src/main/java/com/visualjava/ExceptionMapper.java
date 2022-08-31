package com.visualjava;

import com.visualjava.data.ExceptionInfo;

public class ExceptionMapper {
    private ExceptionInfo[] exceptionInfos;

    public Integer getHandlerPC(int curPC) {
        for (ExceptionInfo excInfo : exceptionInfos) {
            if (excInfo.containsPC(curPC)) return excInfo.getHandlerPC();
        }
        return null;
    }
}
