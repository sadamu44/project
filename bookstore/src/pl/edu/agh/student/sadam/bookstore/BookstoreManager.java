package pl.edu.agh.student.sadam.bookstore;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pl.edu.agh.student.sadam.bookstore.Book;
import pl.edu.agh.student.sadam.bookstore.NotEnoughBooksInBookstoreException;
import pl.edu.agh.student.sadam.bookstore.NotExistsOnPossibleBooksListException;

public class BookstoreManager {
	private static EntityManagerFactory factory;
	private EntityManager em;
	//private List<Book> possibleBooks;
	//private Map<Book, Integer> warehouse;
	//private Map<Book, Integer> bookstore;
	
	public static void main(String[] args){
		BookstoreManager manager = new BookstoreManager();
	}
	
	public BookstoreManager(){
		//konstruktor
		init();
	}
	
	private void init(){
		factory = Persistence.createEntityManagerFactory("bookstore");
	    em = factory.createEntityManager();
	    
	    /*
	    em.getTransaction().begin();
	    BookKeeper keeper = new BookKeeper();
	    keeper.setBook(todo);
	    keeper.setAmount(2);
	    em.persist(todo);
	    em.persist(keeper);
	    em.getTransaction().commit();
	     */
	}
	
	public void clean(){
		em.close();
	}
	
	public void addPossibleBook(Book book){
		em.getTransaction().begin();
		em.persist(book);
		em.getTransaction().commit();
	}
	
	public List<Book> getPossibleBooks(){
		Query q = em.createQuery("select b from Book b");
	    List<Book> bookList = q.getResultList();
	    return bookList;
	}
	
	public void moveBookToBookstore(Book book, int quantity) throws NotExistsOnPossibleBooksListException, NotEnoughBooksInBookstoreException{
		
		if(quantity < 0)
			throw new InvalidParameterException();
		
		/*if(!warehouse.containsKey(book))
			throw new NotExistsOnPossibleBooksListException();
		
		if(warehouse.get(book) < quantity)
			throw new NotEnoughBooksInBookstoreException();
		
		warehouse.put(book, warehouse.get(book) - quantity);
		bookstore.put(book, bookstore.get(book) + quantity);*/
	}
	
	public void addBookToWarehouse(Book book, int quantity) throws NotExistsOnPossibleBooksListException{
		if(quantity < 0)
			throw new InvalidParameterException();
		
		/*if(!warehouse.containsKey(book))
			throw new NotExistsOnPossibleBooksListException();*/
	}
	
}
