package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import play.*;
import models.*;
import play.mvc.*;
import play.mvc.Http.RequestBody;
import play.data.*;
import static play.data.Form.*;
import views.html.restaurant.*;
import java.util.*;

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
		//Form<Restaurant> filledForm=form(Restaurant.class).bindFromRequest();
		DynamicForm filledForm=form().bindFromRequest();
		DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
   	 sdf2.setLenient(false);
		Logger.debug("startDate:"+filledForm.get("start_time"));
       Logger.debug("deadline:"+filledForm.get("end_time"));
      	
		Restaurant.create(filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
	
		
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

    public static Result restaurantServices(int id) {


        session("service",id+"");
        return ok(
                services.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Restaurant.find.byId(id),
                        Service.findAll(),
                        Restaurant.find.byId(id).services
                )
        );


    }


    public static Result setServices(int id) throws ParseException{
        //Form<Restaurant> filledForm=form(Restaurant.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        Logger.debug("serviceList:"+filledForm.get());

        Map<String,String[]> keys = request().body().asFormUrlEncoded();
        Restaurant restaurant= Restaurant.find.byId(id);
        restaurant.services=new ArrayList<Service>();

        for (Map.Entry<String, String[]> entry : keys.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            for(int i=0;i<entry.getValue().length;i++){
                Logger.debug("service:"+entry.getValue()[i]);
                restaurant.services.add(Service.find.byId(Integer.parseInt(entry.getValue()[i])));
            }
        }

        restaurant.update();


        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Restaurant.find.byId(id)
                )
        );

    }


    public static Result updateRestaurant(int id) throws ParseException{
	//Form<Restaurant> filledForm=form(Restaurant.class).bindFromRequest();
	DynamicForm filledForm=form().bindFromRequest();
		
	SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
   	 sdf2.setLenient(false);
	
	Restaurant.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
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