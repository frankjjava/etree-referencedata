/**
 * Copyright © 2020 eTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2020-11-04 
 */
package com.etree.opendata.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etree.commons.core.CommonsSupportConstants;
import com.etree.commons.core.CommonsSupportConstants.COLLECTION_TYPE;
import com.etree.commons.core.exception.EtreeCommonsException;
import com.etree.opendata.common.dto.AirportsDto;
import com.etree.opendata.common.dto.CitiesDto;
import com.etree.opendata.common.dto.CountriesDto;
import com.etree.opendata.common.dto.CurrenciesDto;
import com.etree.opendata.common.dto.LookupCityDto;
import com.etree.opendata.common.dto.LookupCountryDto;
import com.etree.opendata.common.dto.OpendataDto;
import com.etree.opendata.common.dto.OpendataDtoBase;
import com.etree.opendata.common.dto.RegionsDto;
import com.etree.opendata.common.dto.TimezoneDstDto;
import com.etree.opendata.common.dto.TimezonesCountriesDto;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OpendataServiceJsonToDtoConverter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OpendataServiceJsonToDtoConverter.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	public List<OpendataDtoBase> convert(OpendataDto opendataServiceDto, String entities) {
		if (entities == null || entities.trim().isEmpty()) {
			return null;
		}
		String entity = opendataServiceDto.getEntitiesKeyName();
		Class<?> cls = null;
		if (OpendataConstants.AIRPORTS.equalsIgnoreCase(entity)) {
			cls = AirportsDto.class;
		} else if (OpendataConstants.CITIES.equalsIgnoreCase(entity)) {
			cls = CitiesDto.class;
		} else if (OpendataConstants.COUNTRIES.equalsIgnoreCase(entity)) {
			cls = CountriesDto.class;
		} else if (OpendataConstants.CURRENCIES.equalsIgnoreCase(entity)) {
			cls = CurrenciesDto.class;
		} else if (OpendataConstants.LOOKUP_CITY.equalsIgnoreCase(entity)) {
			cls = LookupCityDto.class;
		} else if (OpendataConstants.LOOKUP_COUNTRY.equalsIgnoreCase(entity)) {
			cls = LookupCountryDto.class;
		} else if (OpendataConstants.REGIONS.equalsIgnoreCase(entity)) {
			cls = RegionsDto.class;
		} else if (OpendataConstants.TIMEZONE_DST.equalsIgnoreCase(entity)) {
			cls = TimezoneDstDto.class;
		} else if (OpendataConstants.TIMEZONES_COUNTRIES.equalsIgnoreCase(entity)) {
			cls = TimezonesCountriesDto.class;
		}
		List<OpendataDtoBase> openDataList = null;
		if (cls != null) {
			openDataList = (List<OpendataDtoBase>) convertToPojo(entities, cls, 
					CommonsSupportConstants.COLLECTION_TYPE.LIST); 
		}
		return openDataList;
	}
	
	protected Object convertToPojo(Object value, Class<?> type, COLLECTION_TYPE collectionType) {
		JavaType javaType = null;
		if (COLLECTION_TYPE.LIST == collectionType) {
			javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);
		} else if (COLLECTION_TYPE.MAP == collectionType) {
			if (value instanceof String) {
				javaType = objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, type);
			} else if (value instanceof LinkedHashMap) {
				javaType = objectMapper.getTypeFactory().constructMapType(HashMap.class, LinkedHashMap.class, type);
			}		
		}
		Object result = null;
		if (value instanceof String) {
			try {
				if (javaType != null) {
					result = objectMapper.readValue((String) value, javaType);
				} else {
					result = objectMapper.readValue((String) value, type);
				}
			} catch (IOException e) {
				throw new EtreeCommonsException(e);
			}
		} else {
			if (javaType != null) {
				result = objectMapper.convertValue(value, javaType);
			} else {
				result = objectMapper.convertValue(value, type);
			}
		}
		return result;
	}
	
}