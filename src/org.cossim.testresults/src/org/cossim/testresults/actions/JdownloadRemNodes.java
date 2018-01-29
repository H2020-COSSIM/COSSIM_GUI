package org.cossim.testresults.actions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class JdownloadRemNodes extends Dialog {

	protected JdownloadRemNodes(Shell parentShell) {
		super(parentShell);
	}

	String openRunSH(){ //Reads run.sh file int a String
		String run = "";
		try {
			run = new String(Files.readAllBytes(Paths.get(System.getenv("GEM5")+"/run.sh")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return run;
	}
	
	String[] getLines(String rr) throws IOException { //Briskei tis grammes. Afairei tis kenes kai ta cd(Ta cd efygan etsi ki alliws)
		String lines[] = rr.split("\\r?\\n");
		String lines1[] = rr.split("[\\r\\n]+"); // Remove empty lines

		return lines1;
	}
	
boolean[] findRemote(String[] lines){ //Epistrefei poioi komboi (grammes) einai Remote
		
		boolean[] rem = new boolean[lines.length]; //true if remote
		
		for(int i=0;i<lines.length;i++){
			if(lines[i].split(" ")[0].equals("sshpass")){
				rem[i]=true;
			}else rem[i]=false;
		}
		return rem;
	}



static String[] remAttrs(String line){//Epistrefei gia ka8e grammh ta stoixei toy remote
	
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



String[][] remoteNodes4getRes(){ //String[][] with [remNode][remAttr] --> remAttr[0]=nodeNum, remAttr[1]=IP, remAttr[2]=userName. remAttr[3]=password
	int remNum = 0;
	int remCount = 0;
	try {
		for(int k=0; k<getLines(openRunSH()).length; k++){
			if(findRemote(getLines(openRunSH()))[k]){
				remNum +=1;
			}
		}
	} catch (IOException e) {

		// TODO Auto-generated catch block
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
		e.printStackTrace();
	}
	
	return remAttrs;
}

void connect(String[] remNodes){
	 JSch jsch = new JSch();
     Session session = null;
     //remNodes[0] = node number
     //remNodes[1] = IP	 
     //remNodes[2] = username
     //remNodes[3] = password
     
     try {
         session = jsch.getSession(remNodes[2], remNodes[1], 22);
         session.setConfig("StrictHostKeyChecking", "no");
         session.setPassword(remNodes[3]);
         session.connect();

         Channel channel = session.openChannel("sftp");
         channel.connect();
         
         ChannelSftp channelSftp = (ChannelSftp)channel;
         channelSftp.cd("/home/cossim/COSSIM/gem5/node"+remNodes[0]+"/");
         
         ChannelSftp sftpChannel = (ChannelSftp) channel;
         Vector<ChannelSftp.LsEntry> list = channelSftp.ls("*");

            int f = 0;
         for(ChannelSftp.LsEntry entry : list) {
         	f+=1;
            System.out.println("Downloading file: "+f+". " +entry.getFilename());
         	channelSftp.get(entry.getFilename(), "/home/cossim/COSSIM/gem5/node"+remNodes[0]+"/" + entry.getFilename());
         }

         
         channelSftp.cd("/home/cossim/COSSIM/gem5/McPat/");
         channelSftp.get("energy"+remNodes[0]+".txt","/home/cossim/COSSIM/gem5/McPat/energy"+remNodes[0]+".txt");
         channelSftp.get("mcpatOutput"+remNodes[0]+".txt","/home/cossim/COSSIM/gem5/McPat/mcpatOutput"+remNodes[0]+".txt");

         System.out.println("DONE..");
         channelSftp.exit();
         session.disconnect();
     } catch (JSchException e) {
         e.printStackTrace();  
     } catch (SftpException e) {
         e.printStackTrace();
     }
}


protected Control createDialogArea(final Composite parent) {
	final checkExist ce = new checkExist();
	
	
	final Composite container = new Composite(parent, SWT.NONE);
	GridLayout layout = new GridLayout(2, false);
	container.setLayout(layout);
	
	final ProgressBar bar = new ProgressBar(container, SWT.SMOOTH);
	bar.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, true ) );

	Point preferredSize = bar.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	int width = preferredSize.x +100;
	int height = preferredSize.y - 9;

bar.setSize(width, height);
bar.setSelection(0); 

    bar.setMaximum(100);
    final int maximum = bar.getMaximum();
    
    
	final int[] nodes = new int[remoteNodes4getRes().length];
	for(int o=0;o<nodes.length;o++){
		nodes[o] = Integer.parseInt(remoteNodes4getRes()[o][0]);
	}

    final int b = maximum/nodes.length;
    final double b1 = (double)maximum/(double)nodes.length;
    
    
    ////////////////////////////////////////////////////////////////////
	
    new Thread() {
    	int y = 0;
    	final Label perCent = new Label(container, SWT.NONE);
    	
        public void run() {
       
          for (final int i : nodes) {

            if (container.getDisplay().isDisposed())
              return;
            container.getDisplay().asyncExec(new Runnable() {
            	 
              public void run() {
                if (bar.isDisposed())
                  return;
                File file = new File("/home/cossim/COSSIM/gem5/node"+i);
      		  if (!file.exists()) {
      	            if (file.mkdir()) {
      	               // System.out.println("Directory is created!");
      	            } else {
      	               // System.out.println("Failed to create directory!");
      	            }
      	        }
      		
	        	connect(remoteNodes4getRes()[y]);
                bar.setSelection(b*(y+1));
                double kkk = b1*((double) y+1.0);
                if(kkk>98.0){
                	container.getShell().close();
                }
                y+=1;
              }
            });
          }
        }
      }.start();
      
      bar.addPaintListener(new PaintListener() {
          public void paintControl(PaintEvent e) {
            String string = (bar.getSelection() * 1.0 /((double)bar.getMaximum()-(double)bar.getMinimum()) * 100.00) + "%";
            
            Point point = bar.getSize();
            Font font = new Font( container.getDisplay(),"Courier",10,SWT.BOLD);
            e.gc.setFont(font);
            e.gc.setForeground( container.getDisplay().getSystemColor(SWT.COLOR_WHITE));
            
            FontMetrics fontMetrics = e.gc.getFontMetrics();
            int stringWidth = fontMetrics.getAverageCharWidth() * string.length();
            int stringHeight = fontMetrics.getHeight();
            
            e.gc.drawString(string, (point.x-stringWidth)/2 , (point.y-stringHeight)/2, true);
            font.dispose();
          }
        });

    
	return container;
}
   @Override
protected boolean isResizable() {
    return true;
}
@Override
protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Downloading results for remote nodes");
}

}
