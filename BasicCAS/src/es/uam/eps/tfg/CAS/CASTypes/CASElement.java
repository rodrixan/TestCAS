package es.uam.eps.tfg.CAS.CASTypes;

/**
 * Basic class for the entire types of the CAS
 *
 * @author Rodri
 *
 */
public abstract class CASElement {

	public enum CASElemType {
		LIST, NUMBER, VARIABLE, OPERATION, ELEMENT
	};

	/**
	 * @return the type of the element
	 * @see CASElemType
	 */
	abstract public CASElemType getType();

	/**
	 * @return a string representation of the element
	 */
	abstract public String getRepresentation();

	/**
	 * @return the integer value of the element
	 */
	abstract public int getValue();

	/**
	 * @return infix notation string representation of the element
	 */
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

	/**
	 * @return number of elements (1 if single, list size if list, 0 if
	 *         operation)
	 */
	public abstract int size();

}
