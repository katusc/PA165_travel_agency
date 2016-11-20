package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.TripDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import org.hibernate.service.spi.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.annotations.BeforeMethod;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


/**
 * Created on 17.11.2016.
 *
 * @author Martin Salata
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class TripServiceTest {

    @Mock
    private TripDao tripDao;

    @InjectMocks
    private TripService tripService = new TripServiceImpl();
    
    private Trip t1;
    private Excursion e1;

    @Before
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        Calendar cal = Calendar.getInstance();
        cal.set(2017, 8, 15);
        Date dFrom = cal.getTime();
        cal.set(2017, 8, 25);
        Date dTo = cal.getTime();

        t1 = new Trip();
        t1.setName("Dovolenka v Egypte");
        t1.setDateFrom(dFrom);
        t1.setDateTo(dTo);
        t1.setDestination("Egypt");
        t1.setCapacity(100);
        t1.setPrice(BigDecimal.valueOf(10000));

        cal.set(2017, 8, 20, 10, 0, 0);
        Date excursionDate = cal.getTime();
        cal.set(0, 0, 0, 5, 0, 0);
        Date excursionDuration = cal.getTime();

        e1 = new Excursion();
        e1.setName("Egypt- pyramidy");
        e1.setDate(excursionDate);
        e1.setDuration(excursionDuration);
        e1.setDestination("Kahira");
        e1.setPrice(BigDecimal.valueOf(800));

        t1.addExcursion(e1);
    }

    @Test
    public void testCreate() throws Exception {
        tripService.createTrip(t1);
        verify(tripDao).create(t1);
    }

    @Test
    public void testGetById() throws Exception {
        tripService.getById(t1.getId());
        verify(tripDao).findById(t1.getId());
    }

    @Test
    public void testGetAll() throws Exception {
        tripService.getAll();
        verify(tripDao).findAll();
    }

    @Test
    public void testFindByName() throws Exception {
        tripService.findByName(t1.getName());
        verify(tripDao).findByName(t1.getName());
    }

    @Test
    public void testFindByDate() throws Exception {
        tripService.findByDate(t1.getDateFrom(), t1.getDateTo());
        verify(tripDao).findByDate(t1.getDateFrom(), t1.getDateTo());
    }

    @Test
    public void testFindByDestination() throws Exception {
        tripService.findByDestination(t1.getDestination());
        verify(tripDao).findByDestination(t1.getDestination());
    }

    @Test
    public void testFindByPrice() throws Exception {
        tripService.findByPrice(t1.getPrice(), t1.getPrice());
        verify(tripDao).findByPrice(t1.getPrice(),t1.getPrice());
    }

    @Test
    public void testFindByCapacity() throws Exception {
        tripService.findByCapacity(t1.getCapacity());
        verify(tripDao).findByCapacity(t1.getCapacity());
    }

    @Test
    public void testFindByExcurxion() throws Exception {
        tripService.findByExcursion(e1);
        verify(tripDao).findByExcursion(e1);
    }


    @Test
    public void testNullArguments() {
        doThrow(new NullPointerException()).when(tripDao).create(null);
        doThrow(new NullPointerException()).when(tripDao).update(null);
        doThrow(new NullPointerException()).when(tripDao).delete(null);
        doThrow(new NullPointerException()).when(tripDao).findById(null);
        doThrow(new NullPointerException()).when(tripDao).findByName(null);
        doThrow(new NullPointerException()).when(tripDao).findByDate(null, null);
        doThrow(new NullPointerException()).when(tripDao).findByDate(null, null);
        doThrow(new NullPointerException()).when(tripDao).findByDestination(null);
        doThrow(new NullPointerException()).when(tripDao).findByPrice(null, null);
        doThrow(new NullPointerException()).when(tripDao).findByPrice(null, null);
        doThrow(new NullPointerException()).when(tripDao).findByCapacity(null);
        doThrow(new NullPointerException()).when(tripDao).findByExcursion(null);

        assertThatThrownBy(() -> tripService.createTrip(null))
                .as("create(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.deleteTrip(null))
                .as("delete(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.getById(null))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByName(null))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByDate(null, t1.getDateFrom()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByDate(t1.getDateFrom(), null))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByDestination(null))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByPrice(null, t1.getPrice()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByPrice(t1.getPrice(), null))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByCapacity(null))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByExcursion(null))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * This is to check whether Service layer masks all exceptions from Dao
     */
    @Test
    public void testDataAccessError() {
        doThrow(new EntityExistsException()).when(tripDao).create(any());
        doThrow(new EntityNotFoundException()).when(tripDao).update(any());
        doThrow(new EntityNotFoundException()).when(tripDao).delete(any());
        doThrow(new EntityNotFoundException()).when(tripDao).findById(any());
        doThrow(new EntityNotFoundException()).when(tripDao).findByName(any());
        doThrow(new EntityNotFoundException()).when(tripDao).findByDate(any(), any());
        doThrow(new EntityNotFoundException()).when(tripDao).findByDestination(any());
        doThrow(new EntityNotFoundException()).when(tripDao).findByPrice(any(), any());
        doThrow(new EntityNotFoundException()).when(tripDao).findByCapacity(any());
        doThrow(new EntityNotFoundException()).when(tripDao).findByExcursion(any());

        assertThatThrownBy(() -> tripService.createTrip(any()))
                .as("create(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.deleteTrip(any()))
                .as("delete(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.findByName(any()))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.findByDate(any(), any()))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.findByDestination(any()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.findByPrice(any(), any()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.findByCapacity(any()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.findByExcursion(any()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);
    }
    
    
    
}