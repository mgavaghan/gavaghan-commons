package org.gavaghan.commons.util;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.concurrent.ThreadFactory;

/**
 * {@code ThreadFactory} implementation that allows setting thread priorities.
 *
 * @author <a href="mailto:mike@gavaghan.org">Mike Gavaghan</a>
 * @since 1.0
 */
public class PriorityThreadFactory implements ThreadFactory
{
    /**
     * Thread name prefix.
     */
    private final String mName;

    /**
     * Flag indicating if these threads are daemons.
     */
    private final boolean mDaemon;

    /**
     * The priority to assign to new threads.
     */
    private final int mPriority;

    /**
     * Counter for the thread name suffix.
     */
    private int mLastIndex = 0;

    /**
     * Create a new <code>PriorityThreadFactory</code>.
     *
     * @param name     this will be prepended to the thread name
     * @param priority priority to assign to each thread.
     * @param daemon   indicates if threads should be daemons
     */
    public PriorityThreadFactory(@NotNull String name, int priority, boolean daemon)
    {
        mName = name + "-{0}";
        mPriority = priority;
        mDaemon = daemon;
    }

    /**
     * Return a new Thread instance for the specified Runnable based on the
     * priority and daemon configuration specified at construction.
     *
     * @param r the <code>Runnable</code> to wrap.
     * @return the new thread.
     */
    @Override
    public Thread newThread(@NotNull Runnable r)
    {
        Thread t = new Thread(r);
        String name = MessageFormat.format(mName, ++mLastIndex);

        t.setName(name);
        t.setPriority(mPriority);
        t.setDaemon(mDaemon);

        return t;
    }
}
