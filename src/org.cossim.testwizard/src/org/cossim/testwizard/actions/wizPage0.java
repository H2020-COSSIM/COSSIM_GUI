package org.cossim.testwizard.actions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;


public class wizPage0 extends WizardPage{
	private wizVal values;
	private Text openFile;
	private Button b1;
	private Button b2;
	private Button b3;
	private Button browse;

	boolean bb1 = true;
	boolean bb2 = false;
	boolean bb3 = false;
	boolean bb4 = false;
	public wizPage0(wizVal values) {
		super("Init");
		setTitle("Configuration Process");
		setDescription("Select configuration creation process");
		this.values = values;
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		GridLayout layout = new GridLayout(1, true);
		container.setLayout(layout);
				
		final Group group2 = new Group(container, SWT.NONE);
		group2.setText("Type of Configuration"); 
		group2.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1)); 
		group2.setLayout(new GridLayout(4, false));
			
		b1 = new Button(group2, SWT.RADIO);
		b1.setText("Create New Configuration");
		b1.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,	true, false, 4, 1));
		b1.setSelection(true);
		b1.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				values.Saved5 = false;
				bb1 = true;
				bb2 = false;
				bb3 = false;
				getWizard().getContainer().updateButtons();
 				Button btn = (Button) e.getSource();
 				values.Sel1 = btn.getSelection();
				getWizard().getContainer().updateButtons();
 				if(btn.getSelection()){
 					browse.setEnabled(false);
 					openFile.setEnabled(false);
 				}

 			}
		});
		
		b2 = new Button(group2, SWT.RADIO);
		b2.setText("Load Existing Configuration From File");
		b2.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,	true, false, 4, 1));
		b2.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				values.Saved5 = true;
				bb1 = false;
				if(!openFile.getText().equals(null) && !openFile.getText().equals("")){
					bb2 = true;
				}
				bb3 = false;
				getWizard().getContainer().updateButtons();
				Button btn = (Button) e.getSource();
 				values.Sel2 = btn.getSelection();
				getWizard().getContainer().updateButtons();
 				if(btn.getSelection()){
 					browse.setEnabled(true);
 					openFile.setEnabled(true);
 				}

 			}
		});
		
		openFile = new Text(group2, SWT.BORDER);
		openFile.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 3, 1));
		openFile.setEnabled(false);
		browse = new Button(group2, SWT.PUSH);
		browse.setText("Browse...");
		browse.setEnabled(false);

		browse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				FileDialog fd = new FileDialog(group2.getShell(), SWT.OPEN);
				fd.setText("Choose Configuration File...");
				fd.setFilterPath("/home/cossim/COSSIM/gem5");
				String fn = null;
				boolean done = false;
				
				while(!done){
					fn = fd.open();
					if(fn == null){
						done = true;
					}else{
						openFile.setText(fn);
						values.PFile = fn;
						
						try {
							values.confFromFile = new String(Files.readAllBytes(Paths.get(fn)));
							bb2 = values.correctF(values.getLines(values.confFromFile));
							getWizard().getContainer().updateButtons();

							if(!bb2){
								MessageDialog.openInformation(
										container.getShell(),
										"Wrong File",
										"The configuration is not valid...");
							}
							done = true;
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		

		openFile.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				boolean ex = false;
				File f = new File(openFile.getText());
				ex = f.exists() && f.isFile();
				
				if(!openFile.getText().equals("") && !openFile.equals(null) && ex){
					bb2 = true;
					getWizard().getContainer().updateButtons();

				}else if(openFile.getText().equals("") || openFile.equals(null) || !ex){
					bb2 = false;
					getWizard().getContainer().updateButtons();
				}
			}
		});
	
		b3 = new Button(group2, SWT.RADIO);
		b3.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,	true, false, 4, 1));
		b3.setText("Sample Configuration");
		b3.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				values.Saved5 = true;
				bb1 = false;
				bb2 = false;
				bb3 = true;
				getWizard().getContainer().updateButtons();
 				Button btn = (Button) e.getSource();
 				values.Sel3 = btn.getSelection();
 				if(btn.getSelection()){
 					browse.setEnabled(false);
 					openFile.setEnabled(false);
					getWizard().getContainer().updateButtons();
 				}
 			}
		});
	}
	
	boolean check_NextEnabled(boolean S1, boolean S2, boolean S3){
		boolean next = false;
		if(S1){
			next = true;
		}
		
		if(S3){
			next = true;
		}
		
		if(S2){
			next = true;
		}
		
		return next;
	}
	public boolean canFlipToNextPage(){
		if(check_NextEnabled(bb1, bb2, bb3)){
			return true;
		}else{
			return false;
		}
	
		
	}
	
/*	@Override
	public void performHelp() 
	{
	    Shell shell = new Shell(getShell());
	    shell.setText("My Custom Help !!");
	    shell.setLayout(new GridLayout());
	    shell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	    Browser browser = new Browser(shell, SWT.NONE);
	    browser.setUrl("help.txt");
	    browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    System.out.println(System.getProperty("user.dir"));

	    shell.open();
	}*/
}

