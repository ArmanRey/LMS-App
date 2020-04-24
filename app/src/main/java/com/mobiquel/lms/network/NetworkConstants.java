package com.mobiquel.lms.network;

/**
 * Created by Navjot Singh
 * on 2/3/19.
 */

public class NetworkConstants {

    //    Key Constants
    public static final int CODE_SUCCESS = 200;
    public static final String RESULT_SUCCESS = "Success";

    public static final String BASE_URL ="http://192.168.1.9:8080/LMS/";

    //    Network call end-points
    public static final String END_POINT_LOGIN = "studentLogin";
    public static final String END_POINT_ALL_COURSE = "getAllCourse";
    public static final String END_POINT_COURSE_TESTS = "getCourseTests/";
    public static final String END_POINT_ALL_STUDY_MATERIAL = "getAllCourse";
    public static final String END_POINT_SINE_UP = "createStudents";

    public static final String GCM_SENDER_ID = "604348534456";

    public static final String END_POINT_ADD_USER="addUser/" ;
    public static final String END_POINT_UPDATE_USER="updateUser/" ;
    public static final String END_POINT_UPDATE_MATERIAL_STATUS="updateMaterialStatus/" ;
    public static final String END_POINT_UPDATE_PART_STATUS="updatePartyStatus/" ;
   /* addMaterial
            updateMaterial
    getMaterialById
            getPartyById*/
    public static final String END_POINT_GET_ALL_USERS = "getAllUserList";
    public static final String END_POINT_LOGOUT ="logoutOfficial/" ;
    public static final String END_POINT_ENABLE_DISABLE_USER = "enableDisableUserLogin/";
    public static final String END_POINT_GET_PARTY_LIST = "getAllPartyList/";
    public static final String END_POINT_GET_MATERIAL_LIST = "getAllMaterialList/";
}
