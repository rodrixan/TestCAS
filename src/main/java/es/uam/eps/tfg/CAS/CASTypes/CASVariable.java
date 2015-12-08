package es.uam.eps.tfg.CAS.CASTypes;

/**
 * CAS Variables (non-numeric). Can have a value.
 *
 * @author Rodrigo de Blas
 *
 */
public class CASVariable extends CASElement {

	private String name;
	private int value;

	public CASVariable(String name) {
		this.name = name;
		this.value = CASConstants.NAN;
	}

	public CASVariable(String name, int value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public CASElemType getType() {
		return CASElemType.VARIABLE;
	};

	public void setValue(int value) {
		this.value = value;
	}

	public void rename(String name) {
		this.name = name;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public String getRepresentation() {
		return name;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String toInfixNotation() {
		return getRepresentation();
	}
}
