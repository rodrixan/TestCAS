package es.uam.eps.tfg.CAS.CASTypes;

public abstract class CASOperation extends CASElement {

	private final String operationName;
	private final String operator;
	private CASElement param;

	public CASOperation(String operationName, String operator, CASElement param) {
		this.operationName = operationName;
		this.operator = operator;
		this.param = param;
	}

	public String getOperationName() {
		return operationName;
	}

	public String getOperator() {
		return operator;
	}

	public CASElement getParam() {
		return param;
	}

	@Override
	public CASElemType getType() {
		return CASElemType.OPERATION;
	}

	@Override
	public String getElemRepresentation() {
		return operationName + "(" + param.getElemRepresentation() + ")";
	}

	@Override
	public Integer getElemValue() {
		return NAN;
	}

}