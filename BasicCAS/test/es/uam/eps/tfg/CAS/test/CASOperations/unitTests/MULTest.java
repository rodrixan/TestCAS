package es.uam.eps.tfg.CAS.test.CASOperations.unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import es.uam.eps.tfg.CAS.CASOperations.MUL;
import es.uam.eps.tfg.CAS.CASTypes.CASConstants;
import es.uam.eps.tfg.CAS.CASTypes.CASList;
import es.uam.eps.tfg.CAS.CASTypes.CASVariable;

public class MULTest {

	CASList paramList;
	MUL mulOperation;

	@Before
	public void setup() {
		paramList = createSampleParamList();
		mulOperation = new MUL(paramList);
	}

	@Test
	public void shouldConmuteParamList() {
		mulOperation.conmuteMUL(0, 2);
		assertEquals("MUL(b,c,a,d)", mulOperation.getRepresentation());
	}

	@Test
	public void shouldAssociateParamList() {
		final MUL associateMUL = new MUL(CASList.concat(new CASVariable("a"), new CASVariable("b")));
		mulOperation.associateMUL(0, 2);
		assertEquals(3, mulOperation.paramSize());
		assertEquals(associateMUL, mulOperation.getParamAt(0));
		assertEquals("MUL(MUL(a,b),c,d)", mulOperation.getRepresentation());
	}

	@Test
	public void shouldDisassociateParamList() {
		mulOperation.associateMUL(1, 3);
		assertTrue(mulOperation.disassociateMUL());
		assertEquals("MUL(a,b,c,d)", mulOperation.getRepresentation());
	}

	@Test
	public void shouldConmuteAndAssociateParamList() {
		mulOperation.associateMUL(0, 2);
		mulOperation.conmuteMUL(0, 1);
		assertEquals("MUL(c,MUL(a,b),d)", mulOperation.getRepresentation());
	}

	@Test
	public void shouldRemoveZero() {
		mulOperation = new MUL(CASList.concat(new CASVariable("a"), new CASVariable("b"), CASConstants.ONE));
		assertTrue(mulOperation.identityMUL());
		assertEquals(2, mulOperation.paramSize());
		assertEquals("MUL(a,b)", mulOperation.getRepresentation());
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
