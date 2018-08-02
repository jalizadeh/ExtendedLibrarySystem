import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Book implements Serializable{
	private static final long serialVersionUID = -5431267952765863338L;
	private int isbn;
	private String title, author;
	private double price;
	private List<VIM> vims;
	
	public Book() {
		this.isbn = 0;
		this.title = null;
		this.author = null;
		this.price = 0;
		this.vims = new ArrayList<VIM>();
	}
	
	public Book(int isbn, String title, String author, double price) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.price = price;
		this.vims = new ArrayList<VIM>();
	}
	
	
	public void addVIM(VIM v) {
		vims.add(v);
	}
	
	public String getTitle() {
		return title;
	}
	
	public VIM getVIMByName(String name) {
		VIM v = null;
		Iterator<VIM> i = vims.iterator();
		if(i.hasNext()) {
			v = i.next();
			if(v.getName().toLowerCase().contentEquals(name.toLowerCase())) {
				return v;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "\nisbn: " + isbn + "\ntitle: " + title + 
				"\nauthor: " + author + "\nprice: " + price ;
	}
	
	
}
