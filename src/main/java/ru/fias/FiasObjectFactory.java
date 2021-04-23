package ru.fias;

import ru.progmatik.main.other.XMLFileReader;

public class FiasObjectFactory {

    public Class getFiasObjectClass(String XMLFileName) {
        Class fiasClass = null;
        switch (XMLFileName) {
            case "AS_ADDR_OBJ":
                fiasClass = AddrObj.class;
                break;
            case "AS_ADDR_OBJ_TYPES":
                fiasClass = AddrObjType.class;
                break;
            case "AS_HOUSE_TYPES":
                fiasClass = HouseType.class;
                break;
            case "AS_HOUSES":
                fiasClass = House.class;
                break;
//            case "AS_HOUSES_PARAMS":
//                fiasClass = HouseParam.class;
//                break;
            case "AS_ADM_HIERARCHY":
                fiasClass = AdmHierarchy.class;
                break;
            case "AS_MUN_HIERARCHY":
                fiasClass = MunHierarchy.class;
                break;
//            case "AS_ADDR_OBJ_PARAMS":
//                fiasClass = AddrObjParam.class;
//                break;
            case "AS_OBJECT_LEVELS":
                fiasClass = ObjectLevel.class;
                break;
            case "AS_PARAM_TYPES":
                fiasClass = ParamType.class;
                break;
            default: throw new IllegalArgumentException("Wrong file name:" + XMLFileName);
        }
        return fiasClass;
    }
}
