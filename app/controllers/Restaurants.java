package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.restaurant.*;

@Security.Authenticated(Secured.class)
public class Restaurants extends Controller{

	public static Form<Restaurant> restaurantForm=form(Restaurant.class);
	
	public static Result index() {
		return ok(
				index.render(
				User.find.where().eq("email", request().username()).findUnique(),
						Restaurant.findAll(),
						restaurantForm
						)
				);
    }

	
public static Result newRestaurant() throws ParseException{
		Form<Restaurant> filledForm=form(Restaurant.class).bindFromRequest();
		
		SimpleDateFormat sdf2=new SimpleDateFormat("hh:mm");
   	 sdf2.setLenient(false);
		Logger.debug("startDate:"+sdf2.parse(sdf2.format(filledForm.get().start_time)));
       Logger.debug("deadline:"+sdf2.parse(sdf2.format(filledForm.get().end_time)));
      	
		Restaurant.create(filledForm.get().title, filledForm.get().description,"",sdf2.parse(sdf2.format(filledForm.get().start_time)),sdf2.parse(sdf2.format(filledForm.get().end_time)));
	
		
		return ok(
				index.render(
				User.find.where().eq("email", request().username()).findUnique(),
						Restaurant.findAll(),
						restaurantForm
						)
				);
		
}


public static Result restaurantPage(int id) {

		
		session("restaurant",id+"");
			return ok(
					item.render(
					User.find.where().eq("email", request().username()).findUnique(),
							Restaurant.find.byId(id)
							)
					);
			
	
}


public static Result updateRestaurant(int id) throws ParseException{
	Form<Restaurant> filledForm=form(Restaurant.class).bindFromRequest();
	SimpleDateFormat sdf2=new SimpleDateFormat("hh:mm");
   	 sdf2.setLenient(false);
	
	Restaurant.update(id, filledForm.get().title, filledForm.get().description,"",sdf2.parse(sdf2.format(filledForm.get().start_time)),sdf2.parse(sdf2.format(filledForm.get().end_time)));
	return ok(
			index.render(
			User.find.where().eq("email", request().username()).findUnique(),
					Restaurant.findAll(),
						restaurantForm
					)
			);
		
}

public static Result deleteRestaurant(int id) {
	Restaurant.delete(id);
	return ok(
			index.render(
			User.find.where().eq("email", request().username()).findUnique(),
					Restaurant.findAll(),
						restaurantForm
					)
				);
		
}



}