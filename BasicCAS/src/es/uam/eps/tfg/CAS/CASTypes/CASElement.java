package es.uam.eps.tfg.CAS.CASTypes;

public abstract class CASElement {

	public enum CASElemType {
		LIST, NUMBER, VARIABLE, OPERATION, ELEMENT
	};

	abstract public CASElemType getType();

	abstract public String getRepresentation();

	abstract public int getValue();

	abstract public String toInfixNotation();

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CASElement)) {
			return false;
		}
		final CASElement other = (CASElement) obj;

		if (!this.getType().equals(other.getType())) {
			return false;
		}
		if (!this.getRepresentation().equals(other.getRepresentation())) {
			return false;
		}
		if (this.getValue() != other.getValue()) {
			return false;
		}

		return true;
	}

	public abstract int size();

}
