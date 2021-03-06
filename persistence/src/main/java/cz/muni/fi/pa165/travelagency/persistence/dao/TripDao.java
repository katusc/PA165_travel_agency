package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * Trip interface
 * 
 * @author Jakub Kremláček
 */
public interface TripDao extends GenericDao<Trip, Long>{
    /**
     * 
     * Method for find all trips with specific name
     * 
     * @param name of trips to be looked up
     * @return trips with given name as List
     */
    List<Trip> findByName(String name);
	
    /**
     * 
     * Method for find all trips within specified date range
     * 
     * @param from start of required date range 
     * @param to end of required date range
     * @return trips within range as List
     */
    List<Trip> findByDate(Date from, Date to);
	
    /**
     * 
     * Method for find all trips in specific destination
     * 
     * @param destination of trips to be looked up
     * @return trips in destination as List
     */
    List<Trip> findByDestination(String destination);
	
    /**
     * 
     * Method for find all trips in specific price range
     * 
     * @param minPrice minimal price of trip
     * @param maxPrice maximal price of trip
     * @return trips within price range as List
     */
    List<Trip> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
	
    /**
     * 
     * Method for find all trips with enough capacity
     * 
     * @param capacity free capacity of trip
     * @return trips with enough capacity as List
     */
    List<Trip> findByTotalCapacity(Integer capacity);
	
	
    /**
     * 
     * Method for find all trips with excursion
     * 
     * @param excursion to find
     * @return trips with excursion as List
     */
    List<Trip> findByExcursion(Excursion excursion);
    
}
