package org.cossim.testresults.actions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class selectData extends Dialog {
	private Button AllNodes;
	private Button selNodes;
	private Text nodeText1;
	private Button showNodes;
	
	private Button AllNodesComp;
	private Button selNodesComp;
	private Text nodeText2;
	private Button tickNodes;
	private Button compNodes;
	
	protected selectData(Shell parentShell) {
		super(parentShell);
	}
	
	protected Control createDialogArea(Composite parent) {
		final dialogValues dv = new dialogValues();
		final statsP t2 = new statsP();
		final checkExist ce = new checkExist();
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		container.setLayout(layout);
		
		final Group nodesDet = new Group(container, SWT.NONE);
		nodesDet.setText("Show Nodes Details");
		nodesDet.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		nodesDet.setLayout(new GridLayout(4, false));
		
		AllNodes = new Button(nodesDet, SWT.RADIO);
		AllNodes.setText("Show all nodes");
		AllNodes.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false, 4, 1));
		AllNodes.setSelection(dv.allNodes1);
		AllNodes.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				Button btn = (Button) e.getSource();
				dv.allNodes1 = btn.getSelection();
 			}
		});
		
		
		selNodes = new Button(nodesDet, SWT.RADIO);
		selNodes.setText("Select nodes to show");
		selNodes.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false, 1, 1));
		selNodes.setSelection(!dv.allNodes1);
		
		nodeText1 = new Text(nodesDet,SWT.BORDER);
		nodeText1.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 3, 1));
		nodeText1.setSize(350, 20);
		nodeText1.setText(dv.s1);
		nodeText1.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dv.s1 = nodeText1.getText();
			}
		});
		
		final myRes dialog1 = new myRes(getShell());
		showNodes = new Button(nodesDet, SWT.PUSH);
		showNodes.setText("Show selected nodes");
		showNodes.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false, 2, 1));
		showNodes.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				dv.nodeNum = t2.nodeNum();
				if(dv.allNodes1 && ce.allEx()){
					dialog1.create();
					dialog1.open();	
				}else if(!ce.allEx()){
					MessageDialog.openInformation(
							nodesDet.getShell(),
							"Directory or file missing",
							"Check files and directories in "+System.getenv("GEM5"));
				}else if(!(dv.findRange(dv.s1)!=null && dv.nodeNum>dv.maxParsed)){
					MessageDialog.openInformation(
							nodesDet.getShell(),
							"Not Valid Selection",
							"Please enter a valid node range...");
				}else{
					System.out.println("Einai: "+ce.allEx());
					dialog1.create();
					dialog1.open();			
				}
			}
		});
		
		final Group compareNodes = new Group(container, SWT.NONE);
		compareNodes.setText("Compare Nodes Data");
		compareNodes.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		compareNodes.setLayout(new GridLayout(4, false));
		
		AllNodesComp = new Button(compareNodes, SWT.RADIO);
		AllNodesComp.setText("Compare all nodes");
		AllNodesComp.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false, 4, 1));
		AllNodesComp.setSelection(dv.allNodes2);
		AllNodesComp.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				Button btn = (Button) e.getSource();
				dv.allNodes2 = btn.getSelection();
 			}
		});
		
		selNodesComp = new Button(compareNodes, SWT.RADIO);
		selNodesComp.setText("Select nodes to compare");
		selNodesComp.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false, 1, 1));
		selNodesComp.setSelection(!dv.allNodes2);
		
		nodeText2 = new Text(compareNodes,SWT.BORDER);
		nodeText2.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 3, 1));
		nodeText2.setSize(350, 20);
		nodeText2.setText(dv.s2);
		nodeText2.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dv.s2 = nodeText2.getText();
			}
		});
		
		tickNodes = new Button(compareNodes, SWT.PUSH);
		tickNodes.setText("Select data to compare");
		tickNodes.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false, 3, 1));
		
		final selectAttrs dialog2 = new selectAttrs(getShell());
		tickNodes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				dialog2.create();
				dialog2.open();
			}
		});
		
		
		
		compNodes = new Button(compareNodes, SWT.PUSH);
		compNodes.setText("Compare selected nodes");
		compNodes.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false, 2, 1));
		
		final myComp dialog3 = new myComp(getShell());
		compNodes.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				dv.nodeNum = t2.nodeNum();
				if(dv.allNodes2 && ce.allEx()){
					dialog3.create();
					dialog3.open();
				}else if(!ce.allEx()){
					MessageDialog.openInformation(
							nodesDet.getShell(),
							"Directory or file missing",
							"Check files and directories in "+System.getenv("GEM5"));
				}else if(!(dv.findRange(dv.s2)!=null && dv.nodeNum>dv.maxParsed)){
					MessageDialog.openInformation(
							compareNodes.getShell(),
							"Not Valid Selection",
							"Please enter a valid node range");
				}else{
					dialog3.create();
					dialog3.open();
				}
			}
		});
	
		
		
		
		return container;
	}


	protected boolean isResizable() {
	    return true;
	}
    @Override
    protected void configureShell(Shell newShell) {
            super.configureShell(newShell);
            newShell.setText("Select Action");
    }


}
