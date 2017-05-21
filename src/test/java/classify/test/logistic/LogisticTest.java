package classify.test.logistic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.nlpcn.commons.lang.util.IOUtil;

import com.timerchina.classify.logistic.Instance;
import com.timerchina.classify.logistic.Logistic;
import com.timerchina.itoolkit.common.utils.CommonTools;

public class LogisticTest {
	
	public List<Instance> readData(int nFeatures){
		File  file = new File("train/test.txt");
		List<Instance> sourceList = new ArrayList<Instance>();
		BufferedReader br;
		try {
			br = IOUtil.getReader(file.getAbsolutePath(), IOUtil.UTF8);
			String line = null;
			int index = 1;
			try {
				while((line = br.readLine()) != null){
					int[] result = new int[nFeatures];
					for (int i = 0; i < result.length; i++) {
						result[i] = CommonTools.parseInt(line.split("\\s+")[i]);
						sourceList.add(new Instance(index, result));
					}
					index ++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sourceList;
	}

	public static void main(String[] args) throws IOException {
		LogisticTest lt = new LogisticTest();
		//训练文件：特征+类别标签
		int nFeatures = 9;
		List<Instance> sourceList = lt.readData(nFeatures);
		Logistic logistic = new Logistic(nFeatures);
		String path = "train/train.txt";
		logistic.train(path);
		
		for (int i = 0; i < sourceList.size(); i++) {
			double value = logistic.classify(sourceList.get(i).getX());
//			if (value > 0.5) {
				System.out.println("logic:" + value + "----" + sourceList.get(i).getId());
//			}
		}
	}
}
