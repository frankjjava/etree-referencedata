package referencedata.entitydto;

import lombok.Data;
import referencedata.dto.ReferencedataBaseDto;

@Data
public class AirportsDto implements ReferencedataBaseDto {
	private String code;
	private String name;
	private String city;
	private String country;
	private String key;
	
}
