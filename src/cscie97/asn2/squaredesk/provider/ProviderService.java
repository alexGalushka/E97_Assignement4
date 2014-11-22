package cscie97.asn2.squaredesk.provider;

import java.util.List;

import cscie97.asn3.squaredesk.renter.Observer;
import cscie97.asn4.squaredesk.authentication.AccessNotAllowedException;
import cscie97.asn4.squaredesk.authentication.AccessToken;
import cscie97.common.squaredesk.AccessException;
import cscie97.common.squaredesk.Profile;
import cscie97.common.squaredesk.ProfileAlreadyExistsException;
import cscie97.common.squaredesk.ProfileNotFoundException;
import cscie97.common.squaredesk.Rating;
import cscie97.common.squaredesk.RatingAlreadyExistsException;
import cscie97.common.squaredesk.RatingNotFoundExcepion;



/**
 * The Class OfficeProviderService.
 */
public interface ProviderService
{
	public String createProvider ( AccessToken accToken, Profile  profile ) throws ProfileAlreadyExistsException, AccessNotAllowedException;
	
	/**
	 * Returns provider per passed in providerId,
	 * if there is no match – throws ProfileNotFoundException .
	 * @param providerId the provider id
	 * @return profile, the provider
	 * @throws ProfileNotFoundException the provider not found exception
	 */
	public Profile getProvider( String providerId ) throws ProfileNotFoundException;
	
	/**
	 * Returns whole list of providers.
	 * @return List of OfficeProvider
	 */
	public List<Profile> getProviderList ();
	
	/**
	 * Updates the provider, new Provider instance has to be passed in.
	 * If providerId not found, throws ProfileNotFoundException.
	 * @param accToken - access token
	 * @param provider - provider
	 * @throws ProfileNotFoundException the provider not found exception
	 * @throws AccessNotAllowedException if access is not allowed
	 */
	public void updateProvider ( AccessToken accToken, Profile provider ) throws ProfileNotFoundException, AccessNotAllowedException;
	
	/**
	 * Deleted the provider.
	 * If providerId not found, throws ProfileNotFoundException.
	 * @param accToken - access token
	 * @param providerId the provider id
	 * @throws ProfileNotFoundException the provider not found exception
	 * @throws OfficeSpaceNotFoundException  if office space is not found
	 * @throws AccessNotAllowedException if access is not allowed
	 */
	public void deleteProvider ( AccessToken accToken, String providerId ) throws ProfileNotFoundException, OfficeSpaceNotFoundException, AccessNotAllowedException;
	
	/**
	 * Rate the provider. Rating is an integer from 0 to 5. The rating value is added to officeProviderRatingsMap.
	 * if it is found throw RatingAlreadyExistsException. ProviderId is checked as well if it's not found
	 *  - ProfileNotFoundException is thrown 
	 * @param providerId the provider id
	 * @param renterId the renter id
	 * @param rating the rating
	 * @throws RatingAlreadyExistsException the rating already exists exception
	 * @throws ProfileNotFoundException the provider not found exception
	 */
	public void rateProvider ( String providerId,
			                   String renterId , Rating rating ) throws RatingAlreadyExistsException, ProfileNotFoundException;
	
	/**
	 * The Rating correspondent to renterId is to be removed from officeProviderRatingMap within the officeSpaceMap,
	 * if office space id is not found - ProfileNotFoundException is thrown;
	 * if renterId is not found - RatingNotFoundExcepion is thrown.
	 * @param providerId the provider id
	 * @param renterId the renter id
	 * @throws RatingNotFoundExcepion the rating not found excepion
	 * @throws ProfileNotFoundException the provider not found exception
	 */
	public void removeProviderRating ( String providerId,
			                           String renterId) throws RatingNotFoundExcepion, ProfileNotFoundException;
	
	/**
	 * Gets the rating list.
	 * @param providerId the provider id
	 * @return rating list
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 * @throws ProfileNotFoundException if profile is not found
	 */
	public List<Rating> getRatingList ( String providerId ) throws OfficeSpaceNotFoundException, ProfileNotFoundException;
	
	/**
	 * Creates a new OfficeSpace: add office space to officeSpaceMap; check if it exists already
	 * and throws OfficeSpaceAlreadyExistException if it is. If authentication fails - throw AccessException.
	 * Rating Field is initialized here.
	 * Note: officeSpaceId has to be generated first! Check for officeSpaceIds and providerId in the id buckets,
	 * if this check fails throw the 
	 * @param accToken - access token
	 * @param officeSpace the office space
	 * @param providerId - provider id
	 * @throws OfficeSpaceAlreadyExistException the office space already exist exception
	 * @throws AccessException the access exception
	 * @throws AccessNotAllowedException 
	 */ 
	public void createOfficeSpace ( AccessToken accToken, OfficeSpace officeSpace, String providerId )
			                        throws OfficeSpaceAlreadyExistException, AccessException, AccessNotAllowedException;
	
	/**
	 * accessor method for officeSpaceMap attribute
	 * if the guid not found in the map, it throws OfficeSpaceNotFoundException exception.
	 * @param guid the guid
	 * @return OfficeSpace
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 */
	public OfficeSpace getOfficeSpace ( String guid ) throws OfficeSpaceNotFoundException;
	
	/**
	 * Gets the office space list.
	 * @return all values (office spaces) from officeSpaceMap
	 */
	public List<OfficeSpace> getOfficeSpaceList ();
	
	/**
	 * Gets the office space guid list.
	 * @return List of strings
	 */
	public List<String> getOfficeSpaceGuidList ();
	
	/**
	 * updates particular office space in the office space map with a new office space based on guid passed in
	 * if the guid not found in the map, it throws OfficeSpaceNotFoundException exception.
	 * @param accToken - access token
	 * @param updatedOffice the updated office
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 * @throws AccessNotAllowedException if access is not allowed
	 */
	public void updateOfficeSpace ( AccessToken accToken, String providerId,
			                        OfficeSpace updatedOffice) throws OfficeSpaceNotFoundException, AccessNotAllowedException;
	
	/**
	 * removed particular office space from the office space map based on guid passed in
	 * if the guid not found in the map, it throws OfficeSpaceNotFoundException exception.
	 * @param accToken - access token 
	 * @param providerId - provider id
	 * @param officeSpaceId - office space id
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 * @throws AccessNotAllowedException if access is not allowed
	 */
	public void removeOfficeSpace ( AccessToken accToken, String providerId,
                                    String officeSpaceId ) throws OfficeSpaceNotFoundException, AccessNotAllowedException;	
	
	/**
	 * The new Rating is added to officeSpaceRatingMap within the officeSpaceMap,
	 * if the office space id is not found - OfficeSpaceNotFoundException is thrown;
	 * if the rater already provided his/her rating - RatingAlreadyExistsException is thrown.
	 *
	 * @param authToken - the auth token
	 * @param renterId - the renter id
	 * @param officeSpaceId - the office space id
	 * @param rating - the rating
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 * @throws RatingAlreadyExistsException the rating already exists exception
	 */
	public void rateOfficeSpace ( String authToken, String renterId,
			                      String officeSpaceId, Rating rating ) throws OfficeSpaceNotFoundException, RatingAlreadyExistsException;
	
	/**
	 * The Rating correspondent to renterId is to be removed from officeSpaceRatingMap within the officeSpaceMap,
	 * if office space id is not found - OfficeSpaceNotFoundException is thrown;
	 * if renterId is not found - RatingNotFoundExcepion is thrown.
	 *
	 * @param authToken the auth token
	 * @param renterId the renter id
	 * @param officeSpaceId the office space id
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 * @throws RatingNotFoundExcepion the rating not found excepion
	 */
	public void removeOfficeSpaceRating ( String authToken, String renterId,
                                          String officeSpaceId ) throws OfficeSpaceNotFoundException, RatingNotFoundExcepion;
	
	
	/**
	 * Returns the list all Ratings correspondent to office space the officeSpaceMap,
	 * if the office space id is not found - OfficeSpaceNotFoundException is thrown.
	 *
	 * @param authToken - the auth token
	 * @param officeSpaceId the office space id
	 * @return list of ratings
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 */
	public List<Rating> getOfficeSpaceRatingList  ( String authToken, String officeSpaceId ) throws OfficeSpaceNotFoundException;
    
	// artifacts required for Subject for Observer pattern
    /**
     * Observer's pattern method to add Observer to the list of Observers
     * observer - Observer
     */
	public void registerObserver(Observer observer);

    /**
     * Observer's pattern method to remove Observer from the list of Observers
     * observer - Observer
     */
	public void removeObserver(Observer observer);

    /**
     * Observer's pattern method to notify all the registered observers
     */
	public void notifyObservers();

	
}