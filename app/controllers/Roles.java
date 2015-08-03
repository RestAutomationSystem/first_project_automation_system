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
import views.html.role.*;

@Security.Authenticated(Secured.class)
public class Roles extends Controller{

    public static Form<Role> roleForm=form(Role.class);

    public static Result index() {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Role.findAll(),
                        roleForm
                )
        );
    }


    public static Result newRole() throws ParseException{
        //Form<Role> filledForm=form(Role.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        int r_id=Role.create(filledForm.get("title"), filledForm.get("description"),true,sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String desc="Создана новая роль:"+r_id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
        Event event=new Event("ROLE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        event.save();
        Logger.debug(desc);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Role.findAll(),
                        roleForm
                )
        );

    }




    public static Result rolePage(int id) {


        session("role",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Role.find.byId(id)
                )
        );


    }


    public static Result updateRole(int id) throws ParseException{
        //Form<Role> filledForm=form(Role.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        Role role=Role.find.ref(id);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String desc="Изменена роль:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ role.title+"\nОписание:"+ role.description+"\nСтатус:\nНачало:"+ role.start_time+"\nКонец:"+ role.end_time
                +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");

        Event event=new Event("ROLE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        Role.update(id, filledForm.get("title"), filledForm.get("description"),true,sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
        event.save();
        Logger.debug(desc);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Role.findAll(),
                        roleForm
                )
        );

    }

    public static Result deleteRole(int id) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Role role=Role.find.ref(id);

        String desc="Удалена роль:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ role.title+"\nОписание:"+ role.description+"\nСтатус:\nНачало:"+ role.start_time+"\nКонец:"+ role.end_time;

        Event event=new Event("ROLE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        Role.delete(id);
        event.save();
        Logger.debug(desc);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Role.findAll(),
                        roleForm
                )
        );

    }

}