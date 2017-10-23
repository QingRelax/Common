package com.common.utils;

public class StringUtil {

	public final static String TAG_N = "\n";

	public static String replaceSpecialTags(String tag, String str) {
		if (str != null) {
			return str.trim().replaceAll(tag, "");
		} else {
			return null;
		}
	}

}
