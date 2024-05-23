package posts;

import com.brokenhead.model.request.PostRequest;
import com.brokenhead.model.response.PostResponse;
import com.brokenhead.service.PostsRestService;
import com.brokenhead.service.RestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreatePostTest {

    private final RestService<PostResponse> postRestService = new PostsRestService();
    public static final String POST_REQUEST_TEMPLATE = """
                                                         {
                                                           "%s": %s,
                                                           "title": "beatae soluta recusandae",
                                                           "body": "Test body"
                                                         }
                                                       """;

    @Test
    @DisplayName("Test should successfully create a posts.")
    public void testShouldCreateValidPost() {
        PostRequest requestBody = PostRequest.buildGenericPostRequest();
        PostResponse createdPost = postRestService.createEntity(requestBody.toString());

        assertEquals(requestBody.getUserId(), createdPost.getUserId());
        assertEquals(requestBody.getTitle(), createdPost.getTitle());
        assertEquals(requestBody.getBody(), createdPost.getBody());
    }

    // NOTE: since this API is a "fake api", I disabled this test.
    // In real case, this negative test should return errors in responses, at least for userId=null.
    // Assuming that userId is a mandatory field with validation
    @ParameterizedTest
    @ValueSource(strings = {"\"stringId\"", "101.0543"})
    @NullSource
    @EmptySource
    @DisplayName("Test should return error on invalid userId values.")
    public void testShouldReturnErrorOnInvalidUserId(String invalidId) {
        String requestBody = POST_REQUEST_TEMPLATE.formatted("userId", invalidId);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postRestService.createEntity(requestBody);
        });

        String expectedMessage = "userId has invalid value";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    @DisplayName("Test should return error on non-existing field in request.")
    public void testShouldReturnErrorOnInvalidFieldName() {
        String nonExistField = "nonExist";
        String requestBody = POST_REQUEST_TEMPLATE.formatted(nonExistField, "10");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postRestService.createEntity(requestBody);
        });

        String expectedMessage = "Unrecognized fields: " + nonExistField;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
