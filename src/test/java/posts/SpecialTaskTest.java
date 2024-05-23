package posts;

import com.brokenhead.model.response.PostResponse;
import com.brokenhead.service.PostsRestService;
import com.brokenhead.service.RestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialTaskTest {

    private final RestService<PostResponse> postRestService = new PostsRestService();

    @Test
    @DisplayName("Test should successfully get list of all posts.")
    public void testShouldGetAllPosts() {
        Map<String, Integer> wordsMap = new TreeMap<>();
        List<PostResponse> posts = postRestService.getAllEntities();
        List<String> bodies = posts.stream()
                .map(PostResponse::getBody)
                .toList();



        assertEquals(100, posts.size());
    }

}
