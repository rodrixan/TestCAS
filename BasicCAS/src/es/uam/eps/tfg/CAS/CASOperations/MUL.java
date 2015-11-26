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
		((CASList) param).moveElement(fromIndex, toIndex);
	}

	public boolean associateMUL(int fromIndex, int toIndex) {

		if (fromIndex == 0 && toIndex == ((CASList) param).size() - 1) {
			return true;
		}

		final int paramListSize = ((CASList) param).size();
		final CASList preAssocitedElems = ((CASList) param).subList(0, fromIndex);
		final CASList postAssociatedElems = ((CASList) param).subList(toIndex, paramListSize);

		final CASList associtatedElemList = ((CASList) param).subList(fromIndex, toIndex);

		final MUL associatedElemOperation = new MUL(associtatedElemList);

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
	public int getElemValue() {
		int mulValue = 0;
		for (int i = 0; i < ((CASList) param).size(); i++) {
			mulValue *= ((CASList) param).get(i).getElemValue();
		}
		return mulValue;
	}

	public boolean identityMUL() {
		final int indexOfOne = ((CASList) param).indexOf(CASConstants.ONE);
		if (indexOfOne != -1) {
			((CASList) param).remove(indexOfOne);
			return true;
		}
		return false;
	}
}
