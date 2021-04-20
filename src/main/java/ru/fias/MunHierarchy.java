package ru.fias;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ITEM")
public class MunHierarchy extends FiasObject {

    @XmlAttribute(name = "ID", required = true)
    protected BigInteger id;

    @XmlAttribute(name = "OBJECTID", required = true)
    protected BigInteger objectid;

    @XmlAttribute(name = "PARENTOBJID")
    protected BigInteger parentobjid;

    @XmlAttribute(name = "OKTMO")
    protected String oktmo;

    @XmlAttribute(name = "PREVID")
    protected BigInteger previd;

    @XmlAttribute(name = "NEXTID")
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

    @XmlAttribute(name = "ISACTIVE", required = true)
    protected int isactive;

    private String insertStatement = "insert into fias_gar.mun_hierarchy (id, objectid, parentobjid, oktmo, previd, nextid, updatedate, startdate, enddate, isactive)";

    private String updateStatement = "on conflict (id) do update set objectid = excluded.objectid, parentobjid = excluded.parentobjid, oktmo = excluded.oktmo, " +
            "previd = excluded.previd, nextid = excluded.nextid, updatedate = excluded.updatedate, " +
            "startdate = excluded.startdate, enddate = excluded.enddate, isactive = excluded.isactive";

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

    public BigInteger getParentobjid() {
        return parentobjid;
    }

    public void setParentobjid(BigInteger parentobjid) {
        this.parentobjid = parentobjid;
    }

    public String getOktmo() {
        if (oktmo != null) {
            return "'" + oktmo + "'";
        }
        else {
            return null;
        }
    }

    public void setOktmo(String oktmo) {
        this.oktmo = oktmo;
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

    public int getIsactive() {
        return isactive;
    }

    public void setIsactive(int isactive) {
        this.isactive = isactive;
    }

    @Override
    public String getInsertStatement() {
        this.insertStatement += " values (" +
                this.id + ", " +
                this.objectid + ", " +
                this.parentobjid + ", " +
                this.getOktmo() + ", " +
                this.previd + ", " +
                this.nextid  + ", '" +
                this.updatedate.toString() + "'::date, '" +
                this.startdate.toString() + "'::date, '" +
                this.enddate.toString() + "'::date, " +
                this.isactive + ") " +
                this.updateStatement;
        return this.insertStatement;
    }
}
