package cs544.hpa1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class AppConflict {

	private static SessionFactory sessionFactory;

	protected static void setUp() throws Exception {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("conflict.cfg.xml").build();
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

		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			
			Car car = session.load(Car.class, 1L);
			car.setPrice(car.getPrice() + 50);
			session.save(car);
			
			session.getTransaction().commit();
		}

		tearDown();
	}
}
