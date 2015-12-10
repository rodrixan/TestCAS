package es.uam.eps.tfg.CAS.CASOperations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import es.uam.eps.tfg.CAS.CASTypes.CASList;
import es.uam.eps.tfg.CAS.CASTypes.CASNumber;
import es.uam.eps.tfg.CAS.CASTypes.CASVariable;

public class OperationsIntegrationTest {
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

	@Test
	public void shouldReturnInfixNotation() {
		final SUM sumExp = new SUM(createSampleParamList());
		mulOperationUUT = new MUL(CASList.concat(new CASVariable("c"), sumExp, new NEG(new CASVariable("d"))));
		assertEquals("(c*(a+b)*(-d))", mulOperationUUT.toInfixNotation());
	}

	@Test
	public void shouldRemoveOppositeElementForSUMOperation() {
		sumOperationUUT = new SUM(CASList.concat(new CASNumber(2), new NEG(new CASNumber(2))));
		final boolean expectedResult = sumOperationUUT.identitySUM();
		assertTrue(expectedResult);
		assertEquals(0,sumOperationUUT.getValue());
	}

	private CASList createSampleParamList() {
		final CASList list = new CASList();
		list.add(new CASVariable("a"));
		list.add(new CASVariable("b"));
		return list;
	}

}
