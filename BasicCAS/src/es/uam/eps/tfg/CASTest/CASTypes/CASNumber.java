package es.uam.eps.tfg.CASTest.CASTypes;

public class CASNumber extends CASElement {

	public CASNumber(int value) {
		this.expressionValue = value;
		this.elemRepresentation = expressionValue.toString();
	}

	@Override
	public CASElemType getType() {
		return CASElemType.NUMBER;
	};
}
