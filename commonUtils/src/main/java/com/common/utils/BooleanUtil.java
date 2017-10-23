package com.common.utils;

public class BooleanUtil {

	public static String convertToDBBooleanString(Boolean condition) {
		if (condition == null)
			return null;
		else if (condition)
			return "Y";
		else
			return "N";
	}

	public static String convertToDBBooleanStringDefaultN(Boolean condition) {
		if (condition == null)
			return "N";
		else if (condition)
			return "Y";
		else
			return "N";
	}

	public static boolean convertToBooleanDefaultFalse(String dBBooleanString) {
		if (dBBooleanString == null)
			return false; // default
		if (dBBooleanString.equalsIgnoreCase("Y"))
			return true;
		else if (dBBooleanString.equalsIgnoreCase("N"))
			return false;
		else
			return false; // default
	}

	public static boolean convertToBooleanDefaultTrue(String dBBooleanString) {
		if (dBBooleanString == null)
			return true; // default
		if (dBBooleanString.equalsIgnoreCase("Y"))
			return true;
		else if (dBBooleanString.equalsIgnoreCase("N"))
			return false;
		else
			return true; // default
	}

	public static Boolean convertToBoolean(String dBBooleanString) {
		if (dBBooleanString == null)
			return null;
		if (dBBooleanString.equalsIgnoreCase("Y"))
			return true;
		else if (dBBooleanString.equalsIgnoreCase("N"))
			return false;
		else
			return null;
	}

	public static boolean convertToBooleanFalseIfNull(String dBBooleanString) {
		if (dBBooleanString == null)
			return false;
		if (dBBooleanString.equalsIgnoreCase("Y"))
			return true;
		else if (dBBooleanString.equalsIgnoreCase("N"))
			return false;
		else
			return false;
	}
}
