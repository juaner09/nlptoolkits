package com.timerchina.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

public class CommonTools {
	private static Logger logger = CommonTools.getLogger();

	public static final int PARSE_INT_ERROR = 0;
	public static final double PARSE_DOUBLE_ERROR = -100d;

	private static final char SEPARATOR = '_';

	public static Logger getLogger() {
		return Logger.getLogger(Thread.currentThread().getClass().getName());
	}
	
	public static boolean isEmpty(String value) {
		return value == null || value.equalsIgnoreCase("null") || value.trim().length() <= 0;
	}

	public static <K, V> boolean isEmpty(Map<K, V> map) {
		return map == null || map.keySet().size() <= 0;
	}

	public static boolean isEmpty(Object object) {
		return object == null || object == "" || object.equals("null");
	}

	public static <T> boolean isEmpty(Collection<T> coll) {
		return coll == null || coll.size() <= 0;
	}

	public static int parseInt(Object num) {
		try {
			return Integer.parseInt(String.valueOf(num));
		} catch (Exception e) {
			return PARSE_INT_ERROR;
		}
	}

	public static long parseLong(Object num) {
		try {
			return Long.parseLong(String.valueOf(num));
		} catch (Exception e) {
			return PARSE_INT_ERROR;
		}
	}

	public static double parseDouble(Object num) {
		try {
			return Double.parseDouble((String) num);
		} catch (Exception e) {
			return PARSE_INT_ERROR;
		}
	}

	public static float parseFloat(Object num) {
		try {
			return Float.parseFloat((String) num);
		} catch (Exception e) {
			return PARSE_INT_ERROR;
		}
	}

	public static boolean parseBoolean(Object adjudge) {
		try {
			return Boolean.parseBoolean((String) adjudge);
		} catch (Exception e) {
			return false;
		}
	}

	public static String parseString(Object num) {
		try {
			return String.valueOf(num);
		} catch (Exception e) {
			return "";
		}
	}

	public static String toUnderlineName(String s) {
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			boolean nextUpperCase = true;
			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}
			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}
//		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static Object convertType(String valueStr, Class<?> cl) {
		if ((isEmpty(valueStr) || cl == null) && cl != String.class) {
			valueStr = "-1";
		}
		try {
			if (cl == String.class) {
				return valueStr;
			} else if (cl == Timestamp.class) {
				return DateTool.parse(valueStr);
			} else if (cl == Date.class) {
				return DateTool.parse(valueStr);
			} else if (cl == Integer.class || cl == int.class) {
				return parseInt(valueStr.replace(",", ""));
			} else if (cl == Long.class || cl == long.class) {
				return parseLong(valueStr);
			} else if (cl == Double.class || cl == double.class) {
				return parseDouble(valueStr);
			} else if (cl == Float.class || cl == float.class) {
				if (valueStr.contains("%")) {
					float value = parseFloat(valueStr.replace("%", ""));
					value = value / 100;
					value = new BigDecimal(value).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
					return value;
				}
				return parseFloat(valueStr);
			} else if (cl == Object.class) {
				return valueStr;
			} else if (cl == List.class) {
				List<String> list = new ArrayList<String>();
				String[] splitStrArray = valueStr.split(",");
				for (String temp : splitStrArray) {
					list.add(temp);
				}
				return list;
			} 
		} catch (Exception e) {
			logger.error("#对象转换类型出错！valueStr:'" + valueStr + "',cl:'" + cl + "'", e);
			return null;
		}
		logger.error("#对象转换类型出错：无法识别的类型！valueStr:'" + valueStr + "',cl:'" + cl + "'");
		return valueStr;
	}
	
	public static String decode(String obj, String encode) {
		String text = "";
		try {
			text = URLDecoder.decode(obj, encode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	
	public static String encode(String obj, String encode) {
		String text = "";
		try {
			text = URLEncoder.encode(obj, encode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	
	public static int getOccurCount(String src,String find){
		int o = 0;
		int index=-1;
		while((index=src.indexOf(find, index))>-1){
			++index;
			++o;
		}
		return o;
	}
	
	public static String urlConvert(String input) {
		if (isEmpty(input)) {
			return "";
		}
		String url = null;
		try {
			url = URLDecoder.decode(input, "gb2312");
			url = URLDecoder.decode(url, "gbk");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	/**
	 * 相似度转百分比
	 */
	public static String numToPercent(double resule){
		return  NumberFormat.getPercentInstance(new Locale( "en ", "US ")).format(resule);
	}
	
	public static void main(String[] args) {
		System.out.println(CommonTools.toCamelCase("rank_Number"));
//		System.out.println(convertType("5817.8万", Object.class));
//		System.out.println(encode("×", "gbk"));
	}
}
