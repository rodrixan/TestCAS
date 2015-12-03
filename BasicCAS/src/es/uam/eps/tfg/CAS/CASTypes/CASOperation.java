package es.uam.eps.tfg.CAS.CASTypes;

public abstract class CASOperation extends CASElement {

	private final String operationName;
	private final String operator;
	protected CASElement param;

	public CASOperation(String operationName, String operator, CASElement param) {
		this.operationName = operationName;
		this.operator = operator;
		this.param = param;
	}

	public void setParameter(CASElement param) {
		this.param = param;
	}

	public String getOperationName() {
		return operationName;
	}

	public String getOperator() {
		return operator;
	}

	@Override
	public CASElemType getType() {
		return CASElemType.OPERATION;
	}

	@Override
	public String getRepresentation() {
		return operationName + "(" + param.getRepresentation() + ")";
	}

	@Override
	public int getValue() {
		return CASConstants.NAN;
	}

	@Override
	public int size() {
		return 0;
	}

	public int paramSize() {
		return param.size();
	}

	protected String removeLastOperator(final String dirtyString) {
		final int lastOperatorIndex = dirtyString.lastIndexOf(operator);
		final String cleanString = dirtyString.substring(0, lastOperatorIndex);
		return cleanString;
	}

}
