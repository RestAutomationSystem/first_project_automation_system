package controllers;

import models.Event;
import models.Place;
import models.RestaurantSection;
import models.Service;
import play.Logger;
import play.Routes;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static play.data.Form.form;

@Security.Authenticated(Secured.class)
public class WaiterMenu extends Controller{


   /* public static Result index(int id) {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Service.find.byId(id),
                        models.Menu.findByService(id),
                        menuForm
                )
        );
    }*/

/*
    public static Result newMenu(int id) throws ParseException{
        //Form<Menu> filledForm=form(Menu.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        int m_id=Menu.create(filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")), Service.find.byId(id));
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String desc="Создан новый меню:"+m_id+" внутри сервиса:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
        Event event=new Event("MENU",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        event.save();
        Logger.info(desc);

        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Service.find.byId(id),
                        Menu.findByService(id),
                        menuForm
                )
        );

    }*/




    /*public static Result menuPage(int id) {


        session("menu",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Menu.find.byId(id)
                )
        );


    }*/


   /* public static Result updateMenu(int id) throws ParseException{
        //Form<Menu> filledForm=form(Menu.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        Service service=Menu.find.byId(id).service;

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        Menu menu=Menu.find.ref(id);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String desc="Изменено меню:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ menu.title+"\nОписание:"+ menu.description+"\nСтатус:\nНачало:"+ menu.start_time+"\nКонец:"+ menu.end_time
                +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

        Event event=new Event("MENU",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

        Menu.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
        event.save();
        Logger.info(desc);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        service,
                        Menu.findByService(service.id),
                        menuForm
                )
        );

    }

    public static Result deleteMenu(int id) {
        Service service=Menu.find.byId(id).service;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Menu menu=Menu.find.ref(id);

        String desc="Удалено меню:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ menu.title+"\nОписание:"+ menu.description+"\nСтатус:\nНачало:"+ menu.start_time+"\nКонец:"+ menu.end_time;

        Event event=new Event("MENU",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

        Menu.delete(id);
        event.save();
        Logger.info(desc);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        service,
                        Menu.findByService(service.id),
                        menuForm
                )
        );

    }
*/


    public static Result restoran(int id ) {
        // Integer answer = Integer.valueOf(num1) + Integer.valueOf(num2);
        //List<RestaurantSection> ids = RestaurantSection.findByRestaurant(id);


        //RestaurantSection newUser= (RestaurantSection) RestaurantSection.findByRestaurant(id);
        // JsonNode personJson = Json.toJson(newUser);
        // Json.toJson(RestaurantSection.findByRestaurant(id));


        return ok(Json.toJson(RestaurantSection.findByRestaurant(id)));
    }

    public static Result service(int id ) {
        // Integer answer = Integer.valueOf(num1) + Integer.valueOf(num2);
        //List<RestaurantSection> ids = RestaurantSection.findByRestaurant(id);


        //RestaurantSection newUser= (RestaurantSection) RestaurantSection.findByRestaurant(id);
        // JsonNode personJson = Json.toJson(newUser);
        // Json.toJson(RestaurantSection.findByRestaurant(id));


        return ok(Json.toJson(Place.findByRestaurant(id)));
    }

    /*public static Result minus(String num1, String num2) {
        Integer answer = Integer.valueOf(num1) - Integer.valueOf(num2);
        return ok(views.html.officiant.ajax_result.render(answer));
    }*/

    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("jsRoutes",
                        //
                        controllers.routes.javascript.NewOrder.restoran(),
                        controllers.routes.javascript.NewOrder.section()
                )
        );
    }



}