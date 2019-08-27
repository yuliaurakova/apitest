import com.jayway.restassured.response.ResponseBody;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class IssueTest {
    private static final String TITLE = "Тест1";

    private WebDriver driver;

    @Before
    public void startWebDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void testIssueCreation() {
        Issue issue = creatrIssue(TITLE);
        String issueUrl = String.format(
                "https://bitbucket.org/%s/%s/issues/%s",
                "yuurakova",
                "bitbucketapitest",
                issue.getId()
        );
        driver.get(issueUrl);
        WebElement issueTitle = driver.findElement(By.cssSelector("#issue-title"));
        System.out.println(issueTitle.getText());
        assertThat(issueTitle.getText(), equalTo(issue.getTitle()));
        deleteIssue(issue);
    }

    private Issue creatrIssue(String title) {
        Issue issue = new Issue();
        issue.setTitle(title);
        ResponseBody response = given()
                .auth().basic("yuurakova", "PyeJKxZs5zUNCtPn3gnH")
                .header("Content-Type", " application/json")
                .baseUri("https://api.bitbucket.org/")
                .body(issue)
                .when()
                .post(String.format(
                        "2.0/repositories/%s/%s/issues",
                        "yuurakova",
                        "bitbucketapitest"
                        )
                )
                .getBody();

        return response.as(Issue.class);

    }


    @After
    public void stopWebDriver() {
        driver.quit();
    }

    private void deleteIssue(Issue issue) {
        given()
                .auth().preemptive().basic("yuurakova", "PyeJKxZs5zUNCtPn3gnH")
                .header("Content-Type", " application/json")
                .baseUri("https://api.bitbucket.org/")
                .body(issue)
                .when()
                .delete(String.format(
                        "2.0/repositories/%s/%s/issues/%s",
                        "yuurakova",
                        "bitbucketapitest",
                        issue.getId()
                        )
                )
                .then()
                .statusCode(204);
     }
}

