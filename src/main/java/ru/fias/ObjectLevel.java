package ru.fias;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OBJECTLEVEL")
public class ObjectLevel extends FiasObject {
    @XmlAttribute(name = "LEVEL", required = true)
    protected int level;

    @XmlAttribute(name = "NAME", required = true)
    protected String name;

    @XmlAttribute(name = "SHORTNAME")
    protected String shortname;

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

    private String insertStatement = "insert into fias_gar.object_levels(level, name, shortname, updatedate, startdate, enddate, isactive)";

    private String updateStatement = "on conflict (level) do update set name = excluded.name, shortname = excluded.shortname, " +
            "updatedate = excluded.updatedate, " +
            "startdate = excluded.startdate, enddate = excluded.enddate, isactive = excluded.isactive";

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        if (shortname != null) {
            return "'" + shortname + "'";
        }
        else {
            return null;
        }
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
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

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    @Override
    public String getInsertStatement() {
        this.insertStatement += " values (" +
                this.level + ", '" +
                this.name + "', " +
                this.getShortname() + ", '" +
                this.updatedate.toString() + "'::date, '" +
                this.startdate.toString() + "'::date, '" +
                this.enddate.toString() + "'::date, " +
                this.isactive + ") " +
                this.updateStatement;
        return this.insertStatement;
    }
}
