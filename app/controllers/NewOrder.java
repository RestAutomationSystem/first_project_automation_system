package controllers;

import models.*;

import models.Category;
import models.Item;
import models.Menu;
import models.OrderType;
import models.Place;
import models.Restaurant;
import models.RestaurantSection;
import models.Service;
import play.Routes;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import static play.data.Form.form;

public class NewOrder extends Controller {

   /* public static Result index() {

        return ok(neworder.render());
    }*/


    public static Result oredertype(){

        List<OrderType> gcet = OrderType.find.all();
        List<Restaurant> restaurantses = Restaurant.find.all();
        List<RestaurantSection> section = RestaurantSection.find.all();
        List<models.Service>  service = Service.find.all();
       List<Place> places = Place.find.all();
        List<Menu> menu = Menu.find.all();
        List<Category> category = Category.find.all();
        List<Item> item = Item.find.all();
        List<models.Modificator> modificators = models.Modificator.find.all();




        return ok(views.html.officiant.neworder.render(gcet,restaurantses,section,places,service,menu,category,item,modificators));
    }


    public static Result oredertypesubmit() {
        Form<models.Order> regForm = form(models.Order.class).bindFromRequest();
        if(regForm.hasErrors()){
            return ok((byte[]) null);
        }
        else{
          //  UserTest newUserTest=UserTest.create(regForm.get().name,regForm.get().email,regForm.get().password);
          //  JsonNode personJson = Json.toJson(newUserTest);
          //  Logger.debug("UserTest created: " + regForm.get().name + " " + regForm.get().email);
            //return ok(personJson);

            //return ok( registerSuccess.render( ));
        }

        return null;
    }


    /*public static Result plus(String num1, String num2) {
        Integer answer = Integer.valueOf(num1) + Integer.valueOf(num2);
        return ok(views.html.officiant.ajax_result.render(answer));
    }*/

    public static Result restoran(int id ) {
       // Integer answer = Integer.valueOf(num1) + Integer.valueOf(num2);
        //List<RestaurantSection> ids = RestaurantSection.findByRestaurant(id);


        //RestaurantSection newUser= (RestaurantSection) RestaurantSection.findByRestaurant(id);
       // JsonNode personJson = Json.toJson(newUser);
       // Json.toJson(RestaurantSection.findByRestaurant(id));


        return ok(Json.toJson(RestaurantSection.findByRestaurant(id)));
    }

    public static Result section(int id ) {
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
