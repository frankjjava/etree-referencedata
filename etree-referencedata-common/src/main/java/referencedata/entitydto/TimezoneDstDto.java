package referencedata.entitydto;

import java.util.Date;

import lombok.Data;
import referencedata.dto.ReferencedataBaseDto;

@Data
public class TimezoneDstDto implements ReferencedataBaseDto {
	private String dstId;
	private String dstName;
	private String description;
	private Date dstStartDate;
	private Date dstEndDate;
	private int dstVariationMinutes;
	private String status;

}
