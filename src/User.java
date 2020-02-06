import java.io.Serializable;

public abstract class User implements Serializable{
	String username, password, firstName, lastName;
	public User(String firstName, String lastName) {
		this.firstName= firstName;
		this.lastName=lastName;
		//Constructor does not include username and password because admin and user both have different ways of creating them
	}
	//Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public boolean checkPassword(String password) {//Checks to see if an inputted password is the same as the user's password
		if(this.password.equals(password)) {
			return true;
		}
		return false;
	}
	
}
