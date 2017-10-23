package com.common.utils;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;

public class SystemCacheUtils {

	public final static String CACHE_NAME = "SystemMastersCache";

	private static Ehcache systemCache;

	public static void init() {

		if (CacheManager.getInstance() == null) {
			CacheManager.create();
		}
		systemCache = CacheManager.getInstance().getCache(CACHE_NAME);
	}

	public static void forceCacheFlush() {
		if (systemCache == null) {
			init();
		} else {
			systemCache.flush();
		}
	}

	public static void flushCache(String name) {
		if (systemCache == null) {
			init();
		} else {
			Element e = new Element(name, null);
			getSystemCache().put(e);
		}
	}

	private static Ehcache getSystemCache() {

		if (systemCache == null || systemCache.getStatus().equals(Status.STATUS_UNINITIALISED)) {
			init();
		}

		return systemCache;
	}

	public static Element getElement(String key) {
		Element e = getSystemCache().get(key);
		if (e == null) {
			e = new Element(key, null);
			getSystemCache().put(e);
		}
		return e;
	}

	public static void putElement(Element e) {
		getSystemCache().put(e);
	}

}
