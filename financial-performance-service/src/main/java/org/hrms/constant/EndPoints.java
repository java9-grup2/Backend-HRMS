package org.hrms.constant;

public class EndPoints {

    public static final String VERSION = "api/v1";
    public static final String FINANCIALPERFORMANCE =VERSION +"/financialperformance";

    //genel
    public static final String DELETEBYID ="/deletebyid/{id}";
    public static final String SAVE ="/save";
    public static final String UPDATE ="/update";
    public static final String FINDALL ="/findall";
    public static final String FINDBYID ="/findbyid";
    public static final String FINDSELECTEDYEAR ="/findSelectedYear";

    //Company
    public static final String ISCOMPANYEXISTS ="/iscompanyexists";
    public static final String REGISTERVISITOR ="/registervisitor";
    public static final String CALCULATETOTALEXPENSES ="/CALCULATETOTALEXPENSES";
    public static final String REGISTERMANAGER ="/registermanager";
    public static final String REGISTEREMPLOYEE ="/registeremployee";
    public static final String LOGIN ="/login";
    public static final String ACTIVATION ="/activation";
}
