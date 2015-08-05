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
import views.html.kitchen.*;

@Security.Authenticated(Secured.class)
public class Kitchens extends Controller{

    public static Form<Kitchen> kitchenForm = form(Kitchen.class);

    public static Result index(int id){
        if(Secured.isAdmin()) {
            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.find.byId(id),Kitchen.findAll(),kitchenForm));
        }else{
            return forbidden();
        }



    }

    public static Result newKitchen(int id) throws ParseException{
        if(Secured.isAdmin()) {
            DynamicForm filledForm=form().bindFromRequest();
            DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);
            Date now = new Date();

            //TODO: data and other parameters
            int k_id=Kitchen.create(filledForm.get("title"), filledForm.get("description"),"",
                    Restaurant.find.byId(id),now,now);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Создана новая кухня:"+k_id+" внутри ресторана:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
            Event event=new Event("KITCHEN",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            event.save();
            Logger.info(desc);
            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.find.byId(id),Kitchen.findAll(),kitchenForm));
        }else{
            return forbidden();
        }



    }

    public static Result addingKitchenPage(int id){
        if(Secured.isAdmin()) {
            return ok(add_kitchen.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.find.byId(id),kitchenForm));
        }else{
            return forbidden();
        }



    }

    public static Result kitchenPage(int id){
        if(Secured.isAdmin()) {
            session("kitchen",id+"");
            return ok(item.render(
                    User.find.where().eq("email", request().username()).findUnique(), Kitchen.find.byId(id)));
        }else{
            return forbidden();
        }



    }

    public static Result updateKitchen(int id) throws ParseException{
        if(Secured.isAdmin()) {
            DynamicForm filledForm=form().bindFromRequest();
            SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);
            Date now = new Date();

            Kitchen kitchen=Kitchen.find.ref(id);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Изменена кухня:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ kitchen.title+"\nОписание:"+ kitchen.description+"\nСтатус:\nНачало:"+ kitchen.start_time+"\nКонец:"+ kitchen.end_time
                    +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

            Event event=new Event("KITCHEN",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

            Kitchen.update(id, filledForm.get("title"), filledForm.get("description"),"",now,now);

            event.save();
            Logger.info(desc);
            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    Kitchen.find.byId(id).restaurant,Kitchen.findAll(),kitchenForm));
        }else{
            return forbidden();
        }



    }

    public static Result deleteKitchen(int id){
        if(Secured.isAdmin()) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Kitchen kitchen=Kitchen.find.ref(id);

            String desc="Удалена кухня:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ kitchen.title+"\nОписание:"+ kitchen.description+"\nСтатус:\nНачало:"+ kitchen.start_time+"\nКонец:"+ kitchen.end_time;

            Event event=new Event("KITCHEN",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            Kitchen.delete(id);
            event.save();
            Logger.info(desc);


            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    Kitchen.find.byId(id).restaurant,Kitchen.findAll(),kitchenForm));
        }else{
            return forbidden();
        }


    }
}