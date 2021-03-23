package referencedata.test;

import java.util.List;

import org.junit.jupiter.api.Test;

import referencedata.client.ReferenceDataClient;
import referencedata.dto.ReferencedataDto;
import referencedata.dto.ReferencedataBaseDto;

public class TestClient {

	@Test
	public void runTest() {
		String entity = "countries";
		ReferencedataDto referencedataDto = new ReferencedataDto();
		referencedataDto.setEntitiesKeyName(entity);
		List<ReferencedataBaseDto> referencedataBaseDtos = ReferenceDataClient.fetchList(referencedataDto); 
		for (ReferencedataBaseDto referencedataBaseDto : referencedataBaseDtos) {
			System.out.println(referencedataBaseDto);
		}
		return;
	}
}
