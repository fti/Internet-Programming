import java.sql.*;
import java.util.*;


public class UserBeanDao {

    public UserBean createValueObject() {
          return new UserBean();
    }

    public UserBean getObject(Connection conn, String email) throws NotFoundException, SQLException {

          UserBean valueObject = createValueObject();
          valueObject.setEmail(email);
          load(conn, valueObject);
          return valueObject;
    }


    public void load(Connection conn, UserBean valueObject) throws NotFoundException, SQLException {

          if (valueObject.getEmail() == null) {
               //System.out.println("Can not select without Primary-Key!");
               throw new NotFoundException("Can not select without Primary-Key!");
          }

          String sql = "SELECT * FROM userbean WHERE (email = ? ) "; 
          PreparedStatement stmt = null;

          try {
               stmt = conn.prepareStatement(sql);
               stmt.setString(1, valueObject.getEmail()); 

               singleQuery(conn, stmt, valueObject);

          } finally {
              if (stmt != null)
                  stmt.close();
          }
    }
    public UserBean loadUser(Connection conn, UserBean valueObject) throws NotFoundException, SQLException {
    	
    	if (valueObject.getEmail() == null) {
    		//System.out.println("Can not select without Primary-Key!");
    		throw new NotFoundException("Can not select without Primary-Key!");
    	}
    	
    	String sql = "SELECT * FROM userbean WHERE (email = ? AND password = ?) "; 
    	PreparedStatement stmt = null;
    	
    	try {
    		stmt = conn.prepareStatement(sql);
    		stmt.setString(1, valueObject.getEmail()); 
    		stmt.setString(2, valueObject.getPassword()); 
    		
    		boolean aaa = singleQuery(conn, stmt, valueObject);
    		if (!aaa) {
				valueObject = null;
			}
    		
    	} finally {
    		if (stmt != null)
    			stmt.close();
    	}
    	return valueObject;
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

          String sql = "SELECT * FROM userbean ORDER BY email ASC ";
          List searchResults = listQuery(conn, conn.prepareStatement(sql));

          return searchResults;
    }
    

	public List<UserBean> loadRevievers(Connection conn) throws SQLException {
		String sql = "SELECT * FROM userbean where usertype = 'reviever' ORDER BY email ASC ";
        List<UserBean> searchResults = listQuery(conn, conn.prepareStatement(sql));

        return searchResults;
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
    public synchronized boolean create(Connection conn, UserBean valueObject) throws SQLException {

          String sql = "";
          PreparedStatement stmt = null;
          ResultSet result = null;
          boolean succesfull = true;
          try {
               sql = "INSERT INTO userbean ( email, name, surname, "
               + "password, usertype) select ?, ?, ?, ?, ? where not exists(select * from userbean where email=?)";
               stmt = conn.prepareStatement(sql);

               stmt.setString(1, valueObject.getEmail()); 
               stmt.setString(2, valueObject.getName()); 
               stmt.setString(3, valueObject.getSurname()); 
               stmt.setString(4, valueObject.getPassword()); 
               stmt.setString(5, valueObject.getUsertype()); 
               stmt.setString(6, valueObject.getEmail()); 

               int rowcount = databaseUpdate(conn, stmt);
               if (rowcount != 1) {
                    //System.out.println("PrimaryKey Error when updating DB!");
                    //throw new SQLException("PrimaryKey Error when updating DB!");
            	   succesfull = false;
               }

          } finally {
              if (stmt != null)
                  stmt.close();
          }
          return succesfull;
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
    public void save(Connection conn, UserBean valueObject) 
          throws NotFoundException, SQLException {

          String sql = "UPDATE userbean SET name = ?, surname = ?, password = ?, "
               + "usertype = ?, id = ? WHERE (email = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setString(1, valueObject.getName()); 
              stmt.setString(2, valueObject.getSurname()); 
              stmt.setString(3, valueObject.getPassword()); 
              stmt.setString(4, valueObject.getUsertype()); 
              stmt.setInt(5, valueObject.getId()); 

              stmt.setString(6, valueObject.getEmail()); 

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
    public void delete(Connection conn, UserBean valueObject) 
          throws NotFoundException, SQLException {

          if (valueObject.getEmail() == null) {
               //System.out.println("Can not delete without Primary-Key!");
               throw new NotFoundException("Can not delete without Primary-Key!");
          }

          String sql = "DELETE FROM userbean WHERE (email = ? ) ";
          PreparedStatement stmt = null;

          try {
              stmt = conn.prepareStatement(sql);
              stmt.setString(1, valueObject.getEmail()); 

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

          String sql = "DELETE FROM userbean";
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

          String sql = "SELECT count(*) FROM userbean";
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
    public List searchMatching(Connection conn, UserBean valueObject) throws SQLException {

          List searchResults;

          boolean first = true;
          StringBuffer sql = new StringBuffer("SELECT * FROM userbean WHERE 1=1 ");

          if (valueObject.getEmail() != null) {
              if (first) { first = false; }
              sql.append("AND email LIKE '").append(valueObject.getEmail()).append("%' ");
          }

          if (valueObject.getName() != null) {
              if (first) { first = false; }
              sql.append("AND name LIKE '").append(valueObject.getName()).append("%' ");
          }

          if (valueObject.getSurname() != null) {
              if (first) { first = false; }
              sql.append("AND surname LIKE '").append(valueObject.getSurname()).append("%' ");
          }

          if (valueObject.getPassword() != null) {
              if (first) { first = false; }
              sql.append("AND password LIKE '").append(valueObject.getPassword()).append("%' ");
          }

          if (valueObject.getUsertype() != null) {
              if (first) { first = false; }
              sql.append("AND usertype LIKE '").append(valueObject.getUsertype()).append("%' ");
          }

          if (valueObject.getId() != 0) {
              if (first) { first = false; }
              sql.append("AND id = ").append(valueObject.getId()).append(" ");
          }


          sql.append("ORDER BY email ASC ");

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
    protected boolean singleQuery(Connection conn, PreparedStatement stmt, UserBean valueObject) 
          throws NotFoundException, SQLException {

          ResultSet result = null;
          boolean isit = false;
          try {
              result = stmt.executeQuery();
              if (result.next()) {
            	  isit=true;

                   valueObject.setEmail(result.getString("email")); 
                   valueObject.setName(result.getString("name")); 
                   valueObject.setSurname(result.getString("surname")); 
                   valueObject.setPassword(result.getString("password")); 
                   valueObject.setUsertype(result.getString("usertype")); 
                   valueObject.setId(result.getInt("id")); 

              } else {
                    //System.out.println("UserBean Object Not Found!");
                   // throw new NotFoundException("UserBean Object Not Found!");
              }
          } finally {
              if (result != null)
                  result.close();
              if (stmt != null)
                  stmt.close();
          }
          return isit;
    }


    /**
     * databaseQuery-method. This method is a helper method for internal use. It will execute
     * all database queries that will return multiple rows. The resultset will be converted
     * to the List of valueObjects. If no rows were found, an empty List will be returned.
     *
     * @param conn         This method requires working database connection.
     * @param stmt         This parameter contains the SQL statement to be excuted.
     */
    protected List<UserBean> listQuery(Connection conn, PreparedStatement stmt) throws SQLException {

          ArrayList<UserBean> searchResults = new ArrayList<UserBean>();
          ResultSet result = null;

          try {
              result = stmt.executeQuery();

              while (result.next()) {
                   UserBean temp = createValueObject();

                   temp.setEmail(result.getString("email")); 
                   temp.setName(result.getString("name")); 
                   temp.setSurname(result.getString("surname")); 
                   temp.setPassword(result.getString("password")); 
                   temp.setUsertype(result.getString("usertype")); 
                   temp.setId(result.getInt("id")); 

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