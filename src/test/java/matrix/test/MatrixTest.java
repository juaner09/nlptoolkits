package matrix.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.timerchina.data.DOC;
import com.timerchina.matrix.MatrixComputeTask;
import com.timerchina.nlp.algorithm.cluster.data.FeatureExtractor;
import com.timerchina.nlp.algorithm.cluster.data.SimilarityCalculation;
import com.timerchina.similarity.Jaccard;
import com.timerchina.utils.HtmlMarkCleanner;

public class MatrixTest {
	@Test
	public void testMatrixComputer(){
		Map<String, DOC> dataMap = readData(0, 500, "市场资讯国内市场");
		long start = System.currentTimeMillis();
		float[][] data = matrixForkJoin(dataMap);
		System.out.println(data.length);
		long end = System.currentTimeMillis();
		System.out.println("共用时："+(end-start)/1000+"s");
	}
	
	public float[][] matrixForkJoin(Map<String, DOC> inputDataMap){
		int n = inputDataMap.size();
		float[][] data = new float[n][n];
		int size = Runtime.getRuntime().availableProcessors();
		ForkJoinPool pool = new ForkJoinPool(size);
        
        MatrixComputeTask task = new MatrixComputeTask(inputDataMap);
        Map<String, Map<String,Float>> results = new HashMap<String, Map<String,Float>>();
        try{
        	results = pool.submit(task).get();
        }catch (Exception e) {
            e.printStackTrace();
        }
        int i =0;
        for(String docId: results.keySet()){
        	Map<String,Float> simDocMap = results.get(docId);
        	int j =0;
        	for(Entry<String, Float> entry:simDocMap.entrySet()){
        		float simValue = entry.getValue();
        		data[i][j] = simValue; 
        		j++;
        	}
        	i++;
        }
        return data;
	}
	public float[][] matrix(Map<String, DOC> inputDataMap){
		int n = inputDataMap.size();
		float[][] data = new float[n][n];
		float simValue = 0;
		Object[] docIdArray = inputDataMap.keySet().toArray();
		for(int i = 0; i < docIdArray.length; i++){
			String docId = docIdArray[i].toString();
			String featureStrI = inputDataMap.get(docId).getFeature();
			for(int j = i + 1; j < docIdArray.length; j++){
				String docIdJ = docIdArray[j].toString();
				String featureStrJ = inputDataMap.get(docIdJ).getFeature();
				simValue = (float)Jaccard.jaccard(featureStrI, featureStrJ);
				data[i][j] = data[j][i] = simValue;
			}
			data[i][i] = 1.0f;
		}
		return data;
	}
	
	// 原始文本清理
	public static String cleanText(String str) {
		String content = HtmlMarkCleanner.cleanHtmlMark(str);
		content = content
				.replaceAll("\r|\n|\t", " ")
				.replaceAll("\\s+", " ")
				.replaceAll(
						"<script>.*?</script>|<script\\snonce=\"\\d+\"\\stype=\"text/javascript\">.*?</script>",
						"").replaceAll("<.*?>", "")
				.replaceAll("\\[image\\].*?\\[/image\\]|  ", "");
		return content;
	}
	/**
	 * 读取文本集
	 * 
	 * @param startLine
	 *            聚类文件起始行
	 * @param endLine
	 *            聚类文本结束行
	 * */
	public Map<String, DOC> readData(int startLine, int endLine, String section_type) {
		Map<String, DOC> dataMap = new HashMap<String, DOC>();
		try {
			FileReader fr = new FileReader("clusterDataNew.txt");
			BufferedReader br = new BufferedReader(fr);
			// 存放数据的临时变量
			String lineData = null;
			int line = 0;
			// 按行读取
			while (br.ready()) {
				// 得到原始的字符串
				lineData = br.readLine();
				// System.out.println(lineData);
				if (lineData == null)
					continue;
				try {
					JSONObject clusterDataMap = JSONObject
							.parseObject(lineData);
					String section_type_str = clusterDataMap
							.getString("section_type");
					if (!section_type_str.equals(section_type))
						continue;
					String id = clusterDataMap.getString("id");
					String content = clusterDataMap.getString("content");
					content = cleanText(content);
					if (line > startLine && line <= endLine)
						dataMap.put(id, new DOC(id, content, content));
					line++;
					if (line > endLine) {
						break;
					}
				} catch (Exception e) {
					System.out.println("出错：\t" + lineData);
				}
			}

			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return dataMap;
	}
}
