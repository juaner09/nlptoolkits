package com.timerchina.classify.logistic;

/**
 * @author betty.li
 * 2017.01.06
 * To change this template use File | Settings | File Templates.
 */
public class Instance {
	public String id;
	public int label;
    public int[] x;

    public Instance(int label, int[] x) {
        this.label = label;
        this.x = x;
    }
    public Instance(String id, int[] x) {
        this.id = id;
        this.x = x;
    }

    public int getLabel() {
        return label;
    }

    public int[] getX() {
        return x;
    }
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
