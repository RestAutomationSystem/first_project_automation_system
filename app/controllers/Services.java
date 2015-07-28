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
import views.html.service.*;

@Security.Authenticated(Secured.class)
public class Services extends Controller{

    public static Form<Service> serviceForm=form(Service.class);

    public static Result index() {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Service.findAll(),
                        serviceForm
                )
        );
    }


    public static Result newService() throws ParseException{
        //Form<Service> filledForm=form(Service.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Logger.debug("startDate:"+filledForm.get("start_time"));
        Logger.debug("deadline:"+filledForm.get("end_time"));

        Service.create(filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Service.findAll(),
                        serviceForm
                )
        );

    }




    public static Result servicePage(int id) {


        session("service",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Service.find.byId(id)
                )
        );


    }


    public static Result updateService(int id) throws ParseException{
        //Form<Service> filledForm=form(Service.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        Service.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Service.findAll(),
                        serviceForm
                )
        );

    }

    public static Result deleteService(int id) {
        Service.delete(id);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Service.findAll(),
                        serviceForm
                )
        );

    }



    public static Result serviceMenus(int id) {


        session("menu",id+"");
        return ok(
                menus.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Service.find.byId(id),
                        Menu.findAll(),
                        Service.find.byId(id).menus
                )
        );


    }


    public static Result setMenus(int id) throws ParseException{
        //Form<Service> filledForm=form(Service.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        Logger.debug("menuList:"+filledForm.get());

        Map<String,String[]> keys = request().body().asFormUrlEncoded();
        Service service= Service.find.byId(id);
        service.menus=new ArrayList<Menu>();

        for (Map.Entry<String, String[]> entry : keys.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            for(int i=0;i<entry.getValue().length;i++){
                Logger.debug("menu:"+entry.getValue()[i]);
                service.menus.add(Menu.find.byId(Integer.parseInt(entry.getValue()[i])));
            }
        }

        service.update();


        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Service.find.byId(id)
                )
        );

    }



}