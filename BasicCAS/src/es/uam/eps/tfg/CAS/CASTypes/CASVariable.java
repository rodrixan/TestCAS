package es.uam.eps.tfg.CAS.CASTypes;

public class CASVariable extends CASElement {

	public CASVariable(String name) {
		this.elemRepresentation = name;
		this.elemValue = NAN;
	}

	@Override
	public CASElemType getType() {
		return CASElemType.VARIABLE;
	};

}
