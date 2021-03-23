package referencedata.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import referencedata.config.SchemaConfig;

@SpringBootApplication
@ComponentScan(basePackages = {"referencedata"})
public class ReferencedataApp extends SpringBootServletInitializer {

	@Autowired
	private SchemaConfig schemaConfig;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ReferencedataApp.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ReferencedataApp.class, args);
	}

}
	