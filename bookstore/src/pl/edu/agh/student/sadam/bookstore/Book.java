package pl.edu.agh.student.sadam.bookstore;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {
	@Id
	private Long isbn;
	private String title;
	private String author;
	
	public Book(){
	}
	
	public Book(long isbn, String title, String author){
		this.isbn = isbn;
		this.setTitle(title);
		this.setAuthor(author);
	}
	
	public Long getIsbn(){
		return isbn;
	}
	
	public void setIsbn(Long isbn){
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
