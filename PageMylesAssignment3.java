import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
Myles Page
Cs 1450 002
Monday - Wednesday
Due 02-10-2021
Assignment 3
Array Lists, Polymorphic and Abstract classes
*/

public class PageMylesAssignment3 {
	
	
	public static  void main(String[] args) throws FileNotFoundException {
		//opens the file to read
		File employees = new File("employees.txt");
		Scanner read = new Scanner(employees);
		
		//makes the two teams 
		ArrayList emp = new ArrayList();
		ArrayList<Employee> spaceX = null;
		
		//reads in the size
		int size = read.nextInt();
		
		//fills the emp arrayList 
		for(int i = 0; i < size; i++){
			String roleOfEmployee = read.next();
			String nameOfEmployee = read.next();
			int designInt = read.nextInt();
			int developingInt = read.nextInt();
			int testingInt = read.nextInt();
			int managingInt = read.nextInt();
			
			//decides which type of employee to use 
			switch(roleOfEmployee) {
			case "swa":
				emp.add(new SoftwareEngineer(nameOfEmployee, developingInt, testingInt));
				break;
			case "qae":
				emp.add(new QaEngineer(nameOfEmployee, testingInt));
				break;
			case "swe":
				emp.add(new Architect(nameOfEmployee, designInt));
				break;
			case "swm":
				emp.add(new TeamManager(nameOfEmployee, managingInt));
				break;
			}
		}
		//closes the scanner
		read.close();
		//displays dragon team
		displayDragonTeam(emp);
		System.out.println("\n\nSpace X Team being Built\n\n");
		//adds the best to spaceX team
		spaceX = buildTeam(emp, 1,1,3,3);
		//displays the two teams
		displayDragonTeam(emp);
		displaySpaceXTeam(spaceX);
	}
	
	//displays dragon team members
	public static void displayDragonTeam(ArrayList<Employee> emp) {
		System.out.println("Dragon Team");
		System.out.println("Role\t\t\tName\t      Duties");
		System.out.println("___________________________________________________________________________________________");
		//runs while dragon team has members
		for(Employee dragonTeam : emp) {
			System.out.println(dragonTeam.getRole() +"   \t"+ dragonTeam.getName() +"\t      "+ dragonTeam.duties());
		}
	}
	
	//prints space x team
	public static void displaySpaceXTeam(ArrayList<Employee> spaceX) {
		System.out.println("\n\nSpaceX Team");
		System.out.println("Role\t\t\tName\t      Duties");
		System.out.println("___________________________________________________________________________________________");
		//runs while space x has members
		for(Employee spaceXTeam : spaceX) {
			System.out.println(spaceXTeam.getRole() +"   \t"+ spaceXTeam.getName() +"\t      "+ spaceXTeam.duties());
		}
	}
	
	//builds the new team
	public static ArrayList<Employee> buildTeam(ArrayList<Employee> emp, int numManagers, int numDesigners, int numDevelopers, int numTesters){
		//placeholder array 
		ArrayList<Employee> spaceX = new ArrayList<Employee>();
		//runs till spots are filled in each catagory 
		while(numManagers != 0) {
			int place = findBest(emp, "Team Manager");
			//adds to new array 
			spaceX.add(emp.get(place));
			//delets from old array 
			emp.remove(place);
			//counts down a spot
			numManagers--;
		}
		while(numDesigners != 0) {
			int place = findBest(emp, "Architect");
			//adds to new array
			spaceX.add(emp.get(place));
			//delets from old array 
			emp.remove(place);
			//counts down a spot
			numDesigners--;
		}
		while(numDevelopers != 0) {
			int place = findBest(emp, "Software Engineer");
			//adds to new array
			spaceX.add(emp.get(place));
			//delets from old array 
			emp.remove(place);
			//counts down a spot
			numDevelopers--;
		}
		while(numTesters != 0) {
			int place = findBest(emp, "QA Engineer");
			//adds to new array
			spaceX.add(emp.get(place));
			//delets from old array 
			emp.remove(place);
			//counts down a spot
			numTesters--;
		}
		return spaceX;
	}
	
	//find best 
	public static int findBest(ArrayList<Employee> emp, String role) { 
		int size = emp.size();
		int largestValue = 0;
		int largestPlace = 0;
		//funs till out of size
		for(int i = 0; i < size; i++) {
			//checks what role we are looking for
			switch(role) {
			case "Team Manager":
				//checks if its right role
				if(emp.get(i).getRole() == "swm" ) {
					if(((Manager) emp.get(i)).manage() > largestValue) {
						largestPlace = i;
					}
				}
				break;
			case "Architect":
				if(emp.get(i).getRole() == "swa" ){
					if(((Architect) emp.get(i)).design() > largestValue) {
						largestPlace = i;
					}
				}
				break;
			case "Software Engineer":
				if(emp.get(i).getRole() == "swe" ) {
					int score = ((SoftwareEngineer) emp.get(i)).develop() + ((SoftwareEngineer) emp.get(i)).test();
					if(score > largestValue) {
						largestPlace = i;
					}
				}
				break;
			case "QA Engineer":
				if(emp.get(i).getRole() == "swa" ) {
					if(((QaEngineer) emp.get(i)).test() > largestValue) {
						largestPlace = i;
					}
				}
				break;
			}
		}
		return largestPlace;
	}
}

interface Developer {
	public abstract int develop();
}

interface Manager {
	public abstract int manage();
}

interface Desiger {
	public abstract int design();
}

interface Tester {
	public abstract int test();
}

//employee class
abstract class Employee{
	private String role;
	private String name;

	//getters
	public String getName() {
		return name;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setName(String inputName) {
		this.name = inputName;
	}
	
	public void setRole(String inputRole) {
		this.role = inputRole;
	}
	
	abstract String duties();
}




class SoftwareEngineer extends Employee implements Developer , Tester{
	
	private int developingAbility;
	private int testingAbility;

	public SoftwareEngineer(String name, int developing, int testing) {
		setName(name);
		setRole("Software Engineering");
		this.developingAbility = developing;
		this.testingAbility = testing;
	}
	

	@Override
	String duties() {
		return "I implement, test, and debug code on a daily basis.";
	}


	@Override
	public int test() {
		return testingAbility;
	}


	@Override
	public int develop() {
		return developingAbility;
	}
	
	
}

class QaEngineer extends Employee implements Tester{
	
	private int testingAbility;

	public QaEngineer(String name, int testing) {
		setName(name);
		setRole("QA  Engineer ");
		this.testingAbility = testing;
	}
	
	
	@Override
	String duties() {
		return "I test code on a daily basis.";
	}


	@Override
	public int test() {
		return testingAbility;
	}

}

class Architect  extends Employee implements Desiger{
	
	private int designingAbility;

	public Architect (String name, int designing) {
		setName(name);
		setRole("Architect    ");
		this.designingAbility = designing;
	}
	
	
	@Override
	String duties() {
		return "I design systems and interconnections between systems.";
	}


	@Override
	public int design() {
		return designingAbility;
	}
}

class TeamManager extends Employee implements Manager{
	
	private int managingAbility;
	
	public TeamManager(String name, int managing) {
		setName(name);
		setRole("Team  Manager ");
		this.managingAbility = managing;
	}
	
	@Override
	String duties() {
		return "I manage a development team. It's like herding cats!";
	}

	@Override
	public int manage() {
		return managingAbility;
	}
}





