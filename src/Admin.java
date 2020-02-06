import java.io.Serializable;
import java.util.*;
public class Admin extends User implements Serializable, AdminInterface{
	public Admin(String firstName, String lastName, String username, String password) {
		super(firstName, lastName);
		super.username=username;
		super.password=password;
		//Password and username are put into the constructor when the admin is created
	}
	public void setUsername() {//Set username and password have been overloaded from the user class
		//Admins can change their username and password in the course management system through this method
		Scanner input= new Scanner(System.in);
		System.out.println("Input new username");
		super.username=input.nextLine();
	}
	public void setPassword() {
		Scanner input= new Scanner(System.in);
		System.out.println("Input new password");
		super.password=input.nextLine();
	}
	
}
