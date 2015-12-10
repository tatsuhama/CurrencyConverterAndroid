package jp.tokushima.tatsuhama.currencyconverter;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by tatsuhama on 15/12/09.
 */
public interface KawaseApi {

    @GET("/get.php")
    public Observable<HashMap<String, String>> getCurrency(@Query("format") String format, @Query("code") Code code, @Query("to") Code to);
}
