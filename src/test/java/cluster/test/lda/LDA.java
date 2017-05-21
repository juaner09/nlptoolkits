package cluster.test.lda;

import com.timerchina.cluster.lda.Estimator;
import com.timerchina.cluster.lda.LDAOption;

/**
 * 
 * */
public class LDA implements Runnable{

	@Override
	public void run() {
		LDAOption option = new LDAOption();
		
		option.dir = "models";
		option.dfile = "train.txt";
		option.est = true;   //是否开始训练模型
		option.estc = false;   //是否是基于先前已有的模型基础上继续用新数据训练模型
		option.inf = false;   //是否使用先前已经训练好的模型进行推断
		option.modelName = "model-final";
		option.niters = 200;	//迭代次数
		Estimator estimator = new Estimator();
		estimator.init(option);
		estimator.estimate();
	}

	public static void main(String[] args) {
		new LDA().run();
	}
}
