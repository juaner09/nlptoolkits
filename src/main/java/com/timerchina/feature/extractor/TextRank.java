package com.timerchina.feature.extractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.timerchina.nlp.stopword.StopWord;
import com.timerchina.nlp.utils.CommonTools;
import com.timerchina.nlp.word.WOD;

/**
 * @author betty.li
 * 2017.01.05
 * 基于TextRank算法的关键字提取，适用于单文档
 * */
public class TextRank {
	// 提取多少个关键字
    // 阻尼系数（ＤａｍｐｉｎｇＦａｃｔｏｒ），一般取值为0.85
    final static float d = 0.85f;
    // 最大迭代次数
    final static int max_iter = 200;
    final static float min_diff = 0.001f;

    /**
     * 返回结果和对应的rank
     * @param content
     * @return
     */
    public static Map<String,Float> textRank(List<WOD<String>> termList)
    {
    	Map<String, Float> map = new HashMap<String, Float>();
    	if(CommonTools.isEmpty(termList)) return map;
        map = getRank(termList);
        
        Set<Map.Entry<String, Float>> entrySet = map.entrySet();
        Map<String, Float> result = new HashMap<String, Float>();
        for (Map.Entry<String, Float> entry : entrySet)
        {
        	result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * 使用已经分好的词来计算rank
     * @param termList
     * @return
     */
    private static Map<String,Float> getRank(List<WOD<String>> termList)
    {
    	List<String> wordList = new ArrayList<String>(termList.size());
    	StopWord.cleanerPAS(termList);
    	StopWord.filter(termList);
//    	for(WOD<String> word:termList){
//    		if(RegexTools.isMatched("([a-zA-Z])", word.getName()))//去除字母
//    			continue;
//    		if(word.getName().length() == 1) continue;//单字去除
//    		wordList.add(word.getName());
//    	} 
        Map<String, Set<String>> words = new TreeMap<String, Set<String>>();
        Queue<String> que = new LinkedList<String>();
        for (String w : wordList)
        {
            if (!words.containsKey(w))
            {
                words.put(w, new TreeSet<String>());
            }
            que.offer(w);
            if (que.size() > 5)
            {
                que.poll();
            }

            for (String w1 : que)
            {
                for (String w2 : que)
                {
                    if (w1.equals(w2))
                    {
                        continue;
                    }
                    words.get(w1).add(w2);
                    words.get(w2).add(w1);
                }
            }
        }
        Map<String, Float> score = new HashMap<String, Float>();
        for (int i = 0; i < max_iter; ++i)
        {
            Map<String, Float> m = new HashMap<String, Float>();
            float max_diff = 0;
            for (Map.Entry<String, Set<String>> entry : words.entrySet())
            {
                String key = entry.getKey();
                Set<String> value = entry.getValue();
                m.put(key, 1 - d);
                for (String element : value)
                {
                    int size = words.get(element).size();
                    if (key.equals(element) || size == 0) continue;
                    m.put(key, m.get(key) + d / size * (score.get(element) == null ? 0 : score.get(element)));
                }
                max_diff = Math.max(max_diff, Math.abs(m.get(key) - (score.get(key) == null ? 0 : score.get(key))));
            }
            score = m;
            if (max_diff <= min_diff) break;
        }
        return score;
    }
}
