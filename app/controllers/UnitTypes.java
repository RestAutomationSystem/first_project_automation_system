package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        if(Secured.isAdmin()) {
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            UnitType.findAll(),
                            unitTypeForm
                    )
            );
        }else{
            return forbidden();
        }


    }


    public static Result newUnitType() throws ParseException{
        if(Secured.isAdmin()) {
            //Form<UnitType> filledForm=form(UnitType.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();
            DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);

            int u_id=UnitType.create(filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),filledForm.get("amount"));

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Создана новая единица измерения:"+u_id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
            Event event=new Event("UNIT_TYPE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            event.save();
            Logger.debug(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            UnitType.findAll(),
                            unitTypeForm
                    )
            );
        }else{
            return forbidden();
        }


    }


    public static Result addingUnitTypePage(){
        if(Secured.isAdmin()) {
            return ok(add_unitType.render(User.find.where().eq("email", request().username()).findUnique(),
                    unitTypeForm));
        }else{
            return forbidden();
        }

    }

    public static Result unitTypePage(int id) {
        if(Secured.isAdmin()) {
            session("unitType",id+"");
            return ok(
                    item.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            UnitType.find.byId(id)
                    )
            );
        }else{
            return forbidden();
        }





    }


    public static Result updateUnitType(int id) throws ParseException{
        if(Secured.isAdmin()) {
            //Form<UnitType> filledForm=form(UnitType.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();

            SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);


            UnitType unitType=UnitType.find.ref(id);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Изменена единица измерения:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ unitType.title+"\nОписание:"+ unitType.description+"\nСтатус:\nНачало:"+ unitType.start_time+"\nКонец:"+ unitType.end_time
                    +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

            Event event=new Event("UNIT_TYPE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            UnitType.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),filledForm.get("amount"));
            event.save();
            Logger.debug(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            UnitType.findAll(),
                            unitTypeForm
                    )
            );
        }else{
            return forbidden();
        }


    }

    public static Result deleteUnitType(int id) {
        if(Secured.isAdmin()) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            UnitType unitType=UnitType.find.ref(id);

            String desc="Удалена единица измерения:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ unitType.title+"\nОписание:"+ unitType.description+"\nСтатус:\nНачало:"+ unitType.start_time+"\nКонец:"+ unitType.end_time;

            Event event=new Event("UNIT_TYPE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            UnitType.delete(id);
            event.save();
            Logger.debug(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            UnitType.findAll(),
                            unitTypeForm
                    )
            );
        }else{
            return forbidden();
        }


    }

}