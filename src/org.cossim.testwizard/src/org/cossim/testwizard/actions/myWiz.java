package org.cossim.testwizard.actions;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;

public class myWiz extends Wizard {
	private wizVal values = new wizVal();
	private IWizardPage page0 = new wizPage0(this.values);
	private IWizardPage page1 = new wizPage1(this.values);
	private IWizardPage page2 = new wizPage2(this.values);
	private IWizardPage page3 = new wizPage3(this.values);
	private IWizardPage page4 = new wizPage4(this.values);
	private IWizardPage page4P = new wizPage4P(this.values);
	private IWizardPage page4S = new wizPage4S(this.values);
	private IWizardPage page5 = new wizPage5(this.values);

	boolean countP2 = false;
	boolean countP3 = false;
	boolean countP4 = false;
	boolean countP4P = false;
	String[][] conf;
	//boolean countP5 = false;
	/*String[] proc3;
	String[] rem3;
	String[] ben3;*/
		
	
	@Override
	public void addPages() {
		addPage(page0);
		setForcePreviousAndNextButtons(true);
	}

	@Override
	public IWizardPage getNextPage(IWizardPage currentPage) {
		//wizVal values = new wizVal();
		if (currentPage == page0) {
			if(values.Sel1 == true){
				addPage(page1);	
				return page1;
			}else if(values.Sel2 == true){
				addPage(page4P);
				if(countP4P){
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
						((wizPage4P) page4P).createp4P(((wizPage4P) page4P).node(((wizPage4P) page4P).getLines(values.confFromFile)), values.sc_4, values.grp_2_4, values.grp_3_4, values.grp_4_4, values.container_4, values.container1_4, values.sccontainer_4,values.container2_4);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				countP4P = true;
				return page4P;
			}else if(values.Sel3 == true){
				
				addPage(page4S);
				
			/*	String[][] sampleConf ={{"ARM32","vmlinux.aarch32.ll_20131205.0-gem5"," linux-aarch32-ael.img","512", "10", "ms", "10", "ms", "VExpress_EMM", "vexpress.aarch32.ll_20131205.0-gem5.1cpu.dtb", "null", "null", "null", "null"},
						{"ARM64", "vmlinux.aarch64.20140821", "aarch64-ubuntu-natty-headless.img", "1024", "20", "ms", "20", "us", "VExpress_EMM64", "vexpress.aarch64.20140821.dtb", "null", "null", "null", "null"},
						{"x86", "x86_64-vmlinux-3.2.24-smp", "x86_64root.img", "2048", "30", "us", "30", "us", "null", "null", "null", "null", "null", "null"}};
						*/
				return page4S;
			}
			
			
		}
		if (currentPage == page1) {
			page2.setTitle("Cluster Limits and Individual Details");
			page2.setDescription("Set the type of processor, start and end node and the additional details"
					+ " of each of the "
					+ values.gettotCl() + " clusters");
				
			
			if(((values.prevRefNodesNum != values.gettotNodes()) || (values.prevRefClNum != values.gettotCl()))){
				values.NextOnP2 = new boolean[values.gettotCl()][2];
				values.proc2 = new String[values.gettotCl()];
				values.rem2 = new String[values.gettotCl()];
				values.ben2 = new String[values.gettotCl()];
				values.pow2 = new String[values.gettotCl()];
				values.proc3 = new String[values.gettotCl()];
				values.rem3 = new String[values.gettotCl()];
				values.ben3 = new String[values.gettotCl()];
				values.pow3 = new String[values.gettotCl()];
			}
			//for the refresh of wizPage2 after back and next;
			if(!countP2){
				values.prevRefNodesNum = values.gettotNodes();
				values.prevRefClNum = values.gettotCl();
								
				values.NextOnP2 = new boolean[values.gettotCl()][2];
				for(int n=0;n<values.gettotCl();n++){
					values.NextOnP2[n][0]=false;
					values.NextOnP2[n][1]=false;
				}
				values.NextOnP2[values.gettotCl()-1][0] = true;
				
			}
			//If something has changed
			if(countP2 && ((values.prevRefNodesNum != values.gettotNodes()) || (values.prevRefClNum != values.gettotCl()))){
				page2.setTitle("Clusters of same nodes");
				page2.setDescription("Set the type of processor, start and end node and the additional details"
					+ " of each of the "
					+ values.gettotCl() + " clusters");

					for(Control control : values.composite_2.getChildren()){
	            		control.dispose();
	                  		
	            	}
	            	values.prevRefNodesNum = values.gettotNodes();
	            	values.prevRefClNum = values.gettotCl();
	            	for(int n=0;n<values.gettotCl();n++){
	    				values.NextOnP2[n][0]=false;
	    				values.NextOnP2[n][1]=false;
	    			}
	    			values.NextOnP2[values.gettotCl()-1][0] = true;
	    			
	            	values.composite_2.setEnabled(true);
	            	((wizPage2) page2).createp2(values.composite_2, values.sc2, values.container2);
						
										  
			}

			countP2 = true;
			
			addPage(page2);			
				
			return page2;
		}
		
		if (currentPage == page2) {
			addPage(page3);
	
			if(((values.prevRefNodesNum3 != values.gettotNodes()) || (values.prevRefClNum3 != values.gettotCl()))){
				values.NextOnP3 = new boolean[values.gettotCl()][14];
			}
				if(!countP3){
					
					values.prevRefNodesNum3 = values.gettotNodes();
					values.prevRefClNum3 = values.gettotCl();
					
					page3.setDescription("Set the configuration.");
					//Values (Before back)
					for(int l=0;l<values.gettotCl();l++){
						int	m=l+1;
						String ss = "cl" + m;
						values.proc3[l]=values.map.get(ss)[4];
						values.rem3[l]=values.map.get(ss)[2];
						values.ben3[l]=values.map.get(ss)[3];
						values.pow3[l]=values.map.get(ss)[20];
						}

					for(int l=0;l<values.gettotCl();l++){
						int m = l+1;
						String ss = "cl" + m;
						values.proc2[l]=values.map.get(ss)[4];
						values.rem2[l]=values.map.get(ss)[2];
						values.ben2[l]=values.map.get(ss)[3];
						values.pow2[l]=values.map.get(ss)[20];
					}		
					values.NextOnP3 = new boolean[values.gettotCl()][14];
					for(int n=0;n<values.gettotCl();n++){
						for(int y=0;y<14;y++){
							values.NextOnP3[n][y]=false;
						}
					}
				}

				
				for(int l=0;l<values.gettotCl();l++){
					int	m=l+1;
					String ss = "cl" + m;
					values.proc2[l]=values.map.get(ss)[4];
					values.rem2[l]=values.map.get(ss)[2];
					values.ben2[l]=values.map.get(ss)[3];
					values.pow2[l]=values.map.get(ss)[20];
					}
				
				
				if(countP3 && (hasChanged(values.proc2, values.proc3) || hasChanged(values.rem2, values.rem3) || hasChanged(values.ben2, values.ben3) || hasChanged(values.pow2, values.pow3))){
					 page3.setDescription("Set the configuration");

					  for (Control control : values.composite_3.getChildren()) {
							control.dispose();

						}
						values.composite_3.setEnabled(false);
							for(int y=0;y<14;y++){
								values.NextOnP3[0][y]=false;
							}
							values.prevRefNodesNum3 = values.gettotNodes();
			            	values.prevRefClNum3 = values.gettotCl();
							for(int n=0;n<values.gettotCl();n++){
								for(int y=0;y<14;y++){
									values.NextOnP3[n][y]=false;
								}
								
							}
							
							values.composite_3.setEnabled(true);
							((wizPage3) page3).createp3(values.composite_3, values.sc3, values.container3);
							for(int l=0;l<values.gettotCl();l++){
								int	m=l+1;
								String ss = "cl" + m;
									values.proc3[l]=values.proc2[l];
									values.rem3[l]=values.rem2[l];
									values.ben3[l]=values.ben2[l];
									values.pow3[l]=values.pow2[l];
								}
							for(int l=0;l<values.gettotCl();l++){
								int	m=l+1;
								String ss = "cl" + m;
								values.proc2[l]=values.map.get(ss)[4];
								values.rem2[l]=values.map.get(ss)[2];
								values.ben2[l]=values.map.get(ss)[3];
								values.pow2[l]=values.map.get(ss)[20];
								}
				}
					
				if(countP3 && ((values.prevRefNodesNum3 != values.gettotNodes()) || (values.prevRefClNum3 != values.gettotCl()))){
					  page3.setDescription("Set the configuration");
					  
					  for (Control control : values.composite_3.getChildren()) {
							control.dispose();
						}
							for(int y=0;y<14;y++){
								values.NextOnP3[0][y]=false;
							}
							values.prevRefNodesNum3 = values.gettotNodes();
			            	values.prevRefClNum3 = values.gettotCl();
							for(int n=0;n<values.gettotCl();n++){
								for(int y=0;y<14;y++){
									values.NextOnP3[n][y]=false;
								}
							}
							
							((wizPage3) page3).createp3(values.composite_3, values.sc3, values.container3);	
							for(int l=0;l<values.gettotCl();l++){
								int	m=l+1;
								String ss = "cl" + m;
								values.proc2[l]=values.map.get(ss)[4];
								values.rem2[l]=values.map.get(ss)[2];
								values.ben2[l]=values.map.get(ss)[3];
								values.pow2[l]=values.map.get(ss)[20];
								}
				}
				
				countP3 = true;
		
			return page3;
		}
		
		if (currentPage == page3) {
			addPage(page4);
			int k;
			for (int li = 0; li < values.gettotCl(); li++) {
				k = li + 1;
				String ss = "cl" + k;
				values.obj[li][8] = values.SynchTime;						//For SynchTime and SynchtimeUnit in page3 
				values.interMap.put(values.clAtrrs[9],values.SynchTime); 	//Times from second time
				values.obj[li][9] = values.SynchTimeUnit;
				values.interMap.put(values.clAtrrs[10], values.SynchTimeUnit);
				values.map.put(ss, values.obj[li]);
				values.map2.put(ss, values.interMap);
			}
			
			if(countP4){
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
			
				((wizPage4) page4).createp4(((wizPage4) page4).createNodes(), values.sc_4, values.grp_2_4, values.grp_3_4, values.grp_4_4, values.container_4, values.container1_4, values.sccontainer_4,values.container2_4);
			

			}
			countP4 = true;
		
			return page4;
		}
	if (currentPage == page4 || currentPage == page4P || currentPage == page4S) {
		addPage(page5);
				
		if(values.countP5){

		}
		if(values.countP5){
			for(Control control : values.composite1_5.getChildren()){
				control.dispose();
			}
			((wizPage5) page5).createp5(values.composite1_5, values.composite2_5);

		}
			
			return page5;
		}

		return null;
	}
	
	
	public boolean canFinish(){
		if(getContainer().getCurrentPage()==page0 || getContainer().getCurrentPage()==page1 || getContainer().getCurrentPage()==page2 
				|| getContainer().getCurrentPage()==page3 || getContainer().getCurrentPage()==page4 || getContainer().getCurrentPage()==page5 
				|| getContainer().getCurrentPage()==page4P || getContainer().getCurrentPage()==page4S){
			return false;
		}else{
			return true;
		}
		
	}
	
	@Override
	public IWizardPage getPreviousPage(IWizardPage currentPage) {
		if(currentPage==page2){

			return page1;
			
		}
		if(currentPage==page3){

			return page1;
			
		}
		if(currentPage==page4){
			page4.getControl().setEnabled(true);
			
			return page3;
		}
		
		return null;
	}

	
	@Override
	public boolean performFinish() {
	
		return true;
	}
	
	//Compare  Proc, remote, bench from 3 to 2
	boolean hasChanged(String[] a1, String[] a2){
	boolean c;
	boolean[] tr = new boolean[a1.length];
	
	for(int k =0; k<a1.length; k++){
		if(a1[k].equals(a2[k])){
			tr[k]=true;
		}
	}
	
	boolean comp = true;
	for(int gg = 0; gg < tr.length; gg++){
		
		comp = comp && tr[gg];
	}
	c = !comp;
	return c;
	}
}

