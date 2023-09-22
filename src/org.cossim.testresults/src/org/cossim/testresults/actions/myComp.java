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
		String[] GEM5Attrs = {"simSeconds", "simTicks", "finalTick", "simFreq",
				"hostInstRate","hostOpRate","hostTickRate","hostMemory","hostSeconds","simInsts","simOps"};
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
			Lab1.put("simSeconds", Lab[0]);
			Lab1.put("simTicks", Lab[1]);
			Lab1.put("finalTick", Lab[2]); 
			Lab1.put("simFreq", Lab[3]);
			Lab1.put("hostInstRate", Lab[4]);
			Lab1.put("hostOpRate", Lab[5]);
			Lab1.put("hostTickRate", Lab[6]);
			Lab1.put("hostMemory", Lab[7]);
			Lab1.put("hostSeconds", Lab[8]);
			Lab1.put("simInsts", Lab[9]); 
			Lab1.put("simOps", Lab[10]);
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


				double[] simSeconds = new double[nodeN.length];  
				double[] simTicks = new double[nodeN.length];
				double[] finalTick = new double[nodeN.length];
				double[] simFreq = new double[nodeN.length];
				double[] hostInstRate = new double[nodeN.length];
				double[] hostOpRate = new double[nodeN.length];
				double[] hostTickRate = new double[nodeN.length];
				double[] hostMemory = new double[nodeN.length];
				double[] hostSeconds = new double[nodeN.length];
				double[] simInsts = new double[nodeN.length];
				double[] simOps = new double[nodeN.length];
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
					 simSeconds[index1] =  Double.parseDouble(tempV.get("simSeconds")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("simSeconds")*/);  
					 simTicks[index1] =  Double.parseDouble(tempV.get("simTicks")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("simTicks")*/);
					 finalTick[index1] = Double.parseDouble(tempV.get("finalTick")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("finalTick")*/);
					 simFreq[index1] = Double.parseDouble(tempV.get("simFreq")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("simFreq")*/);
					 hostInstRate[index1] = Double.parseDouble(tempV.get("hostInstRate")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("hostInstRate")*/);
					 hostOpRate[index1] = Double.parseDouble(tempV.get("hostOpRate")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("hostOpRate")*/);
					 hostTickRate[index1] = Double.parseDouble(tempV.get("hostTickRate")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("hostTickRate")*/);
					 hostMemory[index1] = Double.parseDouble(tempV.get("hostMemory")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("hostMemory")*/);
					 hostSeconds[index1] = Double.parseDouble(tempV.get("hostSeconds")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("hostSeconds")*/);
					 simInsts[index1] = Double.parseDouble(tempV.get("simInsts")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("simInsts")*/);
					 simOps[index1] = Double.parseDouble(tempV.get("simOps")[0]/*(String) ((Map) t2.nodeDetails().get("node"+nn)).get("simOps")*/);
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
				double simSecondsM = maxD(simSeconds)/res;  
				double simTicksM = maxD(simTicks)/res;
				double finalTickM = maxD(finalTick)/res;
				double simFreqM = maxD(simFreq)/res;
				double hostInstRateM = maxD(hostInstRate)/res;
				double hostOpRateM = maxD(hostOpRate)/res;
				double hostTickRateM = maxD(hostTickRate)/res;
				double hostMemoryM = maxD(hostMemory)/res;
				double hostSecondsM = maxD(hostSeconds)/res;
				double simInstsM = maxD(simInsts)/res;
				double simOpsM = maxD(simOps)/res;
				double runtimeM = maxD(runtime)/res;
				double energyM = maxD(energy)/res;
				double areaM = maxD(area)/res;
				double peak_powerM = maxD(peak_power)/res;
				double total_leakageM = maxD(total_leakage)/res;
				double peak_dynamicM = maxD(peak_dynamic)/res;
				double runtime_dynamicM = maxD(runtime_dynamic)/res;
	
				double[] simSecondsN = new double[nodeN.length];  
				double[] simTicksN = new double[nodeN.length];
				double[] finalTickN = new double[nodeN.length];
				double[] simFreqN = new double[nodeN.length];
				double[] hostInstRateN = new double[nodeN.length];
				double[] hostOpRateN = new double[nodeN.length];
				double[] hostTickRateN = new double[nodeN.length];
				double[] hostMemoryN = new double[nodeN.length];
				double[] hostSecondsN = new double[nodeN.length];
				double[] simInstsN = new double[nodeN.length];
				double[] simOpsN = new double[nodeN.length];
				double[] runtimeN = new double[nodeN.length];
				double[] energyN = new double[nodeN.length];
				double[] areaN = new double[nodeN.length];
				double[] peak_powerN = new double[nodeN.length];
				double[] total_leakageN = new double[nodeN.length];
				double[] peak_dynamicN = new double[nodeN.length];
				double[] runtime_dynamicN = new double[nodeN.length];
				
				simSecondsN = divM(simSeconds, simSecondsM);
				simTicksN = divM(simTicks, simTicksM);
				finalTickN = divM(finalTick, finalTickM);
				simFreqN = divM(simFreq, simFreqM);
				hostInstRateN = divM(hostInstRate, hostInstRateM);
				hostOpRateN = divM(hostOpRate, hostOpRateM);
				hostTickRateN = divM(hostTickRate, hostTickRateM);
				hostMemoryN = divM(hostMemory, hostMemoryM);
				hostSecondsN = divM(hostSeconds, hostSecondsM);
				simInstsN = divM(simInsts, simInstsM);
				simOpsN = divM(simOps, simOpsM);
				runtimeN = divM(runtime, runtimeM);
				energyN = divM(energy, energyM);
				areaN = divM(area, areaM);
				peak_powerN = divM(peak_power, peak_powerM);
				total_leakageN = divM(total_leakage, total_leakageM);
				peak_dynamicN = divM(peak_dynamic, peak_dynamicM);
				runtime_dynamicN = divM(runtime_dynamic, runtime_dynamicM);
				
				final Map<String, double[]> attrV = new LinkedHashMap<String, double[]>();
				attrV.put("simSeconds", simSecondsN);
				attrV.put("simTicks", simTicksN);
				attrV.put("finalTick", finalTickN); 
				attrV.put("simFreq", simFreqN);
				attrV.put("hostInstRate", hostInstRateN);
				attrV.put("hostOpRate", hostOpRateN);
				attrV.put("hostTickRate", hostTickRateN);
				attrV.put("hostMemory", hostMemoryN);
				attrV.put("hostSeconds", hostSecondsN);
				attrV.put("simInsts", simInstsN); 
				attrV.put("simOps", simOpsN);
				attrV.put("Runtime", runtimeN);
				attrV.put("Energy", energyN);
				attrV.put("Area", areaN);
				attrV.put("Peak Power", peak_powerN);
				attrV.put("Total Leakage", total_leakageN);
				attrV.put("Peak Dynamic", peak_dynamicN);
				attrV.put("Runtime Dynamic", runtime_dynamicN);
				
				final Map<String, double[]> attrV2 = new LinkedHashMap<String, double[]>();
				attrV2.put("simSeconds", simSeconds);
				attrV2.put("simTicks", simTicks);
				attrV2.put("finalTick", finalTick); 
				attrV2.put("simFreq", simFreq);
				attrV2.put("hostInstRate", hostInstRate);
				attrV2.put("hostOpRate", hostOpRate);
				attrV2.put("hostTickRate", hostTickRate);
				attrV2.put("hostMemory", hostMemory);
				attrV2.put("hostSeconds", hostSeconds);
				attrV2.put("simInsts", simInsts); 
				attrV2.put("simOps", simOps);
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