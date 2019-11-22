package com.m_shport.corpphonebook;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataInterface {

    @GET("pbook.json")
    Call<PojoJson> getData();
}
