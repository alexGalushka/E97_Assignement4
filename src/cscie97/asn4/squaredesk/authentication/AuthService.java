package cscie97.asn4.squaredesk.authentication;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;


public interface AuthService 
{
    
	
	/**
     * method to load Authentication Service configuration settings
	 * @return Map
     * @throws FileNotFoundException if file not found
     */
    public Map<String, List<String[]>> loadAuthConfig() throws FileNotFoundException;
    
    /**
     * accessor method for the authentication config map
     * @return Map
     */
    public Map<String,List<String[]>> getAuthConfig();
    
	/**
	 * method to create new registered user and put it in registeredUsersMap; method restricted to admin only
	 * @param accToken - access token
	 * @param login - user's login
	 * @param password - user's password
	 * @param name - registered user's name
	 * @param description - description of user
	 * @param userId - unique user's ID
	 * @throws AccessNotAllowedException if access not allowed
	 * @return RegisteredUser
	 */
    public RegisteredUser createRegisteredUser( AccessToken accToken, String login , String password, String name, String description, String userId ) throws AccessNotAllowedException;
	
	/**
	 * method to update registered within the registeredUsersMap; method restricted to admin only
	 * @param accToken - token to be validated
	 * @param id - unique user's ID
	 * @param regUser - registered user
	 * @throws AccessNotAllowedException if access is not allowed
	 */
	public void updateRegisteredUser( AccessToken accToken , String id, RegisteredUser regUser ) throws AccessNotAllowedException;
	
	/**
	 * method to delete registered user from registeredUsersMap; method restricted to admin only
	 * @param accToken - token to be validated
	 * @param id - unique user's ID
	 * @throws AccessNotAllowedException if access is not allowed
	 */
	public void deleteRegisteredUser( AccessToken accToken , String id ) throws AccessNotAllowedException;

	/**
	 * accessor method for the registered user within the registeredUsersMap associated with user's id; method restricted to admin only
	 * @param id - unique user's ID
	 * @return regUser is registered user
	 */
	public RegisteredUser getRegisterUser ( String id );
	
	/**
	 * method to create a new entry in the entitlementMap: userId - key, role - value; method restricted to admin only
	 * it adds services or roles to a role and associates a user id with this enriched role
	 * @param accToken - token to be validated
	 * @param userId - unique user's ID
	 * @param role - new role to be set
	 * @param permOrRoleList is List of Entitlements 
	 * @throws AccessNotAllowedException if access is not allowed
	 */
	public void createEntitlement( AccessToken accToken , String userId , Entitlement role , List<Entitlement> permOrRoleList) throws AccessNotAllowedException;

	
	/**
	 * method to update the entry in the entitlementMap with new role; method restricted to admin only
	 * @param accToken - token to be validated
	 * @param userId - unique user's ID
	 * @param role - new role to be set
	 * @throws AccessNotAllowedException  if access is not allowed
	 */
	public void updateEntitlement( AccessToken accToken , String userId , Entitlement role ) throws AccessNotAllowedException;
	
	
	/**
	 * method to delete the entry in the entitlementMap; method restricted to admin only
	 * @param accToken - token to be validated
	 * @param userId - unique user's ID
	 * @throws AccessNotAllowedException if access is not allowed
	 */
	public void deleteEntitlement( AccessToken accToken , String userId ) throws AccessNotAllowedException;
	
	
	/**
	 * accessor method for the role within the entitlementMap associated with user's id; method restricted to admin only
	 * @param userId unique user's ID
	 * @return role is Entitlement
	 */
	public Entitlement getEntitlement( String userId );
	
	/**
	 * creates a new Service and if doesn't exist adds to the serviceMap; method restricted to admin only
	 * @param accToken access token
	 * @param id is string 
	 * @param name is string
	 * @param description is string
	 * @param permiList is List of Permissions 
	 * @return service is jsut a service
	 * @throws AccessNotAllowedException if access is not allowed
	 */
	public Service createService ( AccessToken accToken , String id, String name, String description, List<Entitlement> permiList ) throws AccessNotAllowedException;
	
	/**
	 * updates Service from the serviceMap; method restricted to admin only
	 * @param accToken access token
	 * @param id is string
	 * @param name is string
	 * @param description is string
	 * @param permiList is List of Permissions
	 * @throws AccessNotAllowedException if access is not allowed
	 */
	public void updateService ( AccessToken accToken , String id, String name, String description, List<Entitlement> permiList ) throws AccessNotAllowedException;
	
	/**
	 * deletes Service from the serviceMap; method restricted to admin only
	 * @param accToken access token
	 * @param id id string
	 * @throws AccessNotAllowedException if access is not allowed
	 */
	public void deleteService ( AccessToken accToken , String id ) throws AccessNotAllowedException;
	
	/**
	 * get Service from the serviceMap; method restricted to admin only
	 * @param id id string
	 * @return service just a service
	 */
	public Service getService ( String id );
	
	/**
	 * this method logs in the user with valid credentials and generates accToken for him/her,
	 * this method considered to be the start point of user's session
	 * @param login login string
	 * @param password password string
	 * @return accToken access token
	 * @throws LoginFailedException if login fails
	 */
	public AccessToken login( String login, String password ) throws LoginFailedException;
	
	/**
	 * this method logs out the user by invalidating accToken,
	 * this method considered to be the end point of user's session
	 * @param accToken access token
	 * @param login login string
	 * @throws LogoutFailedException if logout fails
	 */
	public void logout( AccessToken accToken, String login ) throws LogoutFailedException;
	
	/**
	 * method to validate calls made by user to certain API Services' methods
	 * @param accToken access token
	 * @param permissionId permission id string
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
	 * @param serviceId service id 
	 * @return List of Entitlements
	 */
	public List<Entitlement> createListOfPermissions ( String serviceId );
	
	/**
	 * access method for the List of the services formatted as a string array
	 * @return List : entListArray
	 */
	public List<String[]> getDefinedServices();
	
	/**
	 * access method for the List of the credentials formatted as a string array
	 * @return List : entListArray
	 */
	public List<String[]> credListArray();
	
	/**
	 * retrieves credentials from the Auth Service config
	 * @param userCsvId id from csv
	 * @return String[] : result
	 */
	public String[] getCredentials ( String userCsvId );
	
	/**
	 * retrieves defined role from the Auth Service config
	 * @param userCsvRole role from csc file
	 * @return String[] : result
	 */
	public String[] getDefinedRole ( String userCsvRole );
	
	/**
	 * access method for the List of the roles formatted as a string array
	 * @return List : rolesListArray
	 */
	public List<String[]> rolesListArray();
}
