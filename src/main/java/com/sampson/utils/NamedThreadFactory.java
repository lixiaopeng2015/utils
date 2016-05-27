package com.sampson.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * ProjectName: utils
 * CreateUser:  lixiaopeng
 * Email: lixiaopeng913@sina.com
 * CreateTime:  2015/11/30 11:00
 * ModifyUser:  lixiaopeng
 * ModifyTime:  2015/11/30 11:00
 * Class Description:
 * To change this template use File | Settings | File Templates.
 */
public class NamedThreadFactory implements ThreadFactory
{
	private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);

	private final AtomicInteger mThreadNum = new AtomicInteger(1);

	private final String mPrefix;

	private final boolean mDaemo;

	private final ThreadGroup mGroup;

	public NamedThreadFactory()
	{
		this("pool-" + POOL_SEQ.getAndIncrement(),false);
	}

	public NamedThreadFactory(String prefix)
	{
		this(prefix,false);
	}

	/**
	 * constructor
	 * @param prefix
	 * @param daemo
	 */
	public NamedThreadFactory(String prefix, boolean daemo)
	{
		mPrefix = prefix + "-thread-";
		mDaemo = daemo;
        SecurityManager s = System.getSecurityManager();
        mGroup = ( s == null ) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
	}

	// new a thread
	public Thread newThread(Runnable runnable)
	{
		String name = mPrefix + mThreadNum.getAndIncrement();
        Thread ret = new Thread(mGroup,runnable,name,0);
        ret.setDaemon(mDaemo);
        return ret;
	}

	public ThreadGroup getThreadGroup()
	{
		return mGroup;
	}
}