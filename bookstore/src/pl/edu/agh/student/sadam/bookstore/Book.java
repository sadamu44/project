package pl.edu.agh.student.sadam.bookstore;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long isbn;
	private String title;
	private String author;
}
