package es.uam.eps.tfg.CAS.test.CASOperations;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.uam.eps.tfg.CAS.CASOperations.SUM;
import es.uam.eps.tfg.CAS.CASTypes.CASList;
import es.uam.eps.tfg.CAS.CASTypes.CASVariable;

public class SUMTest {

	CASList paramList;
	SUM sumOperation;

	@Before
	public void setup() {
		paramList = createSampleParamList();
		sumOperation = new SUM(paramList);
	}

	@Test
	public void shouldConmuteParamList() {
		sumOperation.conmuteSUM(0, 2);
		assertEquals("SUM(b,c,a,d)", sumOperation.getElemRepresentation());
	}

	@Test
	public void shouldAssociateParamList() {
		final SUM associateSUM = new SUM(CASList.concat(new CASVariable("a"), new CASVariable("b")));
		sumOperation.associateSUM(0, 2);
		assertEquals(3, sumOperation.paramSize());
		assertEquals(0, sumOperation.getPositionOf(associateSUM));
		assertEquals("SUM(SUM(a,b),c,d)", sumOperation.getElemRepresentation());
	}

	@Test
	public void shouldConmuteAndAssociateParamList() {
		sumOperation.associateSUM(0, 2);
		sumOperation.conmuteSUM(0, 1);
		assertEquals("SUM(c,SUM(a,b),d)", sumOperation.getElemRepresentation());
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
