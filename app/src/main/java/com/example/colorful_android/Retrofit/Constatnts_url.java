package com.example.colorful_android.Retrofit;

public class Constatnts_url {

//    public static final String BASE_URL = "http://localhost:7777/";
    public static final String BASE_URL_EMULATOR = "http://10.0.2.2:7777/";
    public static final String BASE_URL_MYDEVICE_DH = "http://172.30.1.17:7777/";
    public static final String BASE_URL_MYDEVICE = "http://172.30.1.59:7777/";
    public static final String BASE_URL_MYDEVICE_FLASK = "http://172.30.104.131:5000/";

    public static final String BASE_URL_EC2 = "http://13.124.152.109:8080/Colorful/";

    // 퍼스널컬러 테스트
    public static final String PERSONAL_COLOR_TEST_URL = "test/personalcolor/";

    // 심리컬러 테스트트
    public static final String PSYCHOLOGICAL_COLOR_TEST_QUESTION_URL = "test/psycological/question/";
    public static final String PSYCHOLOGICAL_COLOR_TEST_ANSWER_URL = "test/psycological/answer/";
    public static final String PSYCHOLOGICAL_COLOR_TEST_UPDATE_RESULT_URL = "test/psycological/result/";


    // 유저
    public static final String LOGIN_URL = "customer/login/";
    public static final String SIGNUP_URL = "customer/signup/";
    public static final String LOGOUT_URL = "customer/logout/";
    public static final String WITHDRAWAL_URL = "customer/withdrawal/";

    // 여행지
    public static final String TOURSPOT_DETAIL = "tourspot/detail/";
    public static final String TOURSPOT_RANDOM = "tourspot/random/";
    public static final String RECOMMEND_PSYCOLOR = "tourspot/psycolor/";
    public static final String RECOMMEND_PERSONALCOLOR = "tourspot/personalcolor/";

    public static final String RANDOM_TOURSPOT = "tourspotlist/random/";
    public static final String PALETTE_DETAIL = "tourspot/palette/detail/";
    public static final String ADD_PALETTE = "tourspot/add/palette/";
    public static final String DELETE_PALETTE = "tourspot/delete/palette/";
    public static final String PALETTE_ADD = "tourspot/palette/add/";
    public static final String PALETTE_DELETE = "tourspot/palette/delete/";
    public static final String PALETTE_TOURSPOT = "tourspot/palette/tourspot/";
    public static final String PALETTE_LIST = "tourspot/palette/list/{customerId}";
    public static final String STAR_ADD = "tourspot/star/add/";
    public static final String STAR_DELETE = "tourspot/star/delete/";
    public static final String STAR_TOURSPOTLIST = "tourspot/tourspotlist/";
    public static final String STAR_CHECK = "tourspot/star/check/";

}

