package es.uam.eps.tfg.CAS.test.CASOperations.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
		mulOperationUUT = new MUL(CASList.concat(new CASVariable("c"), sumExp, new CASVariable("d")));

		final SUM distributedResult = mulOperationUUT.distributiveMUL(2, 1);

		assertNotNull(distributedResult);
		assertEquals("SUM(MUL(d,a),MUL(d,b))", distributedResult.getRepresentation());
		assertEquals("MUL(c,SUM(MUL(d,a),MUL(d,b)))", mulOperationUUT.getRepresentation());

	}

	private CASList createSampleParamList() {
		final CASList list = new CASList();
		list.add(new CASVariable("a"));
		list.add(new CASVariable("b"));
		return list;
	}

}
