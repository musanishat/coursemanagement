import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class CourseManagementApp implements Serializable {
	static String username, password;
	static ArrayList<Admin> adminList = new ArrayList<Admin>();
	static ArrayList<Student> studentList = new ArrayList<Student>();
	static Admin adminUser; // This is the current user of the app if the user is an admin
	static Student studentUser; // This is the current user if they are a student
	static AllCourses allCourses;

	public static void main(String[] args) throws IOException {
		importFile(); // See method
		Scanner input = new Scanner(System.in);
		int option = 0;
		boolean isAdmin = isAdmin(); // see method
		System.out.println("Welcome to the Course Registration App");
		if (isAdmin) {// If user is an admin, this if is triggered
			adminOverview(); // See methods
			saveAdmin();
		} else {// If user is a student, this is triggered
			studentManagement(); // See methods
			saveStudent();
		}
		System.out.println("Thank you for using the course management system.");
		exportFile();
	}

	// Serialization
	public static void importFile() {// This method tries to import a serialized file
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("Course File.ser")); // Imports the .ser
																									// file as an
			// ObjectInputStream named in
			allCourses = (AllCourses) in.readObject(); // Creates 3 object using the .ser file: an allCourses class
			adminList = (ArrayList<Admin>) in.readObject(); // A list of all admins registered
			studentList = (ArrayList<Student>) in.readObject(); // A list of all students registered
			System.out.println("Data located. Welcome to the Course Management System.");
			in.close();
		} catch (IOException e) {// If the file is not found then an IO error will be thrown
			createFile(); // If the error is caught, this method runs
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found exception.");
		}
	}

	public static void createFile() {// This creates a serialized file by reading from the csv file of courses
		try {
			System.out.println(
					"Since no saved data was found, class info will now be imported by the Course Management System");
			ArrayList<Course> courseList = new ArrayList<Course>();
			String name, id, instructer, location;
			int maximum, section;
			Scanner inputStream = new Scanner(new File("MyUniversityCourses.csv"));
			inputStream.nextLine(); // Skips first line of csv file with names
			while (inputStream.hasNextLine()) {// IO reader to increment through the csv file
				String line = inputStream.nextLine();
				String[] courseInfo = line.split(","); // Creates an array of strings made up of the first line
				// Separates indices by splitting them with commas
				name = courseInfo[0]; // Each index is manually added to variables
				id = courseInfo[1];
				maximum = Integer.parseInt(courseInfo[2]);
				instructer = courseInfo[5];
				section = Integer.parseInt(courseInfo[6]);
				location = courseInfo[7];
				courseList.add(new Course(name, id, instructer, location, maximum, section)); // Courses are manually
																								// created using Course
																								// constructor
			}
			inputStream.close();
			AllCourses allCourses = new AllCourses(courseList); // Creates an allCourses object using the course list
																// that was just created
			ArrayList<Admin> adminList = new ArrayList<Admin>(); // Creates and empty admin and student list
			ArrayList<Student> studentList = new ArrayList<Student>();
			adminList.add(new Admin("Default", null, "Admin", "Admin001")); // Creates the default admin
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Course File.ser")); // Creates the
																										// .ser file
			out.writeObject(allCourses); // Saves each of these three objects to the ser.
			out.writeObject(adminList);
			out.writeObject(studentList);
			System.out.println("Data successfuly saved!");
			out.close();
			importFile(); // Once the data is saved, runs the importFile() method again to import the
							// three objects
		} catch (FileNotFoundException a) {
			System.out.println("File not found exception.");
		} catch (IOException a) {
			System.out.println("IO exception");
		}
	}

	public static void exportFile() {// When user exits the adjustments they have made to the .set file are saved
										// here
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Course File.ser")); // A new file is
																										// made with the
																										// same name as
																										// the original
																										// .ser
			out.writeObject(allCourses); // All three objects are saved again
			out.writeObject(adminList);
			out.writeObject(studentList);
			System.out.println("Data saved.");
		} catch (FileNotFoundException a) {
			System.out.println("File not found exception.");
		} catch (IOException a) {
			System.out.println("IO exception");
		}
	}

	public static void saveAdmin() {// If user was an admin, their data is saved here
		for (int i = 0; i < adminList.size(); i++) {
			if (adminList.get(i).getUsername().equals(adminUser.getUsername())) {
				adminList.set(i, adminUser); // Index of user is found in the admin list and updated with the new admin
			}
		}
	}

	public static void saveStudent() {// If user was a student, their data is saved here
		for (int i = 0; i < studentList.size(); i++) {
			if (studentList.get(i).getUsername().equals(studentUser.getUsername())) {
				studentList.set(i, studentUser); // Index of user is found in student list and updated with the new
													// student
			}
		}
	}

	// Login methods
	public static boolean isAdmin() {// Prompts a log in and checks if the logged in user is an admin or student
		int userCheck = isValidUsername(); // See method
		while (userCheck == 0) {// Continues until valid password is inputted
			System.out.println("That username is not valid. Please input a valid username.");
			userCheck = isValidUsername();
		}
		System.out.println("That username valid.");
		while (!isValidPassword(userCheck)) {// Once username is validated, checks password for validity. See method
			System.out.println("That password is not valid. Please input a valid password.");
		}
		System.out.println("That password is valid");
		for (int i = 0; i < adminList.size(); i++) {
			if (adminList.get(i).getUsername().equals(username) && adminList.get(i).getPassword().equals(password)) {
				adminUser = adminList.get(i);
				return true;
			}
		}
		for (int i = 0; i < studentList.size(); i++) {
			if (studentList.get(i).getUsername().equals(username)
					&& studentList.get(i).getPassword().equals(password)) {
				studentUser = studentList.get(i);
				return false;
			}
		}
		return false;
	}

	public static int isValidUsername() {// Checks if inputted username is valid
		Scanner input = new Scanner(System.in);
		System.out.println("Input your username.");
		username = input.next();
		for (int i = 0; i < adminList.size(); i++) {
			if (adminList.get(i).getUsername().equals(username)) {
				adminUser = adminList.get(i); // Sets the current user to the admin object associated with the username
												// inputted
				return 1; // If username is in the admin array list, returns a 1
			}
		}
		for (int i = 0; i < studentList.size(); i++) {
			if (studentList.get(i).getUsername().equals(username)) {
				studentUser = studentList.get(i); // Sets the current user to the student object associated with the
													// username inputted
				return 2; // If username is in student array list, returns a 2
			}
		}
		return 0; // If username is not found, returns a 1, meaning username is not valid
	}

	public static boolean isValidPassword(int userCheck) {// Checks to see if password is valid
		// User check returns a different int depending on whether the user is an admin
		// or student
		Scanner input = new Scanner(System.in);
		if (userCheck == 2) {// If the user is a student, the method then checks to see if they have a
								// password yet
			if (studentUser.noPassword()) {
				studentUser.createPassword();
			}
		}
		System.out.println("Input your password.");
		password = input.next();
		if (userCheck == 1) {// Checks if admin's password is correct
			return adminUser.checkPassword(password);
		}
		if (userCheck == 2) {// Checks if user's password is correct
			return studentUser.checkPassword(password);
		}
		return false; // returns true if password is incorrect
	}

	// Menus
	public static void adminOverviewMenu() {// Prints possible options that the user can choose
		System.out.println("Choose an option by inputting a number between 1-3.");
		System.out.println("1. Course Management");
		System.out.println("2. Reports");
		System.out.println("3. Save and exit");
	}

	public static void courseManagementMenu() {
		System.out.println("Choose an option by inputting a number between 1-7.");
		System.out.println("1. Create a new course");
		System.out.println("2. Delete an existing course");
		System.out.println("3. Edit the instructer of an existing course");
		System.out.println("4. Display an existing course");
		System.out.println("5. Register a new student");
		System.out.println("6. Create a new admin");
		System.out.println("7. Change username or password");
		System.out.println("8. Back");
	}

	public static void reportsMenu() {
		System.out.println("Choose an option by inputting a number between 1-7.");
		System.out.println("1. View all courses");
		System.out.println("2. View all full courses");
		System.out.println("3. Create a text file of all full courses");
		System.out.println("4. View students registered in a specific course");
		System.out.println("5. View course list of a specific student");
		System.out.println("6. Sort courses based on number of students");
		System.out.println("7. Back");
	}

	public static void studentMenu() {
		System.out.println("Choose an option by inputting a number between 1-7.");
		System.out.println("1. View all courses");
		System.out.println("2. View all courses that are not full");
		System.out.println("3. Register in a course");
		System.out.println("4. Withdraw from a course");
		System.out.println("5. View all your courses");
		System.out.println("6. Save and exit");
	}

	// Admin Overview methods
	public static void adminOverview() throws IOException {// Menu that lets admin choose course management or reports
		Scanner input = new Scanner(System.in);
		int option = 0;
		while (option != 3) {
			adminOverviewMenu();
			option = input.nextInt();
			if (option == 1) {
				courseManagement();
			}
			if (option == 2) {
				reports();
			}
			if (option > 3) {
				System.out.println("That was an invalid number");
			}
		}
	}

	// Course Management methods
	public static void courseManagement() {// Gives admin a number of options to manage courses
		int option = 0;
		Scanner input = new Scanner(System.in);
		while (option != 8) {
			courseManagementMenu();
			option = input.nextInt();
			if (option == 1) {
				createCourse();
			}
			if (option == 2) {
				deleteCourse();
			}
			if (option == 3) {
				editCourse();
			}
			if (option == 4) {
				displayCourse();
			}
			if (option == 5) {
				addStudent();
			}
			if (option == 6) {
				addAdmin();
			}
			if (option == 7) {
				editAdmin();
			}
			if (option > 8 || option <= 0) {
				System.out.println("That was an invalid number");
			}
		}
	}

	public static void createCourse() {// Manually asks the admin to input data fields to create class
		Scanner input = new Scanner(System.in);
		String name, id, instructer, location;
		int maximum, section;
		System.out.println("What is the name of the new course?");
		name = input.nextLine();
		System.out.println("What is the ID of the new course?");
		id = input.nextLine();
		System.out.println("What is the name of the instructer of the new course?");
		instructer = input.nextLine();
		System.out.println("What is the location of the new course?");
		location = input.nextLine();
		System.out.println("What is the maximum capacity of the new course?");
		maximum = input.nextInt();
		System.out.println("What is the section of the new course?");
		section = input.nextInt();
		Course newCourse = new Course(name, id, instructer, location, maximum, section); // Creates a new course object
																							// using inputted data
		// New course object is used as parameter for addCourse method in allCourses
		// class
		if (allCourses.addCourse(newCourse)) {// if method from allCourses class returns true, the course has been added
			System.out.println("Course successfuly added.");
			newCourse.print();
		} else {
			System.out.println("Course already exists.");
		}
	}

	public static void deleteCourse() {// Gets ID of course and inputs it into the deleteCourse method for allCourses
										// class
		Scanner input = new Scanner(System.in);
		String id;
		System.out.println("What is the ID of the course you want to delete?");
		id = input.nextLine();
		if (allCourses.deleteCourse(id)) {
			System.out.println("Course successfully deleted.");
		} else {
			System.out.println("Course does not exist.");
		}

	}

	public static void editCourse() {// Gets ID of course and inputs it into the editCourse method for allCourses
										// class
		Scanner input = new Scanner(System.in);
		String id;
		System.out.println("What is the ID of the course you want to edit?");
		id = input.nextLine();
		if (allCourses.editCourse(id)) {
			System.out.println("Instructer successfully changed.");
		} else {
			System.out.println("Course does not exist.");
		}
	}

	public static void displayCourse() {// Gets ID of course and inputs it into the displayCourse method for allCourses
										// class
		Scanner input = new Scanner(System.in);
		String id;
		System.out.println("What is the ID of the course you want to display?");
		id = input.nextLine();
		if (allCourses.getCourseIndex(id) >= 0) {
			allCourses.displayCourseInfo(id);
		} else {
			System.out.println("Course does not exist.");
		}
	}

	public static void addStudent() {// Creates a student object and adds it to the student list
		Scanner input = new Scanner(System.in);
		String firstName, lastName;
		System.out.println("What is the new student's first name?");
		firstName = input.next();
		System.out.println("What is the new student's last name?");
		lastName = input.next();
		Student studentEntry = new Student(firstName, lastName, studentList);
		studentList.add(studentEntry);
		System.out.println(studentEntry.getFirstName() + " " + studentEntry.getLastName() + " added successfully.");
		System.out.println("Username: " + studentEntry.getUsername());
	}

	public static void addAdmin() {// Creates an admin object and adds it to the admin list
		Scanner input = new Scanner(System.in);
		String firstName, lastName, username, password;
		System.out.println("What is the new admin's first name?");
		firstName = input.next();
		System.out.println("What is the new admin's last name?");
		lastName = input.next();
		System.out.println("Input a username for the new admin.");
		username = input.next();
		System.out.println("Input a password for the new admin.");
		password = input.next();
		Admin adminEntry = new Admin(firstName, lastName, username, password);
		adminList.add(adminEntry);
		System.out.println(adminEntry.getFirstName() + " " + adminEntry.getLastName() + " added successfully.");
		System.out.println("Username: " + adminEntry.getUsername());
		System.out.println("Password: " + adminEntry.getPassword());
	}

	public static void editAdmin() {// Allows the current admin to change their username or password
		Scanner input = new Scanner(System.in);
		int option = 0;
		while (option != 3) {
			System.out.println("Choose an option by inputting a number between 1-3.");
			System.out.println("1. Edit Username");
			System.out.println("2. Edit Password");
			System.out.println("3. Back");
			option = input.nextInt();
			if (option == 1) {
				adminUser.setUsername();
			}
			if (option == 2) {
				adminUser.setPassword();
			}
			if (option > 3 || option <= 0) {
				System.out.println("That was an invalid number");
			}
		}
	}

	// Reports method
	public static void reports() throws IOException {// Same as course management but for reports related options
		int option = 0;
		Scanner input = new Scanner(System.in);
		while (option != 7) {
			reportsMenu();
			option = input.nextInt();
			if (option == 1) {
				viewAllCourses();
			}
			if (option == 2) {
				viewFullCourses();
			}
			if (option == 3) {
				writeFullCourses();
			}
			if (option == 4) {
				viewStudentListByCourse();
			}
			if (option == 5) {
				viewRegisteredCourses();
			}
			if (option == 6) {
				sortByNumberOfStudents();
			}
			if (option > 7 || option <= 0) {
				System.out.println("That was an invalid number.");
			}
		}
	}

	public static void viewAllCourses() {// Runs the viewAllCourses method from allCourses class
		allCourses.viewAllCourses();
	}

	public static void viewFullCourses() {// Runs the viewFullCourses method from allCourses class
		allCourses.viewFullCourses();
	}

	public static void writeFullCourses() throws IOException {// Writes names full courses to a text file
		ArrayList<String> fullCourses = allCourses.getFullCourses(); // returns an array list of all Course names
		BufferedWriter out = new BufferedWriter(new FileWriter("Full Courses.txt"));// Creates file
		for (int i = 0; i < fullCourses.size(); i++) {
			out.write(fullCourses.get(i)); // Writes course name on file and creates a new line
			out.newLine();
		}
		out.close();
	}

	public static void viewRegisteredCourses() {// Takes student object username and prints the object's class list
		Scanner input = new Scanner(System.in);
		System.out.println("What is username of the student you would like to see the courses of");
		String username = input.nextLine();
		for (int i = 0; i < studentList.size(); i++) {
			if (studentList.get(i).getUsername().equals(username)) {
				studentList.get(i).viewRegisteredCourses();
				return;
			}
		}
		System.out.println("Student not found.");
	}

	public static void viewStudentListByCourse() {// Finds course through ID and prints its student list
		Scanner input = new Scanner(System.in);
		System.out.println("What is ID of the class you would like to see the student list of");
		String id = input.nextLine();
		int index = allCourses.getCourseIndex(id);
		if (!allCourses.displayStudentList(id)) {
			System.out.println("Class does not exist.");
		}
	}

	public static void sortByNumberOfStudents() {// Runs method from allCourses method
		allCourses.sortByNumberOfStudents();
	}

	// Student Management
	public static void studentManagement() {// Student options to manage courses
		int option = 0;
		Scanner input = new Scanner(System.in);
		while (option != 6) {
			studentMenu();
			option = input.nextInt();
			if (option == 1) {
				viewAllCourses();
			}
			if (option == 2) {
				viewOpenCourses();
			}
			if (option == 3) {
				register();
			}
			if (option == 4) {
				withdraw();
			}
			if (option == 5) {
				viewRegisteredCoursesStudent();
			}
			if (option > 6 || option <= 0) {
				System.out.println("That was an invalid number");
			}
		}
	}

	public static void viewOpenCourses() {// Runs method from allCourses class
		allCourses.viewOpenCourses();
	}

	public static void register() {// Asks student for ID of course they want to register in and runs the
									// addStudent method from allCourses class
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the ID of the course you want to register in");
		String id = input.nextLine();
		if (allCourses.addStudent(studentUser, id)) {
			studentUser.addCourse(allCourses.getCourse(allCourses.getCourseIndex(id))); // Also runs the addCourse
																						// method from student class
			System.out.println("Successfuly registered for class.");
		} else {
			System.out.println("Unable to register.");
		}
	}

	public static void withdraw() {// Gets ID of class and removes student from the course and removes course from
									// student's course list
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the ID of the course you want to withdraw from.");
		String id = input.nextLine();
		if (allCourses.deleteStudent(studentUser.getUsername(), id)) {
			studentUser.withdrawCourse(id);
			System.out.println("Successfuly withdrawn from for class.");
		} else {
			System.out.println("Course not found");
		}
	}

	public static void viewRegisteredCoursesStudent() {// Runs the viewRegisteredCourses method from student class
		studentUser.viewRegisteredCourses();
	}

}