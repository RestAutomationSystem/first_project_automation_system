package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.section.*;

@Security.Authenticated(Secured.class)
public class Sections extends Controller{

	public static Form<RestaurantSection> sectionForm = form(RestaurantSection.class);
	
	public static Result index(int id){
		return ok(index.render(
			User.find.where().eq("email", request().username()).findUnique(),
				Restaurant.find.byId(id),RestaurantSection.findByRestaurant(id),sectionForm));
    }

	public static Result newSection(int id) throws ParseException{
		DynamicForm filledForm=form().bindFromRequest();
		DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sdf2.setLenient(false);
		Logger.debug("startDate:"+filledForm.get("start_time"));
	    Logger.debug("deadline:"+filledForm.get("end_time"));
		Date now = new Date();

		//TODO: data and other parameters
		RestaurantSection.create(filledForm.get("title"), filledForm.get("description"),"",
			Restaurant.find.byId(id),now,now);

		return ok(index.render(
			User.find.where().eq("email", request().username()).findUnique(),
			Restaurant.find.byId(id),RestaurantSection.findByRestaurant(id),sectionForm));

	}

	public static Result addingSectionPage(int id){
		return ok(add_section.render(User.find.where().eq("email", request().username()).findUnique(),
			Restaurant.find.byId(id),sectionForm));
	}

	public static Result sectionPage(int id){
		session("section",id+"");
		return ok(item.render(
			User.find.where().eq("email", request().username()).findUnique(),
				RestaurantSection.find.byId(id)));
	}

	public static Result updateSection(int id) throws ParseException{
		DynamicForm filledForm=form().bindFromRequest();
		SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sdf2.setLenient(false);
		Date now = new Date();

		//TODO: data and other parameters
		RestaurantSection.update(id, filledForm.get("title"), filledForm.get("description"),"",now,now);
        Restaurant parentR=RestaurantSection.find.byId(id).restaurant;
		return ok(index.render(
			User.find.where().eq("email", request().username()).findUnique(),
                parentR,RestaurantSection.findByRestaurant(parentR.id),sectionForm));
	}

	public static Result deleteSection(int id){
		Restaurant parentR=RestaurantSection.find.byId(id).restaurant;
		RestaurantSection.delete(id);

		return ok(index.render(
			User.find.where().eq("email", request().username()).findUnique(),
				parentR,RestaurantSection.findByRestaurant(parentR.id),sectionForm));
	}
}