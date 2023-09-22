package org.cossim.testwizard.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;


public class wizPage5 extends WizardPage{

	private wizVal values;
	public String ttt =  values.conf;

	
	public wizPage5(wizVal values) {
		super("TextEditor");
		setTitle("Text Editor");
		setDescription("Edit the commands");
		this.values = values;
		
	}


	@Override
	public void createControl(Composite parent) {

		
		ScrolledComposite container = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		container.setExpandVertical(true);
		container.setExpandHorizontal(true);
		
		Composite container2 = new Composite(container, SWT.NULL);
		container2.setLayout(new FillLayout());
		GridLayout contLayout = new GridLayout();

		container2.setLayout(contLayout);

		container.setContent(container2);
		values.composite2_5 = new Composite(container2, SWT.BORDER);
		values.composite2_5.setLayout(new GridLayout(1, false));
		values.composite1_5 = new ScrolledComposite(container2, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		values.composite1_5.setExpandHorizontal(true);
		values.composite1_5.setExpandVertical(true);

		Point Ssize = getShell().computeSize(100, 100);
		getShell().setSize(Ssize);
		
		createp5(values.composite1_5, values.composite2_5);
		values.countP5 = true;
		container.layout(true,true);
		container.setMinSize(container2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		setControl(container);
		
	}

	
	void createp5(final ScrolledComposite comp1, final Composite comp2){
		values.text1_5 = new Text(comp1, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL  | SWT.WRAP | SWT.BORDER);
		Button saveBtn = new Button(comp2, SWT.PUSH);	
		saveBtn.setText("Save Configuration...");

		values.text1_5.addModifyListener(new ModifyListener(){
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				if(!values.firstTime){
					values.Saved5 = false;
				}
				values.firstTime = false;
			}
			
		});

		saveBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				String toSave;
				toSave = values.text1_5.getText();
				FileDialog fd = new FileDialog(comp1.getShell(), SWT.SAVE);
				fd.setText("Save Command File");
				fd.setFilterPath(System.getenv("GEM5"));
				String[] filterExt = {  "*.sh", "*.*" };
				fd.setFilterExtensions(filterExt);
				fd.setOverwrite(true);
				String selected = null;
				boolean done = false;

				while(!done){
					selected = fd.open();
					if(selected == null){
						done = true;
					}else{

						fd.setOverwrite(true);

						try {
							Files.write(Paths.get(selected), toSave.getBytes());
							values.Saved5 = true;
							done = true;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
						//	e1.printStackTrace();
						}
						
					}
				}
			}
		});

		
		Button doneBtn = new Button(comp2, SWT.PUSH);	
		doneBtn.setText("Done");
		
		doneBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				
				////////////////////////Save Txc.ned///////////////////////////////////////

				//////////////////////////Save Txc.ned - END///////////////////////////////////
				////////////////////////Save run.sh///////////////////////////////////////////
				values.conf = values.createConf1(values.tree_4);
				String runShPath = System.getenv("GEM5");
				File runFile = new File(runShPath, "run.sh");
				PrintWriter textFileWriterSH;
				try {
					textFileWriterSH = new PrintWriter(new FileWriter(runFile));
					textFileWriterSH.write(values.text1_5.getText());
					textFileWriterSH.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				////////////////////////Save run.sh - END////////////////////////////////////
				
				MessageDialog.openInformation(
						comp2.getShell(),
						"Auto saved files",
						"Comnfiguration saved in /home/cossim/COSSIM/gem5/run.sh and Txc.ned file saved in /home/cossim/COSSIM/OMNET_WORKSPACE/HLANode/src");
				getWizard().getContainer().getShell().close();
				/*System.out.println("saved5(in P5 - in DoneBtn ModifyListener): "+values.Saved5);

				if(values.Saved5){ //An einai true = saved to kleinei
					values.firstTime = true;
					getWizard().getContainer().getShell().close();
				}else{ //An einai false mhnyma
					MessageBox notSaved = new MessageBox(comp2.getShell(),	SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
					notSaved.setMessage("There are unsaved changes in current configuration. Do you want to save the configuration?");
					notSaved.setText("Warning - Not saved changes");
					int resp = notSaved.open();
					if (resp == SWT.NO) {
						getWizard().getContainer().getShell().close();
					} else if (resp == SWT.YES) {
					
						values.conf = values.createConf1(values.tree_4);
										
						FileDialog fd = new FileDialog(comp2.getShell(), SWT.SAVE);
						fd.setText("Save Command File");
						fd.setFilterPath("/home/sandrian/Desktop");
						String[] filterExt = { "*.txt", "*.sh", "*.*" };
						fd.setFilterExtensions(filterExt);
						String selected = null;
						boolean done = false;
						
						while(!done){
							selected = fd.open();
							if(selected == null){
								done = true;
							}else{
								fd.setText("Save Command File");
								fd.setFilterPath("/home/sandrian/Desktop");
								String[] filterExt = { "*.txt", "*.sh", "*.*" };
								fd.setFilterExtensions(filterExt);
								fd.setOverwrite(true);

								try {
									Files.write(Paths.get(selected), values.conf.getBytes());
									getWizard().getContainer().getShell().close();
									done = true;
								} catch (IOException e1) {
									System.out.println();
									// TODO Auto-generated catch block
								//	e1.printStackTrace();
								}
								
							}
						}
					
						
					}
				}
				
				*/
			}
		});
	
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		String tt = values.createConf1(values.tree_4);
		
		values.text1_5.setText(tt);
		values.text1_5.setSize(300, 300);

		
		comp1.setContent(values.text1_5);
		comp1.setMinSize(values.text1_5.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		comp1.layout(true, true);
		comp1.setMinSize(100, 100);
	
	}
}


