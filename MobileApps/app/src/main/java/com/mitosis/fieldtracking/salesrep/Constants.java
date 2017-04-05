package com.mitosis.fieldtracking.salesrep;

import java.util.ArrayList;

/**
 * Created by mitosis on 18/2/17.
 */

public class Constants {
    public final static String FRAG_A="fragment_a";
    public final static String FRAG_B="fragment_b";
    public final static String FRAG_D="maps";
    public final static String FRAG_F="profile";

    public static ArrayList<String> contactName=new ArrayList<>();
    public static ArrayList<String> status=new ArrayList<>();
    public static ArrayList<String> addressLine1=new ArrayList<>();
    public static ArrayList<String> addressLine2=new ArrayList<>();
    public static ArrayList<String> city=new ArrayList<>();
    public static ArrayList<String> state=new ArrayList<>();
    public static ArrayList<String> zipCode=new ArrayList<>();
    public static ArrayList<String> mobileNumber=new ArrayList<>();
    public static ArrayList<String> leadDetailsId=new ArrayList<>();
    public static ArrayList<String> latitude=new ArrayList<>();
    public static ArrayList<String> longitude=new ArrayList<>();
    public static ArrayList<String> latitudeArr=new ArrayList<String>();
    public static ArrayList<String> appointmentDate=new ArrayList<String>();
    public static ArrayList<String> leadImage=new ArrayList<String>();

    public static double lat;
    public static double lng;
    public static String leadId;
    public static String appointDate;
    public static String updateUserLocation="http://202.61.120.46:9081/FieldTracking/users/updateUserLocation";

    public static String create="http://202.61.120.46:9081/FieldTracking/lead/create";
    public static String listUrl="http://202.61.120.46:9081/FieldTracking/lead/list?userId=26";
    public static String sortappointdate="http://202.61.120.46:9081/FieldTracking/leadTracking/leadList?repId=1&appointmentDate=2017-03-04";
    public static String leadstatus="http://202.61.120.46:9081/FieldTracking/leadTracking/leadList?repId=1&appointmentDate=2017-03-07&status=reached";
    public static String  update="http://202.61.120.46:9081/FieldTracking/leadTracking/updateStatus";
    public static String imageUpload_URL ="http://202.61.120.46:9081/FieldTracking/lead/uploadLeadImage";
    public static String sortza="http://202.61.120.46:9081/FieldTracking/leadTracking/leadList?repId=26&orderToSort=desc&fieldNameToSort=contactName";
    public static String sortaz="http://202.61.120.46:9081/FieldTracking/leadTracking/leadList?repId=26&orderToSort=asc&fieldNameToSort=contactName";
    public static String comlpete="http://202.61.120.46:9081/FieldTracking/leadTracking/leadList?repId=26&fieldNameToSort=status&statusName=attended";
    public static String sortappointdateasc="http://202.61.120.46:9081/FieldTracking/leadTracking/leadList?repId=26&orderToSort=asc&fieldNameToSort=date";
    public static String pending="http://202.61.120.46:9081/FieldTracking/leadTracking/getAllLeadList?repId=26";
    public static String profileUpdate="http://202.61.120.46:9081/FieldTracking/users/updateProfileDetails";
    public static String forgot="http://202.61.120.46:9081/FieldTracking/users/setNewPassword";


}

