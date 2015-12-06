package es.uam.eps.tfg.CAS.CASTypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

	protected void conmutativeProperty(int fromIndex, int toIndex) throws UnsupportedOperationException {
		checkParamInstanceofList();
		((CASList) param).moveElement(fromIndex, toIndex);
	}

	public boolean associativeProperty(int fromIndex, int toIndex, Class<?> invoker)
			throws UnsupportedOperationException {

		checkParamInstanceofList();

		if (fromIndex == 0 && toIndex == ((CASList) param).size() - 1) {
			return true;
		}
		final int paramListSize = ((CASList) param).size();
		final CASList preAssocitedElems = ((CASList) param).subList(0, fromIndex);
		final CASList postAssociatedElems = ((CASList) param).subList(toIndex, paramListSize);

		final CASOperation associatedElemOperation = createAssociatedOperation(fromIndex, toIndex, invoker);

		final CASList associatedList = CASList.concat(preAssocitedElems, associatedElemOperation, postAssociatedElems);

		if (associatedList != null) {
			this.param = associatedList;
			return true;
		}
		return false;
	}

	private CASOperation createAssociatedOperation(int fromIndex, int toIndex, Class<?> invoker) {
		final CASList associtatedElemList = ((CASList) param).subList(fromIndex, toIndex);

		CASOperation associatedElemOperation = null;
		try {
			associatedElemOperation = createNewOperationFromClass(invoker, associtatedElemList);
		} catch (final Exception e) {

			System.out.println(e.getMessage());
		}
		return associatedElemOperation;
	}

	private CASOperation createNewOperationFromClass(Class<?> invoker, final CASList associtatedElemList)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final Constructor<?> classConstructor = invoker.getConstructor(CASList.class);

		final CASOperation associatedElemOperation = (CASOperation) classConstructor.newInstance(associtatedElemList);

		return associatedElemOperation;
	}

	private void checkParamInstanceofList() {
		if (!(param instanceof CASList)) {
			throw new UnsupportedOperationException("This operation does not support conmutative property.");
		}
	}
}
