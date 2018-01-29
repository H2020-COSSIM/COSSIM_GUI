package org.cossim.testresults.actions;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public SampleAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
	
		
		myRes dialog = new myRes(window.getShell());
		selectData dialog1 = new selectData(window.getShell());
		selectAttrs dialog2 = new selectAttrs(window.getShell());
		myComp dialog3 = new myComp(window.getShell());
		downloadRemNodes dialog4 = new downloadRemNodes(window.getShell());
		JdownloadRemNodes dialog5 = new JdownloadRemNodes(window.getShell());
		//window.getShell().setSize(200, 200);
		/*dialog.create();
		dialog.open();*/
		boolean runSHExist = false;
		System.out.println(runSHExist);
		
		File runsh = new File(System.getenv("GEM5")+"/run.sh");
		runSHExist = runsh.exists();
		int remNum = 0;
		if(runSHExist){
			try {
				for(int k=0; k<dialog4.getLines(dialog4.openRunSH()).length; k++){
					if(dialog5.findRemote(dialog5.getLines(dialog5.openRunSH()))[k]){
						remNum +=1;
					}
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}else{
			MessageDialog.openInformation(
					window.getShell(), "run.sh does not exist",
					"run.sh does not exist in "+System.getenv("GEM5")+
					"\nCannot download remote nodes' results if any") ;
			
		}

		if(remNum!=0 & runSHExist){
			dialog5.create();
			dialog5.open();
		}
		
		dialog1.create();
		dialog1.open();

	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}