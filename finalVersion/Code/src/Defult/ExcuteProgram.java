package Defult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import authenticationServer.AuthenticationToken;
import customDatatypes.EvaluationTypes;
import customDatatypes.Weights;
import loggedInUserFactory.LoggedInUserFactory;
import offerings.CourseOffering;
import offerings.OfferingFactory;
import authenticatedUsers.LoggedInAuthenticatedUser;
import registrar.ModelRegister;
import systemUsers.InstructorModel;
import systemUsers.StudentModel;

public class ExcuteProgram {
	public static String types;
	public static Integer TokenID = null;
	public static Integer sessionID = null;
	public static AuthenticationToken Token;
	public static LoggedInUserFactory login;
	public static LoggedInAuthenticatedUser logT;//return loginxxx
	private static Integer status=0;
	
	public static void main(String[] args) throws IOException{

		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		while(true){
		 
		 if(status==1){
		 System.out.println("Please selete your Login Type");
		 Scanner type=new Scanner(System.in);
	     types = type.next();
	     Token =new AuthenticationToken();
	     Token.setSessionID(sessionID);
	     Token.setTokenID(TokenID);
	     Token.setUserType(types);
	     
	     //now check the three types login !
	     if(types.equals("Admin")){
	    	 login = new LoggedInUserFactory();
		     logT= login.createAuthenticatedUser(Token);
		     System.out.println(logT.getAuthenticationToken().getUserType());
	    	 //put admin code here
	    	 new AdminOperation(logT).Excute();
	    	 System.out.println("Please enter you ID to change system status \n");
	    	 Scanner iii=new Scanner(System.in);
	    	 //change
	    	 String stringInput = iii.next();
	    	 if (stringInput.equals("1234")){
	    		 int stt = Integer.parseInt(stringInput);
	    		 System.out.println("Set the system status: 1 is start, 0 is stop !");
		    	 Scanner ii=new Scanner(System.in);
		    	 Integer st=ii.nextInt();
		    	 if(st==1){
		    		 status=1;
		    		 System.out.println("what do you want to do...\n");
		    		 //
		    		 System.out.println("1 is adding course\n 2 adding instructor \n3 log out");
		    		 Scanner chooses = new Scanner(System.in);
		    		 String choose = chooses.next();
		    		 if (choose.equals("1")) {
		    			 //adding course
		    			 System.out.println("enter course ID you want to add: ");
		    			 Scanner courseIDinput = new Scanner(System.in);
		    			 String courseID = courseIDinput.next();
		    			 if (ModelRegister.getInstance().checkIfCourseHasAlreadyBeenCreated(courseID) == false) {
		    				 CourseOffering course = new CourseOffering();
		    				 System.out.println("enter course name: ");
		    				 Scanner nameinput = new Scanner(System.in);
		    				 String courseName = nameinput.next();
		    				 course.setCourseID(courseID);
		    				 course.setCourseName(courseName);
		    				System.out.println("enter course semester:");
		    				 Scanner seminput = new Scanner(System.in);
		    				 Integer sem = seminput.nextInt();
		    				 course.setSemester(sem);
		    				 List<StudentModel> studentList = null;
		    				 while(true) {
		    					 System.out.println("enter studentID who allows enrolled(press 1 finish):");
		    					 Scanner studentIDinput = new Scanner(System.in);
		    					 String studentID = studentIDinput.next();
		    					 if (studentID.equals("1")) {
		    						 break;
		    					 }else if (ModelRegister.getInstance().checkIfUserHasAlreadyBeenCreated(studentID) == true) {
		    						 studentList.add((StudentModel)ModelRegister.getInstance().getRegisteredUser(studentID));
		    					 }else {
		    						 continue;
		    					 }
		    				 }
		    				 course.setStudentsAllowedToEnroll(studentList);
		    				 while(true) {
		    				 System.out.println("enter course instructorID: ");
		    				 Scanner instructorinput = new Scanner(System.in);
		    				 String instructor = instructorinput.next();
		    				 if (ModelRegister.getInstance().checkIfUserHasAlreadyBeenCreated(instructor)==true) {
		    					 course.addInstructor((InstructorModel)ModelRegister.getInstance().getRegisteredUser(instructor));
		    					 break;
		    				 }
		    				 }//end the loop
		    				 
		    			 }else {
		    				 System.out.println("this course ID already exit.");
		    			 }
		    		 }else if (choose.equals("2")) {
		    			 System.out.println("enter the courseID you want to add instructor: ");
		    			 Scanner courseIDinput = new Scanner(System.in);
		    			 String courseID = courseIDinput.next();
		    			 if (ModelRegister.getInstance().checkIfCourseHasAlreadyBeenCreated(courseID) == true) {
		    				 System.out.println("enter the instrutorID : ");
		    				 Scanner instructorIDinput = new Scanner(System.in);
		    				 String instructorID = instructorIDinput.next();
		    				 if (ModelRegister.getInstance().checkIfUserHasAlreadyBeenCreated(instructorID) == true) {
		    					 CourseOffering course = ModelRegister.getInstance().getRegisteredCourse(courseID);
		    					 course.addInstructor((InstructorModel)ModelRegister.getInstance().getRegisteredUser(instructorID));
		    				 }else {
		    					 System.out.println("the instructor do not exit.");
		    				 }
		    			 }else {
		    				 System.out.println("the courseID invaild.");
		    			 }
		    		 }
		    	 }
		    	 else if(st==0){
		    		 status=0;
		    	 }
		    	 continue;
	    	 }else{
	    		 System.out.println("you ID is not correct please log in again \n");
	    		 continue;
	    	 }//change
	     }
	     
	     
	     else if(types.equals("Student")){
	    	 login = new LoggedInUserFactory();
		     logT= login.createAuthenticatedUser(Token);
		     System.out.println(logT.getAuthenticationToken().getUserType());
	    	 //put your student code here
	    	 new StudentOperation(logT).excute();
	    	 continue;
	     }
	     
	     
	     else if(types.equals("Instructor")){
	    	 login = new LoggedInUserFactory();
		     logT= login.createAuthenticatedUser(Token);
		     System.out.println(logT.getAuthenticationToken().getUserType());
	    	 //put your instructor code here
	    	 new InstructorOperation(logT).Excute();
	    	 continue;
	     }
	     
	     
	     else{
	    	 System.out.println("Your input is invalide");
	    	 continue;
	     }
		 }
		 else{
			 System.out.println("For now Only Admin can excute this program ! ");
			 System.out.println("Please selete your Login Type");
			 Scanner type=new Scanner(System.in);
		     types = type.next();
		     Token =new AuthenticationToken();
		     Token.setSessionID(sessionID);
		     Token.setTokenID(TokenID);
		     Token.setUserType(types);
		    // String typeT=logT.getAuthenticationToken().getUserType();
		     if(types.equals("Admin")){
		    	 login = new LoggedInUserFactory();
			     logT= login.createAuthenticatedUser(Token);
			     System.out.println(logT.getAuthenticationToken().getUserType());
		    	 //here to put my admin code
		    	 new AdminOperation(logT).Excute();
		    	 System.out.println("Please enter your ID to change system status \n");
		    	 Scanner iii=new Scanner(System.in);
		    	 //Integer stt=iii.nextInt();
		    	 //change
		    	 String stringInput = iii.next();
		    	 if (stringInput.equals("1234")){
		    		 int StringInput = Integer.parseInt(stringInput);
		    		 System.out.println("Set the system status: 1 is start, 0 is stop !");
			    	 Scanner ii=new Scanner(System.in);
			    	 Integer st=ii.nextInt();
			    	 if(st==1){
			    		 status=1;
			    	 }
			    	 else if(st==0){
			    		 status=0;
			    	 }
			    	 continue;
		    	 }else{
		    		 System.out.println("you ID is not correct please log in again \n");
		    		 continue;
		    	 //change
		    	 
		    	 }
		     }
		} 
	 }
	}
	 
	 
	 
}
