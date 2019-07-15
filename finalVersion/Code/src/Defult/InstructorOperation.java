package Defult;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import authenticatedUsers.LoggedInAuthenticatedUser;
import customDatatypes.Marks;
import offerings.CourseOffering;
import offerings.ICourseOffering;
import offerings.OfferingFactory;
import registrar.ModelRegister;
import systemUsers.InstructorModel;
import systemUsers.StudentModel;

public class InstructorOperation {
	public String name;
	public String surname;
	public String ID;
	public ModelRegister ModerRegister;
	public String option;
	public LoggedInAuthenticatedUser current;
	public static InstructorModel instructor;
	
	public InstructorOperation(LoggedInAuthenticatedUser current){
		this.current=current;
		this.name=current.getName();
		this.surname=current.getSurname();
		this.ID=current.getID();
	}
	public void Excute() throws IOException{
		if(ModelRegister.getInstance().checkIfUserHasAlreadyBeenCreated(this.ID)==true){
			if(ModelRegister.getInstance().getRegisteredUser(this.ID).getName().equals(name)&&
					ModelRegister.getInstance().getRegisteredUser(this.ID).getSurname().equals(surname)){
				//if user login valid we do next or exit system
				System.out.println("Please selete what you want do");
				while(true){
					System.out.println(" press 1 to add mark for a student \n press 2 to Modify a mark for a student \n press 3 to caculate final mark for a student \n press 4 to print class record \n press 5 to log out \n");
					Scanner input=new Scanner(System.in);
					option =input.next();//enter the option you want
					
					if(option.equals("1")){
						System.out.println("Enter the student ID you want to Add mark");
						Scanner cid=new Scanner(System.in);
						String studentID=cid.next();
						//invoke the addCourse method we create below
						addMark(studentID);
					}
					else if(option.equals("2")){
						//invoke the set notification type we create below
						System.out.println("Enter the student ID you want to modify mark");
						Scanner cid=new Scanner(System.in);
						String studentID=cid.next();
						ModifyMark(studentID);
					}
					else if(option.equals("3")){
						//invoke the method print the record of user 
						System.out.println("Enter the student ID you want to caculate final mark");
						Scanner cid=new Scanner(System.in);
						String studentID=cid.next();
						System.out.println("Enter the course ID to get result \n");
						String courseID=cid.next();
						System.out.println(FinalMark(studentID,courseID));
					}
					else if(option.equals("4")){
						printRecord();
					}
					else if(option.equals("5")){
						return;
					}else {
						System.out.println("Invaild enter.");
					}
				}
			}
			else{
				System.out.println("Your information is not correct please try again");
				return;//exit system
			}
		}
		else{
			System.out.println("Your ID is not register yet!!\n press 1 create one\n press 2 Retype again\n");
			Scanner choose = new Scanner(System.in);
			String chooses = choose.next();
			if (chooses.equals("1")) {
				//create
				System.out.println("enter your ID: ");
				Scanner idinput = new Scanner(System.in);
				String instructorID = idinput.next();
				System.out.println("enter your surname: ");
				Scanner surnameinput = new Scanner(System.in);
				String surname = surnameinput.next();
				System.out.println("enter your firstname: ");
				Scanner nameinput = new Scanner(System.in);
				String name = nameinput.next();
				InstructorModel instructor = new InstructorModel();
				instructor.setID(instructorID);
				instructor.setSurname(surname);
				instructor.setName(name);
				ModelRegister.getInstance().registerUser(instructorID, instructor);
				System.out.println("create sucessfully.");
				
			}
			}
			return;//the system stop
		
		
	
	}
	private void printRecord() throws IOException {
		System.out.println("enter courseID which course you want to print: ");
		Scanner courseID = new Scanner(System.in);
		String courseiid = courseID.next();
		boolean checkExit = ModelRegister.getInstance().checkIfCourseHasAlreadyBeenCreated(courseiid);
		if (checkExit == true) {
			for (CourseOffering course : ModelRegister.getInstance().getAllCourses()) {
				System.out.println(course.getCourseName());
				if (course.getCourseID().equals(courseiid)) {
					System.out.println("ID:" + course.getCourseID() + "\nCourse name: " +course.getCourseName() + "\nSemester : " + course.getSemester());
					System.out.println("Students Enrolled\n");
					for(StudentModel student : course.getStudentsEnrolled()){
						System.out.println("Student name : " + student.getName() + "\nStudent surname : " + student.getSurname() + 
								"\nStudent ID : " + student.getID() + "\nStudent EvaluationType : " + student.getEvaluationEntities().get(course) +"\nStudent's final grade : "+Double.toString(FinalMark(student.getID(),course.getCourseID())) +"\n\n");
						
					}
					return;
				}
			}
			return;
		}
		return;
	}
	public double FinalMark(String studentID,String courseID) {
		// TODO Auto-generated method stub
		if (ModelRegister.getInstance().checkIfUserHasAlreadyBeenCreated(studentID) == true) {
			StudentModel student = (StudentModel) ModelRegister.getInstance().getRegisteredUser(studentID);
			List<ICourseOffering> list = student.getCoursesEnrolled();
			for(ICourseOffering course:list){
				if (course.getCourseID().equals(courseID)) {
					CourseOffering TargetCourse = ModelRegister.getInstance().getRegisteredCourse(courseID);
					double studentGrade=TargetCourse.getGrade(studentID);
					return studentGrade;		
				}
			}
		}
		return 0;
		
		
	}
	private void ModifyMark(String studentID) {
		// TODO Auto-generated method stub
		if (ModelRegister.getInstance().checkIfUserHasAlreadyBeenCreated(studentID) == true) {
			System.out.println("enter the course ID: ");
			Scanner coursei=new Scanner(System.in);
			String iid=coursei.next();
			StudentModel student = (StudentModel) ModelRegister.getInstance().getRegisteredUser(studentID);
			List<ICourseOffering> list = student.getCoursesEnrolled();
			for(ICourseOffering course:list){
				if (course.getCourseID().equals(iid)) {
					Marks marklist = new Marks();
					System.out.println("enter type of mark: ");
					Scanner typeofmark = new Scanner(System.in);
					String examorAssginment = typeofmark.next();
					System.out.println("please enter the mark after modify \n");
					Double markk=typeofmark.nextDouble();
					Marks mmm=student.getPerCourseMarks().get(course);
					mmm.initializeIterator();
					while(mmm.hasNext()) {
						Entry mEntry=mmm.getNextEntry();
						if(mEntry.getKey().equals(examorAssginment)){
							//we do the modify
							//cover the previous data directely
							((StudentModel) ModelRegister.getInstance().getRegisteredUser(studentID)).getPerCourseMarks().get(course).addToEvalStrategy(examorAssginment, markk);
							System.out.println("The modify is done \n");
							return;
						}
					}//end of the loop
					System.out.println("do not have " + examorAssginment +"mark.\n");
					return;
				}
			}//end of the for loop
			System.out.println("invaild course id.\n");
			return;
		}
		System.out.println("invaild student id.\n");
		return;
	}
	
	
	private void addMark(String studentID) {
		 //first we check if user has been register
		if(ModelRegister.getInstance().checkIfUserHasAlreadyBeenCreated(studentID)==true){
			//get current student
			StudentModel student=(StudentModel) ModelRegister.getInstance().getRegisteredUser(studentID);
			//++++++++++++++++++++++++++++++++++++++
			System.out.println("Enter the course ID you want add marks");
			Scanner id=new Scanner(System.in);
			String cid=id.next();
			//++++++++++++++++++++++++++++++++++++++
			for(ICourseOffering course:student.getCoursesEnrolled()){
				//student in the list
				if(course.getCourseID().equals(cid)){
					//get the mark list for current student
					Map<ICourseOffering,Marks> marks=student.getPerCourseMarks();
					if(marks==null){
						//create a new precourseMarks object
						student.setPerCourseMarks(new HashMap<ICourseOffering,Marks>());
						Marks mm=new Marks();
						System.out.println("Please enter the types you want to add \n");
						Scanner type=new Scanner(System.in);
						String typee=type.next();
						System.out.println("please enter the marks you want to add \n");
						Double typeM=type.nextDouble();
						mm.addToEvalStrategy(typee, typeM);
						student.getPerCourseMarks().put(course, mm);
						//++++++++++++++++++++++++++++++++++++++
						((StudentModel) ModelRegister.getInstance().getRegisteredUser(studentID)).setPerCourseMarks(new HashMap<ICourseOffering,Marks>());
						((StudentModel) ModelRegister.getInstance().getRegisteredUser(studentID)).getPerCourseMarks().put(course, mm);
						return;
					}
					//if this course's mark already has we just need to add to the hash map
					System.out.println("Please enter the types you want to add \n");
					Scanner type=new Scanner(System.in);
					String typee=type.next();
					if(((StudentModel) ModelRegister.getInstance().getRegisteredUser(studentID)).getPerCourseMarks().get(course).getValueWithKey(typee)!=null){
						System.out.println("This type already has a mark \n");
						return;
					}
					System.out.println("please enter the marks you want to add \n");
					Double typeM=type.nextDouble();
					((StudentModel) ModelRegister.getInstance().getRegisteredUser(studentID)).getPerCourseMarks().get(course).addToEvalStrategy(typee, typeM);
					return;
				}
			}
			System.out.println("Student is not in the course list \n");
			return;
			
		}
		System.out.println("there is no such student \n");
		return;
		
		
		
	}
	
	
	
	
}
