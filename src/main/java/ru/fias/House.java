package ru.fias;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "HOUSE")

public class House extends FiasObject {
    @XmlAttribute(name = "ID", required = true)
    protected BigInteger id;

    @XmlAttribute(name = "OBJECTID", required = true)
    protected BigInteger objectid;

    @XmlAttribute(name = "OBJECTGUID", required = true)
    protected UUID objectguid;

    @XmlAttribute(name = "HOUSENUM", required = true)
    protected String housenum;

    @XmlAttribute(name = "HOUSETYPE", required = true)
    protected BigInteger housetype;

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

    @XmlAttribute(name = "ADDNUM1", required = true)
    protected String addnum1;

    @XmlAttribute(name = "ADDNUM2", required = true)
    protected String addnum2;

    private String insertStatement = "insert into fias_gar.houses(id, objectid, objectguid, housenum, housetype, previd, nextid, updatedate, startdate, enddate, isactual, isactive, addnum1, addnum2)";

    private String updateStatement = "on conflict (id) do update set objectid = excluded.objectid, objectguid = excluded.objectguid, housenum = excluded.housenum, " +
            "housetype = excluded.housetype, previd = excluded.previd, nextid = excluded.nextid, " +
            "updatedate = excluded.updatedate, startdate = excluded.startdate, enddate = excluded.enddate, isactual = excluded.isactual, " +
            "addnum1 = excluded.addnum2, addnum2 = excluded.addnum2, isactive = excluded.isactive";

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

    public String getHousenum() {
        return housenum;
    }

    public void setHousenum(String housenum) {
        this.housenum = housenum;
    }

    public String getAddnum1() {
        return addnum1;
    }

    public void setAddnum1(String addnum1) {
        this.addnum1 = addnum1;
    }

    public String getAddnum2() {
        return addnum2;
    }

    public void setAddnum2(String addnum2) {
        this.addnum2 = addnum2;
    }

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
        //"insert into fias_gar.houses(id, objectid, objectguid, housenum, housetype, previd, nextid, updatedate, startdate, enddate, isactual, isactive)"
        this.insertStatement += " values (" +
                this.id + ", " +
                this.objectid + ", '" +
                this.objectguid.toString() + "'::uuid, '" +
                this.housenum + "', " +
                this.housetype + ", " +
                this.previd  + ", " +
                this.nextid + ", '" +
                this.updatedate.toString() + "'::date, '" +
                this.startdate.toString() + "'::date, '" +
                this.enddate.toString() + "'::date, " +
                this.isactual + ", " +
                this.isactive + ", " +
                "nullif('" + this.addnum1 + "','null'), " +
                "nullif('" + this.addnum2 + "','null'), " +
                this.updateStatement;
        return this.insertStatement;
    }
}
