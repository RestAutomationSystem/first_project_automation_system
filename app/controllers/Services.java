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
        return ok(index.render(
            User.find.where().eq("email", request().username()).findUnique(),
            Restaurant.find.byId(id), Service.findByRestaurant(id), serviceForm));
    }

    public static Result newService(int id) throws ParseException{
        DynamicForm filledForm = form().bindFromRequest();
        DateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Logger.debug("startDate:" + filledForm.get("start_time"));
        Logger.debug("deadline:" + filledForm.get("end_time"));
        Date now = new Date();

        //TODO: data and other parameters
        Service.create(filledForm.get("title"), filledForm.get("description"),"",
                now,now,Restaurant.find.byId(id));

        return ok(index.render(User.find.where().eq("email", request().username()).findUnique(),
            Restaurant.find.byId(id), Service.findByRestaurant(id), serviceForm));
    }

    public static Result addingServicePage(int id){
        return ok(add_service.render(User.find.where().eq("email", request().username()).findUnique(),
            Restaurant.find.byId(id),serviceForm));
    }

    public static Result servicePage(int id){
        session("service",id+"");
        return ok(item.render(User.find.where().eq("email", request().username()).findUnique(),
            Service.find.byId(id)));
    }

    public static Result updateService(int id) throws ParseException{
        DynamicForm filledForm=form().bindFromRequest();
        Restaurant restaurant=Service.find.byId(id).restaurant;
        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Date now = new Date();

        //TODO: data and other parameters
        Service.update(id, filledForm.get("title"), filledForm.get("description"),"",now,now);

        return ok(index.render(
            User.find.where().eq("email", request().username()).findUnique(),
            restaurant, Service.findByRestaurant(restaurant.id), serviceForm));
    }

    public static Result deleteService(int id){
        Restaurant restaurant=Service.find.byId(id).restaurant;
        Service.delete(id);

        return ok(index.render(User.find.where().eq("email", request().username()).findUnique(),
            restaurant, Service.findByRestaurant(restaurant.id), serviceForm));
    }
}