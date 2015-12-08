package es.uam.eps.tfg.CAS.CASExceptions;

/**
 * CAS exception for error in CASOperation creation when using reflection
 * 
 * @author Rodrigo de Blas
 *
 */
public class CASOperationCreationException extends RuntimeException {
	private static final long serialVersionUID = -6165601537700680788L;

	public CASOperationCreationException(String message) {
		super(message);
	}
}
