package com.ycj.paper.experiments.first;

import java.util.ArrayList;
import java.util.HashMap;

public class SemiSortAlgorithm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
	 * 2、半排序的算法Semi Sort（SS for short）：DR用户优先原则，先对DS用户按照共享的流量大小进行排序，
	 * 然后当一个DR用户遇到一个满足容忍时间DS用户，则与这个DS用户进行匹配；DS用户优先原则，先对DR用户按照需要的流量大小进行排序，
	 * 然后当一个DS用户遇到一个可以满足容忍时间的DR用户，则与这个DR用户进行匹配。
	 * 
	 */

	//在半排序算法中，DS用户优先的匹配算法
	public static void DSPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=list_ds;
		ArrayList<HashMap<String,String>> list_dr_original=Tools.sortUsers(list_dr);
		int successMatchUsers=0;
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
		for (int i=0;i<list_ds_original.size();i++){
			boolean isNotMatch=false;
		
			for(int j=0;j<list_dr_original.size();j++){
				if(Tools.isNotToleranceTime(list_dr_original.get(j), list_ds_original.get(i))){
					isNotMatch=true;
					dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(j), list_ds_original.get(i));
					drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(j), list_ds_original.get(i));
					dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(j),  list_ds_original.get(i));
					drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(j),  list_ds_original.get(i));
					dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(j),  list_ds_original.get(i));
					successMatchUsers++;
					list_dr_original.remove(j);
					break;
				}
			}
			
		}
		
		DataResult.fail_rate[index]+=list_dr_original.size()/(float)DataResult.DRNumber;
		DataResult.dr_contary[index]+=drExpectValue;
		DataResult.ds_contary[index]+=dsExpectValue;
		DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
		DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
		DataResult.sum_contary[index]+=dfferenceValue;
		
	}
	
	//在半排序算法中，DR用户优先的匹配算法
	public static void DRPriorty(ArrayList<HashMap<String,String>> list_ds,ArrayList<HashMap<String,String>> list_dr,int index){
		ArrayList<HashMap<String,String>> list_ds_original=Tools.sortUsers(list_ds);
		ArrayList<HashMap<String,String>> list_dr_original=list_dr;
		int successMatchUsers=0;
		int failTotal=0;
		float dfferenceValue=0,drExpectValue=0,dsExpectValue=0,drCostsValue=0,dsGainsValue=0;
		for (int i=0;i<list_dr_original.size();i++){
			boolean isNotMatch=false;
			for(int j=0;j<list_ds_original.size();j++){
				if(Tools.isNotToleranceTime(list_dr_original.get(i), list_ds_original.get(j))){
					isNotMatch=true;
					successMatchUsers++;
					dfferenceValue+=Tools.computeDifferenceValue(list_dr_original.get(i), list_ds_original.get(j));
					drExpectValue+=Tools.computeDRExpectValue(list_dr_original.get(i), list_ds_original.get(j));
					dsExpectValue+=Tools.computeDSExpectValue(list_dr_original.get(i),  list_ds_original.get(j));
					drCostsValue+=Tools.computeDRCostsValue(list_dr_original.get(i),  list_ds_original.get(j));
					dsGainsValue+=Tools.computeDSGainsValue(list_dr_original.get(i),  list_ds_original.get(j));
					list_ds_original.remove(j);
					break;
				}
			}
			
			if(!isNotMatch){
				failTotal++;
			}
		}
		
		DataResult.fail_rate[index]+=failTotal/(float)DataResult.DRNumber;
		DataResult.dr_contary[index]+=drExpectValue;
		DataResult.ds_contary[index]+=dsExpectValue;
		DataResult.dr_costs[index]+=drCostsValue/(float)successMatchUsers;
		DataResult.ds_gains[index]+=dsGainsValue/(float)successMatchUsers;
		DataResult.sum_contary[index]+=dfferenceValue;
	}
}
