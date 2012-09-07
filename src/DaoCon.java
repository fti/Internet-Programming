import java.sql.Connection;
import java.sql.DriverManager;


public class DaoCon {

	public static Connection getConnection() {
		Connection con = null;

	    try {
	    	Class.forName("org.postgresql.Driver").newInstance();
	    	con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/intprog","postgres", "fti");

	      if(!con.isClosed())
	        System.out.println("Successfully connected to " +
	          "MySQL server using TCP/IP...");

	    } catch(Exception e) {
	      System.err.println("Exception: " + e.getMessage());
	    } 
		
		return con;
	}
}
