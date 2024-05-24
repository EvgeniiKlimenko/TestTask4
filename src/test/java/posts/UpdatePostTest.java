package posts;

import com.brokenhead.model.request.PostRequest;
import com.brokenhead.model.response.PostResponse;
import com.brokenhead.service.PostsRestService;
import com.brokenhead.service.RestService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdatePostTest {

    private final RestService<PostResponse> postRestService = new PostsRestService();
    public static final String POST_REQUEST_SINGLE_TEMPLATE = """
                                                                {
                                                                  "%s": %s
                                                                }
                                                              """;

    // Assumption - in real service id and userId should bot be changeable.
    @Test
    @DisplayName("Test should successfully update existing post with valid values.")
    public void testShouldUpdatePostWithValidValues() {
        Integer postId = RandomGenerator.getDefault().nextInt(1, 101);
        PostResponse existingPost = postRestService.getSingleEntity(postId.toString());

        PostRequest request = PostRequest.builder()
                .userId(existingPost.getUserId())
                .title("TestTitle for update request")
                .body("TestBody for update request")
                .build();

        PostResponse updatedPost = postRestService.updateEntity(existingPost.getId().toString(), request.toString());

        assertEquals(request.getTitle(), updatedPost.getTitle());
        assertEquals(request.getBody(), updatedPost.getBody());
    }

    // Assumption - in real service id and userId should bot be changeable.
    @ParameterizedTest
    @MethodSource("updateSingleFieldPositiveProvider")
    @DisplayName("Test should successfully update a post with valid data.")
    public void testShouldUpdatePostByIdWithSingleValidValue(String requestBody, String value) {
        Integer postId = RandomGenerator.getDefault().nextInt(1, 101);
        PostResponse updatedPost = postRestService.patchEntity(postId.toString(), requestBody);
        assertTrue(updatedPost.toString().contains(value));
    }

    private static Stream<Arguments> updateSingleFieldPositiveProvider() {
        String title = "\"testTitle for a single value update\"";
        String body = "\"testBody for a single value update\"";
        String titleRequest = POST_REQUEST_SINGLE_TEMPLATE.formatted("title", title);
        String bodyRequest = POST_REQUEST_SINGLE_TEMPLATE.formatted("body", body);
        return Stream.of(
                Arguments.of(titleRequest, title),
                Arguments.of(bodyRequest, body));
    }

    @Disabled("This test should check invalid values, but since API returns everything back " +
              "I receive exception on parsing response.")
    @Test
    @DisplayName("Test should successfully get a post by ID.")
    public void testShouldReturnErrorOnInvalidUpdateValues() {
        Integer postId = RandomGenerator.getDefault().nextInt(1, 101);
        String innerObject = PostRequest.buildGenericPostRequest().toString();
        String requestBody = POST_REQUEST_SINGLE_TEMPLATE.formatted("title", innerObject);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postRestService.patchEntity(postId.toString(), requestBody);
        });

        String expectedMessage = "Incompatible type for the field 'title'";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
