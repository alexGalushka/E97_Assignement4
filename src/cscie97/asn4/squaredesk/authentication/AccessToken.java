package cscie97.asn4.squaredesk.authentication;

import java.util.Calendar;
import java.util.Date;

import cscie97.common.squaredesk.GuidGenerator;

public class AccessToken
{
	private String id;
	// set to TRUE upon token creation, setting it to FALSE invalidates token
	private Boolean status; 
	private Date timeStamp; 
	
	private String userId; // added for optimization and flexibility
	
	public AccessToken ()
	{
		this.id = GuidGenerator.getInstance().generateProviderGuid();
		this.status = true;
		timeStamp = Calendar.getInstance().getTime();
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
	 * @return Date : timeStamp
	 */
	public Date getTimeStamp() 
	{
		return timeStamp;
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
	 * @param userId to set
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	/**
	 * mutator method
	 * @param timeStamp to set
	 */
	public void setTimeStamp (Date timeStamp)
	{
		this.timeStamp = timeStamp;
	}
}
