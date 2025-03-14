package com.lot.server.common.context;

public class UserContext {
    private static ThreadLocal<String> userThreadLocal = new ThreadLocal<>();

    public static void setUserName(String userName) {
        userThreadLocal.set(userName);
    }

    public static String getUserName() {
        return userThreadLocal.get();
    }

    public static void removeUserName() {
        userThreadLocal.remove();
    }
}
