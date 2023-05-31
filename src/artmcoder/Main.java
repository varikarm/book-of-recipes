package artmcoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final String URL = "https://trashbox.ru/apps/android/";
    public static final int MAX_PAGES = 15;
    public static final int THREAD_POOL_SIZE = 5;

    public static void main(String[] args) throws IOException, InterruptedException {
        List<Post> posts = parsePosts();
        posts.forEach(System.out::println);
    }



    private static List<Post> parsePosts() throws IOException, InterruptedException {
        List<Post> posts = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (int i = 1; i <= MAX_PAGES; i++) {
            final int page = i;
            executorService.execute(() -> {
                try {
                    System.out.println("Подключение к " + page + " главной странице...");
                    Document doc = Jsoup.connect(page == 1 ? URL : URL + "/" + page + "/").get();
                    Elements postTitleElements = doc.getElementsByAttributeValue("class", "a_topic_content");
                    for (Element postTitleElement : postTitleElements) {
                        String detailsLink = postTitleElement.attr("href");
                        Post post = new Post();
                        post.setDetailsLink(detailsLink);
                        post.setTitle(postTitleElement.attr("title"));
                        System.out.println("Подключение к деталям о посте: " + detailsLink);
                        Document postDetailsDoc = Jsoup.connect(detailsLink).get();

                        try {
                            Element programsElement = postDetailsDoc.getElementsByClass("div_topic_top_download_right").first().child(0);
                            post.setName_programs(postDetailsDoc.getElementsByClass("h_topic_caption").first().text());
                            post.setPrograms(programsElement.attr("div_topic_top_download_right > div_landing_download"));
                            post.setProgramsDetailsLink(programsElement.attr("href"));
                            post.setText_content(postDetailsDoc.getElementsByClass("div_text_content nopadding").first().text());

                        } catch (NullPointerException e) {
                            post.setPrograms("Ссылки нет");
                            post.setName_programs("Текста нет");
                            post.setProgramsDetailsLink("Ссылки нет");
                            post.setText_content("Текста нет");
                        }
                        synchronized (posts) {
                            posts.add(post);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        return posts;
    }
}
