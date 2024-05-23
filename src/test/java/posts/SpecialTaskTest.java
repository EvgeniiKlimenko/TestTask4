package posts;

import com.brokenhead.model.response.PostResponse;
import com.brokenhead.service.PostsRestService;
import com.brokenhead.service.RestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialTaskTest {

    private final RestService<PostResponse> postRestService = new PostsRestService();

    @Test
    @DisplayName("Test should successfully get list of all posts.")
    public void testShouldGetAllPosts() {
        Map<String, Integer> wordsMap = new HashMap<>();
        List<PostResponse> posts = postRestService.getAllEntities();

        assertEquals(100, posts.size());
    }

}
