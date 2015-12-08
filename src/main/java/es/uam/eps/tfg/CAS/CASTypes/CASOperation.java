package es.uam.eps.tfg.CAS.CASTypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tfg.CAS.CASExceptions.CASOperationCreationException;

/**
 * Parent operation for the CAS. It has the basics for an operation. It also has
 * all the properties an operation can implement.
 *
 * <strong>All inherited classes must implement a constructor with the param as
 * the only argument</strong>.
 *
 * @author Rodrigo de Blas
 *
 */
public abstract class CASOperation extends CASElement {

	private static final Logger LOG = LoggerFactory.getLogger(CASOperation.class);

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

	/**
	 * Removes the last operator in a string. Used for representation of the
	 * operation.
	 *
	 * @param dirtyString
	 *            string to clean, removing the last operator.
	 * @return clean string without the last operator symbol.
	 */
	protected String removeLastOperator(final String dirtyString) {
		final int lastOperatorIndex = dirtyString.lastIndexOf(operator);
		final String cleanString = dirtyString.substring(0, lastOperatorIndex);
		return cleanString;
	}

	/**
	 * Conmutative property: moves an element from one position to another.
	 * <strong>It needs a CASList as param</strong>.
	 *
	 * @param fromIndex
	 *            initial position (of the element to conmute)
	 * @param toIndex
	 *            final position
	 * @param invoker
	 *            class of the operation that invokes the property. <strong>The
	 *            parameter must be a CASList</strong>.
	 * @throws UnsupportedOperationException
	 *             if the operation doesn't allow this property (the param isn't
	 *             a list).
	 */
	protected void conmutativeProperty(int fromIndex, int toIndex, Class<?> invoker) {
		checkParamInstanceofList();
		LOG.debug("Applying conmutative property for class {}", invoker.getName());
		((CASList) param).moveElement(fromIndex, toIndex);
	}

	/**
	 * Associative property: wraps together some elements under the same
	 * operation. <strong>It needs a list as param</strong>.
	 *
	 * @param fromIndex
	 *            initial position
	 * @param toIndex
	 *            final position
	 * @param invoker
	 *            class of the operation that invokes the property. Needed to
	 *            create the same operation. <strong>Must have a constructor
	 *            with only one parameter and the parameter must be a
	 *            CASList</strong>.
	 * @return true if success, false if not.
	 * @throws CASOperationCreationException
	 *             if couldn't create a new operation from invoker using
	 *             reflection
	 * @throws UnsupportedOperationException
	 *             if the param is not a list, or if the class doesn't exist.
	 */
	public boolean associativeProperty(int fromIndex, int toIndex, Class<?> invoker) {

		checkParamInstanceofList();

		LOG.debug("Applying associative property for class {}", invoker.getName());
		if (fromIndex == 0 && toIndex == ((CASList) param).size() - 1) {
			return true;
		}

		final int paramListSize = ((CASList) param).size();
		final CASList prefixElements = ((CASList) param).subList(0, fromIndex);
		final CASList postfixElements = ((CASList) param).subList(toIndex, paramListSize);

		final CASList parameterForOperation = ((CASList) param).subList(fromIndex, toIndex);
		CASOperation associatedOperation = null;
		boolean creationError = true;

		try {
			associatedOperation = createNewOperationFromClass(invoker, parameterForOperation);
			creationError = false;
		} catch (final NoSuchMethodException e) {
			LOG.error("Error when applying associative property: Constructor doesn't exist.");
		} catch (final SecurityException e) {
			LOG.error("Error when applying associative property: Security violation.");
		} catch (final InstantiationException e) {
			LOG.error("Error when applying associative property: Couldn't instantiate the operation {}.",
					invoker.getName());
		} catch (final IllegalAccessException e) {
			LOG.error("Error when applying associative property: Couldn't access the constructor.");
		} catch (final IllegalArgumentException e) {
			LOG.error("Error when applying associative property: Constructor doesn't have the specified parameter.");
		} catch (final InvocationTargetException e) {
			LOG.error("Error when applying associative property: Couldn't invoke the method.");
		}

		if (creationError) {
			throw new CASOperationCreationException("Couldn't create the operation");
		}

		final CASList associatedList = CASList.concat(prefixElements, associatedOperation, postfixElements);

		this.param = associatedList;
		return true;

	}

	/**
	 * Creates an operation of the same class that the invoker. Uses reflection
	 * to achieve it.
	 *
	 * @param fromIndex
	 *            initial position
	 * @param toIndex
	 *            final position
	 * @param invoker
	 *            class of the operation that invokes the property. Needed to
	 *            create the same operation. <strong>Must have a constructor
	 *            with only one parameter and the parameter must be a
	 *            CASList</strong>.
	 * @return new CASOperation of the same class that the invoker with a param
	 *         list from one index to another
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */

	private CASOperation createNewOperationFromClass(Class<?> invoker, final CASList associtatedElemList)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final Constructor<?> classConstructor = invoker.getConstructor(CASList.class);

		final CASOperation associatedElemOperation = (CASOperation) classConstructor.newInstance(associtatedElemList);

		return associatedElemOperation;
	}

	/**
	 * Checks if the param of the operation called is a CASList. If not, throws
	 * an UnsupportedOperationException
	 */
	private void checkParamInstanceofList() {
		if (!(param instanceof CASList)) {
			throw new UnsupportedOperationException("This operation does not support the invoked property.");
		}
	}
}
