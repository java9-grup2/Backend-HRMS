package org.hrms.constant;

public class EndPoints {

    public static final String VERSION = "api/v1";
    public static final String USER =VERSION +"/user";

    //genel
    public static final String DELETEBYAUTHID ="/deletebyauthid/{authid}";
    public static final String DELETEBYCOMPANYNAME ="/deletebycompanyname";
    public static final String SAVEVISITOR ="/savevisitor";
    public static final String SAVEMANAGER ="/savemanager";
    public static final String SAVEEMPLOYEE ="/saveemployee";
    public static final String ACTIVATESTATUS ="/activatestatus";
    public static final String APPROVEMANAGER ="/approvemanager";
    public static final String UPDATE ="/update";
    public static final String UPDATECOMPANYDETAILS ="/updatecompanydetails";
    public static final String FINDALL ="/findall";
    public static final String LISTWORKERS ="/listworkers";
    public static final String ISCOMMENTMATCHES ="/iscommentmatches";
    public static final String APPROVECOMMENTOFEMPLOYEE ="/approvecommentofemployee";
    public static final String APPROVECOMMENT ="/approvecomment";


}
