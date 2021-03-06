package eventoris.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import eventoris.datatypes.CategoryInfo;
import eventoris.datatypes.CommentInfo;
import eventoris.datatypes.EventInfo;
import eventoris.datatypes.ParticipantInfo;
import eventoris.datatypes.UserEventStatus;
import eventoris.datatypes.UserInfo;

public class EventJDBCTemplate implements EventDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	protected final Log logger = LogFactory.getLog(getClass());

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void create(EventInfo event) {

		String SQL = "insert into event_info (title, id_category, description, date_created, date_of_event, address, id_owner) values (?, ?, ?, ?, ?, ?, ?)";

		jdbcTemplateObject.update(SQL, event.getTitle(), event.getCategoryID(),
				event.getDescription(), event.getDateCreated(),
				event.getDateOfEvent(), event.getAddress(), event.getOwnerID());
		System.out.println("Created record: \nTitle: " + event.getTitle()
				+ "\nCategID: " + event.getCategoryID() + "\ndescription: "
				+ event.getDescription() + "\ndateCreated: "
				+ event.getDateCreated() + "\ndateOfEvent: "
				+ event.getDateOfEvent() + "\naddress: " + event.getAddress()
				+ "\nauthorID: " + event.getOwnerID());
		return;
	}

	public EventInfo getEvent(int id) { // Aici tot un obiect intreg pentru id?
		String SQL = "select * from event_info where id_event_info = ?";
		EventInfo event = null;
		try {
			event = jdbcTemplateObject.queryForObject(SQL, new Object[] { id },
					new EventMapper());
		} catch (IncorrectResultSizeDataAccessException ex) {
			return null;
		}

		return event;
	}

	public List<EventInfo> getAll() {
		String SQL = "select * from event_info order by date_created DESC";
		List<EventInfo> events = jdbcTemplateObject.query(SQL,
				new EventMapper());

		return events;
	}

	public void delete(int id) {
		String SQL = "delete from event_info where id_event_info = ?";

		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted event, id = " + id);

		return;
	}

	public void update(EventInfo event) {
		String SQL = "update event_info set title = ?, " + "id_category = ?, "
				+ "description = ?, " + "date_created = ?,  "
				+ "date_of_event = ?, " + "address = ?, " + "id_owner = ? "
				+ "where id_event_info = ?";

		jdbcTemplateObject.update(SQL, event.getTitle(), event.getCategoryID(),
				event.getDescription(), event.getDateCreated(),
				event.getDateOfEvent(), event.getAddress(), event.getOwnerID(),
				event.getEventID());
		System.out.println("Updated!");
		return;
	}

	public List<EventInfo> getLastEventsByDate(int eventsCount) {
		String SQL = "select * from event_info order by date_created desc limit ?";

		List<EventInfo> events = jdbcTemplateObject.query(SQL,
				new Object[] { eventsCount }, new EventMapper());

		return events;
	}

	public CategoryInfo getCategory(int id) {
		String SQL = "select * from event_categories where id_event_categories = ? ";
		CategoryInfo category;
		try {
			category = jdbcTemplateObject.queryForObject(SQL,
					new Object[] { id }, new CategoryMapper());
		} catch (IncorrectResultSizeDataAccessException ex) {
			return null;
		}

		return category;
	}

	public List<EventInfo> getTopEvents(int eventsCount) {
		String SQL = "select * from event_info join "
				+ "(select id_event, count(id_event) as member_count from participants where id_status = 1 group by id_event) as participants "
				+ "on participants.id_event = event_info.id_event_info "
				+ "order by participants.member_count desc limit ?";

		List<EventInfo> events = jdbcTemplateObject.query(SQL,
				new Object[] { eventsCount }, new EventMapper());

		return events;
	}

	public boolean checkIfParticipantExists(ParticipantInfo participant) {
		String SQL = "select count(*) from participants where id_user = ? and id_event = ?";
		boolean exists = false;

		int count = jdbcTemplateObject.queryForInt(SQL, new Object[] {
				participant.getIdUser(), participant.getIdEvent() });

		System.out.println(count);

		if (count != 0)
			exists = true;

		return exists;
	}

	public boolean compareExistingAndRequestedStatus(ParticipantInfo participant) {
		String SQL = "select id_status from participants where id_event = ? and id_user = ?";
		boolean isEqual = false;

		int existingStatus = jdbcTemplateObject.queryForInt(SQL, new Object[] {
				participant.getIdEvent(), participant.getIdUser() });

		if (existingStatus == participant.getIdStatus()) {
			isEqual = true;
		}
		return isEqual;
	}

	public void changeParticipationStatus(ParticipantInfo participant) {
		String SQL = "update participants set id_status = ? where id_event = ? and id_user = ?";

		jdbcTemplateObject.update(SQL, new Object[] {
				participant.getIdStatus(), participant.getIdEvent(),
				participant.getIdUser() });

		System.out.println("Succesfully changed status");
	}

	public void addParticipant(ParticipantInfo participant) {
		String SQL = "insert into participants (id_event, id_user, id_status, date_subscribed) values (?, ?, ?, SYSDATE())";

		logger.info("EventJDBCTemplate: adding participant");
		jdbcTemplateObject.update(SQL, new Object[] { participant.getIdEvent(),
				participant.getIdUser(), participant.getIdStatus() });
	}

	/*
	 * public void subscribeToEvent(int idEvent, int idUser, int
	 * idParticipationStatus) { boolean exists =
	 * this.checkIfParticipantExists(idUser, idEvent); boolean isEqual = false;
	 * 
	 * if (exists) { isEqual = this.compareExistingAndRequestedStatus(idEvent,
	 * idUser, idParticipationStatus); if (isEqual)
	 * System.out.println("Dvs. deja sunteţi înscris în lista dată"); else {
	 * this.changeParticipationStatus(idUser, idEvent, idParticipationStatus); }
	 * 
	 * } else this.addParticipant(idEvent, idUser, idParticipationStatus); }
	 */
	public List<CategoryInfo> getAllCategories() {
		String SQL = "select * from event_categories";
		List<CategoryInfo> categories = jdbcTemplateObject.query(SQL,
				new CategoryMapper());
		return categories;
	}

	public List<EventInfo> getEventsByTitle(String searchText) {
		String SQL = "select * from event_info where title like '%"
				+ searchText + "%'";

		List<EventInfo> events = jdbcTemplateObject.query(SQL,
				new EventMapper());

		return events;
	}

	public List<CommentInfo> getCommentsForEvent(int eventId) {
		String SQL = "select comments.*,user_details.firstname as 'poster_name' ,user_details.lastname as 'poster_family'  from comments "
				+ " join user_details on user_details.id_user = comments.id_owner"
				+ " where id_event=? order by comments.date_created desc";
		List<CommentInfo> comments = jdbcTemplateObject.query(SQL,
				new Object[] { eventId }, new CommentMapper());
		return comments;
	}

	public void setComment(CommentInfo comment) {
		String SQL = "INSERT INTO comments (comment, id_owner, id_event, date_created) VALUES (?, ?, ?, SYSDATE())";
		jdbcTemplateObject.update(SQL, new Object[] { comment.getComment(),
				comment.getPosterId(), comment.getEventId() });
	}

	public int getSubscribedUsersCount(int eventId, int status) {
		String SQL = "select count(*) from participants where id_event = ? and id_status = ?";
		return jdbcTemplateObject.queryForInt(SQL, eventId, status);
	}

	public UserInfo getEventOwnerInfo(int eventId) {
		String SQL = "select user_details.* ,users.* from event_info"
				+ "	join users on event_info.id_owner = users.id_users"
				+ "	join user_details on user_details.id_user = users.id_users"
				+ " where event_info.id_event_info = ? ";
		UserInfo owner;
		try {
			owner = jdbcTemplateObject.queryForObject(SQL,
					new Object[] { eventId }, new UserInfoMapper());
		} catch (IncorrectResultSizeDataAccessException ex) {
			return null;
		}
		return owner;
	}

	public List<EventInfo> getEventsOwnedByUser(int userId) {
		String SQL = "select * from event_info where id_owner= ? order by date_created DESC";
		List<EventInfo> events = jdbcTemplateObject.query(SQL,
				new Object[] { userId }, new EventMapper());

		return events;
	}

	public List<EventInfo> getEventsUserIsSubscribedTo(int userId, int statusId) {
		String SQL = "select * from event_info "
				+ "join participants on  participants.id_event = event_info.id_event_info "
				+ "where id_user = ? and id_status =? order by date_of_event DESC";
		List<EventInfo> events = jdbcTemplateObject.query(SQL, new Object[] {
				userId, statusId }, new EventMapper());

		return events;
	}

	public void subscribeToEvent(int idEvent, int idUser,
			int participationStatus) {
		// TODO Auto-generated method stub

	}

	public List<EventInfo> getEventsByCategory(int idCategory) {
		String SQL = "select * from event_info where id_category = ? order by date_created DESC";
		List<EventInfo> events = jdbcTemplateObject.query(SQL,
				new Object[] { idCategory }, new EventMapper());

		return events;
	}

	public UserEventStatus getEventStatusForEvent(int eventId, int userId) {
		String SQL = "select participants.id_status from participants  where id_user = ? and id_event = ?";
		UserEventStatus result = new UserEventStatus();
		int sqlResult = -1;
		try {
			sqlResult = jdbcTemplateObject.queryForInt(SQL, new Object[] {
					userId, eventId });
		} catch (EmptyResultDataAccessException erdae) {
			return result;
		}
		if (sqlResult == 0) {
			return result;
		}
		if (sqlResult == 1) {
			result.setComing(true);
			return result;
		}
		if (sqlResult == 2) {
			result.setMaybeComing(true);
			return result;
		}

		return result;
	}

	public List<UserInfo> getTopParticipatingUsers(int eventId, int statusId,
			int resultCount) {
		String SQL = "select user_details.* ,users.* from users"
				+ " join user_details on user_details.id_user = users.id_users"
				+ " join participants on participants.id_user = users.id_users"
				+ " where participants.id_event = ? and participants.id_status = ?"
				+ " order by participants.date_subscribed desc  limit ?";
		List<UserInfo> users = jdbcTemplateObject.query(SQL, new Object[] {
				eventId, statusId, resultCount }, new UserInfoMapper());

		return users;
	}
	
	public void removeParticipant(ParticipantInfo participant){
		String SQL = "delete from participants where id_user = ? and id_event = ?";

		jdbcTemplateObject.update(SQL,new Object[] {participant.getIdUser(), participant.getIdEvent()});

		return;
	}
}
