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
		((CASList) param).moveElement(fromIndex, toIndex);
	}

	public boolean associateSUM(int fromIndex, int toIndex) {

		if (fromIndex == 0 && toIndex == ((CASList) param).size() - 1) {
			return true;
		}
		final int paramListSize = ((CASList) param).size();
		final CASList preAssocitedElems = ((CASList) param).subList(0, fromIndex);
		final CASList postAssociatedElems = ((CASList) param).subList(toIndex, paramListSize);

		final CASList associtatedElemList = ((CASList) param).subList(fromIndex, toIndex);

		final SUM associatedElemOperation = new SUM(associtatedElemList);

		final CASList associatedList = CASList.concat(preAssocitedElems, associatedElemOperation, postAssociatedElems);

		if (associatedList != null) {
			this.param = associatedList;
			return true;
		}
		return false;
	}

	public int getPositionOf(CASElement element) {
		if (((CASList) param).contains(element)) {
			return ((CASList) param).indexOf(element);
		}
		return -1;
	}

	public CASElement getParamAt(int position) {
		return ((CASList) param).get(position);
	}

	@Override
	public int getValue() {
		int sumValue = 0;
		for (int i = 0; i < ((CASList) param).size(); i++) {
			sumValue += ((CASList) param).get(i).getValue();
		}
		return sumValue;
	}

	public boolean identitySUM() {
		final int indexOfZero = ((CASList) param).indexOf(CASConstants.ZERO);
		if (indexOfZero != -1) {
			((CASList) param).remove(indexOfZero);
			return true;
		}
		return false;
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

		for (int i = 0; i < paramSize(); i++) {
			final CASElement e = ((CASList) param).get(i);
			if (e.getType() == CASElemType.OPERATION && ((CASOperation) e).getOperationName().equals(operationName)) {
				return i;
			}
		}
		return -1;
	}
}
