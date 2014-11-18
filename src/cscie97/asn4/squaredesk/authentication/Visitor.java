package cscie97.asn4.squaredesk.authentication;

public interface Visitor
{
	public void visit( AuthServiceImpl authServ );
	public void visit( Service service );
	public void visit( Entitlement ent );
	public void visit( RegisteredUser regUser );
	public void visit( Permission permission );
}
