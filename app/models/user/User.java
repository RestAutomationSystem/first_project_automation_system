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

    @ManyToOne
	public Role role;
	

    public User(String name,String email,String password,Role role){
        this.email=email;
        this.name=name;
        this.password=password;
        this.role=role;
    }
	
	public static boolean isAdmin(String email){
		return (find.byId(email).role.id==1);
	}

    public static boolean isOficiant(String email){
        return (find.byId(email).role.id==2);
    }

    public static boolean isCashier(String email){
        return (find.byId(email).role.id==3);
    }

    public static Finder<String, User> find=new Finder<String, User>(String.class, User.class);

    public static List<User> findAll(){
        return find.all();
    }

    public static List<User> findAllEmployee(){
        return find.where().ne("role.id", 1).findList();
    }

    public static User authenticate(String email,String password){
        return find.where().eq("email", email).eq("password", password).findUnique();
    }

    public static User create(String name,String email,String password,Role role){
        User newUser=new User(name, email, password,role);

        newUser.save();
		return newUser;
    }

    public static void update(String o_email,String name,String password,Role role){
        User user=User.find.byId(o_email);
        user.name=name;
        user.password=password;
        user.role=role;
        user.update();
    }

    public static void deleteUser(String email){
        find.byId(email).delete();
    }
}
