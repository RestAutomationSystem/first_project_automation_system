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
import views.html.unitType.*;

@Security.Authenticated(Secured.class)
public class UnitTypes extends Controller{

    public static Form<UnitType> unitTypeForm=form(UnitType.class);

    public static Result index() {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        UnitType.findAll(),
                        unitTypeForm
                )
        );
    }


    public static Result newUnitType() throws ParseException{
        //Form<UnitType> filledForm=form(UnitType.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Logger.debug("startDate:"+filledForm.get("start_time"));
        Logger.debug("deadline:"+filledForm.get("end_time"));

        UnitType.create(filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),filledForm.get("amount"));


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        UnitType.findAll(),
                        unitTypeForm
                )
        );

    }




    public static Result unitTypePage(int id) {


        session("unitType",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        UnitType.find.byId(id)
                )
        );


    }


    public static Result updateUnitType(int id) throws ParseException{
        //Form<UnitType> filledForm=form(UnitType.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        UnitType.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),filledForm.get("amount"));
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        UnitType.findAll(),
                        unitTypeForm
                )
        );

    }

    public static Result deleteUnitType(int id) {
        UnitType.delete(id);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        UnitType.findAll(),
                        unitTypeForm
                )
        );

    }

}