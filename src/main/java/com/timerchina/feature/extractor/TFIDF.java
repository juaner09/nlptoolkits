package com.timerchina.feature.extractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.timerchina.nlp.doc.DOC;
import com.timerchina.nlp.word.WOD;

/**
 * @author betty.li
 * 2017.01.06
 * tfidf统计
 * */
public class TFIDF {
	
	public static Map<String, Float> tfidf(List<DOC> docList, List<WOD<String>> termList) {
		Map<String, Float> tfidfMap = new HashMap<String, Float>();
		Map<String, Float> termCountMap = new TreeMap<String, Float>();
		Map<String, Float> docCountMap = new HashMap<String, Float>();
		
		termCountMap = TermFrequency.termFrequency(termList);
		docCountMap = docCount(docList, termList);
		int docNum = docList.size();
		
		int termLen = termList.size();
		//计算tf*idf
		for(String word:termCountMap.keySet()){
			float tf = (float)termCountMap.get(word)/termLen;
			float idf = (float)Math.log((double) docNum / (docCountMap.get(word) + 1));
			float tfidf = tf * idf;
			tfidfMap.put(word, tfidf);
		}
		return tfidfMap;
	}

	/**
	 * 计算文档数
	 * */
	public static Map<String, Float> docCount(List<DOC> docList, List<WOD<String>> termList){
		Map<String, Float> docCountMap = new HashMap<String, Float>();
		for (WOD<String> word : termList) {
			String wordName = word.getName();
			//计算文档频率
			int termDocCount = 0;
			if(!docCountMap.containsKey(wordName)){
				for(DOC doc:docList){
					String content = doc.getContent();
					if(content.contains(wordName)) termDocCount ++;
				}
				docCountMap.put(wordName, (float)termDocCount);
			}
		}
		return docCountMap;
	}
}
