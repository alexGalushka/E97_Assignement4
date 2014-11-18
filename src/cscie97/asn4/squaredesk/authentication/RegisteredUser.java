package cscie97.asn4.squaredesk.authentication;

import java.util.LinkedList;
import java.util.List;

public class RegisteredUser 
{
	private String id;
	private String name;

	List<Entitlement> entiList;
	AccessToken accToken;
	UserNamePassword creds;
	
	public RegisteredUser()
	{
		this.id = "";
		this.name = "";
		entiList = new LinkedList<Entitlement>();
		accToken  = null;
		creds = null;
	}
	
	

	public RegisteredUser( String id, String name, String description,
			               UserNamePassword creds )
	{
		this.id = id;
		this.name = name;
		this.entiList = new LinkedList<Entitlement>();
		this.accToken = null;
		this.creds = creds;
	}
	
	/**
	 * adds entitlement to the list of entitlement
	 * Admin restricted method
	 * @param AccessToken : accToken
	 * @param Permission : permList
	 */
	public void addEntitlement( AccessToken accToken, Entitlement ent )
	{
		// authImpl.validateAccess( accToken, <entitlement string> ).
		if ( !entiList.contains( ent ) )
		{
			entiList.add( ent );
		}
	}
	
	/**
	 * removes entitlement from the list of entitlement
	 * Admin restricted method
	 * @param AccessToken : accToken
	 * @param Permission : permList
	 */
	public void removeEntitlement( AccessToken accToken, Entitlement ent)
	{
		// authImpl.validateAccess( accToken, <entitlement string> ).
		if ( entiList.contains( ent ) )
		{
			entiList.remove( ent );
		}
	}
	
	
	/**
	 * accessor method
	 * @return AccessToken : accToken
	 */
	public AccessToken getAccToken() 
	{
		return accToken;
	}

	/**
	 * accessor method
	 * @return UserNamePassword : creds
	 */
	public UserNamePassword getCreds() 
	{
		return creds;
	}

	/**
	 * mutator method
	 * @param accToken to set
	 */
	public void setAccToken(AccessToken accToken)
	{
		this.accToken = accToken;
	}

	/**
	 * mutator method
	 * @param  creds to set
	 */
	public void setCreds(UserNamePassword creds)
	{
		this.creds = creds;
	}
	
	/**
	 * accessor method
	 * @return List<Entitlement> : entiList
	 */
	public List<Entitlement> getListOfEntitlements ()
	{
		return entiList;
	}
	
	/**
	 * accessor method
	 * @return String : id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * accessor method
	 * @return String : name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * mutator method
	 * @param String : id; service's id to be set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * mutator method
	 * @param String : name; service's name to be set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
}
