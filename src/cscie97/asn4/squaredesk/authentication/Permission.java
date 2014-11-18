package cscie97.asn4.squaredesk.authentication;

public class Permission implements Visitable
{
	private String id;
	private String name;
	private String description;
	
	public Permission()
	{
		this.id = "";
		this.name = "";
		this.description = "";
	}
	
	public Permission(String id, String name, String description)
	{
		this.id = id;
		this.name = name;
		this.description = description;
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
	 * @param String : id; permission's id to be set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * mutator method
	 * @param String : name; permission's name to be set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * mutator method
	 * @param String : description; permission's description to be set
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
