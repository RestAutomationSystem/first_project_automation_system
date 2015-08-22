package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import play.*;
import models.*;
import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.restaurant.*;

@Security.Authenticated(Secured.class)
public class Restaurants extends Controller{

	public static Form<Restaurant> restaurantForm=form(Restaurant.class);
	
	public static Result index() {
        if(Secured.isAdmin()) {
        return ok(
				index.render(
				User.find.where().eq("email", request().username()).findUnique(),
						Restaurant.findAll(),
						restaurantForm
						)
				);
        }else{
            return forbidden();
        }
    }
	
	public static Result newRestaurant() throws ParseException{
		//Form<Restaurant> filledForm=form(Restaurant.class).bindFromRequest();
        if(Secured.isAdmin()) {
        DynamicForm filledForm=form().bindFromRequest();
		DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sdf2.setLenient(false);

        int r_id=Restaurant.create(filledForm.get("title"), filledForm.get("description"),"",
				sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String desc="Создан новый ресторан:"+r_id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
        Event event=new Event("RESTAURANT",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        event.save();
            Logger.debug(desc);

            return ok(
			index.render(
			User.find.where().eq("email", request().username()).findUnique(),
				Restaurant.findAll(),
				restaurantForm
				)
			);
        }else{
            return forbidden();
        }
	}

	public static Result addingRestaurantPage(){
        if(Secured.isAdmin()) {
            return ok(
                    add_restaurant.render(
                            User.find.where().eq("email", request().username()).findUnique(), restaurantForm
                    )
            );
        }else{
            return forbidden();
        }
	}

	public static Result restaurantPage(int id) {
        if(Secured.isAdmin()) {
            session("restaurant",id+"");

			return ok(
				item.render(
				User.find.where().eq("email", request().username()).findUnique(),
						Restaurant.find.byId(id)));
        }else{
            return forbidden();
        }
	}

    public static Result updateRestaurant(int id) throws ParseException{
        if(Secured.isAdmin()) {
        //Form<Restaurant> filledForm=form(Restaurant.class).bindFromRequest();

	DynamicForm filledForm=form().bindFromRequest();
		
	SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
   	 sdf2.setLenient(false);
	

    Restaurant restaurant=Restaurant.find.ref(id);

    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

     String desc="Изменен ресторан:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ restaurant.title+"\nОписание:"+ restaurant.description+"\nСтатус:\nНачало:"+ restaurant.start_time+"\nКонец:"+ restaurant.end_time
             +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

      Event event=new Event("RESTAURANT",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

      Restaurant.update(id, filledForm.get("title"), filledForm.get("description"));
      event.save();
            Logger.debug(desc);


            return ok(
			index.render(
			User.find.where().eq("email", request().username()).findUnique(),
					Restaurant.findAll(),
						restaurantForm
					)
			);
        }else{
            return forbidden();
        }
}

public static Result deleteRestaurant(int id) {
        if(Secured.isAdmin()) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Restaurant restaurant=Restaurant.find.ref(id);

            String desc="Удален ресторан:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ restaurant.title+"\nОписание:"+ restaurant.description+"\nСтатус:\nНачало:"+ restaurant.start_time+"\nКонец:"+ restaurant.end_time;

            Event event=new Event("RESTAURANT",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

            Restaurant.delete(id);
            event.save();
            Logger.debug(desc);

            return ok(
			index.render(
			User.find.where().eq("email", request().username()).findUnique(),
					Restaurant.findAll(),
						restaurantForm
					)
				);
        }else{
            return forbidden();
        }
}


}