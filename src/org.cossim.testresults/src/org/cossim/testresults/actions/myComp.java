package org.cossim.testresults.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
//import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;


public class myComp extends Dialog{
	private Label[] Lblnds;
	private Label[] Cnv;
	private Label[] Data;
	private Label expl;
	public myComp(Shell parent) {
		super(parent);
	}
	dialogValues dv = new dialogValues();
	checkExist ce = new checkExist();

	protected Control createDialogArea(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		container.setLayout(new FillLayout());
		
		if(dv.allNodes2){		//For all nodes
			int[] n1 = new int[dv.nodeNum];
			for(int kk=0;kk<n1.length;kk++){
				n1[kk]=kk;
			}
			createP(n1, dv.getAttrG(), dv.getAttrM(), container);
		}else{
			if(dv.findRange(dv.s2)!=null && dv.nodeNum>dv.maxParsed){
				createP(dv.findRange(dv.s2), dv.getAttrG(), dv.getAttrM(),container);
			}else{
				MessageDialog.openInformation(
						parent.getShell(),
						"Not Valid Selection",
						"Please enter a valid node range");
			}
		}
		
		return container;
	}

	void createP(int[] nodeN,boolean[] G, boolean M[], Composite container){
		statsP t2 = new statsP();
		energyP t3 = new energyP();
		mcpoutP t4 = new mcpoutP();
		
		int tabsG = 0;
		int tabsM = 0;
		String[] GEM5Attrs = {"sim_seconds", "sim_ticks", "finals_tick", "sim_freq",
				"host_inst_rate","host_op_rate","host_tick_rate","host_mem_usage","host_seconds","sim_inst","sim_ops"};
		String[] McPatAttrs = {"Runtime","Energy","Area","Peak Power","Total Leakage","Peak Dynamic","Runtime Dynamic"};
		int nodeNum = dv.nodeNum;
		
		for(int i=0;i<G.length;i++){
			if(G[i]){
				tabsG+=1;
			}
		}
		for(int i=0;i<M.length;i++){
			if(M[i]){
				tabsM+=1;
			}
		}
		int ll = tabsG+tabsM;
		int[] totTabs = new int[ll];
		for(int i=0;i<ll;i++){
			totTabs[i] = i;
		}

		String[] tabNamesG = new String[tabsG];
		int inG=0;
		for(int i=0;i<G.length;i++){
			if(G[i]){
				tabNamesG[inG] = GEM5Attrs[i];
				inG+=1;
			}
		}
		String[] tabNamesM = new String[tabsM];
		int inM=0;
		for(int i=0;i<M.length;i++){
			if(M[i]){
				tabNamesM[inM] = McPatAttrs[i];
				inM+=1;
			}
		}

		final String[] tabs = new String[ll];
		for(int i=0;i<ll;i++){
			if(i<tabsG){
				tabs[i] = tabNamesG[i];
			}else{
				tabs[i] = tabNamesM[i-tabsG];
			}
		}

		Lblnds = new Label[nodeN.length];
		Cnv = new Label[nodeN.length];
		Data = new Label[nodeN.length];
		
		final TabFolder tabFolder = new TabFolder(container, SWT.TOP);
		final Color bars = new Color(tabFolder.getDisplay(),0xEE,0x00,0x00);
		String[] Lab = {"Number of seconds simulated","Number of ticks simulated",
				"Number of ticks from beginning of simulation (restored from checkpoints and never reset)",
				"Frequency of simulated ticks","Simulator instruction rate (inst/s)","Simulator op (including micro ops) rate (op/s)",
				"Simulator tick rate (ticks/s)","Number of bytes of host memory used","Real time elapsed on the host",
				"Number of instructions simulated","Number of ops (including micro ops) simulated","Runtime (sec)","Energy (mJ)","Area (mm^2)",
				"Peak Power (W)", "Total Leakage (W)","Peak Dynamic (W)", "Runtime Dynamic (W)"};
		
		
		
		
		Map<String, String> Lab1 = new LinkedHashMap<String, String>();
			Lab1.put("sim_seconds", Lab[0]);
			Lab1.put("sim_ticks", Lab[1]);
			Lab1.put("finals_tick", Lab[2]);
			Lab1.put("sim_freq", Lab[3]);
			Lab1.put("host_inst_rate", Lab[4]);
			Lab1.put("host_op_rate", Lab[5]);
			Lab1.put("host_tick_rate", Lab[6]);
			Lab1.put("host_mem_usage", Lab[7]);
			Lab1.put("host_seconds", Lab[8]);
			Lab1.put("sim_inst", Lab[9]);
			Lab1.put("sim_ops", Lab[10]);
			Lab1.put("Runtime", Lab[11]);
			Lab1.put("Energy", Lab[12]);
			Lab1.put("Area", Lab[13]);
			Lab1.put("Peak Power", Lab[14]);
			Lab1.put("Total Leakage", Lab[15]);
			Lab1.put("Peak Dynamic", Lab[16]);
			Lab1.put("Runtime Dynamic", Lab[17]);
		
		
		
		for (int ii : totTabs) {
			 TabItem ta1 =  new TabItem(tabFolder, SWT.NULL);
			 ta1.setText(tabs[ii]);
			 ScrolledComposite sc = new ScrolledComposite(tabFolder, SWT.BORDER	| SWT.H_SCROLL | SWT.V_SCROLL);
			 Composite cont = new Composite(sc, SWT.BORDER);
				GridLayout contLayout = new GridLayout();
				contLayout.numColumns = 6;
				contLayout.makeColumnsEqualWidth = false;
				cont.setLayout(contLayout);
				cont.setBackground(cont.getShell().getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

				double[] sim_seconds = new double[nodeN.length];  
				double[] sim_ticks = new double[nodeN.length];
				double[] final_tick = new double[nodeN.length];
				double[] sim_freq = new double[nodeN.length];
				double[] host_inst_rate = new double[nodeN.length];
				double[] host_op_rate = new double[nodeN.length];
				double[] host_tick_rate = new double[nodeN.length];
				double[] host_mem_usage = new double[nodeN.length];
				double[] host_seconds = new double[nodeN.length];
				double[] sim_insts = new double[nodeN.length];
				double[] sim_ops = new double[nodeN.length];
				double[] runtime = new double[nodeN.length];
				double[] energy = new double[nodeN.length];
				double[] area = new double[nodeN.length];
				double[] peak_power = new double[nodeN.length];
				double[] total_leakage = new double[nodeN.length];
				double[] peak_dynamic = new double[nodeN.length];
				double[] runtime_dynamic = new double[nodeN.length];
				 Map<String, String[]> tempV = new LinkedHashMap<String, String[]>();
				int index1 = 0;
				for(int nn : nodeN){
					tempV = (Map<String, String[]>) t2.nodeDetails2().get("node"+nn);
					 sim_seconds[index1] =  Double.parseDouble(tempV.get("sim_seconds")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("sim_seconds")*/);  
					 sim_ticks[index1] =  Double.parseDouble(tempV.get("sim_ticks")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("sim_ticks")*/);
					 final_tick[index1] = Double.parseDouble(tempV.get("final_tick")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("final_tick")*/);
					 sim_freq[index1] = Double.parseDouble(tempV.get("sim_freq")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("sim_freq")*/);
					 host_inst_rate[index1] = Double.parseDouble(tempV.get("host_inst_rate")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("host_inst_rate")*/);
					 host_op_rate[index1] = Double.parseDouble(tempV.get("host_op_rate")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("host_op_rate")*/);
					 host_tick_rate[index1] = Double.parseDouble(tempV.get("host_tick_rate")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("host_tick_rate")*/);
					 host_mem_usage[index1] = Double.parseDouble(tempV.get("host_mem_usage")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("host_mem_usage")*/);
					 host_seconds[index1] = Double.parseDouble(tempV.get("host_seconds")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("host_seconds")*/);
					 sim_insts[index1] = Double.parseDouble(tempV.get("sim_insts")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("sim_insts")*/);
					 sim_ops[index1] = Double.parseDouble(tempV.get("sim_ops")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("sim_ops")*/);
					 if(ce.findEnMc()[nn]){
						 
						 String[] oo = (String[]) t3.runEn().get("node"+nn);
						 String[] en = new String[oo.length];
						 for(int ss=0;ss<oo.length;ss++){
							 String[] temp = oo[ss].split(" ");
							 en[ss]=temp[0];
						 }
						 runtime[index1] = Double.parseDouble(en[0]);
						 energy[index1] = Double.parseDouble(en[1]);
						 
						
						 area[index1] = Double.parseDouble(((String) ((Map) t4.mcp().get("node"+nn)).get("Area")).split(" ")[0]);
						 peak_power[index1] = Double.parseDouble(((String) ((Map) t4.mcp().get("node"+nn)).get("Peak Power")).split(" ")[0]);
						 total_leakage[index1] = Double.parseDouble(((String) ((Map) t4.mcp().get("node"+nn)).get("Total Leakage")).split(" ")[0]);
						 peak_dynamic[index1] = Double.parseDouble(((String) ((Map) t4.mcp().get("node"+nn)).get("Peak Dynamic")).split(" ")[0]);
						 runtime_dynamic[index1] = Double.parseDouble(((String) ((Map) t4.mcp().get("node"+nn)).get("Runtime Dynamic")).split(" ")[0]);
					 }else {
						 runtime[index1] = -100;
						 energy[index1] = -100;
						 area[index1] = -100;
						 peak_power[index1] = -100;
						 total_leakage[index1] = -100;
						 peak_dynamic[index1] = -100;
						 runtime_dynamic[index1] = -100;
					 }
					
						index1++;
				}

				int res = 850;  			// To mege8os thw mparas
				double sim_secondsM = maxD(sim_seconds)/res;  
				double sim_ticksM = maxD(sim_ticks)/res;
				double final_tickM = maxD(final_tick)/res;
				double sim_freqM = maxD(sim_freq)/res;
				double host_inst_rateM = maxD(host_inst_rate)/res;
				double host_op_rateM = maxD(host_op_rate)/res;
				double host_tick_rateM = maxD(host_tick_rate)/res;
				double host_mem_usageM = maxD(host_mem_usage)/res;
				double host_secondsM = maxD(host_seconds)/res;
				double sim_instsM = maxD(sim_insts)/res;
				double sim_opsM = maxD(sim_ops)/res;
				double runtimeM = maxD(runtime)/res;
				double energyM = maxD(energy)/res;
				double areaM = maxD(area)/res;
				double peak_powerM = maxD(peak_power)/res;
				double total_leakageM = maxD(total_leakage)/res;
				double peak_dynamicM = maxD(peak_dynamic)/res;
				double runtime_dynamicM = maxD(runtime_dynamic)/res;
	
				double[] sim_secondsN = new double[nodeN.length];  
				double[] sim_ticksN = new double[nodeN.length];
				double[] final_tickN = new double[nodeN.length];
				double[] sim_freqN = new double[nodeN.length];
				double[] host_inst_rateN = new double[nodeN.length];
				double[] host_op_rateN = new double[nodeN.length];
				double[] host_tick_rateN = new double[nodeN.length];
				double[] host_mem_usageN = new double[nodeN.length];
				double[] host_secondsN = new double[nodeN.length];
				double[] sim_instsN = new double[nodeN.length];
				double[] sim_opsN = new double[nodeN.length];
				double[] runtimeN = new double[nodeN.length];
				double[] energyN = new double[nodeN.length];
				double[] areaN = new double[nodeN.length];
				double[] peak_powerN = new double[nodeN.length];
				double[] total_leakageN = new double[nodeN.length];
				double[] peak_dynamicN = new double[nodeN.length];
				double[] runtime_dynamicN = new double[nodeN.length];
				
				sim_secondsN = divM(sim_seconds, sim_secondsM);
				sim_ticksN = divM(sim_ticks, sim_ticksM);
				final_tickN = divM(final_tick, final_tickM);
				sim_freqN = divM(sim_freq, sim_freqM);
				host_inst_rateN = divM(host_inst_rate, host_inst_rateM);
				host_op_rateN = divM(host_op_rate, host_op_rateM);
				host_tick_rateN = divM(host_tick_rate, host_tick_rateM);
				host_mem_usageN = divM(host_mem_usage, host_mem_usageM);
				host_secondsN = divM(host_seconds, host_secondsM);
				sim_instsN = divM(sim_insts, sim_instsM);
				sim_opsN = divM(sim_ops, sim_opsM);
				runtimeN = divM(runtime, runtimeM);
				energyN = divM(energy, energyM);
				areaN = divM(area, areaM);
				peak_powerN = divM(peak_power, peak_powerM);
				total_leakageN = divM(total_leakage, total_leakageM);
				peak_dynamicN = divM(peak_dynamic, peak_dynamicM);
				runtime_dynamicN = divM(runtime_dynamic, runtime_dynamicM);
				
				final Map<String, double[]> attrV = new LinkedHashMap<String, double[]>();
				attrV.put("sim_seconds", sim_secondsN);
				attrV.put("sim_ticks", sim_ticksN);
				attrV.put("finals_tick", final_tickN);
				attrV.put("sim_freq", sim_freqN);
				attrV.put("host_inst_rate", host_inst_rateN);
				attrV.put("host_op_rate", host_op_rateN);
				attrV.put("host_tick_rate", host_tick_rateN);
				attrV.put("host_mem_usage", host_mem_usageN);
				attrV.put("host_seconds", host_secondsN);
				attrV.put("sim_inst", sim_instsN);
				attrV.put("sim_ops", sim_opsN);
				attrV.put("Runtime", runtimeN);
				attrV.put("Energy", energyN);
				attrV.put("Area", areaN);
				attrV.put("Peak Power", peak_powerN);
				attrV.put("Total Leakage", total_leakageN);
				attrV.put("Peak Dynamic", peak_dynamicN);
				attrV.put("Runtime Dynamic", runtime_dynamicN);
				
				final Map<String, double[]> attrV2 = new LinkedHashMap<String, double[]>();
				attrV2.put("sim_seconds", sim_seconds);
				attrV2.put("sim_ticks", sim_ticks);
				attrV2.put("finals_tick", final_tick);
				attrV2.put("sim_freq", sim_freq);
				attrV2.put("host_inst_rate", host_inst_rate);
				attrV2.put("host_op_rate", host_op_rate);
				attrV2.put("host_tick_rate", host_tick_rate);
				attrV2.put("host_mem_usage", host_mem_usage);
				attrV2.put("host_seconds", host_seconds);
				attrV2.put("sim_inst", sim_insts);
				attrV2.put("sim_ops", sim_ops);
				attrV2.put("Runtime", runtime);
				attrV2.put("Energy", energy);
				attrV2.put("Area", area);
				attrV2.put("Peak Power", peak_power);
				attrV2.put("Total Leakage", total_leakage);
				attrV2.put("Peak Dynamic", peak_dynamic);
				attrV2.put("Runtime Dynamic", runtime_dynamic);

				expl = new Label(cont, SWT.NONE);
				expl.setLayoutData(new GridData(SWT.FILL,	SWT.BEGINNING, false, false, 6, 1));
				Font lbFont = new Font( expl.getDisplay(), new FontData( "Arial", 18, SWT.BOLD ) );
				expl.setFont(lbFont);
				expl.setText(Lab1.get(tabs[ii]));
				
				int[] tt = new int[nodeN.length];
				int index2 = 0;
			
				for(int nn : nodeN){
					final int[] yy = tt;
					final int kk = index2;
					final int iii = ii;
					
					Lblnds[index2] = new Label(cont, SWT.NONE);
					Lblnds[index2].setLayoutData(new GridData(SWT.FILL,	SWT.BEGINNING, false, false, 1, 1));
					Lblnds[index2].setText("node"+nodeN[index2]);
					Cnv[index2] = new Label(cont,SWT.NULL);
					Cnv[index2].setLayoutData(new GridData(SWT.FILL,SWT.BEGINNING, true, false, 4, 1));
					Cnv[index2].setBounds(100, 100, 10, 10);
					Cnv[index2].addPaintListener(new PaintListener() {
					    public void paintControl(PaintEvent e) {
				    	   e.gc.setBackground(bars);
					    	if(attrV.get(tabs[iii])[kk]>=0){
						       e.gc.fillRectangle(20, 10, (int) attrV.get(tabs[iii])[kk], 20);
					       }else{
					    	   e.gc.fillRectangle(20, 10, 0, 20);
					       }
					       
					        
					    }
					});

					Data[index2] = new Label(cont, SWT.NULL);
					if(attrV.get(tabs[iii])[kk]>=0){
						Data[kk].setText(""+attrV2.get(tabs[ii])[kk]);
				       }else{
				    	   Data[index2].setText("No Data..");

				       }
					index2++;
				}
				sc.setContent(cont);
				sc.setExpandVertical(true);
				sc.setExpandHorizontal(true);
				sc.setMinSize(cont.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				ta1.setControl(sc);
		}
		
	}
	
	double maxD(double[] ar){
		double a = ar[0];
		
		for (int c = 1; c < ar.length; c++)
		{
		     if (ar[c] > a)
		     {
		      a = ar[c];
		     }
		}
		return a;
	}
	
	double[] divM(double[] val, double div){
		double[] e = new double[val.length];
		for(int u=0;u<val.length;u++){
			if(val[u]>0){
				e[u] = val[u]/div;				
			}else{
				e[u] = -100;
			}
			
		}
		return e;
	}
	
	int[] d2in(double[] rr){
		int[] o1 = new int[rr.length];
		
		for(int h=0;h<rr.length;h++){
			 o1[h] = (int) rr[h];
		}
		
		return o1;
	}
	
	protected boolean isResizable() {
	    return true;
	}
	   @Override
	    protected Point getInitialSize() {
	            return new Point(1100, 70*dv.nodeNum);
	    }

}
