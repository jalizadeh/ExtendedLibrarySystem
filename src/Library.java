import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Library extends Object implements Serializable{
	private List<Book> collection;
	
	public Library() {
		collection = new ArrayList<Book>();
	} 
	
	public void addBook(Book book) {
		collection.add(book);
	}
	
	public Book getBookByTitle(String title) {
		Book v = null;
		Iterator<Book> i = collection.iterator();
		if(i.hasNext()) {
			v = i.next();
			if(v.getTitle().toLowerCase().contentEquals(title.toLowerCase())) {
				return v;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String total = "\n";
		
		/*
		for (int i = 0; i < collection.size(); i++) {
			Book b = collection.get(i);
			total += b.toString();
		}
		*/
		
		Iterator<Book> i = collection.iterator();
		while(i.hasNext()) {
			Book b = (Book) i.next();
			total += b.toString()+"\n";
		}
		
		//note there is one character => "\n" at least
		if(total.length() > 1)
			return total;
		else
			return "There is no book to show.";
	}
	
}