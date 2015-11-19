package es.uam.eps.tfg.CAS.CASTypes;

public abstract class CASElement {

	public enum CASElemType {
		LIST, NUMBER, VARIABLE, OPERATION, ELEMENT
	};

	public static final int NAN = -1;

	protected CASElemType type;

	protected String elemRepresentation;

	protected Integer elemValue;

	abstract public CASElemType getType();

	public String getElemRepresentation() {
		return elemRepresentation;
	}

	public int getElemValue() {
		return elemValue;
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
		if (this.elemValue != other.getElemValue()) {
			return false;
		}

		return true;
	}

}
