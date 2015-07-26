package models;

/**
 * Created with IntelliJ IDEA.
 * User: SAMSUNG-X120
 * Date: 19.07.15
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Enums.RoleTypes;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class User extends Model{

    @Id
    @Column(length=40, nullable=false)
    public String email;

    public String password;
    public String name;
    
	public int role;
	

    public User(String name,String email,String password){
        this.email=email;
        this.name=name;
        this.password=password;
        this.role=2;
    }
	
	public static boolean isAdmin(String email){
		return (find.byId(email).role==1);
	}

    public static Finder<String, User> find=new Finder<String, User>(String.class, User.class);

    public static List<User> findAll(){
        return find.all();
    }

    public static User authenticate(String email,String password){
        return find.where().eq("email", email).eq("password", password).findUnique();
    }

    public static User create(String name,String email,String password){
        User newUser=new User(name, email, password);

        newUser.save();
		return newUser;
    }
}
