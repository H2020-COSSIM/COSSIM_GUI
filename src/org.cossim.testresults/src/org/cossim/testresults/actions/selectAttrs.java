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
	 * sim_seconds = Number of seconds simulated
	 * sim_ticks = Number of ticks simulated 
	 * final_tick = Number of ticks from beginning of simulation (restored from checkpoints and never reset)
	 * sim_freq = Frequency of simulated ticks host_inst_rate = Simulator instruction rate (inst/s) 
	 * host_op_rate = Simulator op (including microops) rate (op/s) 
	 * host_tick_rate = Simulator tick rate (ticks/s)
	 * host_mem_usage = Number of bytes of host memory used 
	 * host_seconds = Real time elapsed on the host 
	 * sim_insts = Number of instructions simulated
	 * sim_ops = Number of ops (including micro ops) simulated
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
	private Button sim_seconds;
	private Button sim_ticks;
	private Button final_tick;
	private Button sim_freq;
	private Button host_inst_rate;
	private Button host_op_rate;
	private Button host_tick_rate;
	private Button host_mem_usage;
	private Button host_seconds;
	private Button sim_insts;
	private Button sim_ops;

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
					sim_seconds.setSelection(true);
					sim_ticks.setSelection(true);
					final_tick.setSelection(true);
					sim_freq.setSelection(true);
					host_inst_rate.setSelection(true);
					host_op_rate.setSelection(true);
					host_tick_rate.setSelection(true);
					host_mem_usage.setSelection(true);
					host_seconds.setSelection(true);
					sim_insts.setSelection(true);
					sim_ops.setSelection(true);
					for(int k=0;k<dv.getAttrG().length;k++){
						dv.setAttrG(k, true);
					}
					System.out.println("Mesa sto selectAttrs1: "+Arrays.toString(dv.getAttrG()));
				} else if (!btn.getSelection()) {
					sim_seconds.setSelection(false);
					sim_ticks.setSelection(false);
					final_tick.setSelection(false);
					sim_freq.setSelection(false);
					host_inst_rate.setSelection(false);
					host_op_rate.setSelection(false);
					host_tick_rate.setSelection(false);
					host_mem_usage.setSelection(false);
					host_seconds.setSelection(false);
					sim_insts.setSelection(false);
					sim_ops.setSelection(false);
					for(int k=0;k<dv.getAttrG().length;k++){
						dv.setAttrG(k, false);
					}
					System.out.println("Mesa sto selectAttrs2: "+Arrays.toString(dv.getAttrG()));
				}
			}
		});

		empty1 = new Label(GEM5Group, SWT.NONE);

		sim_seconds = new Button(GEM5Group, SWT.CHECK);
		sim_seconds.setText("Number of seconds simulated");
		sim_seconds.setSelection(dv.getAttrG()[0]);
		sim_seconds.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(0, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto sim_seconds: "+Arrays.toString(dv.getAttrG()));
			}
		});

		
		sim_ticks = new Button(GEM5Group, SWT.CHECK);
		sim_ticks.setText("Number of ticks simulated");
		sim_ticks.setSelection(dv.getAttrG()[1]);
		sim_ticks.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(1, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto sim_seconds: "+Arrays.toString(dv.getAttrG()));
			}
		});

		final_tick = new Button(GEM5Group, SWT.CHECK);
		final_tick.setText("Number of ticks from beginning of simulation");
		final_tick.setSelection(dv.getAttrG()[2]);
		final_tick.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(2, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto sim_seconds: "+Arrays.toString(dv.getAttrG()));
			}
		});

		sim_freq = new Button(GEM5Group, SWT.CHECK);
		sim_freq.setText("Frequency of simulated ticks");
		sim_freq.setSelection(dv.getAttrG()[3]);
		sim_freq.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(3, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto sim_seconds: "+Arrays.toString(dv.getAttrG()));
			}
		});

		host_inst_rate = new Button(GEM5Group, SWT.CHECK);
		host_inst_rate.setText("Simulator instruction rate (inst/s)");
		host_inst_rate.setSelection(dv.getAttrG()[4]);
		host_inst_rate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(4, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto sim_seconds: "+Arrays.toString(dv.getAttrG()));
			}
		});

		host_op_rate = new Button(GEM5Group, SWT.CHECK);
		host_op_rate.setText("Simulator op (including micro ops) rate (op/s)");
		host_op_rate.setSelection(dv.getAttrG()[5]);
		host_op_rate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(5, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto sim_seconds: "+Arrays.toString(dv.getAttrG()));
			}
		});


		host_tick_rate = new Button(GEM5Group, SWT.CHECK);
		host_tick_rate.setText("Simulator tick rate (ticks/s)");
		host_tick_rate.setSelection(dv.getAttrG()[6]);
		host_tick_rate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(6, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto sim_seconds: "+Arrays.toString(dv.getAttrG()));
			}
		});

		host_mem_usage = new Button(GEM5Group, SWT.CHECK);
		host_mem_usage.setText("Number of bytes of host memory used");
		host_mem_usage.setSelection(dv.getAttrG()[7]);
		host_mem_usage.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(7, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto sim_seconds: "+Arrays.toString(dv.getAttrG()));
			}
		});

		host_seconds = new Button(GEM5Group, SWT.CHECK);
		host_seconds.setText("Real time elapsed on the host");
		host_seconds.setSelection(dv.getAttrG()[8]);
		host_seconds.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(8, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto sim_seconds: "+Arrays.toString(dv.getAttrG()));
			}
		});

		sim_insts = new Button(GEM5Group, SWT.CHECK);
		sim_insts.setText("Number of instructions simulated");
		sim_insts.setSelection(dv.getAttrG()[9]);
		sim_insts.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(9, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto sim_seconds: "+Arrays.toString(dv.getAttrG()));
			}
		});

		sim_ops = new Button(GEM5Group, SWT.CHECK);
		sim_ops.setText("Number of ops (including micro ops) simulated");
		sim_ops.setSelection(dv.getAttrG()[10]);
		sim_ops.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				dv.setAttrG(10, btn.getSelection());
				
				boolean tt = allTrue(dv.getAttrG());
				selectAllGEM5.setSelection(tt);
				System.out.println("Mesa sto sim_seconds: "+Arrays.toString(dv.getAttrG()));
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
