package org.cossim.testresults.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dialogValues {

	static boolean allNodes1 = true;		//Gia node details. An true --> All nodes, An false --> node Selection
	static boolean allNodes2 = true;		//Gia compare node values. An true --> All 
	static String s1 = "";					//Gia show nodes
	static String s2 = "";					//Gia compare nodes
	String runFile = "";					//Gia na mpei o arxeio run - configuration (isws de xreiazetai)

	static int maxParsed;
	static statsP t2 = new statsP();
	
	static int nodeNum /*= t2.nodeNum()*/; //Plh8os Kombwn
	static int[] nodeNumA = new int[nodeNum];
	static boolean findMc = true;
	static boolean findEn = true;
	public static int[] arr(int k){
		for(int oo=0;oo<k;oo++){
			nodeNumA[oo]=oo;
		}
		return nodeNumA;
	}
	
	static boolean[] selectedNodes = new boolean[nodeNum];
	
	//private static boolean[] attrG = new boolean[11];
	private static boolean[] attrG = {true,true,true,true,true,true,true,true,true,true,true};
	public static boolean[] getAttrG() {
		return attrG;
	}
	public static void setAttrG(int i, boolean vv) {
		dialogValues.attrG[i] = vv;
	}

	//private static boolean[] attrM = new boolean[7];
	private static boolean[] attrM = {true,true,true,true,true,true,true,};
	public static boolean[] getAttrM() {
		return attrM;
	}
	public static void setAttrM(int i, boolean vv) {
		dialogValues.attrM[i] = vv;

	}
//epistrefei lista me arrays apo integers pou periexyn ta ranges..
	int[] findRange(String s){
		int[] ranNodes = null; 
		List<int[]> rangeList = new ArrayList<int[]>();
		List<Integer> l1 = new ArrayList<Integer>();
		String[] out = null;
 	    String[] out1 = null;
		String text =   "^\\d+\\s*+(-\\s*+\\d+)?(?:,\\s*+\\d+(?:\\s*-\\s*+\\d+)?)*+$";
		
		Pattern pattern = Pattern.compile(text);
		Matcher matcher = pattern.matcher(s.trim());
		
	    if(matcher.find()){
	    	out = s.split(",");
	    	out1 = new String[out.length];
	    	for(int oo=0;oo<out.length;oo++){
	    		out1[oo]=out[oo].trim();
	    	}
			int j=0;
		    for(int pp=0;pp<out1.length;pp++){
				String[] val = out1[pp].split("-");
				for(int k=0;k<val.length;k++){
					val[k]=val[k].trim();
				}
				if(val.length==2 && Integer.parseInt(val[0])>Integer.parseInt(val[1])){
				}else if(val.length==2 && Integer.parseInt(val[0])<Integer.parseInt(val[1])){
					int[] temp = new int[Integer.parseInt(val[1]) - Integer.parseInt(val[0])+1]; 
					for(int ii=0 ; ii<=Integer.parseInt(val[1]) - Integer.parseInt(val[0]); ii++){
						temp[ii] =  Integer.parseInt(val[0]) + ii;
					}
					rangeList.add(temp);
				}else if(val.length==1 || Integer.parseInt(val[0])==Integer.parseInt(val[1])){
					int[] temp = new int[1];
					temp[0] = Integer.parseInt(val[0]);
					rangeList.add(temp);
				}
				for(int y : rangeList.get(pp)){
					j=j+1;
					l1.add(y);
	    		}
			}
		    maxParsed = Collections.max(l1);
		    ranNodes = new int[j];
		    for(int yy=0;yy<j;yy++){
		    	ranNodes[yy] = l1.get(yy);
		    }
	    }
		return ranNodes;
	}

	
}
