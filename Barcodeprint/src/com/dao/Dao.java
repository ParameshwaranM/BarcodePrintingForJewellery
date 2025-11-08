package com.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.common.Dbconnection;
import com.model.Dbname;
import com.model.Nontagged;
import com.model.Tagduplicate;
import com.model.Tagmetal;
import com.model.Tagreceipt;
import com.model.Tagstone;
import com.query.Query;
public class Dao {
private List<Dbname>lstDbname=null;
private List<Tagreceipt>lstTagreceipt=null;
private List<Tagduplicate>lsttagDuplicate=null;
private List<Tagstone>lstTagstone=null;
private List<Tagmetal>lstTagmetal=null;
private List<Nontagged>lstNontagged=null;
public static Connection connection;
public static Statement statement;
public static ResultSet resultSet;
private String printername="";
private Query query=new Query();
public List<Dbname>dbname() throws Exception{
	lstDbname=new ArrayList<Dbname>();
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.dbname());
	while(resultSet.next()) {
	Dbname dbname=new Dbname();
	dbname.setCompid(resultSet.getString("compid"));
	dbname.setMasterdb(resultSet.getString("masterdb"));
	dbname.setTrandb(resultSet.getString("trandb"));
	dbname.setStockdb(resultSet.getString("stockdb"));
	dbname.setSchemedb(resultSet.getString("schemedb"));
	dbname.setCatlogdb(resultSet.getString("catlogdb"));
	lstDbname.add(dbname);
	}
	return lstDbname;
  }

public String printer(String description) throws Exception {
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.printer(description));
	if(resultSet.next()) {
		printername=resultSet.getString("printername");
	}
	return printername;	
  }

public List<Tagreceipt>tagreceipt() throws Exception{
	lstTagreceipt =new ArrayList<Tagreceipt>();
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.tagreceipt());
	while(resultSet.next()) {
		Tagreceipt tagreceipt=new Tagreceipt();
		
		tagreceipt.setShrtname(resultSet.getString("shortname"));
		tagreceipt.setYroftag(resultSet.getInt("yr"));
		tagreceipt.setMnthoftag(resultSet.getInt("mnth"));
		tagreceipt.setTagno(resultSet.getString("TAGNO"));
		tagreceipt.setHmuid(resultSet.getString("HMUID"));
		tagreceipt.setMetalcode(resultSet.getString("METALCODE"));
		tagreceipt.setMultimetal(resultSet.getString("MULTIMETAL"));
		tagreceipt.setProname(resultSet.getString("PRONAME"));
		tagreceipt.setRowsign(resultSet.getString("ROWSIGN"));
		tagreceipt.setSizename(resultSet.getInt("SIZENAME"));
		tagreceipt.setTagtypename(resultSet.getString("TAGTYPENAME"));
		tagreceipt.setStudstnpcs(resultSet.getInt("STUDSTNPCS"));
		tagreceipt.setStudstnwt(resultSet.getDouble("STUDSTNWT"));
		tagreceipt.setRate(resultSet.getDouble("RATE"));
		tagreceipt.setRateid(resultSet.getDouble("RATEID"));
		tagreceipt.setMaxmc(resultSet.getDouble("MAXMC"));
		tagreceipt.setMaxwastage(resultSet.getDouble("MAXWASTAGE"));
		tagreceipt.setGrosswt(resultSet.getDouble("GROSSWT"));
		tagreceipt.setNetwt(resultSet.getDouble("NETWT"));
		tagreceipt.setSalemode(resultSet.getString("SALEMODE"));
		tagreceipt.setProcode(resultSet.getInt("PROCODE"));
		tagreceipt.setSalevalue(resultSet.getDouble("SALEVALUE"));
		tagreceipt.setPieces(resultSet.getInt("PIECES"));
		tagreceipt.setVendorName(resultSet.getString("Vendorname"));
	    tagreceipt.setStudDiaWt(resultSet.getDouble("studDiaWt"));
		tagreceipt.setCounterCode(resultSet.getInt("countercode"));
		lstTagreceipt.add(tagreceipt);
	}
	return lstTagreceipt;
   }

public List<Nontagged>nonTagged() throws Exception{
	lstNontagged =new ArrayList<Nontagged>();
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.nonTagged());
	while(resultSet.next()) {
		Nontagged nontagged=new Nontagged();
		nontagged.setPacketno(resultSet.getString("PACKETNO"));
		nontagged.setHmuid(resultSet.getString("HMUID"));
		nontagged.setMetalcode(resultSet.getString("METALCODE"));
		nontagged.setMultimetal(resultSet.getString("MULTIMETAL"));
		nontagged.setProname(resultSet.getString("PRONAME"));
		nontagged.setRowsign(resultSet.getString("ROWSIGN"));
		nontagged.setSizename(resultSet.getString("SIZENAME"));
		nontagged.setTagtypename(resultSet.getString("TAGTYPENAME"));
		nontagged.setRate(resultSet.getDouble("RATE"));
		nontagged.setRateid(resultSet.getDouble("RATEID"));
		nontagged.setMaxmc(resultSet.getDouble("MAXMC"));
		nontagged.setMaxwastage(resultSet.getDouble("MAXWASTAGE"));
		nontagged.setGrosswt(resultSet.getDouble("GROSSWT"));
		nontagged.setNetwt(resultSet.getDouble("NETWT"));
		nontagged.setSalemode(resultSet.getString("SALEMODE"));
		nontagged.setProcode(resultSet.getInt("PROCODE"));
		nontagged.setSalevalue(resultSet.getDouble("SALEVALUE"));
		nontagged.setPieces(resultSet.getInt("PIECES"));
		lstNontagged.add(nontagged);
	}
	return lstNontagged;
   }

public List<Tagstone>tagStone() throws Exception{
	lstTagstone=new ArrayList<Tagstone>();
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.tagStone());
	while(resultSet.next()) {
		Tagstone tagstone=new Tagstone();
//		tagstone.setProname(resultSet.getString("PRONAME"));
		tagstone.setPieces(resultSet.getInt("PIECES"));
		tagstone.setWeight(resultSet.getDouble("WEIGHT"));
		tagstone.setAmount(resultSet.getDouble("AMOUNT"));
		tagstone.setTagrowsign(resultSet.getString("TAGROWSIGN"));
		
		lstTagstone.add(tagstone);
	}
	return lstTagstone;
   }
public List<Tagduplicate>tagDuplicate() throws Exception{
	lsttagDuplicate=new ArrayList<Tagduplicate>();
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.tagDuplicate());
	while(resultSet.next()) {
		Tagduplicate tagduplicate=new Tagduplicate();
		tagduplicate.setNumbercontrolcode(resultSet.getString("NUMBERCONTROLCODE"));
		tagduplicate.setUniquekey(resultSet.getString("UNIQUEKEY"));
		lsttagDuplicate.add(tagduplicate);
	   }
	return lsttagDuplicate;
   }
public List<Tagmetal>tagMetal() throws Exception{
	lstTagmetal=new ArrayList<Tagmetal>();
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.tagMetal());
	while (resultSet.next()) {
		Tagmetal tagmetal=new Tagmetal();
		tagmetal.setMetalcode(resultSet.getString("METALCODE"));
		tagmetal.setGrosswt(resultSet.getDouble("GROSSWT"));
		tagmetal.setNetwt(resultSet.getDouble("NETWT"));
		tagmetal.setTagrowsign(resultSet.getString("TAGROWSIGN"));
		lstTagmetal.add(tagmetal);
	}
	return lstTagmetal;
   }

public List<Tagreceipt> getHmuid(String tagno) throws Exception {
	
	List<Tagreceipt>lstHmuid=new ArrayList<Tagreceipt>();
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.hmuid(tagno));
	while(resultSet.next()) {
		Tagreceipt tagreceipt=new Tagreceipt();
		tagreceipt.setHmuid(resultSet.getString("hmuid"));
		lstHmuid.add(tagreceipt);
	}
	return lstHmuid;
}
}
