package es.uam.eps.tfg.CAS.CASTypes;

public class CASNumber extends CASElement {

	public CASNumber(int value) {
		this.elemValue = value;
		this.elemRepresentation = elemValue.toString();
	}

	@Override
	public CASElemType getType() {
		return CASElemType.NUMBER;
	};
}
