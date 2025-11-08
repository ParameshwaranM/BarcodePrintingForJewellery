package com.common;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.dao.Dao;
import com.jilaba.fileresource.Register;
import com.jilaba.fileresource.Server;
import com.model.Dbname;
public class Dbconnection {
private static String driver,connectionstring;
public static Connection connection;
private static List<Dbname>lstDbname=null;
	@SuppressWarnings("deprecation")
	public Dbconnection() {
		try {
			driver="net.sourceforge.jtds.jdbc.Driver";
			Class.forName(driver).newInstance();
			String compydb=Register.getCompanyId()+"compydb";
			getconnection(compydb);
			Dao dao=new Dao();
			lstDbname=new ArrayList<Dbname>();
			lstDbname=dao.dbname();
			if(lstDbname.size()==0) {
				JOptionPane.showMessageDialog(null,"Operator Login Failed!!..");
				System.exit(0);	
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	private void getconnection(String compydb) throws Exception {
		connectionstring="jdbc:jtds:sqlserver://"+Server.getServerName()+":"+Server.getPortNo()+"/"+compydb;
		connection=DriverManager.getConnection(connectionstring,Server.getUserName(),Server.getPassword());
		//JOptionPane.showMessageDialog(null, compydb+"connected!!!");	
	}
	public static List<Dbname> getLstDbname() {
		return lstDbname;
	}
}
