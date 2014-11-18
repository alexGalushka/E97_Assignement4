package cscie97.asn4.squaredesk.authentication;

public class AuthServiceVisitor implements Visitor
{

	public void visit(AuthServiceImpl authServ) 
	{
		// authServ.
		
	}
    
	public void visit(RegisteredUser regUser)
	{
		regUser.getId();
		regUser.getListOfEntitlements();
		//regUser.g
		
	}
	
	public void visit(Entitlement ent)
	{
		ent.getId();
		// then depends if it's a role or permission (node or leaf)
		
	}
	
	public void visit(Service service)
	{
		service.getId();
		service.getListOfPermissions();
		
	}

	@Override
	public void visit(Permission permission) {
		// TODO Auto-generated method stub
		
	}


}
