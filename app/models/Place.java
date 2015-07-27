package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Place extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    @Required
    private String title;
	
	@Column(columnDefinition="TEXT")
    private String description;

    private String image;
    private String status;
	
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
     private Date start_time;

	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
    private Date end_time;
	
	@ManyToOne
	public RestaurantSection section;
	
	public Place(String title,String description,String image,RestaurantSection section, Date start_time, Date end_time){
		this.title=title;
		this.description=description;
		this.image=image;
		this.section=section;
        this.start_time=start_time;
        this.end_time=end_time;
	}
	
	public static Finder<Integer,Place> find=new Finder<Integer, Place>(Integer.class, Place.class);
	
	
	 public static List<Place> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String title,String description,String image,RestaurantSection section, Date start_time, Date end_time){
		Place newPlace=new Place(title, description,image,section,start_time,end_time);
		newPlace.save();
	}
	
	public static void update(int id,String title,String description,String image, Date start_time, Date end_time){
		Place table=Place.find.ref(id);
		System.out.println("id:"+id);
		table.title=title;
		table.description=description;
		table.image=image;
		table.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public RestaurantSection getSection() {
        return section;
    }

    public void setSection(RestaurantSection section) {
        this.section = section;
    }

}