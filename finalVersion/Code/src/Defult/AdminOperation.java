package Defult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import authenticatedUsers.LoggedInAuthenticatedUser;
import customDatatypes.EvaluationTypes;
import customDatatypes.Weights;
import offerings.CourseOffering;
import offerings.ICourseOffering;
import offerings.OfferingFactory;
import registrar.ModelRegister;
import systemUsers.InstructorModel;
import systemUsers.StudentModel;

public class AdminOperation {
	public String name;
	public String surname;
	public String ID;
	public ModelRegister ModerRegister;
	public Integer option;
	public LoggedInAuthenticatedUser current;
	public Integer nn=null;

	public AdminOperation(LoggedInAuthenticatedUser current){
		this.current=current;
		this.name=current.getName();
		this.surname=current.getSurname();
		this.ID=current.getID();
	}
	public void Excute() throws IOException{
		if(name.equals("qiyuan")&&surname.equals("zhao")&&ID.equals("1234")){
			System.out.println("Please choose what you want to do ... \n");
			while(true){				
				System.out.println(" Load DataFile press 1 \n Register New course press 2 \n given a student permission to enroll course press 3 \n log out press 4 \n ");
				Scanner input = new Scanner(System.in);
				option=input.nextInt();
				if(option==1){
					ReadFile();
				}
				else if(option==3) {
					givePermission();
				}
				else if(option==4){
					return;
				}
				else if(option==2) {
					registerCourse();
				}
				else{
					System.out.println("your enter is invalid !");
				}			
			}
		}
		System.out.println("You are not Admin !! \n");
	}
	private void givePermission() {
		// TODO Auto-generated method stub
		System.out.println("enter stdent ID \n");
		Scanner input3=new Scanner(System.in);
		String id=input3.next();
		if(ModelRegister.getInstance().checkIfUserHasAlreadyBeenCreated(id)==true) {
			System.out.println("Please enter the course ID \n");
			String courseID=input3.next();
			ModelRegister.getInstance().getRegisteredCourse(courseID).getStudentsAllowedToEnroll().add((StudentModel)ModelRegister.getInstance().getRegisteredUser(id));
			((StudentModel) ModelRegister.getInstance().getRegisteredUser(id)).setCoursesAllowed(new ArrayList<ICourseOffering>());
			((StudentModel) ModelRegister.getInstance().getRegisteredUser(id)).getCoursesAllowed().add(ModelRegister.getInstance().getRegisteredCourse(courseID));
			System.out.println("The permission is given !");
			return;
		}
		System.out.println("Student not register yet \n");
		return;
		
	}
	private void registerCourse() {
		// TODO Auto-generated method stub
		System.out.println("enter the course ID you want to add ");
		Scanner input=new Scanner(System.in);
		String courseID=input.next();
		if(ModelRegister.getInstance().checkIfCourseHasAlreadyBeenCreated(courseID)!=true) {
			CourseOffering course=new CourseOffering();
			Scanner input2=new Scanner(System.in);
			System.out.println("please enter course name \n");
			String name=input2.next();
			System.out.println("please enter course ID \n");
			String ID=input2.next();
			System.out.println(System.in);
			String smester=input2.next();
			course.setCourseName(name);
			course.setCourseID(ID);
			course.setSemester(Integer.parseInt(smester));
			course.setInstructor(new ArrayList<InstructorModel>());
			course.setStudentsAllowedToEnroll(new ArrayList<StudentModel>());
			course.setEvaluationStrategies(new HashMap<EvaluationTypes, Weights>());
			course.setStudentsEnrolled(new ArrayList<StudentModel>());
//			and add the new course to the register
			ModelRegister.getInstance().registerCourse(course.getCourseID(), course);
			System.out.println("the new course "+name+" has been registered ");
			return;
		}
		System.out.println("This course already been regisert ");
		return;
		
	}
	private void ReadFile() throws IOException {
		// TODO Auto-generated method stub
		//firstly  we get the instance of all data 
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//		Create an instance of an OfferingFactory
		OfferingFactory factory = new OfferingFactory();
		BufferedReader br = new BufferedReader(new FileReader(new File("note_1.txt")));
//		Use the factory to populate as many instances of courses as many files we've got.
		CourseOffering	courseOffering = factory.createCourseOffering(br);
		br.close();
//		Loading 1 file at a time
		br = new BufferedReader(new FileReader(new File("note_2.txt")));
//		here we have only two files
		courseOffering = factory.createCourseOffering(br);
		br.close();
		return;	
	}
}
