package Defult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import authenticatedUsers.LoggedInAuthenticatedUser;
import customDatatypes.Marks;
import customDatatypes.NotificationTypes;
import offerings.CourseOffering;
import offerings.ICourseOffering;
import offerings.OfferingFactory;
import registrar.ModelRegister;
import systemUsers.IStudentModel;
import systemUsers.StudentModel;
import systemUsers.SystemUserModel;

public class StudentOperation {
	public String name;
	public String surname;
	public String ID;
	public ModelRegister ModerRegister;
	public String option;
	//public Integer option;
	public LoggedInAuthenticatedUser current;
	public static StudentModel student;
	
	public StudentOperation(LoggedInAuthenticatedUser current){
		this.current=current;
		this.name=current.getName();
		this.surname=current.getSurname();
		this.ID=current.getID();
	}
	public void excute(){
		if(ModelRegister.getInstance().checkIfUserHasAlreadyBeenCreated(this.ID)==true){
			if(ModelRegister.getInstance().getRegisteredUser(this.ID).getName().equals(name)&&
					ModelRegister.getInstance().getRegisteredUser(this.ID).getSurname().equals(surname)){
				//if user login valid we do next or exit system
				System.out.println("Please selete what you want do");
				while(true){
					System.out.println(" press 1 to add course \n press 2 to set notification type \n press 3 to print the record of your courses \n press 4 to log out");
					Scanner input=new Scanner(System.in);
					option =input.next();//enter the option you want
					if(option.equals("1")){
						System.out.println("Enter the course ID you want to Add(In upperletter)");
						Scanner cid=new Scanner(System.in);
						String courseID=cid.next();
						//invoke the addCourse method we create below
						addCourse(courseID);
					}
					else if(option.equals("2")){
						//invoke the set notification type we create below
						setNotification();
					}
					else if(option.equals("3")){
						//invoke the method print the record of user 
						printRecord();
					}
					else if(option.equals("4")){
						return;
					}//change
					else{
						System.out.println("invaild enter.");
					}//change
				}
			}
				return;//exit system
			}
		else{
		System.out.println("Your ID is not register yet!!\n press 1 create press 2 return\n");
		Scanner choose = new Scanner(System.in);
		String chooses = choose.next();
		if (chooses.equals("1")) {
			//create 
			System.out.println("enter your ID: ");
			Scanner idinput = new Scanner(System.in);
			String studentID = idinput.next();
			System.out.println("enter your surname: ");
			Scanner surnameinput = new Scanner(System.in);
			String surname = surnameinput.next();
			System.out.println("enter your firstname: ");
			Scanner nameinput = new Scanner(System.in);
			String name = nameinput.next();
			StudentModel student = new StudentModel();
			student.setID(studentID);
			student.setSurname(surname);
			student.setName(name);
			ModelRegister.getInstance().registerUser(studentID, student);
			System.out.println("create sucessfully.");
		}
			return;//the system stop
		}
		
	}
	
	public void addCourse(String CourseID){
		Boolean createTorF = ModelRegister.getInstance().checkIfCourseHasAlreadyBeenCreated(CourseID);
		if(createTorF==false){
			System.out.println("This course not been create yet! \n");
			return;
		}
		student = (StudentModel) ModelRegister.getInstance().getRegisteredUser(ID);
		List<ICourseOffering> courseAllowed=student.getCoursesAllowed();
		List<StudentModel> studentEnrolled=ModelRegister.getInstance().getRegisteredCourse(CourseID).getStudentsEnrolled();
		if (createTorF == false){
			System.out.println("Invaild enter.");
			return;
		}else{
			if(student.getCoursesAllowed() == null) {
				System.out.println("no allowing enter .");
				return;
			}
			for(ICourseOffering course : student.getCoursesAllowed()){
				if(course.getCourseID().equals(CourseID)){
					//maintain two list
					//***********************************************
					//firstly we get the list that students who enrolled in this course
					
					List<StudentModel> studentlist=ModelRegister.getInstance().getRegisteredCourse(CourseID).getStudentsEnrolled();
					//then we add current student in to list
					studentlist.add(student);
					//finally we set the list again
					ModelRegister.getInstance().getRegisteredCourse(CourseID).setStudentsEnrolled(studentlist);
					//**************************************************

					//now we add courses in to student side
					//firstly we get the list store the course we enrolled
					List<ICourseOffering> enrolled=student.getCoursesEnrolled();
					// add new course in to list
					enrolled.add(course);
					//set new list to student
					student.setCoursesEnrolled(enrolled);
					
					System.out.print("The course: "+course.getCourseName()+" has been enrolled ! \n");
					System.out.println(ModelRegister.getInstance().getRegisteredUser("1264").getName());
					return;		
							
						}
					}
			System.out.println("you are not Allowed enroll in this course .");
			return;
			}
			
	}
	
	public void setNotification(){
		student = (StudentModel) ModelRegister.getInstance().getRegisteredUser(ID);
		Scanner input = new Scanner(System.in);
		System.out.println("please selete the notification type: \n inter 1 set email \n enter 2 set Cellphone \n enter 3 set PIGEON_POST \n");
		Integer n;
		n=input.nextInt();
		if(n==1){
			NotificationTypes type = NotificationTypes.EMAIL;
			student.setNotificationType(type);
			System.out.println("you set notification by e-mail \n");
			return;
		}
		else if(n==2){
			NotificationTypes type = NotificationTypes.CELLPHONE;
			student.setNotificationType(type);
			System.out.println("you set notification by Phone \n");
			return;
		}
		else if(n==3){
			NotificationTypes type = NotificationTypes.PIGEON_POST;
			student.setNotificationType(type);
			System.out.println("you set notification by Pigeon_post \n");
			return;
		}
		else{
			System.out.println("Invalide enter!");
			return;
		}
	}
	
	public void printRecord(){
		student = (StudentModel) ModelRegister.getInstance().getRegisteredUser(ID);
		System.out.println("Student Name: "+student.getName()+"\n"+
	"student Surname: "+student.getSurname()+"\n"+"student ID: "+student.getID()+"\n");
		System.out.println("enter the course ID you want get record");
		Scanner input2=new Scanner(System.in);
		String n=input2.nextLine();	
		for(ICourseOffering course:student.getCoursesEnrolled()){
			if(course.getCourseID().equals(n)){
				Marks mm=student.getPerCourseMarks().get(course);
				mm.initializeIterator();
				while(mm.hasNext()){
					Entry mmm=mm.getNextEntry();
					System.out.println("type: "+mmm.getKey()+" marks: "+mmm.getValue());
				}
				System.out.println("The final grade of this course is: "+((CourseOffering) course).getGrade(student.getID()));
				return;
			}
		}
		System.out.println("This course not enrolled \n");
		return;
	}
	
}


























