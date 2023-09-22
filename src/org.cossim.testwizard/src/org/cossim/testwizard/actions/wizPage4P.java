package org.cossim.testwizard.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.IWizardPage.*;
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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class wizPage4P extends WizardPage {
	
	private wizVal values;
	private int newLength;
	private int newLength1;
	final String[] units = { "ms", "us" };
	String SynchTime;		//Gia to parse
	String SynchTimeUnit;	//Gia to parse


	boolean add = false;
	public wizPage4P(wizVal values) {
		super("CreateFile");
		
		setTitle("Configuration of Parsed Nodes");
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
		//values.grp_4_4.setLayout(new GridLayout(2, true));
		values.grp_4_4.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true,2, 1)); // Alignments, spaces etc
		values.grp_4_4.setLayout(new GridLayout(5, true));

				
		values.grp_3_4 = new Group(values.container2_4, SWT.NONE);  //Details gia Add Cluster kai edit node
		values.grp_3_4.setText("Node Details");
		values.grp_3_4.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true,3, 1)); // Alignments, spaces etc
		values.grp_3_4.setLayout(new GridLayout(8, false));
		values.grp_3_4.setEnabled(true);

		String path1 = values.confFromFile;
		
			try {
				createp4P(node(getLines(values.confFromFile)), values.sc_4, values.grp_2_4, values.grp_3_4, values.grp_4_4, values.container_4, values.container1_4, values.sccontainer_4,values.container2_4);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
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
					try {
						createp4P(node(getLines(values.confFromFile)), values.sc_4, values.grp_2_4, values.grp_3_4, values.grp_4_4, values.container_4, values.container1_4, values.sccontainer_4,values.container2_4);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				

			}

		});
		
	}
	
	boolean correctF(String lines[]){
		boolean corFile = false;
		//System.out.println(corFile);
		String[] lineTokens = lines[0].split(" ");
		if(lineTokens.length > 1 ){
			for(int p=0;p<lineTokens.length;p++){
				String[] eqTokens = lineTokens[p].split("=");
				if(eqTokens[0].equals("--kernel")){ //TODO put more attributes
					corFile = true;
				}
			}
		}
		//System.out.println(corFile);
		return corFile;
	}
	
	String[] getLines(String fileConf) throws IOException { //Briskei tis grammes. Afairei tis kenes kai ta cd(Ta cd efygan etsi ki alliws)
		String lines[] = fileConf.split("\\r?\\n");
		String lines1[] = fileConf.split("[\\r\\n]+"); // Remove empty lines

		return lines1;
	}
	
	int getNd(String fileConf) throws IOException { //Briskei tis grammes, afairei kenes kai cd. To plh8os toye einai to plh8os twn kombwn

		String[] temp = fileConf.trim().split(" ");
			
		String lines1[] = fileConf.split("[\\r\\n]+"); // Remove empty lines
		int tND = lines1.length;
	
		return tND;
	}
	String[] findPath(String fileConf, String[] lines) throws IOException{		// gia na brei to PATH (Mporei na mh exei remote kai sto getLines afairei ta cd ths arxhs
		String[] paths = new String[lines.length];
		String path = null;
		String lines1[] = fileConf.split("[\\r\\n]+"); // Remove empty lines
		int k = 0;
		/*correctF(lines);*/
		for(int j=0;j<lines1.length;j++){
			if(lines1[j].trim().split(" ")[0].trim().equals("sshpass")){	//REMOTE
				String[] nohup = lines1[j].trim().split("nohup");		//If Remote, path amesws meta to nohup
				path = nohup[1].trim().split("/")[0];					//Path prin to /
			}else{
				path = lines1[j].trim().split("/")[0];		//Path prin to /
			}
		}
		
		for(int kk=0;kk<paths.length;kk++){
			paths[kk] = path;
		}
		
		return paths;
	}
	
boolean[] findRemote(String[] lines){ //Epistrefei poioi komboi (grammes) einai Remote
		
		boolean[] rem = new boolean[lines.length]; //true if remote
		
		for(int i=0;i<lines.length;i++){
			if(lines[i].split(" ")[0].equals("sshpass")){
				rem[i]=true;
			}else rem[i]=false;
		}
		return rem;
	}
	
	
	String[] Attrs(String line){ //Kaleitai gia ka8e grammh kai epistrefei ta values gia ka8e attribute
		String[] parsed = new String[18];
		/*
		 parsed[0] = proc
		 parsed[1] = kernel
		 parsed[2] = disk-image
		 parsed[3] = mem-size
		 parsed[4] = SynchTime
		 parsed[5] = SynchTime Unit
		 parsed[6] = RxPacketTime
		 parsed[7] = RxPacketTime Unit
		 parsed[8] = ConfigPath
		 parsed[9] = dtb
		 parsed[10] = script
		 parsed[11] = IP
		 parsed[12] = username
		 parsed[13] = password
		 parsed[14] = path
		 parsed[15] = etherdump (true or false (String))
		 //New
		 parsed[16] = power
		 parsed[17] = Cores
		  */
		parsed[15] = "false";
		parsed[17] = "1";
		String[] spaced = line.split(" ");
		
		String[][] attrs = new String[spaced.length][2];
		for(int j=0;j<spaced.length;j++){
			attrs[j] = spaced[j].split("="); //Diaxwrismos kenwn kai = (an yparxei) Epistrefei ena ;h dyo stroixeia gia ka8e grammh analoga an exei = 'h oxi
		}
		
		Pattern pattern = Pattern.compile("-n\\b");
		for(int j=0;j<spaced.length;j++){			//Find Cores
			
			Matcher matcher = pattern.matcher(attrs[j][0].trim());
			if(j<spaced.length-1 && matcher.find()/*attrs[j][0].trim().equals("-n")*/){
				parsed[17] = attrs[j+1][0];  // Briskei to Cores
				
			}
		}
		
		for(int j=0;j<spaced.length;j++){			//Find kernel and proc
			if(attrs[j][0].trim().equals("--kernel")){
				parsed[1] = attrs[j][1];
				if(attrs[j][1].equals("vmlinux.arm64")){ 
					parsed[0]="ARM-64";
				}else if(attrs[j][1].equals("riscv-bootloader-vmlinux-5.10-PCI")){ 
					parsed[0]="RISC-V";
				}else if(attrs[j][1].equals("vmlinux-5.4.49")){ 
					parsed[0]="x86";
				}
			}
		}
		
		for(int j=0;j<spaced.length;j++){			//Find script
			if(attrs[j][0].trim().equals("--script")){
				parsed[10] = attrs[j][1].trim().split("\\.")[0];  // Briskei to script
			}
		}
		
			
		for(int j=0;j<spaced.length;j++){			//Find disk-image
			if(attrs[j][0].trim().equals("--mcpat-xml")){
				parsed[16] = attrs[j][1];				
			}
		}
		
		for(int j=0;j<spaced.length;j++){			//Find disk-image
			if(attrs[j][0].trim().equals("--disk-image")){
				parsed[2] = attrs[j][1];				
			}
		}
		
		for(int j=0;j<spaced.length;j++){			//Find dtb
			if(attrs[j][0].trim().equals("--dtb")){
				parsed[9] = attrs[j][1];				
			}
		}
		
		for(int j=0;j<spaced.length;j++){			//Find --ConfigPath
			if(attrs[j][0].trim().equals("--ConfigPath")){
				parsed[8] = attrs[j][1];				
			}
		}
		
		for(int j=0;j<spaced.length;j++){			//Find --mem-size
			if(attrs[j][0].trim().equals("--mem-size")){
				parsed[3] = attrs[j][1]/*.replaceAll("\\D+","")*/;				
			}
		}
		
		for(int j=0;j<spaced.length;j++){			//Find --etherdump
			if(attrs[j][0].trim().equals("--etherdump")){
				parsed[15] = "true";
			}
		}
		
		for(int j=0;j<spaced.length;j++){			//Find --SynchTime
			if(attrs[j][0].trim().equals("--SynchTime")){
				
				String[] st1 = attrs[j][1].split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");	
				parsed[4]=st1[0];
				parsed[5]=st1[1];
			}
		}
		SynchTime = parsed[4];
		SynchTimeUnit = parsed[5];
		for(int j=0;j<spaced.length;j++){			//Find --RxPacketTime
			if(attrs[j][0].trim().equals("--RxPacketTime")){
				
				String[] st1 = attrs[j][1].split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");	
				parsed[6]=st1[0];
				parsed[7]=st1[1];
		
			}
		}
		
		return parsed;
	}
	
	String[] remAttrs(String line){
		
		String[] rem = new String[3];
		
		String[] cc = line.trim().split("'"); 
		String[] usr = cc[2].trim().split(" ")[1].trim().split("@");
		String pass = cc[1].trim(); //password
		String user = usr[0];		//username
		String IP = usr[1];			//IP
				
		rem[0] = usr[1];
		rem[1] = usr[0];
		rem[2] = cc[1].trim();
				
		return rem;
	}
	
	String[][] node(String[] lines) throws IOException{
		String[][] nodes = new String[lines.length][18];

		for(int j=0;j<lines.length;j++){
			for(int k=0; k<18; k++){
				nodes[j][k] = Attrs(getLines(values.confFromFile)[j])[k];
			}
			
			if(findRemote(getLines(values.confFromFile))[j]){		//An einai remote
				nodes[j][11] = remAttrs(getLines(values.confFromFile)[j])[0];
				nodes[j][12] = remAttrs(getLines(values.confFromFile)[j])[1];
				nodes[j][13] = remAttrs(getLines(values.confFromFile)[j])[2];
			}
					
			nodes[j][14] = findPath(values.confFromFile, getLines(values.confFromFile))[j];
			
		}
		
		return nodes;
		
	}
		
	void createp4P(String[][] nodes, ScrolledComposite sc, Group grp_2, final Group grp_3, Group grp_4, final Composite container, final Composite container1, ScrolledComposite sccontainer,final Composite container2) throws IOException{

		try {
			values.settotNodes4(getNd(values.confFromFile));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		values.tree_4 = new Tree(sc, SWT.SINGLE);
		
		for (int yy = 0; yy < values.gettotNodes4(); yy++) {  //Ftiaxnei to arxiko dentro
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
			TreeItem RxpacketTimeUItemV = new TreeItem(RxpacketTimeUItem,
					SWT.NONE);

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

			if (nodes[yy][8] != null && !nodes[yy][8].equals("null")) {
				TreeItem machineTypeItem = new TreeItem(procItem, SWT.NONE);
				TreeItem machineTypeItemV = new TreeItem(machineTypeItem,
						SWT.NONE);
				machineTypeItem.setText("ConfigPath");
				machineTypeItemV.setText(nodes[yy][8]);
			}

			if (nodes[yy][9] != null && !nodes[yy][9].equals("null")) {
				TreeItem dtbItem = new TreeItem(procItem, SWT.NONE);
				TreeItem dtbItemV = new TreeItem(dtbItem, SWT.NONE);
				dtbItem.setText("dtb");
				dtbItemV.setText(nodes[yy][9]);
			}
			
			if (nodes[yy][10] != null && !nodes[yy][10].equals("null")) {
				TreeItem benchItem = new TreeItem(procItem, SWT.NONE);
				TreeItem benchItemV = new TreeItem(benchItem, SWT.NONE);
				benchItem.setText("script");
				benchItemV.setText(nodes[yy][10]);
			}
			if (nodes[yy][11] != null && !nodes[yy][11].equals("null")) {
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
			if (nodes[yy][16] != null && !nodes[yy][16].equals("null")) {
				TreeItem PowerItem = new TreeItem(procItem, SWT.NONE);
				TreeItem PowerItemV = new TreeItem(PowerItem,SWT.NONE);
				PowerItem.setText("mcpat-xml");
				PowerItemV.setText(nodes[yy][16]);
			}
			TreeItem Cores = new TreeItem(procItem, SWT.NONE);
			TreeItem CoresV = new TreeItem(Cores,SWT.NONE);
			Cores.setText("Cores");
			CoresV.setText(nodes[yy][17]);
		}
		final TreeEditor editor = new TreeEditor(values.tree_4);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;

		sc.setContent(values.tree_4);

		sc.layout(true, true);
		sc.setMinSize(values.tree_4.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc.setMinWidth(200);


		// Delete Cluster
		int[] delStart = new int[getNd(values.confFromFile)];
		for (int nn = 0; nn < getNd(values.confFromFile); nn++) {
			delStart[nn] = nn;
		}
		final String[] a = Arrays.toString(delStart).split("[\\[\\]]")[1]
				.split(", ");

		Label lblName1 = new Label(grp_2, SWT.NONE);
		lblName1.setText("Start Node");
		final Combo combo1 = new Combo(grp_2, SWT.DROP_DOWN |SWT.READ_ONLY);
		combo1.setItems(a); // Mporei na 3ekinhsei apo opoiondhpote node

		Label lblName2 = new Label(grp_2, SWT.NONE);
		lblName2.setText("End Node");
		final Combo combo2 = new Combo(grp_2, SWT.DROP_DOWN |SWT.READ_ONLY);
		combo2.setSize(100, 10);
		combo2.setEnabled(false);
		
		final Button delBtn = new Button(grp_2, SWT.PUSH);
		delBtn.setText("Delete Cluster");
		delBtn.setEnabled(false);

		values.settotNodes4(getNd(values.confFromFile));
		
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

				combo2.setItems(comb2V); // Mporei na teleiwsei se >= ths arxhs
											// kai <= toy megistoy twn kombwn
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


		final Button b1;		//Select Add cluster
		final Button b2;		//Select Edit node
		final Button b3;  		//Button for SynchTme change
		final Label startLbl;
		final Label numOfNewlLbl;
		final Label ndLbl;
		final Combo startComb;
		final Combo numOfNewComb;
		final Combo ndComb;
		final Label SynchTimeLbl;
		final Text SynchTimeT;
		final Combo SynchTimeUnits;
		final Button SynchTimeUp;
		final Label empty4SynchTime;
		final Map<String, String> medNodeMap = new LinkedHashMap<String, String>();
		String[] e1;
	
		
		int[] addStart = new int[values.gettotNodes4() + 1];
		for (int nn1 = 0; nn1 <= values.gettotNodes4(); nn1++) {
			addStart[nn1] = nn1;
		}
		final String[] e = Arrays.toString(addStart).split("[\\[\\]]")[1]
				.split(", ");

		int[] addNum = new int[1024 - values.gettotNodes4()];
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

		
		b2 = new Button(grp_4, SWT.RADIO);
		b2.setText("Edit Node");
		ndLbl = new Label(grp_4, SWT.NONE);
		ndLbl.setText("Node to Edit");
		ndComb = new Combo(grp_4, SWT.DROP_DOWN |SWT.READ_ONLY);
		ndComb.setItems(a);
		ndComb.setEnabled(false);
		ndLbl.setEnabled(false);
		empty4SynchTime = new Label(grp_4,SWT.NONE);
		empty4SynchTime.setLayoutData(new GridData(SWT.LEFT, SWT.BEGINNING,	false, false, 2, 1));
		
		SynchTimeLbl = new Label(grp_4, SWT.NONE);
		SynchTimeLbl.setText("Synch Time");
		SynchTimeLbl.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING,	false, false, 1, 1));
		SynchTimeT = new Text(grp_4, SWT.BORDER);
		SynchTimeT.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,	true, false, 1, 1));
		SynchTimeT.setSize(75, 27);
		SynchTimeT.setText(SynchTime);
		SynchTimeUnits = new Combo(grp_4, SWT.DROP_DOWN | SWT.READ_ONLY);
		SynchTimeUp = new Button(grp_4,SWT.PUSH);
		SynchTimeUp.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		SynchTimeUp.setText("Update SynchTime");
		
		SynchTimeUp.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				SynchTime = SynchTimeT.getText();
				SynchTimeUnit = SynchTimeUnits.getText();

				for (int l = 0; l < values.tree_4.getItemCount(); l++) { //<----Paei apo to 0 mexri to 6
					for (int j = 0; j <values.tree_4.getItem(l).getItemCount(); j++) {
							
							for (int ss = 0; ss <values.tree_4.getItem(l).getItem(j).getItemCount(); ss++) {
								
								if(values.tree_4.getItem(l).getItem(j).getItem(ss).getText().equals("SynchTime")){
									values.tree_4.getItem(l).getItem(j).getItem(ss).getItem(0).setText(SynchTime);
								}
								if(values.tree_4.getItem(l).getItem(j).getItem(ss).getText().equals("SynchTimeUnit")){
									values.tree_4.getItem(l).getItem(j).getItem(ss).getItem(0).setText(SynchTimeUnit);
								}
							}
						}
				}
			}
		});
		
		SynchTimeUnits.setItems(units);
		SynchTimeUnits.select(Arrays.asList(units).indexOf(SynchTimeUnit));

		delBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				values.SavedPS = false;
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
					
					String pp = null;
					for (int l = 0; l < values.tree_4.getItemCount(); l++) {
						//Baze sto Map ta dedomena toy komboy poy epilegatai
	 						for (int j = 0; j < values.tree_4.getItem(l).getItemCount(); j++) {
	 							for (int ss = 0; ss < values.tree_4.getItem(l).getItem(j).getItemCount(); ss++) {
	 								if(values.tree_4.getItem(l).getItem(j).getItem(ss).getText().equals("PATH")){
	 									pp = values.tree_4.getItem(l).getItem(j).getItem(ss).getItem(0).getText();
	 								}
	 							}
	 						}
						}
					
					for (int l = 0; l < values.tree_4.getItemCount(); l++) {
						//Baze sto Map ta dedomena toy komboy poy epilegatai
	 						for (int j = 0; j < values.tree_4.getItem(l).getItemCount(); j++) {
	 							for (int ss = 0; ss < values.tree_4.getItem(l).getItem(j).getItemCount(); ss++) {
	 								if(values.tree_4.getItem(l).getItem(j).getItem(ss).getText().equals("script")){
	 									values.tree_4.getItem(l).getItem(j).getItem(ss).getItem(0).setText(values.benchName+l);;
	 								}
	 								if(values.tree_4.getItem(l).getItem(j).getItem(ss).getText().equals("etherdump")){
	 									values.tree_4.getItem(l).getItem(j).getItem(ss).getItem(0).setText(pp+"/node"+l+"/etherdump_file");
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
				} else if (newLength == 0) { 
					MessageBox zeroNodes = new MessageBox(container.getShell(),	SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					zeroNodes.setMessage("By deleting " + values.gettotNodes4()	+ " nodes the number of nodes will be 0. Are you sure?");
					zeroNodes.setText("Warning - Complete Deletion");
					int resp = zeroNodes.open();
					if (resp == SWT.YES) {
						for (int y = Integer.parseInt(combo1.getText()); y <= Integer
								.parseInt(combo2.getText()); y++) {
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

					} else if (resp == SWT.NO) {
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
		addOn[20] = true; //Epeidh exei hdh grameno text
		addOn[14]=true;
		addOn[3]=true;
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
		7  β†’ mem-size					(OK)			(int 512 - 8192)
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
		final Label StLbl;
		final Button Stbtn; //////////
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
		final Button etherdump;		//////////
		final Text RxPcktTextT;
		final Combo RxPcktCombU;
		final Text IPText;
		final Text userText;
		final Text passText;
		final Text pathText;			//////////
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
		pathText.setText(findPath(values.confFromFile, getLines(values.confFromFile))[0]);
		addCl[18] = findPath(values.confFromFile, getLines(values.confFromFile))[0];

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
					StLbl.setText("Synch Time: "+ SynchTime + SynchTimeUnit);
			}
		});

		// MachineType and dtb if not x86
		configLbl = new Label(grp_3, SWT.NONE);
		configLbl.setText("ConfigPath");
		configLbl.setEnabled(false);
		configComb = new Combo(grp_3, SWT.DROP_DOWN |SWT.READ_ONLY);
		configComb.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
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
		
		
		startComb.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				addCl[0] = startComb.getText();
				addOn[0]=true;
				add = check_addClEnable(addOn);
				addBtn.setEnabled(add);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		
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
		
			etherdump.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){
						Button btn = (Button) e.getSource();
						addCl[19] = String.valueOf(btn.getSelection());
					}else if(!values.addOrEdit){
						Button btn = (Button) e.getSource();
							if(btn.getSelection()){
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
						System.out.println("ADD "+Arrays.toString(addOn));
						if(!pathText.getText().equals("")){
							
							addCl[18] = pathText.getText();
							addOn[20] = true;
							System.out.println("ADD --> OXI Keno "+Arrays.toString(addOn));
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
						}else if(pathText.getText().equals("")){
							
							addOn[20] = false;
							System.out.println("ADD --> KENO "+Arrays.toString(addOn));
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
						}
					}else if(!values.addOrEdit){
						//NEW
						System.out.println("EDIT "+Arrays.toString(addOn));
						if(!pathText.getText().equals("")){
							System.out.println("EDIT --> OXI Keno "+Arrays.toString(addOn));
							medNodeMap.put("PATH", pathText.getText());
							addOn[20] = true;
							add = check_addClEnable(addOn);
							System.out.println("EDIT --> OXI Keno add: "+Arrays.toString(addOn));
							addBtn.setEnabled(add);
						}else if(pathText.getText().equals("")){
							
							addOn[20] = false;
							System.out.println("EDIT --> KENO "+Arrays.toString(addOn));
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
							
							if(!IPText.getText().equals("")){
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

							
						}else if(!btn.getSelection()){
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
								addOn[2]=true;
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
			procComb.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					if(values.addOrEdit){
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
						//	if( addCl[12] != null){
								addCl[12]=null;
						//	}
							dtbLbl.setEnabled(true);
							dtdCombo.setEnabled(true);
							dtdCombo.setItems(dtb86c);
						//	if( addCl[13] != null){
								addCl[13]=null;
						//	}
							dtbLblV.setEnabled(true);
							dtbLblV.setText("Not Selected");
						} /*else if (addCl[4].equals("ARM-32")) {
							powerCmb.setItems(powerARM);
							addOn[19]=false;
							addOn[18]=true;
							add = check_addClEnable(addOn);
							addBtn.setEnabled(add);
							KernelComb.setItems(kernelA32);
							diskImComb.setItems(diskImageA32);
							MachTypeLdl.setEnabled(true);
							MachTypeComb.setEnabled(true);
							MachTypeComb.setItems(macType32);
							addCl[12]=null;
							dtbLbl.setEnabled(true);
							dtdCombo.setEnabled(true);
							dtdCombo.setItems(dtb32c);
						//	if( addCl[13] != null){
								addCl[13]=null;
						//	}
							dtbLblV.setEnabled(true);
							dtbLblV.setText("Not Selected");
							addCl[13]=null;
						}*/ else if (addCl[4].equals("ARM-64")) {
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
					//		if( addCl[13] != null){
								addCl[13]=null;
					//		}
							dtbLblV.setEnabled(true);
							dtbLblV.setText("Not Selected");
						}	else if (addCl[4].equals("RISC-V")) {
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
						if(!procComb.getText().equals(medNodeMap.get("Proc"))){
							medNodeMap.clear();
							medNodeMap.put("PATH", "$GEM5");
							medNodeMap.put("SynchTime", SynchTime);
							medNodeMap.put("SynchTimeUnit", SynchTimeUnit);
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
							/*if(procComb.getText().equals("ARM-32")){
								KernelComb.setItems(kernelA32);
								
								diskImComb.setItems(diskImageA32);
								
								MachTypeLdl.setEnabled(true);
								MachTypeComb.setEnabled(true);
								MachTypeComb.setItems(macType32);
								
								dtbLbl.setEnabled(true);
								dtdCombo.setEnabled(true);
								dtdCombo.setItems(dtb32c);
								
								powerCmb.setItems(powerARM);
								powerBtn.setSelection(false);
								
							}else*/ if(procComb.getText().equals("ARM-64")){
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
								/*if(procComb.getText().equals("ARM-32")){
									KernelComb.setItems(kernelA32);
									KernelComb.select(Arrays.asList(kernelA32).indexOf(medNodeMap.get("kernel")));
									
									diskImComb.setItems(diskImageA32);
									diskImComb.select(Arrays.asList(diskImageA32).indexOf(medNodeMap.get("disk-image")));
									
									MachTypeLdl.setEnabled(true);
									MachTypeComb.setEnabled(true);
									MachTypeComb.setItems(macType32);
									MachTypeComb.select(Arrays.asList(macType32).indexOf(medNodeMap.get("machine-type")));
									
									dtbLbl.setEnabled(true);
									dtdCombo.setEnabled(true);
									dtdCombo.setItems(dtb32c);
									dtdCombo.select(Arrays.asList(dtb32).indexOf(medNodeMap.get("Cores")));
									
									powerCmb.setItems(powerARM);
									if(medNodeMap.containsKey("mcpat-xml")){
										powerBtn.setSelection(true);
										powerCmb.select(Arrays.asList(powerARM).indexOf(medNodeMap.get("mvpat-xml")));
									}else{
										powerBtn.setSelection(false);
										powerCmb.setEnabled(false);
									}
								}else*/ if(procComb.getText().equals("ARM-64")){
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
							if(/*procComb.getText().equals("ARM-32") ||*/ procComb.getText().equals("ARM-64") || procComb.getText().equals("RISC-V")){
								powerCmb.setItems(powerARM);
							}else if(procComb.getText().equals("x86")){
								powerCmb.setItems(powerx86);
							}
						}else{
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
						addCl[8] = SynchTime;
						addCl[9] = SynchTimeUnit;
						addOn[5]=true;
						add = check_addClEnable(addOn);
						addBtn.setEnabled(add);
						addCl[5] = KernelComb.getText();
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
						if(/*procComb.getText().equals("ARM-32") ||*/ procComb.getText().equals("ARM-64") || procComb.getText().equals("RISC-V")){
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
						}/*else if(addCl[4].equals("ARM-32")){
							if(dtdCombo.getText().equals("1")){
								addCl[13] = dtb32[0];
							}else if(dtdCombo.getText().equals("2")){
								addCl[13] = dtb32[1];
							}else if(dtdCombo.getText().equals("4")){
								addCl[13] = dtb32[2];
							}
						}*/else if(addCl[4].equals("ARM-64")){
							addCl[13] = dtb64[0];
						}else if(addCl[4].equals("RISC-V")){
							addCl[13] = dtbriscv[0];
						}
					}else if(!values.addOrEdit){
						dtbLblV.setText("Number of Cores: "+dtdCombo.getText());
						medNodeMap.put("Cores", dtdCombo.getText());
						
							/*if(procComb.getText().equals("ARM-32")){
								if(dtdCombo.getText().equals("1")){
									medNodeMap.put("dtb", dtb32[0]);
								}else if(dtdCombo.getText().equals("2")){
									medNodeMap.put("dtb", dtb32[1]);
								}else if(dtdCombo.getText().equals("4")){
									medNodeMap.put("dtb", dtb32[2]);
								}
							}else*/ if(procComb.getText().equals("ARM-64")){
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
		
		b1.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
 				Button btn = (Button) e.getSource();
 				medNodeMap.put("SynchTime", SynchTime);
 				medNodeMap.put("SynchTimeUnit", SynchTimeUnit);
 				procComb.deselectAll();
				KernelComb.deselectAll();
				diskImComb.deselectAll();
				configComb.deselectAll();
				memSizeComb.deselectAll();
				dtdCombo.deselectAll();
				powerCmb.deselectAll();
				RxPcktCombU.deselectAll();
				//bText.setText("");
				/*StTextT.setText("");*/
				RxPcktTextT.setText("");
				IPText.setText("");
				userText.setText("");
				passText.setText("");
				//pathText.setText("");
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
 				}

 			}
		});

		b2.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				medNodeMap.put("SynchTime", SynchTime);
				medNodeMap.put("SynchTimeUnit", SynchTimeUnit);
				StLbl.setText("Synch Time: "+SynchTime+SynchTimeUnit);
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
 							/*if(procComb.getText().equals("ARM-32")){
 								MachTypeComb.setItems(macType32);
 								MachTypeComb.select(Arrays.asList(macType32).indexOf(medNodeMap.get("machine-type")));
 							}else*/ if(procComb.getText().equals("ARM-64")){
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
 							/*if(procComb.getText().equals("ARM-32")){
 								dtdCombo.setItems(dtb32c);
 								dtdCombo.select(Arrays.asList(dtb32c).indexOf(medNodeMap.get("Cores")));
 							}else*/ if(procComb.getText().equals("ARM-64")){
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
 							/*if(procComb.getText().equals("ARM-32")){
 								KernelComb.setItems(kernelA32);
 								KernelComb.select(Arrays.asList(kernelA32).indexOf(medNodeMap.get("kernel")));
 							}else*/ if(procComb.getText().equals("ARM-64")){
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
 							/*if(procComb.getText().equals("ARM-32")){
 								diskImComb.setItems(diskImageA32);
 								diskImComb.select(Arrays.asList(diskImageA32).indexOf(medNodeMap.get("disk-image")));
 							}else*/ if(procComb.getText().equals("ARM-64")){
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
 							if(/*procComb.getText().equals("ARM-32") ||*/ procComb.getText().equals("ARM-64") || procComb.getText().equals("RISC-V")){
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
					values.SavedPS = false;
					values.Saved5 = false;
					//New Nodes
					addBtn.setEnabled(false);
					if(values.addOrEdit){
						//Gia Add
						for (int yy = Integer.parseInt(addCl[0]); yy < Integer.parseInt(addCl[0])+Integer.parseInt(addCl[1]); yy++) {
							//TODO
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
							}
							if (addCl[14] != null) {
								TreeItem benchItem = new TreeItem(procItem, SWT.NONE);
								TreeItem benchItemV = new TreeItem(benchItem, SWT.NONE);
								benchItem.setText("script");
								benchItemV.setText(addCl[14]+yy);
							}
							if (addCl[15] != null) {
								TreeItem IPItem = new TreeItem(procItem, SWT.NONE);
								TreeItem IPItemV = new TreeItem(IPItem, SWT.NONE);
								IPItem.setText("IP");
								IPItemV.setText(addCl[15]);
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
						final String[] a = Arrays.toString(delStartnew).split("[\\[\\]]")[1]
								.split(", ");

						combo1.setItems(a); // Mporei na 3ekinhsei apo opoiondhpote node
						combo1.setEnabled(true);
						ndComb.setItems(a);
						//ndComb.setEnabled(true);
						//ndLbl.setEnabled(true);
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
		
		
		final Button SaveBtn = new Button(container1, SWT.PUSH);
		SaveBtn.setText("Save As");
				
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
							values.SavedPS = true;	
							values.Saved5 = true;
							done = true;
						} catch (IOException e1) {
						}
					}
				}

			}
		});


		Button DoneBtn = new Button(container1, SWT.PUSH);
		DoneBtn.setText("Done");
		
		
		DoneBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				////////////////////////Save Txc.ned///////////////////////////////////////
				String ttt = values.createFileS(values.tree_4);
				String savePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "OMNET_WORKSPACE/HLANode/src";
				File myFile = new File(savePath, "Txc.ned");
				PrintWriter textFileWriter;
				try {
					textFileWriter = new PrintWriter(new FileWriter(myFile));
					textFileWriter.write(ttt);
					textFileWriter.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				//////////////////////////Save Txc.ned - END///////////////////////////////////
				////////////////////////Save run.sh///////////////////////////////////////////
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
				////////////////////////Save run.sh - END////////////////////////////////////
				
				MessageDialog.openInformation(
						container.getShell(),
						"Auto saved files",
						"Comnfiguration saved in /home/cossim/COSSIM/gem5/run.sh and Txc.ned file saved in /home/cossim/OMNET_WORKSPACE/HLANode/src" + savePath);
				getWizard().getContainer().getShell().close();
				
			}
		});
		
		sccontainer.layout(true, true);
		sccontainer.setMinSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
	}
	
	
	boolean check_addClEnable(boolean[] val){ //Gia na ginei enable to Add Cluster
		boolean enable = false;

	if(val[0]==true && val[1]==true && val[4]==true && val[5]==true && val[6]==true && val[7]==true && val[8]==true && val[9]==true && val[10]==true && val[11]==true && val[20]==true){
		if(val[19]==true){ //if(x86)
			if(val[2]==true){ //if(remote)
				if(val[3]==true){ //if(bench)
					if(val[14]==true && val[15]==true && val[16]==true && val[17]==true){
						enable = true;
						}
					}else if(val[3] == false){ //if(not bench)
						if(val[15]==true && val[16]==true && val[17]==true){
							enable = true;
						}
				}
			}else if(val[2]==false){//if(not remote)
				if(val[3]==true){ //if(bench)
					if(val[14]==true){
						enable = true;
						}
					}else if(val[3] == false){ //if(not bench)
							enable = true;
					}
				
			}
		}else if(val[18]==true){//if(ARM)
			if(val[12]==true && val[13]==true){
				if(val[2]==true){ //if(remote)
					if(val[3]==true){ //if(bench)
						if(val[14]==true && val[15]==true && val[16]==true && val[17]==true){
							enable = true;
							}
						}else if(val[3] == false){ //if(not bench)
							if(val[15]==true && val[16]==true && val[17]==true){
								enable = true;
							}
					}
				}else if(val[2]==false){
					if(val[3]==true){ //if(bench)
						if(val[14]==true){
							enable = true;
							}
						}else if(val[3] == false){ //if(not bench)
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

