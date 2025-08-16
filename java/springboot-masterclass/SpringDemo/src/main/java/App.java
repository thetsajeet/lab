import com.example.spring.MyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationBeanContexts.xml");

        MyBean b = (MyBean) ac.getBean("firstBean");
        System.out.println(b);
    }
}
