package referencedata.entitydto;

import lombok.Data;
import referencedata.dto.ReferencedataBaseDto;

@Data
public class TimezonesCountriesDto implements ReferencedataBaseDto {

	private int timezoneCode;
	private String timezoneId;
	private String timezone;
	private String description;
	private String city;
	private String stateOrTerritory;
	private String country;
	
}
