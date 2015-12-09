package jp.tokushima.tatsuhama.currencyconverter;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by tatsuhama on 15/12/09.
 */
public interface KawaseApi {

    @GET("/get.php")
    void getCurrency(@Query("format") String format, @Query("code") Code code, @Query("to") Code to, Callback<HashMap<String, String>> callback);
}
