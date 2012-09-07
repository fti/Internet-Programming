import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilesDao {



    /**
     * createValueObject-method. This method is used when the Dao class needs
     * to create new value object instance. The reason why this method exists
     * is that sometimes the programmer may want to extend also the valueObject
     * and then this method can be overrided to return extended valueObject.
     * NOTE: If you extend the valueObject class, make sure to override the
     * clone() method in it!
     */
    public Files createValueObject() {
          return new Files();
    }


    /**
     * getObject-method. This will create and load valueObject contents from database 
     * using given Primary-Key as identifier. This method is just a convenience method 
     * for the real load-method which accepts the valueObject as a parameter. Returned
     * valueObject will be created using the createValueObject() method.
     */
    public Files getObject(Connection conn, int id) throws NotFoundException, SQLException {

          Files valueObject = createValueObject();
          valueObject.setId(id);
          load(conn, valueObject);
          return valueObject;
    }


    /**
     * load-method. This will load valueObject contents from database using
     * Primary-Key as identifier. Upper layer should use this so that valueObject
     * instance is created and only primary-key should be specified. Then call
     * this method to complete other persistent information. This method will
     * overwrite all other fields except primary-key and possible runtime variables.
     * If load can not find matching row, NotFoundException will be thrown.
     *
     * @param conn         This method requires working database connection.
     * @param valueObject  This parameter contains the class instance to be loaded.
     *                     Primary-key field must be set for this to work properly.
     */
    public void load(Connection conn, Files valueObject) throws NotFoundException, SQLException {

          String sql = "SELECT * FROM files WHERE (id = ? ) "; 
          PreparedStatement stmt = null;

          try {
               stmt = conn.prepareStatement(sql);
               stmt.setInt(1, valueObject.getId()); 

               singleQuery(conn, stmt, valueObject);

          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    /**
     * LoadAll-method. This will read all contents from database table and
     * build a List containing valueObjects. Please note, that this method
     * will consume huge amounts of resources if table has lot's of rows. 
     * This should only be used when target tables have only small amounts
     * of data.
     *
     * @param conn         This method requires working database connection.
     */
    public List loadAll(Connection conn) throws SQLException {

          String sql = "SELECT * FROM files ORDER BY id ASC ";
          List searchResults = listQuery(conn, conn.prepareStatement(sql));

          return searchResults;
    }
    
    public List<EditorBean> loadFiles(Connection conn) throws SQLException {
    	
    	String sql = "SELECT u.email,u.name,u.surname,f.fname,f.title, f.id  FROM userbean as u, files as f where f.email = u.email and f.revieved=0 ORDER BY u.id ASC ";
    	List<EditorBean> searchResults = listFiles(conn, conn.prepareStatement(sql));
    	
    	return searchResults;
    }
    public List<EditorBean> getRevievers(Connection conn, String email) throws SQLException {
    	
    	String sql = "SELECT u.email,u.name,u.surname,f.fname,f.title, f.id  FROM userbean as u, files as f, filerev as r where r.email = '"+email
    	+"' and r.email = u.email and r.fid = f.id ORDER BY u.id ASC ";
    	List<EditorBean> searchResults = listFiles(conn, conn.prepareStatement(sql));
    	
    	return searchResults;
    }
    public synchronized void insertfilerev(Connection conn, int fid, String assignerEmail) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
             sql = "INSERT INTO fileRev ( email, fid ) VALUES (?, ?) ";
             stmt = conn.prepareStatement(sql);

             stmt.setString(1, assignerEmail); 
             stmt.setInt(2, fid); 

             int rowcount = databaseUpdate(conn, stmt);
             if (rowcount != 1) {
                  //System.out.println("PrimaryKey Error when updating DB!");
                  throw new SQLException("PrimaryKey Error when updating DB!");
             }

        } finally {
            if (stmt != null)
                stmt.close();
        }


  }



    /**
     * create-method. This will create new row in database according to supplied
     * valueObject contents. Make sure that values for all NOT NULL columns are
     * correctly specified. Also, if this table does not use automatic surrogate-keys
     * the primary-key must be specified. After INSERT command this method will 
     * read the generated primary-key back to valueObject if automatic surrogate-keys
     * were used. 
     *
     * @param conn         This method requires working database connection.
     * @param valueObject  This parameter contains the class instance to be created.
     *                     If automatic surrogate-keys are not used the Primary-key 
     *                     field must be set for this to work properly.
     */
    public synchronized void create(Connection conn, Files valueObject) throws SQLException {

          String sql = "";
          PreparedStatement stmt = null;
          ResultSet result = null;

          try {
               sql = "INSERT INTO files ( email, fname, "
               + "revieved, title) VALUES (?, ?, ?, ?) ";
               stmt = conn.prepareStatement(sql);

               stmt.setString(1, valueObject.getEmail()); 
               stmt.setString(2, valueObject.getFname()); 
               stmt.setInt(3, valueObject.getRevieved()); 
               stmt.setString(4, valueObject.getTitle()); 

               int rowcount = databaseUpdate(conn, stmt);
               if (rowcount != 1) {
                    //System.out.println("PrimaryKey Error when updating DB!");
                    throw new SQLException("PrimaryKey Error when updating DB!");
               }

          } finally {
              if (stmt != null)
                  stmt.close();
          }


    }


    /**
     * save-method. This method will save the current state of valueObject to database.
     * Save can not be used to create new instances in database, so upper layer must
     * make sure that the primary-key is correctly specified. Primary-key will indicate
     * which instance is going to be updated in database. If save can not find matching 
     * row, NotFoundException will be thrown.
     *
     * @param conn         This method requires working database connection.
     * @param valueObject  This parameter contains the class instance to be saved.
     *                     Primary-key field must be set for this to work properly.
     */
    public void save(Connection conn, int id) 
          throws NotFoundException, SQLException {

          String sql = "UPDATE files SET revieved = 1 WHERE (id = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setInt(1, id); 

              int rowcount = databaseUpdate(conn, stmt);
              if (rowcount == 0) {
                   //System.out.println("Object could not be saved! (PrimaryKey not found)");
                   throw new NotFoundException("Object could not be saved! (PrimaryKey not found)");
              }
              if (rowcount > 1) {
                   //System.out.println("PrimaryKey Error when updating DB! (Many objects were affected!)");
                   throw new SQLException("PrimaryKey Error when updating DB! (Many objects were affected!)");
              }
          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    /**
     * delete-method. This method will remove the information from database as identified by
     * by primary-key in supplied valueObject. Once valueObject has been deleted it can not 
     * be restored by calling save. Restoring can only be done using create method but if 
     * database is using automatic surrogate-keys, the resulting object will have different 
     * primary-key than what it was in the deleted object. If delete can not find matching row,
     * NotFoundException will be thrown.
     *
     * @param conn         This method requires working database connection.
     * @param valueObject  This parameter contains the class instance to be deleted.
     *                     Primary-key field must be set for this to work properly.
     */
    public void delete(Connection conn, Files valueObject) 
          throws NotFoundException, SQLException {

          String sql = "DELETE FROM files WHERE (id = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setInt(1, valueObject.getId()); 

              int rowcount = databaseUpdate(conn, stmt);
              if (rowcount == 0) {
                   //System.out.println("Object could not be deleted (PrimaryKey not found)");
                   throw new NotFoundException("Object could not be deleted! (PrimaryKey not found)");
              }
              if (rowcount > 1) {
                   //System.out.println("PrimaryKey Error when updating DB! (Many objects were deleted!)");
                   throw new SQLException("PrimaryKey Error when updating DB! (Many objects were deleted!)");
              }
          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    /**
     * deleteAll-method. This method will remove all information from the table that matches
     * this Dao and ValueObject couple. This should be the most efficient way to clear table.
     * Once deleteAll has been called, no valueObject that has been created before can be 
     * restored by calling save. Restoring can only be done using create method but if database 
     * is using automatic surrogate-keys, the resulting object will have different primary-key 
     * than what it was in the deleted object. (Note, the implementation of this method should
     * be different with different DB backends.)
     *
     * @param conn         This method requires working database connection.
     */
    public void deleteAll(Connection conn) throws SQLException {

          String sql = "DELETE FROM files";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              int rowcount = databaseUpdate(conn, stmt);
          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }


    /**
     * coutAll-method. This method will return the number of all rows from table that matches
     * this Dao. The implementation will simply execute "select count(primarykey) from table".
     * If table is empty, the return value is 0. This method should be used before calling
     * loadAll, to make sure table has not too many rows.
     *
     * @param conn         This method requires working database connection.
     */
    public int countAll(Connection conn) throws SQLException {

          String sql = "SELECT count(*) FROM files";
          PreparedStatement stmt = null;
          ResultSet result = null;
          int allRows = 0;

          try {
              stmt = conn.prepareStatement(sql);
              result = stmt.executeQuery();

              if (result.next())
                  allRows = result.getInt(1);
          } finally {
              if (result != null)
                  result.close();
              if (stmt != null)
                  stmt.close();
          }
          return allRows;
    }


    /** 
     * searchMatching-Method. This method provides searching capability to 
     * get matching valueObjects from database. It works by searching all 
     * objects that match permanent instance variables of given object.
     * Upper layer should use this by setting some parameters in valueObject
     * and then  call searchMatching. The result will be 0-N objects in a List, 
     * all matching those criteria you specified. Those instance-variables that
     * have NULL values are excluded in search-criteria.
     *
     * @param conn         This method requires working database connection.
     * @param valueObject  This parameter contains the class instance where search will be based.
     *                     Primary-key field should not be set.
     */
    public List searchMatching(Connection conn, Files valueObject) throws SQLException {

          List searchResults;

          boolean first = true;
          StringBuffer sql = new StringBuffer("SELECT * FROM files WHERE 1=1 ");

          if (valueObject.getId() != 0) {
              if (first) { first = false; }
              sql.append("AND id = ").append(valueObject.getId()).append(" ");
          }

          if (valueObject.getEmail() != null) {
              if (first) { first = false; }
              sql.append("AND email LIKE '").append(valueObject.getEmail()).append("%' ");
          }

          if (valueObject.getFname() != null) {
              if (first) { first = false; }
              sql.append("AND fname LIKE '").append(valueObject.getFname()).append("%' ");
          }

          if (valueObject.getRevieved() != 0) {
              if (first) { first = false; }
              sql.append("AND revieved = ").append(valueObject.getRevieved()).append(" ");
          }


          sql.append("ORDER BY id ASC ");

          // Prevent accidential full table results.
          // Use loadAll if all rows must be returned.
          if (first)
               searchResults = new ArrayList();
          else
               searchResults = listQuery(conn, conn.prepareStatement(sql.toString()));

          return searchResults;
    }


   

    /**
     * databaseUpdate-method. This method is a helper method for internal use. It will execute
     * all database handling that will change the information in tables. SELECT queries will
     * not be executed here however. The return value indicates how many rows were affected.
     * This method will also make sure that if cache is used, it will reset when data changes.
     *
     * @param conn         This method requires working database connection.
     * @param stmt         This parameter contains the SQL statement to be excuted.
     */
    protected int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {

          int result = stmt.executeUpdate();

          return result;
    }



    /**
     * databaseQuery-method. This method is a helper method for internal use. It will execute
     * all database queries that will return only one row. The resultset will be converted
     * to valueObject. If no rows were found, NotFoundException will be thrown.
     *
     * @param conn         This method requires working database connection.
     * @param stmt         This parameter contains the SQL statement to be excuted.
     * @param valueObject  Class-instance where resulting data will be stored.
     */
    protected void singleQuery(Connection conn, PreparedStatement stmt, Files valueObject) 
          throws NotFoundException, SQLException {

          ResultSet result = null;

          try {
              result = stmt.executeQuery();

              if (result.next()) {

                   valueObject.setId(result.getInt("id")); 
                   valueObject.setEmail(result.getString("email")); 
                   valueObject.setFname(result.getString("fname")); 
                   valueObject.setRevieved(result.getInt("revieved")); 

              } else {
                    //System.out.println("Files Object Not Found!");
                    throw new NotFoundException("Files Object Not Found!");
              }
          } finally {
              if (result != null)
                  result.close();
              if (stmt != null)
                  stmt.close();
          }
    }


    /**
     * databaseQuery-method. This method is a helper method for internal use. It will execute
     * all database queries that will return multiple rows. The resultset will be converted
     * to the List of valueObjects. If no rows were found, an empty List will be returned.
     *
     * @param conn         This method requires working database connection.
     * @param stmt         This parameter contains the SQL statement to be excuted.
     */
    protected List listQuery(Connection conn, PreparedStatement stmt) throws SQLException {

          ArrayList searchResults = new ArrayList();
          ResultSet result = null;

          try {
              result = stmt.executeQuery();

              while (result.next()) {
                   Files temp = createValueObject();

                   temp.setId(result.getInt("id")); 
                   temp.setEmail(result.getString("email")); 
                   temp.setFname(result.getString("fname")); 
                   temp.setRevieved(result.getInt("revieved")); 

                   searchResults.add(temp);
              }

          } finally {
              if (result != null)
                  result.close();
              if (stmt != null)
                  stmt.close();
          }

          return (List)searchResults;
    }
    protected List<EditorBean> listFiles(Connection conn, PreparedStatement stmt) throws SQLException {
    	
    	ArrayList<EditorBean> searchResults = new ArrayList<EditorBean>();
    	ResultSet result = null;
    	
    	try {
    		result = stmt.executeQuery();
    		
    		while (result.next()) {
    			EditorBean temp = new EditorBean();
    			
    			temp.setEmail(result.getString("email")); 
    			temp.setName(result.getString("name")); 
    			temp.setSurname(result.getString("surname")); 
    			temp.setFname(result.getString("fname")); 
    			temp.setTitle(result.getString("title")); 
    			temp.setFid(result.getInt("id")); 
    			
    			searchResults.add(temp);
    		}
    		
    	} finally {
    		if (result != null)
    			result.close();
    		if (stmt != null)
    			stmt.close();
    	}
    	
    	return searchResults;
    }


}