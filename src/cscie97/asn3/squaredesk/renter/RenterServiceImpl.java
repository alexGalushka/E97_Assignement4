package cscie97.asn3.squaredesk.renter;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cscie97.asn2.squaredesk.provider.OfficeSpace;
import cscie97.asn2.squaredesk.provider.OfficeSpaceNotFoundException;
import cscie97.asn4.squaredesk.authentication.AccessNotAllowedException;
import cscie97.asn4.squaredesk.authentication.AccessToken;
import cscie97.asn4.squaredesk.authentication.AuthService;
import cscie97.asn4.squaredesk.authentication.AuthServiceImpl;
import cscie97.common.squaredesk.Profile;
import cscie97.common.squaredesk.ProfileAlreadyExistsException;
import cscie97.common.squaredesk.ProfileNotFoundException;
import cscie97.common.squaredesk.Rate;
import cscie97.common.squaredesk.Rating;
import cscie97.common.squaredesk.RatingAlreadyExistsException;
import cscie97.common.squaredesk.RatingNotFoundExcepion;
import cscie97.common.squaredesk.User;
import cscie97.common.squaredesk.UserBucket;

public class RenterServiceImpl implements RenterService
{
    
	private SearchEngine searchEngine;
	private UserBucket userBucket;
	/** The office renter map. */
	private Map<String, User> renterMap;
	private static RenterServiceImpl _obj;
	private SchedulingService schedService;
	private AuthService authService;
	
    private RenterServiceImpl ()
    {
    	setRenterMap(new HashMap<String, User>());
    	userBucket = UserBucket.getInstance();
    	schedService = SchedulingService.getInstance();
    	searchEngine = new SearchEngine();
    	authService = AuthServiceImpl.getInstance();
    }
    
    /**
     * A special static method to access the single RenterServiceImpl instance
     * @return _obj - type: RenterServiceImpl
     */
    public static RenterServiceImpl getInstance() 
    {
    	//Checking if the instance is null, then it will create new one and return it
        if (_obj == null)  
        //otherwise it will return previous one.
        {
            _obj = new RenterServiceImpl();
        }
        return _obj;
    }


    /**
     * this method books the OfficeSpace based on the returned search of the criteria renter has 
     * @param AccessToken accToken
     * @param uutRenter
     * @throws BookingException 
     * @throws AccessNotAllowedException 
     */
    public Boolean bookOfficeSpace ( AccessToken accToken, Profile uutRenter, Rate rate, PaymentStatus paymentStatus ) throws BookingException, AccessNotAllowedException
    {
		if ( !authService.validateAccess( accToken, "create_booking" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to create_booking at this moment" );
		}
    	boolean result = false;
    	OfficeSpace officespace = null;
    	Criteria criteria = uutRenter.getCriteria();
    	List<OfficeSpace> officeSpacesList;
    	officeSpacesList = searchEngine.SearchForOfficeSpace ( criteria );
        if ( officeSpacesList != null)
        {
        	if ( !officeSpacesList.isEmpty() )
        	{
	        	//pick the first one available
	        	officespace =  officeSpacesList.get(0);
	            // daily period by default
	            Booking booking = new Booking ( uutRenter, officespace, rate,
	     		       uutRenter.getCriteria().getStartDate(), uutRenter.getCriteria().getEndDate(), paymentStatus, "daily" );
	            
	            result = schedService.createBooking( booking );
        	}
        }

        return result;
    }
    
    /**
     * wrapper method for the search method of Search Engine class, return list of office spaces based on the search criteria
     * @param accToken
     * @param uutRenter
     * @return List<OfficeSpace> : officeSpacesList
     * @throws AccessNotAllowedException 
     */
    public List<OfficeSpace> searchOfficeSpace ( AccessToken accToken, Profile uutRenter ) throws AccessNotAllowedException
    {
		if ( !authService.validateAccess( accToken, "search_for_office_space" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to search_for_office_space at this moment" );
		}
    	List<OfficeSpace> officeSpacesList;
    	Criteria criteria = uutRenter.getCriteria();
    	officeSpacesList = searchEngine.SearchForOfficeSpace ( criteria );
    	return officeSpacesList;
    }
    
	/**
	 * accessor method
	 * @return the renterMap
	 */
	public Map<String, User> getRenterMap() 
	{
		return renterMap;
	}


	/**
	 * mutator method
	 * @param renterMap the renterMap to set
	 */
	public void setRenterMap(Map<String, User> renterMap)
	{
		this.renterMap = renterMap;
	}
	
	/**
	 * creates new Renter
	 * @param AccessToken accToken
	 * @param profile
	 * @return
	 * @throws ProfileAlreadyExistsException
	 * @throws AccessNotAllowedException 
	 */
	public String createRenter ( AccessToken accToken, Profile  profile ) throws ProfileAlreadyExistsException, AccessNotAllowedException
	{
		if ( !authService.validateAccess( accToken, "create_renter" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to create_renter at this moment" );
		}
		User user = new User();
		// set renter user with guid passed in from the Registered User's AccessToken
		user.setGuid( accToken.getUserId() );
		String userId = user.getGuid();
		profile.setGuid( userId );
		user.setAccount( profile.getAccount() );
		user.setContact( profile.getContact() );
		user.setPicture( profile.getPicture() );
		user.addProfile( "renter" , profile );
		userBucket.createUser( user );
		return userId;
	}
	
	/**
	 * returns renter Profile
	 * @param renterId
	 * @return
	 * @throws ProfileNotFoundException
	 */
	public Profile getRenter( String renterId ) throws ProfileNotFoundException
	{
		User user = null;
		Profile profile = null;
		user = userBucket.getUser( renterId );
		if ( user == null )
		{
			throw new ProfileNotFoundException ( "no user profile found" );
		}
		else
		{
			profile = user.getProfile( "renter" );
		}
		return profile;
	}
	
	/**
	 * Returns whole list of renters.
	 * @return List<Renter>
	 */
	public List<Profile> getRenterList ()
	{
		List<Profile> result = new LinkedList<Profile>();
		Map<String, User> userBucketMap = userBucket.getUserMap();
		Collection<User> tempSet;
		tempSet = userBucketMap.values();	
		for (User u : tempSet )
		{
			try
			{
				result.add( u.getProfile( "renter" ) );
			} 
			catch (ProfileNotFoundException e)
			{
				// continue through the loop, this exception is not critical here
			}
		}
		
		return result;
	}
	
	/**
	 * Updates the renter, new renter instance has to be passed in.
	 * If renterId not found, throws ProfileNotFoundException.
	 * @param AccessToken accToken
	 * @param renterId the renter id
	 * @param renter the renter
	 * @throws ProfileNotFoundException the renter not found exception
	 * @throws AccessNotAllowedException 
	 */
	public void updateRenter ( AccessToken accToken, Profile renter ) throws ProfileNotFoundException, AccessNotAllowedException
	{
		if ( !authService.validateAccess( accToken, "update_renter" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to update_renter at this moment" );
		}
		User user = null;
		String renterId = renter.getGuid();
		user = userBucket.getUser( renterId );
		if ( user != null )
		{
			user.updateProfile( "renter", renter );
			userBucket.updateUser( renterId, user );
		}
		else
		{
			throw new ProfileNotFoundException();
		}
	}
	
	/**
	 * Deleted the renter
	 * If renterId not found, throws ProfileNotFoundException.
	 * @param AccessToken accToken
	 * @param renterId the renter id
	 * @throws ProfileNotFoundException the renter not found exception
	 * @throws AccessNotAllowedException 
	 * @throws OfficeSpaceNotFoundException 
	 */
	public void deleteRenter ( AccessToken accToken, String renterId ) throws ProfileNotFoundException, AccessNotAllowedException
	{
		if ( !authService.validateAccess( accToken, "delete_renter" ) )
		{
			throw new AccessNotAllowedException( "User with ID "+accToken.getUserId()+" not allowed to delete_renter at this moment" );
		}
		User user = null;
		user = userBucket.getUser( renterId );
		if ( user != null )
		{
			user.updateProfile( "renter", null );
			userBucket.updateUser( renterId, user );
		}
		else
		{
			throw new ProfileNotFoundException();
		}
	}
	
	/**
	 * Rate the renter. Rating is an integer from 0 to 5. The rating value is added to officeRenterRatingsMap.
	 * if it is found throw RatingAlreadyExistsException. renterId is checked as well if it's not found
	 *  - ProfileNotFoundException is thrown 
	 * @param renterId the renter id
	 * @param providerId the provider id
	 * @param rating the rating
	 * @throws RatingAlreadyExistsException the rating already exists exception
	 * @throws ProfileNotFoundException the renter not found exception
	 */
	public void rateRenter ( String renterId,
			                 String providerId , Rating rating ) throws RatingAlreadyExistsException, ProfileNotFoundException 
	{
		if ( userBucket.getUserMap().containsKey( renterId ) )
		{
			User tempUser = userBucket.getUser( renterId );
			Profile tempRenter = tempUser.getProfile("renter");
			Map<String, Rating>  tempRenterRatingMap = tempRenter.getRatingsMap();
			if ( !tempRenterRatingMap.containsKey( providerId ) )
			{
				tempRenterRatingMap.put( providerId, rating );
				tempRenter.setRatingsMap ( tempRenterRatingMap );
				tempUser.updateProfile("renter", tempRenter);
				userBucket.updateUser( renterId, tempUser );
			}
			else
			{
				throw new RatingAlreadyExistsException();
			}

		}
		else
		{
			throw new ProfileNotFoundException();
		}
	}

	
	/**
	 * The Rating correspondent to providerId is to be removed from officeProviderRatingMap within the officeSpaceMap,
	 * if office space id is not found - ProfileNotFoundException is thrown;
	 * if renterId is not found - RatingNotFoundExcepion is thrown.
	 * @param providerId the provider id
	 * @param renterId the renter id
	 * @throws RatingNotFoundExcepion the rating not found excepion
	 * @throws ProfileNotFoundException the provider not found exception
	 */
	public void removeRenterRating (   String renterId,
			                           String providerId) throws RatingNotFoundExcepion, ProfileNotFoundException
	{
		if (userBucket.getUserMap().containsKey( renterId ) )
		{
            User tempUser = userBucket.getUser( renterId );
            Profile tempRenter = tempUser.getProfile("renter");
            Map<String, Rating> tempRenterRatingMap = tempRenter.getRatingsMap();
			if ( tempRenterRatingMap.containsKey( providerId ) )
			{
				tempRenterRatingMap.remove( providerId );
			}
			else
			{
				throw new RatingNotFoundExcepion();
			}
			tempRenter.setRatingsMap ( tempRenterRatingMap );
			tempUser.updateProfile("renter", tempRenter);
			userBucket.updateUser( renterId, tempUser );
		}
		else
		{
			throw new ProfileNotFoundException();
		}
	}
	
	/**
	 * Gets the rating list.
	 * @param renterId the renter id
	 * @return the rating list
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 * @throws ProfileNotFoundException 
	 */
	public List<Rating> getRatingList ( String renterId ) throws OfficeSpaceNotFoundException, ProfileNotFoundException
	{
		if ( userBucket.getUserMap().containsKey( renterId ) )
		{
			User tempUser = userBucket.getUser( renterId );
			List<Rating> tempRenterRatingList;
			tempRenterRatingList = tempUser.getProfile("renter").getAllRatings();
			return tempRenterRatingList;
		}
		else
		{
			throw new OfficeSpaceNotFoundException();
		}
	}


	
}
