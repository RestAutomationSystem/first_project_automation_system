package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//TODO: CLOSE_SESSION methods MUST BE DONE IN CONSTRUCTORS
@Entity
public class SessionOld extends Model {

	@Column(columnDefinition="TEXT")
	private String description;

	@Formats.DateTime(pattern="dd/MM/yyyy/H:i")
	private Date start_time;

	@Formats.DateTime(pattern="dd/MM/yyyy/H:i")
	private Date end_time;

	private boolean status;
	private User user;

	public SessionOld(String description, boolean status, Date start_time, Date end_time, User user) {
		this.description = description;
		this.status = status;
		this.start_time = start_time;
		this.end_time = end_time;
		this.user = user;
	}

	public static Finder<Long, SessionOld> find = new Finder<>(Long.class, SessionOld.class);

	public List<SessionOld> findAll() {
		return find.all();
	}

	public void update(Long id, String description, boolean status, Date end_time, User user) {
		SessionOld session = SessionOld.find.ref(id);
		System.out.println("id:" + id);
		session.description = description;
		session.status = status;
		session.end_time = end_time;
		session.user = user;
		session.update();
	}

	public void delete(Long id){
		find.ref(id).delete();
	}
}