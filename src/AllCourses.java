import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AllCourses implements Serializable {
	ArrayList<Course> courseList;
	public AllCourses(ArrayList<Course> courseList) {
		this.courseList= courseList;
	}
	public Course getCourse(int index) {//Returns the inputted index from the list of courses
		return courseList.get(index);
	}
	public void viewAllCourses() {//Loops through array list and prints all courses
		for(int i=0; i<courseList.size(); i++) {
			courseList.get(i).print();
		}
	}
	public void viewFullCourses() {//Prints only courses that are full
		for(int i=0; i<courseList.size(); i++) {
			if(courseList.get(i).isFull()) {
				courseList.get(i).print();
			}	
		}
	}
	public void viewOpenCourses() {//Prints only courses that aren't full
		for(int i=0; i<courseList.size(); i++) {
			if(!courseList.get(i).isFull()) {
				courseList.get(i).print();
			}	
		}
	}
	public void sortByNumberOfStudents() {//Uses the compareTo method in Course class to sort by number of students
		Collections.sort(courseList);
	}
	public int getCourseIndex(String id) {//Returns the index of a course in the array list using its ID to search for it
		for(int i=0; i<courseList.size(); i++) {
			if (courseList.get(i).getId().equals(id)){
				return i;
			}
		}
		return -1; //Returns a -1 if course is not found
	}
	public boolean addCourse(Course newCourse) {//Adds course to array list of courses
		for(int i=0; i<courseList.size(); i++) {
			if (courseList.get(i).isSame(newCourse)){//Does not add course if a course with the same name exists
				return false;
			}
		}
		courseList.add(newCourse);
		return true;
	}
	public boolean deleteCourse(String id) {//Deletes course by searching for its ID
		int index= getCourseIndex(id);
		if(index>=0) {
			courseList.remove(index);
			return true;
		}
		return false;
		
	}
	public boolean editCourse(String id) {//Edits course by searching its ID and prompting the user to enter a new name
		Scanner input= new Scanner(System.in);
		int index=getCourseIndex(id);
		System.out.println("What is the new professor's name");
		String instructer= input.nextLine();
		if(index>=0) {
			courseList.get(index).setInstructer(instructer);;
			return true;
		}
		return false;
		
	}
	public void displayCourseInfo(String id) {//Prints the info for a specific course
		int index= getCourseIndex(id);
		courseList.get(index).print();
	}
	public boolean displayStudentList(String id) {//Displays the student list of a specific course
		int index= getCourseIndex(id);
		if(index>=0) {
			courseList.get(index).printStudentList();;
			return true;
		}
		return false;
	}
	public boolean addStudent(Student newStudent, String id) {//Adds student to student list of a specific course
		int index= getCourseIndex(id);
		if(index>=0) {
			return courseList.get(index).addStudent(newStudent);
		}
		return false;
	}
	public boolean deleteStudent(String username, String id) {//Deletes student from a student list of a specific course
		int index= getCourseIndex(id);
		if(index>=0) {
			return courseList.get(index).deleteStudent(username);
		}
		return false;
	}
	public ArrayList<String> getFullCourses(){ //returns and array list of all full courses
		ArrayList<String> fullCourses= new ArrayList<String>();
		for(int i=0; i<courseList.size();i++) {
			if(courseList.get(i).isFull()) {
				fullCourses.add(courseList.get(i).getName());
			}
		}	
		return fullCourses;
	}
}
