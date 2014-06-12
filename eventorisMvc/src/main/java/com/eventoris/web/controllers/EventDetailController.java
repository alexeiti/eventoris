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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.eventoris.service.CategoryManager;
import com.eventoris.service.EventManager;

import eventoris.datatypes.CategoryInfo;
import eventoris.datatypes.CommentInfo;
import eventoris.datatypes.EventInfo;
import eventoris.datatypes.UserInfo;

public class EventDetailController implements Controller{

	protected final Log logger = LogFactory.getLog(getClass());
	private EventManager eventManager;
	private CategoryManager categoryManager;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Map<String, Object> myModel = new HashMap<String, Object>();
		
		
		String eventIdAsString = request.getParameter("event");
		
		int eventId = -1;
		try{
		    eventId = Integer.parseInt(eventIdAsString);
		}catch(NumberFormatException ex){
		 
			return new ModelAndView("eventdetail", "dataMap", null);
		}
		EventInfo resultEventInfo = eventManager.getEventById(eventId);
		if(resultEventInfo == null)
			return new ModelAndView("eventdetail", "dataMap", null);
		
		logger.info("returning eventInfo:" + resultEventInfo);
	 
		UserInfo owner = eventManager.getOnwerOfTheEvent(eventId);
		CategoryInfo category = categoryManager.getCategoryById(resultEventInfo.getCategoryID());
		
	    List<CommentInfo> comments = eventManager.getCommentsForEvent(eventId);
		myModel.put("eventInfo", resultEventInfo);
		myModel.put("comments", comments);
		myModel.put("ownerInfo", owner);
		myModel.put("categoryInfo", category);
		return new ModelAndView("eventdetail", "dataMap", myModel);
	}

	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}

    public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}
 
	
}
