package controllers;

import java.util.*;
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

	public static Form<Place> placeForm = form(Place.class);
	
	public static Result index(int id) {
		return ok(
			index.render(
			User.find.where().eq("email", request().username()).findUnique(),
					RestaurantSection.find.byId(id),
					Place.findAll(),
					placeForm
					)
			);
    }

	public static Result newPlace(int id) throws ParseException{
		//Form<Places> filledForm=form(Places.class).bindFromRequest();
		DynamicForm filledForm=form().bindFromRequest();
		DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
		 sdf2.setLenient(false);

		int p_id=Place.create(filledForm.get("title"), filledForm.get("description"), "", RestaurantSection.find.byId(id), sdf2.parse(filledForm.get("start_time")), sdf2.parse(filledForm.get("end_time")));

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String desc="Создано новое место:"+p_id+" внутри секции:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
        Event event=new Event("PLACE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        event.save();
        Logger.info(desc);
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
		//Form<Places> filledForm=form(Places.class).bindFromRequest();
		DynamicForm filledForm=form().bindFromRequest();
		SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sdf2.setLenient(false);
        Place place=Place.find.ref(id);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String desc="Изменено меню:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ place.getTitle()+"\nОписание:"+ place.getDescription()+"\nСтатус:\nНачало:"+ place.getStart_time()+"\nКонец:"+ place.getEnd_time()
                +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

        Event event=new Event("PLACE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        Place.update(id, filledForm.get("title"), filledForm.get("description"), "", sdf2.parse(filledForm.get("start_time")), sdf2.parse(filledForm.get("end_time")));
        event.save();
        Logger.info(desc);

		return ok(
			index.render(
			User.find.where().eq("email", request().username()).findUnique(),
				Place.find.byId(id).section, Place.findAll(), placeForm)
			);
	}

	public static Result deletePlace(int id) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Place place=Place.find.ref(id);

        String desc="Удалено место:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ place.getTitle()+"\nОписание:"+ place.getDescription()+"\nСтатус:\nНачало:"+ place.getStart_time()+"\nКонец:"+ place.getEnd_time();

        Event event=new Event("PLACE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

        Place.delete(id);
        event.save();
        Logger.info(desc);
		return ok(
			index.render(
			User.find.where().eq("email", request().username()).findUnique(),
				Place.find.byId(id).section, Place.findAll(), placeForm)
			);

	}
}