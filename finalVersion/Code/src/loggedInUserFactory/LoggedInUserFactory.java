package loggedInUserFactory;

import java.util.Scanner;

import authenticatedUsers.LoggedInAdmin;
import authenticatedUsers.LoggedInAuthenticatedUser;
import authenticatedUsers.LoggedInInstructor;
import authenticatedUsers.LoggedInStudent;
import authenticationServer.AuthenticationToken;

public class LoggedInUserFactory {
	public LoggedInStudent currentStudent;
	public LoggedInInstructor currentInstructor;
	public LoggedInAdmin currentAdmin;
	public LoggedInUserFactory(){
		
	}
	
	public LoggedInAuthenticatedUser createAuthenticatedUser(AuthenticationToken authenticationToken){
		switch(authenticationToken.getUserType()){
		case "Admin":
			return createLoggedInAdmin(authenticationToken);
		case "Student":
			return createLoggedInStudent(authenticationToken);
		case "Instructor":
			return createLoggedInInstructor(authenticationToken);
		default:
			return null;
		}
	}
	
	public LoggedInStudent createLoggedInStudent(AuthenticationToken authenticationToken){
//		TODO add object creation logic
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		System.out.println("please enter your Surname");
		Scanner scan=new Scanner(System.in);
		String surname=scan.next();
		
		System.out.println("please enter your name");
        String name = scan.next();
        
        System.out.println("Please enter your ID");
        String logID = scan.next();
        
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        currentStudent=new LoggedInStudent();
        currentStudent.setName(name);
        currentStudent.setID(logID);
        currentStudent.setSurname(surname);
        currentStudent.setAuthenticationToken(authenticationToken);
        return this.currentStudent;
	}
	
	public LoggedInAdmin createLoggedInAdmin(AuthenticationToken authenticationToken){
//		TODO add object creation logic
		System.out.println("please enter your Surname");
		Scanner scan=new Scanner(System.in);
		String surname=scan.next();
		
		System.out.println("please enter your name");
        String name = scan.next();
        
        System.out.println("Please enter your ID");
        String logID = scan.next();
        
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        currentAdmin=new LoggedInAdmin();
        currentAdmin.setName(name);
        currentAdmin.setID(logID);
        currentAdmin.setSurname(surname);
        currentAdmin.setAuthenticationToken(authenticationToken);
        return this.currentAdmin;
	}
	
	public LoggedInInstructor createLoggedInInstructor(AuthenticationToken authenticationToken){
//		TODO add object creation logic
		System.out.println("please enter your Surname");
		Scanner scan=new Scanner(System.in);
		String surname=scan.next();
		
		System.out.println("please enter your name");
        String name = scan.next();
        
        System.out.println("Please enter your ID");
        String logID = scan.next();
        
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        currentInstructor=new LoggedInInstructor();
        currentInstructor.setName(name);
        currentInstructor.setID(logID);
        currentInstructor.setSurname(surname);
        currentInstructor.setAuthenticationToken(authenticationToken);
        return this.currentInstructor;
	}
}
