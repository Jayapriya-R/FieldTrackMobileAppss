package com.mitosis.salesmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mitosis on 18/2/17.
 */

public class Constants {
    public final static String FRAG_A = "repview";
    public final static String FRAG_B = "details";
    public final static String FRAG_c= "profile";
    public static String create="http://202.61.120.46:9081/FieldTracking/lead/create";
    public static String repview="http://202.61.120.46:9081/FieldTracking/manager/getAllRepresentatives";
    public static String repviewtotal="http://202.61.120.46:9081/FieldTracking/manager/getAllRepresentatives";
    public static String leadlist="http://202.61.120.46:9081/FieldTracking/manager/leadListOfRepresentative?userId=";
    public static  String sortappointdateasc="http://202.61.120.46:9081/FieldTracking/leadTracking/leadList?repId=26&orderToSort=asc&fieldNameToSort=date";
    public static String sortza="http://202.61.120.46:9081/FieldTracking/leadTracking/leadList?repId=26&orderToSort=desc&fieldNameToSort=contactName";
    public static String sortaz="http://202.61.120.46:9081/FieldTracking/leadTracking/leadList?repId=26&orderToSort=asc&fieldNameToSort=contactName";
    public static String profile="http://202.61.120.46:9081/FieldTracking/users/getProfileDetails?userName=9176137343";
    public static String profileUpdate="http://202.61.120.46:9081/FieldTracking/users/updateProfileDetails";
    public static String getAllRepresentativesLocation="http://202.61.120.46:9081/FieldTracking/manager/getAllRepresentativesLocation";
    public static String getUserLocation="http://202.61.120.46:9081/FieldTracking/users/getUserLocation?userName=";
    public static ArrayList<String> firstname = new ArrayList<String>();
    public static  ArrayList<String> username=new ArrayList<String>();
    public static  ArrayList<String> totalcount = new ArrayList<String>();
    public static  ArrayList<String> completecount = new ArrayList<String>();
    public static  ArrayList<String> pendingcount = new ArrayList<String>();
    public static    ArrayList<String> lastName = new ArrayList<String>();
    public static  ArrayList<String> userId= new ArrayList<String>();
    public static  ArrayList<String> uuuserId= new ArrayList<String>();
    public static  ArrayList<String> contactName= new ArrayList<String>();
    public static  ArrayList<String> status= new ArrayList<String>();
    public static  ArrayList<String> leadName= new ArrayList<String>();
    public static  ArrayList<String> telephoneNumber= new ArrayList<String>();
    public static  ArrayList<String> mobileNumber= new ArrayList<String>();
    public static  ArrayList<String> email= new ArrayList<String>();
    public static  ArrayList<String> addressLine1= new ArrayList<String>();
    public static  ArrayList<String> addressLine2= new ArrayList<String>();
    public static  ArrayList<String> city= new ArrayList<String>();
    public static  ArrayList<String> state= new ArrayList<String>();
    public static  ArrayList<String> zipCode= new ArrayList<String>();
    public static  ArrayList<String> country= new ArrayList<String>();
    public static  ArrayList<String> landMark= new ArrayList<String>();
    public static  ArrayList<String> leadDetailsId= new ArrayList<String>();
    public static  ArrayList<String> imageUrl= new ArrayList<String>();
    public static  ArrayList<String> repId= new ArrayList<String>();
    public static  ArrayList<String> appointmentDate= new ArrayList<String>();
    public static  ArrayList<String> latitide= new ArrayList<String>();
    public static  ArrayList<String> longitude= new ArrayList<String>();
}