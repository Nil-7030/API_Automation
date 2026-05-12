package com.myproject.endpoints;

public class Endpoints {

    // Pet Endpoints
    public static String BASE = "/pet";

    public static String GET_BY_STATUS = BASE + "/findByStatus";
    public static String GET_BY_ID = BASE + "/{petId}";
    public static String ADD = BASE;
    public static String PETUPDATE = BASE;
    public static String UPDATE_WITH_FORM = BASE + "/{petId}";
    public static String DELETE = BASE + "/{petId}";

    // User Endpoints
    public static String LOGIN = "/user/login";
    public static String POST_LIST = "/user/createWithList";
    public static String GET_BY_USERNAME = "/user/{username}";
    public static String UPDATE = "/user/{username}";
    public static String DELETEUSER = "/user/{username}";
}
