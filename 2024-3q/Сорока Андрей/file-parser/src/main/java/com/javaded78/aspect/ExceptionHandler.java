package com.javaded78.aspect;

import com.beust.jcommander.ParameterException;
import com.javaded78.exception.ProcessFileException;
import com.javaded78.exception.ReadFileException;
import com.javaded78.exception.WriteFileException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

public class ExceptionHandler {

    private static final Logger logger = Logger.getLogger(ExceptionHandler.class.getName());

    public static Object createProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    try {
                        return method.invoke(target, args);
                    } catch (InvocationTargetException e) {
                        handleException(e);
                        return null;
                    }
                });
    }

    private static void handleException(InvocationTargetException e) {
        Throwable cause = e.getCause();
        if (cause instanceof WriteFileException) {
            logger.severe("Failed to write to file: " + cause.getMessage());
        } else if (cause instanceof ReadFileException) {
            logger.severe("Failed to read file: " + cause.getMessage());
        } else if (cause instanceof ProcessFileException) {
            logger.severe("Failed to process file: " + cause.getMessage());
        } else if (cause instanceof ParameterException) {
            logger.severe("Incorrect arguments: " + cause.getMessage());
        }
    }
}
