package es.uam.eps.tfg.CAS.CASTypes;

/**
 * Numbers of the CAS (Constants)
 *
 * @author Rodrigo de Blas
 *
 */
public class CASNumber extends CASElement {

	private int value;

	public CASNumber(int value) {
		this.value = value;
	}

	@Override
	public CASElemType getType() {
		return CASElemType.NUMBER;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public String getRepresentation() {
		return value + "";
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String toInfixNotation() {
		return getRepresentation();
	};
}
