package ru.fias;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PARAM")
public class AddrObjParam extends FiasObject{
    @XmlAttribute(name = "ID", required = true)
    protected BigInteger id;

    @XmlAttribute(name = "OBJECTID", required = true)
    protected BigInteger objectid;

    @XmlAttribute(name = "TYPEID", required = true)
    protected BigInteger typeid;

    @XmlAttribute(name = "VALUE", required = true)
    protected String value;

    @XmlAttribute(name = "UPDATEDATE", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar updatedate;

    private String insertStatement = "insert into fias_gar.addr_obj_params(id, objectid, typeid, value, updatedate)";

    private String updateStatement = "on conflict (id) do update set objectid = excluded.objectid, typeid = excluded.typeid, value = excluded.value, " +
            "updatedate = excluded.updatedate";

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

    public BigInteger getTypeid() {
        return typeid;
    }

    public void setTypeid(BigInteger typeid) {
        this.typeid = typeid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public XMLGregorianCalendar getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(XMLGregorianCalendar updatedate) {
        this.updatedate = updatedate;
    }

    @Override
    public String getInsertStatement(){
        //insert into fias_gar.addr_obj_params(id, objectid, typeid, value, updatedate)
        this.insertStatement += " values (" +
                this.id + ", " +
                this.objectid + ", " +
                this.typeid + ", '" +
                this.value + "', '" +
                this.updatedate.toString() + "'::date "
                + ") " +
                this.updateStatement;
        return this.insertStatement;
    }
}
