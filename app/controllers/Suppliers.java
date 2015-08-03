package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import java.util.Date;

import static play.data.Form.*;
import views.html.supplier.*;
@Security.Authenticated(Secured.class)
public class Suppliers extends Controller{

    public static Form<Supplier> supplierForm=form(Supplier.class);

    public static Result index() {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Supplier.findAll(),
                        supplierForm
                )
        );
    }


    public static Result newSupplier() throws ParseException{
        //Form<Supplier> filledForm=form(Supplier.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        int s_id=Supplier.create(filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String desc="Создан новый поставщик:"+s_id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
        Event event=new Event("SUPPLIER",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        event.save();
        Logger.debug(desc);

        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Supplier.findAll(),
                        supplierForm
                )
        );

    }

    public static Result addingSupplierPage(){
        return ok(add_supplier.render(User.find.where().eq("email", request().username()).findUnique(),
                supplierForm));
    }

    public static Result supplierPage(int id) {


        session("supplier",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Supplier.find.byId(id)
                )
        );


    }


    public static Result updateSupplier(int id) throws ParseException{
        //Form<Supplier> filledForm=form(Supplier.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Supplier supplier=Supplier.find.ref(id);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String desc="Изменен поставщик:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ supplier.title+"\nОписание:"+ supplier.description+"\nСтатус:\nНачало:"+ supplier.start_time+"\nКонец:"+ supplier.end_time
                +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

        Event event=new Event("SUPPLIER",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        Supplier.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
        event.save();
        Logger.debug(desc);

        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Supplier.findAll(),
                        supplierForm
                )
        );

    }

    public static Result deleteSupplier(int id) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Supplier supplier=Supplier.find.ref(id);

        String desc="Удален поставщик:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ supplier.title+"\nОписание:"+ supplier.description+"\nСтатус:\nНачало:"+ supplier.start_time+"\nКонец:"+ supplier.end_time;

        Event event=new Event("SUPPLIER",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

        Supplier.delete(id);
        event.save();
        Logger.debug(desc);


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Supplier.findAll(),
                        supplierForm
                )
        );

    }



}