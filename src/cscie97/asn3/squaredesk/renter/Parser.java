package cscie97.asn3.squaredesk.renter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Parser 
{
	private Scanner scanner;
	private Map<String,List<String[]>> csvInputMap;
	private List<String[]> defineServiceList;
	private List<String[]> definePermiList;
	private List<String[]> defineRoleList;
	private List<String[]> addEntitlementToRoleList;
	private List<String[]> createUserList;
	private List<String[]> addCredentialList;
	private List<String[]> addRoleToUserList;
	
	
	private String offEntry;
	
	public Parser( String fileName ) throws FileNotFoundException
	{
		scanner = new Scanner( new File( fileName ) ); //"C:/Users/apgalush/Documents/Personal/Harvard/Falls2014/E97_SoftwareDesign/assign4/authentication2.csv"
		csvInputMap = new HashMap<String,List<String[]>>();
		defineServiceList = new LinkedList<String[]>();
		definePermiList = new LinkedList<String[]>();
		defineRoleList = new LinkedList<String[]>();
		addEntitlementToRoleList = new LinkedList<String[]>(); 
		createUserList  = new LinkedList<String[]>(); 
		addCredentialList = new LinkedList<String[]>();
		addRoleToUserList = new LinkedList<String[]>(); 
		offEntry = "";
	}  
	
	public Map<String,List<String[]>> parse()
	{
		
		scanner.useDelimiter(",");

		while (scanner.hasNext())
		{
			String output = scanner.next();
			if (!output.isEmpty() && !output.equals(null) )
			{
				output = output.trim();
		
				if ( output.equals( "define_service" ) )
				{   
					parserProcessor( "define_service", defineServiceList, 3);
				}
				
				if ( output.equals( "define_permission" ) )
				{   
					parserProcessor( "define_permission", definePermiList, 4);
				}
				
				if ( output.equals( "define_role" ) )
				{   
					parserProcessor( "define_role", defineRoleList, 3);
				}
				
				if ( output.equals( "add_entitlement_to_role" ) )
				{   
					parserProcessor( "add_entitlement_to_role", addEntitlementToRoleList, 2);
				}
				
				if ( output.equals( "create_user" ) )
				{   
					parserProcessor( "create_user", createUserList, 2);
				}
				
				if ( output.equals( "add_credential" ) )
				{   
					parserProcessor( "add_credential", addCredentialList, 3);
				}
				
				if ( output.equals( "add_role_to_user" ) )
				{   
					parserProcessor( "add_role_to_user", addRoleToUserList, 2);
				}
			}
		}
		 
		scanner.close();
		return csvInputMap;
	}
	
	private String[] parseEntries ( int numEntries )
	{
		offEntry = "";
		String parsedArray[] = new String[numEntries];
		String output = "";
		for (int i = 0; i<numEntries; i++)
		{
			parsedArray[i] = "";
			output = scanner.next();
			output = output.trim();
			String[] splitted = output.split("\r\n");
			//scanner weird behavior compensation
			if ( splitted.length == 2 )
			{
				parsedArray[i] = splitted[0];
				offEntry = splitted[1];
			}
			else
			{
				parsedArray[i] = output;
			}
		}
		return parsedArray;
	}
	
	private void parserProcessor ( String mapkey, List<String[]> entyList, int numEntries )
	{
		entyList.add( parseEntries ( numEntries ) );
		while ( offEntry.equals( mapkey ) )
		{
			entyList.add(parseEntries ( numEntries ) );
		}
		csvInputMap.put( mapkey, entyList );
	}

}
