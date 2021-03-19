/**
* Copyright (c) 2020 ElasticTree Technologies Pvt. Ltd.
*
* @author  Franklin Abel
* @version 1.0
* @since   2019-01-06 
*/
package com.etree.opendata.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.etree.opendata.common.biz.OpendataService;
import com.etree.opendata.common.dao.OpendataDao;
import com.etree.opendata.web.biz.impl.OpendataServiceImpl;
import com.etree.opendata.web.dao.impl.OpendataDaoImpl;

@Configuration
public class OpendataConfig {

	@Bean
	public OpendataDao createSofiaDao() {
		OpendataDao opendataDao = new OpendataDaoImpl();
		return opendataDao;
	}
	
	@Bean
	public OpendataService createSofiaService() {
		OpendataService opendataService = new OpendataServiceImpl();
		return opendataService;
	}
}
