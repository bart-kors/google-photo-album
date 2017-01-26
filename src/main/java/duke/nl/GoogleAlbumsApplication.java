package duke.nl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class GoogleAlbumsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GoogleAlbumsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GoogleAlbumsApplication.class);
    }


    @Autowired
    private ImageRepository repository;


    @Bean
    public ServletRegistrationBean redirectServletRegistrationBean() {
        return new ServletRegistrationBean(new RedirectServlet(repository), "/open/*");
    }
}
