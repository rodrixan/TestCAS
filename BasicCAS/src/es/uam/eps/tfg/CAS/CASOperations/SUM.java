package es.uam.eps.tfg.CAS.CASOperations;

import es.uam.eps.tfg.CAS.CASTypes.CASList;
import es.uam.eps.tfg.CAS.CASTypes.CASOperation;

public class SUM extends CASOperation {

	public static final String SUM_NAME = "SUM";
	public static final String SUM_OPERATOR = "+";

	public SUM(CASList listParam) {
		super(SUM_NAME, SUM_OPERATOR, listParam);
	}

	public void conmuteSUM(int fromIndex, int toIndex) {
		((CASList) param).moveElement(fromIndex, toIndex);
	}

	public boolean associateSUM(int fromIndex, int toIndex) {
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

	@Override
	public int getElemValue() {
		int sumValue = 0;
		for (int i = 0; i < ((CASList) param).size(); i++) {
			sumValue += ((CASList) param).get(i).getElemValue();
		}
		return sumValue;
	}
}
