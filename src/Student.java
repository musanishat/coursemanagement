import java.util.*;
public class Student extends User implements StudentInterface {
	ArrayList<Course> courseList;
	public Student(String firstName, String lastName, ArrayList<Student> studentList ) {
		super(firstName, lastName);
		//usernames are created by getting the initials of the first and the last names of the students
		super.username= this.firstName.substring(0,1).toLowerCase() + this.lastName.substring(0,1).toLowerCase();
		int repeats=0;
		super.password="default";
		//Since students select their own password, it is set to default when the student is first constructed and then they 
		//are asked to change it themselves when they log in
		for(int i=0; i<studentList.size(); i++) {
			//Everytime a student is created they also receive the student list from the main class
			if(studentList.get(i).getUsername().equals(username)) {
				//This is used to check if the username already exists
				repeats++;
				//if it does, the variable repeats increments for each use of the username
			}
		}
		if(repeats>0) {
			//This adds the number of repeats to the end of the username, insuring that each new username is unique
			username+=repeats;
		}
		courseList= new ArrayList<Course>();
	}
	@Override
	public void printStudent() {
		System.out.println(firstName+" " +lastName);
		System.out.println("Username: " +username);
		System.out.println("Password: " +password);
	}
	@Override
	public void printStudentName() {
		System.out.println(firstName+" " +lastName);
	}
	@Override
	public boolean noPassword() {//This method runs whenever a student logs in to see if they have a password yet
		if(password.equals("default")) {
			return true;
		}
		return false;
	}
	@Override
	public void createPassword() {//If noPassword() returns true, students are then prompted to create a password
		Scanner input= new Scanner(System.in);
		System.out.println("This is your first time logging in, please input a password");
		password= input.nextLine();
		System.out.println("Your password is " + password + ". Don't forget it!");
	}
	@Override
	public void addCourse(Course newCourse) {//Adds course to array list of courses
		courseList.add(newCourse);
	}
	@Override
	public void withdrawCourse(String id) {//Finds course in array list of courses and deletes it
		for(int i=0; i<courseList.size(); i++) {
			if(courseList.get(i).getId().equals(id)) {
				courseList.remove(i);
			}
		}
	}
	@Override
	public void viewRegisteredCourses() {//Goes through the array list of courses and prints each course
		System.out.println(firstName+ " " + lastName);
		for(int i=0; i<courseList.size(); i++) {
			System.out.println("#"+ (i+1));
			courseList.get(i).print();
		}
	}
}
