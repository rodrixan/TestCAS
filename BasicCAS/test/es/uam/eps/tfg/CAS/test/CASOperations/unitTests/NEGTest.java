package es.uam.eps.tfg.CAS.test.CASOperations.unitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.uam.eps.tfg.CAS.CASOperations.NEG;
import es.uam.eps.tfg.CAS.CASTypes.CASNumber;

public class NEGTest {

	private NEG negOperation;

	@Test
	public void shouldReturnNegativeValue() {
		negOperation = new NEG(new CASNumber(5));
		assertEquals(-5, negOperation.getValue());
	}

	@Test
	public void shouldReturnPositiveValue() {
		negOperation = new NEG(new CASNumber(-3));
		assertEquals(3, negOperation.getValue());
	}

	@Test
	public void shouldReturnRepresentation() {
		negOperation = new NEG(new CASNumber(5));
		assertEquals("NEG(5)", negOperation.getRepresentation());
	}

	@Test
	public void shouldReturnInfixNotation() {
		negOperation = new NEG(new CASNumber(5));
		assertEquals("(-5)", negOperation.toInfixNotation());
	}
}
