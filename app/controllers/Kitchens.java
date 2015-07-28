package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.kitchen.*;

@Security.Authenticated(Secured.class)
public class Kitchens extends Controller{

    public static Form<Kitchen> kitchenForm=form(Kitchen.class);

    public static Result index(int id) {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Restaurant.find.byId(id),
                        Kitchen.findAll(),
                        kitchenForm
                )
        );
    }


    public static Result newKitchen(int id) throws ParseException{
        //Form<Kitchen> filledForm=form(Kitchen.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Logger.debug("startDate:"+filledForm.get("start_time"));
        Logger.debug("deadline:"+filledForm.get("end_time"));

        Kitchen.create(filledForm.get("title"), filledForm.get("description"),"",Restaurant.find.byId(id),sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Restaurant.find.byId(id),
                        Kitchen.findAll(),
                        kitchenForm
                )
        );

    }


    public static Result kitchenPage(int id) {


        session("kitchen",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Kitchen.find.byId(id)
                )
        );


    }


    public static Result updateKitchen(int id) throws ParseException{
        //Form<Kitchen> filledForm=form(Kitchen.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        Kitchen.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Kitchen.find.byId(id).restaurant,

                        Kitchen.findAll(),
                        kitchenForm
                )
        );

    }

    public static Result deleteKitchen(int id) {
        Kitchen.delete(id);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Kitchen.find.byId(id).restaurant,

                        Kitchen.findAll(),
                        kitchenForm
                )
        );

    }



}