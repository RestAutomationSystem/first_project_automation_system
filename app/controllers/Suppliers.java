package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.supplier.*;

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
        Logger.debug("startDate:"+filledForm.get("start_time"));
        Logger.debug("deadline:"+filledForm.get("end_time"));

        Supplier.create(filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Supplier.findAll(),
                        supplierForm
                )
        );

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

        Supplier.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Supplier.findAll(),
                        supplierForm
                )
        );

    }

    public static Result deleteSupplier(int id) {
        Supplier.delete(id);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Supplier.findAll(),
                        supplierForm
                )
        );

    }



}