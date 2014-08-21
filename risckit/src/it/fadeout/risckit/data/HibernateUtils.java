package it.fadeout.risckit.data;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

	private static final SessionFactory sessionFactory;
	
	static {  
        try {  
            // Create the SessionFactory from hibernate.cfg.xml  
            sessionFactory = new Configuration().configure("/hibernate-postgres.cfg.xml").buildSessionFactory();  
        } catch (Throwable ex) {  
            // Make sure you log the exception, as it might be swallowed  
            System.err.println("Initial SessionFactory creation failed." + ex);  
            throw new ExceptionInInitializerError(ex);  
        }  
    }  
	
	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure("/hibernate-postgres.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	
	public static SessionFactory getSessionFactory() {

		return sessionFactory;
	}

	
	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
	
	public static void releaseSession() {
		// Close caches and connection pools
		
	}
	

}
