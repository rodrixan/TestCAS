package es.uam.eps.tfg.CAS.CASTypes;

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
	public String getElemRepresentation() {
		return value + "";
	}

	@Override
	public int getElemValue() {
		return value;
	};
}
