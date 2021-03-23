package referencedata.entitydto;

import lombok.Data;
import referencedata.dto.ReferencedataBaseDto;

@Data
public class CitiesDto implements ReferencedataBaseDto {
	private String isoCode;
	private String cityName;
	private String description;
	private String isMetro;
	private String status;
	private String countryCode;
	private String timezoneCode;

}
