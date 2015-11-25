package es.uam.eps.tfg.CAS.CASTypes;

public class CASVariable extends CASElement {

	private String name;
	private int value;

	public CASVariable(String name) {
		this.name = name;
		this.value = NAN;
	}

	public CASVariable(String name, int value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public CASElemType getType() {
		return CASElemType.VARIABLE;
	};

	public void setElemValue(int value) {
		this.value = value;
	}

	public void setElemRepresentation(String representation) {
		this.name = representation;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public String getElemRepresentation() {
		return name;
	}

	@Override
	public int getElemValue() {
		return value;
	}
}
