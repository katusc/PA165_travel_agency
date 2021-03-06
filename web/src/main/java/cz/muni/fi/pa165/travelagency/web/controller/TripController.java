package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.TripCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * SpringMVC Controller for trips
 * @author Jakub Kremláček
 */

@Controller
@RequestMapping(value = "/trip")
public class TripController {
    
    private String DEFAULT_REDIRECT = "redirect:/trip/list";
    @Inject
    private TripFacade tripFacade;
    
    @Inject
    private ReservationFacade reservationFacade;
    
    @Inject
    private ExcursionFacade excursionFacade;
    
    final static Logger log = LoggerFactory.getLogger(TripController.class);
    
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {        
        log.info("request: GET /trip/list");
        List<TripDto> trips = tripFacade.findAll();
        model.addAttribute("trips", trips);
        model.addAttribute("filter", "none");
        return "trip/list";
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("request: GET /excursion/detail/{}", id);
        TripDto tripDto = tripFacade.findById(id);

        if (tripDto == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Trip no. " + id + " doesn't exist");
            return DEFAULT_REDIRECT;
        }

        model.addAttribute("trip", tripDto);
        model.addAttribute("availableCapacity", tripDto.getCapacity() - reservationFacade.findByTrip(tripDto).size());
        model.addAttribute("excursions", excursionFacade.findByTrip(tripDto));

        return "trip/detail";
    }
    
     @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, HttpServletResponse res, HttpServletRequest req) {

        log.info("request: POST /trip/delete/{}",id);
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin()) {
                redirectAttributes.addFlashAttribute("alert_danger", "Only admin can create new trips.");
                return DEFAULT_REDIRECT;
        }
        
        TripDto tripDto = tripFacade.findById(id);
        if (tripDto == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Trip no. " + id + " does not exist");
            return DEFAULT_REDIRECT;
        }

        try {
            tripFacade.delete(tripFacade.findById(id));
        } catch (DataAccessException ex) {
            log.error("request: POST /trip/delete/{}",id);
            if (!reservationFacade.findByTrip(tripDto).isEmpty()) {
                redirectAttributes.addFlashAttribute("alert_danger", "Trip no. " + id + " could not be deleted, because there are existing reservations for it. ");
                return DEFAULT_REDIRECT;
            }
            redirectAttributes.addFlashAttribute("alert_danger", "Trip no. " + id + " could not be deleted.");
            return DEFAULT_REDIRECT;
        }
        redirectAttributes.addFlashAttribute("alert_success", "Trip no. " + id + " has been successfully deleted");

        return DEFAULT_REDIRECT;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, HttpServletResponse res, HttpServletRequest req) {
        log.info("request: GET /trip/edit/{}",id);
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin()) {
                redirectAttributes.addFlashAttribute("alert_danger", "Only admin can create new trips.");
                return DEFAULT_REDIRECT;
        }
        
        TripDto toUpdate = tripFacade.findById(id);

        model.addAttribute("toUpdate", toUpdate);
        model.addAttribute("trips", tripFacade.findAll());


        return "trip/edit";

    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable Long id, @ModelAttribute("toUpdate") TripDto tripDto, Model model, RedirectAttributes redirectAttributes, HttpServletResponse res, HttpServletRequest req) {

        log.info("request: POST /trip/update/{}",id);
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin()) {
                redirectAttributes.addFlashAttribute("alert_danger", "Only admin can update trips.");
                return DEFAULT_REDIRECT;
        }
        
        if (tripDto == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Trip no. " + id + " does not exist");
            return "redirect:/trip/create";
        }
        
        try {            
            tripFacade.update(tripDto);
        } catch (TransactionSystemException ex) {
            log.error("request: POST /trip/update/{}",id,ex);
            redirectAttributes.addFlashAttribute("alert_danger", "Trip could not be in past");
            return "redirect:/trip/edit/" + id;
       
        } catch (Exception ex) {
            log.error("request: POST /trip/update/{}",id,ex);
            redirectAttributes.addFlashAttribute("alert_danger", "Trip could not be updated");
            return "redirect:/trip/edit/" + id;
        }

        redirectAttributes.addFlashAttribute("alert_success", "Trip no. " + id
                + " successfuly updated.");
        return "redirect:/trip/detail/" + id;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newTrip(Model model , RedirectAttributes redirectAttributes, HttpServletResponse res, HttpServletRequest req) {
        log.info("request: GET /trip/new/");
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin()) {
                redirectAttributes.addFlashAttribute("alert_danger", "Only admin can create new trips.");
                return DEFAULT_REDIRECT;
        }
        
        model.addAttribute("newTrip", new TripCreateDto());
        return "trip/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("newTrip") TripCreateDto tripCreateDto, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, HttpServletResponse res, HttpServletRequest req) {
        log.info("request: POST /trip/create/");
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin()) {
                redirectAttributes.addFlashAttribute("alert_danger", "Only admin can create new trips.");
                return DEFAULT_REDIRECT;
        }

        Long id;
        try {
            id = tripFacade.create(tripCreateDto);
        } catch (ValidationException ex) {
            log.error("request: POST /trip/create/",ex);
            redirectAttributes.addFlashAttribute("alert_danger", "Trip could not be in past.");
            return "redirect:/trip/new";
        } catch (DataAccessException ex) {
            log.error("request: POST /trip/create/",ex);
            redirectAttributes.addFlashAttribute("alert_danger", "Trip could not be created (the same trip probably already exists).");
            return "redirect:/trip/new";
        } catch (Exception ex) {
            log.error("request: POST /trip/create/",ex);
            redirectAttributes.addFlashAttribute("alert_danger", "Trip could not be created (internal error).");
            return "redirect:/trip/new";
        }

        redirectAttributes.addFlashAttribute("alert_success", "Trip " + tripCreateDto.getName()
                + " successfully created");

        return "redirect:/trip/detail/" + id;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        sdf.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
}
