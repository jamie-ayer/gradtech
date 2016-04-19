//package com.ga.gradtech;
//
//import com.ga.gradtech.Cards.NYTimes.NYTService;
//
//import retrofit.RestAdapter;
//
//public class Domains {
//
//    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder().setEndpoint(ApiKeys.NYTIMES_API_URL).build();
//    private static final NYTService SERVICE = REST_ADAPTER.create(NYTService.class);
//
//    public static NYTService getNYTService() {
//        return SERVICE;
//    }
//
//}