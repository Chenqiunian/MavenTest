package Demo;

import Demo.config.AppConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@MapperScan("Demo.Mapper")
@SpringBootApplication
public class BootDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(BootDemoApplication.class, args);

        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            System.out.println("beanName: " + beanName);
        }
    }

}
