package es.uam.eps.tfg.CAS.CASTypes;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Basic class for the entire types of the CAS
 *
 * @author Rodri
 *
 */
public abstract class CASElement {

	/** Element types of the CAS. */
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

	/**
	 * @return number of elements (1 if single, list size if list, 0 if
	 *         operation)
	 */
	public abstract int size();

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CASElement)) {
			return false;
		}
		final CASElement other = (CASElement) obj;

		return new EqualsBuilder().append(this.getType(), other.getType()).append(this.getValue(), other.getValue())
				.append(this.getRepresentation(), other.getRepresentation()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getType()).append(this.getValue()).append(this.getRepresentation())
				.hashCode();
	}

}
