package cscie97.asn4.squaredesk.authentication;

import java.util.Calendar;
import java.util.Date;

import cscie97.common.squaredesk.GuidGenerator;

public class AccessToken
{
	private String id;
	// set to TRUE upon token creation, setting it to FALSE invalidates token
	private Boolean status; 
	private Date startingTime; 
	
	private String userId; // added for optimization and flexibility
	
	//later on
	//Date now = Calendar.getInstance().getTime();
	//long timeElapsed = now.getTime() - startingTime.getTime();
	
	public AccessToken ()
	{
		this.id = GuidGenerator.getInstance().generateProviderGuid();
		this.status = true;
		startingTime = Calendar.getInstance().getTime();
	}

	
	/**
	 * accessor method
	 * @return String : id
	 */
	public String getTokenId() 
	{
		return id;
	}
	
	/**
	 * accessor method
	 * @return Date : startingTime
	 */
	public Date getStartingTime() 
	{
		return startingTime;
	}
	
	/**
	 * accessor method
	 * @return Boolean : status
	 */
	public Boolean getStatus() 
	{
		return status;
	}

	/**
	 * mutator method
	 * @param status to set
	 */
	public void setStatus(Boolean status)
	{
		this.status = status;
	}

	/**
	 * accessor method
	 * @return the userId
	 */
	public String getUserId() 
	{
		return userId;
	}

	/**
	 * mutator method
	 * @param userId the userId to set
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
		
}
