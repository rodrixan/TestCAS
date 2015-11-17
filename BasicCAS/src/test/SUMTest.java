package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import operations.SUM;
import types.Element;

public class SUMTest {

	private SUM sumUUT;

	@Before
	public void setup() {
		final List<Element> params = createSampleParams();
		sumUUT = new SUM(params);
	}

	private List<Element> createSampleParams() {
		final List<Element> elemList = new ArrayList<>();
		elemList.add(new Element("a"));
		elemList.add(new Element("b"));
		elemList.add(new Element("c"));
		return elemList;
	}

	@Test
	public void shouldReturnInfixNotationExpression() {
		assertEquals("a + b + c", sumUUT.toString());
	}

	@Test
	public void shouldReturnConmutedExpression() {
		sumUUT.conmutativeSUM(0, 1);
		assertEquals("b + a + c", sumUUT.toString());
	}
}
