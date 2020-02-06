import java.io.Serializable;
import java.util.*;
@SuppressWarnings("serial")
public class Course implements java.lang.Comparable<Course>, Serializable{
	String name, id, instructor, location, studentNames;
	int maximum, section; 
	ArrayList<Student> studentList;
	public Course(String name, String id, String instructor, String location, int maximum, int section) {
		this.name= name;
		this.id= id;
		this.instructor= instructor;
		this.location= location;
		this.maximum= maximum;
		this.section= section;
		studentList= new ArrayList<Student>(); //The student list will be empty at the start since no students have registered yet
	}
	public String getStudentNames() {
		return studentNames;
	}

	public void setStudentNames(String studentNames) {
		this.studentNames = studentNames;
	}

	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructer(String instructor) {
		this.instructor = instructor;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public int getStudents() {
		return studentList.size();
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public void print() {
		System.out.println(id + " - " + name + ": "+ section);
		System.out.println("________");
		System.out.println("Professor "+ instructor);
		System.out.println("Max Capacity: " + maximum);
		System.out.println("Number of students: " + studentList.size());
		System.out.println("Location: " + location);
		System.out.println();
	}
	public boolean isFull() {//Checks if number of students = maximum capacity of students to see if course is full
		if (maximum==studentList.size()){
			return true;
		}
		return false;
	}
	public boolean isSame(Course x) {//Checks to see if a course with that name already exists
		if(name.equals(x.getName())) {
			return true;
		}
		return false;
	}
	public boolean addStudent(Student newStudent) {//Adds new student to course
		for(int i=0; i<studentList.size();i++) {
			if (newStudent.equals(studentList.get(i)) || isFull()){//if the student is already in the course or the course is full
				//then the student is not added
				return false;
			}
		}
		studentList.add(newStudent);
		return true;
		
	}
	public boolean deleteStudent(String username) {//Deletes student from course
		for(int i=0; i<studentList.size(); i++) {
			if(studentList.get(i).getUsername().equals(username)) {//If student is found, they are removed
				studentList.remove(i);
				return true;
			}
		}
		return false;
	}
	public void printStudentList() {//Loops through list of students and prints them out
		for(int i=0; i<studentList.size(); i++) {
			System.out.print((i+1)+") ");
			studentList.get(i).printStudentName();
		}
	}
	@Override
	public int compareTo(Course x) {//Overrides the compareTo method from the comparable interface
		//Compares courses by number of students sort courses
		return Integer.compare(studentList.size(), x.getStudents());
	}
}
