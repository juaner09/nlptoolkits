package com.timerchina.similarity;

/**
 * @author betty.li
 * 2017.01.04
 * 编辑距离实现
 * */
public class EditDistance {
	/**
	 * 相似度计算
	 * @param strA：字符串A
	 * @param strB：字符串B
	 * @return
	 */
	public static double editDis(String strA, String strB){
		String newStrA = removeSign(strA);
		String newStrB = removeSign(strB);
		char[] chars_strA = newStrA.toCharArray();
		char[] chars_strB = newStrB.toCharArray();
		int m = chars_strA.length;
		int n = chars_strB.length;
		if (m<n) {
			String C = newStrA;
			newStrA = newStrB;
			newStrB = C;
		}
		int temp = Math.max(newStrA.length(), newStrB.length());
		int temp2 = longestCommonSubstring(newStrA, newStrB).length();
		return temp2 * 1.0 / temp;
	}

	/**
	 * 字符串清理（去除非汉字、英文、数字的字符）
	 * */
	private static String removeSign(String str) {
		StringBuffer sb = new StringBuffer();
		for (char item : str.toCharArray())
			if (charReg(item)){
				sb.append(item);
			}
		return sb.toString();
	}

	/**
	 * 判断是否为符合要求的字符
	 * */
	private static boolean charReg(char charValue) {
		return (charValue >= 0x4E00 && charValue <= 0X9FA5)//是否为汉字
				|| (charValue >= 'a' && charValue <= 'z')//是否为小写英文字母
				|| (charValue >= 'A' && charValue <= 'Z')//是否为大写英文字母
				|| (charValue >= '0' && charValue <= '9');//是否为数字
	}
	
	/**
	 * 动态规划求最长公共子串
	 * */
	private static String longestCommonSubstring(String strA, String strB) {
		char[] chars_strA = strA.toCharArray();
		char[] chars_strB = strB.toCharArray();
		int m = chars_strA.length;
		int n = chars_strB.length;
		
		int[][] matrix = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (chars_strA[i - 1] == chars_strB[j - 1])
					matrix[i][j] = matrix[i - 1][j - 1] + 1;
				else
					matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
			}
		}
		char[] result = new char[matrix[m][n]];
		int currentIndex = result.length - 1;
		while (matrix[m][n] != 0) {
			if (matrix[n] == matrix[n-1]){
				n--;
			}else if (matrix[m][n] == matrix[m - 1][n]) {
				m--;
			}else {
				result[currentIndex] = chars_strA[m - 1];
				currentIndex--;
				n--;
				m--;
			}
		}
		return new String(result);
	}
}
