import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GitHubTest {
    @Test
    public void TestRepositories() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();
        GitHubClient client = retrofit.create(GitHubClient.class);
        List<Repository> body = client.listRepos("yuliaurakova").execute().body();
        assertEquals(body.get(0).name, "course_python");

    }

    @Test
    public void TestCreateRepo() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();
        GitHubClient client = retrofit.create(GitHubClient.class);
        Repository request = new Repository();
        request.name = "asdasdasd1";
        int responseCode = client.createRepo(request).execute().code();
        assertEquals(201, responseCode);
    }

    @Test
    public void TestDeleteRepo() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();
        GitHubClient client = retrofit.create(GitHubClient.class);
        Object responseCode = client.deleteRepo("yuliaurakova", "asdasdasd1").execute().code();
        assertEquals(204, responseCode);
    }
}
