package com.common;
import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.security.CodeSource;
import java.util.List;
import javax.swing.JOptionPane;

import com.dao.Dao;
import com.frm.Barcodeprint;
import com.jilaba.common.ReturnStatus;
import com.jilaba.fileresource.FileRead;
import com.jilaba.fileresource.JilabaFile;
import com.model.Tagduplicate;
public class Applicationstart {
private static String uniquekey,printername;
private static String ipid="";
private static InetAddress ipname=null;
private static List<Tagduplicate>lstTagduplicate=null;
private static boolean duplicate;
	public static void main(String[] args) {
	try {
		if(args.length==0) {
			JOptionPane.showMessageDialog(null,"Arguments Empty!!..");
			System.exit(0);
		}
		
		CodeSource codeSource=Applicationstart.class.getProtectionDomain().getCodeSource();
		File file=new File(codeSource.getLocation().getPath());
		FileWriter fileWriter=new FileWriter(file.getParent()+File.separator+"Param.jas");
		fileWriter.write(args[0].toString());
		fileWriter.close();
		
		ReturnStatus returnStatus=null;
		FileRead fileRead=new FileRead();
		returnStatus=fileRead.read(Applicationstart.class,JilabaFile.SERVER);
		if(!returnStatus.isStatus()) {
			JOptionPane.showMessageDialog(null,returnStatus.getDescription());
			System.exit(0);
		  }
		returnStatus=fileRead.read(Applicationstart.class,JilabaFile.REGISTER);
		if(!returnStatus.isStatus()) {
			JOptionPane.showMessageDialog(null,returnStatus.getDescription());
			System.exit(0);	
		  }

		if(args.length>0) {
			String[] arg=args[0].split("\\|");
			if(args[0].contains("|")) {
				uniquekey=arg[3];
			}
			else {
				uniquekey=arg[0];
			}
		}
		ipname=InetAddress.getLocalHost();
		ipid=ipname.getHostAddress();
		new Dbconnection();
		Dao dao=new Dao();
		printername=dao.printer("Barcodeprinting");
		if("".equalsIgnoreCase(printername)) {
			JOptionPane.showMessageDialog(null,"Set The Printer Control!!..");
			System.exit(0);	
		}
		lstTagduplicate=dao.tagDuplicate();
		for (int i = 0; i < lstTagduplicate.size(); i++) {
		 Tagduplicate tagduplicate=lstTagduplicate.get(i);
		 if("DUPLICATETAGNO".equalsIgnoreCase(tagduplicate.getNumbercontrolcode())) {
			 uniquekey=tagduplicate.getUniquekey();
			 setDuplicate(true);
		 }
		}
		new Barcodeprint();
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage());
	}
 }
	public static String getIpid() {
		return ipid;
	}

	public static void setIpid(String ipid) {
		Applicationstart.ipid = ipid;
	}

	public static String getUniquekey() {
		return uniquekey;
	}
	public static void setUniquekey(String uniquekey) {
		Applicationstart.uniquekey = uniquekey;
	}
	public static String getPrintername() {
		return printername;
	}
	public static void setPrintername(String printername) {
		Applicationstart.printername = printername;
	}
	public static boolean isDuplicate() {
	return duplicate;
    }
    public static void setDuplicate(boolean duplicate) {
	Applicationstart.duplicate = duplicate;
    } 

}
