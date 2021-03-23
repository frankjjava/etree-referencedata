package referencedata.entitydto;

import lombok.Data;
import referencedata.dto.ReferencedataBaseDto;

@Data
public class CountriesDto implements ReferencedataBaseDto {
	private String isoCodeAlpha2;
	private String isoCodeAlpha3;
	private String isoCodeNumeric;
	private String countryName;
	private String isdCode;
	private String status;
	private String currencyCode;
	private String regionCode;

}
