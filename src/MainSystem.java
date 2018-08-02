import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

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
					+ "\nEnter 3: add book to library"
					+ "\nEnter 4: import & save Video/Image/Music (vim) file to book"
					+ "\nEnter 5: load & export vim file from book & play it");
			
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
			
			case 4:
				importOpenAndSave();
				break;
				
			case 5:
				loadOpenAndExport();
				break;
			
			default:
				System.out.println("Entered vlaue is not appropriate. please try again.");
				break;
			}
		}
		
		System.exit(0);
	}

	
	
	private static void loadOpenAndExport() {
		String vimName = null;
		String bookTitle = null;
		Book book;
		VIM vim = null;
		byte[] data = null;
		File file;
		FileOutputStream fos = null;
		
		System.out.println("\nEnter the book title: ");
		bookTitle = in.next();
		book = lib.getBookByTitle(bookTitle);
		if(book == null) {
			System.out.println("This book doesn`t exist");
		} else {
			System.out.println(book.toString());
			System.out.println("\nEnter complete file name: ");
			vimName = in.next();
			vim = book.getVIMByName(vimName);
			if(vim == null) {
				System.out.println("This VIM doesn`t exist");				
			} else {
				data = vim.getData();
				file = new File(vim.getName());
				try {
					fos = new FileOutputStream(file);
					fos.write(data);
					fos.close();
					Desktop.getDesktop().open(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}



	private static void importOpenAndSave() {
		JFileChooser chooser;
		FileFilter filter;
		FileInputStream fis = null;
		
		Book book;
		String bookTitle = null;
		VIM vim;
		File file = null;
		byte[] data = null;
		boolean stop = false;
		
		System.out.println("\nEnter the book title to add vim on it: ");
		bookTitle = in.next();
		book = lib.getBookByTitle(bookTitle);
		if(book == null) {
			System.out.println("This book doesn`t exist.");
		} else {
			System.out.println("\nChoose your video/image/music to add: ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			chooser = new JFileChooser();
			filter = new FileNameExtensionFilter("Video / Image / Music", "wav", "avi", "jpg", "mp3", "mp4", "png", "jpeg");
			chooser.addChoosableFileFilter(filter);
			chooser.setFileFilter(filter);
			int resultCode = chooser.showOpenDialog(null);
			if(resultCode == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				data = new byte[(int)file.length()];
			} else {
				System.out.println("You cancelled adding the vim.");
				stop = true;
			}
		}
		
		if(!stop) {
			try {
				fis = new FileInputStream(file);
				fis.read(data);
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			vim = new VIM(file.getName(), data);
			book.addVIM(vim);
			
			System.out.println("You`ve added file: " + file.getName() 
					+ " to book: " + bookTitle);
			
		}
		
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
