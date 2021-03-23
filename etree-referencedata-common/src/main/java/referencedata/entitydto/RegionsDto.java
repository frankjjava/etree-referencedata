package referencedata.entitydto;

import lombok.Data;
import referencedata.dto.ReferencedataBaseDto;

@Data
public class RegionsDto implements ReferencedataBaseDto {
	private String code;
	private String regionName;
	private String description;
	private String status;
}
