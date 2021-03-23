package referencedata.entitydto;

import lombok.Data;
import referencedata.dto.ReferencedataBaseDto;

@Data
public class CurrenciesDto implements ReferencedataBaseDto {
	private String isoCode;
	private String currencyName;
	private int rounding;
	private int decimals;
	private String description;
	private String status;
	
}
