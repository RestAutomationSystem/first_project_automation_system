package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.storage.*;

@Security.Authenticated(Secured.class)
public class Storages extends Controller{

    public static Form<Storage> storageForm=form(Storage.class);

    public static Result index(int id) {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Restaurant.find.byId(id),
                        Storage.findAll(),
                        storageForm
                )
        );
    }


    public static Result newStorage(int id) throws ParseException{
        //Form<Storage> filledForm=form(Storage.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Logger.debug("startDate:"+filledForm.get("start_time"));
        Logger.debug("deadline:"+filledForm.get("end_time"));

        Storage.create(filledForm.get("title"), filledForm.get("description"),"",Restaurant.find.byId(id),sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Restaurant.find.byId(id),
                        Storage.findAll(),
                        storageForm
                )
        );

    }


    public static Result storagePage(int id) {


        session("storage",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Storage.find.byId(id)
                )
        );


    }


    public static Result updateStorage(int id) throws ParseException{
        //Form<Storage> filledForm=form(Storage.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        Storage.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Storage.find.byId(id).restaurant,

                        Storage.findAll(),
                        storageForm
                )
        );

    }

    public static Result deleteStorage(int id) {
        Storage.delete(id);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Storage.find.byId(id).restaurant,

                        Storage.findAll(),
                        storageForm
                )
        );

    }



}