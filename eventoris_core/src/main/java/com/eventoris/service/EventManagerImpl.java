package com.eventoris.service;

import java.util.List;

import eventoris.dao.EventDAO;
import eventoris.dao.EventJDBCTemplate;
import eventoris.datatypes.CommentInfo;
import eventoris.datatypes.EventInfo;
import eventoris.datatypes.UserInfo;

public class EventManagerImpl implements EventManager {

	private EventDAO eventDaoProvider;

	public EventManagerImpl() {
		eventDaoProvider = new EventJDBCTemplate();
	}

	public void setDataSource(EventDAO dataSource) {
		eventDaoProvider = dataSource;
	}

	public List<EventInfo> getAllEventsCreatedByUser(int userId) {
		return eventDaoProvider.getEventsOwnedByUser(userId);
	}

	public List<EventInfo> getAllEvents() {
		return eventDaoProvider.getAll();
	}
	
	public void createNewEventInfo(EventInfo event) {
		eventDaoProvider.create(event);
	}

	public List<EventInfo> getLastEventsByDate(int eventsCount) {
		return eventDaoProvider.getLastEventsByDate(eventsCount);
	}

	public List<EventInfo> getTopEvents(int eventsCount) {
		return eventDaoProvider.getTopEvents(eventsCount);
	}

	public EventInfo getEventById(int id) {
		return eventDaoProvider.getEvent(id);
	}

	public List<EventInfo> getEventByTitle(String searchText) {
		return eventDaoProvider.getEventsByTitle(searchText);
	}

	public List<CommentInfo> getCommentsForEvent(int eventId) {
		return eventDaoProvider.getCommentsForEvent(eventId);
	}

	public int getNumberOfComingParticipants(int eventId) {
		return eventDaoProvider.getSubscribedUsersCount(eventId, 1);
	}

	public int getNumberOfTotalParticipants(int eventId) {
		int subscribed = eventDaoProvider.getSubscribedUsersCount(eventId, 1);
		int maybe = eventDaoProvider.getSubscribedUsersCount(eventId, 2);
		return subscribed + maybe;
	}

	public int getNumberOfMaybeGoingParticipants(int eventId) {
		return eventDaoProvider.getSubscribedUsersCount(eventId, 2);
	}

	public UserInfo getOnwerOfTheEvent(int eventId) {
		return eventDaoProvider.getEventOwnerInfo(eventId);
	}

	public List<EventInfo> getAllEventsUserIsGoing(int userId) {
		return eventDaoProvider.getEventsUserIsSubscribedTo(userId,1);
	}

	public List<EventInfo> getAllEventsUserMaybeComes(int userId) {
		return eventDaoProvider.getEventsUserIsSubscribedTo(userId,2);
	}

	public void updateEvent(EventInfo eventInfo) {
		eventDaoProvider.update(eventInfo);
		
	}
	
	public void addComment(CommentInfo comment){
		eventDaoProvider.setComment(comment);
	}

	public void participate(int eventID, int userId, int status) {
		eventDaoProvider.addParticipant(eventID, userId, status);
	}

}
