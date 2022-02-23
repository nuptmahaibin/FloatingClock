package com.avatarmind.floatingclock.util;

import android.util.Log;

public class LogUtil {
    private static final boolean DEBUG = false;
    private static final String TAG = LogUtil.class.getSimpleName() + " ";
    private static int LEVEL = Log.INFO;

    public static void setLevel(int level) {
        LEVEL = level;
    }

    public static int getLevel() {
        return LEVEL;
    }

    public static int v(String msg) {
        if (DEBUG) {
            LEVEL = Log.VERBOSE;
        }

        if (LEVEL <= Log.VERBOSE) {
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            return Log.v(TAG + stacks[1].getClassName() + "[" + stacks[1].getLineNumber() + "]", msg);
        } else {
            return -1;
        }
    }

    public static int v(String msg, Throwable tr) {
        if (DEBUG) {
            LEVEL = Log.VERBOSE;
        }

        if (LEVEL <= Log.VERBOSE) {
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            return Log.v(TAG + stacks[1].getClassName() + "[" + stacks[1].getLineNumber() + "]", msg, tr);
        } else {
            return -1;
        }
    }

    public static int d(String msg) {
        if (DEBUG) {
            LEVEL = Log.VERBOSE;
        }

        if (LEVEL <= Log.DEBUG) {
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            return Log.d(TAG + stacks[1].getClassName() + "[" + stacks[1].getLineNumber() + "]", msg);
        } else {
            return -1;
        }
    }

    public static int d(String msg, Throwable tr) {
        if (DEBUG) {
            LEVEL = Log.VERBOSE;
        }

        if (LEVEL <= Log.DEBUG) {
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            return Log.d(TAG + stacks[1].getClassName() + "[" + stacks[1].getLineNumber() + "]", msg, tr);
        } else {
            return -1;
        }
    }

    public static int i(String msg) {
        if (DEBUG) {
            LEVEL = Log.VERBOSE;
        }

        if (LEVEL <= Log.INFO) {
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            return Log.i(TAG + stacks[1].getClassName() + "[" + stacks[1].getLineNumber() + "]", msg);
        } else {
            return -1;
        }
    }

    public static int i(String msg, Throwable tr) {
        if (DEBUG) {
            LEVEL = Log.VERBOSE;
        }

        if (LEVEL <= Log.INFO) {
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            return Log.i(TAG + stacks[1].getClassName() + "[" + stacks[1].getLineNumber() + "]", msg, tr);
        } else {
            return -1;
        }
    }

    public static int w(String msg) {
        if (DEBUG) {
            LEVEL = Log.VERBOSE;
        }

        if (LEVEL <= Log.WARN) {
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            return Log.w(TAG + stacks[1].getClassName() + "[" + stacks[1].getLineNumber() + "]", msg);
        } else {
            return -1;
        }
    }

    public static int w(String msg, Throwable tr) {
        if (DEBUG) {
            LEVEL = Log.VERBOSE;
        }

        if (LEVEL <= Log.WARN) {
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            return Log.w(TAG + stacks[1].getClassName() + "[" + stacks[1].getLineNumber() + "]", msg, tr);
        } else {
            return -1;
        }
    }

    public static int e(String msg) {
        if (DEBUG) {
            LEVEL = Log.VERBOSE;
        }

        if (LEVEL <= Log.ERROR) {
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            return Log.e(TAG + stacks[1].getClassName() + "[" + stacks[1].getLineNumber() + "]", msg);
        } else {
            return -1;
        }
    }

    public static int e(String msg, Throwable tr) {
        if (DEBUG) {
            LEVEL = Log.VERBOSE;
        }

        if (LEVEL <= Log.ERROR) {
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            return Log.e(TAG + stacks[1].getClassName() + "[" + stacks[1].getLineNumber() + "]", msg, tr);
        } else {
            return -1;
        }
    }
}

