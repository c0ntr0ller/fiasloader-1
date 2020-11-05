package ru.fias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;
import java.util.UUID;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ITEM")
public class AddrObjDivision extends FiasObject {

    @XmlAttribute(name = "ID", required = true)
    protected BigInteger id;

    @XmlAttribute(name = "PARENTID", required = true)
    protected BigInteger parentid;

    @XmlAttribute(name = "CHILDID", required = true)
    protected  BigInteger childid;

    private String insertStatement = "insert into fias_gar.addr_obj_division(id, parentid, childid)";

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getParentid() {
        return parentid;
    }

    public void setParentid(BigInteger parentid) {
        this.parentid = parentid;
    }

    public BigInteger getChildid() {
        return childid;
    }

    public void setChildid(BigInteger childid) {
        this.childid = childid;
    }

    @Override
    public String getInsertStatement() {
        //insert into fias_gar.addr_obj_division(id, parentid, childid)
        this.insertStatement += " values (" +
                this.id + ", " +
                this.parentid + ", " +
                this.childid + ")";
        return this.insertStatement;
    }
}
