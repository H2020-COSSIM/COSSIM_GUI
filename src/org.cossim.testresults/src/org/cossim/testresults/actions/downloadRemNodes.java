package org.cossim.testresults.actions;


import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;


public class downloadRemNodes extends Dialog {


	
	protected downloadRemNodes(Shell parentShell) {
		super(parentShell);
	}


	/////////// FOR REMOTE!!!!! //////////////////////////////
	String openRunSH(){ //Reads run.sh file int a String
		String run = "";
		try {
			run = new String(Files.readAllBytes(Paths.get(System.getenv("GEM5")+"/run.sh")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return run;
	}
	
	String[] getLines(String rr) throws IOException { 
		String lines[] = rr.split("\\r?\\n");
		String lines1[] = rr.split("[\\r\\n]+"); // Remove empty lines


		return lines1;
	}
	
boolean[] findRemote(String[] lines){ 
		
		boolean[] rem = new boolean[lines.length]; //true if remote
		
		for(int i=0;i<lines.length;i++){
			if(lines[i].split(" ")[0].equals("sshpass")){
				rem[i]=true;
			}else rem[i]=false;
		}
		return rem;
	}






static String[] remAttrs(String line){
	
	String[] rem = new String[3];
	
	String[] cc = line.trim().split("'"); 
	String[] usr = cc[2].trim().split(" ")[1].trim().split("@");
	String pass = cc[1].trim(); //password
	String user = usr[0];		//username
	String IP = usr[1];			//IP
			
	rem[0] = usr[1];
	rem[1] = usr[0];
	rem[2] = cc[1].trim();
	
	return rem;
}






String[][] remoteNodes4getRes(){ 
	int remNum = 0;
	int remCount = 0;
	try {
		for(int k=0; k<getLines(openRunSH()).length; k++){
			if(findRemote(getLines(openRunSH()))[k]){
				remNum +=1;
			}
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	String[][] remAttrs = new String[remNum][4];
	
	try {
		for(int k=0; k<getLines(openRunSH()).length; k++){
			if(findRemote(getLines(openRunSH()))[k]){
				remAttrs[remCount][0] = Integer.toString(k);					//Number of remote node
				remAttrs[remCount][1] =remAttrs(getLines(openRunSH())[k])[0];	//IP
				remAttrs[remCount][2] =remAttrs(getLines(openRunSH())[k])[1];	//username
				remAttrs[remCount][3] =remAttrs(getLines(openRunSH())[k])[2];	//password
				remCount+=1;
			}
			
		}
	} catch (IOException e) {
		
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return remAttrs;
}


String[] createCommands(){
	String[][] remNodes = remoteNodes4getRes();
	String[] callScript = new String[remNodes.length];
	String st = "/usr/bin/xterm -e ";
	String sc_p = "/get_stats_new.sh ";
	String sc = "/get_stats.sh ";
	for(int i=0;i<remNodes.length;i++){
		callScript[i] = st+System.getenv("GEM5")+sc+remNodes[i][0]+" "+remNodes[i][1]+" "+remNodes[i][2]+" "+remNodes[i][3];
	}
	
	return callScript;
}




	//////////  FOR REMOTE - END!!!!! ////////////////////////
	
	protected Control createDialogArea(final Composite parent) {
		final checkExist ce = new checkExist();
		
		
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		container.setLayout(layout);
		
		
	    Label down = new Label(container, SWT.NONE);
	    down.setText("Downloading results for remote nodes...");


	   
	    createCommands();
	    
	    Process[] pr = new Process[createCommands().length];


	    for(int c=0;c<createCommands().length;c++){
	    	try {
	    		pr[c] = Runtime.getRuntime().exec(createCommands()[c]);


			} catch (IOException/* | InterruptedException*/ e) {
				
				e.printStackTrace();
			} 
	    }
	
	    try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    down.setText("Downloading completed.");
	
		return container;
	}
	


}