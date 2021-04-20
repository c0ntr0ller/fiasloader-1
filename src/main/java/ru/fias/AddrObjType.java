package ru.fias;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ADDRESSOBJECTTYPE")

public class AddrObjType extends FiasObject{

        @XmlAttribute(name = "ID", required = true)
        protected BigInteger id;

        @XmlAttribute(name = "LEVEL", required = true)
        protected int level;

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

        private String insertStatement = "insert into fias_gar.addr_obj_types(id, level, name, shortname, \"desc\", updatedate, startdate, enddate, isactive)";

        private String updateStatement = "on conflict (id) do update set level = excluded.level, name = excluded.name, shortname = excluded.shortname, " +
                "\"desc\" = excluded.\"desc\", updatedate = excluded.updatedate, startdate = excluded.startdate, enddate = excluded.enddate, " +
                "isactive = excluded.isactive";

        public BigInteger getId() {
                return id;
        }

        public void setId(BigInteger id) {
                this.id = id;
        }

        public int getLevel() {
                return level;
        }

        public void setLevel(int level) {
                this.level = level;
        }

        public String getName() {
                return name.replaceAll("[']", "");
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getShortname() {
                return shortname.replaceAll("[']", "");
        }

        public void setShortname(String shortname) {
                this.shortname = shortname;
        }

        public String getDesc() {
                return desc.replaceAll("[']", "");
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

        public String getInsertStatement(){
                // insert into fias_gar.addr_obj_types(id, level, name, shortname, desc, updatedate, startdate, enddate, isactive)
                this.insertStatement += " values (" +
                        this.id + ", " +
                        this.level + ", '" +
                        this.getName() + "', '" +
                        this.getShortname() + "', '" +
                        this.getDesc() + "', '" +
                        this.updatedate.toString() + "'::date, '" +
                        this.startdate.toString() + "'::date, '" +
                        this.enddate.toString() + "'::date, " +
                        this.isactive + ") " +
                        this.updateStatement;
                return this.insertStatement;
        }
}
