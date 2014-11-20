package cscie97.asn3.squaredesk.renter;

import java.util.List;
import java.util.Map;

import cscie97.asn2.squaredesk.provider.OfficeSpace;
import cscie97.asn2.squaredesk.provider.OfficeSpaceNotFoundException;
import cscie97.asn4.squaredesk.authentication.AccessNotAllowedException;
import cscie97.asn4.squaredesk.authentication.AccessToken;
import cscie97.common.squaredesk.Profile;
import cscie97.common.squaredesk.ProfileAlreadyExistsException;
import cscie97.common.squaredesk.ProfileNotFoundException;
import cscie97.common.squaredesk.Rate;
import cscie97.common.squaredesk.Rating;
import cscie97.common.squaredesk.RatingAlreadyExistsException;
import cscie97.common.squaredesk.RatingNotFoundExcepion;
import cscie97.common.squaredesk.User;

public interface RenterService
{

    /**
     * this method books the OfficeSpace based on the returned search of the criteria renter has 
     * @param authToken
     * @param uutRenter
     * @throws BookingException 
     * @throws AccessNotAllowedException 
     */
    public Boolean bookOfficeSpace ( AccessToken accToken, Profile uutRenter, Rate rate, PaymentStatus paymentStatus ) throws BookingException, AccessNotAllowedException;
	
    /**
     * wrapper method for the search method of Search Engine class, return list of office spaces based on the search criteria
     * @param accToken
     * @param uutRenter
     * @return List<OfficeSpace> : officeSpacesList
     * @throws AccessNotAllowedException 
     */
    public List<OfficeSpace> searchOfficeSpace ( AccessToken accToken, Profile uutRenter ) throws AccessNotAllowedException;
    
	/**
	 * accessor method
	 * @return the renterMap
	 */
	public Map<String, User> getRenterMap();


	/**
	 * mutator method
	 * @param renterMap the renterMap to set
	 */
	public void setRenterMap(Map<String, User> renterMap);
	
	/**
	 * creates new Renter
	 * @param AccessToken accToken
	 * @param profile
	 * @return
	 * @throws ProfileAlreadyExistsException
	 * @throws AccessNotAllowedException 
	 */
	public String createRenter ( AccessToken accToken, Profile  profile ) throws ProfileAlreadyExistsException, AccessNotAllowedException;
	
	/**
	 * returns renter Profile
	 * @param renterId
	 * @return
	 * @throws ProfileNotFoundException
	 */
	public Profile getRenter( String renterId ) throws ProfileNotFoundException;
	
	/**
	 * Returns whole list of renters.
	 * @return List<Renter>
	 */
	public List<Profile> getRenterList ();
	
	/**
	 * Updates the renter, new renter instance has to be passed in.
	 * If renterId not found, throws ProfileNotFoundException.
	 * @param AccessToken accToken
	 * @param renterId the renter id
	 * @param renter the renter
	 * @throws ProfileNotFoundException the renter not found exception
	 * @throws AccessNotAllowedException 
	 */
	public void updateRenter ( AccessToken accToken, Profile renter ) throws ProfileNotFoundException, AccessNotAllowedException;
	
	/**
	 * Deleted the renter
	 * If renterId not found, throws ProfileNotFoundException.
	 * @param AccessToken accToken
	 * @param renterId the renter id
	 * @throws ProfileNotFoundException the renter not found exception
	 * @throws AccessNotAllowedException 
	 * @throws OfficeSpaceNotFoundException 
	 */
	public void deleteRenter ( AccessToken accToken, String renterId ) throws ProfileNotFoundException, AccessNotAllowedException;
	
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
			                 String providerId , Rating rating ) throws RatingAlreadyExistsException, ProfileNotFoundException;

	
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
			                           String providerId) throws RatingNotFoundExcepion, ProfileNotFoundException;
	
	/**
	 * Gets the rating list.
	 * @param renterId the renter id
	 * @return the rating list
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 * @throws ProfileNotFoundException 
	 */
	public List<Rating> getRatingList ( String renterId ) throws OfficeSpaceNotFoundException, ProfileNotFoundException;
	
}
