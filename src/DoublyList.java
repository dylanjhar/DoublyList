import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/* 
 * This program uses a doubly linked list loaded with data from a text file to keep track of student records
 * User is presented with a menu with options to use the student records
*/

class Student {
	private String name;
	private int sumOfGrades;
	private int numOfGrades;
	//Default Constructor
	public Student() {
		name = null;
		sumOfGrades = numOfGrades = 0;
	}
	//Setters
	public void setName(String n) {
		name = n;
	}
	public void setSumOfGrades(int s) {
		sumOfGrades = s;
	}
	public void setNumOfGrades(int g) {
		numOfGrades = g;
	}
	//Getters
	public String getName() {
		return name;
	}
	public int getSumOfGrades() {
		return sumOfGrades;
	}
	public int getNumOfGrades() {
		return numOfGrades;
	}
}

class Node {
	private Student data;
	private Node previous;
	private Node next;
	//Constructors
	public Node() {
		data = null;
		previous = next = null;
	}
	public Node(Student d, Node p, Node n) {
		data = d;
		previous = p;
		next = n;
	}
	//Setters
	public void setData(Student d) {
		data = d;
	}
	public void setPrevious(Node p) {
		previous = p;
	}
	public void setNext(Node n) {
		next = n;
	}
	//Getters
	public Student getData() {
		return data;
	}
	public Node getPrevious() {
		return previous;
	}
	public Node getNext() {
		return next;
	}
}

class DList {
	private Node head;
	private Node tail;
	//Default Constructor
	public DList() {
		head = tail = null;
	}
	//addHead adds node with particular data to front of list
	public void addHead(Student d) {
		Node t = new Node(d, null, null);
		if (head == null) {
			head = tail = t;
		} else {
			t.setNext(head);
			head.setPrevious(t);
			head = t;
		}
	}
	//addTail adds node with particular data to end of list
	public void addTail(Student d) {
		Node t = new Node(d, null, null);
		if (head == null) {
			head = tail = t;
		}
		else {
			tail.setNext(t);
			t.setPrevious(tail);
			tail = t;
		}
	}
	//removeHead removes node at the front of list
	public void removeHead() {
		if (head == null) {
			return;
		} else if (head == tail) {
			head = tail = null;
		} else {
			head = head.getNext();
			head.setPrevious(null);
		}
	}
	//removeTail removes node at the end of list
	public void removeTail() {
		if (head == null) {
			return;
		} else if (head == tail) {
			head = tail = null;
		} else {
			tail = tail.getPrevious();
			tail.setNext(null);
		}
	}
	//removeStudent removes a particular student by name
	public void removeStudent(String name) {
		Node t = head;
		if (head != null && head.getData().getName().equals(name)) {
			head = head.getNext();
			if (head != null) {
				head.setPrevious(null);
			}
		}
		while (t != null && t.getNext() != null) {
			if (t.getNext().getData().getName().equals(name)) {
				if (t.getNext().getNext() == null) {
					tail = t;
				}
				t.setNext(t.getNext().getNext());
				if (t.getNext() != null) {
					t.getNext().setPrevious(t);
				}
			}
			t = t.getNext();
		}
	}
	//findStudent finds student name in list and returns Student object
	public Student findStudent(String name) {
		Node t = head;
		if (t == null) {
			return null;
		}
		while (t.getNext() != null && !t.getData().getName().equals(name)) {
			t = t.getNext();
		}
		if (t.getData().getName().equals(name)) {
			return t.getData();
		} else {
			return null;
		}
	}
	//printList prints all contents of list
	public void printList() {
		if (head == null) {
			System.out.println("List is empty");
		} else {
			System.out.println("Printing list...");
			System.out.println("Name, sum, grades");
			Node t = head;
			while (t != null) {
				System.out.print(t.getData().getName() + ", ");
				System.out.print(t.getData().getSumOfGrades() + ", ");
				System.out.println(t.getData().getNumOfGrades());
				t = t.getNext();
			}
		}
	}
}

public class DoublyList {
	//loadList loads data from a text file into the doubly linked list
	public static void loadList(DList d, Scanner readFile) {
		while (readFile.hasNext()) {
			Student s = new Student();
			s.setName(readFile.next());
			s.setSumOfGrades(readFile.nextInt());
			s.setNumOfGrades(readFile.nextInt());
			d.addTail(s);
		}
	}
	//printMenu prints a menu for the user to see their options
	public static void printMenu() {
		System.out.println("Enter number to choose option");
		System.out.println("1) Update a student record");
		System.out.println("2) Remove a student record");
		System.out.println("3) Display info for all students");
		System.out.println("4) Display a student's average grade");
		System.out.println("5) Exit");
	}
	//updateStudent updates the name, sum, or number of grades for a particular student
	public static void updateStudent(Scanner scan, DList d) {
		System.out.println("Enter name of student to update");
		String name = scan.next();
		Student student = d.findStudent(name);
		if (student == null) {
			System.out.println("Student does not exist");
			return;
		}
		System.out.println("Enter number to choose option");
		System.out.println("1) Update name");
		System.out.println("2) Update sum of grades");
		System.out.println("3) Update number of grades");
		int option = scan.nextInt();
		if (option == 1) {
			System.out.println("Enter new name");
			name = scan.next();
			student.setName(name);
		} else if (option == 2) {
			System.out.println("Enter new sum");
			int sum = scan.nextInt();
			student.setSumOfGrades(sum);
		} else {
			System.out.println("Enter new number");
			int num = scan.nextInt();
			student.setNumOfGrades(num);
		}
	}
	//removeStudent removes a particular student by name
	public static void removeStudent(Scanner scan, DList d) {
		System.out.println("Enter name of student to remove");
		String name = scan.next();
		Student student = d.findStudent(name);
		if (student == null) {
			System.out.println("Student does not exist");
			return;
		}
		d.removeStudent(name);
		System.out.println("Student has been removed");
	}
	//displayGrade displays the average grade for a particular student
	public static void displayGrade(Scanner scan, DList d) {
		System.out.println("Enter name of student to display average grade");
		String name = scan.next();
		Student student = d.findStudent(name);
		if (student == null) {
			System.out.println("Student does not exist");
			return;
		}
		int sum = student.getSumOfGrades();
		int num = student.getNumOfGrades();
		double avg = sum / num;
		System.out.println("Average grade: " + avg);
	}

	public static void main(String[] args) throws IOException {
		DList d = new DList();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter input file name");
		String fileName = scan.next();
		File inFile = new File(fileName);
		if (!inFile.exists()) {
			System.out.println("error - file does not exist");
			System.exit(-1);
		}
		
		Scanner readFile = new Scanner(inFile);
		
		loadList(d, readFile);
		
		printMenu();
		int option = scan.nextInt();
		while (option != 5) {
			if (option == 1) {
				updateStudent(scan, d);
			} else if (option == 2) {
				removeStudent(scan, d);
			} else if (option == 3) {
				d.printList();
			} else if (option == 4) {
				displayGrade(scan, d);
			} else {
				System.out.println("Not an option");
			}
			printMenu();
			option = scan.nextInt();
		}
		scan.close();
		readFile.close();
	}
}
