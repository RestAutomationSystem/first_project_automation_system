package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.place.*;

@Security.Authenticated(Secured.class)
public class Places extends Controller{

	public static Form<Place> placeForm=form(Place.class);
	
	public static Result index(RestaurantSection id) {
		return ok(
				index.render(
				User.find.where().eq("email", request().username()).findUnique(),
						RestaurantSection.find.byId(id),
						Place.findAll(),
						placeForm
						)
				);
    }

	
public static Result newPlace(RestaurantSection id) throws ParseException{
		//Form<Place> filledForm=form(Place.class).bindFromRequest();
		DynamicForm filledForm=form().bindFromRequest();
		DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
   	 sdf2.setLenient(false);
		Logger.debug("startDate:"+filledForm.get("start_time"));
       Logger.debug("deadline:"+filledForm.get("end_time"));
      	
		Place.create(filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
	
		
		return ok(
				index.render(
				User.find.where().eq("email", request().username()).findUnique(),
						RestaurantSection.find.byId(id),
						Place.findAll(),
						placeForm
						)
				);
		
}


public static Result placePage(int id) {

		
		session("place",id+"");
			return ok(
					item.render(
					User.find.where().eq("email", request().username()).findUnique(),
							Place.find.byId(id)
							)
					);
			
	
}


public static Result updatePlace(int id) throws ParseException{
	//Form<Place> filledForm=form(Place.class).bindFromRequest();
	DynamicForm filledForm=form().bindFromRequest();
		
	SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
   	 sdf2.setLenient(false);
	
	Place.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
	return ok(
			index.render(
			User.find.where().eq("email", request().username()).findUnique(),
			Place.find.byId(id).section,
						
					Place.findAll(),
						placeForm
					)
			);
		
}

public static Result deletePlace(int id) {
	Place.delete(id);
	return ok(
			index.render(
			User.find.where().eq("email", request().username()).findUnique(),
			Place.find.byId(id).section,
						
					Place.findAll(),
						placeForm
					)
				);
		
}



}