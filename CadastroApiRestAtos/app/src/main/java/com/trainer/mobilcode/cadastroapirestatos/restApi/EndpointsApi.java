package com.trainer.mobilcode.cadastroapirestatos.restApi;


import com.trainer.mobilcode.cadastroapirestatos.model.Contacto;
import com.trainer.mobilcode.cadastroapirestatos.restApi.model.ContactoResponse;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Gustavo Ovelar on 19/09/2018.
 */
public interface EndpointsApi {

    @GET(ConstantesRestApi.ROOT_URL)
    Call<ContactoResponse> getRecentMedia();
    @POST("cadastro")
    Call<Void> saveContacto(@Body Contacto contacto);
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantesRestApi.ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
