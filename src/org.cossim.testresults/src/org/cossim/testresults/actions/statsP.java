package org.cossim.testresults.actions;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class statsP {
	dialogValues dv = new dialogValues();


	//Number of nodes in the simulation: Finds (nodes#)
	int nodeNum() {
		int n = -1;
		Pattern pattern = Pattern.compile("^node([0-9]{1}[0-9]{0,1}[0-9]{0,1})$");


		String gem5Path = System.getenv("GEM5");
		File currentDir = new File(gem5Path); // current directory
		File[] files = currentDir.listFiles();
		
		int kk = 0;
		for (File file : files) { 
			Matcher matcher = pattern.matcher(file.getName());
			if (file.isDirectory() && matcher.find()) {
				kk += 1;
			}
		}
		n = kk;
		return n;
	}
	
	//Seperate lines
	String[] getLines(String file){
		file = file.substring(file.indexOf(System.getProperty("line.separator"))+1);
		String lines[] = file.split("\\r?\\n");
		String lines1[] = file.split("[\\r\\n]+"); // Remove empty lines


		return lines1;
	}
	
	Map nodeDetails2(){
		Map<String, Map<String, String[]>> nodes = new LinkedHashMap<String, Map<String, String[]>>();
		int n = 0;
		Pattern pattern = Pattern.compile("^node([0-9]{1}[0-9]{0,1}[0-9]{0,1})$");
		String gem5Path = System.getenv("GEM5");
		File currentDir = new File(gem5Path); // current directory
		File[] files = currentDir.listFiles();


		int kk = 0;
		for (File file : files) {
			Matcher matcher = pattern.matcher(file.getName());
			if (file.isDirectory() && matcher.find()) {
				kk += 1;
			}
		}
		n = kk;
		
		String[] st = new String[n];
		for(int i=0;i<n;i++){
			try {
				String ss = gem5Path+"/node"+i+"/stats.txt";
				st[i] = new String(Files.readAllBytes(Paths.get(ss)));
			} catch (IOException e) {
				e.printStackTrace();


			}
		}
		
		for(int tt=0;tt<n;tt++){ //for all nodes
			Map<String, String[]> med = new LinkedHashMap<String, String[]>();
			String[] lines;
			lines = getLines(st[tt]);
			for(int uu=1;uu<lines.length;uu++){ //For every line
				lines[uu] = lines[uu].trim().replaceAll(" +", " "); //replace all spaces with one space
				String simSeconds = "[Ss][Ii][Mm][Ss][Ee][Cc][Oo][Nn][Dd][Ss]";
				String simTicks = "[Ss][Ii][Mm][Tt][Ii][Cc][Kk][Ss]";
				String finalTick = "[Ff][Ii][Nn][Aa][Ll][Tt][Ii][Cc][Kk]";
				String simFreq = "[Ss][Ii][Mm][Ff][Rr][Ee][Qq]";
				String hostSeconds = "[Hh][Oo][Ss][Tt][Ss][Ee][Cc][Oo][Nn][Dd][Ss]";
				String hostTickRate = "[Hh][Oo][Ss][Tt][Tt][Ii][Cc][Kk][Rr][Aa][Tt][Ee]";
				String hostMemory = "[Hh][Oo][Ss][Tt][Mm][Ee][Mm][Oo][Rr][Yy]";
				String simInsts = "[Ss][Ii][Mm][Ii][Nn][Ss][Tt][Ss]";
				String simOps = "[Ss][Ii][Mm][Oo][Pp][Ss]";
				String hostInstRate = "[Hh][Oo][Ss][Tt][Ii][Nn][Ss][Tt][Rr][Aa][Tt][Ee]";
				String hostOpRate = "[Hh][Oo][Ss][Tt][Oo][Pp][Rr][Aa][Tt][Ee]";
				
				
				Pattern ptrn1 = Pattern.compile(simSeconds);
				Pattern ptrn2 = Pattern.compile(simTicks);
				Pattern ptrn3 = Pattern.compile(finalTick);
				Pattern ptrn4 = Pattern.compile(simFreq);
				Pattern ptrn5 = Pattern.compile(hostSeconds);
				Pattern ptrn6 = Pattern.compile(hostTickRate);
				Pattern ptrn7 = Pattern.compile(hostMemory);
				Pattern ptrn8 = Pattern.compile(simInsts);
				Pattern ptrn9 = Pattern.compile(simOps);
				Pattern ptrn10 = Pattern.compile(hostInstRate);
				Pattern ptrn11 = Pattern.compile(hostOpRate);


				
				String[] med1 = new String[2];
				if(ptrn1.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("simSeconds", med1);
					//System.out.println("simSeconds "+uu);
				}
				if(ptrn2.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("simTicks", med1);
					//System.out.println("simTicks "+uu);
				}
				if(ptrn3.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("finalTick", med1);
					//System.out.println("finalTick "+uu);
				}
				if(ptrn4.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("simFreq", med1);
					//System.out.println("simFreq "+uu);
				}
				if(ptrn5.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("hostSeconds", med1);
					//System.out.println("hostSeconds "+uu);
				}
				if(ptrn6.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("hostTickRate", med1);
					//System.out.println("hostTickRate "+uu);
				}
				if(ptrn7.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("hostMemory", med1);
					//System.out.println("hostMemory "+uu);
				}
				if(ptrn8.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("simInsts", med1);
					//System.out.println("simInsts "+uu);
				}
				if(ptrn9.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("simOps", med1);
					//System.out.println("simOps "+uu);
				}
				if(ptrn10.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("hostInstRate", med1);
					//System.out.println("hostInstRate "+uu);
				}
				if(ptrn11.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("hostOpRate", med1);
					//System.out.println("hostOpRate "+uu);
				}
				


			}
			nodes.put("node"+tt, med);
		}
		
		
		return nodes;


	}
	
	
	Map nodeDetails(){
		Map<String, Map<String, String>> nodes = new LinkedHashMap<String, Map<String, String>>();
		////////////////////// Number of nodes //////////////////////////////////
		int n = 0;
		Pattern pattern = Pattern.compile("node[0-9]?[0-9]?[0-9]*$");
		String gem5Path = System.getenv("GEM5");
		File currentDir = new File(gem5Path); // current directory
		File[] files = currentDir.listFiles();


		int kk = 0;
		for (File file : files) {
			Matcher matcher = pattern.matcher(file.getName());
			if (file.isDirectory() && matcher.find()) {


				kk += 1;
			}
		}
		n = kk;
		//////////////////////Number of nodes - END//////////////////////////////////
		//////////////Content of stats in String Array for each node////////////////
		String[] st = new String[n];
		for(int i=0;i<n;i++){
			try {
				String ss = gem5Path+"/node"+i+"/stats.txt";
				//System.out.println(ss);
				st[i] = new String(Files.readAllBytes(Paths.get(ss)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//////////////Content of stats in String Array for each node - END//////////
		//////////////////////Seperate Lines of each node//////////////////////////
		for(int tt=0;tt<n;tt++){ //Gia oloys toys komboys
			Map<String, String> med = new LinkedHashMap<String, String>();
			String[] lines;
			lines = getLines(st[tt]);
			for(int uu=1;uu<12/*lines.length*/;uu++){ //gia oles tis grammes mesa ston kombo
				
				String[] line1;
				line1 = lines[uu].split("#");
				//System.out.println(uu+" l1---> "+Arrays.deepToString(line1));
				String[] line2 = new String[line1.length];
				for(int ii=0;ii<line1.length;ii++){
					line2[ii] = line1[ii].trim().replaceAll(" +", " "); //Antikatesthse osa kena breis me ena keno
				}
				String[] tempLine = line2[0].split(" ");
				med.put(tempLine[0], tempLine[1]);
			}
			nodes.put("node"+tt, med);
		}
		//////////////////////Seperate Lines of each node - END////////////////////
		return nodes;
	}
	
	public static void main(String[] args) {
		statsP t2 = new statsP();
		t2.nodeNum();
		///System.out.println("statsPOut: "+t2.nodeDetails2());
		Map ggg = t2.nodeDetails2();
		int ll = ggg.size();
		System.out.println(ll);
		Map<String, String[]> ok = new LinkedHashMap<String, String[]>();
		ok = (Map<String, String[]>) ggg.get("node0");	
		if(ok.get("simSeconds")!=null){
			System.out.println(ok.get("simSeconds")[0]);
		}else{
			System.out.println("nullara");
		}






	}


}