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
import static org.junit.jupiter.api.Assertions.assertNull;
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

    // Assuming that userId is a mandatory field with validations
    @Test
    @DisplayName("Test should successfully create a posts with only mandatory fields.")
    public void testShouldCreateValidPostWithOnlyMandatoryFields() {
        PostRequest requestBody = PostRequest.buildGenericPostRequest();
        requestBody.setTitle("");
        requestBody.setBody("");
        PostResponse createdPost = postRestService.createEntity(requestBody.toString());

        assertEquals(requestBody.getUserId(), createdPost.getUserId());
        assertEquals(requestBody.getTitle(), createdPost.getTitle());
        assertEquals(requestBody.getBody(), createdPost.getBody());
    }

    // NOTE: since this API is a "fake api", it returns 201 created
    // In real case, this negative test should return errors in responses, at least for userId=null.
    @ParameterizedTest(name = "Invalid value: {0}")
    @ValueSource(strings = {"\"stringId\"", "101.0543", "-10"})
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

    // Assumption - in real case, this should be a negative test with 400 BAD REQUEST
    @Test
    @DisplayName("Test should return error on empty request.")
    public void testShouldReturnErrorOnEmptyRequest() {
        PostResponse createdPost = postRestService.createEntity("");

        assertNull(createdPost.getUserId());
        assertNull(createdPost.getTitle());
        assertNull(createdPost.getBody());
        assertEquals(101, createdPost.getId());
    }

    // Assumption - that non-existing fields should be validated and return 400.
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
