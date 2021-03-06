package com.eventoris.web.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.eventoris.service.EventManager;
import com.eventoris.web.auth.UserSessionInfo;
import com.eventoris.web.formbeans.AddEventFormData;

import eventoris.datatypes.EventInfo;

public class MyCalendarController implements Controller {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private EventManager eventManager;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserSessionInfo activeUser = (UserSessionInfo) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		List<EventInfo> eventsGoing = eventManager
				.getAllEventsUserIsGoing(activeUser.getId());
		List<EventInfo> eventsMaybe = eventManager
				.getAllEventsUserMaybeComes(activeUser.getId());

		Map<String, Object> myModel = new HashMap<String, Object>();
		myModel.put("productsGoing", eventsGoing);
		myModel.put("productsMaybe", eventsMaybe);
		return new ModelAndView("events_calendar", "model", myModel);
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		AddEventFormData eventInfo = new AddEventFormData();
		return eventInfo;
	}

	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}

}
