/**
 * Copyright © 2016 Verteil Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2016-01-15 
 */
package referencedata.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etree.commons.core.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import referencedata.common.ReferencedataConstants;
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
import referencedata.exception.ReferencedataException;

public class ReferenceDataClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceDataClient.class);
	
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String fetch(ReferencedataDto referencedataDto) {
		String entity = referencedataDto.getEntitiesKeyName();
		if (entity == null || entity.trim().isEmpty()) {
			return null;
		}
		Client client = ClientBuilder.newClient();

		WebTarget resource = client.target("http://localhost:2427/" + entity);

		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();
		MessageDto messageDto = response.readEntity(MessageDto.class);
		Object msgResponse = messageDto.getResponse();
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(msgResponse);
		} catch (JsonProcessingException e) {
			throw new ReferencedataException("", e);
		} 
		return jsonString;
	}
	
	public static ReferencedataBaseDto fetchEntity(ReferencedataDto referencedataDto) {
		throw new ReferencedataException("", "Not implemented!");
	}
	
	public static List<ReferencedataBaseDto> fetchList(ReferencedataDto referencedataDto) {
		String json = fetch(referencedataDto);
		List<ReferencedataBaseDto> referenceDataList = convert(json, referencedataDto.getEntitiesKeyName());
		return referenceDataList;
	}
	
	public static List<ReferencedataBaseDto> convert(String json, String entity) {
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
		List<ReferencedataBaseDto> referenceDataList = null;
		if (cls != null) {
			try {
				JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, cls);
				referenceDataList = (List<ReferencedataBaseDto>) objectMapper.readValue(json, javaType); 
			} catch (JsonProcessingException e) {
				throw new ReferencedataException("", e);
			} 
		}
		return referenceDataList;
	}
}