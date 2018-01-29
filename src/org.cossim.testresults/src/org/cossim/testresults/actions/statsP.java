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
				lines[uu] = lines[uu].trim().replaceAll(" +", " "); //replace all spaces with one sapce
				String sim_seconds = "[Ss][Ii][Mm]_[Ss][Ee][Cc][Oo][Nn][Dd][Ss]";
				String sim_ticks = "[Ss][Ii][Mm]_[Tt][Ii][Cc][Kk][Ss]";
				String final_tick = "[Ff][Ii][Nn][Aa][Ll]_[Tt][Ii][Cc][Kk]";
				String sim_freq = "[Ss][Ii][Mm]_[Ff][Rr][Ee][Qq]";
				String host_inst_rate = "[Hh][Oo][Ss][Tt]_[Ii][Nn][Ss][Tt]_[Rr][Aa][Tt][Ee]";
				String host_op_rate = "[Hh][Oo][Ss][Tt]_[Oo][Pp]_[Rr][Aa][Tt][Ee]";
				String host_tick_rate = "[Hh][Oo][Ss][Tt]_[Tt][Ii][Cc][TKk]_[Rr][Aa][Tt][Ee]";
				String host_mem_usage = "[Hh][Oo][Ss][Tt]_[Mm][Ee][Mm]_[Uu][Ss][Aa][Gg][Ee]";
				String host_seconds = "[Hh][Oo][Ss][Tt]_[Ss][Ee][Cc][Oo][Nn][Dd][Ss]";
				String sim_insts = "[Ss][Ii][Mm]_[Ii][Nn][Ss][Tt][Ss]";
				String sim_ops = "[Ss][Ii][Mm]_[Oo][Pp][Ss]";
				Pattern ptrn1 = Pattern.compile(sim_seconds);
				Pattern ptrn2 = Pattern.compile(sim_ticks);
				Pattern ptrn3 = Pattern.compile(final_tick);
				Pattern ptrn4 = Pattern.compile(sim_freq);
				Pattern ptrn5 = Pattern.compile(host_inst_rate);
				Pattern ptrn6 = Pattern.compile(host_op_rate);
				Pattern ptrn7 = Pattern.compile(host_tick_rate);
				Pattern ptrn8 = Pattern.compile(host_mem_usage);
				Pattern ptrn9 = Pattern.compile(host_seconds);
				Pattern ptrn10 = Pattern.compile(sim_insts);
				Pattern ptrn11 = Pattern.compile(sim_ops);

				
				String[] med1 = new String[2];
				if(ptrn1.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("sim_seconds", med1);
					//System.out.println("sim_seconds "+uu);
				}
				if(ptrn2.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("sim_ticks", med1);
					//System.out.println("sim_ticks "+uu);
				}
				if(ptrn3.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("final_tick", med1);
					//System.out.println("final_tick "+uu);
				}
				if(ptrn4.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("sim_freq", med1);
					//System.out.println("sim_freq "+uu);
				}
				if(ptrn5.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("host_inst_rate", med1);
					//System.out.println("host_inst_rate "+uu);
				}
				if(ptrn6.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("host_op_rate", med1);
					//System.out.println("host_op_rate "+uu);
				}
				if(ptrn7.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("host_tick_rate", med1);
					//System.out.println("host_tick_rate "+uu);
				}
				if(ptrn8.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("host_mem_usage", med1);
					//System.out.println("host_mem_usage "+uu);
				}
				if(ptrn9.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("host_seconds", med1);
					//System.out.println("host_seconds "+uu);
				}
				if(ptrn10.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("sim_insts", med1);
					//System.out.println("sim_insts "+uu);
				}
				if(ptrn11.matcher(lines[uu].trim().split("#")[0].trim().split(" ")[0].trim()).find()){
					med1[1] = lines[uu].trim().split("#")[1];
					med1[0] = lines[uu].trim().split("#")[0].trim().split(" ")[1];
					med.put("sim_ops", med1);
					//System.out.println("sim_ops "+uu);
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
		if(ok.get("sim_seconds")!=null){
			System.out.println(ok.get("sim_seconds")[0]);
		}else{
			System.out.println("nullara");
		}



	}

}
