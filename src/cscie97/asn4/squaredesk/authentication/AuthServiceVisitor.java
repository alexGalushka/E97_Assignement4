package cscie97.asn4.squaredesk.authentication;

import java.util.List;

/**
 * AuthServiceVisitor Class implements Visitor interface
 * authentication service configuration data collector
 * @author APGalush
 *
 */
public class AuthServiceVisitor implements Visitor
{
	private String inventory;
	
	public AuthServiceVisitor()
	{
		inventory = "\nList of Authenication Service Inventory:\n";
	}
    
	/**
	 * visit method ( intended to visit RegisteredUser instance )
	 * @param RegisteredUser : regUser
	 *
	 */
	public void visit( RegisteredUser regUser )
	{
		inventory += "  <Users>\n";
		inventory += "    "+regUser.getId()+":\n";
		inventory += "    Name: "+regUser.getName()+"\n";
		inventory += "      Credentials:\n";
		
		inventory += "      Credentials:\n";
		inventory += "        login: "+regUser.getCreds().getLogin()+"\n";
		inventory += "        password hash: "+regUser.getCreds().getPasswordHash()+"\n";
		inventory += "      Access Token:\n";
		inventory += "        TokenId: "+regUser.getAccToken().getTokenId()+"\n";
		inventory += "        Time Stamp: "+regUser.getAccToken().getStartingTime()+"\n";
		inventory += "        Status: "+regUser.getAccToken().getStatus()+"\n";
	}
	
	/**
	 * visit method ( intended to visit Role instance )
	 * @param Entitlement : ent
	 *
	 */
	public void visit( Entitlement ent )
	{
		inventory += "  <Roles>\n";
		inventory += "    "+ent.getId()+":\n";
		List<Entitlement> entList = ent.getEntList();
		for ( Entitlement entit : entList )
		{
			inventory += "      "+entit.getId()+"\n";
		}
	}
	
	/**
	 * visit method ( intended to visit Role instance )
	 * @param Entitlement : ent
	 *
	 */
	public void visit( Service service )
	{
		inventory += "  <Services>\n";
		inventory += "    "+service.getId()+":\n";
		List<Permission> permiList = service.getListOfPermissions();
		for ( Permission perm : permiList )
		{
			inventory += "      "+perm.getId()+"\n";
		}
	}


	public void visit( Permission permission ) 
	{

		// doesn't make sense to implement, cause Permissions are accessible through aggregation of composition...
	}

	/**
	 * accessor method, returns formatted inventory string 
	 * @return String : inventory
	 */
	public String getInventoryFromVisitor()
	{
		return inventory;
	}
}
