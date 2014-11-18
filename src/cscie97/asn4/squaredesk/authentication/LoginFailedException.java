package cscie97.asn4.squaredesk.authentication;


/**
 * The Class FeatureNotFoundException.
 */
public class LoginFailedException extends Exception
{	
	
	/** serialization. */
	private static final long serialVersionUID = 859266886328631955L;

	/**
	 * Instantiates a new feature not found exception.
	 */
	public LoginFailedException()
	{ 
		super();
	}
	
	/**
	 * Instantiates a new feature not found exception.
	 *
	 * @param message the message
	 */
	public LoginFailedException( String message ) 
	{
		super( message );
	}
}
