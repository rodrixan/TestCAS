package es.uam.eps.tfg.CASTest.CASTypes;

public class CASVariable extends CASElement {

	public CASVariable(String name) {
		this.elemRepresentation = name;
		this.expressionValue = NAN;
	}

	@Override
	public CASElemType getType() {
		return CASElemType.VARIABLE;
	};

}
