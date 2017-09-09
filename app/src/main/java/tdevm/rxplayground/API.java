package tdevm.rxplayground;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Tridev on 10-09-2017.
 */

public interface API {
    @GET("gists/{gistId}")
    Observable<Object> getMyGist(@Path("gistId") String gistId);
}
