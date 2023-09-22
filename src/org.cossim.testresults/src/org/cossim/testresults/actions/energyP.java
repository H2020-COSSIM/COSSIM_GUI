package org.cossim.testresults.actions;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class energyP {
	checkExist ce = new checkExist();


	String[] getLines(String file){
		file = file.substring(file.indexOf(System.getProperty("line.separator"))+1);
		String lines[] = file.split("\\r?\\n");
		String lines1[] = file.split("[\\r\\n]+"); // Remove empty lines
	
		return lines;
	}
	Map runEn(){
		Map<String, String[]> nodes = new LinkedHashMap<String,String[]>();
		String[] rn = new String[2];
		Pattern pattern = Pattern.compile("^energy([0-9]{1}[0-9]{0,1}[0-9]{0,1}).txt$");
		
		String gem5Path = System.getenv("GEM5");
		File currentDir = new File(gem5Path+"/McPat"); // current directory
		File[] files = currentDir.listFiles();
		
		int kk = 0;
		for (File file : files) {
			Matcher matcher = pattern.matcher(file.getName());
			if (file.isFile() && matcher.find()) {
				kk += 1;
			}
		}
		int n=kk;
		String[] st = new String[ce.nodeNum()];
		for(int i=0;i<ce.nodeNum();i++){ //Gia olous tous kombous
			st[i] = null;
			if(ce.findEnMc()[i]){
				try {
					String ss = gem5Path+"/McPat/energy"+i+".txt";
					st[i] = new String(Files.readAllBytes(Paths.get(ss))); //Periexei ta arxeia
				} catch (IOException e) {
					e.printStackTrace();
				}
			}


		}
		for(int tt=0;tt<ce.nodeNum();tt++){ //Runtime for all nodes
			if(ce.findEnMc()[tt]){
				String[] med = new String[2]; 
				String[] lines; 					
				lines = getLines(st[tt]);	
				String[] l1 = lines[1].split("runtime: ");
				
				String[] l2 = l1[1].split(" ");
				
				med[0] = l2[0].concat(" ").concat(l2[1]); //med[0]=runtime, med[1]=energy
				
				String[] l3 = lines[2].split(" ");
				
				med[1] = l3[2].concat(" ").concat(l3[3]);
				nodes.put("node"+tt, med);
			}
			
		}
		
		
		
		return nodes;
	}
	public static void main(String[] args) {
		energyP t1 = new energyP();
		t1.runEn();


	}


}