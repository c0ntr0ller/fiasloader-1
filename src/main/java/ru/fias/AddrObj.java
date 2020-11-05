package ru.fias;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Generated;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OBJECT")
public class AddrObj extends FiasObject {

    @XmlAttribute(name = "ID", required = true)
    protected BigInteger id;

    @XmlAttribute(name = "OBJECTID", required = true)
    protected BigInteger objectid;

    @XmlAttribute(name = "OBJECTGUID", required = true)
    protected UUID objectguid;

    @XmlAttribute(name = "NAME", required = true)
    protected String name;

    @XmlAttribute(name = "TYPENAME", required = true)
    protected String typename;

    @XmlAttribute(name = "LEVEL", required = true)
    protected String level;

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

    @XmlAttribute(name = "ISACTUAL", required = true)
    protected int isactual;

    @XmlAttribute(name = "ISACTIVE", required = true)
    protected int isactive;

    //@Value("${addrobj_query:\"insert into fias_gar.addr_obj(id, objectid, objectguid, name, typename, level, previd, nextid, updatedate, startdate, enddate, isactual, isactive)\"}")
    private String insertStatement = "insert into fias_gar.addr_obj(id, objectid, objectguid, name, typename, level, previd, nextid, updatedate, startdate, enddate, isactual, isactive)";

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
        this.insertStatement += " values (" +
                this.id + ", " +
                this.objectid + ", '" +
                this.objectguid.toString() + "'::uuid, '" +
                this.name + "', '" +
                this.typename + "', '" +
                this.level + "', " +
                this.previd + ", " +
                this.nextid  + ", '" +
                this.updatedate.toString() + "'::date, '" +
                this.startdate.toString() + "'::date, '" +
                this.enddate.toString() + "'::date, " +
                this.isactual + ", " +
                this.isactive + ")";
        return this.insertStatement;
    }
}
