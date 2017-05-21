package classify.test.svm;

import java.io.IOException;

import org.junit.Test;

import com.timerchina.classify.svm.svm_predict;
import com.timerchina.classify.svm.svm_train;

public class SVMTest {
	/**
	 * parameter description:
	  -s svm_type : set type of SVM (default 0)
		0 -- C-SVC
		1 -- nu-SVC
		2 -- one-class SVM
		3 -- epsilon-SVR
		4 -- nu-SVR

	-t kernel_type : set type of kernel function (default 2)
		0 -- linear: u'*v
		1 -- polynomial: (gamma*u'*v + coef0)^degree
		2 -- radial basis function: exp(-gamma*|u-v|^2)
		3 -- sigmoid: tanh(gamma*u'*v + coef0)
		4 -- precomputed kernel (kernel values in training_instance_matrix)

	-d degree : set degree in kernel function (default 3)

	-g gamma : set gamma in kernel function (default 1/k)

	-r coef0 : set coef0 in kernel function (default 0)

	-c cost : set the parameter C of C-SVC, epsilon-SVR, and nu-SVR (default 1)

	-n nu : set the parameter nu of nu-SVC, one-class SVM, and nu-SVR (default 0.5)

	-p epsilon : set the epsilon in loss function of epsilon-SVR (default 0.1)

	-m cachesize : set cache memory size in MB (default 100)

	-e epsilon : set tolerance of termination criterion (default 0.001)

	-h shrinking: whether to use the shrinking heuristics, 0 or 1 (default 1)

	-b probability_estimates: whether to train a SVC or SVR model for probability estimates, 0 or 1 (default 0)

	-wi weight: set the parameter C of class i to weight*C, for C-SVC (default 1)

	-v n: n-fold cross validation mode 
	 * */
	@Test
	public void train() throws IOException{
		String[] arg = { "-b","1","trainfile\\train.txt", // 存放SVM训练模型用的数据的路径
		"trainfile\\model_r.txt" }; // 存放SVM通过训练数据训练出来的模型的路径
		// 创建一个训练对象
		svm_train t = new svm_train();
		t.run(arg);
	}
	@Test
	public void predict() throws IOException{
		String[] parg = { "-b","1","trainfile\\labeltestTianmao.txt", // 这个是存放测试数据
				"trainfile\\model_r.txt", // 调用的是训练以后的模型
				"trainfile\\out_r.txt" }; // 生成的结果的文件的路径
		System.out.println("........SVM运行开始..........");
		
		svm_predict p = new svm_predict();
		p.run(parg); // 调用
	}
}