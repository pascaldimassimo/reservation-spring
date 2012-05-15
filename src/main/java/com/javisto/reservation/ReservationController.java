package com.javisto.reservation;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.javisto.reservation.model.Appointment;
import com.javisto.reservation.model.ReservationDAO;

@Controller
public class ReservationController
{
    @Autowired
    private ReservationDAO dao;

    @RequestMapping("/next")
    public ModelAndView next(Principal principal)
    {
        // TODO use principal.getName()
        Appointment nextAppointment = dao.reserveNextAppointment("bart@gmail.com");
        String viewname = nextAppointment == ReservationDAO.BLANK_APPOINTMENT ? "sorry" : "confirm";
        ModelAndView modelAndView = new ModelAndView(viewname);
        modelAndView.addObject("appointment", nextAppointment);
        return modelAndView;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirm(@RequestParam("appointmentId") Long appointmentId)
    {
        dao.confirm(appointmentId);
        return "confirmed";
    }
}
