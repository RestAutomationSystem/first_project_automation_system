package models;

import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class User {
   
    public interface All {}
    public interface Step1{}    
	public interface Step2{}    

    @Required(groups = {All.class, Step1.class})
    @MinLength(value = 4, groups = {All.class, Step1.class})
    public String username;

    
    @Required(groups = {All.class, Step1.class})
    @MinLength(value = 6, groups = {All.class, Step1.class})
    public String password;

    
    public User() {}
    
    public User(String username,  String password) {
        this.username = username;
        this.password = password;
    }
    

    
}