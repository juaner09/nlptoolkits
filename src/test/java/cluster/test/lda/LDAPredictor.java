package cluster.test.lda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.nlpcn.commons.lang.util.IOUtil;

import com.timerchina.cluster.lda.Inferencer;
import com.timerchina.cluster.lda.LDAOption;
import com.timerchina.cluster.lda.Model;

/**
 * 
 * */
public class LDAPredictor {

	private Inferencer inferencer;

	//输入模型文件地址初始化
	public LDAPredictor(String dir, String modelName) {
		LDAOption option = new LDAOption();
		
		option.dir = dir;
		option.modelName = modelName;
		option.est = false;   //是否开始训练模型
		option.estc = false;   //是否是基于先前已有的模型基础上继续用新数据训练模型
		option.inf = true;
		inferencer = new Inferencer();
		inferencer.init(option);
	}
	
	//推断新数据
	public Model inference(String data){//Model
		String [] docs = new String[1];
		docs[0] = data;
//		inferencer.inference(docs);
		return inferencer.inference(docs);
	}
	
	@Test
	public void test1() throws FileNotFoundException, IOException, ClassNotFoundException {
		LDAPredictor predictor = new LDAPredictor("models", "model-final");
		String path = "models/test_POS.txt";
		File file = new File(path);
		BufferedReader br = IOUtil.getReader(file.getAbsolutePath(), IOUtil.UTF8);
		
		try {
			String str = "";
			while ((str = br.readLine()) != null) {
				Model model = predictor.inference(str);
				double [] dist = model.theta[0];
				double max = dist[0];
				int index=0;
				int i=0;
				for (double d : dist) {
					if (d > max) {
						max = d;
						index=i;
					}
					i++;
				}
				System.out.println(index+"  "+max + " ");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
	}
	
	@Test
	public void test2(){
		LDAPredictor predictor = new LDAPredictor("models", "model-final");
		String input = "中铁七局 一项目部 占用 土地 酿纠纷 百人 进村 打砸";
		Model model = predictor.inference(input);
		double [] dist = model.theta[0];
		System.out.println(dist.length);
		double max = dist[0];
		int index=0;
		int i=0;
		for (double d : dist) {
			if (d > max) {
				max = d;
				index=i;
			}
			i++;
		}
		System.out.println(index+" ");
		System.out.print(max + " ");
	}
	
	public static void main(String[] args) {
	}
}
