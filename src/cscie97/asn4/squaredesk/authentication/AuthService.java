package cscie97.asn4.squaredesk.authentication;

import java.util.List;

public interface AuthService 
{
	/**
	 * method to create new registered user and put it in registeredUsersMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : login - credentials: square desk login name 
	 * @param String : password - credentials: square desk password 
	 * @param String : name - registered user's name
	 * @param String : userId - unique user's ID
	 */
	public void createRegisteredUser( AccessToken accToken, String login , String password, String name, String id );
	
	/**
	 * method to update registered within the registeredUsersMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 */
	public void updateRegisteredUser( AccessToken accToken , String id, RegisteredUser regUser );
	
	/**
	 * method to delete registered user from registeredUsersMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 */
	public void deleteRegisteredUser( AccessToken accToken , String id );

	/**
	 * accessor method for the registered user within the registeredUsersMap associated with user's id; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @return RegisteredUser : regUser 
	 */
	public RegisteredUser getRegisterUser ( AccessToken accToken , String id );
	
	/**
	 * method to create a new entry in the entitlementMap: userId - key, role - value; method restricted to admin only
	 * it adds services or roles to a role and associates a user id with this enriched role
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @param Entitlement : role - new role to be set
	 * @param List<Entitlement> : servOrRoleList
	 */
	public void createEntitlement( AccessToken accToken , String userId , Entitlement role , List<Entitlement> servOrRoleList);

	
	/**
	 * method to update the entry in the entitlementMap with new role; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @param Entitlement : role - new role to be set
	 */
	public void updateEntitlement( AccessToken accToken , String userId , Entitlement role );
	
	
	/**
	 * method to delete the entry in the entitlementMap; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 */
	public void deleteEntitlement( AccessToken accToken , String userId );
	
	
	/**
	 * accessor method for the role within the entitlementMap associated with user's id; method restricted to admin only
	 * @param AccessToken : accToken - token to be validated
	 * @param String : userId - unique user's ID
	 * @return Entitlement : role 
	 */
	public Entitlement getEntitlement( AccessToken accToken , String userId );
	
	/**
	 * creates a new Service and if doesn't exist adds to the serviceMap; method restricted to admin only
	 * @param AccessToken : accToken
	 * @param String : id
	 * @param String : name
	 * @param String : description
	 * @param List<Permission> : permiList
	 * @return Service : service
	 */
	public Service createService ( AccessToken accToken , String id, String name, String description, List<Permission> permiList );
	
	/**
	 * updates Service from the serviceMap; method restricted to admin only
	 * @param AccessToken : accToken
	 * @param String : id
	 * @param String : name
	 * @param String : description
	 * @param List<Permission> : permiList
	 */
	public void updateService ( AccessToken accToken , String id, String name, String description, List<Permission> permiList );
	
	/**
	 * deletes Service from the serviceMap; method restricted to admin only
	 * @param AccessToken : accToken
	 * @param String : id
	 */
	public void deleteService ( AccessToken accToken , String id );
	
	/**
	 * get Service from the serviceMap; method restricted to admin only
	 * @param AccessToken : accToken
	 * @param String : id
	 * @return Service : service
	 */
	public Service getService ( AccessToken accToken , String id );
	
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
}
