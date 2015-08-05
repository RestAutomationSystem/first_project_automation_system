package controllers;

import models.User;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Context;

public class Secured extends Security.Authenticator{
	
	@Override
	public String getUsername(Context ctx) {
		// TODO Auto-generated method stub
		return ctx.session().get("email");
	}
	
	public String getRole(Context ctx) {
		// TODO Auto-generated method stub
		return ctx.session().get("role");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		// TODO Auto-generated method stub
		return redirect(routes.Application.login());
	}
	
	public static boolean isAdmin() {
	    return User.isAdmin(
	        Context.current().request().username()
	    );
	}

    public static boolean isOficiant() {
        return User.isOficiant(
                Context.current().request().username()
        );
    }

    public static boolean isCashier() {
        return User.isCashier(
                Context.current().request().username()
        );
    }
	

}