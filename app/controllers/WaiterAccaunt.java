package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class WaiterAccaunt extends Controller {

    public static Result index() {

        return ok(views.html.officiant.accaunt.render());
    }
}
