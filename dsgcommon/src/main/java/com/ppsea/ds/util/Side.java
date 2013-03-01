package com.ppsea.ds.util;

/** */
/**
 * 图中的有向边，包括节点名及他的一个前向节点名，和它们之间的权重
 * 
 */
public class Side {
	private int preNode; // 前向节点

	private int node;// 后向节点

	private int weight;// 权重

	public Side(int preNode, int node, int weight) {
		this.preNode = preNode;
		this.node = node;
		this.weight = weight;
	}

	public int getPreNode() {
		return preNode;
	}

	public void setPreNode(int preNode) {
		this.preNode = preNode;
	}

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
