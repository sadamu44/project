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
	
	public BookstoreManager(){
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
	
	public void removePossibleBook(Long isbn){
		Book book = em.find(Book.class, isbn);
		 
		em.getTransaction().begin();
		em.remove(book);
		em.getTransaction().commit();
	}
	
	public void orderToWarehouse(long isbn, int amount) throws NotExistingBookException, InvalidDatabaseStateException{
		if(amount <= 0)
			throw new InvalidParameterException();
		
		Book book = em.find(Book.class, isbn);
		
		if(book == null)
			throw new NotExistingBookException();
		
		Query q = em.createQuery("select w from Warehouse w where w.book.isbn = :isbn");
		q.setParameter("isbn", isbn);
	    List<Warehouse> warehouseEntities = q.getResultList();
	    
	    if(warehouseEntities.size() > 1 || warehouseEntities.size() < 0)
	    	throw new InvalidDatabaseStateException();
	    
	    if(warehouseEntities.size() == 1){
	    	warehouseEntities.get(0).setAmount(warehouseEntities.get(0).getAmount() + amount); 
	    	em.getTransaction().begin();
			em.persist(warehouseEntities.get(0));
			em.getTransaction().commit();
	    } else{
	    	Warehouse warehouseEntry = new Warehouse();
	    	warehouseEntry.setBook(book);
	    	warehouseEntry.setAmount(amount);
	    	
	    	em.getTransaction().begin();
			em.persist(warehouseEntry);
			em.getTransaction().commit();
	    }
	    
	}
	
	public List<Warehouse> getWarehouseEntries(){
		Query q = em.createQuery("select w from Warehouse w");
	    List<Warehouse> warehouseEntries = q.getResultList();
	    return warehouseEntries;
	}
	
	
	public List<Shop> getShopEntries(){
		Query q = em.createQuery("select s from Shop s");
	    List<Shop> shopEntries = q.getResultList();
	    return shopEntries;
	}
	
	public void moveToShop(long isbn, int amount) throws NotExistingBookException, InvalidDatabaseStateException, TooMuchException
	{
		if(amount <= 0)
			throw new InvalidParameterException();

		Book book = em.find(Book.class, isbn);
		
		if(book == null)
			throw new NotExistingBookException();
		
		Query q = em.createQuery("select w from Warehouse w where w.book.isbn = :isbn");
		q.setParameter("isbn", isbn);
	    List<Warehouse> warehouseEntities = q.getResultList();
	    
	    if(warehouseEntities.size() > 1 || warehouseEntities.size() < 0)
	    	throw new InvalidDatabaseStateException();
	    if(warehouseEntities.size() ==0)
	    	throw new InvalidParameterException();
	    
	    if (warehouseEntities.get(0).getAmount() < amount)
	    	throw new TooMuchException();	
	    	
	    warehouseEntities.get(0).setAmount(warehouseEntities.get(0).getAmount() - amount);	    
	    em.getTransaction().begin();
		em.persist(warehouseEntities.get(0));
		em.getTransaction().commit();
//////	    
		Query u = em.createQuery("select s from Shop s where s.book.isbn = :isbn");
		u.setParameter("isbn", isbn);
	    List<Shop> shopEntities = u.getResultList();
	    
	   if(shopEntities.size() > 1 || shopEntities.size() < 0)
	    	throw new InvalidDatabaseStateException();
	    
	    if(shopEntities.size() == 1){
	    	shopEntities.get(0).setAmount(shopEntities.get(0).getAmount() + amount); 
	    	em.getTransaction().begin();
			em.persist(shopEntities.get(0));
			em.getTransaction().commit();
	    } else{
	    	Shop shopEntry = new Shop();
	    	shopEntry.setBook(book);
	    	shopEntry.setAmount(amount);
	    	
	    	em.getTransaction().begin();
			em.persist(shopEntry);
			em.getTransaction().commit();
	    }
		
	}
	
	
	
	
	public void sellFromShop(long isbn, int amount) throws NotExistingBookException, InvalidDatabaseStateException, TooMuchException {

		if(amount <= 0)
			throw new InvalidParameterException();

		Book book = em.find(Book.class, isbn);		
		if(book == null)
			throw new NotExistingBookException();
		
		Query q = em.createQuery("select s from Shop s where s.book.isbn = :isbn");
		q.setParameter("isbn", isbn);
	    List<Shop> shopEntities = q.getResultList();
	    
	    if(shopEntities.size() > 1 || shopEntities.size() < 0)
	    	throw new InvalidDatabaseStateException();
	    if(shopEntities.size() ==0)
	    	throw new InvalidParameterException();
	    
	    if (shopEntities.get(0).getAmount() < amount)
	    	throw new TooMuchException();	
	    	
	    shopEntities.get(0).setAmount(shopEntities.get(0).getAmount() - amount);	    
	    em.getTransaction().begin();
		em.persist(shopEntities.get(0));
		em.getTransaction().commit();	
		
		
	}
	
}
