package cscie97.asn4.squaredesk.authentication;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import cscie97.asn3.squaredesk.renter.Parser;

public class TesterClass
{
	public static void main(String[] args) throws IOException
	{

		Parser parser = new Parser ( "C:/Users/apgalush/Documents/Personal/Harvard/Falls2014/E97_SoftwareDesign/assign4/authentication2.csv" );
		
		Map<String,List<String[]>> csvInputMap = parser.parse();
		
		for ( String[] sa : csvInputMap.get("add_credential") )
		{
			System.out.print( sa[0] );
		}
		System.out.print(csvInputMap);
		
	}
	
}
