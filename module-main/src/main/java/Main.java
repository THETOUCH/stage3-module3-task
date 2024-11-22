import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.implementation.NewsController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.mjc.school");

        NewsController newsController = context.getBean(NewsController.class);
        AuthorController authorController = context.getBean(AuthorController.class);

        System.out.println(newsController);
        System.out.println(authorController);

        context.close();
    }
}
