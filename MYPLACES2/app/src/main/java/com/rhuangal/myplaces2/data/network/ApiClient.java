package com.rhuangal.myplaces2.data.network;



import com.rhuangal.myplaces2.data.network.entity.CategoriaResponse;
import com.rhuangal.myplaces2.data.network.entity.Favoritos;
import com.rhuangal.myplaces2.data.network.entity.FavoritosResponse;
import com.rhuangal.myplaces2.data.network.entity.Login;
import com.rhuangal.myplaces2.data.network.entity.LoginResponse;
import com.rhuangal.myplaces2.data.network.entity.RecomendadosResponse;
import com.rhuangal.myplaces2.data.network.entity.User;
import com.rhuangal.myplaces2.data.network.entity.UserResponse;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rober on 30-Oct-17.
 */

public class ApiClient {

    private static final String TAG = "ApiClient";
    private static final String API_BASE_URL="https://api.backendless.com/9477C47E-756C-D2EE-FFBB-1CCB8BFD1D00/8594FD97-4AD0-8CD4-FF84-9381A4B23500/";

    private static ServicesApiInterface servicesApiInterface;
    private static OkHttpClient.Builder httpClient;


    public static ServicesApiInterface getMyApiClient() {

        if (servicesApiInterface == null) {

            Retrofit.Builder builder =new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            httpClient =new OkHttpClient.Builder();
            httpClient.addInterceptor(interceptor());

            Retrofit retrofit = builder.client(httpClient.build()).build();
            servicesApiInterface = retrofit.create(ServicesApiInterface.class);
        }
        return servicesApiInterface;
    }

    public interface ServicesApiInterface {

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "application-type: REST"
        })
        @POST("users/login")
        Call<LoginResponse> login(@Body Login raw);

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "application-type: REST"
        })
        @POST("users/register")
        Call<UserResponse> register(@Body User raw);

        @GET("data/categoria")
        Call<List<CategoriaResponse>> getCategorias();

        @GET("data/recomendados")
        Call<List<RecomendadosResponse>> getRecomendadosByCat(@Query("where") String where);

        @POST("data/favoritos")
        Call<FavoritosResponse> saveFavoritos(@Body Favoritos raw);

        @GET("data/favoritos")
        Call<List<FavoritosResponse>> getFavoritos(@Query("where") String where);

    }

    /*private static OkHttpClient.Builder client(){
        if(httpClient==null)httpClient=new OkHttpClient.Builder();
        return httpClient;
    }*/
    private  static HttpLoggingInterceptor interceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor= new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
