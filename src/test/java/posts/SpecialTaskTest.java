package posts;

import com.brokenhead.model.response.PostResponse;
import com.brokenhead.service.PostsRestService;
import com.brokenhead.service.RestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecialTaskTest {

    private final RestService<PostResponse> postRestService = new PostsRestService();

    @Test
    @DisplayName("Test should build a list of words.")
    public void testShouldBuildListOfWords() {
        Map<String, Integer> wordsMap = new HashMap<>();
        List<PostResponse> posts = postRestService.getAllEntities();
        posts.forEach(post -> {
            String[] split = post.getBody().replace("\n", " ").split(" ");
            Arrays.stream(split).forEach(word -> {
                String caseWord = word.toLowerCase();
                if (wordsMap.containsKey(caseWord)) {
                    Integer rate = wordsMap.get(caseWord);
                    wordsMap.put(caseWord, ++rate);
                } else {
                    wordsMap.put(caseWord, 1);
                }
            });
        });

        List<Map.Entry<String, Integer>> sorted = wordsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();

        for (int i = sorted.size() - 1; i >= sorted.size() - 10; i--) {
            Map.Entry<String, Integer> entry = sorted.get(i);
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

}
