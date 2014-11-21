package cscie97.asn4.squaredesk.authentication;

import java.util.LinkedList;
import java.util.List;

public class Role implements Entitlement
{
	private String id;
	private String name;
	private String description;
	private List<Entitlement> entList;
	
	public Role()
	{
		this.id = "";
		this.name = "";
		this.description = "";
		this.entList = new LinkedList<Entitlement>();
	}
	
	/**
	 * Parameterized constructor
	 * @param id
	 * @param name
	 * @param description
	 */
	public Role(String id, String name, String description)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.entList = new LinkedList<Entitlement>();
	}
	
	/**
	 * adding entitlment to the list
	 */
	public void add( Entitlement entitlement )
	{
		if (!entList.contains(entitlement))
		{
			entList.add(entitlement);
		}
	}
	
	/**
	 * removing entitlment from the list
	 */
	public void remove( Entitlement entitlement )
	{
		if(entList.contains(entitlement))
		{
			entList.remove(entitlement);
		}
	}

	/**
	 * accessor method
	 * @return List<Entitlement> : entList
	 */
	public List<Entitlement> getEntList()
	{
		return entList;
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
	 * accessor method
	 * @return String : description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * mutator method
	 * @param String : id; roles's id to be set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * mutator method
	 * @param String : name; roles' name to be set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * mutator method
	 * @param String : description; role's description to be set
	 */
	public void setDescription(String description) 
	{
		this.description = description;
	}

	/**
	 * support method for Visitor design pattern: accepts Visitor - performs "visit" operation
	 */
	public void acceptVisitor(Visitor v) 
	{
		v.visit(this);	
	}

}
