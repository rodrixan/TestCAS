package es.uam.eps.tfg.CAS.CASOperations;

import es.uam.eps.tfg.CAS.CASTypes.CASElement;
import es.uam.eps.tfg.CAS.CASTypes.CASNumber;
import es.uam.eps.tfg.CAS.CASTypes.CASOperation;
import es.uam.eps.tfg.CAS.CASTypes.CASVariable;

public class NEG extends CASOperation {
	public static final String NEG_NAME = "NEG";
	public static final String NEG_OPERATOR = "-";

	public NEG(CASOperation operation) {
		super(NEG_NAME, NEG_OPERATOR, operation);
	}

	public NEG(CASVariable variable) {
		super(NEG_NAME, NEG_OPERATOR, variable);
	}

	public NEG(CASNumber number) {
		super(NEG_NAME, NEG_OPERATOR, number);
	}

	@Override
	public int getValue() {
		return -(param.getValue());
	}

	public CASElement getParam() {
		return param;
	}

	@Override
	public String toInfixNotation() {
		return "(" + NEG_OPERATOR + param.toInfixNotation() + ")";
	}

}
