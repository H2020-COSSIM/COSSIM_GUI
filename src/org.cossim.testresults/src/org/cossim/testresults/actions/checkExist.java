package org.cossim.testresults.actions;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class checkExist {


	
	//Find node#, checks if they are in correct order
	boolean findNodeDir() { 
		boolean cor = false;
		int nodeNum = -1;
		int biggest;
		Pattern pattern = Pattern.compile("^node([0-9]{1}[0-9]{0,1}[0-9]{0,1})$");


		String gem5Path = System.getenv("GEM5");
	
		
		if(findRootDirs()){
			File currentDir = new File(gem5Path); // current directory
			File[] files = currentDir.listFiles();


			for (File file : files) { // Find Directories in order to find number of nodes
				Matcher matcher = pattern.matcher(file.getName());
				if (file.isDirectory() && matcher.find()) {
					nodeNum += 1;
				}
			}
			List<Integer> nodeInd = new ArrayList<Integer>();
			for (File file : files) {
				Matcher matcher = pattern.matcher(file.getName());
				if (file.isDirectory() && matcher.find()) {
					nodeInd.add(Integer.parseInt(file.getName().substring(4)));


				}
			}


			if (Collections.max(nodeInd) == nodeNum) {
				cor = true;
			}
		}


		return cor;
	}
	boolean findRootDirs(){
		boolean rd = false;
		String gem5Path = System.getenv("GEM5");
		Path gem5PathP = Paths.get(gem5Path); 


		String mc = gem5Path+"/McPat";
		Path mcP = Paths.get(mc);
		
		boolean gem5Ex = Files.exists(gem5PathP);
		boolean mcEx = Files.exists(mcP);
		
	
		rd = gem5Ex && mcEx;
		return rd;
	}
	
	//Find number of nodes
	int nodeNum() {
		int n = -1;		//If find nothing return -1
		//TODO Create regex
		Pattern pattern = Pattern.compile("^node([0-9]{1}[0-9]{0,1}[0-9]{0,1})$");


		String gem5Path = System.getenv("GEM5");


		if(findNodeDir()  && findRootDirs()){
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
		}
		
		return n;
	}
	
	//Check if all node# contain stats.txt
	boolean findStats() {
		boolean fs = false;
		String st = "stats.txt";
		String gem5Path = System.getenv("GEM5");
		File currentDir = new File(gem5Path); // current directory
		File[] files = currentDir.listFiles();
		if (findNodeDir() && findRootDirs()) {
			String[] dirs = new String[nodeNum()];
			for (int i = 0; i < nodeNum(); i++) {
				dirs[i] = gem5Path + "/node" + i;
			}
			boolean test = true;


			for (int i = 0; i < nodeNum(); i++) {
				test = test && new File(dirs[i],st).exists();
			}
			
			fs = test;


		}


		return fs;
	}
//Finds Energy#.txt and Mcpatouput#.txt
	boolean[] findEnMc(){
		boolean[] fe = new boolean[nodeNum()];
		String mcPath = System.getenv("GEM5")+"/McPat";
		File currentDir = new File(mcPath); // current directory
		File[] files = currentDir.listFiles();
		
		String en = "energy";
		String  mc = "mcpatOutput";
		String txt = ".txt";
		
		for(int k=0; k<nodeNum(); k++){
			String enC = en+k+txt;
			String mcC = mc+k+txt;
			fe[k] = new File(currentDir,enC).exists() && new File(currentDir,mcC).exists();
		}
	
		return fe;
	}


	boolean allEx(){
		boolean t;


		boolean nd = findNodeDir();
		boolean st = findStats();
		boolean r = findRootDirs();
		
		t = nd && st && r;
		
		return t;
	}
	public static void main(String[] args) {
		checkExist c = new checkExist();


	}


}