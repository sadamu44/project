package pl.edu.agh.student.sadam.bookstore;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

public class BookstoreManager {
	private List<Book> possibleBooks;
	private Map<Book, Integer> warehouse;
	private Map<Book, Integer> bookstore;
	
	public BookstoreManager(){
		//konstruktor
		init();
	}
	
	private void init(){
		//tutaj b�dziemy �adowa� dane z bazy danych
	}
	
	public void addPossibleBook(Book book){
		possibleBooks.add(book);
	}
	
	public void moveBookToBookstore(Book book, int quantity) throws NotExistsOnPossibleBooksListException, NotEnoughBooksInBookstoreException{
		
		if(quantity < 0)
			throw new InvalidParameterException();
		
		if(!warehouse.containsKey(book))
			throw new NotExistsOnPossibleBooksListException();
		
		if(warehouse.get(book) < quantity)
			throw new NotEnoughBooksInBookstoreException();
		
		warehouse.put(book, warehouse.get(book) - quantity);
		bookstore.put(book, bookstore.get(book) + quantity);
	}
	
	public void addBookToWarehouse(Book book, int quantity) throws NotExistsOnPossibleBooksListException{
		if(quantity < 0)
			throw new InvalidParameterException();
		
		if(!warehouse.containsKey(book))
			throw new NotExistsOnPossibleBooksListException();
	}
	
}
