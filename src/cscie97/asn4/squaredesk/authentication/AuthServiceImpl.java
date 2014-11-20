package cscie97.asn4.squaredesk.authentication;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * AuthServiceImpl - square desk authentication service implementation class 
 * @author APGalush
 *
 */
public class AuthServiceImpl implements AuthService
{
	private Map<String, RegisteredUser> registeredUsersMap;
	private Map<String, Entitlement> entitlementMap;
	private Map<String, Service> serviceMap;
	private Map<String, AccessToken> accessTokenMap;
	private Map<String, UserNamePassword> credsForUserMap;
	private Utils utils;
	private static AuthServiceImpl _obj;
	
	private AuthServiceImpl()
	{
		registeredUsersMap = new HashMap<String, RegisteredUser>();
		entitlementMap = new HashMap<String, Entitlement>();
		serviceMap = new HashMap<String, Service>(); 
		accessTokenMap = new HashMap<String, AccessToken>(); 
		credsForUserMap = new HashMap<String, UserNamePassword>(); 
		utils = new Utils();
	}

    /**
     * A special static method to access the single AuthServiceImpl instance
     * @return _obj - type: AuthServiceImpl
     */
    public static AuthServiceImpl getInstance() 
    {
    	//Checking if the instance is null, then it will create new one and return it
        if (_obj == null)  
        //otherwise it will return previous one.
        {
            _obj = new AuthServiceImpl();
        }
        return _obj;
    }
	
	/**
	 * method to create new registered user and put it in registeredUsersMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : login - credentials: square desk login name 
	 * @param String : password - credentials: square desk password 
	 * @param String : name - registered user's name
	 * @param String : userId - unique user's ID
	 */
	public void createRegisteredUser( AccessToken accToken, String login , String password, String name, String userId )
	{
		// check the validity
		
		// create AccesToken for nonadmin user 
				
		String description = "what kind of description";
		
		UserNamePassword credsForUser = new UserNamePassword( userId, login, password); 
		
		RegisteredUser newRegUser = new RegisteredUser( userId, name, description, credsForUser);
		
		credsForUserMap.put( login, credsForUser );
		registeredUsersMap.put( userId, newRegUser );
	}


	/**
	 * method to update registered within the registeredUsersMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 */
	public void updateRegisteredUser( AccessToken accToken , String id, RegisteredUser regUser )
	{
		if ( registeredUsersMap.containsKey(id) )
		{
			registeredUsersMap.put( id, regUser );
		}
	}


	/**
	 * method to delete registered user from registeredUsersMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 */
	public void deleteRegisteredUser( AccessToken accToken , String id )
	{
		// check the validity
		if ( registeredUsersMap.containsKey( id ) )
		{
			registeredUsersMap.remove( id );
		}
	}

	
	/**
	 * accessor method for the registered user within the registeredUsersMap associated with user's id; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @return RegisteredUser : regUser 
	 */
	public RegisteredUser getRegisterUser ( AccessToken accToken , String id )
	{
		// check the validity
		RegisteredUser regUser = null;
		if ( registeredUsersMap.containsKey( id ) )
		{
			regUser = registeredUsersMap.get( id );
		}
		
		return regUser;
	}
		
	
	/**
	 * method to create a new entry in the entitlementMap: userId - key, role - value; method restricted to admin only
	 * it adds services or roles to a role and associates a user id with this enriched role
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @param Entitlement : role - new role to be set
	 * @param List<Entitlement> : servOrRoleList
	 */
	public void createEntitlement( AccessToken accToken , String userId , Entitlement role , List<Entitlement> servOrRoleList)
	{
		// check the validity
		for ( Entitlement sOrR : servOrRoleList )
		{
			role.add(sOrR);
		}
		
		entitlementMap.put(userId, role);
	}

	
	/**
	 * method to update the entry in the entitlementMap with new role; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @param Entitlement : role - new role to be set
	 */
	public void updateEntitlement( AccessToken accToken , String userId , Entitlement role )
	{
		// check the validity
		if (entitlementMap.containsKey( userId ) )
		{
			entitlementMap.put(userId, role);
		}
	}
	
	
	/**
	 * method to delete the entry in the entitlementMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 */
	public void deleteEntitlement( AccessToken accToken , String userId )
	{
		// check the validity
		if (entitlementMap.containsKey( userId ) )
		{
			entitlementMap.remove( userId );
		}
	}
	
	
	/**
	 * accessor method for the role within the entitlementMap associated with user's id; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @return Entitlement : role 
	 */
	public Entitlement getEntitlement( AccessToken accToken , String userId )
	{
		// check the validity
		Entitlement role = null;
		if (entitlementMap.containsKey( userId ) )
		{
			role = entitlementMap.get( userId );
		}
		return role;
	}
	
	/**
	 * creates a new Service and if doesn't exist adds to the serviceMap; method restricted to admin only
	 * @param AccessToken : accToken
	 * @param String : id
	 * @param String : name
	 * @param String : description
	 * @param List<Permission> : permiList
	 * @return Service : service
	 */
	public Service createService ( AccessToken accToken , String id, String name, String description, List<Permission> permiList )
	{
		Service service = null;
		if ( serviceMap.containsKey( id ) )
		{
			service = serviceMap.get( id );
		}
		else
		{
			service = new Service ( id, name, description, permiList );
			serviceMap.put( id, service );
		}
		return service;
	}
	
	/**
	 * updates Service from the serviceMap; method restricted to admin only
	 * @param AccessToken : accToken
	 * @param String : id
	 * @param String : name
	 * @param String : description
	 * @param List<Permission> : permiList
	 */
	public void updateService ( AccessToken accToken , String id, String name, String description, List<Permission> permiList )
	{
		if ( serviceMap.containsKey( id ) )
		{
			Service service = new Service ( id, name, description, permiList );
			service = serviceMap.put( id, service );
		}
	}
	
	/**
	 * deletes Service from the serviceMap; method restricted to admin only
	 * @param AccessToken : accToken
	 * @param String : id
	 */
	public void deleteService ( AccessToken accToken , String id )
	{
		if ( serviceMap.containsKey( id ) )
		{
			serviceMap.remove( id );
		}
	}
	
	/**
	 * get Service from the serviceMap; method restricted to admin only
	 * @param AccessToken : accToken
	 * @param String : id
	 * @return Service : service
	 */
	public Service getService ( AccessToken accToken , String id )
	{
		Service service = null;
		if ( serviceMap.containsKey( id ) )
		{
			service = serviceMap.get( id );
		}
		return service;
	}
	
	/**
	 * this method logs in the user with valid credentials and generates accToken for him/her,
	 * this method considered to be the start point of user's session
	 * @param String : login
	 * @param String : password
	 * @return AccessToken : accToken
	 * @throws LoginFailedException
	 */
	public AccessToken login( String login, String password ) throws LoginFailedException
	{
		UserNamePassword creds = null;
		
		if ( !credsForUserMap.containsKey(login) )
		{
			String message = "login is incorrect";
			throw new LoginFailedException( message );
		}
		
		// get the user's credentials from the map based on user's login
		creds = credsForUserMap.get( login );
		
		if ( !creds.getPasswordHash().equals( utils.hashPassword( password ) ) )
		{	
			String message = "password is incorrect";
			throw new LoginFailedException( message );
		}
		// create new access token
		AccessToken accToken = new AccessToken();
		// associate UserId with AccessToken for convenience
		String userId = creds.getId();
		accToken.setUserId( userId );
		
		accessTokenMap.put( accToken.getTokenId() , accToken ); // key is the AccessToken Id
		
		return accToken;
	}
	
	/**
	 * this method logs out the user by invalidating accToken,
	 * this method considered to be the end point of user's session
	 * @param AccessToken : accToken
	 * @param String : login
	 * @throws LoginFailedException
	 */
	public void logout( AccessToken accToken, String login ) throws LoginFailedException
	{
		if ( !credsForUserMap.containsKey(login) )
		{
			String message = "login is incorrect";
			throw new LoginFailedException( message );
		}

		String accessTokenId = accToken.getTokenId();
		if ( !accessTokenMap.containsKey( accessTokenId ) )
		{
			String message = "access token is invalid";
			throw new LoginFailedException( message );
		}
		
		accessTokenMap.remove( accessTokenId ); //invalidate token
	}
	
	/**
	 * method to validate calls made by user to certain API Services' methods
	 * @param AccessToken : accToken
	 * @param String : permissionId
	 * @return true if validation is successful, false if opposite
	 */
	public Boolean validateAccess( AccessToken accToken, String permissionId )
	{
		Boolean result = true;
			
		Date accTokenStartingTime = accToken.getStartingTime();
		Date timeNow = Calendar.getInstance().getTime();
		long timeElapsed = timeNow.getTime() - accTokenStartingTime.getTime();
		
		// if > 30 min - invalidate the token, set token status to FALSE and make validateAccess return FALSE
		if ( timeElapsed > 30*60*1000 )
		{
			result = false;
			accToken.setStatus( result );
			return result;
		}
		else
		{
			accToken.setStartingTime( timeNow );
		}
		
		String accTokenId = accToken.getTokenId();
		Boolean accTokenStatus = accToken.getStatus();
		if ( !accessTokenMap.containsKey( accTokenId ) || accTokenStatus.equals( false ) )
		{
			result = false;
			return result;
		}
		
		// switch to permission part
		String userId = accToken.getUserId();
		Entitlement role = entitlementMap.get( userId );
		//don't care about the role of the roles
		List<Entitlement> entList = role.getEntList(); // could be list of Services or List of Roles
		
		// assume list of Services
		result = false;
		for ( Entitlement entEntry : entList )
		{
			if ( entEntry.getId().equals( permissionId ) )
			{	
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * provides the configuration data of the Authentication Service instance
	 * @return String : result
	 */
	public String getInventory()
	{
		String result = "";
		
		AuthServiceVisitor visitor = new AuthServiceVisitor();
		
		//private Map<String, RegisteredUser> registeredUsersMap;
		
		Iterator<Entry<String, Service>> entriesServices = serviceMap.entrySet().iterator();
		Service service;
		while (entriesServices.hasNext()) 
		{
	        Entry<String, Service> thisEntry = entriesServices.next();
	        service = thisEntry.getValue();
			if ( service != null)
		    {
				service.acceptVisitor( visitor );
			}
			
		}
		

		Iterator<Entry<String, Entitlement>> entriesEnt = entitlementMap.entrySet().iterator();
		Entitlement ent;
		while (entriesEnt.hasNext()) 
		{
	        Entry<String, Entitlement> thisEntry = entriesEnt.next();
	        ent = thisEntry.getValue();
			if ( ent != null)
		    {
				ent.acceptVisitor( visitor );
			}
			
		}
		
		Iterator<Entry<String,RegisteredUser>> entriesRegUser = registeredUsersMap.entrySet().iterator();
		RegisteredUser regUser;
		while (entriesRegUser.hasNext()) 
		{
	        Entry<String, RegisteredUser> thisEntry = entriesRegUser.next();
			regUser = thisEntry.getValue();
			if ( regUser != null)
		    {
			    regUser.acceptVisitor( visitor );
			}
			
		}
		
		result = visitor.getInventoryFromVisitor();
		return result;
	}
}
