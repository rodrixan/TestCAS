package es.uam.eps.tfg.CAS.CASTypes;

public abstract class CASElement {

	public enum CASElemType {
		LIST, NUMBER, VARIABLE, OPERATION, ELEMENT
	};

	public static final int NAN = -1;

	abstract public CASElemType getType();

	abstract public String getElemRepresentation();

	abstract public int getElemValue();

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
		if (!this.getElemRepresentation().equals(other.getElemRepresentation())) {
			return false;
		}
		if (this.getElemValue() != other.getElemValue()) {
			return false;
		}

		return true;
	}

	public abstract int size();

}
