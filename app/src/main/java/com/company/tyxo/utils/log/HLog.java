package com.company.tyxo.utils.log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


/**
 * 日志打印类
 * 关闭log ,只需改 HConfig 里面的 DEBUG 值
 */
public class HLog {

    private static HashMap<String, HILogger> loggerHashMap = new HashMap<>();
    private static final HILogger defaultLogger = new HPrintToLogCatLogger();

    public static void addLogger(HILogger logger) {

        String loggerName = logger.getClass().getName();
        String defaultLoggerName = defaultLogger.getClass().getName();
        if (!loggerHashMap.containsKey(loggerName)
                && !defaultLoggerName.equalsIgnoreCase(loggerName)) {
            logger.open();
            loggerHashMap.put(loggerName, logger);
        }

    }

    public static void removeLogger(HILogger logger) {
        String loggerName = logger.getClass().getName();
        if (loggerHashMap.containsKey(loggerName)) {
            logger.close();
            loggerHashMap.remove(loggerName);
        }

    }

    public static void d(Object object, String message) {

        printLoger(HConfig.LOG_DEBUG, object, message);

    }

    public static void e(Object object, String message) {

        printLoger(HConfig.LOG_ERROR, object, message);

    }

    public static void i(Object object, String message) {

        printLoger(HConfig.LOG_INFO, object, message);

    }

    public static void v(Object object, String message) {

        printLoger(HConfig.LOG_VERBOSE, object, message);

    }

    public static void w(Object object, String message) {

        printLoger(HConfig.LOG_WARN, object, message);

    }

    public static void out(Object object, String message) {

        printLoger(HConfig.SYS_OUT, object, message);

    }

    public static void err(Object object, String message) {

        printLoger(HConfig.SYS_ERR, object, message);

    }

    public static void d(String tag, String message) {

        printLoger(HConfig.LOG_DEBUG, tag, message);

    }

    public static void e(String tag, String message) {

        printLoger(HConfig.LOG_ERROR, tag, message);

    }

    public static void i(String tag, String message) {

        printLoger(HConfig.LOG_INFO, tag, message);

    }

    public static void v(String tag, String message) {

        printLoger(HConfig.LOG_VERBOSE, tag, message);

    }

    public static void w(String tag, String message) {

        printLoger(HConfig.LOG_WARN, tag, message);

    }

    public static void out(String object, String message) {

        printLoger(HConfig.SYS_OUT, object, message);

    }

    public static void err(String object, String message) {

        printLoger(HConfig.SYS_ERR, object, message);

    }

    public static void println(int priority, String tag, String message) {
        printLoger(priority, tag, message);
    }

    private static void printLoger(int priority, Object object, String message) {
        Class<?> cls = object.getClass();
        String tag = cls.getName();
        String arrays[] = tag.split("\\.");
        tag = arrays[arrays.length - 1];
        printLoger(priority, tag, message);
    }

    private static void printLoger(int priority, String tag, String message) {
        if (HConfig.DEBUG) {
            printLoger(defaultLogger, priority, tag, message);
            Iterator<Entry<String, HILogger>> iter = loggerHashMap.entrySet()
                    .iterator();
            while (iter.hasNext()) {
                Entry<String, HILogger> entry = iter.next();
                HILogger logger = entry.getValue();
                if (logger != null) {
                    printLoger(logger, priority, tag, message);
                }
            }
        }
    }

    private static void printLoger(HILogger logger, int priority, String tag, String message) {

        switch (priority) {
            case HConfig.LOG_VERBOSE:
                logger.v(tag, message);
                break;
            case HConfig.LOG_DEBUG:
                logger.d(tag, message);
                break;
            case HConfig.LOG_INFO:
                logger.i(tag, message);
                break;
            case HConfig.LOG_WARN:
                logger.w(tag, message);
                break;
            case HConfig.LOG_ERROR:
                logger.e(tag, message);
                break;

            case HConfig.SYS_OUT:
                logger.e(tag, message);
                break;

            case HConfig.SYS_ERR:
                logger.e(tag, message);
                break;
            default:
                break;
        }
    }

    /** 调试标记 */
    public static boolean getHardDebugFlag() {
        return HConfig.DEBUG;
    }
}
