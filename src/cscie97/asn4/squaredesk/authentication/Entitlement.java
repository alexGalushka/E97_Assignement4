package cscie97.asn4.squaredesk.authentication;

import java.util.List;

/**
 * Interface Entitlement: implemented by Role and Permission classes
 * @author APGalush
 *
 */
public interface Entitlement extends Visitable
{
	public void add( Entitlement entitlement );
	
	public void remove( Entitlement entitlement );
	
	/**
	 * accessor method
	 * @return String : id
	 */
	public String getId();

	/**
	 * accessor method
	 * @return String : name
	 */
	public String getName();

	/**
	 * accessor method
	 * @return String : description
	 */
	public String getDescription();

	/**
	 * mutator method
	 * @param id - roles's id to be set
	 */
	public void setId(String id);

	/**
	 * mutator method
	 * @param name - roles' name to be set
	 */
	public void setName(String name);

	/**
	 * mutator method
	 * @param description - role's description to be set
	 */
	public void setDescription(String description);
	
	/**
	 * accessor method
	 * @return entList - List of Entitlements 
	 */
	public List<Entitlement> getEntList();
}
