package org.cossim.testwizard.actions;

import java.io.IOException;
import java.util.*;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.*;

public class wizPage1 extends WizardPage {
	private wizVal values;
	private Combo clTot;
	private Combo ndTot;
	Combo SynchTimeUnits;
	private Label configLbl;
	private Text configsT;
	Text SynchTimeT;
	private Label scriptLbl;
	private Label SynchTimeLbl;
	private Text scriptT;
	private Label lbl3;
	private Label lbl4;
	String flagN = "Off";
	String flagC = "Off";
		
	final String[] units = { "ms", "us" };
	
	public wizPage1(wizVal values) {
		super("NodesClusters");
		setTitle("Global Clusters' Details");
		setDescription("Set the number of nodes and clusters and the global details of clusters (paths, syncronization time)");
		this.values = values;
	}

	public void createControl(Composite parent) {
		values.NextOnP1 = new boolean[6];

		for(int nn=0;nn<values.NextOnP1.length;nn++){
			values.NextOnP1[nn]=false;
		}
		values.NextOnP1[0]=true;
		values.NextOnP1[1]=true;
		final Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		GridLayout layout = new GridLayout(1, true);
		container.setLayout(layout);
		//int[] nodes = new int[127];
		int[] nodes = new int[1023];
		Group group1 = new Group(container, SWT.NONE);
		group1.setText("Parameters of Commands"); 
		group1.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1)); // Alignments, spaces etc
		group1.setLayout(new GridLayout(3, false));
		
		Group group2 = new Group(container, SWT.NONE);
		group2.setText("Nodes and Clusters"); 
		group2.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false,
				1, 1)); // Alignments, spaces etc
		group2.setLayout(new GridLayout(2, false));


		for (int nn = 0; nn < 1023; nn++) {
			nodes[nn] = nn + 2;
		}

		final String[] a = Arrays.toString(nodes).split("[\\[\\]]")[1]
				.split(", ");

		configLbl = new Label(group1, SWT.NONE);
		configLbl.setText("Config Path");
		configsT = new Text(group1, SWT.BORDER);
		configsT.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,	true, false, 2, 1));
		configsT.setText("$GEM5/configs/example/fs.py");
		values.configs=configsT.getText();
		configsT.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				values.configs=configsT.getText();
				if(!configsT.getText().equals("")){
					values.NextOnP1[0]=true;
					getWizard().getContainer().updateButtons();
				}
				if(configsT.getText().equals("")){
					values.NextOnP1[0]=false;
					getWizard().getContainer().updateButtons();
				}
			}
		});
		
		scriptLbl = new Label(group1, SWT.NONE);
		scriptLbl.setText("Script");
		scriptT = new Text(group1, SWT.BORDER);
		scriptT.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,	true, false, 2, 1));
		scriptT.setText("$GEM5/configs/boot/COSSIM/script");
		values.benchName=scriptT.getText();
		scriptT.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				values.benchName=scriptT.getText();
				if(!scriptT.getText().equals("")){
					values.NextOnP1[1]=true;
					getWizard().getContainer().updateButtons();
				}
				if(scriptT.getText().equals("")){
					values.NextOnP1[1]=false;
					getWizard().getContainer().updateButtons();
				}
			}
		});
		
		SynchTimeLbl = new Label(group1, SWT.NONE);
		SynchTimeLbl.setText("Synchronization Time");
		SynchTimeT = new Text(group1, SWT.BORDER);
		SynchTimeT.setSize(65, 27);
		
		SynchTimeT.addListener(SWT.Verify, new Listener() {
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
		
		SynchTimeT.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				values.SynchTime=SynchTimeT.getText();
				if(!SynchTimeT.getText().equals("")){
					values.NextOnP1[2]=true;
					getWizard().getContainer().updateButtons();
				}
				if(SynchTimeT.getText().equals("")){
					values.NextOnP1[2]=false;
					getWizard().getContainer().updateButtons();
				}
			}
		});
		
		
		
		SynchTimeUnits = new Combo(group1, SWT.DROP_DOWN | SWT.READ_ONLY);
		SynchTimeUnits .setItems(units);
		SynchTimeUnits.setSize(50, 27);
		SynchTimeUnits.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				values.SynchTimeUnit=SynchTimeUnits.getText();
				values.NextOnP1[3]=true;
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		Label lblName1 = new Label(group2, SWT.NONE);
		lblName1.setText("Total Nodes Number (2 - 1024):");
		clTot = new Combo(group2, SWT.DROP_DOWN | SWT.READ_ONLY);
		clTot.setItems(a);
		clTot.setSize(40, 27);
		
		Label lblName2 = new Label(group2, SWT.NONE);
		lblName2.setText("Total Clusters Number");

		ndTot = new Combo(group2, SWT.DROP_DOWN | SWT.READ_ONLY);
		ndTot.setEnabled(false);
		ndTot.setSize(40, 27);

		lbl3 = new Label(group2, SWT.NONE | SWT.BORDER);
		lbl3.setText("Number of Nodes:  0       ");

		lbl4 = new Label(group2, SWT.NONE | SWT.BORDER);
		lbl4.setText("Number of Clusters: 0       ");

		clTot.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				values.NextOnP1[4]=true;
				lbl3.setEnabled(true);
				lbl3.setText("Number of Nodes: " + clTot.getText());
				ndTot.setEnabled(true);
				ndTot.setSize(70, 27);
			
				
				String[] b = new String[Integer.parseInt(clTot.getText())];
				b[0] = "1";

				for (int ci = 1; ci < Integer.parseInt(clTot.getText()); ci++) {
					b[ci] = a[ci - 1];
				}

				if(values.gettotCl()!=0){
					if(values.gettotCl()>Integer.parseInt(clTot.getText())){
						MessageDialog errorClNod = new MessageDialog(container.getShell(), "Warning", null, "Number of clusters must be less than or equal the number of nodes. New number of clusters is "+clTot.getText()+" (the new number of nodes).",MessageDialog.ERROR, new String[]{"OK"},0);
						flagC = "off";
						int k = errorClNod.open();
						ndTot.select(Integer.parseInt(clTot.getText()) - 1);
						lbl4.setText("Number of Clusters: " + clTot.getText());
						values.settotCl(Integer.parseInt(clTot.getText()));

					}
				}
				
				ndTot.setItems(b);
				values.settotNodes(Integer.parseInt(clTot.getText()));
				getWizard().getContainer().updateButtons();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		ndTot.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				values.NextOnP1[5]=true;
				values.settotCl(Integer.parseInt(ndTot.getText()));
				lbl4.setEnabled(true);
				lbl4.setText("Number of Clusters: " + ndTot.getText());
				getWizard().getContainer().updateButtons();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	public boolean canFlipToNextPage(){
				
		if(values.NextOnP1[0]==true && values.NextOnP1[1]==true && values.NextOnP1[2]==true && 
				values.NextOnP1[3]==true && values.NextOnP1[4]==true && values.NextOnP1[5]==true){
			return true;
		}else{
			return false;
		}
		
	}
}
