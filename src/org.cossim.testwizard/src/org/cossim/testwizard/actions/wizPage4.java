package org.cossim.testwizard.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;


public class wizPage4 extends WizardPage {

	private wizVal values;
	//private Button[] btn;
	private int newLength;
	private int newLength1;

	boolean add = false;
	
	public wizPage4(wizVal values) {
		super("CreateFile");
		setTitle("Configuration of Nodes");
		setDescription("Add/Delete/Edit nodes and cluster of nodes and create the configuration file");
		this.values = values;
	}

	public void createControl(Composite parent) {

		values.sccontainer_4 = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		values.sccontainer_4.setExpandVertical(true);
		values.sccontainer_4.setExpandHorizontal(true);

		values.container_4 = new Composite(values.sccontainer_4, SWT.NULL);
		values.container_4.setLayout(new FillLayout());
		GridLayout contLayout = new GridLayout();
		contLayout.numColumns = 2;
		contLayout.makeColumnsEqualWidth = false;
		values.container_4.setLayout(contLayout);

		values.sccontainer_4.setContent(values.container_4);

		Composite container1 = new Composite(values.container_4, SWT.NULL);
		container1.setLayout(new GridLayout(1, false));
		
		values.container2_4 = new Composite(values.container_4, SWT.NULL);
		values.container2_4.setLayout(new GridLayout(3, false));

		values.container1_4 = new Composite(values.container_4, SWT.NULL);
		values.container1_4.setLayout(new GridLayout(1, false));
		
		final Button RefreshBtn = new Button(container1, SWT.PUSH);
		RefreshBtn.setText("Undo Changes");

		values.sc_4 = new ScrolledComposite(container1,	SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		values.sc_4.setExpandVertical(true);
		values.sc_4.setExpandHorizontal(true);

		setControl(values.sccontainer_4);

		values.grp_2_4 = new Group(values.container2_4, SWT.NONE); //Delete Cluster
		values.grp_2_4.setText("Delete Cluster");
		values.grp_2_4.setLayout(new GridLayout(2, true));

		values.grp_4_4 = new Group(values.container2_4, SWT.NONE);  //Add Cluster mikro
		values.grp_4_4.setText("Selection of Modification");
		values.grp_4_4.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true,2, 1)); // Alignments, spaces etc
		values.grp_4_4.setLayout(new GridLayout(5, true));

		values.grp_3_4 = new Group(values.container2_4, SWT.NONE);  //Details gia Add Cluster kai edit node
		values.grp_3_4.setText("Node Details");
		values.grp_3_4.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true,3, 1)); // Alignments, spaces etc
		values.grp_3_4.setLayout(new GridLayout(8, false));
		values.grp_3_4.setEnabled(true);

	
		//Fist Time Widgets 
		createp4(createNodes(), values.sc_4, values.grp_2_4, values.grp_3_4, values.grp_4_4, values.container_4, values.container1_4, values.sccontainer_4,values.container2_4);


		// Refresh
		RefreshBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				for (Control control : values.sc_4.getChildren()) {
					control.dispose();
				}


				for (Control control : values.grp_2_4.getChildren()) {
					control.dispose();
				}


				for (Control control : values.grp_3_4.getChildren()) {
					control.dispose();
				}
				for (Control control : values.grp_4_4.getChildren()) {
					control.dispose();
				}
				for (Control control : values.container1_4.getChildren()) {
					control.dispose();
				}
				createp4(createNodes(), values.sc_4, values.grp_2_4, values.grp_3_4, values.grp_4_4, values.container_4, values.container1_4, values.sccontainer_4,values.container2_4);
			}
		});
		
	}
	
	String[][] createNodes(){
		int treeSize = values.gettotNodes();
		String[][] nodes = new String[treeSize][19];


		for (int ii = 0; ii < values.gettotCl(); ii++) {
			int ll = ii + 1;
			for (int kk = Integer.parseInt(values.map.get("cl" + ll)[0].toString()); kk <= Integer.parseInt(values.map.get("cl"	+ ll)[1].toString()); kk++) {


				// Gia na mhn mpoyn ta Start - End Cluster
				for (int oo = 0; oo < 19; oo++) {
					nodes[kk][oo] = values.map.get("cl" + ll)[oo + 4];
				}
			}
		}
		return nodes;
	}
	
	void createp4(String[][] nodes, ScrolledComposite sc, Group grp_2, final Group grp_3, Group grp_4, final Composite container, final Composite container1, ScrolledComposite sccontainer,final Composite container2){
		wizPage4 ddd = new wizPage4(values);


		values.settotNodes4(values.gettotNodes());


		//Dhmiourgia toy dentroy poou mpainei aristera arxika apo toys komboys pou erxotai apo to page3.
		//Meta ua 3anaginei, eite me addNodes, eite me edite nodes, eite me delete nodes
		values.tree_4 = new Tree(sc, SWT.SINGLE);
		
		for (int yy = 0; yy < values.gettotNodes(); yy++) { //Dhmiourgei thn ptyssomenh lista (dentro)
			
			TreeItem nodeItem = new TreeItem(values.tree_4, SWT.NONE);
			TreeItem procItem = new TreeItem(nodeItem, SWT.NONE);
			
			TreeItem kernelItem = new TreeItem(procItem, SWT.NONE);
			TreeItem kernelItemV = new TreeItem(kernelItem, SWT.NONE);
			
			TreeItem diskImageItem = new TreeItem(procItem, SWT.NONE);
			TreeItem diskImageItemV = new TreeItem(diskImageItem, SWT.NONE);


			TreeItem memSizeItem = new TreeItem(procItem, SWT.NONE);
			TreeItem memSizeItemV = new TreeItem(memSizeItem, SWT.NONE);


			TreeItem SynchTimeItem = new TreeItem(procItem, SWT.NONE);
			TreeItem SynchTimeItemV = new TreeItem(SynchTimeItem, SWT.NONE);


			TreeItem SynchTimeUItem = new TreeItem(procItem, SWT.NONE);
			TreeItem SynchTimeUItemV = new TreeItem(SynchTimeUItem, SWT.NONE);


			TreeItem RxpacketTimeItem = new TreeItem(procItem, SWT.NONE);
			TreeItem RxpacketTimeItemV = new TreeItem(RxpacketTimeItem,
					SWT.NONE);


			TreeItem RxpacketTimeUItem = new TreeItem(procItem, SWT.NONE);
			TreeItem RxpacketTimeUItemV = new TreeItem(RxpacketTimeUItem,SWT.NONE);


			nodeItem.setText("node" + yy);
			procItem.setText(nodes[yy][0]);


			kernelItem.setText("kernel");
			kernelItemV.setText(nodes[yy][1]);


			diskImageItem.setText("disk-image");
			diskImageItemV.setText(nodes[yy][2]);


			memSizeItem.setText("mem-size");
			memSizeItemV.setText(nodes[yy][3]);


			SynchTimeItem.setText("SynchTime");
			SynchTimeItemV.setText(nodes[yy][4]);


			SynchTimeUItem.setText("SynchTimeUnit");
			SynchTimeUItemV.setText(nodes[yy][5]);


			RxpacketTimeItem.setText("RxPacketTime");
			RxpacketTimeItemV.setText(nodes[yy][6]);


			RxpacketTimeUItem.setText("RxPacketTimeUnit");
			RxpacketTimeUItemV.setText(nodes[yy][7]);


			if (nodes[yy][8] != null && !nodes[yy][8].equals("null")) { //einai ARM kai mpainei to machine type
				TreeItem machineTypeItem = new TreeItem(procItem, SWT.NONE);
				TreeItem machineTypeItemV = new TreeItem(machineTypeItem,
						SWT.NONE);
				machineTypeItem.setText("ConfigPath");
				machineTypeItemV.setText(nodes[yy][8]);
			}


			if (nodes[yy][9] != null && !nodes[yy][9].equals("null")) {//einai ARM kai mpainei to dtb
				TreeItem dtbItem = new TreeItem(procItem, SWT.NONE);
				TreeItem dtbItemV = new TreeItem(dtbItem, SWT.NONE);
				dtbItem.setText("dtb");
				dtbItemV.setText(nodes[yy][9]);
			}
			
			if (nodes[yy][10] != null && !nodes[yy][10].equals("null")) { //exei benchmark --> 8a ginei script
				TreeItem benchItem = new TreeItem(procItem, SWT.NONE);
				TreeItem benchItemV = new TreeItem(benchItem, SWT.NONE);
				benchItem.setText("script");
				benchItemV.setText(nodes[yy][10].concat(Integer.toString(yy)));
			}
			if (nodes[yy][11] != null && !nodes[yy][11].equals("null")) { //Exei remote
				TreeItem IPItem = new TreeItem(procItem, SWT.NONE);
				TreeItem IPItemV = new TreeItem(IPItem, SWT.NONE);
				IPItem.setText("IP");
				IPItemV.setText(nodes[yy][11]);
			}
			if (nodes[yy][12] != null&& !nodes[yy][12].equals("null")) {
				TreeItem userItem = new TreeItem(procItem, SWT.NONE);
				TreeItem userItemV = new TreeItem(userItem, SWT.NONE);
				userItem.setText("username");
				userItemV.setText(nodes[yy][12]);
			}
			if (nodes[yy][13] != null && !nodes[yy][13].equals("null")) {
				TreeItem passItem = new TreeItem(procItem, SWT.NONE);
				TreeItem passItemV = new TreeItem(passItem, SWT.NONE);
				passItem.setText("password");
				passItemV.setText(nodes[yy][13]);
			}
				TreeItem pathItem = new TreeItem(procItem, SWT.NONE);
				TreeItem pathItemV = new TreeItem(pathItem, SWT.NONE);
				pathItem.setText("PATH");
				pathItemV.setText(nodes[yy][14]);
		
			if (!nodes[yy][15].equals("false")) {
				TreeItem etherItem = new TreeItem(procItem, SWT.NONE);
				TreeItem etherItemV = new TreeItem(etherItem, SWT.NONE);
				etherItem.setText("etherdump");
				etherItemV.setText(nodes[yy][14]+"/node"+yy+"/etherdump_file");
			}
			
			if (!nodes[yy][16].equals("false")) {
				TreeItem PowerItem = new TreeItem(procItem, SWT.NONE);
				TreeItem PowerItemV = new TreeItem(PowerItem,SWT.NONE);
				PowerItem.setText("mcpat-xml");
				PowerItemV.setText(nodes[yy][17]);
			}
			
			TreeItem Cores = new TreeItem(procItem, SWT.NONE);
			TreeItem CoresV = new TreeItem(Cores,SWT.NONE);
			Cores.setText("Cores");
			CoresV.setText(nodes[yy][18]);
		}
		final TreeEditor editor = new TreeEditor(values.tree_4);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;


		sc.setContent(values.tree_4);


		sc.layout(true, true);
		sc.setMinSize(values.tree_4.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc.setMinWidth(200);


		//Delete
		int[] delStart = new int[values.gettotNodes()];
		for (int nn = 0; nn < values.gettotNodes(); nn++) {
			delStart[nn] = nn;
		}
		final String[] a = Arrays.toString(delStart).split("[\\[\\]]")[1].split(", ");


		Label lblName1 = new Label(grp_2, SWT.NONE);
		lblName1.setText("Start Node");
		final Combo combo1 = new Combo(grp_2, SWT.DROP_DOWN |SWT.READ_ONLY); //For delete (start)
		combo1.setItems(a); // Mporei na 3ekinhsei apo opoiondhpote node	


		Label lblName2 = new Label(grp_2, SWT.NONE);
		lblName2.setText("End Node");
		final Combo combo2 = new Combo(grp_2, SWT.DROP_DOWN |SWT.READ_ONLY);  //For delete (end)
		combo2.setSize(100, 10);
		combo2.setEnabled(false);
	
		final Button delBtn = new Button(grp_2, SWT.PUSH);
		delBtn.setText("Delete Cluster");
		delBtn.setEnabled(false);


		values.settotNodes4(values.gettotNodes());


		combo1.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {


				combo2.setEnabled(true);
				combo2.setSize(65, 27);
				int b = Integer.parseInt(combo1.getText());
				int comb2Length = values.gettotNodes4() - b;
				String[] comb2V = new String[comb2Length];


				for (int ci = 0; ci < comb2Length; ci++) {
					int ll = ci + b;
					comb2V[ci] = Integer.toString(ll);
				}


				combo2.setItems(comb2V); // Mporei na teleiwsei se >= ths arxhs kai <= toy megistoy twn kombwn


				getWizard().getContainer().updateButtons();


			}


			public void widgetDefaultSelected(SelectionEvent e) {


			}
		});


		combo2.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				newLength1 = values.tree_4.getItemCount();
				newLength = values.gettotNodes4() - (Integer.parseInt(combo2.getText())	- Integer.parseInt(combo1.getText()) + 1); 
				delBtn.setEnabled(true);
			}


			public void widgetDefaultSelected(SelectionEvent e) {


			}
		});


		//ADD cluster or Edit node
		final Button b1;		//Select Add cluster
		final Button b2;		//Select Edit node
		final Label startLbl;    
		final Label numOfNewlLbl;
		final Label ndLbl;
		final Combo startComb;
		final Combo numOfNewComb;
		final Combo ndComb;  //Gia edit node kombos to edit
		
		final Map<String, String> medNodeMap = new LinkedHashMap<String, String>();
		String[] e1;
				
		int[] addStart = new int[values.gettotNodes4() + 1];		//Apo pou mporei na 3ekinaei to add
		for (int nn1 = 0; nn1 <= values.gettotNodes4(); nn1++) {
			addStart[nn1] = nn1;
		}
		final String[] e = Arrays.toString(addStart).split("[\\[\\]]")[1].split(", ");


		//int[] addNum = new int[128 - values.gettotNodes4()];		//Poso mporei na einai to plh8os twn kombwn pou 8a proste8oun
		int[] addNum = new int[1024 - values.gettotNodes4()];		//Poso mporei na einai to plh8os twn kombwn pou 8a proste8oun
		if(addNum.length>0){
			for (int nn1 = 0; nn1 < addNum.length; nn1++) {
				addNum[nn1] = nn1 + 1;
			}
			String[] e2 = Arrays.toString(addNum).split("[\\[\\]]")[1].split(", ");
			e1=e2;


		}else{
			String[] e2 = {"0"};
			e1=e2;
		}
		//Add Cluster
		b1 = new Button(grp_4, SWT.RADIO);
		b1.setText("Add Cluster");
		startLbl = new Label(grp_4, SWT.NONE);
		startLbl.setText("Start Node");
		startComb = new Combo(grp_4, SWT.DROP_DOWN |SWT.READ_ONLY);
		startComb.setItems(e);
		startLbl.setEnabled(false);
		startComb.setEnabled(false);
		
		numOfNewlLbl = new Label(grp_4, SWT.NONE);
		numOfNewlLbl.setText("Number of \n new nodes");
		numOfNewComb = new Combo(grp_4, SWT.DROP_DOWN |SWT.READ_ONLY);
		numOfNewComb.setItems(e1);
		numOfNewComb.setEnabled(false);
		numOfNewlLbl.setEnabled(false);


		//Edit node
		b2 = new Button(grp_4, SWT.RADIO);
		b2.setText("Edit Node");
		ndLbl = new Label(grp_4, SWT.NONE);
		ndLbl.setText("Node to Edit");
		ndComb = new Combo(grp_4, SWT.DROP_DOWN |SWT.READ_ONLY);
		ndComb.setItems(a);
		ndComb.setEnabled(false);
		ndLbl.setEnabled(false);
		
		//Node deletion
		delBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				values.Saved = false;
				values.Saved5 = false;


				int k = 0; // Deikths gia to tree.getItem


				if (newLength != 0) {
					for (int y = Integer.parseInt(combo1.getText()); y <= Integer.parseInt(combo2.getText()); y++) {
						values.tree_4.getItem(y - k).dispose();
						k += 1;
					}


					values.settotNodes4(newLength);
				
					// Rename remaining nodes
					for (int l = 0; l < newLength; l++) {
						values.tree_4.getItem(l).setText("node" + l);
					}
					
					//Create new tree after deletion
					String pp = null;
					for (int l = 0; l < values.tree_4.getItemCount(); l++) {
	 						for (int j = 0; j < values.tree_4.getItem(l).getItemCount(); j++) {
	 							for (int ss = 0; ss < values.tree_4.getItem(l).getItem(j).getItemCount(); ss++) {
	 								if(values.tree_4.getItem(l).getItem(j).getItem(ss).getText().equals("PATH")){
	 									pp = values.tree_4.getItem(l).getItem(j).getItem(ss).getItem(0).getText();
	 								}
	 							}
	 						}


						}
					for (int l = 0; l < values.tree_4.getItemCount(); l++) {
	 						for (int j = 0; j < values.tree_4.getItem(l).getItemCount(); j++) {
	 							for (int ss = 0; ss < values.tree_4.getItem(l).getItem(j).getItemCount(); ss++) {
	 								if(values.tree_4.getItem(l).getItem(j).getItem(ss).getText().equals("scipt")){
	 									values.tree_4.getItem(l).getItem(j).getItem(ss)
	 									.getItem(0).setText(values.benchName+l);
	 								}
	 								if(values.tree_4.getItem(l).getItem(j).getItem(ss).getText().equals("etherdump")){
	 									values.tree_4.getItem(l).getItem(j).getItem(ss)
	 									.getItem(0).setText(pp+"/node"+l+"/etherdump_file");
	 								}
	 							}
	 						}


						}
					// New Start node combo values after delete (combo1)
					int[] newdelStart = new int[values.gettotNodes4()];
					for (int nn = 0; nn < values.gettotNodes4(); nn++) {
						newdelStart[nn] = 0;
					}
					for (int nn = 0; nn < values.gettotNodes4(); nn++) {
						newdelStart[nn] = nn;
					}
					String[] newA = Arrays.toString(newdelStart).split(
							"[\\[\\]]")[1].split(", ");


					combo1.setItems(newA);
					combo2.setEnabled(false);
					ndComb.setItems(newA);
				} else if (newLength == 0) { //Warning for delete
					MessageBox zeroNodes = new MessageBox(container.getShell(),	SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					zeroNodes.setMessage("By deleting " + values.gettotNodes4()	+ " nodes the number of nodes will be 0. Are you sure?");
					zeroNodes.setText("Warning - Complete Deletion");
					int resp = zeroNodes.open();
					if (resp == SWT.YES) {
						for (int y = Integer.parseInt(combo1.getText()); y <= Integer.parseInt(combo2.getText()); y++) {
						// Se ka8e loop pou ginetai dispose to epomeno pairnei thn timh toy prohgoymenou opote prepei panta na gientai
						// dispose sthn idia 8esh. Gi'ayto y-k
							values.tree_4.getItem(y - k).dispose();
							k += 1;
						}
						values.settotNodes4(newLength);
						String[] ss = {" "};


						combo1.setEnabled(false);
						combo2.setEnabled(false);
						delBtn.setEnabled(false);
						ndComb.setEnabled(false);
						ndComb.setItems(ss);
						ndLbl.setEnabled(false);


					}else if (resp == SWT.NO) {
						newLength = newLength1;


						int[] newdelStart = new int[values.gettotNodes4()];
						for (int nn = 0; nn < values.gettotNodes4(); nn++) {
							newdelStart[nn] = 0;
						}
						for (int nn = 0; nn < values.gettotNodes4(); nn++) {
							newdelStart[nn] = nn;
						}
						String[] newA = Arrays.toString(newdelStart).split(
								"[\\[\\]]")[1].split(", ");


						combo1.setItems(newA);
						combo2.setEnabled(false);
					}
				}
				// Start and number of add must be changed also
				int[] addStart = new int[values.gettotNodes4() + 1];
				for (int nn1 = 0; nn1 <= values.gettotNodes4(); nn1++) {
					addStart[nn1] = nn1;
				}
				final String[] en = Arrays.toString(addStart).split("[\\[\\]]")[1]
						.split(", ");
				startComb.setItems(en);


				//int[] addNum = new int[128 - values.gettotNodes4()];
				int[] addNum = new int[1024 - values.gettotNodes4()];
				for (int nn1 = 0; nn1 < addNum.length; nn1++) {
					addNum[nn1] = nn1 + 1;
				}
				final String[] en1 = Arrays.toString(addNum).split("[\\[\\]]")[1]
						.split(", ");
				numOfNewComb.setItems(en1);


				delBtn.setEnabled(false);
			}
		});


		grp_2.layout(true, true);


		// Opws sto page3 me 8 sthles anti gia 9 (-1 apo ka8e grammh)
		final String[] addCl; // Dedomena gia to ADD Cluster
		addCl = new String[23];


		final String[] proc = { "RISC-V", "ARM-64", "x86" };

		
		final String[] kernelRISCV = {"riscv-bootloader-vmlinux-5.10-PCI" };
		final String[] kernelA64 = { "vmlinux.arm64" }; 
		final String[] kernelx86 = { "vmlinux-5.4.49" }; 


		final String[] diskImageRISCV = {"riscv-ubuntu.img" };
		final String[] diskImageA64 = { "ubuntu-18.04-arm64-docker.img" }; 
		final String[] diskImagex86 = { "x86-ubuntu.img" }; 
		
		final String[] memSize = { "2048MB", "4096MB", "8192MB" }; 


		final String[] units = { "ms", "us" };


		final String[] macType64 = { "VExpress_GEM5_V1" }; 

		
		final String[] dtb64 = { "" }; 
		final String[] dtbriscv = { "" };
		
		final String[] dtbriscvc = {"1", "2", "4", "8", "16", "32", "64"};
		final String[] dtb64c = {"1", "2", "4", "8", "16", "32", "64"};		
		final String[] dtb86c = {"1", "2", "4", "8", "16", "32", "64"};		
		
		final String[] powerx86 = {"x86_AtomicSimpleCPU_template.xml"};
		final String[] powerARM = {"ARM_AtomicSimpleCPU_template.xml"};


		final boolean[] addOn = new boolean[24];	
		for(int kk=0;kk<addOn.length;kk++){
			addOn[kk] = false;
		}
		addOn[20] = true; //PATH (Because it constins $GEM5)
		addOn[14] = true; //Benchmrk
		addOn[3] = true;  //Benchmark on
		
		//For Edit check if Apply On
		final boolean[] addOnE = new boolean[24];	
		for(int kk=0;kk<addOnE.length;kk++){
			addOnE[kk] = false;
		}
		//For Proc.


		/*
		 * Gia to addOn (21 theseis) (gia na energopoih8ei to button add cluster)
		 * 0 β†’ Start 					  
		 * 1 β†’ Number 					  
		 * 2 β†’ remote 					 
		 * 3 β†’ benchmark on (--script)			 
		 * 4 β†’ Proc 					 
		 * 5 β†’ kernel 					  
		 * 6 β†’ disk-image 				  
		 * 7 β†’ mem-size 				
		 * 8 β†’ SyncTime 				  
		 * 9 β†’ SyncTimetime unit 		  
		 * 10 β†’ PacketTime 				  
		 * 11 β†’ PacketTimetime unit 	  
		 * 12 β†’ ConfigPath 			  
		 * 13 β†’ dtb 					  
		 * 14 β†’ -b 						
		 * 15 β†’ IP 							 
		 * 16 β†’ username 					  
		 * 17 β†’ password 					 
		 * 18 β†’ Arm
		 * 19 β†’ x86
		 * 20 β†’ Path
		 * 21 PowerOn
		 * 22 mcpat-xml
		 * 23 number of cores
		 */
		/* Gia to map (Clusters) (23 theseis ) (OLD -- EXEI ALLA3EI TO ETHERDUMP KAI H ARIMISH TWN PARAKATW)
		0  β†’ cluster Start			(OK)			(int)
		1  β†’ cluster End				(OK)			(int)
		2  β†’ remote					(OK)			(boolean)
		3  β†’ script					(OK)           	(boolean)
		========================================================
		4  β†’ Proc						(OK)			(String)
		5  β†’ kernel					(OK)			(String)				
		6  β†’ disk-image				(OK)			(String)
		7  β†’ mem-size					(OK)			(int 2048 - 8192)
		8  β†’ SyncTime					(OK)			(int)
		9  β†’ SyncTime time unit		(OK)			(String)
		10  β†’ PacketTime 				(OK)			(int)				
		11 β†’ PacketTime time unit		(OK)			(String)
		12 β†’ ConfigPath 				(OK)			(String)			
		13 β†’ dtb						(OK)			(String)
		14 β†’ -b 						(OK)			(String)
		15 β†’ IP										(String)
		16 β†’ username									(String)
		17 β†’ password									(String)
		18 β†’ path (gia to cd kai to etherdump)		(String)
		19 β†’ Etherdump 								(boolean)
		20 β†’ power	 					            (boolean)
		21 β†’ powerValue 						        (boolean)
		22 β†’ number of Cores						    (int)
		*/
		
	
		
		final Label procLbl;
		final Label KernelLbl;
		final Label diskImLbl;
		final Label configLbl;			
		final Label pathLbl;
		final Label memSizeLbl;
		final Label RxPcktLbl;
		final Label dtbLbl;
		final Label dtbLblV;

		final Label IPLbl;
		final Label usrLbl;
		final Label passLbl;
		final Combo procComb;
		final Combo KernelComb;
		final Combo diskImComb;
		final Combo configComb;			
		final Combo memSizeComb;
		final Combo dtdCombo;
		final Button bench;
		final Button remote;
		final Button etherdump;		
		final Label StLbl;
		final Button Stbtn;
		final Text RxPcktTextT;
		final Combo RxPcktCombU;
		final Text IPText;
		final Text userText;
		final Text passText;
		final Text pathText;			
		final Button addBtn;
		final Button powerBtn;
		final Combo powerCmb;
		final Label empty1;
		
		
		procLbl = new Label(grp_3, SWT.NONE);
		procLbl.setText("Processor");
		procComb = new Combo(grp_3, SWT.DROP_DOWN |SWT.READ_ONLY);
		procComb.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		procComb.setItems(proc);
		


		pathLbl = new Label(grp_3, SWT.NONE);
		pathLbl.setText("PATH");
		
		pathText = new Text(grp_3, SWT.BORDER);
		pathText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false, 2, 1));
		pathText.setText(/*"/home/cossim/COSSIM/gem5"*/"$GEM5");
		addCl[18] = "/home/cossim/COSSIM/gem5";
		
		addCl[19] = "false";
		etherdump = new Button(grp_3, SWT.CHECK);
		etherdump.setText("Etherdump");


		addCl[2] = "false";
		remote = new Button(grp_3, SWT.CHECK);
		remote.setText("Remote");
		


		addCl[3] = "false";
		bench = new Button(grp_3, SWT.CHECK);
		bench.setText("Script");
		
		// Kernel
		KernelLbl = new Label(grp_3, SWT.NONE);
		KernelLbl.setText("kernel");
		KernelLbl.setEnabled(false);
		KernelComb = new Combo(grp_3, SWT.DROP_DOWN |SWT.READ_ONLY);
		KernelComb.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false, 3, 1));
		KernelComb.setEnabled(false);
		
		//disk-image
		diskImLbl = new Label(grp_3, SWT.NONE);
		diskImLbl.setText("disk-image");
		diskImLbl.setEnabled(false);
		diskImComb = new Combo(grp_3, SWT.DROP_DOWN |SWT.READ_ONLY);
		diskImComb.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false, 3, 1));
		diskImComb.setEnabled(false);
		
		//mem-size
		memSizeLbl = new Label(grp_3, SWT.NONE);
		memSizeLbl.setText("mem-size");
		memSizeLbl.setEnabled(false);
		memSizeComb = new Combo(grp_3, SWT.DROP_DOWN |SWT.READ_ONLY);
		memSizeComb.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false, 1, 1));
		memSizeComb.setItems(memSize);
		memSizeComb.setEnabled(false);
			
		// RxPacketTime
		RxPcktLbl = new Label(grp_3, SWT.NONE);
		RxPcktLbl.setText("RxPacketTime");
		RxPcktLbl.setEnabled(false);
		RxPcktTextT = new Text(grp_3, SWT.BORDER);
		RxPcktTextT.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false, 1, 1));
		RxPcktTextT.setEnabled(false);
		RxPcktCombU = new Combo(grp_3, SWT.DROP_DOWN |SWT.READ_ONLY);
		RxPcktCombU.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false, 1, 1));
		RxPcktCombU.setItems(units);
		RxPcktCombU.setEnabled(false);
		// SynchTime
		Stbtn = new Button(grp_3, SWT.PUSH);
		Stbtn.setText("SyncTime");
		StLbl = new Label(grp_3, SWT.NONE);
		StLbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Stbtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
					StLbl.setText("Synch Time: "+values.SynchTime+values.SynchTimeUnit);
			}
		});
		
		
		// MachineType and dtb if not x86
		configLbl = new Label(grp_3, SWT.NONE);
		configLbl.setText("ConfigPath");
		configLbl.setEnabled(false);
		configComb = new Combo(grp_3, SWT.DROP_DOWN |SWT.READ_ONLY);
		configComb.setLayoutData(new GridData(SWT.FILL,
				SWT.BEGINNING, true, false, 2, 1));
		configComb.setEnabled(false);


		dtbLbl = new Label(grp_3, SWT.NONE);
		dtbLbl.setLayoutData(new GridData(SWT.RIGHT,SWT.BEGINNING, true, false, 1, 1));
		dtbLbl.setText("Cores");
		dtbLbl.setEnabled(false);
		
		dtdCombo = new Combo(grp_3, SWT.DROP_DOWN |SWT.READ_ONLY);
		dtdCombo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		dtdCombo.setEnabled(false);
		
		dtbLblV = new Label(grp_3, SWT.NONE);
		dtbLblV.setLayoutData(new GridData(SWT.FILL,SWT.BEGINNING, true, false, 3, 1));
		dtbLblV.setEnabled(false);
	
		//IP
		IPLbl = new Label(grp_3, SWT.NONE);
		IPLbl.setText("IP");
		IPLbl.setEnabled(false);
		IPText = new Text(grp_3,SWT.BORDER);
		IPText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false, 1, 1));
		IPText.setEnabled(false);
		
		//UserName
		usrLbl = new Label(grp_3, SWT.NONE);
		usrLbl.setText("Username");
		usrLbl.setEnabled(false);
		userText = new Text(grp_3,SWT.BORDER);
		userText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false, 2, 1));
		userText.setEnabled(false);
		
		//Password
		passLbl = new Label(grp_3, SWT.NONE);
		passLbl.setText("Password");
		passLbl.setEnabled(false);
		passText = new Text(grp_3, SWT.BORDER | SWT.PASSWORD);
		passText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false, 2, 1));
		passText.setEnabled(false);
		
		addCl[20] = "false";
		powerBtn= new Button(grp_3, SWT.CHECK);
		powerBtn.setText("Power");
		powerBtn.setEnabled(false);
		//TODO power --> FALSE
		//powerBtn.setVisible(false);
		
		powerCmb = new Combo(grp_3, SWT.DROP_DOWN |SWT.READ_ONLY);
		powerCmb.setLayoutData(new GridData(SWT.FILL,SWT.BEGINNING, true, false, 4, 1));
		powerCmb.setEnabled(false);
		//TODO power --> FALSE
		//powerCmb.setVisible(false);
		
		empty1 = new Label(grp_3, SWT.NONE);
		empty1.setLayoutData(new GridData(SWT.FILL,	SWT.BEGINNING, true, false, 3, 1));


		addBtn = new Button(grp_3, SWT.PUSH);
		addBtn.setText("Apply");
		addBtn.setEnabled(false);
		
		//Gia thn epilogh addcluster to start
		startComb.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				addCl[0] = startComb.getText();
				addOn[0]=true; //Start
				add = check_addClEnable(addOn);
				addBtn.setEnabled(add);
			}
			public void widgetDefaultSelected(SelectionEvent e) {


			}
		});
		
		//Number of new nodes
		numOfNewComb.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				addOn[1]=true;
				addCl[1] = numOfNewComb.getText();
				add = check_addClEnable(addOn);
				addBtn.setEnabled(add);
			}


			public void widgetDefaultSelected(SelectionEvent e) {


			}
		});
		
		//////////////////////////////// Start Widgets for Add Cluster//////////////////////////////////////
			etherdump.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){				//gia add cluster
						Button btn = (Button) e.getSource();
						addCl[19] = String.valueOf(btn.getSelection());
					}else if(!values.addOrEdit){		//gia edit cluster
						Button btn = (Button) e.getSource();
							if(btn.getSelection()){ //an epilegei
								if(!medNodeMap.containsKey("etherdump")){
									medNodeMap.put("etherdump", medNodeMap.get("PATH").concat("/"+values.tree_4.getItem(Integer.parseInt(ndComb.getText())).getText()+"/etherdump_file"));
								}
							}else if(!btn.getSelection()){
								if(medNodeMap.containsKey("etherdump")){
									medNodeMap.remove("etherdump");
								}
							}
							addBtn.setEnabled(true);
					}
					
				}


				public void widgetDefaultSelected(SelectionEvent e) {


				}
			});
		
			pathText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					if(values.addOrEdit){
						if(!pathText.getText().equals("")){
							addCl[18] = pathText.getText();
							addOn[20] = true;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
						}else if(pathText.getText().equals("")){
							addOn[20] = false;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
						}
					}else if(!values.addOrEdit){
						//NEW
						if(!pathText.getText().equals("")){
							medNodeMap.put("PATH", pathText.getText());
							addOnE[20] = true;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);


						}else if(pathText.getText().equals("")){
							addOnE[20] = false;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
						}
					}
				}
			});
			
			
			bench.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){
						Button btn = (Button) e.getSource();
						addCl[3] = String.valueOf(btn.getSelection());
						if(btn.getSelection()){
							addOn[3] = true;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
							addCl[14] = values.benchName;
						}else if(!btn.getSelection()){
							addCl[14] = null;
							}
					}else if(!values.addOrEdit){
						Button btn = (Button) e.getSource();
							if(btn.getSelection()){
								addOn[3] = true;
								add = check_addClEnable(addOn);
								addBtn.setEnabled(add);
								if(!medNodeMap.containsKey("script")){
									medNodeMap.put("script", values.benchName+ndComb.getText());
								}
							}else if(!btn.getSelection()){
								if(medNodeMap.containsKey("script")){
									medNodeMap.remove("script");
									addCl[14] = null;
								}
							}
							//addBtn.setEnabled(true);
					}
				}
				public void widgetDefaultSelected(SelectionEvent e) {


				}
			});
		
			remote.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){
						Button btn = (Button) e.getSource();
						addCl[2] = String.valueOf(btn.getSelection());
						if(btn.getSelection()){
							addOn[2]=true;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
							addCl[15] = IPText.getText();
							addCl[16] = userText.getText();
							addCl[17] = passText.getText();
							IPLbl.setEnabled(true);
							IPText.setEnabled(true);
							usrLbl.setEnabled(true);
							userText.setEnabled(true);
							passLbl.setEnabled(true);
							passText.setEnabled(true);
							
							if(!IPText.getText().equals("")){ //Check if they contain something
								addOn[15]=true;
								add = check_addClEnable(addOn);
								addBtn.setEnabled(add);
							}else if(IPText.getText().equals("")){
								addOn[15]=false;
								add = check_addClEnable(addOn);
								addBtn.setEnabled(add);
							}
							if(!userText.getText().equals("")){
								addOn[16]=true;
								add = check_addClEnable(addOn);
								addBtn.setEnabled(add);
							}else if(userText.getText().equals("")){
								addOn[16]=false;
								add = check_addClEnable(addOn);
								addBtn.setEnabled(add);
							}
							if(!passText.getText().equals("")){
								addOn[17]=true;
								add = check_addClEnable(addOn);
								addBtn.setEnabled(add);
							}else if(passText.getText().equals("")){
								addOn[17]=false;
								add = check_addClEnable(addOn);
								addBtn.setEnabled(add);
							}
							
							IPText.addModifyListener(new ModifyListener() {
								public void modifyText(ModifyEvent e) {
									if(!IPText.getText().equals("")){
										addOn[15]=true;
										add = check_addClEnable(addOn);
										addBtn.setEnabled(add);
									}else if(IPText.getText().equals("")){
										addOn[15]=false;
										add = check_addClEnable(addOn);
										addBtn.setEnabled(add);
									}
									addCl[15] = IPText.getText();
								}
							});
							userText.addModifyListener(new ModifyListener() {
								public void modifyText(ModifyEvent e) {
									if(!userText.getText().equals("")){
										addOn[16]=true;
										add = check_addClEnable(addOn);
										addBtn.setEnabled(add);
									}else if(userText.getText().equals("")){
										addOn[16]=false;
										add = check_addClEnable(addOn);
										addBtn.setEnabled(add);
									}
									addCl[16] = userText.getText();
								}
							});
							passText.addModifyListener(new ModifyListener() {
								public void modifyText(ModifyEvent e) {
									if(!passText.getText().equals("")){
										addOn[17]=true;
										add = check_addClEnable(addOn);
										addBtn.setEnabled(add);
									}else if(passText.getText().equals("")){
										addOn[17]=false;
										add = check_addClEnable(addOn);
										addBtn.setEnabled(add);
									}
									addCl[17] = passText.getText();
								}
							});


							
						}else if(!btn.getSelection()){//If not remote
							addOn[2]=false;
							IPLbl.setEnabled(false);
							IPText.setEnabled(false);
							usrLbl.setEnabled(false);
							userText.setEnabled(false);
							passLbl.setEnabled(false);
							passText.setEnabled(false);
							addCl[15]=null;
							addCl[16]=null;
							addCl[17]=null;
							addOn[15]=false;
							addOn[16]=false;
							addOn[17]=false;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);


						}
					}else if(!values.addOrEdit){
						Button btn = (Button) e.getSource();
							if(btn.getSelection()){
								addOnE[2]=true;
								IPText.setEnabled(true);
								IPLbl.setEnabled(true);
								userText.setEnabled(true);
								usrLbl.setEnabled(true);
								passText.setEnabled(true);
								passLbl.setEnabled(true);
								if(medNodeMap.containsKey("IP")){
									IPText.setText(medNodeMap.get("IP"));
								}
								if(!medNodeMap.containsKey("IP")){
									medNodeMap.put("IP", IPText.getText());
								}
								if(medNodeMap.containsKey("username")){
									userText.setText(medNodeMap.get("username"));
								}
								if(!medNodeMap.containsKey("username")){
									medNodeMap.put("username", userText.getText());
								}
								if(medNodeMap.containsKey("password")){
									passText.setText(medNodeMap.get("password"));
								}
								if(!medNodeMap.containsKey("password")){
									medNodeMap.put("password", passText.getText());
								}


								if(!IPText.getText().equals("")){
									addOnE[15]=true;
									add = check_addClEnable(addOn);
									addBtn.setEnabled(add);
								}else if(IPText.getText().equals("")){
									addOnE[15]=false;
									add = check_addClEnable(addOn);
									addBtn.setEnabled(add);
								}
								if(!userText.getText().equals("")){
									addOnE[16]=true;
									add = check_addClEnable(addOn);
									addBtn.setEnabled(add);
								}else if(userText.getText().equals("")){
									addOnE[16]=false;
									add = check_addClEnable(addOn);
									addBtn.setEnabled(add);
								}
								if(!passText.getText().equals("")){
									addOnE[17]=true;
									add = check_addClEnable(addOn);
									addBtn.setEnabled(add);
								}else if(passText.getText().equals("")){
									addOn[17]=false;
									add = check_addClEnable(addOn);
									addBtn.setEnabled(add);
								}
								
								IPText.addModifyListener(new ModifyListener() {
									public void modifyText(ModifyEvent e) {
									medNodeMap.put("IP", IPText.getText());
										if(!IPText.getText().equals("")){
											addOn[15]=true;
											add = check_addClEnable(addOn);
											addBtn.setEnabled(add);
										}else if(IPText.getText().equals("")){
											addOn[15]=false;
											add = check_addClEnable(addOn);
											addBtn.setEnabled(add);
										}
									}
								});
								userText.addModifyListener(new ModifyListener() {
									public void modifyText(ModifyEvent e) {
										medNodeMap.put("username", userText.getText());
										if(!userText.getText().equals("")){
											addOn[16]=true;
											add = check_addClEnable(addOn);
											addBtn.setEnabled(add);
										}else if(userText.getText().equals("")){
											addOn[16]=false;
											add = check_addClEnable(addOn);
											addBtn.setEnabled(add);
										}
									}
								});
								passText.addModifyListener(new ModifyListener() {
									public void modifyText(ModifyEvent e) {
										medNodeMap.put("password", passText.getText());
										if(!passText.getText().equals("")){
											addOn[17]=true;
											add = check_addClEnable(addOn);
											addBtn.setEnabled(add);
										}else if(passText.getText().equals("")){
											addOn[17]=false;
											add = check_addClEnable(addOn);
											addBtn.setEnabled(add);
										}
									}
								});


								///////////////////////////////NEW - END///////////////////////
							}else if(!btn.getSelection()){
								if(medNodeMap.containsKey("IP")){
									medNodeMap.remove("IP");
									IPText.setEnabled(false);
									IPLbl.setEnabled(false);
								}
								if(medNodeMap.containsKey("username")){
									medNodeMap.remove("username");
									userText.setEnabled(false);
									usrLbl.setEnabled(false);
								}
								if(medNodeMap.containsKey("password")){
									medNodeMap.remove("password");
									passText.setEnabled(false);
									passLbl.setEnabled(false);
								}
								addOn[2]=false;
								add = check_addClEnable(addOn);
								addBtn.setEnabled(add);
								IPText.setEnabled(false);
								IPLbl.setEnabled(false);
								userText.setEnabled(false);
								usrLbl.setEnabled(false);
								passText.setEnabled(false);
								passLbl.setEnabled(false);
							}
					}
					


				}


				public void widgetDefaultSelected(SelectionEvent e) {


				}
			});
			
			procComb.addSelectionListener(new SelectionListener() { //select proc
				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){ //if add
						powerBtn.setSelection(false);
						powerCmb.setEnabled(false);
						addOn[20]=true;
						addOn[21]=false;
						addOn[22]=false;
						addOn[4]=true;
						addOn[5]=false;
						addOn[6]=false;
						addOn[12]=false;
						addOn[13]=false;
						add = check_addClEnable(addOn);
						addBtn.setEnabled(add);
						addCl[4] = procComb.getText();
						KernelLbl.setEnabled(true);	
						KernelComb.setEnabled(true);
						diskImLbl.setEnabled(true);	
						diskImComb.setEnabled(true);
						memSizeLbl.setEnabled(true);
						memSizeComb.setEnabled(true);
						RxPcktLbl.setEnabled(true);
						RxPcktTextT.setEnabled(true);
						RxPcktCombU.setEnabled(true);
						powerBtn.setEnabled(true);
						if (addCl[4].equals("x86")) {
							powerCmb.setItems(powerx86);
							addOn[18]=false;
							addOn[19]=true;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
							KernelComb.setItems(kernelx86);
							diskImComb.setItems(diskImagex86);
							configLbl.setEnabled(false);
							configComb.setEnabled(false);
							//if( addCl[12] != null){
								addCl[12]=null;
							//}
							dtbLbl.setEnabled(true);
							dtdCombo.setEnabled(true);
							dtdCombo.setItems(dtb86c);
						//	if( addCl[13] != null){
								addCl[13]=null;
						//	}
							dtbLblV.setEnabled(true);
							dtbLblV.setText("Not Selected");
						} else if (addCl[4].equals("ARM-64")) {
							powerCmb.setItems(powerARM);
							addOn[19]=false;
							addOn[18]=true;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
							KernelComb.setItems(kernelA64);
							diskImComb.setItems(diskImageA64);
							configLbl.setEnabled(true);
							configComb.setEnabled(true);
							configComb.setItems(macType64);
							addCl[12]=null;
							dtbLbl.setEnabled(true);
							dtdCombo.setEnabled(true);
							dtdCombo.setItems(dtb64c);
						//	if( addCl[13] != null){
								addCl[13]=null;
						//	}
							dtbLblV.setEnabled(true);
							dtbLblV.setText("Not Selected");
						}
						else if (addCl[4].equals("RISC-V")) {
							powerCmb.setItems(powerARM);
							addOn[19]=false;
							addOn[18]=true;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
							KernelComb.setItems(kernelRISCV);
							diskImComb.setItems(diskImageRISCV);
							configLbl.setEnabled(true);
							configComb.setEnabled(true);
							configComb.setItems(macType64);
							addCl[12]=null;
							dtbLbl.setEnabled(true);
							dtdCombo.setEnabled(true);
							dtdCombo.setItems(dtbriscvc);
						//	if( addCl[13] != null){
								addCl[13]=null;
						//	}
							dtbLblV.setEnabled(true);
							dtbLblV.setText("Not Selected");
						}
					}else if(!values.addOrEdit){
						if(!procComb.getText().equals(medNodeMap.get("Proc"))){ //If proc change
							for(int kk=0;kk<addOnE.length;kk++){
								addOnE[kk] = false;
							}
							//For Path
							addOnE[20] = true;
							
							medNodeMap.clear();
							medNodeMap.put("PATH", "$GEM5");
							medNodeMap.put("SynchTime", values.SynchTime);
							medNodeMap.put("SynchTimeUnit", values.SynchTimeUnit);
							etherdump.setSelection(false);
							remote.setSelection(false);
							bench.setSelection(false);
							KernelComb.deselectAll();
							diskImComb.deselectAll();
							memSizeComb.deselectAll();
							RxPcktTextT.setText("");
							RxPcktCombU.deselectAll();
							configComb.deselectAll();
							configComb.setEnabled(false);
							dtdCombo.deselectAll();
							dtbLblV.setText("Not Selected");
							IPText.setText("");
							IPText.setEnabled(false);
							userText.setText("");
							userText.setEnabled(false);
							passText.setText("");
							passText.setEnabled(false);
							powerBtn.setSelection(false);
							powerCmb.deselectAll();
							powerCmb.setEnabled(false);
							if(procComb.getText().equals("ARM-64")){
								addOnE[18]=true;
								addOnE[19]=false;
								KernelComb.setItems(kernelA64);
								diskImComb.setItems(diskImageA64);
								
								configLbl.setEnabled(true);
								configComb.setEnabled(true);
								configComb.setItems(macType64);
								
								dtbLbl.setEnabled(true);
								dtdCombo.setEnabled(true);
								dtdCombo.setItems(dtb64c);
								powerBtn.setSelection(false);
								powerCmb.setItems(powerARM);
							}else if(procComb.getText().equals("RISC-V")){
								addOnE[18]=true;
								addOnE[19]=false;
								KernelComb.setItems(kernelRISCV);
								diskImComb.setItems(diskImageRISCV);
								
								configLbl.setEnabled(true);
								configComb.setEnabled(true);
								configComb.setItems(macType64);
								
								dtbLbl.setEnabled(true);
								dtdCombo.setEnabled(true);
								dtdCombo.setItems(dtbriscvc);
								powerBtn.setSelection(false);
								powerCmb.setItems(powerARM);
							}else if(procComb.getText().equals("x86")){
								addOnE[18]=false;
								 addOnE[19]=true;
								KernelComb.setItems(kernelx86);
								diskImComb.setItems(diskImagex86);
								configLbl.setEnabled(false);
								configComb.setEnabled(false);
								dtbLbl.setEnabled(true);
								dtbLbl.setEnabled(true);
								dtdCombo.setEnabled(true);
								dtdCombo.setItems(dtb86c);
								powerBtn.setSelection(false);
								powerCmb.setItems(powerx86);
								medNodeMap.remove("ConfigPath");
								medNodeMap.remove("dtb");


							}


								medNodeMap.put("Proc", procComb.getText());	
							}else{//An den alla3ei o proc!!!!!!!!!!
								if(procComb.getText().equals("ARM-64")){
									KernelComb.setItems(kernelA64);
									KernelComb.select(Arrays.asList(kernelA64).indexOf(medNodeMap.get("kernel")));
									diskImComb.setItems(diskImageA64);
									diskImComb.select(Arrays.asList(diskImageA64).indexOf(medNodeMap.get("disk-image")));
									
									configLbl.setEnabled(true);
									configComb.setEnabled(true);
									configComb.setItems(macType64);
									configComb.select(Arrays.asList(macType64).indexOf(medNodeMap.get("ConfigPath")));
									
									dtbLbl.setEnabled(true);
									dtdCombo.setEnabled(true);
									dtdCombo.setItems(dtb64c);
									dtdCombo.select(Arrays.asList(dtb64).indexOf(medNodeMap.get("dtb")));
									
									powerCmb.setItems(powerARM);
									if(medNodeMap.containsKey("mcpat-xml")){
										powerBtn.setSelection(true);
										powerCmb.select(Arrays.asList(powerARM).indexOf(medNodeMap.get("mcpat-xml")));
									}else{
										powerBtn.setSelection(false);
										powerCmb.setEnabled(false);
									}


								}else if(procComb.getText().equals("RISC-V")){
									KernelComb.setItems(kernelRISCV);
									KernelComb.select(Arrays.asList(kernelRISCV).indexOf(medNodeMap.get("kernel")));
									diskImComb.setItems(diskImageRISCV);
									diskImComb.select(Arrays.asList(diskImageRISCV).indexOf(medNodeMap.get("disk-image")));
									
									configLbl.setEnabled(true);
									configComb.setEnabled(true);
									configComb.setItems(macType64);
									configComb.select(Arrays.asList(macType64).indexOf(medNodeMap.get("ConfigPath")));
									
									dtbLbl.setEnabled(true);
									dtdCombo.setEnabled(true);
									dtdCombo.setItems(dtbriscvc);
									dtdCombo.select(Arrays.asList(dtbriscv).indexOf(medNodeMap.get("dtb")));
									
									powerCmb.setItems(powerARM);
									if(medNodeMap.containsKey("mcpat-xml")){
										powerBtn.setSelection(true);
										powerCmb.select(Arrays.asList(powerARM).indexOf(medNodeMap.get("mcpat-xml")));
									}else{
										powerBtn.setSelection(false);
										powerCmb.setEnabled(false);
									}


								}else if(procComb.getText().equals("x86")){
									KernelComb.setItems(kernelx86);
									KernelComb.select(Arrays.asList(kernelx86).indexOf(medNodeMap.get("kernel")));
									diskImComb.setItems(diskImagex86);
									diskImComb.select(Arrays.asList(diskImagex86).indexOf(medNodeMap.get("disk-image")));
									configLbl.setEnabled(false);
									dtbLbl.setEnabled(true);
									dtdCombo.setEnabled(true);
									dtdCombo.setItems(dtb86c);
									
									powerCmb.setItems(powerx86);
									if(medNodeMap.containsKey("mcpat-xml")){
										powerBtn.setSelection(true);
										powerCmb.select(Arrays.asList(powerx86).indexOf(medNodeMap.get("mcpat-xml")));
									}else{
										powerBtn.setSelection(false);
										powerCmb.setEnabled(false);
									}


								}
							}
							addBtn.setEnabled(true);
					}
				}


				public void widgetDefaultSelected(SelectionEvent e) {


				}
			});
			
			powerBtn.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					Button btn = (Button) e.getSource();
					if(values.addOrEdit){
						addCl[20] = String.valueOf(btn.getSelection());
						if(btn.getSelection()){
							powerCmb.setEnabled(true);
							addOn[21]=true;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
						}else{
							powerCmb.setEnabled(false);
							addOn[21]=false;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
						}
					}else if(!values.addOrEdit){
						if(btn.getSelection()){
							powerCmb.setEnabled(true);
							addOnE[21] = true;
							if( procComb.getText().equals("ARM-64") || procComb.getText().equals("RISC-V")){
								powerCmb.setItems(powerARM);
							}else if(procComb.getText().equals("x86")){
								powerCmb.setItems(powerx86);
							}
						}else{
							addOn[21]=false;
							powerCmb.setEnabled(false);
							medNodeMap.remove("mcpat-xml");
						}
					}
				}
				public void widgetDefaultSelected(SelectionEvent e) {


				}
			});
			


			powerCmb.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){
						addCl[21] = powerCmb.getText();
						addOn[22] = true;
						add = check_addClEnable(addOn);
						addBtn.setEnabled(add);
					}else if(!values.addOrEdit){
						medNodeMap.put("mcpat-xml", powerCmb.getText());
						addBtn.setEnabled(true);
					}
				}
				public void widgetDefaultSelected(SelectionEvent e) {


				}
			});
			
			
			KernelComb.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){
						addOn[8]=true;
						addOn[9]=true;
						addOn[5]=true;
						add = check_addClEnable(addOn);
						addBtn.setEnabled(add);
						addCl[5] = KernelComb.getText();
						addCl[8] = values.SynchTime;
						addCl[9] = values.SynchTimeUnit;		
					}else if(!values.addOrEdit){
						medNodeMap.put("kernel", KernelComb.getText());
						addBtn.setEnabled(true);
					}
					
				}


				public void widgetDefaultSelected(SelectionEvent e) {


				}
			});


			diskImComb.addSelectionListener(new SelectionListener() {


				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){
						addOn[6]=true;
						add = check_addClEnable(addOn);
						addBtn.setEnabled(add);
						addCl[6] = diskImComb.getText();
					}else if(!values.addOrEdit){
						medNodeMap.put("disk-image", diskImComb.getText());
							addBtn.setEnabled(true);
					}
					
				}


				public void widgetDefaultSelected(SelectionEvent e) {


				}
			});
			
			memSizeComb.addSelectionListener(new SelectionListener() {


				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){
						addOn[7]=true;
						add = check_addClEnable(addOn);
						addBtn.setEnabled(add);
						addCl[7] = memSizeComb.getText();
					}else if(!values.addOrEdit){
						medNodeMap.put("mem-size", memSizeComb.getText());
							addBtn.setEnabled(true);
					}
					
				}


				public void widgetDefaultSelected(SelectionEvent e) {


				}
			});
		
			RxPcktTextT.addListener(SWT.Verify, new Listener() {
				@Override
				public void handleEvent(Event e) {
					 String string = e.text;
				     char[] chars = new char[string.length()];	
				     string.getChars(0, chars.length, chars, 0);
				        for (int i = 0; i < chars.length; i++) {
				            if (!('0' <= chars[i] && chars[i] <= '9')) {
				              e.doit = false;
				              return;
				            }
				          }
						}
					});


			RxPcktTextT.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					if(values.addOrEdit){
						if(!RxPcktTextT.getText().equals("")){
							addOn[10]=true;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
						}else if(RxPcktTextT.getText().equals("")){
							addOn[10]=false;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
						}
						addCl[10] = RxPcktTextT.getText();
					}else if(!values.addOrEdit){
						medNodeMap.put("RxPacketTime", RxPcktTextT.getText());
							addBtn.setEnabled(true);
					}
					
				}
			});
			
			RxPcktCombU.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){
						addOn[11]=true;
						add = check_addClEnable(addOn);
						addBtn.setEnabled(add);
						addCl[11] = RxPcktCombU.getText();
					}else if(!values.addOrEdit){
						medNodeMap.put("RxPacketTimeUnit", RxPcktCombU.getText());
							addBtn.setEnabled(true);
					}
					
				}


				public void widgetDefaultSelected(SelectionEvent e) {


				}
			});
			
			configComb.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){
						addOn[12]=true;
						add = check_addClEnable(addOn);
						addBtn.setEnabled(add);
						addCl[12] = configComb.getText();
					}else if(!values.addOrEdit){
						if( procComb.getText().equals("ARM-64") || procComb.getText().equals("RISC-V")){
							medNodeMap.put("ConfigPath", configComb.getText());
							addBtn.setEnabled(true);
						}else if(medNodeMap.containsKey("ConfigPath")){
							medNodeMap.remove("ConfigPath");
						}
					}				
				}


				public void widgetDefaultSelected(SelectionEvent e) {


				}
			});
			
			dtdCombo.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){
						addOn[13] = true;
						addOn[23] = true;
						add = check_addClEnable(addOn);
						addBtn.setEnabled(add);
						addCl[22] = dtdCombo.getText();
						dtbLblV.setText("Number of Cores: "+dtdCombo.getText());
						if(addCl[4].equals("x86")){
							addCl[13] = "null";
						}else if(addCl[4].equals("ARM-64")){
							addCl[13] = dtb64[0];
						}
						else if(addCl[4].equals("RISC-V")){
							addCl[13] = dtbriscv[0];
						}
					}else if(!values.addOrEdit){
						dtbLblV.setText("Number of Cores: "+dtdCombo.getText());
						medNodeMap.put("Cores", dtdCombo.getText());
						
							if(procComb.getText().equals("ARM-64")){
								medNodeMap.put("dtb", dtb64[0]);
							}else if(procComb.getText().equals("RISC-V")){
								medNodeMap.put("dtb", dtbriscv[0]);
							}else if(procComb.getText().equals("x86")){
								if(medNodeMap.containsKey("dtb")){
									medNodeMap.remove("dtb");
								}
							}
						
						addBtn.setEnabled(true);
					}
					
				}


				public void widgetDefaultSelected(SelectionEvent e) {


				}
			});
		
		/////////////////////////// End Widgets for ADD CLUSTER ///////////////////////////
		b1.addSelectionListener(new SelectionAdapter(){ //For edit clusters
			public void widgetSelected(SelectionEvent e){
 				Button btn = (Button) e.getSource();
 				medNodeMap.put("SynchTime", values.SynchTime);
 				medNodeMap.put("SynchTimeUnit", values.SynchTimeUnit);
 				procComb.deselectAll();
				KernelComb.deselectAll();
				diskImComb.deselectAll();
				configComb.deselectAll();
				memSizeComb.deselectAll();
				dtdCombo.deselectAll();
				powerCmb.deselectAll();
				StLbl.setText("Synch Time: "+values.SynchTime+values.SynchTimeUnit);
				RxPcktCombU.deselectAll();
				RxPcktTextT.setText("");
				IPText.setText("");
				userText.setText("");
				passText.setText("");
				powerBtn.setSelection(false);
				bench.setSelection(false);
				remote.setSelection(false);
				etherdump.setSelection(false);
				IPText.setEnabled(false);
				userText.setEnabled(false);
				passText.setEnabled(false);
				ndComb.deselectAll();
				
 				if(btn.getSelection()){


 					/*addOn[1]=false;
 					addOn[0]=false;*/
 					addOnE[1] = true;
 					for(int kk=0;kk<addOn.length;kk++){
 						addOn[kk] = false;
 					}
 					addOn[20] = true; //PATH (Because it contains $GEM5)
 					addOn[14] = true; //Benchmrk
 					addOn[3] = true;  //Benchmark on
 					
 					startComb.deselectAll();
 					numOfNewComb.deselectAll();
 					ndComb.setEnabled(false);
 					ndLbl.setEnabled(false);
 					startLbl.setEnabled(true);
 					startComb.setEnabled(true);
 					numOfNewComb.setEnabled(true);
 					numOfNewlLbl.setEnabled(true);
 					add = check_addClEnable(addOn);
 					addBtn.setEnabled(add);
 					values.addOrEdit = true;
 					
 				}else if(!btn.getSelection()){
 					values.addOrEdit = false;
 					addOnE[1]=false;
 				}


 			}
		});
		
		b2.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				medNodeMap.put("SynchTime", values.SynchTime);
				medNodeMap.put("SynchTimeUnit", values.SynchTimeUnit);
				StLbl.setText("Synch Time: "+values.SynchTime+values.SynchTimeUnit);
				addBtn.setEnabled(false);
 				Button btn = (Button) e.getSource();
 				if(btn.getSelection() && values.tree_4.getItemCount()!=0 ){
 					ndComb.setEnabled(true);
 					ndLbl.setEnabled(true);
 					startLbl.setEnabled(false);
 					startComb.setEnabled(false);
 					numOfNewComb.setEnabled(false);
 					numOfNewlLbl.setEnabled(false);
 					powerBtn.setEnabled(true);
 					values.addOrEdit = false;
 				}else if(!btn.getSelection() || values.tree_4.getItemCount()==0){
 					ndComb.setEnabled(false);
 					ndLbl.setEnabled(false);
 					values.addOrEdit = true;
 				}
 				if(!values.addOrEdit){
 					ndComb.addSelectionListener(new SelectionListener() { 
 						public void widgetSelected(SelectionEvent e) {
 						addBtn.setEnabled(true);
 						medNodeMap.clear();
 						//Baze sto Map ta dedomena toy komboy poy epilegatai
 						for (int j = 0; j < values.tree_4.getItem(Integer.parseInt(ndComb.getText())).getItemCount(); j++) {
 							medNodeMap.put("Proc", values.tree_4.getItem(Integer.parseInt(ndComb.getText())).getItem(j).getText());
 							for (int k = 0; k < values.tree_4.getItem(Integer.parseInt(ndComb.getText())).getItem(j).getItemCount(); k++) {
 								medNodeMap.put(values.tree_4.getItem(Integer.parseInt(ndComb.getText())).getItem(j).getItem(k).getText(), values.tree_4.getItem(Integer.parseInt(ndComb.getText())).getItem(j).getItem(k).getItem(0).getText());
 							}
 						}
 						if(medNodeMap.containsKey("Proc")){
 							procComb.select(Arrays.asList(proc).indexOf(medNodeMap.get("Proc")));
 						}
 						
 						if(!medNodeMap.containsKey("ConfigPath")){
 							configLbl.setEnabled(false);
 							configComb.setEnabled(false);
 						}
 			 						
 						if(!medNodeMap.containsKey("IP")){
 							IPLbl.setEnabled(false);
 							IPText.setEnabled(false);
 						}
 						if(!medNodeMap.containsKey("username")){
 							usrLbl.setEnabled(false);
 							userText.setEnabled(false);
 						}
 						if(!medNodeMap.containsKey("password")){
 							passLbl.setEnabled(false);
 							passText.setEnabled(false);
 						}
 				 						
 						if(medNodeMap.containsKey("ConfigPath")){
 							configLbl.setEnabled(true);
 							configComb.setEnabled(true);
 							if(procComb.getText().equals("ARM-64")){
 								configComb.setItems(macType64);
 								configComb.select(Arrays.asList(macType64).indexOf(medNodeMap.get("ConfigPath")));
 							}else if(procComb.getText().equals("RISC-V")){
 								configComb.setItems(macType64);
 								configComb.select(Arrays.asList(macType64).indexOf(medNodeMap.get("ConfigPath")));
 							}else if(procComb.getText().equals("x86")){
 								configComb.setEnabled(false);
 								configLbl.setEnabled(false);
 							}
 						}


 						if(medNodeMap.containsKey("Cores")){
 							dtbLbl.setEnabled(true);
 							dtbLbl.setText(("Cores"));
 							dtbLblV.setEnabled(true);
 							dtbLblV.setText("Number of Cores: "+medNodeMap.get("Cores"));
 							dtdCombo.setEnabled(true);
 							if(procComb.getText().equals("ARM-64")){
 								dtdCombo.setItems(dtb64c);
 								dtdCombo.select(Arrays.asList(dtb64c).indexOf(medNodeMap.get("Cores")));
 							}else if(procComb.getText().equals("RISC-V")){
 								dtdCombo.setItems(dtbriscvc);
 								dtdCombo.select(Arrays.asList(dtbriscvc).indexOf(medNodeMap.get("Cores")));
 							}else if(procComb.getText().equals("x86")){
 								dtdCombo.setItems(dtb86c);
 								dtdCombo.select(Arrays.asList(dtb86c).indexOf(medNodeMap.get("Cores")));
 							}
 						}
 						
 						if(medNodeMap.containsKey("kernel")){
 							KernelComb.setEnabled(true);
 							KernelLbl.setEnabled(true);
 							if(procComb.getText().equals("ARM-64")){
 								KernelComb.setItems(kernelA64);
 								KernelComb.select(Arrays.asList(kernelA64).indexOf(medNodeMap.get("kernel")));
 							}else if(procComb.getText().equals("RISC-V")){
 								KernelComb.setItems(kernelRISCV);
 								KernelComb.select(Arrays.asList(kernelRISCV).indexOf(medNodeMap.get("kernel")));
 							}else if(procComb.getText().equals("x86")){
 								KernelComb.setItems(kernelx86);
 								KernelComb.select(Arrays.asList(kernelx86).indexOf(medNodeMap.get("kernel")));
 							}
 						}
 						
 						if(medNodeMap.containsKey("disk-image")){
 							diskImComb.setEnabled(true);
 							diskImLbl.setEnabled(true);
 							if(procComb.getText().equals("ARM-64")){
 								diskImComb.setItems(diskImageA64);
 								diskImComb.select(Arrays.asList(diskImageA64).indexOf(medNodeMap.get("disk-image")));
 							}else if(procComb.getText().equals("RISC-V")){
 								diskImComb.setItems(diskImageRISCV);
 								diskImComb.select(Arrays.asList(diskImageRISCV).indexOf(medNodeMap.get("disk-image")));
 							}else if(procComb.getText().equals("x86")){
 								diskImComb.setItems(diskImagex86);
 								diskImComb.select(Arrays.asList(diskImagex86).indexOf(medNodeMap.get("disk-image")));
 							}
 						}
 						
 						
 						if(medNodeMap.containsKey("mem-size")){
 							memSizeLbl.setEnabled(true);
 							memSizeComb.setEnabled(true);
 							memSizeComb.setItems(memSize);
 							memSizeComb.select(Arrays.asList(memSize).indexOf(medNodeMap.get("mem-size")));
 						}
 					
 						if(medNodeMap.containsKey("RxPacketTime")){
 							RxPcktLbl.setEnabled(true);
 							RxPcktTextT.setEnabled(true);
 							RxPcktTextT.setText(medNodeMap.get("RxPacketTime"));;
 						}
 
 						if(medNodeMap.containsKey("RxPacketTimeUnit")){
 							RxPcktCombU.setEnabled(true);
 							RxPcktCombU.setItems(units);
 							RxPcktCombU.select(Arrays.asList(units).indexOf(medNodeMap.get("RxPacketTimeUnit")));
 						}
 					 						
 						if(medNodeMap.containsKey("PATH")){
 							pathLbl.setEnabled(true);
 							pathText.setEnabled(true);
 							pathText.setText(medNodeMap.get("PATH"));;
 						}
 	
 						if(medNodeMap.containsKey("etherdump")){
 							etherdump.setSelection(true);
 						}else if(!medNodeMap.containsKey("etherdump")){
 							etherdump.setSelection(false);
 						}
 						
 						if(medNodeMap.containsKey("script")){
 							bench.setSelection(true);
 						}else if(!medNodeMap.containsKey("script")){
 							bench.setSelection(false);
 						}
 						
 						if(medNodeMap.containsKey("IP")){
 							remote.setSelection(true);
 							IPText.setEnabled(true);
 							IPLbl.setEnabled(true);
 							IPText.setText(medNodeMap.get("IP"));
 							userText.setEnabled(true);
 							usrLbl.setEnabled(true);
 							passText.setEnabled(true);
 							passLbl.setEnabled(true);
 							if(medNodeMap.containsKey("username")){
 								userText.setText(medNodeMap.get("username"));
 							}
 							if(medNodeMap.containsKey("password")){
 								passText.setText(medNodeMap.get("password"));
 							}
 						}else if(!medNodeMap.containsKey("IP")){
 							remote.setSelection(false);
 						}
 						
 						
 						if(medNodeMap.containsKey("mcpat-xml")){
 							powerBtn.setEnabled(true);
 							powerBtn.setSelection(true);
 							powerCmb.setEnabled(true);
 							if(procComb.getText().equals("ARM-64")|| procComb.getText().equals("RISC-V")){
 								powerCmb.setItems(powerARM);
 								powerCmb.select(Arrays.asList(powerARM).indexOf(medNodeMap.get("mcpat-xml")));
 							}else if(procComb.getText().equals("x86")){
 								powerCmb.setItems(powerx86);
 								powerCmb.select(Arrays.asList(powerx86).indexOf(medNodeMap.get("mcpat-xml")));
 							}
 						}else if(!medNodeMap.containsKey("mcpat-xml")){
 							
 							powerBtn.setSelection(false);
 							powerCmb.setEnabled(false);
 						}
 						addBtn.setEnabled(true);
 						}
 						public void widgetDefaultSelected(SelectionEvent e) {


 						}
 					});
 					
 				}
 				
 			}
		});
	
		addBtn.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					values.Saved = false;	
					values.Saved5 = false;
					//New Nodes
					addBtn.setEnabled(false);
					if(values.addOrEdit){
						//Gia Add
						for (int yy = Integer.parseInt(addCl[0]); yy < Integer.parseInt(addCl[0])+Integer.parseInt(addCl[1]); yy++) {
							TreeItem nodeItem = new TreeItem(values.tree_4, SWT.NONE,yy);
							TreeItem procItem = new TreeItem(nodeItem, SWT.NONE);


							TreeItem kernelItem = new TreeItem(procItem, SWT.NONE);
							TreeItem kernelItemV = new TreeItem(kernelItem, SWT.NONE);


							TreeItem diskImageItem = new TreeItem(procItem, SWT.NONE);
							TreeItem diskImageItemV = new TreeItem(diskImageItem, SWT.NONE);


							TreeItem memSizeItem = new TreeItem(procItem, SWT.NONE);
							TreeItem memSizeItemV = new TreeItem(memSizeItem, SWT.NONE);


							TreeItem SynchTimeItem = new TreeItem(procItem, SWT.NONE);
							TreeItem SynchTimeItemV = new TreeItem(SynchTimeItem, SWT.NONE);


							TreeItem SynchTimeUItem = new TreeItem(procItem, SWT.NONE);
							TreeItem SynchTimeUItemV = new TreeItem(SynchTimeUItem, SWT.NONE);


							TreeItem RxpacketTimeItem = new TreeItem(procItem, SWT.NONE);
							TreeItem RxpacketTimeItemV = new TreeItem(RxpacketTimeItem,
									SWT.NONE);


							TreeItem RxpacketTimeUItem = new TreeItem(procItem, SWT.NONE);
							TreeItem RxpacketTimeUItemV = new TreeItem(RxpacketTimeUItem,
									SWT.NONE);


							
							nodeItem.setText("New node" + yy);
							procItem.setText(addCl[4]);


							kernelItem.setText("kernel");
							kernelItemV.setText(addCl[5]);


							diskImageItem.setText("disk-image");
							diskImageItemV.setText(addCl[6]);


							memSizeItem.setText("mem-size");
							memSizeItemV.setText(addCl[7]);


							SynchTimeItem.setText("SynchTime");
							SynchTimeItemV.setText(addCl[8]);


							SynchTimeUItem.setText("SynchTimeUnit");
							SynchTimeUItemV.setText(addCl[9]);


							RxpacketTimeItem.setText("RxPacketTime");
							RxpacketTimeItemV.setText(addCl[10]);


							RxpacketTimeUItem.setText("RxPacketTimeUnit");
							RxpacketTimeUItemV.setText(addCl[11]);


							if (addCl[12] != null) {
								TreeItem machineTypeItem = new TreeItem(procItem, SWT.NONE);
								TreeItem machineTypeItemV = new TreeItem(machineTypeItem,
										SWT.NONE);
								machineTypeItem.setText("ConfigPath");
								machineTypeItemV.setText(addCl[12]);


							}


							if (addCl[13] != null || addCl[13] != "null") {
								TreeItem dtbItem = new TreeItem(procItem, SWT.NONE);
								TreeItem dtbItemV = new TreeItem(dtbItem, SWT.NONE);
								dtbItem.setText("dtb");
								dtbItemV.setText(addCl[13]);
								// dtbItem.setExpanded(true);
							}
							if (addCl[14] != null) {
								TreeItem benchItem = new TreeItem(procItem, SWT.NONE);
								TreeItem benchItemV = new TreeItem(benchItem, SWT.NONE);
								benchItem.setText("script");
								benchItemV.setText(addCl[14]+yy);
								// dtbItem.setExpanded(true);
							}
							if (addCl[15] != null) {
								TreeItem IPItem = new TreeItem(procItem, SWT.NONE);
								TreeItem IPItemV = new TreeItem(IPItem, SWT.NONE);
								IPItem.setText("IP");
								IPItemV.setText(addCl[15]);
								// dtbItem.setExpanded(true);
							}
							if (addCl[16] != null) {
								TreeItem userItem = new TreeItem(procItem, SWT.NONE);
								TreeItem userItemV = new TreeItem(userItem, SWT.NONE);
								userItem.setText("username");
								userItemV.setText(addCl[16]);
							}
							if (addCl[17] != null) {
								TreeItem passItem = new TreeItem(procItem, SWT.NONE);
								TreeItem passItemV = new TreeItem(passItem, SWT.NONE);
								passItem.setText("password");
								passItemV.setText(addCl[17]);
							}
							TreeItem pathItem = new TreeItem(procItem, SWT.NONE);
							TreeItem pathItemV = new TreeItem(pathItem, SWT.NONE);
							pathItem.setText("PATH");
							pathItemV.setText(addCl[18]);
							
						if (!addCl[19].equals("false")) {
							TreeItem etherItem = new TreeItem(procItem, SWT.NONE);
							TreeItem etherItemV = new TreeItem(etherItem, SWT.NONE);
							etherItem.setText("etherdump");
							etherItemV.setText(addCl[18]+"/node"+yy+"/etherdump_file");
						}
						
						if (!addCl[20].equals("false")) {
							TreeItem PowerItem = new TreeItem(procItem, SWT.NONE);
							TreeItem PowerItemV = new TreeItem(PowerItem,SWT.NONE);
							PowerItem.setText("mcpat-xml");
							PowerItemV.setText(addCl[21]);
						}
						
						TreeItem Cores = new TreeItem(procItem, SWT.NONE);
						TreeItem CoresV = new TreeItem(Cores,SWT.NONE);
						Cores.setText("Cores");
						CoresV.setText(addCl[22]);
		
						}
						values.settotNodes4(values.tree_4.getItemCount());
						
						//Rename nodes below new nodes
						for (int l = 0; l < values.tree_4.getItemCount(); l++) {
							values.tree_4.getItem(l).setText("node" + l);


						}
						
						String pp = null;
						for (int l = 0; l < values.tree_4.getItemCount(); l++) {
		 						for (int j = 0; j < values.tree_4.getItem(l).getItemCount(); j++) {
		 							for (int k = 0; k < values.tree_4.getItem(l).getItem(j).getItemCount(); k++) {
		 								if(values.tree_4.getItem(l).getItem(j).getItem(k).getText().equals("PATH")){
		 									pp = values.tree_4.getItem(l).getItem(j).getItem(k).getItem(0).getText();
		 								}
		 							}
		 						}


							}
						
						for (int l = 0; l < values.tree_4.getItemCount(); l++) {
							//Baze sto Map ta dedomena toy komboy poy epilegatai
		 						for (int j = 0; j < values.tree_4.getItem(l).getItemCount(); j++) {
		 							for (int k = 0; k < values.tree_4.getItem(l).getItem(j).getItemCount(); k++) {
		 								if(values.tree_4.getItem(l).getItem(j).getItem(k).getText().equals("script")){
		 									values.tree_4.getItem(l).getItem(j).getItem(k).getItem(0).setText(values.benchName+l);
		 								}
		 								if(values.tree_4.getItem(l).getItem(j).getItem(k).getText().equals("etherdump")){
		 									values.tree_4.getItem(l).getItem(j).getItem(k).getItem(0).setText(pp+"/node"+l+"/etherdump_file");
		 								}
		 							}
		 						}


							}
						
						for (int l = Integer.parseInt(addCl[0]); l < Integer.parseInt(addCl[0])+Integer.parseInt(addCl[1]); l++) {
							for (int j = 0; j < values.tree_4.getItem(l).getItemCount(); j++) {
	 							for (int k = 0; k < values.tree_4.getItem(l).getItem(j).getItemCount(); k++) {
	 								if(values.tree_4.getItem(l).getItem(j).getItem(k).getText().equals("script")){
	 									values.tree_4.getItem(l).getItem(j).getItem(k).getItem(0).setText(values.benchName+l);
	 								}
	 								if(values.tree_4.getItem(l).getItem(j).getItem(k).getText().equals("etherdump")){
	 									values.tree_4.getItem(l).getItem(j).getItem(k).getItem(0).setText(pp+"/node"+l+"/etherdump_file");
	 								}
	 								
	 							}
	 						}


						}
						//New values at Del Start
						int[] delStartnew = new int[values.gettotNodes4()];
						for (int nn = 0; nn < values.gettotNodes4(); nn++) {
							delStartnew[nn] = nn;
						}
						final String[] a = Arrays.toString(delStartnew).split("[\\[\\]]")[1].split(", ");


						combo1.setItems(a); // Mporei na 3ekinhsei apo opoiondhpote node
						combo1.setEnabled(true);
						ndComb.setItems(a);
						//ndComb.setEnabled(true);
						//ndLbl.setEnabled(true);
						// Prepei na alla3ei kai h arxh kai to plh8os tou add
						int[] addStartNew = new int[values.gettotNodes4() + 1];
						for (int nn1 = 0; nn1 <= values.gettotNodes4(); nn1++) {
							addStartNew[nn1] = nn1;
						}
						final String[] enNew = Arrays.toString(addStartNew).split("[\\[\\]]")[1]
								.split(", ");
						startComb.setItems(enNew);


						//int[] addNumNew = new int[128 - values.gettotNodes4()];
						int[] addNumNew = new int[1024 - values.gettotNodes4()];
						String[] en1New = null;
						if(addNumNew.length>0){
							for (int nn1 = 0; nn1 < addNumNew.length; nn1++) {
								addNumNew[nn1] = nn1 + 1;
							}	
							final String[] en2New = Arrays.toString(addNumNew).split("[\\[\\]]")[1].split(", ");
							en1New = en2New;
						}else{
							String[] en2New = {"0"};
							en1New = en2New;
						}
						
						numOfNewComb.setItems(en1New);
						startComb.deselectAll();;
						numOfNewComb.deselectAll();
						ndComb.deselectAll();
						addBtn.setEnabled(false);
					}else if(!values.addOrEdit){
						int add1 = Integer.parseInt(ndComb.getText());
						values.tree_4.getItem(Integer.parseInt(ndComb.getText())).dispose();
						
						TreeItem nodeItem = new TreeItem(values.tree_4, SWT.NONE,add1);
						TreeItem procItem = new TreeItem(nodeItem, SWT.NONE);
						
						TreeItem kernelItem = new TreeItem(procItem, SWT.NONE);
						TreeItem kernelItemV = new TreeItem(kernelItem, SWT.NONE);


						TreeItem diskImageItem = new TreeItem(procItem, SWT.NONE);
						TreeItem diskImageItemV = new TreeItem(diskImageItem, SWT.NONE);


						TreeItem memSizeItem = new TreeItem(procItem, SWT.NONE);
						TreeItem memSizeItemV = new TreeItem(memSizeItem, SWT.NONE);


						TreeItem SynchTimeItem = new TreeItem(procItem, SWT.NONE);
						TreeItem SynchTimeItemV = new TreeItem(SynchTimeItem, SWT.NONE);


						TreeItem SynchTimeUItem = new TreeItem(procItem, SWT.NONE);
						TreeItem SynchTimeUItemV = new TreeItem(SynchTimeUItem, SWT.NONE);


						TreeItem RxpacketTimeItem = new TreeItem(procItem, SWT.NONE);
						TreeItem RxpacketTimeItemV = new TreeItem(RxpacketTimeItem,	SWT.NONE);


						TreeItem RxpacketTimeUItem = new TreeItem(procItem, SWT.NONE);
						TreeItem RxpacketTimeUItemV = new TreeItem(RxpacketTimeUItem,  SWT.NONE);
						
						
						nodeItem.setText("node" + add1);
						procItem.setText(medNodeMap.get("Proc"));


						kernelItem.setText("kernel");
						kernelItemV.setText(medNodeMap.get("kernel"));


						diskImageItem.setText("disk-image");
						diskImageItemV.setText(medNodeMap.get("disk-image"));


						memSizeItem.setText("mem-size");
						memSizeItemV.setText(medNodeMap.get("mem-size"));


						SynchTimeItem.setText("SynchTime");
						SynchTimeItemV.setText(medNodeMap.get("SynchTime"));


						SynchTimeUItem.setText("SynchTimeUnit");
						SynchTimeUItemV.setText(medNodeMap.get("SynchTimeUnit"));


						RxpacketTimeItem.setText("RxPacketTime");
						RxpacketTimeItemV.setText(medNodeMap.get("RxPacketTime"));


						RxpacketTimeUItem.setText("RxPacketTimeUnit");
						RxpacketTimeUItemV.setText(medNodeMap.get("RxPacketTimeUnit"));
						
						if (medNodeMap.containsKey("ConfigPath")) {
							TreeItem machineTypeItem = new TreeItem(procItem, SWT.NONE);
							TreeItem machineTypeItemV = new TreeItem(machineTypeItem,SWT.NONE);
							machineTypeItem.setText("ConfigPath");
							machineTypeItemV.setText(medNodeMap.get("ConfigPath"));


						}
						if (medNodeMap.containsKey("dtb")) {
							TreeItem dtbItem = new TreeItem(procItem, SWT.NONE);
							TreeItem dtbItemV = new TreeItem(dtbItem,SWT.NONE);
							dtbItem.setText("dtb");
							dtbItemV.setText(medNodeMap.get("dtb"));


						}
						if (medNodeMap.containsKey("script")) {
							TreeItem benchItem = new TreeItem(procItem, SWT.NONE);
							TreeItem benchItemV = new TreeItem(benchItem, SWT.NONE);
							benchItem.setText("script");
							benchItemV.setText(medNodeMap.get("script"));
						}
						if (medNodeMap.containsKey("IP")) {
							TreeItem IPItem = new TreeItem(procItem, SWT.NONE);
							TreeItem IPItemV = new TreeItem(IPItem, SWT.NONE);
							IPItem.setText("IP");
							IPItemV.setText(medNodeMap.get("IP"));
							
						}
						if (medNodeMap.containsKey("username")) {
							TreeItem userItem = new TreeItem(procItem, SWT.NONE);
							TreeItem userItemV = new TreeItem(userItem, SWT.NONE);
							userItem.setText("username");
							userItemV.setText(medNodeMap.get("username"));
						}
						if (medNodeMap.containsKey("password")) {
							TreeItem passItem = new TreeItem(procItem, SWT.NONE);
							TreeItem passItemV = new TreeItem(passItem, SWT.NONE);
							passItem.setText("password");
							passItemV.setText(medNodeMap.get("password"));
						}
						
						TreeItem pathItem = new TreeItem(procItem, SWT.NONE);
						TreeItem pathItemV = new TreeItem(pathItem, SWT.NONE);
						pathItem.setText("PATH");
						pathItemV.setText(medNodeMap.get("PATH"));
				
					if (medNodeMap.containsKey("etherdump")) {
						TreeItem etherItem = new TreeItem(procItem, SWT.NONE);
						TreeItem etherItemV = new TreeItem(etherItem, SWT.NONE);
						etherItem.setText("etherdump");
						etherItemV.setText(medNodeMap.get("etherdump"));
					}


					if (medNodeMap.containsKey("mcpat-xml")) {
						TreeItem PowerItem = new TreeItem(procItem, SWT.NONE);
						TreeItem PowerItemV = new TreeItem(PowerItem,SWT.NONE);
						PowerItem.setText("mcpat-xml");
						PowerItemV.setText(medNodeMap.get("mcpat-xml"));
					}
					
					TreeItem Cores = new TreeItem(procItem, SWT.NONE);
					TreeItem CoresV = new TreeItem(Cores,SWT.NONE);
					Cores.setText("Cores");
					CoresV.setText(medNodeMap.get("Cores"));
					startComb.deselectAll();;
					numOfNewComb.deselectAll();
					ndComb.deselectAll();
					}
			
					procComb.deselectAll();
					KernelComb.deselectAll();
					diskImComb.deselectAll();
					configComb.deselectAll();
					memSizeComb.deselectAll();
					dtdCombo.deselectAll();
					powerCmb.deselectAll();
					powerBtn.setSelection(false);
					RxPcktCombU.deselectAll();
					RxPcktTextT.setText("");
					IPText.setText("");
					userText.setText("");
					passText.setText("");
					bench.setSelection(false);
					remote.setSelection(false);
					etherdump.setSelection(false);
				}
			});
		
		
		Button SaveBtn = new Button(container1, SWT.PUSH);
		SaveBtn.setText("Save As");
		
		//SAVE button
		SaveBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
						
				values.conf = values.createConf1(values.tree_4);
				FileDialog fd = new FileDialog(container2.getShell(), SWT.SAVE);
				fd.setText("Save Command File");
				fd.setFilterPath(System.getenv("GEM5"));
				String[] filterExt = { "*.sh", "*.*" };
				fd.setFilterExtensions(filterExt);
				fd.setOverwrite(true);
				String selected = null;
				boolean done = false;


				while(!done){
					selected = fd.open();
					if(selected == null){
						done = true;
					}else{
						String ttt = values.createFileS(values.tree_4);
						String savePath = System.getenv("GEM5");
						File myFile;
						if(fd.getFileName().contains(".")){
							myFile = new File(savePath, /*"Txc.ned"*/fd.getFileName().substring(0, fd.getFileName().lastIndexOf('.', fd.getFileName().lastIndexOf('.')))+".ned");
						}else{
							myFile = new File(savePath, /*"Txc.ned"*/fd.getFileName()+".ned");


						}
						PrintWriter textFileWriter;
						try {
							textFileWriter = new PrintWriter(new FileWriter(myFile));
							textFileWriter.write(ttt);
							textFileWriter.close();
						} catch (IOException e2) {
							e2.printStackTrace();
						}
						try {
							Files.write(Paths.get(selected), values.conf.getBytes());
							values.Saved = true;	
							values.Saved5 = true;
							done = true;
						} catch (IOException e1) {
							
						}
					}
				}}
		});


		Button DoneBtn = new Button(container1, SWT.PUSH);
		DoneBtn.setText("Done");
		
		//DONE Button
		DoneBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				//Save Txc.ned
				String ttt = values.createFileS(values.tree_4);
				String savePath = System.getProperty("user.dir") 
						+ System.getProperty("file.separator") 
						+ "OMNET_WORKSPACE/HLANode/src";
				File myFile = new File(savePath, "Txc.ned");
				PrintWriter textFileWriter;
				try {
					textFileWriter = new PrintWriter(new FileWriter(myFile));
					textFileWriter.write(ttt);
					textFileWriter.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				//Save run.sh
				values.conf = values.createConf1(values.tree_4);
				String runShPath = System.getenv("GEM5");
				File runFile = new File(runShPath, "run.sh");
				PrintWriter textFileWriterSH;
				try {
					textFileWriterSH = new PrintWriter(new FileWriter(runFile));
					textFileWriterSH.write(values.conf);
					textFileWriterSH.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				MessageDialog.openInformation(
						container.getShell(),
						"Auto saved files",
						"Comnfiguration saved in /home/cossim/COSSIM/gem5/run.sh "
						+ "and Txc.ned file saved in "
						+ "/home/cossim/OMNET_WORKSPACE/HLANode/src");
				getWizard().getContainer().getShell().close();
			}
		});
		
		sccontainer.layout(true, true);
		sccontainer.setMinSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
	}
	
	boolean check_addClEnableEDIT(boolean[] val){ //Gia na ginei enable to Add Cluster
		boolean enable = true;
		//TODO FUNCTION
		return enable;
	}
	boolean check_addClEnable(boolean[] val){ //Gia na ginei enable to Add Cluster
		boolean enable = false;


	if(val[0]==true && val[1]==true && val[4]==true && val[5]==true && val[6]==true && val[7]==true && val[8]==true && val[9]==true && val[10]==true && val[11]==true && val[20]==true && val[13] && val[23]){
		if(val[19]==true){ //if(x86)
			if(val[2]==true){ //if(remote)
				if(val[21]==true){ //if(Power)
					if(/*val[14]==true &&*/ val[15]==true && val[16]==true && val[17]==true && val[22]==true){
						enable = true;
						}else{
							enable=false;
						}
							
					}else if(val[21] == false){ //if(not power)
						if(val[15]==true && val[16]==true && val[17]==true){
							enable = true;
						}
				}
			}else if(val[2]==false){//if(not remote)
				if(val[21]==true){ //if(power)
					if(/*val[14]==true*/val[22]==true){
						enable = true;
						}else{
							enable=false;
						}
					}else if(val[21] == false){ //if(not power)
							enable = true;
					}
				//enable = true;
			}
		}else if(val[18]==true){//if(ARM)
			if(val[12]==true/* && val[13]==true*/){ //If (machine type)
				if(val[2]==true){ //if(remote)
					if(val[21]==true){ //if(Power)
						if(/*val[14]==true &&*/ val[15]==true && val[16]==true && val[17]==true && val[22]==true){
							enable = true;
							}else if(val[22] == false){
								enable=false;
							}
						}else if(val[21] == false){ //if(not power)
							if(val[15]==true && val[16]==true && val[17]==true){
								enable = true;
							}
					}
				}else if(val[2]==false){//if(not remote)
					if(val[21]==true){ //if(Power)
						if(/*val[14]==true &&*/ val[22]==true){
							enable = true;
							}else if(val[22] == false){
								enable=false;
							}
						}else if(val[21] == false){ //if(not power)
							
								enable = true;
							
					}
				}
			}
			
		}
	}
	
		return enable;
	}
	


	public boolean canFlipToNextPage(){
		return true;
	}


}