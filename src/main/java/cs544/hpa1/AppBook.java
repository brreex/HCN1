package cs544.hpa1;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class AppBook {
	public static SessionFactory sessionFactory;

	protected static void setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
	
	 protected static void tearDown() throws Exception {
	        if (sessionFactory != null) {
	            sessionFactory.close();
	        }
	    }

	public static void main(String[] args) throws Exception {
		setUp();
			
		try(Session session = sessionFactory.openSession()){
			session.beginTransaction();
			
			Book b1 = new Book("Hibernate","1234","Michael",200.0,new Date());
			session.persist(b1);
			
			Book b2 = new Book("Java core","4567","Michael",200.0,new Date());
			session.persist(b2);
			
			Book b3 = new Book("Spring Security","9811","Michael",200.0,new Date());
			session.persist(b3);
			
			session.getTransaction().commit();
		}
		
		try(Session session = sessionFactory.openSession()){
			session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<Book> allbooks = session.createQuery("From Book").list();
			for(Book book :allbooks){
				 System.out.println(book.getTitle()+" By "+book.getAuthor());
			}
			session.getTransaction().commit();
		}
		
		try(Session session = sessionFactory.openSession()){
			session.beginTransaction();
			
			Book tempbook = session.get(Book.class,2);
			System.out.println("Book "+ tempbook);
			
			session.delete(session.get(Book.class, 1));
			session.close();
			
		}
		
		try(Session session = sessionFactory.openSession()){
			session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<Book> books = session.createQuery("From Book").list();
			
			for(Book book :books){
				 System.out.println(book.getTitle()+" By "+book.getAuthor());
			}
			
			session.getTransaction().commit();
		}
		
		tearDown();
	}
}
