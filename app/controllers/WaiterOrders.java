package controllers;

import play.mvc.Controller;
import play.mvc.Result;


public class WaiterOrders extends Controller {

    public static Result index() {

        return ok(views.html.officiant.waitorders.render());
    }
}
