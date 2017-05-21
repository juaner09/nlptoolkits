package com.timerchina.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.RecursiveTask;

import com.timerchina.data.DOC;
import com.timerchina.similarity.Jaccard;

public class MatrixComputeTask extends RecursiveTask<Map<String, Map<String,Float>>>{
	private static final long serialVersionUID = 1L;
	
	private static TreeMap<String, DOC> IdToDataASC = new TreeMap<String, DOC>();
	private String rawIndex;
	private String leftIndex;
	private String rightIndex;
	private Role role;
	private Map<String, Map<String,Float>> results = new HashMap<String, Map<String,Float>>();
	private static final float simArg = 0.5f;//similarity
	
	
	public MatrixComputeTask(Map<String, DOC> data) {
		IdToDataASC.putAll(data);
		role = Role.root;
	}
	
	public MatrixComputeTask(String rawIndex) {
		this.rawIndex = rawIndex;
		role = Role.raw;
	}
	
	public MatrixComputeTask(String leftIndex, String rightIndex) {
		this.leftIndex = leftIndex;
		this.rightIndex = rightIndex;
		role = Role.unit;
	}
	
	@Override
	protected Map<String, Map<String, Float>> compute() {
		switch (role) {
			case root : createRawTaskSet();
			break;
			case raw : createUnitTaskSet();
			break;
			case unit : doCompute();
			break;
		}
		return results;
	}
	
	private void createRawTaskSet() {
		List<MatrixComputeTask> taskSet = new ArrayList<MatrixComputeTask>();
		for(String id : IdToDataASC.keySet()) {
			MatrixComputeTask task = new MatrixComputeTask(id);
			taskSet.add(task);
		}
		doTaskSet(taskSet);
	}
	
	private void createUnitTaskSet() {
		List<MatrixComputeTask> taskSet = new ArrayList<MatrixComputeTask>();
		Set<String> rightIndexSet = IdToDataASC.tailMap(rawIndex,false).keySet();
		for(String rightIndex : rightIndexSet) {
			taskSet.add(new MatrixComputeTask(rawIndex, rightIndex));
		}	
        // Asynchronous execute subTasks
        invokeAll(taskSet);
        //wait for result
        Map<String, Map<String,Float>> subRawResult;
        Map<String,Float> subResult = new HashMap<String, Float>();
        for(MatrixComputeTask task : taskSet) {
            try {         	
            	subRawResult = task.get();
            	subResult.putAll(subRawResult.get(rawIndex));          	
            }catch(Exception e) {

            }
        }
        results.put(rawIndex, subResult);
//		doTaskSet(taskSet);
	}
	
	private void doCompute() {	
		Map<String,Float> result = new HashMap<String, Float>();
		float similarity = 0;
		similarity = (float)Jaccard.jaccard(IdToDataASC.get(leftIndex).getContent(), IdToDataASC.get(rightIndex).getContent());
		
//		if(similarity >= simArg){
			result.put(rightIndex, similarity);
			results.put(leftIndex, result);
//		}
	}
	
	private void doTaskSet(List<MatrixComputeTask> taskSet) {
        //Asynchronous execute subTasks
        invokeAll(taskSet);
        //wait for result
        Map<String, Map<String,Float>> subResult;
        for(MatrixComputeTask task : taskSet) {
            try {
            	subResult = task.get();
            	results.putAll(subResult);
            }catch(Exception e) {

            }
        }
	}
	static enum Role {
		root,raw,unit;
	}
	
	public static void main(String[] args) throws Exception {	
		
	}
}
