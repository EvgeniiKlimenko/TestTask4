package posts;

import com.brokenhead.model.response.PostResponse;
import com.brokenhead.service.PostsRestService;
import com.brokenhead.service.RestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetPostsTest {

    private final RestService<PostResponse> postRestService = new PostsRestService();
    public static final String EXISTING_USERID = "1";
    public static final String EXISTING_ID = "10";
    public static final String EXISTING_TITLE = "optio molestias id quia eum";


    @Test
    @DisplayName("Service should successfully get list of all posts.")
    public void testServiceShouldGetAllPosts() {
        List<PostResponse> posts = postRestService.getAllEntities();
        assertEquals(100, posts.size());
    }

    @Test
    @DisplayName("Service should successfully get a post by ID.")
    public void testServiceShouldGetPostById() {
        Integer postId = RandomGenerator.getDefault().nextInt(1, 101);
        PostResponse post = postRestService.getSingleEntity(postId.toString());
        assertEquals(postId, post.getId());
    }

    @ParameterizedTest(name = "Search parameter: {1}")
    @MethodSource("parametersProvider")
    @DisplayName("Service should successfully get list of posts by single parameter.")
    public void testServiceShouldGetAllPostsBySingleParameter(Map<String, String> params, String key) {
        PostResponse post = postRestService.getListOfEntities(params).get(0);
        assertTrue(post.toString().contains(params.get(key)));
    }

    private static Stream<Arguments> parametersProvider() {
        return Stream.of(
                Arguments.of(Map.of("title", EXISTING_TITLE), "title"),
                Arguments.of(Map.of("userId", EXISTING_USERID), "userId"),
                Arguments.of(Map.of("id", EXISTING_ID), "id"));
    }

    @Test
    @DisplayName("Service should successfully get list of all posts by two parameters.")
    public void testServiceShouldGetAllPostsByTwoParameters() {
        Map<String, String> params = Map.of("title", EXISTING_TITLE, "userId", EXISTING_USERID);
        List<PostResponse> posts = postRestService.getListOfEntities(params);

        assertEquals(1, posts.size());
        assertEquals(params.get("title"), posts.get(0).getTitle());
        assertEquals(params.get("userId"), posts.get(0).getUserId().toString());
    }

    @Test
    @DisplayName("Service should successfully get list of all posts by post ID.")
    public void testServiceShouldGetAllPostsByEntityId() {
        Integer postId = RandomGenerator.getDefault().nextInt(1, 101);
        Map<String, String> params = Map.of("id", postId.toString());

        List<PostResponse> posts = postRestService.getListOfEntities(params);
        assertEquals(1, posts.size());
        assertEquals(posts.get(0).getId(), postId);
    }

    @ParameterizedTest(name = "Parameter is {1}")
    @MethodSource("invalidParametersProvider")
    @DisplayName("Service should return empty list in response on invalid search parameter.")
    public void testServiceShouldReturnEmptyResponseOnInvalidParameters(Map<String, String> param, String field) {
        List<PostResponse> posts = postRestService.getListOfEntities(param);
        assertEquals(0, posts.size());
    }

    private static Stream<Arguments> invalidParametersProvider() {
        return Stream.of(
                Arguments.of(Map.of("title", "NoTitle"), "title"),
                Arguments.of(Map.of("userId", "0"), "userId"),
                Arguments.of(Map.of("body", "NoBody"), "body"),
                Arguments.of(Map.of("id", "0"), "id"));
    }

    @Test
    @DisplayName("Service should return list of all posts on non existing parameter in request.")
    public void testServiceShouldReturnListOfAllPostsOnNonExistingSearchParameter() {
        Map<String, String> param = Map.of("nonExistPar", "37");
        List<PostResponse> posts = postRestService.getListOfEntities(param);
        assertEquals(100, posts.size());
    }
}
