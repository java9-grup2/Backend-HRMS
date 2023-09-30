package org.hrms.constant;

public class EndPoints {

    public static final String VERSION = "api/v1";
    public static final String COMPANY =VERSION +"/company";

    //genel
    public static final String DELETEBYID ="/deletebyid/{id}";
    public static final String DELETEBYCOMPANYNAME ="/deletebycompanyname";
    public static final String SAVE ="/save";
    public static final String UPDATE ="/update";
    public static final String FINDALL ="/findall";
    public static final String FINDBYID ="/findbyid";
    public static final String FINDBYCOMPANYNAME ="/findbycompanyname";

    //Company
    public static final String ISCOMPANYEXISTS ="/iscompanyexists";
    public static final String REGISTERVISITOR ="/registervisitor";
    public static final String REGISTERMANAGER ="/registermanager";
    public static final String REGISTEREMPLOYEE ="/registeremployee";
    public static final String LOGIN ="/login";
    public static final String ACTIVATION ="/activation";
    public static final String PUBLICHOLIDAY="/publicholiday";
    public static final String ACTIVATESTATUS="/activatestatus";

}
