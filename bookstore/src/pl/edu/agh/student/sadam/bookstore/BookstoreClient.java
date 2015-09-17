package pl.edu.agh.student.sadam.bookstore;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Query;

import org.apache.derby.tools.sysinfo;

public class BookstoreClient {

	public static void main(String[] args){
		
		BookstoreManager manager = new BookstoreManager();
		Scanner in = new Scanner(System.in);
		
		boolean isRunning = true;
		
		while(isRunning){
			System.out.println(
					"What do you want to do?\n"+
						"\t1) Add new book\n"+
						"\t2) List possible books\n"+
						"\t3) Remove book\n"+
						"\t4) Order book to warehouse\n"+
						"\t5) List available books(warehouse)\n"+
						"\t6) Send book to shop\n"+
						"\t7) Sell book\n"+
						"\t8) List available books(shop)\n"+
						"\n"+
						"\t0) Quit\n\n<< ");
			
			int num = new Integer(in.nextLine());
			
			switch(num){
			case 0:
				manager.clean();
				isRunning = false;
				break;
			case 1:
			{
				Book book = new Book();
				long isbn;
				String title;
				String author;
				
				System.out.println("ISBN: ");
				isbn = new Long(in.nextLine());
				
				System.out.println("Title: ");
				title = in.nextLine();
				
				System.out.println("Author: ");
				author = in.nextLine();
				
				book.setIsbn(isbn);
				book.setTitle(title);
				book.setAuthor(author);
				manager.addPossibleBook(book);
				break;
			}
			case 2:
			{
				List<Book> books = manager.getPossibleBooks();
				
				for(Book book: books){
					System.out.printf("%11d | %40s | %24s\n", book.getIsbn(), book.getTitle(), book.getAuthor());
				}
				
				break;
			}
			case 3:
			{
				System.out.println("ISBN: ");
				Long isbn = new Long(in.nextLine());
				
				manager.removePossibleBook(isbn);
				
				break;
			}
			case 4:
			{
				System.out.println("ISBN: ");
				long isbn = new Long(in.nextLine());
				
				System.out.println("Amount: ");
				int amount = new Integer(in.nextLine());
				
				try {
					manager.orderToWarehouse(isbn, amount);
				} catch (NotExistingBookException e) {
					System.out.println("This book does not exist!!!");
				} catch (InvalidDatabaseStateException e) {
					System.out.println("Something bad happened to your database. It will explode soon. Run.");
				}
				
				break;
			}
			
			case 5:
			{
				List<Warehouse> warehouseEntries = manager.getWarehouseEntries();
				
				for(Warehouse warehouseEntry: warehouseEntries){
					System.out.printf("%11d | %40s | %5d\n", warehouseEntry.getBook().getIsbn(), warehouseEntry.getBook().getTitle(), warehouseEntry.getAmount());
				}
				
				break;
			}
			
			case 6:
			{
				System.out.println("ISBN: ");
				long isbn = new Long(in.nextLine());
				
				System.out.println("Amount: ");
				int amount = new Integer(in.nextLine());
				
				try {
					manager.moveToShop(isbn, amount);
				} catch (NotExistingBookException e) {
					System.out.println("No such book in existance");
				} catch (InvalidDatabaseStateException e) {
					System.out.println("No such book in warehouse");
				} catch (TooMuchException e) {
					System.out.println("Not enough books");
				}
				break;
			}
			
			case 7:
			{
				System.out.println("ISBN: ");
				long isbn = new Long(in.nextLine());
				
				System.out.println("Amount: ");
				int amount = new Integer(in.nextLine());
				
				try {
					manager.sellFromShop(isbn, amount);
				} catch (NotExistingBookException e) {
					System.out.println("No such book in existance");
				} catch (InvalidDatabaseStateException e) {
					System.out.println("No such book in shop");
				} catch (TooMuchException e) {
					System.out.println("Not enough books");
					//e.printStackTrace();
				}
				
				break;
			}
			
			case 8:
			{

				List<Shop> shopEntries = manager.getShopEntries();
				
				for(Shop shopEntry: shopEntries){
					System.out.printf("%11d | %40s | %5d\n", shopEntry.getBook().getIsbn(), shopEntry.getBook().getTitle(), shopEntry.getAmount());
				}
				break;
			}
			
			
			default:
				break;
			}
		}
	}
	
}
