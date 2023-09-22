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


public class mcpoutP {
	checkExist ce = new checkExist();
	
	String[] getLines(String file){
		file = file.substring(file.indexOf(System.getProperty("line.separator"))+1);
		String lines[] = file.split("\\r?\\n");
		String lines1[] = file.split("[\\r\\n]+"); // Remove empty lines


		return lines;
	}
	
	
	Map mcp(){
		Map<String, Map<String, String>> mcNodes = new LinkedHashMap<String, Map<String, String>>();
		Pattern pattern = Pattern.compile("^mcpatOutput([0-9]{1}[0-9]{0,1}[0-9]{0,1}).txt$");	//Fielname
		
		String gem5Path = System.getenv("GEM5");										//Path (exported))
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
		for(int i=0;i<ce.nodeNum();i++){
			st[i]=null;
			if(ce.findEnMc()[i]){
				try {
					String ss = gem5Path+"/McPat/mcpatOutput"+i+".txt";
					st[i] = new String(Files.readAllBytes(Paths.get(ss)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}


		}
		
		for(int tt=0;tt<ce.nodeNum();tt++){ //Gia oloys toys komboys
			if(ce.findEnMc()[tt]){
				Map<String, String> med = new LinkedHashMap<String, String>();
				String[] lines = null;
				
				String[] tempT = st[tt].split(":"); //Gia na brei to Processor
				for(int yy=0;yy<tempT.length;yy++){
					lines = getLines(tempT[yy]);
					for(int uu=0;uu</*20*/lines.length;uu++){ 
						if(lines[uu].trim().equals("Processor")){
							String[] ll1 = getLines(tempT[yy+1]);
							String Area = "[Aa][Rr][Ee][Aa]";
							String PeakPower = "[Pp][Ee][Aa][Kk]+\\s[Pp][Oo][Ww][Ee][Rr]";
							String TotLeakage = "[Tt][Oo][Tt][Aa][Ll]+\\s[Ll][Ee][Aa][Kk][Aa][Gg][Ee]";
							String PeakDyn = "[Pp][Ee][Aa][Kk]+\\s[Dd][Yy][Nn][Aa][Mm][Ii][Cc]";
							String RuntimeDyn = "[Rr][Uu][Nn][Tt][Ii][Mm][Ee]+\\s[Dd][Yy][Nn][Aa][Mm][Ii][Cc]";
								
							Pattern ptrn1 = Pattern.compile(Area);
							Pattern ptrn2 = Pattern.compile(PeakPower);
							Pattern ptrn3 = Pattern.compile(TotLeakage);
							Pattern ptrn4 = Pattern.compile(PeakDyn);
							Pattern ptrn5 = Pattern.compile(RuntimeDyn);


							for(int hh=0;hh<ll1.length;hh++){
								String[] line1 = ll1[hh].split(" = ");


								if(ptrn1.matcher(line1[0].trim()).find()){
									med.put(line1[0].trim(), line1[1]);
								}
								
								if(ptrn2.matcher(line1[0].trim()).find()){
									med.put(line1[0].trim(), line1[1]);					
								}
								if(ptrn3.matcher(line1[0].trim()).find()){
									med.put(line1[0].trim(), line1[1]);
								}
								if(ptrn4.matcher(line1[0].trim()).find()){
									med.put(line1[0].trim(), line1[1]);
								}
								if(ptrn5.matcher(line1[0].trim()).find()){
									med.put(line1[0].trim(), line1[1]);
								}
							}
						
						}
											}
				}
				mcNodes.put("node"+tt, med);
			}
			
		}
		
		return mcNodes;
	}
	public static void main(String[] args) {
		mcpoutP t1 = new mcpoutP();
		t1.mcp();


	}


}