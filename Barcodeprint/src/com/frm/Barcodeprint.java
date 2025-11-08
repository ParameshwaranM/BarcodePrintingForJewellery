package com.frm;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.common.Applicationstart;
import com.dao.Dao;
import com.model.Nontagged;
import com.model.Tagmetal;
import com.model.Tagreceipt;
import com.model.Tagstone;

public class Barcodeprint {
	private List<Tagreceipt> lstTagreceipt = null;
	private List<Tagstone> lstTagstone = null;
	private List<Tagmetal> lstTagmetal = null;
	private Dao dao = new Dao();
	private DecimalFormat wf = new DecimalFormat("0.000");
	private DecimalFormat af = new DecimalFormat("0.00");
	private DecimalFormat tf = new DecimalFormat("0");
	private List<Nontagged> lstNontagged = null;
	private List<Tagreceipt>lstHmuid=null;

	public Barcodeprint() {
		try {
			lstTagreceipt = new ArrayList<Tagreceipt>();
			lstTagreceipt = dao.tagreceipt();
			lstTagmetal = new ArrayList<Tagmetal>();
			lstTagmetal = dao.tagMetal();
			lstNontagged = new ArrayList<Nontagged>();
			lstNontagged = dao.nonTagged();
			CodeSource codeSource = Applicationstart.class.getProtectionDomain().getCodeSource();
			File file = new File(codeSource.getLocation().getPath());
			FileWriter fileWriter = new FileWriter(file.getParent() + File.separator + "CTR.jas");
			String filename = file.getParent() + File.separator + "CTR.jas";
			barprint(fileWriter, file);
			batchFile(filename);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void barprint(FileWriter fileWriter, File file) throws Exception {
		if (lstTagreceipt.size() > 0) {
			double   studStnWeight = 0,studDiaWt=0;
			int studstnPcs=0;
			for (int i = 0; i < lstTagreceipt.size(); i++) {
				Tagreceipt tagreceipt = lstTagreceipt.get(i);
				printHeader(fileWriter, file);
				fileWriter.write(
						"A795,097,2,3,1,1,N,\"" + tagreceipt.getProcode() +tagreceipt.getProname().substring(0,1)+ "-" + tagreceipt.getTagno() + "\"" + "\n");
				
				fileWriter.write(
						"A550,097,2,3,1,1,N,\"" + tagreceipt.getProcode() +tagreceipt.getProname().substring(0,1)+ "-" + tagreceipt.getTagno() + "\"" + "\n");
				
				fileWriter.write("b350,07,Q,m2,s3,eL,iA,\"" + tagreceipt.getTagno() + "\"" + "\n");
//				fileWriter.write("A680,060,2,2,1,1,N,\"" + tagreceipt.getProname() + "\"" + "\n");
//				if (!"".equalsIgnoreCase(tagreceipt.getHmuid())) {
//
					
//				}
//		if(!"".equalsIgnoreCase(tagreceipt.getSizename())) {
//		fileWriter.write("A340,05,2,2,1,1,N,\"SIZE:"+tagreceipt.getSizename()+"\""+"\n");	
//		 }
				if (!"".equalsIgnoreCase(tagreceipt.getVendorName())) {
					fileWriter.write("A550,020,2,3,1,1,N,\"" + tagreceipt.getVendorName() + "\"" + "\n");	
				}
				if ("N".equalsIgnoreCase(tagreceipt.getMultimetal())) {
					if ("W".equalsIgnoreCase(tagreceipt.getSalemode())) {
//		    fileWriter.write("A505,080,2,2,1,1,N,\"GWT:"+wf.format(tagreceipt.getGrosswt())+"\""+"\n");
						fileWriter.write("A550,072,2,3,1,1,N,\"WT:" + wf.format(tagreceipt.getNetwt()) + "\"" + "\n");
						
						fileWriter.write("A795,072,2,3,1,1,N,\"WT:" + wf.format(tagreceipt.getNetwt()) + "\"" + "\n");
						
						if(tagreceipt.getSizename()>0) {
							fileWriter.write("A655,072,2,3,1,1,N,\"/"+tagreceipt.getSizename()+"\""+"\n");
						}
						lstHmuid=dao.getHmuid(tagreceipt.getTagno());
						if(lstHmuid.size()>0) {
						
							 fileWriter.write("A795,020,2,3,1,1,N,\""+lstHmuid.get(0).getHmuid()+"\""+"\n");
						if(lstHmuid.size()==2) {
							 fileWriter.write("A710,020,2,3,1,1,N,\","+lstHmuid.get(1).getHmuid()+"\""+"\n");
						}
						}else {
							fileWriter.write("A795,020,2,3,1,1,N,\"MC:"+tagreceipt.getMaxmc()+"\""+"\n");
						}
		   
						
						int wasv=0;
						
						 wasv=(int) (tagreceipt.getMaxwastage()*100);
						
//						if(wasv<10) {
//							wasv*=1000;
//						}else if(wasv<100) {
//							wasv*=100;
//						}else {
//							wasv*=10;
//						}
						 String was;
						 if(tagreceipt.getMaxwastage()<1) {
								was=tagreceipt.getCounterCode()+"0"+wasv+""+tagreceipt.getMnthoftag()+""+tagreceipt.getYroftag()+""+tagreceipt.getCounterCode();
							}else {
								 was=tagreceipt.getCounterCode()+""+wasv+""+tagreceipt.getMnthoftag()+""+tagreceipt.getYroftag()+""+tagreceipt.getCounterCode();
							}		 
						
		                fileWriter.write("A550,043,2,3,1,1,N,\""+was+"\""+"\n");	
					} else if ("P".equalsIgnoreCase(tagreceipt.getSalemode())) {
						fileWriter.write("A505,080,2,2,1,1,N,\"PIECES:" + tagreceipt.getPieces() + "\"" + "\n");
						fileWriter.write("A505,060,2,2,1,1,N,\"RATE:" + af.format(tagreceipt.getRate()) + "\"" + "\n");
						fileWriter.write(
								"A505,040,2,2,1,1,N,\"RATEID:" + af.format(tagreceipt.getRateid()) + "\"" + "\n");
						fileWriter.write(
								"A505,020,2,2,1,1,N,\"SALEVALUE:" + af.format(tagreceipt.getSalevalue()) + "\"" + "\n");
					}
//		else if("M".equalsIgnoreCase(tagreceipt.getSalemode())) {
//			fileWriter.write("A505,080,2,2,1,1,N,\"GWT:"+wf.format(tagreceipt.getGrosswt())+"\""+"\n");
//			fileWriter.write("A505,060,2,2,1,1,N,\"NWT:"+wf.format(tagreceipt.getNetwt())+"\""+"\n");	
//			fileWriter.write("A505,040,2,2,1,1,N,\"PIECES:"+tagreceipt.getPieces()+"\""+"\n");	
//			fileWriter.write("A505,020,2,2,1,1,N,\"RATE:"+af.format(tagreceipt.getRate())+"\""+"\n");	
//			  }
//		else {
//			fileWriter.write("A505,080,2,2,1,1,N,\"GWT:"+wf.format(tagreceipt.getGrosswt())+"\""+"\n");
//		    fileWriter.write("A505,060,2,2,1,1,N,\"NWT:"+wf.format(tagreceipt.getNetwt())+"\""+"\n");	
//		    fileWriter.write("A505,040,2,2,1,1,N,\"MC:"+af.format(tagreceipt.getMaxmc())+"\""+"\n");	
//		    fileWriter.write("A505,020,2,2,1,1,N,\"WAS:"+wf.format(tagreceipt.getMaxwastage())+"\""+"\n");	
//		    }
					lstTagstone = new ArrayList<Tagstone>();
					lstTagstone = dao.tagStone();

					if (lstTagstone.size() > 0) {
						for (int j = 0; j < lstTagstone.size(); j++) {
							Tagstone tagstone = lstTagstone.get(j);
							if (tagreceipt.getRowsign().equalsIgnoreCase(tagstone.getTagrowsign())) {
								studstnPcs += tagreceipt.getStudstnpcs();
								studStnWeight += tagreceipt.getStudstnwt();
								
//								break;
							}
						}
						if(tagreceipt.getSizename()>0) {
						fileWriter.write("A605,010,1,3,1,1,N,\"" + tagreceipt.getSizename() + "\"" + "\n");
						}
						if(tagreceipt.getShrtname().equalsIgnoreCase("")) {
							fileWriter.write("A605,015,1,3,1,1,N,\"" + tagreceipt.getShrtname() + "\"" + "\n");
						}
						fileWriter.write("A795,043,2,3,1,1,N,\"" + studstnPcs +"/" +wf.format(studStnWeight) + "\"" + "\n");
					}
//			if(lstTagstone.size()>0) {
//				int x1=680,x2=710,x3=710,x4=710;
//				printFooter(fileWriter, file);
//				printHeader(fileWriter, file);
//			    for (int j = 0; j < lstTagstone.size(); j++) {
//				Tagstone tagstone=lstTagstone.get(j);
//				if(tagreceipt.getRowsign().equalsIgnoreCase(tagstone.getTagrowsign())) {
//					if(j%2==0&&j>0) {
//						printFooter(fileWriter, file);
//						printHeader(fileWriter, file);
//						x1=x1+400;
//						x2=x2+410;
//						x3=x3+410;
//						x4=x4+410;
//						}
//				fileWriter.write("A"+String.valueOf(x1)+",080,2,2,1,1,N,\""+tagstone.getProname()+"\""+"\n");
//				x1=x1-200;
//				fileWriter.write("A"+String.valueOf(x2)+",060,2,2,1,1,N,\""+tagstone.getPieces()+"\""+"\n");
//				x2=x2-205;
//				fileWriter.write("A"+String.valueOf(x3)+",040,2,2,1,1,N,\""+tagstone.getWeight()+"\""+"\n");
//				x3=x3-205;
//				fileWriter.write("A"+String.valueOf(x4)+",020,2,2,1,1,N,\""+tagstone.getAmount()+"\""+"\n");
//				x4=x4-205;
//				  }
//			    }
//			}
				} else {

					if ("W".equalsIgnoreCase(tagreceipt.getSalemode())) {
						if (lstTagmetal.size() > 0) {
							int x01 = 80;
							for (int j = 0; j < lstTagmetal.size(); j++) {
								Tagmetal tagmetal = lstTagmetal.get(j);
								if (tagreceipt.getRowsign().equalsIgnoreCase(tagmetal.getTagrowsign())) {
									fileWriter.write(
											"A505,0" + String.valueOf(x01) + ",2,2,1,1,N,\"" + tagmetal.getMetalcode()
													+ " GWT:" + wf.format(tagmetal.getGrosswt()) + "\"" + "\n");
									x01 = x01 - 20;
								}
							}
							fileWriter
									.write("A505,040,2,2,1,1,N,\"MC:" + af.format(tagreceipt.getMaxmc()) + "\"" + "\n");
							fileWriter.write(
									"A505,020,2,2,1,1,N,\"WAS:" + wf.format(tagreceipt.getMaxwastage()) + "\"" + "\n");
						}
					} else if ("P".equalsIgnoreCase(tagreceipt.getSalemode())) {
						fileWriter.write("A505,080,2,2,1,1,N,\"PIECES:" + tagreceipt.getPieces() + "\"" + "\n");
						fileWriter.write("A505,060,2,2,1,1,N,\"RATE:" + af.format(tagreceipt.getRate()) + "\"" + "\n");
						fileWriter.write(
								"A505,040,2,2,1,1,N,\"RATEID:" + af.format(tagreceipt.getRateid()) + "\"" + "\n");
						fileWriter.write(
								"A505,020,2,2,1,1,N,\"SALEVALUE:" + af.format(tagreceipt.getSalevalue()) + "\"" + "\n");
					} else if ("M".equalsIgnoreCase(tagreceipt.getSalemode())) {
						if (lstTagmetal.size() > 0) {
							int x01 = 80;
							for (int j = 0; j < lstTagmetal.size(); j++) {
								Tagmetal tagmetal = lstTagmetal.get(j);
								if (tagreceipt.getRowsign().equalsIgnoreCase(tagmetal.getTagrowsign())) {
									fileWriter.write(
											"A505,0" + String.valueOf(x01) + ",2,2,1,1,N,\"" + tagmetal.getMetalcode()
													+ " GWT:" + wf.format(tagmetal.getGrosswt()) + "\"" + "\n");
									x01 = x01 - 20;
								}
							}
							fileWriter.write("A505,040,2,2,1,1,N,\"PIECES:" + tagreceipt.getPieces() + "\"" + "\n");
							fileWriter.write(
									"A505,020,2,2,1,1,N,\"RATE:" + af.format(tagreceipt.getRate()) + "\"" + "\n");
						}
					} else {
						if (lstTagmetal.size() > 0) {
							int x01 = 80;
							for (int j = 0; j < lstTagmetal.size(); j++) {
								Tagmetal tagmetal = lstTagmetal.get(j);
								if (tagreceipt.getRowsign().equalsIgnoreCase(tagmetal.getTagrowsign())) {
									fileWriter.write(
											"A505,0" + String.valueOf(x01) + ",2,2,1,1,N,\"" + tagmetal.getMetalcode()
													+ " GWT:" + wf.format(tagmetal.getGrosswt()) + "\"" + "\n");
									x01 = x01 - 20;
								}
							}
							fileWriter
									.write("A505,040,2,2,1,1,N,\"MC:" + af.format(tagreceipt.getMaxmc()) + "\"" + "\n");
							fileWriter.write(
									"A505,020,2,2,1,1,N,\"WAS:" + wf.format(tagreceipt.getMaxwastage()) + "\"" + "\n");
						}
					}
					lstTagstone = new ArrayList<Tagstone>();
					lstTagstone = dao.tagStone();
					if (lstTagstone.size() > 0) {
						for (int j = 0; j < lstTagstone.size(); j++) {
							Tagstone tagstone = lstTagstone.get(j);
							if (tagreceipt.getRowsign().equalsIgnoreCase(tagstone.getTagrowsign())) {
								fileWriter.write("A680,040,2,2,1,1,N,\"" + tagreceipt.getStudstnpcs() + "\\"
										+ tagreceipt.getStudstnwt() + "\"" + "\n");
								break;
							}
						}
					}
					if (lstTagstone.size() > 0) {
						int x1 = 680, x2 = 710, x3 = 710, x4 = 710;
						printFooter(fileWriter, file);
						printHeader(fileWriter, file);
						for (int j = 0; j < lstTagstone.size(); j++) {
							Tagstone tagstone = lstTagstone.get(j);
							if (tagreceipt.getRowsign().equalsIgnoreCase(tagstone.getTagrowsign())) {
								if (j % 2 == 0 && j > 0) {
									printFooter(fileWriter, file);
									printHeader(fileWriter, file);
									x1 = x1 + 400;
									x2 = x2 + 410;
									x3 = x3 + 410;
									x4 = x4 + 410;
								}
								fileWriter.write("A" + String.valueOf(x1) + ",080,2,2,1,1,N,\"" + tagstone.getProname()
										+ "\"" + "\n");
								x1 = x1 - 200;
								fileWriter.write("A" + String.valueOf(x2) + ",060,2,2,1,1,N,\"" + tagstone.getPieces()
										+ "\"" + "\n");
								x2 = x2 - 205;
								fileWriter.write("A" + String.valueOf(x3) + ",040,2,2,1,1,N,\"" + tagstone.getWeight()
										+ "\"" + "\n");
								x3 = x3 - 205;
								fileWriter.write("A" + String.valueOf(x4) + ",020,2,2,1,1,N,\"" + tagstone.getAmount()
										+ "\"" + "\n");
								x4 = x4 - 205;
							}
						}
					}
				}
				printFooter(fileWriter, file);
			}
		} else if (lstNontagged.size() > 0) {
			for (int i = 0; i < lstNontagged.size(); i++) {
				Nontagged nontagged = lstNontagged.get(i);
				fileWriter.write(
						"A750,080,2,2,1,1,N,\"" + nontagged.getProcode() + "-" + nontagged.getPacketno() + "\"" + "\n");
				fileWriter.write("b350,07,Q,m2,s3,eL,iA,\"" + nontagged.getPacketno() + "\"" + "\n");
				fileWriter.write("A680,060,2,2,1,1,N,\"" + nontagged.getProname() + "\"" + "\n");
				if (!"".equalsIgnoreCase(nontagged.getHmuid())) {
					fileWriter.write("A680,040,2,2,1,1,N,\"HMUID:" + nontagged.getHmuid() + "\"" + "\n");
				}
				if (nontagged.getGrosswt() != 0) {
					fileWriter.write("A505,080,2,2,1,1,N,\"GWT:" + wf.format(nontagged.getGrosswt()) + "\"" + "\n");
				}
				if (nontagged.getNetwt() != 0) {
					fileWriter.write("A505,060,2,2,1,1,N,\"NWT:" + wf.format(nontagged.getNetwt()) + "\"" + "\n");
				}
				if (nontagged.getMaxmc() != 0) {
					fileWriter.write("A505,040,2,2,1,1,N,\"MC:" + af.format(nontagged.getMaxmc()) + "\"" + "\n");
				}
				if (nontagged.getMaxwastage() != 0) {
					fileWriter.write("A505,020,2,2,1,1,N,\"WAS:" + wf.format(nontagged.getMaxwastage()) + "\"" + "\n");
				}
			}
		}
		fileWriter.close();
	}

	private void printHeader(FileWriter fileWriter, File file) throws Exception {
		fileWriter.write("I8,A" + "\n");
		fileWriter.write("q812" + "\n");
		fileWriter.write("0" + "\n");
		fileWriter.write("JF" + "\n");
		fileWriter.write("D12" + "\n");
		fileWriter.write("ZT" + "\n");
		fileWriter.write("Q176,25" + "\n");
		fileWriter.write("N" + "\n");

	}

	private void printFooter(FileWriter fileWriter, File file) throws Exception {
		fileWriter.write("P01" + "\n");

	}

	private void batchFile(String filename) throws Exception {
		CodeSource codeSource = Applicationstart.class.getProtectionDomain().getCodeSource();
		File file1 = new File(codeSource.getLocation().getPath());
		String jasfile = file1.getParent() + File.separator + "Batchfile.bat";

		StringBuilder printCommand = new StringBuilder("Type " + filename + ">\\\\" + Applicationstart.getIpid() + "\\"
				+ Applicationstart.getPrintername() + "\nExit");
		Files.write(Paths.get(jasfile), printCommand.toString().getBytes());

		String[] command = { "cmd.exe", "/c", "start", jasfile };
		Runtime.getRuntime().exec(command);

	}

}
