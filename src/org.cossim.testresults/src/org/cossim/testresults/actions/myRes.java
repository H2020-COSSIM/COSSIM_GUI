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
			 if(temp.containsKey("sim_seconds")){
				 Label sim_seconds = new Label(GEM5Group, SWT.NONE);
				 sim_seconds.setText(temp.get("sim_seconds")[1]+" (sim_seconds): "/*"Number of seconds simulated (sim_seconds): "*/);
				 sim_seconds.setBounds(10, 10, 300, 30);
				 Label sim_secondsV = new Label(GEM5Group, SWT.NONE);
				 sim_secondsV.setText(""+temp.get("sim_seconds")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("sim_seconds")*/);
				 sim_secondsV.setBounds(100, 10, 300, 30);
			 }
			 
			 if(temp.get("sim_ticks")!=null){
				 Label sim_ticks = new Label(GEM5Group, SWT.NONE);
				 sim_ticks.setText(temp.get("sim_ticks")[1]+" (sim_ticks): "/*"Number of ticks simulated (sim_ticks): "*/);
				 sim_ticks.setBounds(10, 10, 300, 30);
				 Label sim_ticksV = new Label(GEM5Group, SWT.NONE);
				 sim_ticksV.setText(""+temp.get("sim_ticks")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("sim_ticks")*/);
				 sim_ticksV.setBounds(100, 10, 300, 30);
			 }
				 
			 if(temp.get("final_tick")!=null){
				 Label final_tick = new Label(GEM5Group, SWT.NONE);
				 //Epeidh poly megalo to grafw edw me new line
				 final_tick.setText(/*temp.get("final_tick")[1]*/"Number of ticks from beginning of simulation\n(restored from checkpoints and never reset)"+" (final_tick): "/*"Number of ticks from beginning of simulation (final_tick): "*/);
				 final_tick.setBounds(10, 10, 300, 30);
				 Label final_tickV = new Label(GEM5Group, SWT.NONE);
				 final_tickV.setText(""+temp.get("final_tick")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("final_tick")*/);
				 final_tickV.setBounds(100, 10, 300, 30);
			 }
					 
			 if(temp.get("sim_freq")!=null){
				 Label sim_freq = new Label(GEM5Group, SWT.NONE);
				 sim_freq.setText(temp.get("sim_freq")[1]+" (sim_freq): "/*"Frequency of simulated ticks (sim_freq): "*/);
				 sim_freq.setBounds(10, 10, 300, 30);
				 Label sim_freqV = new Label(GEM5Group, SWT.NONE);
				 sim_freqV.setText(""+temp.get("sim_freq")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("sim_freq")*/);
				 sim_freqV.setBounds(100, 10, 300, 30);
			 }
						 
			 if(temp.get("host_inst_rate")!=null){
				 Label host_inst_rate = new Label(GEM5Group, SWT.NONE);
				 host_inst_rate.setText(temp.get("host_inst_rate")[1]+" (host_inst_rate): "/*"Simulator instruction rate (inst/s) (host_inst_rate): "*/);
				 host_inst_rate.setBounds(10, 10, 300, 30);
				 Label host_inst_rateV = new Label(GEM5Group, SWT.NONE);
				 host_inst_rateV.setText(""+temp.get("host_inst_rate")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("host_inst_rate")*/);
				 host_inst_rateV.setBounds(100, 10, 300, 30);
			 }
						 
			 if(temp.get("host_op_rate")!=null){
				 Label host_op_rate = new Label(GEM5Group, SWT.NONE);
				 host_op_rate.setText(temp.get("host_op_rate")[1]+" (host_op_rate): "/*"imulator op (including micro ops) rate (op/s) (host_op_rate): "*/);
				 host_op_rate.setBounds(10, 10, 300, 30);
				 Label host_op_rateV = new Label(GEM5Group, SWT.NONE);
				 host_op_rateV.setText(""+temp.get("host_op_rate")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("host_op_rate")*/);
				 host_op_rateV.setBounds(100, 10, 300, 30);
			 }
						 
			 if(temp.get("host_tick_rate")!=null){
				 Label host_tick_rate = new Label(GEM5Group, SWT.NONE);
				 host_tick_rate.setText(temp.get("host_tick_rate")[1]+" (host_inst_rate): "/*"Simulator tick rate (ticks/s) (host_tick_rate): "*/);
				 host_tick_rate.setBounds(10, 10, 300, 30);
				 Label host_tick_rateV = new Label(GEM5Group, SWT.NONE);
				 host_tick_rateV.setText(""+temp.get("host_tick_rate")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("host_tick_rate")*/);
				 host_tick_rateV.setBounds(100, 10, 300, 30);
			 }
			 			 
			 if(temp.get("host_mem_usage")!=null){
				 Label host_mem_usage = new Label(GEM5Group, SWT.NONE);
				 host_mem_usage.setText(temp.get("host_mem_usage")[1]+" (host_mem_usage): "/*"Number of bytes of host memory used (host_mem_usage): "*/);
				 host_mem_usage.setBounds(10, 10, 300, 30);
				 Label host_mem_usageV = new Label(GEM5Group, SWT.NONE);
				 host_mem_usageV.setText(""+temp.get("host_mem_usage")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("host_mem_usage")*/);
				 host_mem_usageV.setBounds(10, 10, 300, 30);
			 }
			 		 
			 if(temp.get("host_seconds")!=null){
				 Label host_seconds = new Label(GEM5Group, SWT.NONE);
				 host_seconds.setText(temp.get("host_seconds")[1]+" (host_seconds): "/*"Real time elapsed on the host (host_seconds): "*/);
				 host_seconds.setBounds(10, 10, 300, 30);
				 Label host_secondsV = new Label(GEM5Group, SWT.NONE);
				 host_secondsV.setText(""+temp.get("host_seconds")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("host_seconds")*/);
				 host_secondsV.setBounds(100, 10, 300, 30);
			 }
						 
			 if(temp.get("sim_insts")!=null){
				 Label sim_insts = new Label(GEM5Group, SWT.NONE);
				 sim_insts.setText(temp.get("sim_insts")[1]+" (sim_insts): "/*"Number of instructions simulated (sim_insts): "*/);
				 sim_insts.setBounds(10, 10, 300, 30);
				 Label sim_instsV = new Label(GEM5Group, SWT.NONE);
				 sim_instsV.setText(""+temp.get("sim_insts")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("sim_insts")*/);
				 sim_instsV.setBounds(100, 10, 300, 30);
			 }
			 			 
			 if(temp.get("sim_ops")!=null){
				 Label sim_ops = new Label(GEM5Group, SWT.NONE);
				 sim_ops.setText(temp.get("sim_ops")[1]+" (sim_ops): "/*"Number of ops (including micro ops) simulated (sim_ops): "*/);
				 sim_ops.setBounds(10, 10, 300, 30);
				 Label sim_opsV = new Label(GEM5Group, SWT.NONE);
				 sim_opsV.setText(""+temp.get("sim_ops")[0]/*((Map) t2.nodeDetails().get("node"+ii)).get("sim_ops")*/);
				 sim_opsV.setBounds(100, 10, 300, 30);
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
