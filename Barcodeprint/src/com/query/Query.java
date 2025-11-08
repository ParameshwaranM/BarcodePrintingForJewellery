package com.query;

import com.common.Applicationstart;
import com.common.Dbconnection;

public class Query {
public String dbname() {
	StringBuilder sb=new StringBuilder();
	sb.append("select isnull(a.compid,'')as compid,isnull(a.masterdb,'')as masterdb,\n");
	sb.append("isnull(a.trandb,'')as trandb,isnull(stockdb,'')as stockdb,isnull(a.schemedb,'')\n");
	sb.append("as schemedb,isnull(a.catlogdb ,'')as catlogdb from filemain as a\n");
	sb.append("left join computer as b on a.CompId=b.CompCode where IPAdd='");
	sb.append(Applicationstart.getIpid());
	sb.append("' and FinYearFromDate between FromDate and ToDate\n");
	return sb.toString();
    }
public String printer(String description) {
	
	StringBuilder sb=new StringBuilder();
	sb.append("select ISNULL(PrinterName,'')AS PrinterName  from "+Dbconnection.getLstDbname().get(0).getMasterdb()+"..PrinterControl where SystemIP='");
	sb.append(Applicationstart.getIpid());
	sb.append("'and description='"+description+"'");
	return sb.toString();	
    }
public String tagreceipt() {
	
	StringBuilder sb=new StringBuilder();
	
	sb.append("select isnull(c.shortname,'')shortname,isnull(hmuid,'')hmuid,isnull(studDiaWt,0)studDiaWt,isnull(MONTH(tagadddate),0)mnth,isnull(SUBSTRING(cast(year(tagadddate) as varchar), 3,4),0)yr,countercode,isnull(vendorname,'')vendorname,ISNULL(A.tagno,'')AS TAGNO,ISNULL(A.grosswt,'')AS GROSSWT,ISNULL(A.netwt,'')\n");
	sb.append("AS NETWT,ISNULL(A.metalcode,'')AS METALCODE,ISNULL(A.maxwastage,'')AS MAXWASTAGE,\n");
	sb.append("ISNULL(A.maxmc,'')AS MAXMC,ISNULL(A.studstnpcs,'')AS STUDSTNPCS,ISNULL(A.studstnwt,'')\n");
	sb.append("AS STUDSTNWT,ISNULL(A.rate,'')AS RATE,ISNULL(A.rateid,'')AS RATEID,ISNULL(A.rowsign,'')\n");
	sb.append("AS ROWSIGN,ISNULL(A.multimetal,'')AS MULTIMETAL ,ISNULL(B.PRONAME,'') AS PRONAME,\n");
	sb.append("d.sizename as sizename,ISNULL(E.TAGTYPENAME,'')\n");
	sb.append("AS TAGTYPENAME,ISNULL(A.SALEMODE,'')AS SALEMODE,ISNULL(A.PROCODE,'')AS PROCODE,\n");
	sb.append("ISNULL(A.SALEVALUE,'')AS SALEVALUE,ISNULL(A.PIECES,'')AS PIECES FROM\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb());
	sb.append("..TagReceipt AS A\n");
	if(Applicationstart.isDuplicate()==true) {
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getTrandb());
	sb.append("..DuplicateTags AS F on a.TagNo=f.TagNo\n");
	}
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb());
	sb.append("..category AS c ON c.catcode=a.catcode\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb());
	sb.append("..Product AS B ON A.ProCode=B.ProCode\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getMasterdb());
	sb.append("..vendormaster AS vm ON vm.acctcode=a.AcctCode\n");
//	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getTrandb());
//	sb.append("..HMHistory AS C ON A.TAGNO=C.TAGNO\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb());
	sb.append("..OrnSize AS D ON D.SizeCode=A.SizeCode\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb());
	sb.append("..TagType AS E ON E.TagTypeCode=A.TagTypeCode where\n");
	if(Applicationstart.isDuplicate()==true) {
	sb.append("f.uniquekey='"+Applicationstart.getUniquekey()+"'");	
	}
	else {
	sb.append("a.uniquekey='"+Applicationstart.getUniquekey()+"'");
	}
	//System.out.println(sb);
	return sb.toString();	
  }

public String nonTagged() {
	StringBuilder sb=new StringBuilder();
	sb.append("select ISNULL(A.PacketNo,'')AS PacketNo,ISNULL(A.grosswt,'')AS GROSSWT,ISNULL(A.netwt,'')\n");
	sb.append("AS NETWT,ISNULL(A.metalcode,'')AS METALCODE,ISNULL(A.maxwastage,'')AS MAXWASTAGE,\n");
	sb.append("ISNULL(A.maxmc,'')AS MAXMC,\n");
	sb.append("ISNULL(A.rate,'')AS RATE,ISNULL(A.rateid,'')AS RATEID,ISNULL(A.rowsign,'')\n");
	sb.append("AS ROWSIGN,ISNULL(A.multimetal,'')AS MULTIMETAL ,ISNULL(B.PRONAME,'') AS PRONAME,\n");
	sb.append("ISNULL(D.SIZENAME,'')AS SIZENAME,ISNULL(E.TAGTYPENAME,'')\n");
	sb.append("AS TAGTYPENAME,ISNULL(A.SALEMODE,'')AS SALEMODE,ISNULL(A.PROCODE,'')AS PROCODE,\n");
	sb.append("ISNULL(A.SALEVALUE,'')AS SALEVALUE,ISNULL(A.PIECES,'')AS PIECES FROM\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb());
	sb.append(".dbo.NonTagged AS A\n");
	if(Applicationstart.isDuplicate()==true) {
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getTrandb());
	sb.append("..DuplicateTags AS F on a.packetno=f.TagNo\n");
	}
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb());
	sb.append("..Product AS B ON A.ProCode=B.ProCode\n");
//	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getTrandb());
//	sb.append("..HMHistory AS C ON A.PacketNo=C.PacketNo\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb());
	sb.append("..OrnSize AS D ON D.SizeCode=A.SizeCode\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb());
	sb.append("..TagType AS E ON E.TagTypeCode=A.TagTypeCode where\n");
	if(Applicationstart.isDuplicate()==true) {
	sb.append("f.uniquekey='"+Applicationstart.getUniquekey()+"' and a.issrec='r'");	
	}
	else {
	sb.append("a.uniquekey='"+Applicationstart.getUniquekey()+"' and a.issrec='r'");	
	
	}
	//System.out.println(sb);
	return sb.toString();	
}

public String tagStone() {
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(A.PIECES,'')AS PIECES,ISNULL(A.WEIGHT,'')AS WEIGHT , \n");
	sb.append("ISNULL(A.AMOUNT,'')AS AMOUNT,ISNULL(A.TAGROWSIGN,'')AS TAGROWSIGN from\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb());
	sb.append("..Tagstone AS A\n");
	if(Applicationstart.isDuplicate()==true) {
		sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getTrandb());
		sb.append("..DuplicateTags AS F on a.TagNo=f.TagNo\n");
		}
	if(Applicationstart.isDuplicate()==true) {
		sb.append("where f.uniquekey='"+Applicationstart.getUniquekey()+"'");	
		}
		else {
		sb.append("where a.uniquekey='"+Applicationstart.getUniquekey()+"'");	
		}
	return sb.toString();
  }
public String tagMetal() {
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(MetalCode,'')AS METALCODE,ISNULL(GrossWt,'')AS GROSSWT,\n");
	sb.append("ISNULL(Netwt,'')AS NETWT,\n");
	if(Applicationstart.isDuplicate()==true) {
		sb.append("ISNULL(a.TAGROWSIGN,'')AS TAGROWSIGN from\n");
		}
	else {
		sb.append("ISNULL(TAGROWSIGN,'')AS TAGROWSIGN from\n");
	}
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb());
	sb.append("..TagMetal\n");
	if(Applicationstart.isDuplicate()==true) {
		sb.append(" as a LEFT JOIN "+Dbconnection.getLstDbname().get(0).getTrandb());
		sb.append("..DuplicateTags AS F on a.TagNo=f.TagNo\n");
		}
	if(Applicationstart.isDuplicate()==true) {
		sb.append("where f.uniquekey='"+Applicationstart.getUniquekey()+"'");	
		}
		else {
		sb.append("where uniquekey='"+Applicationstart.getUniquekey()+"'");	
		}
	return sb.toString();
  }
public String tagDuplicate() {
	StringBuilder sb=new StringBuilder();
	sb.append("select isnull(numbercontrolcode,'')as numbercontrolcode,UniqueKey from\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb());
	sb.append("..TranRefTable where UniqueKey='"+Applicationstart.getUniquekey()+"'");
	//System.out.println(sb);
	return sb.toString();
  }
public String hmuid(String tagno) {
	StringBuilder sb=new StringBuilder();
	sb.append("select hmuid from ").append(Dbconnection.getLstDbname().get(0).getTrandb()+"..hmhistory where tagno='"+tagno+"'");	
	return sb.toString();
}
}
