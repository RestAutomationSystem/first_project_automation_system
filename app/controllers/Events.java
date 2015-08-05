package controllers;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 04.08.15
 * Time: 9:19
 * To change this template use File | Settings | File Templates.
 */

import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.event.*;

@Security.Authenticated(Secured.class)
public class Events  extends Controller {

    public static Result index(int page, String sortBy, String order, String filter) {
        if(Secured.isAdmin()) {
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            Event.page(page, 10, "event_time", "desc", filter),
                            sortBy, order, filter
                    )
            );
        }else{
            return forbidden();
        }

    }

}
