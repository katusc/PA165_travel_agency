package cz.muni.fi.pa165.travelagency.sampledata;

import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.ExcursionService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import cz.muni.fi.pa165.travelagency.service.TripService;
import cz.muni.fi.pa165.travelagency.service.UserService;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Loads some sample data to populate the travel agency database.
 * 
 * @author Jakub Kremláček
 */

@Component
@Transactional //transactions are handled on facade layer
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {
    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);
    
    @Autowired
    private ExcursionService excursionService;
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private TripService tripService;
    
    @Autowired
    private UserService userService;
    
    @Override
    @SuppressWarnings("unused")
    public void loadData() {
        
        User admin = user("admin@pa165.com", "password", "Admin Strong", date(1988, Calendar.JANUARY, 1), 8801014444L, 123456789);
        User customerSheep = user("sheep@pa165.com", "password", "Ben Sheep", date(1971, Calendar.JUNE, 20), 7106203333L, 987654321);
        User customerJane = user("jane@pa165.com", "password", "Jane Cute", date(1990, Calendar.NOVEMBER, 16), 9066163333L, 732015789);
        
        log.info("Loaded users.");
        
        Trip austriaTrip = trip("Beautiful Austria", daysAfterNow(21), daysAfterNow(28), "Austria, Alps", 50, BigDecimal.valueOf(6399));
        Trip londonTrip = trip("Gorgeous London", daysAfterNow(14), daysAfterNow(18), "UK, London", 200, BigDecimal.valueOf(14099));
        Trip parisTrip = trip("Day in Paris", daysAfterNow(60), daysAfterNow(61), "France, Paris", 132, BigDecimal.valueOf(2399));
        Trip usaTrip = trip("Endurance through USA", daysAfterNow(7), daysAfterNow(38), "USA", 181, BigDecimal.valueOf(149999));
        
        log.info("Loaded trips.");

        Excursion dachsteinExcursion = excursion("Dachstein tour", daysAfterNow(22), 7, "Austria, Dachstein", BigDecimal.valueOf(900), austriaTrip);
        Excursion viennaExcursion = excursion("Vienna", daysAfterNow(16), 3, "Austria, Vienna", BigDecimal.valueOf(1200), austriaTrip);
        Excursion bigbenExcursion = excursion("Big Ben", daysAfterNow(15), 9, "UK, London, Big Ben", BigDecimal.valueOf(800), londonTrip);
        Excursion eiffelExcursion = excursion("Eiffel tower", daysAfterNowWithHour(60, 10), 10, "France, Paris, Eiffel tower", BigDecimal.valueOf(400), parisTrip);
        Excursion notreDameExcursion = excursion("Notre Dame", daysAfterNowWithHour(60, 7), 15, "France, Paris, Notre Dame", BigDecimal.valueOf(300), parisTrip);
        Excursion franceMuseumExcursion = excursion("National Museum of France", daysAfterNowWithHour(60, 14), 2, "France, Paris, National Museum of France", BigDecimal.valueOf(300), parisTrip);
        
        log.info("Loaded excursions.");
        
        HashSet<Excursion> customer1ExcursionSet = new HashSet<>();
        customer1ExcursionSet.add(eiffelExcursion);
        customer1ExcursionSet.add(notreDameExcursion);
        
        log.info("Prepared excursion sets.");
        
        reservation(customerSheep, new HashSet<>(), usaTrip);
        reservation(customerJane, customer1ExcursionSet, parisTrip);
        
        log.info("Loaded reservations.");
    }
    
    private Excursion excursion(String name, Date date, Integer duration, String destination, BigDecimal price, Trip trip) {
        Excursion e = new Excursion(name, date, duration, destination, price);
        
        e.setTrip(trip);
        trip.addExcursion(e);
        
        excursionService.create(e);
        tripService.update(trip);
        
        return e;
    }
    
    private Reservation reservation(User user, Set<Excursion> excursionSet, Trip trip) {
        Reservation r = new Reservation(user, excursionSet, trip);
        
        trip.addReservation(r);
        user.addReservation(r);
        
        reservationService.create(r);
        tripService.update(trip);
        userService.update(user);
        
        return r;
    }
    
    private Trip trip(String name, Date dateFrom, Date dateTo, String destination, Integer capacity, BigDecimal price) {
        Trip t = new Trip(name, dateFrom, dateTo, destination, capacity, price);
        
        tripService.create(t);
        
        return t;
    }
    
    private User user(String mail, String password, String name, Date birthDate, Long personalNumber, Integer phoneNumber) {
        User u = new User(name, birthDate, personalNumber, mail, phoneNumber);
        
        u.setIsAdmin(password.equals("admin"));
        
        userService.createRegisteredUser(u, password);
        
        return u;
    }

    private static Date daysAfterNow(int days) {
        return Date.from(ZonedDateTime.now().plusDays(days).toInstant());
    }
    
    private static Date date(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        
        return cal.getTime();
    }
    
    private static Date daysAfterNowWithHour(int days, int hour) {
        Calendar cal = Calendar.getInstance();
        
        ZonedDateTime d = ZonedDateTime.now().plusDays(days);
        
        cal.set(Calendar.YEAR, d.getYear());
        cal.set(Calendar.MONTH, d.getMonthValue());
        cal.set(Calendar.DAY_OF_MONTH, d.getDayOfMonth());
        cal.set(Calendar.HOUR_OF_DAY, d.getHour() + hour);
        
        return cal.getTime();
    }
}