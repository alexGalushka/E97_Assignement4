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
		inventory += "  <User>\n";
		inventory += "    User ID: "+regUser.getId()+"\n";
		inventory += "    Name: "+regUser.getName()+"\n";
		inventory += "    Credentials:\n";
		inventory += "      login: "+regUser.getCreds().getLogin()+"\n";
		inventory += "      password hash: "+regUser.getCreds().getPasswordHash()+"\n";
		inventory += "    Access Token:\n";
		if ( regUser.getAccToken() != null )
		{
			inventory += "      TokenId: "+regUser.getAccToken().getTokenId()+"\n";
			inventory += "      Time Stamp: "+regUser.getAccToken().getStartingTime()+"\n";
			inventory += "      Status: "+regUser.getAccToken().getStatus()+"\n";
		}
	}
	
	/**
	 * visit method ( intended to visit Role instance )
	 * @param Entitlement : ent
	 *
	 */
	public void visit( Entitlement ent )
	{
		inventory += "  <Role>\n";
		inventory += "    "+ent.getId()+":\n";
		List<Entitlement> entList = ent.getEntList();
		for ( Entitlement entit : entList )
		{ 
			if ( entit!= null )
			{
				inventory += "      "+entit.getId()+"\n";
			}
		}
	}
	
	/**
	 * visit method ( intended to visit Role instance )
	 * @param Entitlement : ent
	 *
	 */
	public void visit( Service service )
	{
		inventory += "  <Service>\n";
		inventory += "    "+service.getId()+":\n";
		List<Entitlement> permiList = service.getListOfPermissions();
		for ( Entitlement perm : permiList )
		{
			if ( perm!= null )
			{
			 inventory += "      "+perm.getId()+"\n";
			}
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

	/**
	 * accessor method
	 * @return the inventory
	 */
	public String getInventory()
	{
		return inventory;
	}

	/**
	 * mutator method
	 * @param inventory the inventory to set
	 */
	public void setInventory(String inventory) 
	{
		this.inventory = inventory;
	}
}
