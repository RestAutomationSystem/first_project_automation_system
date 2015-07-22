package controllers;

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
				UserTest.find.where().eq("email", request().username()).findUnique(),
						Restaurant.findAll(),
						restaurantForm
						)
				);
    }

	
public static Result newRestaurant() {
		Form<Restaurant> filledForm=form(Restaurant.class).bindFromRequest();
		Restaurant.create(filledForm.get().name, filledForm.get().description,"");
		return ok(
				index.render(
				UserTest.find.where().eq("email", request().username()).findUnique(),
						Restaurant.findAll(),
						restaurantForm
						)
				);
		
}


public static Result restaurantPage(Long id) {

		
		session("restaurant",id+"");
			return ok(
					item.render(
					UserTest.find.where().eq("email", request().username()).findUnique(),
							Restaurant.find.byId(id)
							)
					);
			
	
}


public static Result updateRestaurant(Long id) {
	Form<Restaurant> filledForm=form(Restaurant.class).bindFromRequest();
	Restaurant.update(id, filledForm.get().name, filledForm.get().description);
	return ok(
			index.render(
			UserTest.find.where().eq("email", request().username()).findUnique(),
					Restaurant.findAll(),
						restaurantForm
					)
			);
		
}

public static Result deleteRestaurant(Long id) {
	Restaurant.delete(id);
	return ok(
			index.render(
			UserTest.find.where().eq("email", request().username()).findUnique(),
					Restaurant.findAll(),
						restaurantForm
					)
				);
		
}



}