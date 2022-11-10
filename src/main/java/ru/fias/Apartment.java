package ru.fias;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "APARTMENT")

public class Apartment extends FiasObject {
    @XmlAttribute(name = "ID", required = true)
    protected BigInteger id;

    @XmlAttribute(name = "OBJECTID", required = true)
    protected BigInteger objectid;

    @XmlAttribute(name = "OBJECTGUID", required = true)
    protected UUID objectguid;

    @XmlAttribute(name = "NUMBER", required = true)
    protected String appartnum;

    @XmlAttribute(name = "APARTTYPE", required = true)
    protected BigInteger aparttype;

    @XmlAttribute(name = "PREVID", required = true)
    protected BigInteger previd;

    @XmlAttribute(name = "NEXTID", required = true)
    protected BigInteger nextid;

    @XmlAttribute(name = "UPDATEDATE", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar updatedate;

    @XmlAttribute(name = "STARTDATE", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startdate;

    @XmlAttribute(name = "ENDDATE", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar enddate;

    @XmlAttribute(name = "ISACTUAL", required = true)
    protected int isactual;

    @XmlAttribute(name = "ISACTIVE", required = true)
    protected int isactive;

    private String insertStatement = "insert into fias_gar.apartments(id, objectid, objectguid, appartnum, aparttype, previd, nextid, updatedate, startdate, enddate, isactual, isactive)";

    private String updateStatement = "on conflict (id) do update set objectid = excluded.objectid, objectguid = excluded.objectguid, appartnum = excluded.appartnum, " +
            "aparttype = excluded.aparttype, previd = excluded.previd, nextid = excluded.nextid, " +
            "updatedate = excluded.updatedate, startdate = excluded.startdate, enddate = excluded.enddate, isactual = excluded.isactual, isactive = excluded.isactive";

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getObjectid() {
        return objectid;
    }

    public void setObjectid(BigInteger objectid) {
        this.objectid = objectid;
    }

    public UUID getObjectguid() {
        return objectguid;
    }

    public void setObjectguid(UUID objectguid) {
        this.objectguid = objectguid;
    }

    public String getAppartnum() {
        return appartnum;
    }

    public void setAppartnum(String appartnum) {
        this.appartnum = appartnum;
    }

    public BigInteger getAparttype() {return aparttype;}

    public void setAparttype(BigInteger aparttype) {this.aparttype = aparttype; }

    public BigInteger getPrevid() {
        return previd;
    }

    public void setPrevid(BigInteger previd) {
        this.previd = previd;
    }

    public BigInteger getNextid() {
        return nextid;
    }

    public void setNextid(BigInteger nextid) {
        this.nextid = nextid;
    }

    public XMLGregorianCalendar getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(XMLGregorianCalendar updatedate) {
        this.updatedate = updatedate;
    }

    public XMLGregorianCalendar getStartdate() {
        return startdate;
    }

    public void setStartdate(XMLGregorianCalendar startdate) {
        this.startdate = startdate;
    }

    public XMLGregorianCalendar getEnddate() {
        return enddate;
    }

    public void setEnddate(XMLGregorianCalendar enddate) {
        this.enddate = enddate;
    }

    public int getIsactual() {
        return isactual;
    }

    public void setIsactual(int isactual) {
        this.isactual = isactual;
    }

    public int getIsactive() {
        return isactive;
    }

    public void setIsactive(int isactive) {
        this.isactive = isactive;
    }

    @Override
    public String getInsertStatement(){
        //"insert into fias_gar.apartments(id, objectid, objectguid, appartnum, aparttype, previd, nextid, updatedate, startdate, enddate, isactual, isactive)"
        this.insertStatement += " values (" +
                this.id + ", " +
                this.objectid + ", '" +
                this.objectguid.toString() + "'::uuid, '" +
                this.appartnum + "', " +
                this.aparttype + ", " +
                this.previd  + ", " +
                this.nextid + ", '" +
                this.updatedate.toString() + "'::date, '" +
                this.startdate.toString() + "'::date, '" +
                this.enddate.toString() + "'::date, " +
                this.isactual + ", " +
                this.isactive + ") " +
                this.updateStatement;
        return this.insertStatement;
    }
}
