package com.mitosis.fieldtracking.regionalmanager;

import java.util.ArrayList;

/**
 * Created by mitosis on 18/2/17.
 */

public class RMConstants {
    public final static String FRAG_A = "rmrepview";
    public final static String FRAG_B = "rmdetails";
    public final static String FRAG_c= "rmprofile";
    public static String create="http://202.61.120.46:9081/FieldTracking/lead/create";
    public static String repview="http://202.61.120.46:9081/FieldTracking/manager/getAllRepresentatives";
    public static String repviewtotal="http://202.61.120.46:9081/FieldTracking/manager/getAllRepresentatives";
    public static String leadListOfRepresentative="http://202.61.120.46:9081/FieldTracking/manager/leadListOfRepresentative?userId=";
    public static String pendingleadListOfRepresentative="http://202.61.120.46:9081/FieldTracking/manager/pendingleadListOfRepresentative?userId=";
    public static String completedleadListOfRepresentative="http://202.61.120.46:9081/FieldTracking/manager/completedleadListOfRepresentative?userId=";

    public static  String sortappointdateasc="http://202.61.120.46:9081/FieldTracking/leadTracking/leadList?repId=26&orderToSort=asc&fieldNameToSort=date";
    public static String sortza="http://202.61.120.46:9081/FieldTracking/leadTracking/leadList?repId=26&orderToSort=desc&fieldNameToSort=contactName";
    public static String sortaz="http://202.61.120.46:9081/FieldTracking/leadTracking/leadList?repId=26&orderToSort=asc&fieldNameToSort=contactName";
    public static String forgot="http://202.61.120.46:9081/FieldTracking/users/setNewPassword";
    public static String profile="http://202.61.120.46:9081/FieldTracking/users/getProfileDetails?userName=9176137343";
    public static String profileUpdate="http://202.61.120.46:9081/FieldTracking/users/updateProfileDetails";
    public static String getUserLocation="http://202.61.120.46:9081/FieldTracking/users/getUserLocation?userName=";
    public static String getAllRepresentativesLocation="http://202.61.120.46:9081/FieldTracking/manager/getAllRepresentativesLocation";
    public static String repza="http://202.61.120.46:9081/FieldTracking/manager/sortRepresentatives?orderToSort=desc";
    public static String repaz="http://202.61.120.46:9081/FieldTracking/manager/sortRepresentatives?orderToSort=asc";

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
    public static  ArrayList<String> notes= new ArrayList<String>();
   // public static  ArrayList<String> lat2= new ArrayList<String>();
   // public static  ArrayList<String> long2= new ArrayList<String>();
    ArrayList<String> latdoub=new ArrayList<String>();
    ArrayList<String> longdoub=new ArrayList<String>();

    public static  ArrayList<String> distanceArr= new ArrayList<String>();
    public static  ArrayList<String> sdistanceArr= new ArrayList<String>();




    public static  ArrayList<String> scontactName= new ArrayList<String>();
    public static  ArrayList<String> sstatus= new ArrayList<String>();
    public static  ArrayList<String> sleadName= new ArrayList<String>();
    public static  ArrayList<String> stelephoneNumber= new ArrayList<String>();
    public static  ArrayList<String> smobileNumber= new ArrayList<String>();
    public static  ArrayList<String> semail= new ArrayList<String>();
    public static  ArrayList<String> saddressLine1= new ArrayList<String>();
    public static  ArrayList<String> saddressLine2= new ArrayList<String>();
    public static  ArrayList<String> scity= new ArrayList<String>();
    public static  ArrayList<String> sstate= new ArrayList<String>();
    public static  ArrayList<String> szipCode= new ArrayList<String>();
    public static  ArrayList<String> scountry= new ArrayList<String>();
    public static  ArrayList<String> slandMark= new ArrayList<String>();
    public static  ArrayList<String> sleadDetailsId= new ArrayList<String>();
    public static  ArrayList<String> simageUrl= new ArrayList<String>();
    public static  ArrayList<String> srepId= new ArrayList<String>();
    public static  ArrayList<String> sappointmentDate= new ArrayList<String>();
    public static  ArrayList<String> slatitide= new ArrayList<String>();
    public static  ArrayList<String> slongitude= new ArrayList<String>();
    public static  ArrayList<String> snotes= new ArrayList<String>();



    public static  ArrayList<String> pcontactName= new ArrayList<String>();
    public static  ArrayList<String> pstatus= new ArrayList<String>();
    public static  ArrayList<String> pleadname= new ArrayList<String>();
    public static  ArrayList<String> ptelephonenumber= new ArrayList<String>();
    public static  ArrayList<String> pmobileNumber= new ArrayList<String>();
    public static  ArrayList<String> pemail= new ArrayList<String>();
    public static  ArrayList<String> paddressLine1= new ArrayList<String>();
    public static  ArrayList<String> paddressLine2= new ArrayList<String>();
    public static  ArrayList<String> pcity= new ArrayList<String>();
    public static  ArrayList<String> pstate= new ArrayList<String>();
    public static  ArrayList<String> pzipCode= new ArrayList<String>();
    public static  ArrayList<String> pcountry= new ArrayList<String>();
    public static  ArrayList<String> plandMark= new ArrayList<String>();
    public static  ArrayList<String> pleadDetailsId= new ArrayList<String>();
    public static  ArrayList<String> pimageUrl= new ArrayList<String>();
    public static  ArrayList<String> prepId= new ArrayList<String>();
    public static  ArrayList<String> pappointmentDate= new ArrayList<String>();
    public static  ArrayList<String> platitude= new ArrayList<String>();
    public static  ArrayList<String> plongitude= new ArrayList<String>();
    public static  ArrayList<String> pnotes= new ArrayList<String>();


    public static  ArrayList<String> ccontactName= new ArrayList<String>();
    public static  ArrayList<String> cstatus= new ArrayList<String>();
    public static  ArrayList<String> cleadname= new ArrayList<String>();
    public static  ArrayList<String> ctelephonenumber= new ArrayList<String>();
    public static  ArrayList<String> cmobileNumber= new ArrayList<String>();
    public static  ArrayList<String> cemail= new ArrayList<String>();
    public static  ArrayList<String> caddressLine1= new ArrayList<String>();
    public static  ArrayList<String> caddressLine2= new ArrayList<String>();
    public static  ArrayList<String> ccity= new ArrayList<String>();
    public static  ArrayList<String> cstate= new ArrayList<String>();
    public static  ArrayList<String> czipCode= new ArrayList<String>();
    public static  ArrayList<String> ccountry= new ArrayList<String>();
    public static  ArrayList<String> clandMark= new ArrayList<String>();
    public static  ArrayList<String> cleadDetailsId= new ArrayList<String>();
    public static  ArrayList<String> cimageUrl= new ArrayList<String>();
    public static  ArrayList<String> crepId= new ArrayList<String>();
    public static  ArrayList<String> cappointmentDate= new ArrayList<String>();
    public static  ArrayList<String> clatitude= new ArrayList<String>();
    public static  ArrayList<String> clongitude= new ArrayList<String>();
    public static  ArrayList<String> cnotes= new ArrayList<String>();











}