package es.uam.eps.tfg.CAS.CASTypes;

/**
 * Comparator used to know if one element is the inverse of another one.
 *
 * @author Rodrigo de Blas
 *
 * @param <T>
 *            Class of the elements to compare
 */
public interface InverseElementComparator<T> {
    /**
     * Checks if two elements are inverse
     * 
     * @param o1
     *            first element
     * @param o2
     *            second element
     * @return true if the elements were inverse, false if not
     */
    boolean areInverse(T o1, T o2);
}
