package com.lot.server.common.context;

public class UserContext {
    private static ThreadLocal<Integer> userThreadLocal = new ThreadLocal<>();

    public static void setUserId(Integer userId) {
        userThreadLocal.set(userId);
    }

    public static Integer getUserId() {
        return userThreadLocal.get();
    }

    public static void removeUserName() {
        userThreadLocal.remove();
    }
}
