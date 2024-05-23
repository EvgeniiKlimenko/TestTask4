package posts;

import com.brokenhead.model.response.PostResponse;
import com.brokenhead.service.PostsRestService;
import com.brokenhead.service.RestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetPostsTest {

    private final RestService<PostResponse> postRestService = new PostsRestService();

    @Test
    @DisplayName("Test should successfully get list of all posts.")
    public void testShouldGetAllPosts() {
        List<PostResponse> posts = postRestService.getAllEntities();
        assertEquals(100, posts.size());
    }

    @Test
    @DisplayName("Test should successfully get a post by ID.")
    public void testShouldGetPostById() {
        Integer postId = RandomGenerator.getDefault().nextInt(1, 101);
        PostResponse post = postRestService.getSingleEntity(postId.toString());
        assertEquals(postId, post.getId());
    }

    @Test
    @DisplayName("Test should successfully get list of all posts by userId.")
    public void testShouldGetAllPostsByParameters() {
        Integer userId = RandomGenerator.getDefault().nextInt(1, 11);
        Map<String, String> params = Map.of("userId", userId.toString());

        List<PostResponse> posts = postRestService.getListOfEntities(params);
        assertEquals(10, posts.size());
        assertEquals(posts.get(0).getUserId(), userId);
    }

    @Test
    @DisplayName("Test should successfully get list of all posts by post ID.")
    public void testShouldGetAllPostsByEntityId() {
        Integer postId = RandomGenerator.getDefault().nextInt(1, 101);
        Map<String, String> params = Map.of("id", postId.toString());

        List<PostResponse> posts = postRestService.getListOfEntities(params);
        assertEquals(1, posts.size());
        assertEquals(posts.get(0).getId(), postId);
    }



}
