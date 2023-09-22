package org.cossim.testresults.actions;


import java.util.LinkedHashMap;
import java.util.Map;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
//import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;


public class myRes extends Dialog {


	protected myRes(Shell parentShell) {
		super(parentShell);
	}
	dialogValues dv = new dialogValues();
	checkExist ce = new checkExist();


	protected Control createDialogArea(Composite parent) {
			
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		container.setLayout(new FillLayout());
		
		if(dv.allNodes1){		//For all nodes
			int[] n1 = new int[dv.nodeNum];
			for(int kk=0;kk<n1.length;kk++){
				n1[kk]=kk;
			}
			createP(n1, container);
		}else{
			if(dv.findRange(dv.s1)!=null && dv.nodeNum>dv.maxParsed){ //For correct exprassion
				createP(dv.findRange(dv.s1),container);
			}else{ 
				MessageDialog.openInformation(
						 parent.getShell(),
						"Not Valid Selection",
						"Please enter a valid node range");
			}
		}
		return container;
	}
	
	void createP(int[] nodeN, Composite container){
		statsP t2 = new statsP();
		energyP t3 = new energyP();
		mcpoutP t4 = new mcpoutP();
		int nodeNum = dv.nodeNum;
		final TabFolder tabFolder = new TabFolder(container, SWT.TOP);
		
		 for (int ii : nodeN) {
			 TabItem ta1 =  new TabItem(tabFolder, SWT.NULL);
			 ta1.setText("node"+ii);
			 Composite composite = new Composite(tabFolder, SWT.NONE);
		     composite.setLayout(new FillLayout());
		     //GEM5 
			 Group GEM5Group = new Group(composite, SWT.NONE);
			 GEM5Group.setText("GEM5 Results for node"+ii);
			 GEM5Group.setLayout(new GridLayout(2, true));
			 Map<String, String[]> temp = new LinkedHashMap<String, String[]>();
			 temp = (Map<String, String[]>) t2.nodeDetails2().get("node"+ii);
			 if(temp.containsKey("simSeconds")){ 
				 Label simSeconds = new Label(GEM5Group, SWT.NONE);
				 simSeconds.setText(temp.get("simSeconds")[1]+": "/*"Number of seconds simulated (Second): "*/);
				 simSeconds.setBounds(10, 10, 300, 30);
				 Label simSecondsV = new Label(GEM5Group, SWT.NONE);
				 simSecondsV.setText(""+temp.get("simSeconds")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("simSeconds")*/);
				 simSecondsV.setBounds(100, 10, 300, 30);
			 }
			 
			 if(temp.get("simTicks")!=null){
				 Label simTicks = new Label(GEM5Group, SWT.NONE);
				 simTicks.setText(temp.get("simTicks")[1]+": "/*"# Number of ticks simulated (Tick): "*/);
				 simTicks.setBounds(10, 10, 300, 30);
				 Label simTicksV = new Label(GEM5Group, SWT.NONE);
				 simTicksV.setText(""+temp.get("simTicks")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("simTicks")*/);
				 simTicksV.setBounds(100, 10, 300, 30);
			 }
				 
			 if(temp.get("finalTick")!=null){
				 Label finalTick = new Label(GEM5Group, SWT.NONE);
				 //Epeidh poly megalo to grafw edw me new line
				 finalTick.setText(/*temp.get("finalTick")[1]*/"Number of ticks from beginning of simulation\n(restored from checkpoints and never reset)"+" (Tick): "/*"Number of ticks from beginning of simulation (finalTick): "*/);
				 finalTick.setBounds(10, 10, 300, 30);
				 Label finalTickV = new Label(GEM5Group, SWT.NONE);
				 finalTickV.setText(""+temp.get("finalTick")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("finalTick")*/);
				 finalTickV.setBounds(100, 10, 300, 30);
			 }
					 
			 if(temp.get("simFreq")!=null){
				 Label simFreq = new Label(GEM5Group, SWT.NONE);
				 simFreq.setText(temp.get("simFreq")[1]+": "/*"The number of ticks per simulated second ((Tick/Second)): "*/);
				 simFreq.setBounds(10, 10, 300, 30);
				 Label simFreqV = new Label(GEM5Group, SWT.NONE);
				 simFreqV.setText(""+temp.get("simFreq")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("simFreq")*/);
				 simFreqV.setBounds(100, 10, 300, 30);
			 }	
			 
			 if(temp.get("hostSeconds")!=null){
				 Label hostSeconds = new Label(GEM5Group, SWT.NONE);
				 hostSeconds.setText(temp.get("hostSeconds")[1]+": "/*"Real time elapsed on the host (Second): "*/);
				 hostSeconds.setBounds(10, 10, 300, 30);
				 Label hostSecondsV = new Label(GEM5Group, SWT.NONE);
				 hostSecondsV.setText(""+temp.get("hostSeconds")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("hostSeconds")*/);
				 hostSecondsV.setBounds(100, 10, 300, 30);
			 }
			 
			 if(temp.get("hostTickRate")!=null){
				 Label hostTickRate = new Label(GEM5Group, SWT.NONE);
				 hostTickRate.setText(temp.get("hostTickRate")[1]+": "/*"The number of ticks simulated per host second (ticks/s) ((Tick/Second)): "*/); 
				 hostTickRate.setBounds(10, 10, 300, 30);
				 Label hostTickRateV = new Label(GEM5Group, SWT.NONE);
				 hostTickRateV.setText(""+temp.get("hostTickRate")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("hostTickRate")*/);
				 hostTickRateV.setBounds(100, 10, 300, 30);
			 }
			 
			 if(temp.get("hostMemory")!=null){
				 Label hostMemory = new Label(GEM5Group, SWT.NONE);
				 hostMemory.setText(temp.get("hostMemory")[1]+": "/*"Number of bytes of host memory used (Byte): "*/);
				 hostMemory.setBounds(10, 10, 300, 30);
				 Label hostMemoryV = new Label(GEM5Group, SWT.NONE);
				 hostMemoryV.setText(""+temp.get("hostMemory")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("hostMemory")*/);
				 hostMemoryV.setBounds(10, 10, 300, 30);
			 }
			 			 
			 if(temp.get("simInsts")!=null){
				 Label simInsts = new Label(GEM5Group, SWT.NONE);
				 simInsts.setText(temp.get("simInsts")[1]+": "/*"Number of instructions simulated (Count): "*/);
				 simInsts.setBounds(10, 10, 300, 30);
				 Label simInstsV = new Label(GEM5Group, SWT.NONE);
				 simInstsV.setText(""+temp.get("simInsts")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("simInsts")*/);
				 simInstsV.setBounds(100, 10, 300, 30);
			 }
			 			 
			 if(temp.get("simOps")!=null){
				 Label simOps = new Label(GEM5Group, SWT.NONE);
				 simOps.setText(temp.get("simOps")[1]+": "/*"Number of ops (including micro ops) simulated (Count): "*/);
				 simOps.setBounds(10, 10, 300, 30);
				 Label simOpsV = new Label(GEM5Group, SWT.NONE);
				 simOpsV.setText(""+temp.get("simOps")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("simOps")*/);
				 simOpsV.setBounds(100, 10, 300, 30);
			 }
			 
			 if(temp.get("hostInstRate")!=null){
				 Label hostInstRate = new Label(GEM5Group, SWT.NONE);
				 hostInstRate.setText(temp.get("hostInstRate")[1]+": "/*"Simulator instruction rate (inst/s) ((Count/Second)): "*/);
				 hostInstRate.setBounds(10, 10, 300, 30);
				 Label hostInstRateV = new Label(GEM5Group, SWT.NONE);
				 hostInstRateV.setText(""+temp.get("hostInstRate")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("hostInstRate")*/);
				 hostInstRateV.setBounds(100, 10, 300, 30);
			 }
						 
			 if(temp.get("hostOpRate")!=null){
				 Label hostOpRate = new Label(GEM5Group, SWT.NONE);
				 hostOpRate.setText(temp.get("hostOpRate")[1]+": "/*"Simulator op (including micro ops) rate (op/s) ((Count/Second)): "*/); 
				 hostOpRate.setBounds(10, 10, 300, 30);
				 Label hostOpRateV = new Label(GEM5Group, SWT.NONE);
				 hostOpRateV.setText(""+temp.get("hostOpRate")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("hostOpRate")*/);
				 hostOpRateV.setBounds(100, 10, 300, 30);
			 }
			
			 
			 			 
			 if(ce.findEnMc()[ii]){
				 //McPat
				 Group McPGroup = new Group(composite, SWT.NONE);
				 McPGroup.setText("McPat Results for node"+ii);
						
				 String[] oo = (String[]) t3.runEn().get("node"+ii);
				
				 Label runtime = new Label(McPGroup, SWT.NONE);
				 runtime.setText("runtime: ");
				 runtime.setBounds(10, 10, 300, 30);
				 Label runtimeV = new Label(McPGroup, SWT.NONE);
				 runtimeV.setText(oo[0]);
				 runtimeV.setBounds(140, 10, 300, 30);
				 
				 Label energy = new Label(McPGroup, SWT.NONE);
				 energy.setText("energy: ");
				 energy.setBounds(10, 30, 300, 30);
				 Label energyV = new Label(McPGroup, SWT.NONE);
				 energyV.setText(oo[1]);
				 energyV.setBounds(140, 30, 300, 30);
				 
				 
				 Label area = new Label(McPGroup, SWT.NONE);
				 area.setText("Area: ");
				 area.setBounds(10, 50, 300, 30);
				 Label areaV = new Label(McPGroup, SWT.NONE);
				 areaV.setText(""+((Map) t4.mcp().get("node"+ii)).get("Area"));
				 areaV.setBounds(140, 50, 300, 30);
				 
				 Label PeakPower = new Label(McPGroup, SWT.NONE);
				 PeakPower.setText("Peak Power: ");
				 PeakPower.setBounds(10, 70, 300, 30);
				 Label PeakPowerV = new Label(McPGroup, SWT.NONE);
				 PeakPowerV.setText(""+((Map) t4.mcp().get("node"+ii)).get("Peak Power"));
				 PeakPowerV.setBounds(140, 70, 300, 30);
				 
				 Label TotalLeakage = new Label(McPGroup, SWT.NONE);
				 TotalLeakage.setText("Total Leakage: ");
				 TotalLeakage.setBounds(10, 90, 300, 30);
				 Label TotalLeakageV = new Label(McPGroup, SWT.NONE);
				 TotalLeakageV.setText(""+((Map) t4.mcp().get("node"+ii)).get("Total Leakage"));
				 TotalLeakageV.setBounds(140, 90, 300, 30);
				 
				 Label PeakDynamic = new Label(McPGroup, SWT.NONE);
				 PeakDynamic.setText("Peak Dynamic: ");
				 PeakDynamic.setBounds(10, 110, 300, 30);
				 Label PeakDynamicV = new Label(McPGroup, SWT.NONE);
				 PeakDynamicV.setText(""+((Map) t4.mcp().get("node"+ii)).get("Peak Dynamic"));
				 PeakDynamicV.setBounds(140, 110, 300, 30);
				 
				 Label RuntimeDynamic = new Label(McPGroup, SWT.NONE);
				 RuntimeDynamic.setText("Runtime Dynamic: ");
				 RuntimeDynamic.setBounds(10, 130, 300, 30);
				 Label RuntimeDynamicV = new Label(McPGroup, SWT.NONE);
				 RuntimeDynamicV.setText(""+((Map) t4.mcp().get("node"+ii)).get("Runtime Dynamic"));
				 RuntimeDynamicV.setBounds(140, 130, 300, 30);
			 }


			
			 
			 ta1.setControl(composite);
		      
		    }
		
		
	}
    @Override
    protected Point getInitialSize() {
            return new Point(1100, 480);
    }
	protected boolean isResizable() {
	    return true;
	}
    @Override
    protected void configureShell(Shell newShell) {
            super.configureShell(newShell);
            newShell.setText("Nodes");
    }
}