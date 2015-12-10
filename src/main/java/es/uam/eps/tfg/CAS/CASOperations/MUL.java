package es.uam.eps.tfg.CAS.CASOperations;

import es.uam.eps.tfg.CAS.CASTypes.CASConstants;
import es.uam.eps.tfg.CAS.CASTypes.CASElement;
import es.uam.eps.tfg.CAS.CASTypes.CASList;
import es.uam.eps.tfg.CAS.CASTypes.CASOperation;

public class MUL extends CASOperation {

	public static final String MUL_NAME = "MUL";
	public static final String MUL_OPERATOR = "*";

	public MUL(CASList listParam) {
		super(MUL_NAME, MUL_OPERATOR, listParam);
	}

	public void conmuteMUL(int fromIndex, int toIndex) {
		super.conmutativeProperty(fromIndex, toIndex, MUL.class);
	}

	public boolean associateMUL(int fromIndex, int toIndex) {
		return super.associativeProperty(fromIndex, toIndex, MUL.class);
	}

	public int getPositionOf(CASElement element) {
		return ((CASList) param).indexOf(element);
	}

	public CASElement getParamAt(int position) {
		return ((CASList) param).get(position);
	}

	@Override
	public int getValue() {
		int mulValue = 0;
		for (final CASElement e : ((CASList) param)) {
			mulValue *= e.getValue();
		}
		return mulValue;
	}

	public boolean identityMUL() {
		return super.identityProperty(MUL.class, CASConstants.ONE, this);
	}

	public boolean disassociateMUL() {

		int indexOfMUL = 0;
		int timesFound = 0;

		while (indexOfMUL != -1) {
			indexOfMUL = firstIndexOf(MUL_NAME);
			if (indexOfMUL == -1) {
				break;
			}

			moveMULParamsToThisParams(indexOfMUL);

			timesFound++;
		}
		return timesFound != 0;
	}

	private void moveMULParamsToThisParams(int indexOfMUL) {
		final MUL MULParam = (MUL) this.getParamAt(indexOfMUL);

		((CASList) param).remove(indexOfMUL);

		((CASList) param).set(indexOfMUL, MULParam.param);
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

	public SUM distributiveMUL(int indexOfElementToDistribute, int indexOfSUMOperation) {
		final CASElement elementAtSUMIndex = getParamAt(indexOfSUMOperation);

		if (elementAtSUMIndex.getType() != CASElemType.OPERATION
				|| ((CASOperation) elementAtSUMIndex).getOperationName() != SUM.SUM_NAME) {
			return null;
		}

		final SUM distributedElement = createDistributiveElement(indexOfElementToDistribute, elementAtSUMIndex);

		removeOldElements(indexOfElementToDistribute, indexOfSUMOperation);

		final int minIndex = Math.min(indexOfElementToDistribute, indexOfSUMOperation);

		if (minIndex <= ((CASList) param).size()) {
			((CASList) param).add(distributedElement);
		} else {
			((CASList) param).set(minIndex, distributedElement);
		}

		return distributedElement;

	}

	private SUM createDistributiveElement(int indexOfElementToDistribute, final CASElement elementAtSUMIndex) {
		final SUM sumElement = (SUM) elementAtSUMIndex;
		final CASElement elementToDistribute = getParamAt(indexOfElementToDistribute);

		final CASList newParams = createDistributedParamList(sumElement, elementToDistribute);

		final SUM distributedElement = new SUM(newParams);
		return distributedElement;
	}

	private void removeOldElements(int indexOfElementToDistribute, int indexOfSUMOperation) {
		if (indexOfSUMOperation > indexOfElementToDistribute) {
			((CASList) param).remove(indexOfSUMOperation);
			((CASList) param).remove(indexOfElementToDistribute);
		} else {
			((CASList) param).remove(indexOfElementToDistribute);
			((CASList) param).remove(indexOfSUMOperation);
		}
	}

	private CASList createDistributedParamList(final SUM sumElement, final CASElement elementToDistribute) {
		final CASList list = new CASList();
		for (int i = 0; i < sumElement.paramSize(); i++) {
			final CASList sumParamWithMulElement = CASList.concat(elementToDistribute, sumElement.getParamAt(i));
			final MUL mulOperation = new MUL(sumParamWithMulElement);
			list.add(mulOperation);
		}
		return list;
	}

	@Override
	public String toInfixNotation() {
		final StringBuilder builder = new StringBuilder();

		builder.append("(");
		for (final CASElement e : ((CASList) param)) {
			builder.append(e.toInfixNotation() + MUL_OPERATOR);
		}
		final String cleanString = super.removeLastOperator(builder.toString());

		return cleanString + ")";
	}

	@Override
	public boolean areInverse(CASElement o1, CASElement o2) {
		final int mul = o1.getValue() * o2.getValue();
		final boolean isNAN = (o1.getValue()==CASConstants.NAN)||(o2.getValue()==CASConstants.NAN);
		return mul == CASConstants.ONE.getValue()&& !isNAN;
	}
}