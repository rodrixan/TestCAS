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
 * @see InverseElementComparator
 */
public abstract class CASOperation extends CASElement implements InverseElementComparator<CASElement> {

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

    private CASOperation createNewOperationFromClass(Class<?> invoker, CASList associtatedElemList)
	    throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
	    IllegalArgumentException, InvocationTargetException {
	final Constructor<?> classConstructor = invoker.getConstructor(CASList.class);

	final CASOperation associatedElemOperation = (CASOperation) classConstructor.newInstance(associtatedElemList);

	return associatedElemOperation;
    }

    /**
     * Identity property: removes the elements that are neutral factors or
     * inverse of another.
     *
     * @param invoker
     *            class of the operation that invokes the property. Needed to
     *            create the same operation. <strong>Must have a constructor
     *            with only one parameter and the parameter must be a
     *            CASList</strong>.
     * @param neutralFactor
     *            neutral factor of the operation
     * @param inverseChecker
     *            class with the method 'areInverse'
     * @return true if were applied, false if not.
     * @see InverseElementComparator
     */
    public boolean identityProperty(Class<?> invoker, CASNumber neutralFactor,
	    InverseElementComparator<CASElement> inverseChecker) {

	checkParamInstanceofList();
	LOG.debug("Applying identity property for class {}", invoker.getName());

	final boolean usedNeutralFactor = removeNeutralFactor(neutralFactor);
	final boolean usedInverseElement = removeInverseElements(inverseChecker);

	return usedNeutralFactor || usedInverseElement;
    }

    /**
     * Removes the neutral factor from the param list
     *
     * @param neutralFactor
     *            element used as the neutral factor for the operation
     * @return true if were removed, false if not
     */
    private boolean removeNeutralFactor(CASNumber neutralFactor) {
	int indexOfNeutralFactor = 0;
	int times = 0;
	while (indexOfNeutralFactor != -1) {
	    indexOfNeutralFactor = ((CASList) param).indexOf(neutralFactor);
	    if (indexOfNeutralFactor != -1) {
		((CASList) param).remove(indexOfNeutralFactor);
		LOG.debug("Neutral factor found at position {}", indexOfNeutralFactor);
		times++;
	    }
	}
	LOG.debug("Removed neutral factor {} times.", times);
	return times != 0;
    }

    /**
     * Removes elements which are inverse from the param list
     *
     * @param inverseChecker
     *            class with the method 'areInverse'
     * @return true if elements were removed, false if not.
     * @see InverseElementComparator
     */
    private boolean removeInverseElements(InverseElementComparator<CASElement> inverseChecker) {
	int times = 0;
	for (int i = 0; i < param.size(); i++) {

	    for (int j = i + 1; j < param.size(); j++) {

		if (inverseChecker.areInverse(((CASList) param).get(i), ((CASList) param).get(j))) {
		    LOG.debug("Inverse elements found at positions {},{}", i, j);
		    removeElementsByOrder(j, i);
		    times++;
		}
	    }

	}
	LOG.debug("Removed inverse elements {}", times);
	return times != 0;
    }

    /**
     * Removes two elements by the given order
     *
     * @param first
     *            index of first element to remove.
     * @param second
     *            index of the second element to remove
     */
    private void removeElementsByOrder(int first, int second) {
	((CASList) param).remove(first);
	((CASList) param).remove(second);

    }

    /**
     * Checks if the param of the operation called is a CASList. If not, throws
     * an UnsupportedOperationException
     */
    private void checkParamInstanceofList() {
	if (!(param instanceof CASList)) {
	    LOG.error("Tried to use param which is not a CASList as one");
	    throw new UnsupportedOperationException("This operation does not support the invoked property.");
	}
    }

    @Override
    public boolean areInverse(CASElement o1, CASElement o2) {
	return false;
    }
}
