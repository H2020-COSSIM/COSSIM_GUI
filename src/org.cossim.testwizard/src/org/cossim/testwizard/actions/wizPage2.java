package org.cossim.testwizard.actions;

import java.util.*;

import javax.management.ValueExp;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class wizPage2 extends WizardPage {

	private wizVal values;
	private Label[] lblsS;
	private Label[] lblsE;
	private Label[] lblsP;
	private Combo[] comblimE;
	private Label   lbllimE;
	private Combo[] combProc;
	private Button[] etherdump;
	private Button[] bench;
	private Button[] remote;
	private Button[] power;
	private String[][] obj;
	private int[] start;

	final String[] proc = { "RISC-V", "ARM-64", "x86" };

	public wizPage2(wizVal values) {
		super("Clusters");
		this.values = values;
	}
	/* For map (Clusters) 
	0  β†’ cluster Start			(OK)			(int)
	1  β†’ cluster End				(OK)			(int)
	2  β†’ remote					(OK)			(boolean)
	3  β†’ benchmark on				(OK)            (boolean)
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
	14 β†’ script path				(OK)			(String)
	15 β†’ IP										(String)
	16 β†’ username									(String)
	17 β†’ password									(String)
	18 β†’ path (gia to cd kai to etherdump)		(String)
	19 β†’ Etherdump 								(boolean)
	20 β†’ power	 							    (boolean)
	21 β†’ powerValue 							    (String)
	22 β†’ number of Cores						    (int)
	*/
	

	public void createControl(Composite parent){
				
		values.sc2 = new ScrolledComposite(parent, SWT.BORDER	| SWT.H_SCROLL | SWT.V_SCROLL);
		values.sc2.setExpandVertical(true);
		values.sc2.setExpandHorizontal(true);
		
		values.container2 = new Composite(values.sc2, SWT.NULL);
		values.sc2.setContent(values.container2);
		values.container2.setLayout(new GridLayout(1, true));
		
		setControl(values.sc2);   
		
		values.composite_2 = new Composite(values.container2, SWT.NONE);
        values.composite_2.setLayout(new GridLayout(1, true));
        values.composite_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));

        createp2(values.composite_2, values.sc2, values.container2);
    }

	public void createp2(Composite composite_2, ScrolledComposite sc, Composite container){
	
			int[] clnod = new int[values.gettotNodes()];		

     		int[] clnod1 = new int[values.gettotNodes() - values.gettotCl()+1];  //Save nodes for the next clusters
     	
     		for (int nn = 1; nn < clnod.length; nn++) {
     			clnod[nn] = nn; 
     		}

     		for (int oo = 0; oo < clnod1.length; oo++) {
     			clnod1[oo] = clnod[oo];
     		}

     		final String[] a1 = Arrays.toString(clnod1).split("[\\[\\]]")[1].split(", ");
     		
     		values.grp2 = new Group[values.gettotCl()];
     		lblsS = new Label[values.gettotCl()];				//Start NodeLabel
     		lblsE = new Label[values.gettotCl()];				//End Node Label
     		lblsP = new Label[values.gettotCl()];				//Processor Label 
     		comblimE = new Combo[values.gettotCl()];			//End cluster Combo
     		combProc = new Combo[values.gettotCl()];			//Processor Combo
     		etherdump = new Button[values.gettotCl()];			//Ethedump select button
     		bench = new Button[values.gettotCl()];				//Benchmark Select Button
     		remote = new Button[values.gettotCl()];				//Remote Select Button
     		power = new Button[values.gettotCl()];				//power Select Button
     		obj = new String[values.gettotCl()][23];			//Values
     		final Map<String, Object> interMap = new LinkedHashMap<String, Object>(); 
     		
     		start = new int[values.gettotCl()];

     		start[0] = 0;
     		
     		obj[0][18] = "/home/cossim/COSSIM/gem5/";
     		interMap.put(values.clAtrrs[19], "/home/cossim/COSSIM/gem5/");

     		values.grp2[0] = new Group(composite_2, SWT.NONE);
     		values.grp2[0].setText("Cluster 1"); 
     		values.grp2[0].setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true,	1, 1)); 
            
     		values.grp2[0].setLayout(new GridLayout(5, false));

     		lblsS[0] = new Label(values.grp2[0], SWT.NONE);
     		lblsS[0].setText("Start node: 0   ");
     	
     		lblsE[0] = new Label(values.grp2[0], SWT.NONE);
     		lblsE[0].setText("End node");
     		
     		if(values.gettotCl()>1){  //If more than one clusters end-cluster combo values from a1
     			comblimE[0] = new Combo(values.grp2[0], SWT.DROP_DOWN | SWT.READ_ONLY);
         		comblimE[0].setItems(a1);
     		}else if(values.gettotCl()==1){  //If there is only one cluster put label instead of combo with total nodes number as value
     			lbllimE = new Label(values.grp2[0], SWT.DROP_DOWN | SWT.BORDER);
				lbllimE.setText(""+Integer.toString(values.gettotNodes()-1));
     		}
     		
     		lblsP[0] = new Label(values.grp2[0], SWT.NONE);
     		lblsP[0].setText("Processor");
     		combProc[0] = new Combo(values.grp2[0], SWT.DROP_DOWN | SWT.READ_ONLY);
     		combProc[0].setItems(proc);
     		
     	
     		
     		etherdump[0] = new Button(values.grp2[0], SWT.CHECK);
     		etherdump[0].setText("Etherdump");
     		
     		bench[0] = new Button(values.grp2[0], SWT.CHECK);
     		bench[0].setText("Script");
     		
     		remote[0] = new Button(values.grp2[0], SWT.CHECK);
     		remote[0].setText("Remote");
     		
     		power[0] = new Button(values.grp2[0], SWT.CHECK);
     		power[0].setText("Power");
       		
     		values.grp2[0].layout(true, true);

     		int k;
     		combProc[0].addSelectionListener(new SelectionListener() {
     			public void widgetSelected(SelectionEvent e) {
     				obj[0][4] = combProc[0].getText();
     				interMap.put(values.clAtrrs[5], combProc[0].getText());
     				values.NextOnP2[0][1]=true;
     				getWizard().getContainer().updateButtons();
     			}
     			public void widgetDefaultSelected(SelectionEvent e) {

     			}
     			
     		});
     		
     		if(values.gettotCl()>1){		//If cluster > 1 put combo
     			comblimE[0].addSelectionListener(new SelectionListener() {
         			public void widgetSelected(SelectionEvent e) {
         				obj[0][1] = comblimE[0].getText();
         				interMap.put(values.clAtrrs[1], comblimE[0].getText());
         				values.NextOnP2[0][0] = true;
         				values.NextOnP2[1][0] = false;
 						values.NextOnP2[values.gettotCl()-1][0] = true;
							
         				getWizard().getContainer().updateButtons();

         			}
         			public void widgetDefaultSelected(SelectionEvent e) {

         			}
         			
         		});
     		}else if(values.gettotCl()==1){
     			obj[0][1]= Integer.toString(values.gettotNodes()-1);
     			interMap.put(values.clAtrrs[1], Integer.toString(values.gettotNodes()-1));
     		}
     		
     		
     		obj[0][2] = "false";
     		interMap.put(values.clAtrrs[2], false);
     		remote[0].addSelectionListener(new SelectionAdapter(){
     			public void widgetSelected(SelectionEvent e){
     				Button btn = (Button) e.getSource();
     				interMap.put(values.clAtrrs[2], btn.getSelection());
     				obj[0][2] = String.valueOf(btn.getSelection());
     			}
     			
     		});
     		
     		obj[0][3] = "false";
     		interMap.put(values.clAtrrs[3], false);
     		bench[0].addSelectionListener(new SelectionAdapter(){
     			public void widgetSelected(SelectionEvent e){
     				Button btn = (Button) e.getSource();
     				interMap.put(values.clAtrrs[3], btn.getSelection());
     				obj[0][3] = String.valueOf(btn.getSelection());
     			}
     		});
     		
     		obj[0][19] = "false"; //In the map etherdump is 4
     		interMap.put(values.clAtrrs[4], false);
     		etherdump[0].addSelectionListener(new SelectionAdapter(){
     			public void widgetSelected(SelectionEvent e){
     				Button btn = (Button) e.getSource();
     				obj[0][19] = String.valueOf(btn.getSelection());
     				interMap.put(values.clAtrrs[4], btn.getSelection());
     			}
     		});
     		
     		obj[0][20] = "false"; 
     		interMap.put(values.clAtrrs[20], false);
     		power[0].addSelectionListener(new SelectionAdapter(){
     			public void widgetSelected(SelectionEvent e){
     				Button btn = (Button) e.getSource();
     				interMap.put(values.clAtrrs[20], btn.getSelection());
     				obj[0][20] = String.valueOf(btn.getSelection());
     				
     			}
     		});

     		obj[0][0] = "0";
     		interMap.put(values.clAtrrs[0], "0");
     		values.map2.put("cl1", interMap);
     		values.map.put("cl1", obj[0]);
     		
     		if (values.gettotCl() > 1) {
       			for (int li = 1; li < values.gettotCl(); li++) {
     				obj[li][2] = "false";
     	    		obj[li][3] = "false";
     	    		obj[li][19] = "false";
     	    		obj[li][20] = "false";
     	    		obj[li][18] = "/home/cossim/COSSIM/gem5/";  
     				k = li + 1;
     				values.grp2[li] = new Group(composite_2, SWT.NONE);
     				values.grp2[li].setText("Cluster " + k); 
														
     				values.grp2[li].setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,	true, false, 1, 1)); 
     				values.grp2[li].setLayout(new GridLayout(5, false));
     				values.grp2[li].setEnabled(false);

     				lblsS[li] = new Label(values.grp2[li], SWT.NONE);
     				lblsS[li].setEnabled(false);
     				lblsS[li].setText("Start Node");

     				lblsE[li] = new Label(values.grp2[li], SWT.NONE);
     				lblsE[li].setText("End node");
     				lblsE[li].setEnabled(false);
     				//Label in the last
     				if(li<values.gettotCl()-1){
     					comblimE[li] = new Combo(values.grp2[li], SWT.DROP_DOWN  | SWT.READ_ONLY);
     					comblimE[li].setEnabled(false);
     				}else if(li==values.gettotCl()-1){
     					lbllimE = new Label(values.grp2[li], SWT.DROP_DOWN | SWT.BORDER);
     					lbllimE.setText(""+Integer.toString(values.gettotNodes()-1));
     					lbllimE.setEnabled(false);
     				}
     				
     				lblsP[li] = new Label(values.grp2[li], SWT.NONE);
     				lblsP[li].setText("Processor");
     				lblsP[li].setEnabled(false);

     				combProc[li] = new Combo(values.grp2[li], SWT.DROP_DOWN | SWT.READ_ONLY);
     				combProc[li].setItems(proc);
     				combProc[li].setEnabled(false);
     				
     				etherdump[li] = new Button(values.grp2[li], SWT.CHECK);
     				etherdump[li].setText("Etherdump");
     				etherdump[li].setEnabled(false);
   				
     				bench[li] = new Button(values.grp2[li], SWT.CHECK);
     	    		bench[li].setText("Script");
     	    		bench[li].setEnabled(false);
  
     	    		remote[li] = new Button(values.grp2[li], SWT.CHECK);
     	    		remote[li].setText("Remote");
     	    		remote[li].setEnabled(false);
     	    		
     	    		power[li] = new Button(values.grp2[li], SWT.CHECK);
     	    		power[li].setText("Power");
     	    		power[li].setEnabled(false);
     			}

     			final int[] cc = new int[values.gettotCl()];

     			for (int ki = 0; ki < values.gettotCl(); ki++) {
     				cc[ki] = ki;
     			}

     			for (int ki = 1; ki < values.gettotCl(); ki++) {
     				final Map<String, Object> interMap2 = new LinkedHashMap<String, Object>(); 
             		interMap2.put(values.clAtrrs[2], false);
             		interMap2.put(values.clAtrrs[3], false);
             		interMap2.put(values.clAtrrs[4], false);
             		interMap2.put(values.clAtrrs[20], false);
             		final int kl = cc[ki];
     			
     				comblimE[ki - 1].addSelectionListener(new SelectionListener() {

     					public void widgetSelected(SelectionEvent e) {
     						values.NextOnP2[kl][0] = false;
     						values.NextOnP2[kl][1] = false;
     						values.NextOnP2[values.gettotCl()-1][0] = true;
 							getWizard().getContainer().updateButtons();

     						int jj = Integer.parseInt(comblimE[kl - 1].getText()) + 1;

     						int[] nextCl = new int[values.gettotNodes() - jj
     								- values.gettotCl() + kl + 1];
     						int hh = values.gettotNodes() - jj - values.gettotCl()
     								+ kl + 1;

     						for (int yy = jj; yy < nextCl.length + jj; yy++) {
     							nextCl[yy - jj] = yy;
     						}

     						final String[] bb = Arrays.toString(nextCl).split(
     								"[\\[\\]]")[1].split(", ");
     						
     						final String[] bb1 = new String[nextCl.length]; 
     						for(int yy=0;yy<nextCl.length;yy++){
     							bb1[yy]=String.valueOf(nextCl[yy]);
     						}

     						values.grp2[kl].setEnabled(true);
     						lblsS[kl].setEnabled(true);
     						
     						lblsS[kl].setText("Start Node: "+jj+"   ");
     						
     						lblsP[kl].setEnabled(true);
     						combProc[kl].setEnabled(true);
     						combProc[kl].setItems(proc);
     						
     						
     						lblsE[kl].setEnabled(true);
     						if(kl<values.gettotCl()-1){
     							comblimE[kl].setEnabled(true);
     							comblimE[kl].setItems(bb1);
     						}else if(kl==values.gettotCl()-1){
     							lbllimE.setEnabled(true);
     						}
         						                  				
     						bench[kl].setEnabled(true);
     						remote[kl].setEnabled(true);
     						etherdump[kl].setEnabled(true);
     						power[kl].setEnabled(true);
     						
     						values.grp2[kl].layout(true, true);

     						obj[kl][0] = Integer.toString(jj);
     						interMap2.put(values.clAtrrs[0], Integer.toString(jj));
     					}

     					public void widgetDefaultSelected(SelectionEvent e) {

     					}
     				});

     				if(kl<values.gettotCl()-1){
     					comblimE[kl].addSelectionListener(new SelectionListener() {

     						public void widgetSelected(SelectionEvent e) {
     							obj[kl][1] = comblimE[kl].getText();
     							interMap2.put(values.clAtrrs[1], comblimE[kl].getText());
     							values.NextOnP2[kl][0]=true;
     							getWizard().getContainer().updateButtons();
     						}
     						public void widgetDefaultSelected(SelectionEvent e) {
     						}
     					});
     				}else if(kl==values.gettotCl()-1){
     					obj[kl][1]= Integer.toString(values.gettotNodes()-1);
     					interMap2.put(values.clAtrrs[1], Integer.toString(values.gettotNodes()-1));
     				}
     				combProc[kl].addSelectionListener(new SelectionListener() {
     					public void widgetSelected(SelectionEvent e) {
     						obj[kl][4] = combProc[kl].getText();
     						interMap2.put(values.clAtrrs[5], combProc[kl].getText());
     						values.NextOnP2[kl][1] = true; 
     						getWizard().getContainer().updateButtons();
     					}

     					public void widgetDefaultSelected(SelectionEvent e) {

     					}
     				});

     				remote[kl].addSelectionListener(new SelectionAdapter(){
     	    			
     	    			public void widgetSelected(SelectionEvent e){
     	    				Button btn = (Button) e.getSource();
     	    				obj[kl][2] = String.valueOf(btn.getSelection());
     						interMap2.put(values.clAtrrs[2], btn.getSelection());
     	    			}
     	    			
     	    		});
     				
     				bench[kl].addSelectionListener(new SelectionAdapter(){
     	    			
     	    			public void widgetSelected(SelectionEvent e){
     	    				Button btn = (Button) e.getSource();
     	    				obj[kl][3] = String.valueOf(btn.getSelection());
     	    				interMap2.put(values.clAtrrs[3], btn.getSelection());
     	    			}
     	    		});
     				
     				etherdump[kl].addSelectionListener(new SelectionAdapter(){
     	    			
     	    			public void widgetSelected(SelectionEvent e){
     	    				Button btn = (Button) e.getSource();
     	    				obj[kl][19] = String.valueOf(btn.getSelection());
     	    				interMap2.put(values.clAtrrs[4], btn.getSelection());

     	    			}
     	    		});
     				////////////////////Power/////////////////////////////////////////
     				power[kl].addSelectionListener(new SelectionAdapter(){
     	    			
     	    			public void widgetSelected(SelectionEvent e){
     	    				Button btn = (Button) e.getSource();
     	    				obj[kl][20] = String.valueOf(btn.getSelection());
     	    				interMap2.put(values.clAtrrs[20], btn.getSelection());

     	    			}
     	    		});
     				
     				String ss = "cl"+(kl+1);
     				values.map.put(ss, obj[kl]);
     				values.map2.put(ss, interMap2);

     			}
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
			enable[k] = false;
			if(val[k][0]==true && val[k][1]==true){
				enable[k] = true;
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
		 
		if(check_NextlEnable(values.NextOnP2)){
			return true;
		}else{
			return false;
		}
		
	}
}


