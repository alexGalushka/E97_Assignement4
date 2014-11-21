package cscie97.asn4.squaredesk.authentication;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cscie97.common.squaredesk.GuidGenerator;


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
	private Map<String,List<String[]>> authConfigMap;
	private Parser parser;
	private Utils utils;
	private static AuthServiceImpl _obj;
	
	private AuthServiceImpl( String fileName ) throws FileNotFoundException
	{
		registeredUsersMap = new HashMap<String, RegisteredUser>();
		entitlementMap = new HashMap<String, Entitlement>();
		serviceMap = new HashMap<String, Service>(); 
		accessTokenMap = new HashMap<String, AccessToken>(); 
		credsForUserMap = new HashMap<String, UserNamePassword>(); 
		parser = new Parser ( fileName );
		authConfigMap = loadAuthConfig() ;
		utils = new Utils();
	}

    /**
     * A special static method to access the single AuthServiceImpl instance
     * @return _obj - type: AuthServiceImpl
     * @throws FileNotFoundException 
     */
    public static AuthServiceImpl getInstance()
    {
        return _obj;
    }
    
    /**
     * A special static method to access the single AuthServiceImpl instance
     * @return _obj - type: AuthServiceImpl
     * @throws FileNotFoundException 
     */
    public static AuthServiceImpl getInstance( String fileName ) throws FileNotFoundException 
    {
    	//Checking if the instance is null, then it will create new one and return it
        if (_obj == null)  
        //otherwise it will return previous one.
        {
            _obj = new AuthServiceImpl( fileName );
        }
        return _obj;
    }
	
    /**
     * method to load Authentication Service configuration settings
     * @param String : fileName - absolute directory to the .csv config file 
     * @return Map<String, List<String[]>> : authConfigMap
     * @throws FileNotFoundException
     */
    public Map<String, List<String[]>> loadAuthConfig() throws FileNotFoundException
    {
		return authConfigMap = parser.parse();
    }
    
    /**
     * accessor method for the authentication config map
     * @return Map<String,List<String[]>>
     */
    public Map<String,List<String[]>> getAuthConfig()
    {
    	return authConfigMap;
    }
    
	/**
	 * method to create new registered user and put it in registeredUsersMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : login - credentials: square desk login name 
	 * @param String : password - credentials: square desk password 
	 * @param String : name - registered user's name
	 * @param String : userId - unique user's ID
	 * @throws AccessNotAllowedException 
	 * @return RegisteredUser : newRegUser;
	 */
	public RegisteredUser createRegisteredUser( AccessToken accToken, String login , String password, String name, String description, String userId ) throws AccessNotAllowedException
	{

		if ( !validateAccess( accToken, "create_user" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to create_user at this moment" );
		}		
		UserNamePassword credsForUser = new UserNamePassword( userId, login, password); 
		
		RegisteredUser newRegUser = new RegisteredUser( userId, name, description, credsForUser);
		
		credsForUserMap.put( login, credsForUser );
		registeredUsersMap.put( userId, newRegUser );
		return newRegUser;
	}


	/**
	 * method to update registered within the registeredUsersMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @throws AccessNotAllowedException 
	 */
	public void updateRegisteredUser( AccessToken accToken , String id, RegisteredUser regUser ) throws AccessNotAllowedException
	{
		if ( !validateAccess( accToken, "update_user" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to update_user at this moment" );
		}		
		if ( registeredUsersMap.containsKey(id) )
		{
			registeredUsersMap.put( id, regUser );
		}
	}


	/**
	 * method to delete registered user from registeredUsersMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @throws AccessNotAllowedException 
	 */
	public void deleteRegisteredUser( AccessToken accToken , String id ) throws AccessNotAllowedException
	{
		if ( !validateAccess( accToken, "delete_user" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to delete_user at this moment" );
		}
		if ( registeredUsersMap.containsKey( id ) )
		{
			registeredUsersMap.remove( id );
		}
	}

	
	/**
	 * accessor method for the registered user within the registeredUsersMap associated with user's id; method restricted to admin only
	 * @param String : userId - unique user's ID
	 * @return RegisteredUser : regUser 
	 */
	public RegisteredUser getRegisterUser ( String id )
	{
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
	 * @throws AccessNotAllowedException 
	 */
	public void createEntitlement( AccessToken accToken , String userId , Entitlement role , List<Entitlement> servOrRoleList) throws AccessNotAllowedException
	{
		if ( !validateAccess( accToken, "add_entitlement_to_role" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to add_entitlement_to_role at this moment" );
		}
		for ( Entitlement sOrR : servOrRoleList )
		{
			role.add(sOrR);
		}
		
		entitlementMap.put(userId, role);
		RegisteredUser regUser = getRegisterUser( userId );
		updateRegisteredUser( accToken , userId, regUser );
	}

	
	/**
	 * method to update the entry in the entitlementMap with new role; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @param Entitlement : role - new role to be set
	 * @throws AccessNotAllowedException 
	 */
	public void updateEntitlement( AccessToken accToken , String userId , Entitlement role ) throws AccessNotAllowedException
	{
		if ( !validateAccess( accToken, "update_entitlement_of_role" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to update_entitlement_of_role at this moment" );
		}
		if (entitlementMap.containsKey( userId ) )
		{
			entitlementMap.put(userId, role);
		}
	}
	
	
	/**
	 * method to delete the entry in the entitlementMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @throws AccessNotAllowedException 
	 */
	public void deleteEntitlement( AccessToken accToken , String userId ) throws AccessNotAllowedException
	{
		if ( !validateAccess( accToken, "delete_entitlement_from_role" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to delete_entitlement_from_role at this moment" );
		}
		if (entitlementMap.containsKey( userId ) )
		{
			entitlementMap.remove( userId );
		}
	}
	
	
	/**
	 * accessor method for the role within the entitlementMap associated with user's id; method restricted to admin only
	 * @param String : userId - unique user's ID
	 * @return Entitlement : role 
	 */
	public Entitlement getEntitlement( String userId )
	{
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
	 * @throws AccessNotAllowedException 
	 */
	public Service createService ( AccessToken accToken , String id, String name, String description, List<Entitlement> permiList ) throws AccessNotAllowedException
	{
		if ( !validateAccess( accToken, "define_service" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to define_service at this moment" );
		}
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
	 * @throws AccessNotAllowedException 
	 */
	public void updateService ( AccessToken accToken , String id, String name, String description, List<Entitlement> permiList ) throws AccessNotAllowedException
	{
		if ( !validateAccess( accToken, "update_service" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to update_service at this moment" );
		}
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
	 * @throws AccessNotAllowedException 
	 */
	public void deleteService ( AccessToken accToken , String id ) throws AccessNotAllowedException
	{
		if ( !validateAccess( accToken, "delete_service" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to delete_service at this moment" );
		}
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
	public Service getService ( String id )
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
	public void logout( AccessToken accToken, String login ) throws LogoutFailedException
	{
		if ( !credsForUserMap.containsKey(login) )
		{
			String message = "login is incorrect";
			throw new LogoutFailedException( message );
		}

		String accessTokenId = accToken.getTokenId();
		if ( !accessTokenMap.containsKey( accessTokenId ) )
		{
			String message = "access token is invalid";
			throw new LogoutFailedException( message );
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
		// check the token	
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
		
		// check permission part
		String userId = accToken.getUserId();
		Entitlement role = entitlementMap.get( userId );
		//don't care about the role of the roles
		List<Entitlement> entList = role.getEntList(); // could be list of Services or List of Roles
		
		// assume list of Permissions
		result = false;
		for ( Entitlement entEntry : entList )
		{
			if ( entEntry != null)
			{
				if ( entEntry.getId().equals( permissionId ) )
				{	
					result = true;
				}
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
		Service service = null;
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
	

	/**
	 * creates administrator bootstrap account
	 */
	public RegisteredUser createAdmin()
	{
		String[] loginPswd = getCredentials( "authentication_admin_user" );
		String login = loginPswd[0];
		String password = loginPswd[1];

		// generate admin's guid
		String guidAdmin = GuidGenerator.getInstance().generateProviderGuid();
		// set credentials
		UserNamePassword creds = new UserNamePassword( guidAdmin, login, password );
		// generate user with above settings
		RegisteredUser admin = new RegisteredUser( guidAdmin, "Alexander", "Administrator of SquareDesk", creds );
		
		credsForUserMap.put( login, creds );
		registeredUsersMap.put( guidAdmin, admin );

		// create List of entitlements
		List<Entitlement> entList = createListOfPermissions ( "authentication_service" );

		// create Role
		Role role = new Role( "authentication_admin_role", "Authentication Admin Role", "Role for Authentication Administrator" ); 
		
		// entitle the user to actually become an administrator
        for ( Entitlement sOrR : entList )
		{
        	role.add(sOrR);
		}
			
	    entitlementMap.put( guidAdmin, role );
	    admin.addEntitlement( role );
	    
	    return admin;
	}
	
	/**
	 * unrestricted method to create the list of Entitlement out of the auth service config file
	 * @param serviceId
	 * @return
	 */
	public List<Entitlement> createListOfPermissions ( String serviceId )
	{
		List<String[]> entListArray = authConfigMap.get( "define_permission" );
		List<Entitlement> entList = new LinkedList<Entitlement>();
		Entitlement ent = null;
		for  ( String[] entry : entListArray )
		{
			if ( entry[0].equals( serviceId) )
			{
				ent = new Permission(); 
				ent.setId( entry[1] );
				ent.setName( entry[2] );
				ent.setDescription( entry[3] );
				entList.add( ent );
			}
			
		}
		
		return entList;
	}
	
	
	/**
	 * access method for the List of the services formatted as a string array
	 * @return List<String[]> : entListArray
	 */
	public List<String[]> getDefinedServices()
	{	
		List<String[]> entListArray = authConfigMap.get( "define_service" );
		return entListArray;
	}
	
	/**
	 * access method for the List of the credentials formatted as a string array
	 * @return List<String[]> : entListArray
	 */
	public List<String[]> credListArray()
	{	
		List<String[]> credListArray = authConfigMap.get( "add_credential" );
		return credListArray;
	}
	
	/**
	 * retrieves credentials from the Auth Service config
	 * @param userCsvId
	 * @return String[] : result
	 */
	public String[] getCredentials ( String userCsvId )
	{
		String result[] = new String[2];
		result[0] = "";
		result[1] = "";
		List<String[]> credList = credListArray();
		for  ( String[] entry : credList )
		{
			if ( entry[0].equals( userCsvId ) )
			{
				result[0] = entry[1];
				result[1] = entry[2];
			}
		}
		return result;
	}
	
	/**
	 * retrieves defined role from the Auth Service config
	 * @param userCsvRole
	 * @return String[] : result
	 */
	public String[] getDefinedRole ( String userCsvRole )
	{
		String result[] = new String[3];
		result[0] = "";
		result[1] = "";
		result[2] = "";
		List<String[]> credList = rolesListArray();
		for  ( String[] entry : credList )
		{
			if ( entry[0].equals( userCsvRole ) )
			{
				result[0] = entry[0];
				result[1] = entry[1];
				result[2] = entry[2];
			}
		}
		return result;
	}
	
	/**
	 * access method for the List of the roles formatted as a string array
	 * @return List<String[]> : rolesListArray
	 */
	public List<String[]> rolesListArray()
	{	
		List<String[]> rolesListArray = authConfigMap.get( "define_role" );
		return rolesListArray;
	}
}
