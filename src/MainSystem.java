import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class MainSystem {

	static String filename = null;
	static Library lib = new Library();
	static Scanner in = new Scanner(System.in);
	static Boolean running = true;

	public static void main(String[] args) {
		while (running) {
			System.out.println("\nEnter 0: load a library" 
					+ "\nEnter 1: save & quit" 
					+ "\nEnter 2: list all books"
					+ "\nEnter 3: add book to library");
			
			int answer = in.nextInt();
			
			switch (answer) {
			case 0:
				System.out.println("Enter the file name to load: ");
				loadLibrary(in.next());
				break;
			
			case 1:
				saveAndQuit();
				break;
			
			case 2:
				System.out.println(lib.toString());
				break;	
			
			case 3:
				addNewBook();
				break;
			
			default:
				
				break;
			}
		}
		
		System.exit(0);
	}

	
	
	private static void addNewBook() {
		int isbn;
		String title, author;
		double price;
		
		System.out.println("\nEnter the book ISBN: ");
		isbn = in.nextInt();
		
		System.out.println("\nEnter the book title: ");
		title = in.next();
		
		System.out.println("\nEnter the book author: ");
		author = in.next();
		
		System.out.println("\nEnter the book price: ");
		price = in.nextDouble();
		
		Book b = new Book(isbn, title, author, price);
		System.out.println(b.toString());
		
		lib.addBook(b);
		System.out.println("\nNew book added successfully.");
	}

	
	private static void saveAndQuit() {
		System.out.println("Enter the file name: ");
		filename = in.next();
		running = false;
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try {
			fos = new FileOutputStream(filename + ".ser");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(lib);
			fos.close();
			oos.close();
			System.out.println("File successfully saved.\nExited.");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
	private static void loadLibrary(String filename) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		File file = new File(filename+".ser");
		if(file.exists()) {
			try {
				fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);
				lib = (Library)ois.readObject();
				fis.close();
				ois.close();
				System.out.println("The file "+ filename+ " uploaded successfully.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("The file does not exist.");
		}
		
	}
}
