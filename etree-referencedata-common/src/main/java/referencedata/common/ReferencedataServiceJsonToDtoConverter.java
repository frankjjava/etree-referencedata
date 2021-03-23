/**
 * Copyright © 2020 eTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2020-11-04 
 */
package referencedata.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etree.commons.core.CommonsSupportConstants;
import com.etree.commons.core.CommonsSupportConstants.COLLECTION_TYPE;
import com.etree.commons.core.exception.EtreeCommonsException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import referencedata.dto.ReferencedataDto;
import referencedata.entitydto.AirportsDto;
import referencedata.entitydto.CitiesDto;
import referencedata.entitydto.CountriesDto;
import referencedata.entitydto.CurrenciesDto;
import referencedata.entitydto.LookupCityDto;
import referencedata.entitydto.LookupCountryDto;
import referencedata.entitydto.RegionsDto;
import referencedata.entitydto.TimezoneDstDto;
import referencedata.entitydto.TimezonesCountriesDto;
import referencedata.dto.ReferencedataBaseDto;

public class ReferencedataServiceJsonToDtoConverter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReferencedataServiceJsonToDtoConverter.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	public List<ReferencedataBaseDto> convert(ReferencedataDto opendataServiceDto, String entities) {
		if (entities == null || entities.trim().isEmpty()) {
			return null;
		}
		String entity = opendataServiceDto.getEntitiesKeyName();
		Class<?> cls = null;
		if (ReferencedataConstants.AIRPORTS.equalsIgnoreCase(entity)) {
			cls = AirportsDto.class;
		} else if (ReferencedataConstants.CITIES.equalsIgnoreCase(entity)) {
			cls = CitiesDto.class;
		} else if (ReferencedataConstants.COUNTRIES.equalsIgnoreCase(entity)) {
			cls = CountriesDto.class;
		} else if (ReferencedataConstants.CURRENCIES.equalsIgnoreCase(entity)) {
			cls = CurrenciesDto.class;
		} else if (ReferencedataConstants.LOOKUP_CITY.equalsIgnoreCase(entity)) {
			cls = LookupCityDto.class;
		} else if (ReferencedataConstants.LOOKUP_COUNTRY.equalsIgnoreCase(entity)) {
			cls = LookupCountryDto.class;
		} else if (ReferencedataConstants.REGIONS.equalsIgnoreCase(entity)) {
			cls = RegionsDto.class;
		} else if (ReferencedataConstants.TIMEZONE_DST.equalsIgnoreCase(entity)) {
			cls = TimezoneDstDto.class;
		} else if (ReferencedataConstants.TIMEZONES_COUNTRIES.equalsIgnoreCase(entity)) {
			cls = TimezonesCountriesDto.class;
		}
		List<ReferencedataBaseDto> openDataList = null;
		if (cls != null) {
			openDataList = (List<ReferencedataBaseDto>) convertToPojo(entities, cls, 
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