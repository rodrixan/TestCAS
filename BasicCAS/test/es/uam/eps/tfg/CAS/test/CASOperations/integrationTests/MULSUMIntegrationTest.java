package es.uam.eps.tfg.CAS.test.CASOperations.integrationTests;

import org.junit.Test;

import es.uam.eps.tfg.CAS.CASOperations.MUL;
import es.uam.eps.tfg.CAS.CASOperations.SUM;
import es.uam.eps.tfg.CAS.CASTypes.CASList;
import es.uam.eps.tfg.CAS.CASTypes.CASVariable;

public class MULSUMIntegrationTest {
	MUL mulOperationUUT;
	SUM sumOperationUUT;

	@Test
	public void shouldUseMULDistributiveProperty() {
		final SUM sumExp = new SUM(createSampleParamList());
		mulOperationUUT = new MUL(CASList.concat(new CASVariable("e"), sumExp, new CASVariable("f")));

	}

	private CASList createSampleParamList() {
		final CASList list = new CASList();
		list.add(new CASVariable("a"));
		list.add(new CASVariable("b"));
		list.add(new CASVariable("c"));
		list.add(new CASVariable("d"));
		return list;
	}

}
