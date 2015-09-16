package pl.edu.agh.student.sadam.bookstore;

import java.util.List;
import java.util.Scanner;

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
						"\t3) Remove book"+
						"...\n"+
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
			default:
				break;
			}
		}
	}
	
}
