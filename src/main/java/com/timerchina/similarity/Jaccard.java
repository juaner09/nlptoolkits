package com.timerchina.similarity;


/**
 * @author betty.li 2017.01.04 
 * Jaccard计算
 * */
public class Jaccard {

	public static double jaccard(String strA, String strB) {
		double d = 0;
		int m = strA.length();
		int n = strB.length();
		
		String shortStr = strA;
		String longerStr = strB;
		if(n < m) {
			shortStr = strB;
			longerStr = strA;
		}
		char[] chars_strA = shortStr.toCharArray();
		
		if (m < 2 || n < 2) {
			if (strA.equals(strB))
				d = 1.0;
		} else {
			int commonNum = 0; // 相同元素个数（交集）
			
			for(char c:chars_strA){
				if(longerStr.contains(String.valueOf(c)))
					commonNum++;
			}
			int mergeNum = m + n - commonNum; // 并集元素个数
			d = (double) commonNum / mergeNum;
		}
		return d;
	}
}
