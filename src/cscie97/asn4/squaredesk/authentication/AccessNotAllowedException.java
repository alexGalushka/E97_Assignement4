package cscie97.asn4.squaredesk.authentication;


/**
 * The Class FeatureNotFoundException.
 */
public class AccessNotAllowedException extends Exception
{	
	
	/** serialization. */
	private static final long serialVersionUID = 4830697693803423529L;


	/**
	 * Instantiates a new feature not found exception.
	 */
	public AccessNotAllowedException()
	{ 
		super();
	}
	
	/**
	 * Instantiates a new feature not found exception.
	 *
	 * @param message the message
	 */
	public AccessNotAllowedException( String message ) 
	{
		super( message );
	}
}
