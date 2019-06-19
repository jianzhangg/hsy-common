/**
 * 
 */
package com.hsy.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 张梓枫
 * @Description
 * @date: 2019年1月22日 上午11:09:20
 */
public final class DecimalUtils {

	public static final String PATTERN_THREE = "000";

	public static final String PATTERN_SIX = "000000";

	private static final String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	private static final ThreadLocal<DecimalFormat> decimalFormatLocal = new ThreadLocal<DecimalFormat>() {
		@Override
		protected DecimalFormat initialValue() {
			return new DecimalFormat();
		}
	};

	/**
	 * 生成随机码 length 位数
	 * 
	 * @return
	 */
	public static String generateUuid(int length) {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < length; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}

	/**
	 * 
	 * @author 张梓枫
	 * @Description: 根据需要生成的长度格式化数字
	 * @param @param  customer
	 * @param @param  length
	 * @param @return
	 * @return String
	 * @throws Exception
	 */
	public static String generateCustomer(int customer, int length) {
		DecimalFormat decimalFormat = decimalFormatLocal.get();
		String pattern = generatePattern(length);
		decimalFormat.applyPattern(pattern);
		return decimalFormat.format(customer);
	}

	/**
	 * 
	 * @author 张梓枫
	 * @Description: 根据传入的规则格式化数字
	 * @param @param  customer
	 * @param @param  pattern
	 * @param @return
	 * @return String
	 * @throws Exception
	 */
	public static String generateCustomer(int customer, String pattern) {
		DecimalFormat decimalFormat = decimalFormatLocal.get();
		decimalFormat.applyPattern(pattern);
		return decimalFormat.format(customer);
	}

	private static String generatePattern(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(0);
		}
		return sb.toString();
	}

	/**
	 * @author 张梓枫
	 * @Description:生成随机位数的随机数
	 * @param @param  length
	 * @param @return
	 * @return String
	 * @throws Exception
	 */
	public static String generateRandom(int length) {
		int l = generateInt(length);
		Integer random = (int) ((Math.random() * 9 + 1) * l);
		return random.toString();
	}

	private static int generateInt(int length) {
		StringBuffer sb = new StringBuffer("1");
		for (int i = 0; i < length - 1; i++) {
			sb.append(0);
		}
		return ObjectUtils.convertToInteger(sb);
	}

	/**
	 * @author 张梓枫
	 * @Description: 计算两点之间的距离
	 * @param @param  startLongitude
	 * @param @param  startLatitude
	 * @param @param  endLongitude
	 * @param @param  endLatitude
	 * @param @return
	 * @return double
	 * @throws Exception
	 */
	public double getDistance(double startLongitude, double startLatitude, double endLongitude, double endLatitude) {
		double startLon = (Math.PI / 180) * startLongitude;
		double endLon = (Math.PI / 180) * endLongitude;
		double startLat = (Math.PI / 180) * startLatitude;
		double endlat = (Math.PI / 180) * endLatitude;
		// 地球半径
		double R = 6371;
		// 两点间距离 km，如果想要米的话，结果*1000就可以了
		double d = Math.acos(Math.sin(startLat) * Math.sin(endlat) + Math.cos(startLat) * Math.cos(endlat) * Math.cos(startLon - endLon)) * R;
		d = new BigDecimal(d).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		return Math.abs(d);
	}

	/**
	 * @author 张梓枫
	 * @param number 生成的个数
	 * @param length 生成位数
	 * @return
	 * @return List<String>
	 * @throws Exception
	 * @desc 生成永不重复的随机数
	 */
	public static List<String> generateRandom(int number, int length) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		List<String> strs = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			String uuid = UUID.randomUUID().toString();
			Matcher m = p.matcher(uuid);
			String radString = m.replaceAll("").trim();
			if (radString.length() >= length) {
				strs.add(radString.substring(0, length));
				continue;
			}
			int l = length - radString.length();
			String s = DecimalUtils.generateRandom(l);
			strs.add(radString.concat(s));
		}
		return strs;
	}
}
