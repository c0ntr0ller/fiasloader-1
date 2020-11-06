package ru.fias;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "HOUSETYPE")

public class HouseType extends FiasObject{
    @XmlAttribute(name = "ID", required = true)
    protected BigInteger id;

    @XmlAttribute(name = "NAME", required = true)
    protected String name;

    @XmlAttribute(name = "SHORTNAME", required = true)
    protected String shortname;

    @XmlAttribute(name = "DESC", required = true)
    protected String desc;

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
    protected boolean isactive;

    private String insertStatement = "insert into fias_gar.house_types(id, name, shortname, \"desc\", updatedate, startdate, enddate, isactive)";

    private String updateStatement = "on conflict (id) do update set name = excluded.name, shortname = excluded.shortname, " +
            "\"desc\" = excluded.\"desc\", updatedate = excluded.updatedate, startdate = excluded.startdate, enddate = excluded.enddate, " +
            "isactive = excluded.isactive";

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    @Override
    public String getInsertStatement(){
        //insert into fias_gar.house_types(id, name, shortname, desc, updatedate, startdate, enddate, isactive)
        this.insertStatement += " values (" +
                this.id + ", '" +
                this.name + "', '" +
                this.shortname + "', '" +
                this.desc + "', '" +
                this.updatedate.toString() + "'::date, '" +
                this.startdate.toString() + "'::date, '" +
                this.enddate.toString() + "'::date, " +
                this.isactive + ") " +
                this.updateStatement;
        return this.insertStatement;
    }
}
