import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface GitHubClient {
    @GET("users/{user}/repos")
    Call<List<Repository>> listRepos(@Path("user") String user);

    @Headers("Authorization: token 4941e789def617cd51c9103a874e7fbe4e8a2229")
    @POST("/user/repos")
    Call<Repository> createRepo(@Body Repository body);

    @Headers("Authorization: token 4941e789def617cd51c9103a874e7fbe4e8a2229")
    @DELETE("/repos/{user}/{id}")
    Call<Repository> deleteRepo(@Path("user") String user, @Path("id") String id);
}