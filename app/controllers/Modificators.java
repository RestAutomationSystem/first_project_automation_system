package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Map;

import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.modificator.*;

@Security.Authenticated(Secured.class)
public class Modificators extends Controller{

    public static Form<Modificator> modificatorForm=form(Modificator.class);

    public static Result index(int id) {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Item.find.byId(id),
                        Modificator.findByItem(id),
                        modificatorForm,
                        UnitType.findAll()
                )
        );
    }


    public static Result newModificator(int id) throws ParseException{
        //Form<Modificator> filledForm=form(Modificator.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Logger.debug("startDate:"+filledForm.get("start_time"));
        Logger.debug("deadline:"+filledForm.get("end_time"));
        UnitType ut=UnitType.find.byId(Integer.parseInt(filledForm.get("unit_type")));
        Modificator.create(filledForm.get("title"), filledForm.get("description"),"","",Double.parseDouble(filledForm.get("price_original")),Double.parseDouble(filledForm.get("price_for_sale")),sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),ut,Item.find.byId(id));


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Item.find.byId(id),
                        Modificator.findByItem(id),
                        modificatorForm,
                        UnitType.findAll()
                )
        );

    }




    public static Result modificatorPage(int id) {


        session("modificator",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Modificator.find.byId(id),
                        UnitType.findAll()
                )
        );


    }


    public static Result updateModificator(int id) throws ParseException{
        //Form<Modificator> filledForm=form(Modificator.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        Item item=Modificator.find.byId(id).item;

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        UnitType ut=UnitType.find.byId(Integer.parseInt(filledForm.get("unit_type")));
        Modificator.update(id, filledForm.get("title"), filledForm.get("description"),"","",Double.parseDouble(filledForm.get("price_original")),Double.parseDouble(filledForm.get("price_for_sale")),sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),ut);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        item,
                        Modificator.findByItem(item.id),
                        modificatorForm,
                        UnitType.findAll()
                )
        );

    }

    public static Result deleteModificator(int id) {
        Item item=Modificator.find.byId(id).item;

        Modificator.delete(id);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        item,
                        Modificator.findByItem(item.id),
                        modificatorForm,
                        UnitType.findAll()
                )
        );

    }


}