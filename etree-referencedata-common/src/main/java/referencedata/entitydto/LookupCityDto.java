package referencedata.entitydto;

import lombok.Data;
import referencedata.dto.ReferencedataBaseDto;

@Data
public class LookupCityDto implements ReferencedataBaseDto {
	private String isoCode;
	private String cityName;
	private String countryCode;
	
}
