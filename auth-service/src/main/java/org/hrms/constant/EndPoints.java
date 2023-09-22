package org.hrms.constant;

public class EndPoints {

    public static final String VERSION = "api/v1";
    public static final String AUTH =VERSION +"/auth";

    //genel
    public static final String DELETEBYID ="/deletebyid/{id}";
    public static final String SAVE ="/save";
    public static final String UPDATE ="/update";
    public static final String FINDALL ="/findall";

    //Auth
    public static final String REGISTERVISITOR ="/registervisitor";
    public static final String REGISTERMANAGER ="/registermanager";
    public static final String REGISTEREMPLOYEE ="/registeremployee";
    public static final String LOGIN ="/login";
    public static final String ACTIVATION ="/activation";
    public static final String ISCOMPANYEXISTS ="/iscompanyexists";


}
