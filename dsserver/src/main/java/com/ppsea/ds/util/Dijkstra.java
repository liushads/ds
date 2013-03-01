package com.ppsea.ds.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.ppsea.ds.data.Path;

public class Dijkstra {
	private static ArrayList<Integer> redAgg = null;
	private static ArrayList<Integer> blueAgg = null;
	private static Side[] parents = null;
 
	private int[] nodes;
	private static ArrayList<Side> map = null;
	
	public Dijkstra(){
		
	}

	public void init(ArrayList<Side> sides, int[] nodes){
		this.nodes = nodes;
		map = sides;
	}	
	
	public List<Path> search(int startId){	
		List<Path> paths = new LinkedList<Path>();
		// 初始化已知最短路径的顶点集，即红点集，只加入顶点0
		redAgg = new ArrayList<Integer>();
		redAgg.add(nodes[startId]);

		// 初始化未知最短路径的顶点集,即蓝点集
		blueAgg = new ArrayList<Integer>();
		for (int i = 0; i < nodes.length; i++){
			if(i ==  startId) continue;
			blueAgg.add(nodes[i]);
		}
		// 初始化每个顶点在最短路径中的父结点,及它们之间的权重,权重-1表示无连通
		parents = new Side[nodes.length];
		parents[0] = new Side(-1, nodes[startId], 0);
		
		for (int i = 0; i < blueAgg.size(); i++) {
			int n = blueAgg.get(i);
			parents[i+1] = new Side(nodes[startId], n, getWeight(nodes[startId], n));
		}
		
		
		// 找从蓝点集中找出权重最小的那个顶点,并把它加入到红点集中
		while (blueAgg.size() > 0) {
			MinShortPath msp = getMinSideNode();
			if (msp.getWeight() == -1)
				msp.outputPath(nodes[startId]);
			else
				msp.outputPath();
			int node = msp.getLastNode();
			redAgg.add(node);
			// 如果因为加入了新的顶点,而导致蓝点集中的顶点的最短路径减小,则要重要设置
			setWeight(node);
			
			//记录最短路径集
			Path path = new Path();
			path.getNodes().addAll(msp.getNodeList());
			path.setDistance(msp.getWeight());
			paths.add(path);
		}
		return paths;
	}

	/** */
	/**
	 * 得到一个节点的父节点
	 * 
	 * @param parents
	 * @param node
	 * @return
	 */
	public static int getParent(Side[] parents, int node) {
		if (parents != null) {
			for (Side nd : parents) {
				if (nd.getNode() == node) {
					return nd.getPreNode();
				}
			}
		}
		return -1;
	}

	/** */
	/**
	 * 重新设置蓝点集中剩余节点的最短路径长度
	 * 
	 * @param preNode
	 * @param map
	 * @param blueAgg
	 */
	public static void setWeight(int preNode) {
		if (map != null && parents != null && blueAgg != null) {
			for (int node : blueAgg) {
				MinShortPath msp = getMinPath(node);
				int w1 = msp.getWeight();
				if (w1 == -1)
					continue;
				for (Side n : parents) {
					if (n.getNode() == node) {
						if (n.getWeight() == -1 || n.getWeight() > w1) {
							n.setWeight(w1);
							n.setPreNode(preNode);// 重新设置顶点的父顶点

							break;
						}
					}
				}
			}
		}
	}

	/** */
	/**
	 * 得到两点节点之间的权重
	 * 
	 * @param map
	 * @param preNode
	 * @param node
	 * @return
	 */
	public static int getWeight(int preNode, int node) {
		if (map != null) {
			for (Side s : map) {
				if (s.getPreNode() == preNode && s.getNode() == node)
					return s.getWeight();
			}
		}
		return -1;
	}

	/** */
	/**
	 * 从蓝点集合中找出路径最小的那个节点
	 * 
	 * @param map
	 * @param blueAgg
	 * @return
	 */
	public static MinShortPath getMinSideNode() {
		MinShortPath minMsp = null;
		if (blueAgg.size() > 0) {
			int index = 0;
			for (int j = 0; j < blueAgg.size(); j++) {
				MinShortPath msp = getMinPath(blueAgg.get(j));
				if (minMsp == null 
					|| (minMsp != null && minMsp.getWeight() == -1) 
					|| (msp.getWeight() != -1 && msp.getWeight() < minMsp.getWeight())) 
				{
					minMsp = msp;
					index = j;
				}
			}
			blueAgg.remove(index);
		}
		
		return minMsp;
	}

	/** */
	/**
	 * 得到某一节点的最短路径(实际上可能有多条,现在只考虑一条)
	 * 
	 * @param node
	 * @return
	 */
	public static MinShortPath getMinPath(int node) {
		MinShortPath msp = new MinShortPath(node);
		if (parents != null && redAgg != null) {
			for (int i = 0; i < redAgg.size(); i++) {
				MinShortPath tempMsp = new MinShortPath(node);
				int parent = redAgg.get(i);
				int curNode = node;
				while (parent > -1) {
					int weight = getWeight(parent, curNode);
					if (weight > -1) {
						tempMsp.addNode(parent);
						tempMsp.addWeight(weight);
						curNode = parent;
						parent = getParent(parents, parent);
					} else
						break;
				}
				if (msp.getWeight() == -1 || tempMsp.getWeight() != -1
						&& msp.getWeight() > tempMsp.getWeight())
					msp = tempMsp;
			}
		}
		return msp;
	}
}

class MinShortPath {
	private ArrayList<Integer> nodeList;// 最短路径集

	private int weight;// 最短路径

	public MinShortPath(int node) {
		nodeList = new ArrayList<Integer>();
		nodeList.add(node);
		weight = -1;
	}

	public ArrayList<Integer> getNodeList() {
		return nodeList;
	}

	public void setNodeList(ArrayList<Integer> nodeList) {
		this.nodeList = nodeList;
	}

	public void addNode(int node) {
		if (nodeList == null)
			nodeList = new ArrayList<Integer>();
		nodeList.add(0, node);
	}

	public int getLastNode() {
		int size = nodeList.size();
		return nodeList.get(size - 1);
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void outputPath() {
		outputPath(-1);
	}

	public void outputPath(int srcNode) {
//		String result = "[";
		if (srcNode != -1)
			nodeList.add(srcNode);
//		for (int i = 0; i < nodeList.size(); i++) {
//			result += "" + nodeList.get(i);
//			if (i < nodeList.size() - 1)
//				result += ",";
//		}
//		result += "]:" + weight;
//		System.out.println(result);
	}

	public void addWeight(int w) {
		if (weight == -1)
			weight = w;
		else
			weight += w;
	}
}
