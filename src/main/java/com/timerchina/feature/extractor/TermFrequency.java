package com.timerchina.feature.extractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.timerchina.nlp.utils.CommonTools;
import com.timerchina.nlp.word.WOD;

/**
 * @author betty.li
 * 2017.01.06
 * 统计词频
 * */
public class TermFrequency {
	/**
     * 返回结果和对应的词频
     * @param content
     * @return
     */
    public static Map<String,Float> termFrequency(List<WOD<String>> termList)
    {
    	Map<String, Float> map = new HashMap<String, Float>();
    	if(CommonTools.isEmpty(termList)) return map;
    	map = tf(termList);
    	return map;
    }
    /**
     * 返回结果和对应的词出现次数
     * @param content
     * @return
     */
    public static Map<String, Float> termCount(List<WOD<String>> termList)
    {
    	Map<String, Float> map = new HashMap<String, Float>();
    	if(CommonTools.isEmpty(termList)) return map;
    	map = count(termList);
    	return map;
    }
    
    private static Map<String, Float> tf(List<WOD<String>> termList){
    	Map<String, Float> termFreqMap = new HashMap<String, Float>(); 
    	Map<String, Float> termCountMap = count(termList);
    	int size = termList.size();
    	for(String word:termCountMap.keySet()){
    		float count = termCountMap.get(word);
    		float freq = (float)count/size;
    		termFreqMap.put(word, freq);
    	}
    	return termFreqMap;
    }
    
    private static Map<String, Float> count(List<WOD<String>> termList){
    	Map<String, Float> termCountMap = new TreeMap<String, Float>();
    	for (WOD<String> word : termList) {
    		String wordName = word.getName();
			if (wordName.trim().length() == 0)
				continue;
			if (termCountMap.get(wordName) == null) {
				termCountMap.put(wordName, 1f);
			} else {
				termCountMap.put(wordName, termCountMap.get(wordName) + 1);
			}
		}
		return termCountMap;
    }
}
