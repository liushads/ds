package com.ppsea.ds.data;

import java.util.ArrayList;
import java.util.List;

import com.ppsea.ds.manager.MapMG;

public class Path implements Comparable<Path> {
	private List<Integer> nodes = new ArrayList<Integer>();	
	private int distance;
	/**
	 * @return the nodes
	 */
	public List<Integer> getNodes() {
		return nodes;
		
	}
	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(List<Integer> nodes) {
		this.nodes = nodes;
	}
	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	/**
	 * 得到终点城市id
	 * */
	public int getEnd(){
		//nodes中节点数一定大于等于2
		return nodes.get(nodes.size()-1);
	}
	
	public int getCityId(int index){
		return nodes.get(index);
	}
	
	public String toString(){
		StringBuffer s = new StringBuffer();
		Integer end = getEnd();
		for(Integer cityId:nodes){
			s.append(MapMG.instance.getCity(cityId).getName());
			if(cityId != end){
				s.append("->");
			}
		}
		return s.toString();
	}
	
	public int compareTo(Path path) {
		return distance -  path.getDistance();
	}
}
