package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.*;

@Security.Authenticated(Secured.class)
public class AdminPanel extends Controller {

    public static Result index() {
        if(Secured.isAdmin()) {
        return ok(index.render(User.find.where().eq("email", request().username()).findUnique()));
        }else{
            if(Secured.isOficiant())
                return ok(oficiant.render(User.find.where().eq("email", request().username()).findUnique()));
            else if(Secured.isCashier())
                return ok(cashier.render(User.find.where().eq("email", request().username()).findUnique()));
            else
                return forbidden();
        }
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(routes.Application.login());
    }
}
