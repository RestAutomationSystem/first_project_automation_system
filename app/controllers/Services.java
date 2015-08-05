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
import views.html.service.*;

@Security.Authenticated(Secured.class)
public class Services extends Controller{

    public static Form<Service> serviceForm = form(Service.class);

    public static Result index(int id){
        if(Secured.isAdmin()) {
            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.find.byId(id), Service.findByRestaurant(id), serviceForm));
        }else{
            return forbidden();
        }





    }

    public static Result newService(int id) throws ParseException{
        if(Secured.isAdmin()) {
            DynamicForm filledForm = form().bindFromRequest();
            DateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);
            Date now = new Date();

            //TODO: data and other parameters
            int s_id=Service.create(filledForm.get("title"), filledForm.get("description"),"",
                    now,now,Restaurant.find.byId(id));

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Создан новый cервис:"+s_id+" внутри ресторана:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
            Event event=new Event("SERVICE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            event.save();
            Logger.info(desc);

            return ok(index.render(User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.find.byId(id), Service.findByRestaurant(id), serviceForm));
        }else{
            return forbidden();
        }





    }

    public static Result addingServicePage(int id){
        if(Secured.isAdmin()) {
            return ok(add_service.render(User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.find.byId(id),serviceForm));
        }else{
            return forbidden();
        }





    }

    public static Result servicePage(int id){
        if(Secured.isAdmin()) {
            session("service",id+"");
            return ok(item.render(User.find.where().eq("email", request().username()).findUnique(),
                    Service.find.byId(id)));
        }else{
            return forbidden();
        }





    }

    public static Result updateService(int id) throws ParseException{
        if(Secured.isAdmin()) {
            DynamicForm filledForm=form().bindFromRequest();
            Restaurant restaurant=Service.find.byId(id).restaurant;
            SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);
            Date now = new Date();

            Service service=Service.find.ref(id);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Изменен сервис:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ service.title+"\nОписание:"+ service.description+"\nСтатус:\nНачало:"+ service.start_time+"\nКонец:"+ service.end_time
                    +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

            Event event=new Event("SERVICE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

            Service.update(id, filledForm.get("title"), filledForm.get("description"),"",now,now);
            event.save();
            Logger.info(desc);


            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    restaurant, Service.findByRestaurant(restaurant.id), serviceForm));
        }else{
            return forbidden();
        }





    }

    public static Result deleteService(int id){
        if(Secured.isAdmin()) {
            Restaurant restaurant=Service.find.byId(id).restaurant;
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Service service=Service.find.ref(id);

            String desc="Удален сервис:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ service.title+"\nОписание:"+ service.description+"\nСтатус:\nНачало:"+ service.start_time+"\nКонец:"+ service.end_time;

            Event event=new Event("SERVICE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

            Service.delete(id);
            event.save();
            Logger.info(desc);

            return ok(index.render(User.find.where().eq("email", request().username()).findUnique(),
                    restaurant, Service.findByRestaurant(restaurant.id), serviceForm));
        }else{
            return forbidden();
        }





    }
}