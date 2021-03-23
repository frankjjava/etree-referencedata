/**
* Copyright (c) 2020 ElasticTree Technologies Pvt. Ltd.
*
* @author  Franklin Abel
* @version 1.0
* @since   2019-01-06 
*/
package referencedata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import referencedata.common.ReferencedataDao;
import referencedata.common.ReferencedataService;
import referencedata.impl.ReferencedataDaoImpl;
import referencedata.impl.ReferencedataServiceImpl;

@Configuration
public class ReferencedataConfig {

	@Bean
	public ReferencedataDao createSofiaDao() {
		ReferencedataDao opendataDao = new ReferencedataDaoImpl();
		return opendataDao;
	}
	
	@Bean
	public ReferencedataService createSofiaService() {
		ReferencedataService opendataService = new ReferencedataServiceImpl();
		return opendataService;
	}
}
