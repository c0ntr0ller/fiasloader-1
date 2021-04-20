package ru.fias;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ITEM")
public class AdmHierarchy extends FiasObject {

    @XmlAttribute(name = "ID", required = true)
    protected BigInteger id;

    @XmlAttribute(name = "OBJECTID", required = true)
    protected BigInteger objectid;

    @XmlAttribute(name = "PARENTOBJID")
    protected BigInteger parentobjid;

    @XmlAttribute(name = "REGIONCODE")
    protected String regioncode;

    @XmlAttribute(name = "AREACODE")
    protected String areacode;

    @XmlAttribute(name = "CITYCODE")
    protected String citycode;

    @XmlAttribute(name = "PLACECODE")
    protected String placecode;

    @XmlAttribute(name = "PLANCODE")
    protected String plancode;

    @XmlAttribute(name = "STREETCODE")
    protected String streetcode;

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

    private String insertStatement = "insert into fias_gar.adm_hierarchy (id, objectid, parentobjid, regioncode, areacode, citycode, placecode, plancode, streetcode, previd, nextid, updatedate, startdate, enddate, isactive)";

    private String updateStatement = "on conflict (id) do update set objectid = excluded.objectid, parentobjid = excluded.parentobjid, regioncode = excluded.regioncode, " +
            "areacode = excluded.areacode, citycode = excluded.citycode, placecode = excluded.placecode, plancode = excluded.plancode, " +
            "streetcode = excluded.streetcode, previd = excluded.previd, nextid = excluded.nextid, updatedate = excluded.updatedate, " +
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

    public String getRegioncode() {
        if (regioncode != null) {
            return "'" + regioncode + "'";
        }
        else {
            return null;
        }
    }

    public void setRegioncode(String regioncode) {
        this.regioncode = regioncode;
    }

    public String getAreacode() {
        if (areacode != null) {
            return "'" + areacode + "'";
        }
        else {
            return null;
        }
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getCitycode() {
        if (citycode != null) {
            return "'" + citycode + "'";
        }
        else {
            return null;
        }
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getPlacecode() {
        if (placecode != null) {
            return "'" + placecode + "'";
        }
        else {
            return null;
        }
    }

    public void setPlacecode(String placecode) {
        this.placecode = placecode;
    }

    public String getPlancode() {
        if (plancode != null) {
            return "'" + plancode + "'";
        }
        else {
            return null;
        }
    }

    public void setPlancode(String plancode) {
        this.plancode = plancode;
    }

    public String getStreetcode() {
        if (streetcode != null) {
            return "'" + streetcode + "'";
        }
        else {
            return null;
        }
    }

    public void setStreetcode(String streetcode) {
        this.streetcode = streetcode;
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
        //"insert into fias_gar.adm_hiararchy (id, objectid, parentobjid, regioncode, areacode, citycode, placecode, plancode, streetcode, previd, nextid, updatedate, startdate, enddate, isactive)"
        this.insertStatement += " values (" +
                this.id + ", " +
                this.objectid + ", " +
                this.parentobjid + ", " +
                this.getRegioncode() + ", " +
                this.getAreacode() + ", " +
                this.getCitycode() + ", " +
                this.getPlacecode() + ", " +
                this.getPlancode() + ", " +
                this.getStreetcode() + ", " +
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
