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
        if(Secured.isAdmin()) {
            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.find.byId(id),RestaurantSection.findByRestaurant(id),sectionForm));
        }else{
            return forbidden();
        }




    }

	public static Result newSection(int id) throws ParseException{
        if(Secured.isAdmin()) {
            DynamicForm filledForm=form().bindFromRequest();
            DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);
            Date now = new Date();

            //TODO: data and other parameters
            int s_id=RestaurantSection.create(filledForm.get("title"), filledForm.get("description"),"",
                    Restaurant.find.byId(id),now,now);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Создана новая cекция:"+s_id+" внутри ресторана:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
            Event event=new Event("SECTION",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            event.save();
            Logger.info(desc);
            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.find.byId(id),RestaurantSection.findByRestaurant(id),sectionForm));
        }else{
            return forbidden();
        }





	}

	public static Result addingSectionPage(int id){
        if(Secured.isAdmin()) {
            return ok(add_section.render(User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.find.byId(id),sectionForm));
        }else{
            return forbidden();
        }




	}

	public static Result sectionPage(int id){
        if(Secured.isAdmin()) {
            session("section",id+"");
            return ok(item.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    RestaurantSection.find.byId(id)));
        }else{
            return forbidden();
        }




	}

	public static Result updateSection(int id) throws ParseException{
        if(Secured.isAdmin()) {
            DynamicForm filledForm=form().bindFromRequest();
            SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);
            Date now = new Date();

            //TODO: data and other parameters
            RestaurantSection seсtion=RestaurantSection.find.ref(id);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Изменена секция:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ seсtion.title+"\nОписание:"+ seсtion.description+"\nСтатус:\nНачало:"+ seсtion.start_time+"\nКонец:"+ seсtion.end_time
                    +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

            Event event=new Event("SECTION",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

            RestaurantSection.update(id, filledForm.get("title"), filledForm.get("description"),"",now,now);
            event.save();
            Logger.info(desc);


            Restaurant parentR=RestaurantSection.find.byId(id).restaurant;
            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    parentR,RestaurantSection.findByRestaurant(parentR.id),sectionForm));
        }else{
            return forbidden();
        }




	}

	public static Result deleteSection(int id){
        if(Secured.isAdmin()) {
            Restaurant parentR=RestaurantSection.find.byId(id).restaurant;
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            RestaurantSection section=RestaurantSection.find.ref(id);

            String desc="Удалена секция:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ section.title+"\nОписание:"+ section.description+"\nСтатус:\nНачало:"+ section.start_time+"\nКонец:"+ section.end_time;

            Event event=new Event("SECTION",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

            RestaurantSection.delete(id);
            event.save();
            Logger.info(desc);


            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    parentR,RestaurantSection.findByRestaurant(parentR.id),sectionForm));
        }else{
            return forbidden();
        }




	}
}