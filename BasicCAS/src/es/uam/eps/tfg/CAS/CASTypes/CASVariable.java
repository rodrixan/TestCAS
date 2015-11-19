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

	public void setElemValue(int value) {
		this.elemValue = value;
		this.elemRepresentation = Integer.valueOf(value).toString();
	}

	public void setElemRepresentation(String representation) {
		this.elemRepresentation = representation;
	}
}
