package org.cossim.testwizard.actions;

import java.io.IOException;
import java.util.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

public class wizVal {
	public static Map<String, String[]> map = new LinkedHashMap<String, String[]>();         //For Clusters 8a alla3ei me to parakartw
	public static Map<String, Map<String, Object>> map2 = new LinkedHashMap<String, Map<String, Object>>();
	
	public static Map<String, String[]> nodesMap = new LinkedHashMap<String, String[]>();	 //For Nodes
	
	public static Map<String, Map<String, String>> nodesMap2 = new LinkedHashMap<String, Map<String, String>>();
	public static Map<String, String> medNodeMap = new LinkedHashMap<String, String>();

	static String conf = "";
	static String TxcNed = "";
	public static Object obj1 = new Object[15];
	static boolean[] NextOnP1;
	static boolean[][] NextOnP2;
	static boolean[][] NextOnP3;
	// for the refresh of wizPage2 after back and next;
	static Composite composite_2;
	static Composite composite_3;
	static ScrolledComposite sc2;
	static Composite container2;
	static ScrolledComposite sc3;
	static Composite container3;

	ScrolledComposite sc_4;
	static Group grp_2_4;			//Delete Cluster
	static Group grp_3_4;			//DetailsDetails
	static Group grp_4_4;			//Add Cluster
	static Group grp_5_4;			//Edit Cluster

	static Composite container_4;
	static Composite container1_4;
	static Button SaveBtn_4;
	static ScrolledComposite sccontainer_4;
	static Composite container2_4;
	static Tree tree_4;

	static Group[] grp2;
	static Group[] grp3;
	static int prevRefNodesNum = 0;
	static int prevRefClNum = 0;

	static boolean countP5 = false;
	static ScrolledComposite composite1_5;
	static Composite composite2_5;
	static Text text1_5;

	static String[] proc2;
	static String[] rem2;
	static String[] ben2;
	static String[] pow2;

	static String[] proc3;
	static String[] rem3;
	static String[] ben3;
	static String[] pow3;

	static int prevRefNodesNum3 = 0;
	static int prevRefClNum3 = 0;
	
	String confFromFile = null;
	int totNodes4 = 0; // For wizPage4

	public int gettotNodes4() {
		return totNodes4;
	}

	public void settotNodes4(int totNodes) {
		this.totNodes4 = totNodes;
	}

	int totNodes4a = 0; // For wizPage4 in ADD Cluster

	public int gettotNodes4a() {
		return totNodes4;
	}

	public void settotNodes4a(int totNodes) {
		this.totNodes4 = totNodes;
	}

	int totNodes = 0;

	public int gettotNodes() {
		return totNodes;
	}

	public void settotNodes(int totNodes) {
		this.totNodes = totNodes;
	}

	int totCl = 0;

	public int gettotCl() {
		return totCl;
	}

	public void settotCl(int totCl) {
		this.totCl = totCl;
	}

	final String exprt = "/home/cossim/GEM5/full_system_images/";
	final String armDir = "aarch-system-2014-10/";
	final String x86Dir = "x86-system/";
	
	
	String configs = "$GEM5/configs/example/fs.py";
	String benchName = "$GEM5/configs/boot/COSSIM/script";
	String path =  "$GEM5";
	String etherdump = "$GEM5";
	String SynchTime;
	String SynchTimeUnit;

	String[][] obj;  //For page3 in order to get  synchTime everytime it changes in page1
	 Map<String, Object> interMap = new LinkedHashMap<String, Object>(); //look up
	
	boolean addOrEdit = true;
	
	
	boolean Sel1 = true;
	boolean Sel2 = false;
	boolean Sel3 = false;
	boolean Saved = false;
	boolean SavedPS = true;
	boolean Saved5;
	boolean firstTime = true;
	
	
	String PFile;
	String[] clAtrrs = {"start", "end", "remoteOn", "benchmarkOn", "etherdumpOn", "Proc", "kernel", "disk-image", "mem-size", "SynchTime", "SynchTimeUnit",
			"RxPacketTime", "RxPacketTimeUnit", "machine-type", "dtb", "script", "IP", "username", "password", "PATH", "powerOn", "mcpat-xml", "coreNumber"};
	
	/* For map (Clusters) (21 positions)
	0  β†’ cluster Start			(OK)			(int)
	1  β†’ cluster End				(OK)			(int)
	2  β†’ remote					(OK)			(boolean)
	3  β†’ benchmark on				(OK)            (boolean)
	4  β†’ Etherdump 							    (boolean)
	========================================================
	5  β†’ Proc						(OK)			(String)
	6  β†’ kernel					(OK)			(String)				
	7  β†’ disk-image				(OK)			(String)
	8  β†’ mem-size					(OK)			(int 512 - 4096)
	9  β†’ SyncTime					(OK)			(int)
	10 β†’ SyncTime time unit		(OK)			(String)
	11 β†’ PacketTime 				(OK)			(int)				
	12 β†’ PacketTime time unit		(OK)			(String)
	13 β†’ machine-type 			(OK)			(String)			
	14 β†’ dtb						(OK)			(String)
	15 β†’ -b 						(OK)			(String)
	16 β†’ IP										(String)
	17 β†’ username									(String)
	18 β†’ password									(String)
	19 β†’ path (gia to cd kai to etherdump)		(String)
	20 β†’ Power     							    (boolean)
	21 β†’ mcpat-xml     			(OK)			(String) 
	22 β†’ coreNumber     			(OK)			(int)
	 */
	boolean correctF(String lines[]){
		boolean corFile = false;
		//System.out.println(corFile);
		String[] lineTokens = lines[0].split(" ");
		if(lineTokens.length > 1 ){
			for(int p=0;p<lineTokens.length;p++){
				String[] eqTokens = lineTokens[p].split("=");
				if(eqTokens[0].equals("--kernel")){
					corFile = true;
				}
			}
		}
		return corFile;
	}
	
	String[] getLines(String fileConf) throws IOException { //Briskei tis grammes. Afairei tis kenes kai ta cd(Ta cd efygan etsi ki alliws)
		String lines[] = fileConf.split("\\r?\\n");
		String lines1[] = fileConf.split("[\\r\\n]+"); // Remove empty lines

		return lines1;
	}
	//For txc.ned
	String createFileS(final Tree tree) {
		Map<String, Map<String, String>> nodesFromTree = new LinkedHashMap<String, Map<String, String>>();
		nodesFromTree = createConfFromTree(tree);
		String Txc = "";
		double time0 = 0.0;
		if(nodesFromTree.get("node0").get("RxPacketTimeUnit").equals("ms")){
			time0 = Double.parseDouble(nodesFromTree.get("node0").get("RxPacketTime"))/1000;
		}else if(nodesFromTree.get("node0").get("RxPacketTimeUnit").equals("us")){
			time0 = Double.parseDouble(nodesFromTree.get("node0").get("RxPacketTime"))/1000000;
		}
		String node0 = "simple Txc0\n{\n	parameters:\n		double RXPacketTime = default("+time0+
				");\n		int nodeNo = default(0);\n		bool sendInitialMessage = default(false);\n\n		@display(\"i=block/routing\");\n	"
				+ "gates:\n		inout gate;\n}\n";
		String s0 = "simple Txc";
		String s1 = " extends Txc0\n{\n		parameters:\n		RXPacketTime = default(";
		String s2 = ");\n		nodeNo = default(";
		String s3 = ");\n\n		sendInitialMessage = default(false);\n		@display(\"i=block/routing\");\n}\n";
		//Txc = null;
		int NodesNum = nodesFromTree.size();

		for(int oo=1;oo<NodesNum;oo++){
			double time = 0.0;
			if(nodesFromTree.get("node"+oo).get("RxPacketTimeUnit").equals("ms")){
				time = Double.parseDouble(nodesFromTree.get("node"+oo).get("RxPacketTime"))/1000;
			}else if(nodesFromTree.get("node"+oo).get("RxPacketTimeUnit").equals("us")){
				time = Double.parseDouble(nodesFromTree.get("node"+oo).get("RxPacketTime"))/1000000;
			}
			Txc = Txc+s0+oo+s1+time+s2+oo+s3;
		}
		double SynchT = 100;
		if(nodesFromTree.get("node0").get("SynchTimeUnit").equals("ms")){
			SynchT = Double.parseDouble(nodesFromTree.get("node0").get("SynchTime"))/1000;
		}else if(nodesFromTree.get("node0").get("SynchTimeUnit").equals("us")){
			SynchT = Double.parseDouble(nodesFromTree.get("node0").get("SynchTime"))/1000000;
		}
		String SynchNode = "simple SyncNode\n{\n	parameters:\n		int NumberOfHLANodes = default("+NodesNum+
				");\n		double SynchTime = default("+SynchT+");\n\n		bool sendInitialMessage = default(false);\n"+
				"		@display(\"i=block/routing\");\n	gates:\n		input in;\n		output out;\n}";
		
		
		
		Txc = "package HLANode;\n\n"+node0+Txc+SynchNode;

		return Txc;
	}

	
	String[] createConf(Map<String, Map<String, String>> nodesAll) {
		int totNodes1 = nodesAll.size();
		String[] nodes = new String[totNodes1];
		String[] build = new String[totNodes1]; // = "build/ARM/gem5.opt";
		
		final String s = " ";
		String[] nn = new String[totNodes1];
		Arrays.fill(nn, "");
		
		for (int k = 0; k < nodes.length; k++) {
			if (nodesAll.get("node" + k).get("Proc").equals("x86")) {
				build[k] = "$GEM5/build/X86/gem5.opt --listener-mode=on";
			} else if (nodesAll.get("node" + k).get("Proc").equals("ARM-32")
					|| nodesAll.get("node" + k).get("Proc").equals("ARM-64")) {
				build[k] = "$GEM5/build/ARM/gem5.opt --listener-mode=on";

			}
		}

		for (int k = 0; k < nodes.length; k++) {
			if (nodesAll.get("node" + k).containsKey("Cores")) {
				if (nodesAll.get("node" + k).get("Cores").equals("1")) { //dtd cores==1
					nn[k] = "";					
				}else if (nodesAll.get("node" + k).get("Cores").equals("2")) { //dtd cores==2
					nn[k] = " -n 2";
				} else if (nodesAll.get("node" + k).get("Cores").equals("4")) {
					nn[k] = " -n 4";
				}
			}
		}
	
		for (int k = 0; k < nodes.length; k++) {
			nodes[k] = build[k] + s + "-r -d $GEM5/node" + k + s + configs
					+ nn[k]+ " --kernel=" + nodesAll.get("node" + k).get("kernel")
					+ " --disk-image=" + nodesAll.get("node" + k).get("disk-image")
					+ " --mem-size=" + nodesAll.get("node" + k).get("mem-size")/* + "MB"*/
					+ " --SynchTime=" + nodesAll.get("node" + k).get("SynchTime")
					+ nodesAll.get("node" + k).get("SynchTimeUnit") + " --RxPacketTime="
					+ nodesAll.get("node" + k).get("RxPacketTime")
					+ nodesAll.get("node" + k).get("RxPacketTimeUnit")
					+ " --TotalNodes=" + totNodes1 + " --nodeNum=" + k;
			
			if (nodesAll.get("node" + k).containsKey("machine-type")) {
				nodes[k] = nodes[k].concat(" --machine-type=").concat(
						nodesAll.get("node" + k).get("machine-type"));
			}
			if (nodesAll.get("node" + k).containsKey("dtb")) {
				nodes[k] = nodes[k].concat(" --dtb=").concat(
						nodesAll.get("node" + k).get("dtb"));
			}
			if (nodesAll.get("node" + k).containsKey("script")) {
				nodes[k] = nodes[k].concat(" --script=").concat(nodesAll
						.get("node" + k).get("script")+".rcS");
			}
			if (nodesAll.get("node" + k).containsKey("etherdump")) {
				nodes[k] = nodes[k].concat(" --etherdump=").concat(
						nodesAll.get("node" + k).get("etherdump"));
			}if (nodesAll.get("node" + k).containsKey("mcpat-xml")) {
				nodes[k] = nodes[k].concat(" --mcpat-xml=").concat(
						nodesAll.get("node" + k).get("mcpat-xml"));
			}
		}

		return nodes;
	}

	Map createConfFromTree(final Tree tree){
		Map<String, Map<String, String>> mapFromTree = new LinkedHashMap<String, Map<String, String>>();
		//Tree levels...
		for (int i = 0; i < tree.getItemCount(); i++) {
			Map<String, String> med = new LinkedHashMap<String, String>();
			
			for (int j = 0; j < tree.getItem(i).getItemCount(); j++) {
				med.put("Proc", tree.getItem(i).getItem(j).getText());
				for (int k = 0; k < tree.getItem(i).getItem(j).getItemCount(); k++) {
					med.put(tree.getItem(i).getItem(j).getItem(k).getText(), 
							tree.getItem(i).getItem(j).getItem(k).getItem(0).getText());
				}
			}
			mapFromTree.put(tree.getItem(i).getText(), med);
		}
		return mapFromTree;
	}
	
	String createConf1(final Tree tree){
		nodesMap2 = createConfFromTree(tree);
		conf = "";
		String[] rr = new String[gettotNodes4()]; 			
		for(int ww=0;ww<rr.length;ww++){
			rr[ww]="";
		}
		for (int k = 0; k < gettotNodes4(); k++) {
			if(nodesMap2.get("node" + k).containsKey("IP")){   //Check the IP
			rr[k] = "sshpass -p '"+ nodesMap2.get("node"+k).get("password") +
					"' ssh "+ nodesMap2.get("node"+k).get("username")+
					"@"+nodesMap2.get("node"+k).get("IP") 
					+  " '(nohup "+createConf(nodesMap2)[k] +" --cossim"
					+ " > cmd"+k+".out 2> cmd"+k+".err < /dev/null &)'\n";
		}
			if(!nodesMap2.get("node" + k).containsKey("IP")){
				rr[k]=createConf(nodesMap2)[k]+" --cossim &"+"\n"; 
			}
		}
		for (int k = 0; k < gettotNodes4(); k++) {
			conf = conf+rr[k];
		}
		return conf;
	}
}
