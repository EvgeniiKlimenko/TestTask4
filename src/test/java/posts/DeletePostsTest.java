package posts;

import com.brokenhead.model.response.PostResponse;
import com.brokenhead.service.PostsRestService;
import com.brokenhead.service.RestService;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeletePostsTest {

    private final RestService<PostResponse> postRestService = new PostsRestService();

    @Test
    @DisplayName("Test should successfully delete a post by ID")
    public void testShouldDeletePostById() {
        Response deleteResult = postRestService.deleteEntity("1");
        int code = deleteResult.getStatusCode();
        assertEquals(200, code);
    }

    @Disabled("Assumption: in real case, this negative test should return errors in responses.")
    @ParameterizedTest(name = "Invalid value: {0}")
    @ValueSource(strings = {"wrongId", "500", "\\n"})
    @DisplayName("Test should return error on invalid post ID")
    public void testShouldGet404NotFoundOnInvalidId(String invalidId) {
        Response deleteResult = postRestService.deleteEntity(invalidId);
        int code = deleteResult.getStatusCode();
        assertEquals(404, code);
    }
}
