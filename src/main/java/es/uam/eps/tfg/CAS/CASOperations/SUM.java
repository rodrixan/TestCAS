package es.uam.eps.tfg.CAS.CASOperations;

import es.uam.eps.tfg.CAS.CASTypes.CASConstants;
import es.uam.eps.tfg.CAS.CASTypes.CASElement;
import es.uam.eps.tfg.CAS.CASTypes.CASList;
import es.uam.eps.tfg.CAS.CASTypes.CASOperation;

public class SUM extends CASOperation {

	public static final String SUM_NAME = "SUM";
	public static final String SUM_OPERATOR = "+";

	public SUM(CASList listParam) {
		super(SUM_NAME, SUM_OPERATOR, listParam);
	}

	public SUM() {
		super(SUM_NAME, SUM_OPERATOR, new CASList());
	}

	public void setParameter(CASList param) {
		super.setParameter(param);
	}

	public void conmuteSUM(int fromIndex, int toIndex) {
		super.conmutativeProperty(fromIndex, toIndex, SUM.class);
	}

	public boolean associateSUM(int fromIndex, int toIndex) {
		return super.associativeProperty(fromIndex, toIndex, SUM.class);
	}

	public int getPositionOf(CASElement element) {
		return ((CASList) param).indexOf(element);
	}

	public CASElement getParamAt(int position) {
		return ((CASList) param).get(position);
	}

	@Override
	public int getValue() {
		int sumValue = 0;

		for (final CASElement e : ((CASList) param)) {
			sumValue += e.getValue();
		}
		return sumValue;
	}

	public boolean identitySUM() {
		return super.identityProperty(SUM.class, CASConstants.ZERO, this);
	}

	public boolean disassociateSUM() {

		int indexOfSUM = 0;
		int timesFound = 0;

		while (indexOfSUM != -1) {
			indexOfSUM = firstIndexOf(SUM_NAME);
			if (indexOfSUM == -1) {
				break;
			}

			moveSUMParamsToThisParams(indexOfSUM);

			timesFound++;
		}
		return timesFound != 0;
	}

	private void moveSUMParamsToThisParams(int indexOfSUM) {
		final SUM SUMParam = (SUM) this.getParamAt(indexOfSUM);

		((CASList) param).remove(indexOfSUM);

		((CASList) param).set(indexOfSUM, SUMParam.param);
	}

	public boolean contains(String operationName) {
		return (firstIndexOf(operationName) != -1);
	}

	public int firstIndexOf(String operationName) {

		for (final CASElement e : ((CASList) param)) {
			if (e.getType() == CASElemType.OPERATION && ((CASOperation) e).getOperationName().equals(operationName)) {
				return ((CASList) param).indexOf(e);
			}
		}
		return -1;
	}

	@Override
	public String toInfixNotation() {
		final StringBuilder builder = new StringBuilder();

		builder.append("(");
		for (final CASElement e : ((CASList) param)) {
			builder.append(e.toInfixNotation() + SUM_OPERATOR);
		}
		final String cleanString = super.removeLastOperator(builder.toString());

		return cleanString + ")";
	}

	@Override
	public boolean areInverse(CASElement o1, CASElement o2) {
		final int sum = o1.getValue() + o2.getValue();
		return sum == CASConstants.ZERO.getValue();
	}

}
