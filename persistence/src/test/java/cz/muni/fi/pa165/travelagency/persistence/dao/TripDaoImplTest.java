package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.config.InMemorySpring;
import cz.muni.fi.pa165.travelagency.persistence.dao.TripDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Testing DAO implementation of Trip 
 * @author Katerina Caletkova
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemorySpring.class)
@Transactional
public class TripDaoImplTest {
    
    @Inject
    private TripDao tripDao;
    
    @PersistenceContext
    private EntityManager em;
    
    private Trip trip1;
    private Trip trip2;
    private Excursion excursion1;
    
    @Before
    public void setup() {
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,9,20);
        Date dateFrom1 = calendar.getTime();
        calendar.set(2017,9,28);
        Date dateTo1 = calendar.getTime();
        
        trip1 = new Trip("Podzim ve Francii",dateFrom1, dateTo1, "Francie", 20, BigDecimal.valueOf(5000));           
        calendar.set(0,0,0,5,0,0);
        Date excursionDuration = calendar.getTime();
        excursion1 = new Excursion("Vylet do Parize",dateFrom1, excursionDuration, "Pariz",BigDecimal.valueOf(500));
        em.persist(excursion1);
        calendar.set(2017,5,12);
        Date dateFrom2 = calendar.getTime();
        calendar.set(2017,5,26);
        Date dateTo2 = calendar.getTime();
        trip2 = new Trip("Odpocinek na Malte",dateFrom2, dateTo2, "Malta", 55, BigDecimal.valueOf(12000));       
        trip2.addExcursion(excursion1);
        
    }
    
    @Test
    public void testCreate() throws Exception {        
        assertTrue(tripDao.findAll().isEmpty());
        tripDao.create(trip1);        
        assertNotNull(trip1.getId());
        assertEquals(tripDao.findAll().size(), 1);
        tripDao.create(trip2);
        assertEquals(tripDao.findAll().size(), 2);
    }

    @Test
    public void testUpdate() throws Exception {
        tripDao.create(trip1);
        assertEquals(trip1.getDestination(), "Francie");
        trip1.setDestination("Italie");
        tripDao.update(trip1);
         assertEquals(trip1.getDestination(), "Italie");
    }

    @Test
    public void testDelete() throws Exception {
        tripDao.create(trip1);       
        assertFalse(tripDao.findAll().isEmpty());
        tripDao.delete(trip1);       
        assertTrue(tripDao.findAll().isEmpty());
    }

    @Test
    public void testFindById() throws Exception {
        tripDao.create(trip1);        
        assertEquals(tripDao.findById(trip1.getId()),trip1);
    }

    @Test
    public void testFindAll() throws Exception {
       assertTrue(tripDao.findAll().isEmpty());
       tripDao.create(trip1);      
       assertEquals(tripDao.findAll().size(), 1);
       tripDao.create(trip2);
       assertEquals(tripDao.findAll().size(), 2);
    }
    
    @Test
    public void testFindByName() throws Exception {
        tripDao.create(trip1);
        assertEquals(tripDao.findByName(trip1.getName()).get(0),trip1);
    }
    
    @Test
    public void testFindByDate() throws Exception {
        tripDao.create(trip1);       
     
        assertEquals(tripDao.findByDate(trip1.getDateFrom(),trip1.getDateTo()).get(0),trip1);
    }
    
    @Test
    public void testFindByDestination() throws Exception {
        tripDao.create(trip1);
        assertEquals(tripDao.findByDestination(trip1.getDestination()).get(0),trip1);
    }
    
    @Test
    public void testFindByPrice() throws Exception {
        tripDao.create(trip1);
        // TODO dodelat
        assertEquals(tripDao.findByPrice(trip1.getPrice().subtract(BigDecimal.valueOf(10)),trip1.getPrice().add(BigDecimal.valueOf(10))).get(0),trip1);
    }
    
    @Test
    public void testFindByCapacity() throws Exception {
        tripDao.create(trip1);
        assertEquals(tripDao.findByCapacity(trip1.getCapacity()).get(0),trip1);
    }
    
    @Test
    public void testFindByExcursion() throws Exception {
        // TODO dodelat
        tripDao.create(trip2);
        //assertEquals(tripDao.findByExcursion(excursion1).get(0),trip2);
    } 
    @Test
    public void nullAttributes() {
        //TODO
    }
    
}