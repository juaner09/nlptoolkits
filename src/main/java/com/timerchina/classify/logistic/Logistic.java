package com.timerchina.classify.logistic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author betty.li
 * 2017.01.06
 * change this template use File | Settings | File Templates.
 */
public class Logistic {

	/** the learning rate */
	private double rate;

	/** the weight to learn */
	private double[] weights;

	/** the number of iterations */
	private int ITERATIONS = 2000;

	public Logistic(int n) {
		this.rate = 0.0001;
		weights = new double[n];
	}

	private double sigmoid(double z) {
		return 1 / (1 + Math.exp(-z));
	}

	public void train(String file) {
		List<Instance> instances = readDataSet(file);
		for (int n = 0; n < ITERATIONS; n++) {
			double lik = 0.0;
			for (int i = 0; i < instances.size(); i++) {
				int[] x = instances.get(i).getX();
				double predicted = classify(x);
				int label = instances.get(i).getLabel();
				for (int j = 0; j < weights.length; j++) {
					weights[j] = weights[j] + rate * (label - predicted) * x[j];
				}
				// not necessary for learning
				lik += label * Math.log(classify(x)) + (1 - label) * Math.log(1 - classify(x));
			}
			System.out.println("iteration: " + n + " " + Arrays.toString(weights) + " mle: " + lik);
		}
	}

	public double classify(int[] x) {
		double logit = .0;
		for (int i = 0; i < weights.length; i++) {
			logit += weights[i] * x[i];
		}
		return sigmoid(logit);
	}

	@SuppressWarnings("resource")
	public List<Instance> readDataSet(String file){
        List<Instance> dataset = new ArrayList<Instance>();
        Scanner scanner = null;
		try {	
			scanner = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("#")) {
                continue;
            }
            String[] columns = line.split("\\s+");

            //last column is the label
            int i = 1;
            int[] data = new int[columns.length-1];
			for (i = 0; i < columns.length - 1; i++) {
				data[i] = Integer.parseInt(columns[i]);//Integer.parseInt(columns[i]);
			}
            int label = Integer.parseInt(columns[i]);
            Instance instance = new Instance(label, data);
            dataset.add(instance);
        }
        return dataset;
    }
	
	public static void main(String... args) {
		String file = "train.txt";
		Logistic logistic = new Logistic(6);
		logistic.train(file);
		int[] x = { 1, 1, 0, 0, 0, 2 };
		System.out.println("prob(1|x) = " + logistic.classify(x));

		int[] x2 = { 1, 1, 1, 0, 0, 2 };
		System.out.println("prob(1|x2) = " + logistic.classify(x2));

	}
}
