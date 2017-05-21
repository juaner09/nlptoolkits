package com.timerchina.similarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.timerchina.nlp.segment.SplitWords;
import com.timerchina.nlp.stopword.StopWord;
import com.timerchina.nlp.tool.distance.Distance;
import com.timerchina.nlp.word.WOD;
import com.timerchina.utils.CommonTools;
import com.timerchina.vec.Word2VEC;

/**
 * @author betty.li
 * 2017.01.04
 * 改进的jaccard相似度计算方法(借助了word2vec获取词向量，需预先加载词向量模型)
 * 方法详细讲解见：http://blog.csdn.net/malefactor/article/details/50471118
 * */
public class JaccardImprove {
	Logger logger = Logger.getLogger(JaccardImprove.class);
	private static JaccardImprove jaccard = null;
	public static Word2VEC w2v = new Word2VEC();
	private static float threshold = 0.35f;
	
	public static JaccardImprove getInstance(){
		if (jaccard == null) {
			synchronized (JaccardImprove.class) {
				if (jaccard == null) {
					jaccard = new JaccardImprove();
				}
			}
		}
		return jaccard;
	}
	private JaccardImprove() {
		initial();
	}
	private void initial(){
		StopWord.init();
        try {
			w2v.loadJavaModel("models/vector.mod") ;
		} catch (IOException e) {
			e.printStackTrace();
		}
        logger.info("jaccard initial!");
	}
	
	public float jaccard(String str1, String str2){
		//分词
		List<String> str1List = ansjFenCi(str1);
		List<String> str2List = ansjFenCi(str2);
		float numerator = 0.01f;//分子
		float denominator = 0.0f;//分母
		//词向量——矩阵
		float[][] matrix = getCiMatrix(w2v, str1List, str2List);
		//compute numerator
		JaccardMatrix maxDot = getMaxMatrix(matrix);
		float maxValue = maxDot.getValue();
		int rowLength = matrix.length;//行
		int cellLength = matrix[0].length;
		//jaccard
		while(maxValue >= threshold){
			numerator = numerator + maxValue ;
			//置0
			int row = maxDot.getRow();
			int cell = maxDot.getCell();
			
			matrix[row] = new float[cellLength];
			for (int j = 0; j < rowLength; j++) {
				matrix[j][cell] = 0;
			}
			//重新获取最大值
			maxDot = getMaxMatrix(matrix);
			maxValue = maxDot.getValue();
		}
		//compute denominator
        float denominatorSub = 0.0f;//分母
		for (int i = 0; i < rowLength; i++) {
			float sum = getSumNotZero(matrix, i);
			denominatorSub += sum;
		}
		denominator = numerator + denominatorSub;
		float jaccard = numerator/(float)denominator;
		return jaccard;
	}
	
	private JaccardMatrix getMaxMatrix(float[][] matrix) {
		int rowlength = matrix.length;//行
        int columnlength = matrix[0].length;//列
        JaccardMatrix maxDot = new JaccardMatrix(0, 0, 0.0f);
		for (int i = 0; i < rowlength; i++) {
			for (int j = 0; j < columnlength; j++) {
				float max = maxDot.getValue();
				if (matrix[i][j] > max) {
					max = matrix[i][j];
					maxDot = new JaccardMatrix(i, j, max);
				}
			}
		}
		return maxDot;
	}
	
	private float getSumNotZero(float[][] matrix, int i){
		float sum = 0.0f;
		for(int j=0;j<matrix[i].length;j++){
			if(matrix[i][j]>0){
				sum += (1 - matrix[i][j]);
			}
		}
		return sum;
	}

	private float[][] getCiMatrix(Word2VEC w2v, List<String> str1List, List<String> str2List){
		List<String> str1ListNew = new ArrayList<>();
		List<String> str2ListNew = new ArrayList<>();
		if (!CommonTools.isEmpty(str1List)||!CommonTools.isEmpty(str2List)) {
			if (str1List.size() < str2List.size()) {
				str1ListNew = str2List;
				str2ListNew = str1List;
			}else {
				str1ListNew = str1List;
				str2ListNew = str2List;
			}
		}
		float[][] matrix = new  float[str1ListNew.size()][str2ListNew.size()];
		float dis = 0;
		for (int i = 0; i < str1ListNew.size(); i++) {
			String ciStr1 = str1ListNew.get(i).trim();
			for (int j = 0; j < str2ListNew.size(); j++) {
				String ciStr2 = str2ListNew.get(j).trim();
				//get sim value
				float[] ciVec1 = getWordVec(w2v, ciStr1);
				float[] ciVec2 = getWordVec(w2v, ciStr2);
				if (ciVec1 == null) {
					ciVec1 = new float[50];
				}
				if (ciVec2 == null) {
					ciVec2 = new float[50];
				}
				dis = Distance.cosin(ciVec1, ciVec2);
				matrix[i][j] = dis;
			}
		}
		return matrix;
	}

	private float[] getWordVec(Word2VEC w2v,String word){
        float[] value = w2v.getWordVector(word);	 
        return value;
	}
 	
	private List<String> ansjFenCi(String str){			
		List<WOD<String>> wordList = SplitWords.ToAnalysisParse(str);
		StopWord.cleanerPAS(wordList);
		StopWord.natureFilter(wordList, "");
		List<String> ciList  = new ArrayList<String>() ;
		for (WOD<String> wod : wordList) {
			ciList.add(wod.getName());	
		}
		return ciList;
	}
	
	public class JaccardMatrix {
		private float value;
		private int row;
		private int cell;
		
		public JaccardMatrix(int row, int cell,float value) {
			this.value = value;
			this.row = row;
			this.cell = cell;
		}
		
		public float getValue() {
			return value;
		}
		public void setValue(float value) {
			this.value = value;
		}
		public int getRow() {
			return row;
		}
		public void setRow(int row) {
			this.row = row;
		}
		public int getCell() {
			return cell;
		}
		public void setCell(int cell) {
			this.cell = cell;
		}
	}
}
