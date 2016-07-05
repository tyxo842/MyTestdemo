package com.company.tyxo.ui.manager;

/**
 * Created by LiYang on 2016/7/4 15: 23.
 * Mail      1577441454@qq.com
 * Describe :  未用到,可以删除此类,来自反编译的qq5.0,内含错误未改完(混淆后)
 */
public final class ThreadManager {

    /*private static Handler jdField_a_of_type_AndroidOsHandler;
    private static HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
    private static Timer jdField_a_of_type_JavaUtilTimer;
    public static final Executor a;
    public static final boolean a;
    private static Handler jdField_b_of_type_AndroidOsHandler;
    private static HandlerThread jdField_b_of_type_AndroidOsHandlerThread;
    private static Handler c;

    static
    {
        jdField_a_of_type_JavaUtilConcurrentExecutor = b();
    }

    public static Handler a()
    {
        if (jdField_b_of_type_AndroidOsHandler == null);
        try
        {
            jdField_b_of_type_AndroidOsHandlerThread = new HandlerThread("QQ_FILE_RW");
            jdField_b_of_type_AndroidOsHandlerThread.start();
            jdField_b_of_type_AndroidOsHandler = new Handler(jdField_b_of_type_AndroidOsHandlerThread.getLooper());
            return jdField_b_of_type_AndroidOsHandler;
        }
        finally
        {
        }
    }

    public static Looper a()
    {
        return a().getLooper();
    }

    public static Thread a()
    {
        if (jdField_a_of_type_AndroidOsHandlerThread == null)
            b();
        return jdField_a_of_type_AndroidOsHandlerThread;
    }

    public static Timer a()
    {
        if (jdField_a_of_type_JavaUtilTimer == null);
        try
        {
            jdField_a_of_type_JavaUtilTimer = new fkb("QQ_Timer");
            return jdField_a_of_type_JavaUtilTimer;
        }
        finally
        {
        }
    }

    public static Executor a()
    {
        return new fkd(null);
    }

    public static void a()
    {
    }

    public static void a(Runnable paramRunnable)
    {
        jdField_a_of_type_JavaUtilConcurrentExecutor.execute(paramRunnable);
    }

    public static Handler b()
    {
        if (jdField_a_of_type_AndroidOsHandler == null);
        try
        {
            jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread("QQ_SUB");
            jdField_a_of_type_AndroidOsHandlerThread.start();
            jdField_a_of_type_AndroidOsHandler = new Handler(jdField_a_of_type_AndroidOsHandlerThread.getLooper());
            return jdField_a_of_type_AndroidOsHandler;
        }
        finally
        {
        }
    }

    public static Looper b()
    {
        return b().getLooper();
    }

    @TargetApi(11)
    private static Executor b()
    {
        Object localObject2;
        if (VersionUtils.e())
            localObject2 = AsyncTask.THREAD_POOL_EXECUTOR;
        while (true)
        {
            if ((localObject2 instanceof ThreadPoolExecutor))
                ((ThreadPoolExecutor)localObject2).setCorePoolSize(3);
            return localObject2;
            try
            {
                Field localField = AsyncTask.class.getDeclaredField("sExecutor");
                localField.setAccessible(true);
                localObject1 = (Executor)localField.get(null);
                localObject2 = localObject1;
            }
            catch (Exception localException)
            {
                while (true)
                {
                    QLog.e("ThreadManager", 1, localException.getMessage(), localException);
                    Object localObject1 = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue());
                }
            }
        }
    }

    public static void b(Runnable paramRunnable)
    {
        b().post(paramRunnable);
    }

    public static Handler c()
    {
        if (c == null);
        try
        {
            HandlerThread localHandlerThread = new HandlerThread("Msg_Handler");
            localHandlerThread.start();
            c = new Handler(localHandlerThread.getLooper());
            return c;
        }
        finally
        {
        }
    }*/
}