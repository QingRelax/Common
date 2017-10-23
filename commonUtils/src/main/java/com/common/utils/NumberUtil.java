package com.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {
	
	public static final int MONEY_SCALE = 4;
	public static final int QTY_SCALE = 4;

	public static BigDecimal convertTo2Scale(BigDecimal value) {
		if (value != null) {
			if (value.doubleValue() > 0) {
				BigDecimal newValue = value.divide(new BigDecimal("1"), 2,
						RoundingMode.HALF_DOWN);
				return newValue;
			} else {
				return new BigDecimal(0);
			}
		} else {
			return null;
		}
	}

	public static BigDecimal convertTo2ScaleWithSymbol(BigDecimal value) {
		if (value != null) {
			BigDecimal newValue = value.divide(new BigDecimal("1"), 2,
					RoundingMode.HALF_DOWN);
			return newValue;
		} else {
			return null;
		}
	}

	public static BigDecimal convertTo4Scale(BigDecimal value) {
		if (value != null) {
			if (value.doubleValue() > 0) {
				BigDecimal newValue = value.divide(new BigDecimal("1"), 4,
						RoundingMode.HALF_DOWN);
				return newValue;
			} else {
				return new BigDecimal(0);
			}
		} else {
			return null;
		}
	}
	
	public static String bigDecimalToString(BigDecimal d, int scale) {
		String s = null;
		if (d != null) {
			BigDecimal ds = BigDecimal.valueOf(d.doubleValue());
			ds.setScale(scale);
			s = ds.toPlainString();
		}
		return s;
	}
	
	public static Long emptyIfNull(Long l) {
		if (l == null) {
			return new Long(0l);
		}
		else {
			return l;
		}
	}
	
	public static Integer emptyIfNull(Integer l) {
		if (l == null) {
			return new Integer(0);
		}
		else {
			return l;
		}
	}
	
	public static BigDecimal emptyIfNull(BigDecimal l) {
		if (l == null) {
			return BigDecimal.ZERO;
		}
		else {
			return l;
		}
	}
	
	public static Double emptyIfNull(Double l) {
		if (l == null) {
			return new Double(0);
		}
		else {
			return l;
		}
	}
}
