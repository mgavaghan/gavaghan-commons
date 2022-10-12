package org.gavaghan.commons.util;

/**
 * Utility classes for threads.
 *
 * @author <a href="mailto:mike@gavaghan.org">Mike Gavaghan</a>
 * @since 1.0
 */
public class ThreadUtils
{
    /**
     * Disallow instantiation.
     */
    private ThreadUtils()
    {
    }

    /**
     * Exception safe join. {@code InterruptedException} is ignored.
     *
     * @param t thread to join
     */
    static public void join(Thread t)
    {
        for (; ; )
        {
            try
            {
                t.join();
                return;
            }
            catch(InterruptedException ignored)
            {
            }
        }
    }

    /**
     * Exception safe join. {@code InterruptedException} is ignored.
     *
     * @param t      thread to join
     * @param millis maximum milliseconds to wait
     */
    static public boolean join(Thread t, long millis)
    {
        long remaining = millis;
        long elapsed;

        long start = System.currentTimeMillis();

        for (; ; )
        {
            try
            {
                t.join(remaining);
                return true;
            }
            catch(InterruptedException ignored)
            {
            }

            elapsed = System.currentTimeMillis() - start;
            remaining = millis - elapsed;

            if (remaining <= 0) break;
        }

        return false;
    }

    /**
     * Exception safe wait. {@code InterruptedException} is ignored.
     *
     * @param monitor object to monitor
     */
    static public void wait(Object monitor)
    {
        for (; ; )
        {
            try
            {
                monitor.wait();
                return;
            }
            catch(InterruptedException ignored)
            {
            }
        }
    }

    /**
     * Exception safe wait. {@code InterruptedException} is ignored.
     *
     * @param monitor object to monitor
     * @param millis  maximum milliseconds to wait
     * @return 'false' if wait timed out
     */
    static public boolean wait(Object monitor, long millis)
    {
        long remaining = millis;
        long elapsed;

        long start = System.currentTimeMillis();

        for (; ; )
        {
            try
            {
                monitor.wait(remaining);
                return true;
            }
            catch(InterruptedException ignored)
            {
            }

            elapsed = System.currentTimeMillis() - start;
            remaining = millis - elapsed;
            if (remaining <= 0) break;
        }

        return false;
    }

    /**
     * Exception-safe sleep. {@code InterruptedException} is ignored.
     *
     * @param millis milliseconds to sleep
     */
    static public void sleep(long millis)
    {
        long elapsed;
        long remaining = millis;

        long start = System.currentTimeMillis();

        for (; ; )
        {
            try
            {
                //noinspection BusyWait
                Thread.sleep(remaining);
                return;
            }
            catch(InterruptedException ignored)
            {
            }

            elapsed = System.currentTimeMillis() - start;
            remaining = millis - elapsed;
            if (remaining <= 0) break;
        }
    }
}
