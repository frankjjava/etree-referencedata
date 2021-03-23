package referencedata.config;

import java.net.MalformedURLException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileUrlResource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import referencedata.exception.ReferencedataException;

@Component
public class SchemaConfig {

	@Getter @Setter
    private Map<String, Object> entities;

    @Autowired
    private Environment environment;

    @PostConstruct
    private void loadYaml() {
        YamlMapFactoryBean yaml = new YamlMapFactoryBean();
        try {
			yaml.setResources(new FileUrlResource(environment.getProperty("spring.config.location")));
		} catch (MalformedURLException e) {
			throw new ReferencedataException(e);
		}
        entities = yaml.getObject();
        if (entities != null) {
        	entities = (Map<String, Object>) entities.get("configparams");
        }
        return;
    }
}
