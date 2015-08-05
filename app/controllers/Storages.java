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
import views.html.storage.*;

@Security.Authenticated(Secured.class)
public class Storages extends Controller{

    public static Form<Storage> storageForm = form(Storage.class);

    public static Result all() {
        if(Secured.isAdmin()) {
            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    null,Storage.findAll(),storageForm));
        }else{
            return forbidden();
        }

    }

    public static Result index(int id) {
        if(Secured.isAdmin()) {
            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.find.byId(id),Storage.findAll(),storageForm));
        }else{
            return forbidden();
        }

    }

    public static Result newStorage(int id) throws ParseException{
        if(Secured.isAdmin()) {
            DynamicForm filledForm=form().bindFromRequest();
            DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);
            Date now = new Date();

            int s_id=Storage.create(filledForm.get("title"), filledForm.get("description"),"",
                    Restaurant.find.byId(id),now,now);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Создан новый склад:"+s_id+" внутри ресторана:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
            Event event=new Event("STORAGE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            event.save();
            Logger.info(desc);
            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.find.byId(id),Storage.findAll(),storageForm));
        }else{
            return forbidden();
        }

    }

    public static Result addingStoragePage(int id){
        if(Secured.isAdmin()) {
            return ok(add_storage.render(User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.find.byId(id),storageForm));
        }else{
            return forbidden();
        }

    }

    public static Result storagePage(int id){
        if(Secured.isAdmin()) {
            session("storage",id+"");
            return ok(item.render(
                    User.find.where().eq("email", request().username()).findUnique(),Storage.find.byId(id)));
        }else{
            return forbidden();
        }

    }

    public static Result updateStorage(int id) throws ParseException{
        if(Secured.isAdmin()) {
            DynamicForm filledForm=form().bindFromRequest();
            SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);
            Date now = new Date();

            Storage storage=Storage.find.ref(id);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Изменен склад:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ storage.title+"\nОписание:"+ storage.description+"\nСтатус:\nНачало:"+ storage.start_time+"\nКонец:"+ storage.end_time
                    +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

            Event event=new Event("STORAGE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

            Storage.update(id, filledForm.get("title"), filledForm.get("description"),"",now,now);
            event.save();
            Logger.info(desc);

            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    Storage.find.byId(id).restaurant,Storage.findAll(),storageForm));
        }else{
            return forbidden();
        }

    }

    public static Result deleteStorage(int id){
        if(Secured.isAdmin()) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Storage storage=Storage.find.ref(id);

            String desc="Удален склад:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ storage.title+"\nОписание:"+ storage.description+"\nСтатус:\nНачало:"+ storage.start_time+"\nКонец:"+ storage.end_time;

            Event event=new Event("STORAGE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

            Storage.delete(id);
            event.save();
            Logger.info(desc);

            return ok(index.render(
                    User.find.where().eq("email", request().username()).findUnique(),
                    Storage.find.byId(id).restaurant,Storage.findAll(),storageForm));
        }else{
            return forbidden();
        }

    }
}