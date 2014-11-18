package cscie97.asn4.squaredesk.authentication;


public class UserNamePassword 
{
	private String id;
	private String login;
	private String passwordHash;
	private Utils credUtils;
	
	public UserNamePassword()
	{
		this.id = "";
		this.passwordHash = "";
		this.login = "";
		this.credUtils = new Utils();
	}
	
	public UserNamePassword( String id, String login, String password )
	{
		this.id = id;
		this.login = login;
		this.credUtils = new Utils();
		this.passwordHash = credUtils.hashPassword( password );
	}

	/**
	 * accessor method
	 * @return String : id
	 */
	public String getId() {
		return id;
	}

	/**
	 * accessor method
	 * @return String : login
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * accessor method
	 * @return String : passwordHash
	 */
	public String getPasswordHash() 
	{
		return passwordHash;
	}

	/**
	 * mutator method
	 * @param  id to set
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * mutator method
	 * @param login the login to set
	 */
	public void setLogin( String login ) 
	{
		this.login = login;
	}

	/**
	 * mutator method
	 * takes password, converts it to password hash and set it to passwordHash attribute
	 * @param password to be hashed and set
	 */
	public void setPasswordHash( String password )
	{
		this.passwordHash = credUtils.hashPassword( password );
	}


}
