package es.uam.eps.tfg.CASTest.CASTypes;

public abstract class CASElement {

	public enum CASElemType {
		LIST, NUMBER, VARIABLE, OPERATION, ELEMENT
	};

	public static final int NAN = -1;

	protected CASElemType type;

	protected String elemRepresentation;

	protected Integer expressionValue;

	public CASElemType getType() {
		return CASElemType.ELEMENT;
	};

	String getElemRepresentation() {
		return elemRepresentation;
	}

	Integer getExpressionValue() {
		return expressionValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CASElement)) {
			return false;
		}
		final CASElement other = (CASElement) obj;

		if (!this.type.equals(other.getType())) {
			return false;
		}
		if (!this.elemRepresentation.equals(other.getElemRepresentation())) {
			return false;
		}
		if (this.expressionValue != other.getExpressionValue()) {
			return false;
		}

		return true;
	}

}
