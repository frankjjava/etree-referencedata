package referencedata.entitydto;

import lombok.Data;
import referencedata.dto.ReferencedataBaseDto;

@Data
public class LookupCountryDto implements ReferencedataBaseDto {
	private String isoCode;
	private String countryName;
	
}
