package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.food.*;

@Security.Authenticated(Secured.class)
public class Foods extends Controller{

    public static Form<Food> foodForm=form(Food.class);

    public static Result index(int id) {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Storage.find.byId(id),
                        Food.findAll(),
                        foodForm,
                        Supplier.findAll()
                )
        );
    }


    public static Result newFood(int id) throws ParseException{
        //Form<Food> filledForm=form(Food.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Logger.debug("startDate:"+filledForm.get("start_time"));
        Logger.debug("deadline:"+filledForm.get("end_time"));

        Food.create(filledForm.get("title"), filledForm.get("description"),"",Storage.find.byId(id),sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),
                Double.parseDouble(filledForm.get("price")),Supplier.find.byId(Integer.parseInt(filledForm.get("supplier_id"))));


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Storage.find.byId(id),
                        Food.findAll(),
                        foodForm,
                        Supplier.findAll()
                )
        );

    }


    public static Result foodPage(int id) {


        session("food",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Food.find.byId(id),
                        Supplier.findAll()
                )
        );


    }


    public static Result updateFood(int id) throws ParseException{
        //Form<Food> filledForm=form(Food.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        Food.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),Double.parseDouble(filledForm.get("price")),Supplier.find.byId(Integer.parseInt(filledForm.get("supplier_id"))));
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Food.find.byId(id).getStorage(),

                        Food.findAll(),
                        foodForm,
                        Supplier.findAll()
                )
        );

    }

    public static Result deleteFood(int id) {
        Food.delete(id);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Food.find.byId(id).getStorage(),

                        Food.findAll(),
                        foodForm,
                        Supplier.findAll()
                )
        );

    }



}