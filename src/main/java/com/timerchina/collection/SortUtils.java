package com.timerchina.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.timerchina.nlp.algorithm.cluster.data.MaxHeap;

/**
 * @author betty.li
 * 2017.01.06
 * 排序
 * */
public class SortUtils {

	/**
	 * 小根堆实现对map升序排序
	 * */
	public static Map<String, Float> ascSortByHeap(Map<String, Float> map, int size){
		Map<String, Float> result = new HashMap<String, Float>();
		for (Map.Entry<String, Float> entry : new MaxHeap<Map.Entry<String, Float>>(size, new Comparator<Map.Entry<String, Float>>()
		{
			@Override
			public int compare(Map.Entry<String, Float> o1,Map.Entry<String, Float> o2) {
					return o2.getValue().compareTo(o1.getValue());
			}
			}).addAll(map.entrySet()).toList()) {
				result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * 大根堆实现对map降序排序
	 * */
	public static Map<String, Float> descSortByHeap(Map<String, Float> map, int size){
		Map<String, Float> result = new HashMap<String, Float>();
		for (Map.Entry<String, Float> entry : new MaxHeap<Map.Entry<String, Float>>(size, new Comparator<Map.Entry<String, Float>>()
		{
			@Override
			public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
					return o1.getValue().compareTo(o2.getValue());
			}
			}).addAll(map.entrySet()).toList()) {
				result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	/**
	 * 归并排序实现对map升序排序
	 * */
	public static Map<String, Float> ascSortByMerger(Map<String, Float> map, int size) {
		Map<String, Float> result = new HashMap<String, Float>();
		List<Map.Entry<String, Float>> wordList = new ArrayList<Map.Entry<String, Float>>(map.entrySet());
		Collections.sort(wordList, new Comparator<Map.Entry<String, Float>>() {
			@Override
			public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		
		List<Entry<String, Float>> subList = new ArrayList<Map.Entry<String, Float>>();
		if (wordList.size() > size) {
			subList = wordList.subList(0, size);
		} else {
			subList = wordList;
		}
		for(Entry<String, Float> entry: subList){
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	/**
	 * 归并排序实现对map降序排序
	 * */
	public static Map<String, Float> descSortByMerger(Map<String, Float> map, int size) {
		Map<String, Float> result = new HashMap<String, Float>();
		List<Map.Entry<String, Float>> wordList = new ArrayList<Map.Entry<String, Float>>(map.entrySet());
		Collections.sort(wordList, new Comparator<Map.Entry<String, Float>>() {
			@Override
			public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		
		List<Entry<String, Float>> subList = new ArrayList<Map.Entry<String, Float>>();
		if (wordList.size() > size) {
			subList = wordList.subList(0, size);
		} else {
			subList = wordList;
		}
		for(Entry<String, Float> entry: subList){
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
