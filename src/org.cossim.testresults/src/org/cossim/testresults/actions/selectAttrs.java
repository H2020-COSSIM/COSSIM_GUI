package org.cossim.testresults.actions;


import java.util.Arrays;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;


public class selectAttrs extends Dialog {


	/*
	 * ~/COSSIM/gem5/node#/stats.txt 
	 * simSeconds = Number of seconds simulated
	 * simTicks = Number of ticks simulated 
	 * finalTick = Number of ticks from beginning of simulation (restored from checkpoints and never reset)
	 * simFreq = Frequency of simulated ticks hostInstRate = Simulator instruction rate (inst/s) 
	 * hostOpRate = Simulator op (including microops) rate (op/s) 
	 * hostTickRate = Simulator tick rate (ticks/s)
	 * hostMemory = Number of bytes of host memory used 
	 * hostSeconds = Real time elapsed on the host 
	 * simInsts = Number of instructions simulated
	 * simOps = Number of ops (including micro ops) simulated
	 * 
	 * ~/COSSIM/gem5/McPat/energy#.txt 
	 * runtime 
	 * energy
	 * 
	 * ~/COSSIM/gem5/McPat/mcpatOutput#.txt 
	 * Area 
	 * Peak Power 
	 * Total Power 
	 * Total Leakage 
	 * Peak Dynamic 
	 * Runtime Dynamic
	 */
	private Button selectAllGEM5;
	private Label empty1;
	private Button simSeconds;
	private Button simTicks;
	private Button finalTick;
	private Button simFreq;
	private Button hostInstRate;
	private Button hostOpRate;
	private Button hostTickRate;
	private Button hostMemory;
	private Button hostSeconds;
	private Button simInsts;
	private Button simOps;


	private Button selectAllMc;
	private Label empty2;
	private Button runtime;
	private Button energy;
	private Button Area;
	private Button Peak_Power;
	private Button Total_Leakage;
	private Button Peak_Dynamic;
	private Button Runtime_Dynamic;
	//boolean[] attrs = new boolean[11];


	dialogValues dv = new dialogValues();
	
	protected selectAttrs(Shell parentShell) {
		super(parentShell);
		
		
		
	}


	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);


		
		
		Group GEM5Group = new Group(container, SWT.NONE);
		GEM5Group.setText("GEM5 Details");
		GEM5Group.setLayout(new GridLayout(1, true));


		selectAllGEM5 = new Button(GEM5Group, SWT.CHECK);
		selectAllGEM5.setText("Select All GEM5");
		/*selectAllGEM5.setSelection(true);*/
		boolean tt = allTrue(dv.getAttrG());
		selectAllGEM5.setSelection(tt);


		selectAllGEM5.addSelectionListener(new SelectionAdapter() {


			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				System.out.println(btn.getSelection());
				if (btn.getSelection()) {
					simSeconds.setSelection(true);
					simTicks.setSelection(true);
					finalTick.setSelection(true);
					simFreq.setSelection(true);
					hostInstRate.setSelection(true);
					hostOpRate.setSelection(true);
					hostTickRate.setSelection(true);
					hostMemory.setSelection(true);
					hostSeconds.setSelection(true);
					simInsts.setSelection(true);
					simOps.setSelection(true);
					for(int k=0;k<dv.getAttrG().length;k++){
						dv.setAttrG(k, true);
					}
					System.out.println("Mesa sto selectAttrs1: "+Arrays.toString(dv.getAttrG()));
				} else if (!btn.getSelection()) {
					simSeconds.setSelection(false);
					simTicks.setSelection(false);
					finalTick.setSelection(false);
					simFreq.setSelection(false);
					hostInstRate.setSelection(false);
					hostOpRate.setSelection(false);
					hostTickRate.setSelection(false);
					hostMemory.setSelection(false);
					hostSeconds.setSelection(false);
					simInsts.setSelection(false);
					simOps.setSelection(false);
					for(int k=0;k<dv.getAttrG().length;k++){
						dv.setAttrG(k, false);
					}
					System.out.println("Mesa sto selectAttrs2: "+Arrays.toString(dv.getAttrG()));
				}
			}
		});


		empty1 = new Label(GEM5Group, SWT.NONE);


		simSeconds = new Button(GEM5Group, SWT.CHECK);
		simSeconds.setText("Number of seconds simulated");
		simSeconds.setSelection(dv.getAttrG()[0]);
		simSeconds.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(0, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto simSeconds: "+Arrays.toString(dv.getAttrG()));
			}
		});


		
		simTicks = new Button(GEM5Group, SWT.CHECK);
		simTicks.setText("Number of ticks simulated");
		simTicks.setSelection(dv.getAttrG()[1]);
		simTicks.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(1, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto simSeconds: "+Arrays.toString(dv.getAttrG()));
			}
		});


		finalTick = new Button(GEM5Group, SWT.CHECK);
		finalTick.setText("Number of ticks from beginning of simulation");
		finalTick.setSelection(dv.getAttrG()[2]);
		finalTick.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(2, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto simSeconds: "+Arrays.toString(dv.getAttrG()));
			}
		});


		simFreq = new Button(GEM5Group, SWT.CHECK);
		simFreq.setText("Frequency of simulated ticks");
		simFreq.setSelection(dv.getAttrG()[3]);
		simFreq.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(3, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto simSeconds: "+Arrays.toString(dv.getAttrG()));
			}
		});


		hostInstRate = new Button(GEM5Group, SWT.CHECK);
		hostInstRate.setText("Simulator instruction rate (inst/s)");
		hostInstRate.setSelection(dv.getAttrG()[4]);
		hostInstRate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(4, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto simSeconds: "+Arrays.toString(dv.getAttrG()));
			}
		});


		hostOpRate = new Button(GEM5Group, SWT.CHECK);
		hostOpRate.setText("Simulator op (including micro ops) rate (op/s)");
		hostOpRate.setSelection(dv.getAttrG()[5]);
		hostOpRate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(5, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto simSeconds: "+Arrays.toString(dv.getAttrG()));
			}
		});




		hostTickRate = new Button(GEM5Group, SWT.CHECK);
		hostTickRate.setText("Simulator tick rate (ticks/s)");
		hostTickRate.setSelection(dv.getAttrG()[6]);
		hostTickRate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(6, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto simSeconds: "+Arrays.toString(dv.getAttrG()));
			}
		});


		hostMemory = new Button(GEM5Group, SWT.CHECK);
		hostMemory.setText("Number of bytes of host memory used");
		hostMemory.setSelection(dv.getAttrG()[7]);
		hostMemory.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(7, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto simSeconds: "+Arrays.toString(dv.getAttrG()));
			}
		});


		hostSeconds = new Button(GEM5Group, SWT.CHECK);
		hostSeconds.setText("Real time elapsed on the host");
		hostSeconds.setSelection(dv.getAttrG()[8]);
		hostSeconds.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(8, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto simSeconds: "+Arrays.toString(dv.getAttrG()));
			}
		});


		simInsts = new Button(GEM5Group, SWT.CHECK);
		simInsts.setText("Number of instructions simulated");
		simInsts.setSelection(dv.getAttrG()[9]);
		simInsts.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(9, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto simSeconds: "+Arrays.toString(dv.getAttrG()));
			}
		});


		simOps = new Button(GEM5Group, SWT.CHECK);
		simOps.setText("Number of ops (including micro ops) simulated");
		simOps.setSelection(dv.getAttrG()[10]);
		simOps.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(10, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto simSeconds: "+Arrays.toString(dv.getAttrG()));
			}
		});
		
		Group McPGroup = new Group(container, SWT.NONE);
		McPGroup.setText("McPat Details");
		McPGroup.setLayout(new GridLayout(1, true));


		selectAllMc = new Button(McPGroup, SWT.CHECK);
		selectAllMc.setText("Select All McPat");
		/*selectAllMc.setSelection(true);*/
		boolean tt1 = allTrue(dv.getAttrM());
		selectAllMc.setSelection(tt1);
		
		selectAllMc.addSelectionListener(new SelectionAdapter() {


			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				System.out.println(btn.getSelection());
				if (btn.getSelection()) {
					runtime.setSelection(true);
					energy.setSelection(true);
					Area.setSelection(true);
					Peak_Power.setSelection(true);
					Total_Leakage.setSelection(true);
					Peak_Dynamic.setSelection(true);
					Runtime_Dynamic.setSelection(true);
					for(int k=0;k<dv.getAttrM().length;k++){
						dv.setAttrM(k, true);
					}
					
				} else if (!btn.getSelection()) {
					runtime.setSelection(false);
					energy.setSelection(false);
					Area.setSelection(false);
					Peak_Power.setSelection(false);
					Total_Leakage.setSelection(false);
					Peak_Dynamic.setSelection(false);
					Runtime_Dynamic.setSelection(false);
					for(int k=0;k<dv.getAttrM().length;k++){
						dv.setAttrM(k, false);
					}
				}
			}
		});
		
		empty2 = new Label(McPGroup, SWT.NONE);
		 /* ~/COSSIM/gem5/McPat/energy#.txt 
		 * runtime 
		 * energy
		 * 
		 * ~/COSSIM/gem5/McPat/mcpatOutput#.txt 
		 * Area 
		 * Peak Power 
		 * Total Power 
		 * Total Leakage 
		 * Peak Dynamic 
		 * Runtime Dynamic
		 */
		runtime = new Button(McPGroup, SWT.CHECK);
		runtime.setText("Runtime");
		runtime.setSelection(dv.getAttrM()[0]);
		runtime.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrM(0, btn.getSelection());
				
				boolean tt1 = allTrue(dv.getAttrM());
				selectAllMc.setSelection(tt1);
				System.out.println("Mesa sto : "+Arrays.toString(dv.getAttrM()));
			}
		});
		
		energy = new Button(McPGroup, SWT.CHECK);
		energy.setText("Energy");
		energy.setSelection(dv.getAttrM()[1]);
		energy.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrM(1, btn.getSelection());
				
				boolean tt1 = allTrue(dv.getAttrM());
				selectAllMc.setSelection(tt1);
				System.out.println("Mesa sto : "+Arrays.toString(dv.getAttrM()));
			}
		});
		
		Area = new Button(McPGroup, SWT.CHECK);
		Area.setText("Area");
		Area.setSelection(dv.getAttrM()[2]);
		Area.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrM(2, btn.getSelection());
				
				boolean tt1 = allTrue(dv.getAttrM());
				selectAllMc.setSelection(tt1);
				System.out.println("Mesa sto : "+Arrays.toString(dv.getAttrM()));
			}
		});
		
		Peak_Power  = new Button(McPGroup, SWT.CHECK);
		Peak_Power.setText("Peak Power");
		Peak_Power.setSelection(dv.getAttrM()[3]);
		Peak_Power.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrM(3, btn.getSelection());
				
				boolean tt1 = allTrue(dv.getAttrM());
				selectAllMc.setSelection(tt1);
				System.out.println("Mesa sto : "+Arrays.toString(dv.getAttrM()));
			}
		});
		
		
		
		Total_Leakage = new Button(McPGroup, SWT.CHECK);
		Total_Leakage.setText("Total Leakage");
		Total_Leakage.setSelection(dv.getAttrM()[4]);
		Total_Leakage.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrM(4, btn.getSelection());
				
				boolean tt1 = allTrue(dv.getAttrM());
				selectAllMc.setSelection(tt1);
				System.out.println("Mesa sto : "+Arrays.toString(dv.getAttrM()));
			}
		});
		
		Peak_Dynamic = new Button(McPGroup, SWT.CHECK);
		Peak_Dynamic.setText("Peak Dynamic");
		Peak_Dynamic.setSelection(dv.getAttrM()[5]);
		Peak_Dynamic.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrM(5, btn.getSelection());
				
				boolean tt1 = allTrue(dv.getAttrM());
				selectAllMc.setSelection(tt1);
				System.out.println("Mesa sto : "+Arrays.toString(dv.getAttrM()));
			}
		});
		
		Runtime_Dynamic = new Button(McPGroup, SWT.CHECK);
		Runtime_Dynamic.setText("Runtime Dynamic");
		Runtime_Dynamic.setSelection(dv.getAttrM()[6]);
		Runtime_Dynamic.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrM(6, btn.getSelection());
				
				boolean tt1 = allTrue(dv.getAttrM());
				selectAllMc.setSelection(tt1);
				System.out.println("Mesa sto : "+Arrays.toString(dv.getAttrM()));
			}
		});


		return container;
	}
	
	 boolean allTrue(boolean[] array) {
		    for (boolean b : array) {
		        if (!b) {
		           return false;
		        }
		    }
		    return true;
		}
}