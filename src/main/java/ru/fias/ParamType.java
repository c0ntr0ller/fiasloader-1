package ru.fias;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PARAMTYPE")
public class ParamType extends  FiasObject{
    @XmlAttribute(name = "ID", required = true)
    protected BigInteger id;

    @XmlAttribute(name = "NAME", required = true)
    protected String name;

    @XmlAttribute(name = "CODE", required = true)
    protected String code;

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

    private String insertStatement = "insert into fias_gar.param_types(id, name, code, \"desc\", updatedate, startdate, enddate, isactive)";

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    @Override
    public String getInsertStatement() {
        this.insertStatement += " values (" +
                this.id + ", '" +
                this.name + "', '" +
                this.code + "', '" +
                this.desc + "', '" +
                this.updatedate.toString() + "'::date, '" +
                this.startdate.toString() + "'::date, '" +
                this.enddate.toString() + "'::date, " +
                this.isactive + ")";
        return this.insertStatement;
    }

}
