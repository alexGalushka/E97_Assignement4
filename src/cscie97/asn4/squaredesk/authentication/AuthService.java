package cscie97.asn4.squaredesk.authentication;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;


public interface AuthService 
{
    
	
	/**
     * method to load Authentication Service configuration settings
     * @param String : fileName - absolute directory to the .csv config file 
	 * @return 
     * @throws FileNotFoundException
     */
    public Map<String, List<String[]>> loadAuthConfig() throws FileNotFoundException;
    
    /**
     * accessor method for the authentication config map
     * @return Map<String,List<String[]>>
     */
    public Map<String,List<String[]>> getAuthConfig();
    
	/**
	 * method to create new registered user and put it in registeredUsersMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : login - credentials: square desk login name 
	 * @param String : password - credentials: square desk password 
	 * @param String : name - registered user's name
	 * @param String : description - registered user's description
	 * @param String : userId - unique user's ID
	 * @throws AccessNotAllowedException 
	 */
    public void createRegisteredUser( AccessToken accToken, String login , String password, String name, String description, String userId ) throws AccessNotAllowedException;
	
	/**
	 * method to update registered within the registeredUsersMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @throws AccessNotAllowedException 
	 */
	public void updateRegisteredUser( AccessToken accToken , String id, RegisteredUser regUser ) throws AccessNotAllowedException;
	
	/**
	 * method to delete registered user from registeredUsersMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @throws AccessNotAllowedException 
	 */
	public void deleteRegisteredUser( AccessToken accToken , String id ) throws AccessNotAllowedException;

	/**
	 * accessor method for the registered user within the registeredUsersMap associated with user's id; method restricted to admin only
	 * @param String : userId - unique user's ID
	 * @return RegisteredUser : regUser 
	 */
	public RegisteredUser getRegisterUser ( String id );
	
	/**
	 * method to create a new entry in the entitlementMap: userId - key, role - value; method restricted to admin only
	 * it adds services or roles to a role and associates a user id with this enriched role
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @param Entitlement : role - new role to be set
	 * @param List<Entitlement> : servOrRoleList
	 * @throws AccessNotAllowedException 
	 */
	public void createEntitlement( AccessToken accToken , String userId , Entitlement role , List<Entitlement> servOrRoleList) throws AccessNotAllowedException;

	
	/**
	 * method to update the entry in the entitlementMap with new role; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @param Entitlement : role - new role to be set
	 * @throws AccessNotAllowedException 
	 */
	public void updateEntitlement( AccessToken accToken , String userId , Entitlement role ) throws AccessNotAllowedException;
	
	
	/**
	 * method to delete the entry in the entitlementMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @throws AccessNotAllowedException 
	 */
	public void deleteEntitlement( AccessToken accToken , String userId ) throws AccessNotAllowedException;
	
	
	/**
	 * accessor method for the role within the entitlementMap associated with user's id; method restricted to admin only
	 * @param String : userId - unique user's ID
	 * @return Entitlement : role 
	 */
	public Entitlement getEntitlement( String userId );
	
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
	public Service createService ( AccessToken accToken , String id, String name, String description, List<Entitlement> permiList ) throws AccessNotAllowedException;
	
	/**
	 * updates Service from the serviceMap; method restricted to admin only
	 * @param AccessToken : accToken
	 * @param String : id
	 * @param String : name
	 * @param String : description
	 * @param List<Permission> : permiList
	 * @throws AccessNotAllowedException 
	 */
	public void updateService ( AccessToken accToken , String id, String name, String description, List<Entitlement> permiList ) throws AccessNotAllowedException;
	
	/**
	 * deletes Service from the serviceMap; method restricted to admin only
	 * @param AccessToken : accToken
	 * @param String : id
	 * @throws AccessNotAllowedException 
	 */
	public void deleteService ( AccessToken accToken , String id ) throws AccessNotAllowedException;
	
	/**
	 * get Service from the serviceMap; method restricted to admin only
	 * @param String : id
	 * @return Service : service
	 */
	public Service getService ( String id );
	
	/**
	 * this method logs in the user with valid credentials and generates accToken for him/her,
	 * this method considered to be the start point of user's session
	 * @param String : login
	 * @param String : password
	 * @return AccessToken : accToken
	 * @throws LoginFailedException
	 */
	public AccessToken login( String login, String password ) throws LoginFailedException;
	
	/**
	 * this method logs out the user by invalidating accToken,
	 * this method considered to be the end point of user's session
	 * @param AccessToken : accToken
	 * @param String : login
	 * @throws LoginFailedException
	 */
	public void logout( AccessToken accToken, String login ) throws LoginFailedException;
	
	/**
	 * method to validate calls made by user to certain API Services' methods
	 * @param AccessToken : accToken
	 * @param String : permissionId
	 * @return true if validation is successful, false if opposite
	 */
	public Boolean validateAccess( AccessToken accToken, String permissionId );
	
	/**
	 * provides the configuration data of the Authentication Service instance
	 * @return String : result
	 */
	public String getInventory();
	
	/**
	 * creates administrator bootstrap account
	 * @return RegisteredUser : admin
	 */
	public RegisteredUser createAdmin();
	
	/**
	 * unrestricted method to create the list of Entitlement out of the auth service config file
	 * @param serviceId
	 * @return
	 */
	public List<Entitlement> createListOfPermissions ( String serviceId );
	
	/**
	 * access method for the List of the services formatted as a string array
	 * @return List<String[]> : entListArray
	 */
	public List<String[]> getDefinedServices();
}
