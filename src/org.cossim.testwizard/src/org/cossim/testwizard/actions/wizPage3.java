package org.cossim.testwizard.actions;


import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class wizPage3 extends WizardPage {

	private wizVal values;
	
	
	private Label[] KernelLbl;
	private Label[] diskImLbl;
	private Label[] configLbl; 					
	private Label[] memSizeLbl;
	private Label[] StLbl;
	private Button[] Stbtn;
	private Label[] RxPcktLbl;
	private Label[] dtbLbl;
	private Label[] dtbVLbl;
	private Label[] IPLbl;
	private Label[] usrLbl;
	
	private Label[] passLbl;
	private Combo[] KernelComb;
	private Combo[] diskImComb;
	private Combo[] configComb;   				
	private Combo[] memSizeComb;
	
	private Text[] RxPcktTextT;
	private Combo[] RxPcktCombU;
	private Text[] IPText;
	private Text[] userText;
	private Text[] passText;
	private Combo[] dtdCombo;
	
	private Label[] empty1;
	private Label[] empty2;
	private Label[] empty3;
	
	private Label[] powerLbl;
	private Combo[] powerCombo;
	
	private Label[] core86Lbl;
	private Combo[] core86Combo;
	private Label[] core86VLbl;
	
	final String[] kernelRISCV = { "riscv-bootloader-vmlinux-5.10-PCI" };
	final String[] kernelA64 = { "vmlinux.arm64" }; 
	final String[] kernelx86 = { "vmlinux-5.4.49" }; 

	final String[] diskImageRISCV = {"riscv-ubuntu.img" };
	final String[] diskImageA64 = { "ubuntu-18.04-arm64-docker.img" }; 
	final String[] diskImagex86 = { "x86-ubuntu.img" }; 

	final String[] memSize = { "2048MB", "4096MB", "8192MB" }; 

	final String[] units = { "ms", "us" };
	
	final String[] configRISCV = { "$GEM5/configs/example/gem5_library/riscv-ubuntu-run.py" };
	final String[] configA64 = { "$GEM5/configs/example/arm/starter_fs.py" };
	final String[] configx86 = { "$GEM5/configs/example/fs.py" };

	final String[] dtbriscv = {"1", "2", "4", "8", "16", "32", "64"};		
	final String[] dtb64 = {"1", "2", "4", "8", "16", "32", "64"};			
	final String[] dtb86 = {"1", "2", "4", "8", "16", "32", "64"};			
	
	final String[] dtbriscvl = { "" };
	final String[] dtb64l = { "" }; 
		
	final String[] powerx86 = {"x86_AtomicSimpleCPU_template.xml"};
	final String[] powerARM = {"ARM_AtomicSimpleCPU_template.xml"};

	
	public wizPage3(wizVal values) {
		super("Details");
		
		setTitle("Nodes Details");
		this.values = values;
	}

	public void createControl(Composite parent) {
		values.sc3 = new ScrolledComposite(parent, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		values.sc3.setExpandVertical(true);
		values.sc3.setExpandHorizontal(true);

		values.container3 = new Composite(values.sc3, SWT.NULL);
		values.sc3.setContent(values.container3);
		values.container3.setLayout(new GridLayout(1, true));

		setControl(values.sc3);

		values.composite_3 = new Composite(values.container3, SWT.NONE);
		values.composite_3.setLayout(new GridLayout(1, true));
		values.composite_3.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));

		createp3(values.composite_3, values.sc3, values.container3);
	}

	void createp3(Composite composite_2, ScrolledComposite sc, Composite container){
		values.grp3 = new Group[values.gettotCl()];
		
		empty1 = new Label[values.gettotCl()];
		empty2 = new Label[values.gettotCl()];
		empty3 = new Label[values.gettotCl()];
		KernelLbl = new Label[values.gettotCl()];
		diskImLbl = new Label[values.gettotCl()];
		configLbl = new Label[values.gettotCl()];		
		memSizeLbl = new Label[values.gettotCl()];
		StLbl = new Label[values.gettotCl()];
		Stbtn = new Button[values.gettotCl()];
		RxPcktLbl = new Label[values.gettotCl()];
		dtbLbl = new Label[values.gettotCl()];
		dtbVLbl = new Label[values.gettotCl()];

		KernelComb = new Combo[values.gettotCl()];
		IPLbl = new Label[values.gettotCl()];
		usrLbl = new Label[values.gettotCl()];
		passLbl = new Label[values.gettotCl()];
		diskImComb = new Combo[values.gettotCl()];
		configComb = new Combo[values.gettotCl()];			
		memSizeComb = new Combo[values.gettotCl()];
		RxPcktTextT = new Text[values.gettotCl()];
		RxPcktCombU = new Combo[values.gettotCl()];
		dtdCombo = new Combo[values.gettotCl()];
		IPText = new Text[values.gettotCl()];
		userText = new Text[values.gettotCl()];
		passText = new Text[values.gettotCl()];
		
		powerLbl = new Label[values.gettotCl()];
		powerCombo = new Combo[values.gettotCl()];
		core86Lbl = new Label[values.gettotCl()];
		core86Combo  = new Combo[values.gettotCl()];
		core86VLbl = new Label[values.gettotCl()];
		values.obj = new String[values.gettotCl()][23];

		
		for (int k = 0; k < values.gettotCl(); k++) {
			int l = k + 1;
		}

		int k;
		for (int li = 0; li < values.gettotCl(); li++) {
			k = li + 1;
			String ss = "cl" + k;
			values.interMap.put(values.clAtrrs[0], values.map2.get(ss).get(values.clAtrrs[0]));
			values.interMap.put(values.clAtrrs[1], values.map2.get(ss).get(values.clAtrrs[1]));
     		values.interMap.put(values.clAtrrs[2], values.map2.get(ss).get(values.clAtrrs[2]));
     		values.interMap.put(values.clAtrrs[3], values.map2.get(ss).get(values.clAtrrs[3]));
     		values.interMap.put(values.clAtrrs[4], values.map2.get(ss).get(values.clAtrrs[4]));
     		values.interMap.put(values.clAtrrs[5], values.map2.get(ss).get(values.clAtrrs[5]));
     		values.interMap.put(values.clAtrrs[19], values.map2.get(ss).get(values.clAtrrs[19]));

			values.obj[li] = values.map.get(ss);
			
			values.grp3[li] = new Group(composite_2, SWT.NONE);
			values.grp3[li].setText("Cluster " + k + " Configuration");
			values.grp3[li].setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true,true, 1, 1)); // Alignments, spaces etc
			values.grp3[li].setLayout(new GridLayout(10, false));
			values.grp3[li].setEnabled(true);
			
			final int ki = li;
			values.obj[ki][18] = values.path;			
			
			// Kernel
			KernelLbl[li] = new Label(values.grp3[li], SWT.NONE);
			KernelLbl[li].setText("kernel");
			KernelComb[li] = new Combo(values.grp3[li], SWT.DROP_DOWN | SWT.READ_ONLY);
			KernelComb[li].setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
					false, false, 3, 1));
			if (values.map.get("cl" + k)[4].equals("x86")) {
				KernelComb[li].setItems(kernelx86);
			}	else if (values.map.get("cl" + k)[4].equals("ARM-64")) {
				KernelComb[li].setItems(kernelA64);
			}	else if (values.map.get("cl" + k)[4].equals("RISC-V")) {
				KernelComb[li].setItems(kernelRISCV);
			}

			
			final int iii = k;
			KernelComb[li].addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e) {
					
					values.obj[ki][5] = KernelComb[ki].getText();
					values.interMap.put(values.clAtrrs[6], KernelComb[ki].getText());
					values.NextOnP3[ki][0]=true;
					getWizard().getContainer().updateButtons();
				}

				public void widgetDefaultSelected(SelectionEvent e) {

				}
			});

			// Disk Image
			diskImLbl[li] = new Label(values.grp3[li], SWT.NONE);
			diskImLbl[li].setText("disk-image");
			diskImComb[li] = new Combo(values.grp3[li], SWT.DROP_DOWN | SWT.READ_ONLY);
			diskImComb[li].setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
					false, false, 3, 1));
			if (values.map.get("cl" + k)[4].equals("x86")) {
				diskImComb[li].setItems(diskImagex86);
			}	else if (values.map.get("cl" + k)[4].equals("ARM-64")) {
				diskImComb[li].setItems(diskImageA64);
			}   else if (values.map.get("cl" + k)[4].equals("RISC-V")) {
				diskImComb[li].setItems(diskImageRISCV);
			}

			diskImComb[li].addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e) {
					values.obj[ki][6] = diskImComb[ki].getText();
					values.interMap.put(values.clAtrrs[7], diskImComb[ki].getText());
					values.NextOnP3[ki][1]=true;
					getWizard().getContainer().updateButtons();
				}

				public void widgetDefaultSelected(SelectionEvent e) {

				}
			});
			
			// Mem Size
			memSizeLbl[li] = new Label(values.grp3[li], SWT.NONE);
			memSizeLbl[li].setText("mem-size");
			memSizeComb[li] = new Combo(values.grp3[li], SWT.DROP_DOWN | SWT.READ_ONLY);
			memSizeComb[li].setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,	false, false, 1, 1));
			memSizeComb[li].setItems(memSize);
			memSizeComb[li].addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e) {
					values.obj[ki][7] = memSizeComb[ki].getText();
					values.interMap.put(values.clAtrrs[8], memSizeComb[ki].getText());
					values.NextOnP3[ki][2]=true;
					getWizard().getContainer().updateButtons();
				}

				public void widgetDefaultSelected(SelectionEvent e) {

				}
			});

			
			// Configuration Path                                      
			configLbl[li] = new Label(values.grp3[li], SWT.NONE);
			configLbl[li].setText("ConfigPath");
			configComb[li] = new Combo(values.grp3[li], SWT.DROP_DOWN | SWT.READ_ONLY);
			configComb[li].setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false, 2, 1));
			if (values.map.get("cl" + k)[4].equals("x86")) {
				configComb[li].setItems(configx86);
			}	else if (values.map.get("cl" + k)[4].equals("ARM-64")) {
				configComb[li].setItems(configA64);
			}	else if (values.map.get("cl" + k)[4].equals("RISC-V")) {
				configComb[li].setItems(configRISCV);
			}
			

			configComb[li].addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					values.obj[ki][12] = configComb[ki].getText();
					values.interMap.put(values.clAtrrs[13], configComb[ki].getText());
					values.NextOnP3[ki][7]=true;
					getWizard().getContainer().updateButtons();
				}

				public void widgetDefaultSelected(SelectionEvent e) {

				}
			});
			
			
			
			
			if (!values.map.get("cl" + k)[4].equals("x86")) {
			dtbLbl[li] = new Label(values.grp3[li], SWT.NONE);
			dtbLbl[li].setText("Cores"); 
			dtbLbl[li].setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			dtdCombo[li] = new Combo(values.grp3[li], SWT.DROP_DOWN | SWT.READ_ONLY);
			dtdCombo[li].setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			if (values.map.get("cl" + k)[4].equals("ARM-64")) {
				dtdCombo[li].setItems(dtb64);
			} else if (values.map.get("cl" + k)[4].equals("RISC-V")) {
				dtdCombo[li].setItems(dtbriscv);
			}
			dtbVLbl[li] = new Label(values.grp3[li], SWT.NONE);
			dtbVLbl[li].setLayoutData(new GridData(SWT.FILL,
					SWT.BEGINNING, true, false, 5, 1));
			dtbVLbl[li].setText(""); // (Not Selected)

			final int dd = k;
			dtdCombo[li].addSelectionListener(new SelectionListener() {
				
				public void widgetSelected(SelectionEvent e) {
					values.obj[ki][13] = dtdCombo[ki].getText();
					values.interMap.put(values.clAtrrs[14], dtdCombo[ki].getText());
					values.NextOnP3[ki][8]=true;
					
					if (values.map.get("cl" + dd)[4].equals("ARM-64")) { 
						if(dtdCombo[ki].getText().equals("1")){
							values.obj[ki][13] = dtb64l[0];		//Only one value at this time
							values.obj[ki][22] = "1";
							values.interMap.put(values.clAtrrs[14], dtb64l[0]);
						}else if(dtdCombo[ki].getText().equals("2")){
							values.obj[ki][13] = dtb64l[0];		//Only one value at this time
							values.obj[ki][22] = "2";
							values.interMap.put(values.clAtrrs[14], dtb64l[0]);
						}else if(dtdCombo[ki].getText().equals("4")){
							values.obj[ki][13] = dtb64l[0];		//Only one value at this time
							values.obj[ki][22] = "4";
							values.interMap.put(values.clAtrrs[14], dtb64l[0]);
						}else if(dtdCombo[ki].getText().equals("8")){	
							values.obj[ki][13] = dtb64l[0];		//Only one value at this time
							values.obj[ki][22] = "8";
							values.interMap.put(values.clAtrrs[14], dtb64l[0]);
						}else if(dtdCombo[ki].getText().equals("16")){
							values.obj[ki][13] = dtb64l[0];		//Only one value at this time
							values.obj[ki][22] = "16";
							values.interMap.put(values.clAtrrs[14], dtb64l[0]);
						}else if(dtdCombo[ki].getText().equals("32")){
							values.obj[ki][13] = dtb64l[0];		//Only one value at this time
							values.obj[ki][22] = "32";
							values.interMap.put(values.clAtrrs[14], dtb64l[0]);
						}else if(dtdCombo[ki].getText().equals("64")){
							values.obj[ki][13] = dtb64l[0];		//Only one value at this time
							values.obj[ki][22] = "64";
							values.interMap.put(values.clAtrrs[14], dtb64l[0]);
						}																								
					}
					else if (values.map.get("cl" + dd)[4].equals("RISC-V")) { 
						if(dtdCombo[ki].getText().equals("1")){
							values.obj[ki][13] = dtbriscvl[0];		//Only one value at this time
							values.obj[ki][22] = "1";
							values.interMap.put(values.clAtrrs[14], dtbriscvl[0]);
						}else if(dtdCombo[ki].getText().equals("2")){
							values.obj[ki][13] = dtbriscvl[0];		//Only one value at this time
							values.obj[ki][22] = "2";
							values.interMap.put(values.clAtrrs[14], dtbriscvl[0]);
						}else if(dtdCombo[ki].getText().equals("4")){
							values.obj[ki][13] = dtbriscvl[0];		//Only one value at this time
							values.obj[ki][22] = "4";
							values.interMap.put(values.clAtrrs[14], dtbriscvl[0]);
						}else if(dtdCombo[ki].getText().equals("8")){													
							values.obj[ki][13] = dtbriscvl[0];		//Only one value at this time
							values.obj[ki][22] = "8";
							values.interMap.put(values.clAtrrs[14], dtbriscvl[0]);
						}else if(dtdCombo[ki].getText().equals("16")){
							values.obj[ki][13] = dtbriscvl[0];		//Only one value at this time
							values.obj[ki][22] = "16";
							values.interMap.put(values.clAtrrs[14], dtbriscvl[0]);
						}else if(dtdCombo[ki].getText().equals("32")){
							values.obj[ki][13] = dtbriscvl[0];		//Only one value at this time
							values.obj[ki][22] = "32";
							values.interMap.put(values.clAtrrs[14], dtbriscvl[0]);
						}else if(dtdCombo[ki].getText().equals("64")){
							values.obj[ki][13] = dtbriscvl[0];		//Only one value at this time
							values.obj[ki][22] = "64";
							values.interMap.put(values.clAtrrs[14], dtbriscvl[0]);
						}																								
					}
					getWizard().getContainer().updateButtons();
				}
				
				public void widgetDefaultSelected(SelectionEvent e) {

				}
			});
			}

			if (values.map.get("cl" + k)[4].equals("x86")) {
				core86Lbl[li] = new Label(values.grp3[li], SWT.NONE);
				core86Lbl[li].setText("Cores");
				core86Lbl[li].setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				core86Combo[li] = new Combo(values.grp3[li], SWT.DROP_DOWN | SWT.READ_ONLY);
				core86Combo[li].setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				core86Combo[li].setItems(dtb86);
				
				core86VLbl[li] = new Label(values.grp3[li], SWT.NONE);
				core86VLbl[li].setLayoutData(new GridData(SWT.FILL,
						SWT.BEGINNING, true, false, 5, 1)); 
				core86VLbl[li].setText(""); // (Not Selected)
				
				final int dd1 = k;
				core86Combo[li].addSelectionListener(new SelectionListener() {
					
					public void widgetSelected(SelectionEvent e) {
						values.NextOnP3[ki][8]=true;
						if(core86Combo[ki].getText().equals("1")){
							values.obj[ki][22] = "1";
						}else if(core86Combo[ki].getText().equals("2")){
							values.obj[ki][22] = "2";
						}else if(core86Combo[ki].getText().equals("4")){
							values.obj[ki][22] = "4";
						}else if(core86Combo[ki].getText().equals("8")){		
							values.obj[ki][22] = "8";
						}else if(core86Combo[ki].getText().equals("16")){
							values.obj[ki][22] = "16";
						}else if(core86Combo[ki].getText().equals("32")){
							values.obj[ki][22] = "32";
						}else if(core86Combo[ki].getText().equals("64")){
							values.obj[ki][22] = "64";
						}														
						getWizard().getContainer().updateButtons();
					}

					
					public void widgetDefaultSelected(SelectionEvent e) {

					}
				});
						
				
			}
			
			values.NextOnP3[ki][3]=true;   
			values.NextOnP3[ki][4]=true; 
			values.obj[ki][8] = values.SynchTime;
			values.interMap.put(values.clAtrrs[9],values.SynchTime); 
			values.obj[ki][9] = values.SynchTimeUnit;
			values.interMap.put(values.clAtrrs[10], values.SynchTimeUnit);
		
			// RxPacketTime
			RxPcktLbl[li] = new Label(values.grp3[li], SWT.NONE);
			RxPcktLbl[li].setText("RxPacketTime");
			RxPcktTextT[li] = new Text(values.grp3[li], SWT.BORDER);
			RxPcktTextT[li].setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
					false, false, 1, 1));
			
			RxPcktTextT[li].addListener(SWT.Verify, new Listener() {
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
			
			
			RxPcktTextT[li].addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					values.obj[ki][10] = RxPcktTextT[ki].getText();
					values.interMap.put(values.clAtrrs[11], RxPcktTextT[ki].getText());
					if(!RxPcktTextT[ki].getText().equals("")){
						values.NextOnP3[ki][5]=true;
						getWizard().getContainer().updateButtons();
					}
					if(RxPcktTextT[ki].getText().equals("")){
						values.NextOnP3[ki][5]=false;
						getWizard().getContainer().updateButtons();
					}
					
				}
			});

			RxPcktCombU[li] = new Combo(values.grp3[li], SWT.DROP_DOWN | SWT.READ_ONLY);
			RxPcktCombU[li].setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
					false, false, 1, 1));
			RxPcktCombU[li].setItems(units);

			RxPcktCombU[li].addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e) {
					values.obj[ki][11] = RxPcktCombU[ki].getText();
					values.interMap.put(values.clAtrrs[12], RxPcktCombU[ki].getText());
					values.NextOnP3[ki][6]=true;
					getWizard().getContainer().updateButtons();
				}

				public void widgetDefaultSelected(SelectionEvent e) {

				}
			});
			
			
			
			Stbtn[li] = new Button(values.grp3[li], SWT.PUSH);
			Stbtn[li].setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			Stbtn[li].setText("SyncTime");
			StLbl[li] = new Label(values.grp3[li], SWT.NONE);
			if (!values.map.get("cl" + k)[4].equals("x86")) {
				StLbl[li].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
			}else{
				StLbl[li].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
			}
			
			Stbtn[li].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					for (int yy = 0; yy < values.gettotCl(); yy++) {
						StLbl[yy].setText("Synchronization Time: "+values.SynchTime+values.SynchTimeUnit);
					}
				}
			});
	
			// Benchmark
			values.NextOnP3[ki][9]=true; 
			
			if ((boolean) values.map2.get("cl" + k).get(values.clAtrrs[3])) {
				values.interMap.put(values.clAtrrs[15], values.benchName);
			}else{
				values.interMap.remove(values.clAtrrs[15]);
			}
			
			if (values.map.get("cl" + k)[3].equals("true")) {
				values.obj[ki][14] = values.benchName;
			}else if(values.map.get("cl" + k)[3].equals("false")){
				values.obj[ki][14] = "null";
				
			}
			
			// IP + Username+Password - Array
			if (values.map.get("cl" + k)[2].equals("true")) {
				// IP
				IPLbl[li] = new Label(values.grp3[li], SWT.NONE);
				IPLbl[li].setText("IP");
				IPText[li] = new Text(values.grp3[li], SWT.BORDER);
				IPText[li].setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
						true, false, 2, 1));
				IPText[li].setSize(30, 27);
				IPText[li].addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						values.obj[ki][15] = IPText[ki].getText();
						if(!IPText[ki].getText().equals("")){
							values.NextOnP3[ki][10]=true;
							getWizard().getContainer().updateButtons();
						}
						if(IPText[ki].getText().equals("")){
							values.NextOnP3[ki][10]=false;
							getWizard().getContainer().updateButtons();
						}
						
					}
				});

				// Username
				usrLbl[li] = new Label(values.grp3[li], SWT.NONE);
				usrLbl[li].setText("Username");
				userText[li] = new Text(values.grp3[li], SWT.BORDER);
				userText[li].setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
						true, false, 2, 1));
				userText[li].setSize(150, 27);
				userText[li].addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						values.obj[ki][16] = userText[ki].getText();
						if(!userText[ki].getText().equals("")){
							values.NextOnP3[ki][11]=true;
							getWizard().getContainer().updateButtons();
						}
						if(userText[ki].getText().equals("")){
							values.NextOnP3[ki][11]=false;
							getWizard().getContainer().updateButtons();
						}
						
					}
				});
				// Password
				passLbl[li] = new Label(values.grp3[li], SWT.NONE);
				passLbl[li].setText("Password");
				passText[li] = new Text(values.grp3[li], SWT.BORDER | SWT.PASSWORD);
				passText[li].setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
						true, false, 3, 1));
				passText[li].addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						values.obj[ki][17] = passText[ki].getText();
						if(!passText[ki].getText().equals("")){
							values.NextOnP3[ki][12]=true;
							getWizard().getContainer().updateButtons();
						}
						if(passText[ki].getText().equals("")){
							values.NextOnP3[ki][12]=false;
							getWizard().getContainer().updateButtons();
						}
					}
				});
			}else if(values.map.get("cl" + k)[2].equals("false")){
				values.obj[ki][15] = "null";
				values.obj[ki][16] = "null";
				values.obj[ki][17] = "null";
			}
			// Energy
			if (values.map.get("cl" + k)[20].equals("true")) {
				powerLbl[li] = new Label(values.grp3[li], SWT.NONE);
				powerLbl[li].setText("McPat-xml");
				
				powerCombo[li] = new Combo(values.grp3[li], SWT.DROP_DOWN | SWT.READ_ONLY);
				powerCombo[li].setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
				if(values.map.get("cl" + k)[4].equals("x86")) {
					powerCombo[li].setItems(powerx86);
				}else{
					powerCombo[li].setItems(powerARM);
				}
				
				powerCombo[li].addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e) {
						values.obj[ki][21] = powerCombo[ki].getText();
						values.interMap.put(values.clAtrrs[21], powerCombo[ki].getText());
						values.NextOnP3[ki][13]=true;
						getWizard().getContainer().updateButtons();
					}

					public void widgetDefaultSelected(SelectionEvent e) {

					}
				});
				
				
			}
			
			
			
			
		
		}

		for (int m = 0; m < values.gettotCl(); m++) {
			int l = m + 1;
		}
		sc.layout(true, true);
		sc.setMinSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
	}
	
	boolean check_NextlEnable(boolean[][] val){
		boolean[] enable = new boolean[values.gettotCl()];
		boolean enable1 = false;
		
		for(int k=0;k<values.gettotCl();k++){
			enable[k] = false;
		}
	
		for(int k=0;k<values.gettotCl();k++){
			int l=k+1;
			if(val[k][0] == true && val[k][1] == true && val[k][2] == true && val[k][3] == true && val[k][4] == true  && val[k][5] == true  && val[k][6] == true && val[k][7] == true){ // val[k][7] == true gia to config
				if(val[k][8] == true){ //if cores
					if(values.map.get("cl"+l)[2].equals("true")){ //if(remote)
						if(values.map.get("cl"+l)[20].equals("true")){ //if(Energy) 
							if(val[k][13] == true && val[k][10] == true && val[k][11] == true && val[k][12] == true){
								enable[k]=true;
							}
						}else if(values.map.get("cl"+l)[20].equals("false")){ //if(not Energy)
							if(val[k][10] == true && val[k][11] == true && val[k][12] == true){
								enable[k]=true;
							}
						}
					}else if(values.map.get("cl"+l)[2].equals("false")){ //if (not remote)
						if(values.map.get("cl"+l)[20].equals("true")){ //if(Energy) 
							if(val[k][13] == true){
								enable[k]=true;
							}
						}else if(values.map.get("cl"+l)[20].equals("false")){ //if(not Energy) 
							enable[k]=true;
						}
					}
				}
			}
		}
		
		boolean comp = true;
		for(int gg = 0; gg < enable.length; gg++){
			
			comp = comp && enable[gg];
		}
		enable1 = comp;
		
		return enable1;
	}
	
	
	public boolean canFlipToNextPage(){
		 
		if(check_NextlEnable(values.NextOnP3)){
			return true;
			
		}else{
			return false;
		}
		
	}
}


