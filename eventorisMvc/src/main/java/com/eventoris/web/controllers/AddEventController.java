package com.eventoris.web.controllers;

import java.awt.font.NumericShaper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.eventoris.service.CategoryManager;
import com.eventoris.service.EventManager;
import com.eventoris.web.auth.UserSessionInfo;
import com.eventoris.web.formbeans.AddEventFormData;

import eventoris.datatypes.CategoryInfo;
import eventoris.datatypes.EventInfo;

public class AddEventController extends SimpleFormController {

	  /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private EventManager eventManager;
    private CategoryManager categoryManager;
    
    public ModelAndView onSubmit(Object command)
            throws ServletException {
    	AddEventFormData newEvent = ((AddEventFormData) command) ;
       

        EventInfo event = new EventInfo();
        String hour = newEvent.getHour();
        String minutes = newEvent.getMinutes();
        String date = newEvent.getEventdate();
        
        String dateOfEvent = date.substring(6, 10)+"-"+date.substring(0, 2)+"-"+date.substring(3, 5)+" "+hour+":"+minutes;
        
        event.setTitle(newEvent.getTitle());
        event.setDescription(newEvent.getDescription());
        event.setAddress(newEvent.getAddress());
        event.setDateOfEvent(dateOfEvent);
        event.setCategoryID(newEvent.getCategory());
		UserSessionInfo activeUser = (UserSessionInfo) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
        event.setOwnerID(activeUser.getId());
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-d HH:mm:ss");
        Date dateCreated = new Date();
        event.setDateCreated(format.format( dateCreated));
        logger.info("AddEventController: new eventId=" + newEvent.getEventid() );
        
        if(newEvent.getEventid()==0){
        	logger.info("AddEventController: Creating event " + event);
        	eventManager.createNewEventInfo(event);
        }
        else{
        	logger.info("AddEventController: Updating event " + event);
        	event.setEventID(newEvent.getEventid());
        	eventManager.updateEvent(event);
        	return new ModelAndView(new RedirectView("eventdetail.htm?event="+newEvent.getEventid()));
        }
        logger.info("AddEventController: EventId " + newEvent);
        return new ModelAndView(new RedirectView(getSuccessView()));
    }

    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
    	List<CategoryInfo> categories = this.categoryManager.getAllCategories();
    	Map<Integer,String> countryMap = new LinkedHashMap<Integer,String>();
    	for(CategoryInfo category:categories){
    		countryMap.put(category.getIdCategory(), category.getCategoryName());
    	}
		Map<String, Object> myModel = new HashMap<String, Object>();
		myModel.put("categories", countryMap);
    	return myModel;
    }
   
    
    @Override
    protected void onFormChange(HttpServletRequest request,
    		HttpServletResponse response, Object command) throws Exception {
    	 logger.info("AddEventController: onFormChange" );
    	super.onFormChange(request, response, command);
    }
	
	@Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	AddEventFormData eventInfo = new AddEventFormData();
    	
    	if(request.getParameter("eventid") != null){
    		int eventId;
    		try{
    			eventId = Integer.parseInt(request.getParameter("eventid"));
    		}catch(NumberFormatException nfe){
    			return new AddEventController();
    		}
	    	EventInfo evInfo = eventManager.getEventById(eventId);
	    	if(evInfo == null){
	    		return new AddEventController();
	    	}
	    	String dateOfEvent = evInfo.getDateOfEvent();
	    	String date = dateOfEvent.substring(5, 7)+"/"+dateOfEvent.substring(8, 10)+"/"+dateOfEvent.substring(0, 4);
	    	String hour = dateOfEvent.substring(11, 13);
	    	String minutes = dateOfEvent.substring(14, 16);
	    	
	    	eventInfo.setAddress(evInfo.getAddress());
	    	eventInfo.setDescription(evInfo.getDescription());
	    	eventInfo.setCategory(evInfo.getCategoryID());
	    	eventInfo.setEventdate(date);
	    	eventInfo.setHour(hour);
	    	eventInfo.setMinutes(minutes);
	    	eventInfo.setTitle(evInfo.getTitle( ));
	    	eventInfo.setEventid(eventId);
	    	
	    	UserSessionInfo activeUser = (UserSessionInfo) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();

	    	
	    	if(evInfo.getOwnerID() == activeUser.getId())
	    		return eventInfo;
	    	else
	    		return new AddEventFormData();
    	}else{
    		return new AddEventFormData();
    	}
    }
 
    public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}
    
    public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}
}
