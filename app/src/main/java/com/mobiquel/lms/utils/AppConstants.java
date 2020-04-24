package com.mobiquel.lms.utils;

/**
 * Created by Navjot Singh
 * on 2/3/19.
 */

public class AppConstants {

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final String KEY_USER_NUMBER = "user_number";
    public static final String KEY_DOB = "dob";
    public static final String KEY_ABOUT = "about";
    public static final String KEY_CATEGORY = "category";
    public static final String BASE_URL = "http://matdaan360.in:8080/UdhampurDisasterRelief/rest/service/";
    public static final String GCM_SENDER_ID = "985290004498";
    public static final String TEST_APP_DOWNLOAD_LINK = "http://matdaan360.in:8080/UdhampurDisasterRelief/rest/service/";

    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 12;

    public static final String KEY_TITLE = "title";
    public static final String KEY_SUB_TITLE = "sub_title";
    public static final String KEY_DESCRIPTION = "description";

    public static final String KEY_END_POINT = "end_point";

    public static final String AADHAAR_DATA_TAG = "PrintLetterBarcodeData",
            AADHAR_UID_ATTR = "uid",
            AADHAR_NAME_ATTR = "name",
            AADHAR_GENDER_ATTR = "gender",
            AADHAR_YOB_ATTR = "yob",
            AADHAR_CO_ATTR = "co",
            AADHAR_VTC_ATTR = "vtc",
            AADHAR_HOUSE = "house",
            AADHAR_DIST_ATTR = "dist",
            AADHAR_STATE_ATTR = "state",
            AADHAR_PC_ATTR = "pc";
    public static final String ENABLE_INTERNET_SETTING_MESSAGE ="Please connect to Internet" ;

    public static final class MESSAGES
    {
        public static final String ENABLE_INTERNET_SETTING_MESSAGE = "Please connect to internet";
        public static final String DATA_SAVE_OFFLINE_MESSAGE = "No internet present! Image save offline";
        public static final String DATA_SAVE_OFFLINE_MESSAGE_2 = "No internet present! Grievance saved offline. You can go to draft tab to re-submit";

        public static final String ERROR_FETCHING_DATA_MESSAGE = "Error fetching data, please try again later";
        public static final String ERROR_NO_DATA_AVAILABLE_MESSAGE = "No data available";
        public static final String NETWORK_ERROR_MESSAGE = "Network Error, please try again later";
        public static final String NO_DATA_AVAILABLE_MESSAGE = "No new updates available";
        public static final String NO_FIELD_BLANK_MESSAGE = "No field can be left blank";
        public static final String INVALID_HOOD_NAME = "Invalid hood name";
        public static final String ENTER_VALID_EMAIL = "Please enter a valid email address";
        public static final String ENTER_VALID_PHONE_NUMBER = "Please enter a valid mobile number";
        public static final String SIGNED_IN_SUCCESS = "is connected now";
        public static final String SIGNED_OUT_SUCCESS = "Signed out successfully";
        public static final String ADDED_TO_FAVORITES = "Added to favorites";
        public static final String REMOVED_FROM_FAVORITES = "Removed from favorites";
        public static final String LOGIN_TO_SUBMIT_REVIEW = "Please login to submit review";
        public static final String BLANK_COMMENT_SUBMIT = "Blank comment cannot be submitted!";
        public static final String COMMENT_LENGTH_EXCEED_SUBMIT = "Comment exceeding 20 word length limit!";
        public static final String IMPRESSION_SUBMISSION_LENGTH_EXCEED_SUBMIT = "Submission exceeding 20 word length limit!";
    }
}

