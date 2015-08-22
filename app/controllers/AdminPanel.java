package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.index;

@Security.Authenticated(Secured.class)
public class AdminPanel extends Controller {

    public static Result index() {
        if(Secured.isAdmin()) {
        return ok(index.render(User.find.where().eq("email", request().username()).findUnique()));
        }else{


            return ok(views.html.officiant.mainpage.render(session("email"),"00:00"));
        }
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(routes.Application.login());
    }
}
