import java.io.Serializable;

public class Files implements Cloneable, Serializable {

    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private int id;
    private String email;
    private String fname;
    private String title;
    private int revieved;



    public Files () {

    }
    public Files (String email, String path, String title) {
    	this.email = email;
    	this.fname = path;
    	this.title = title;
    	this.revieved = 0;
    }

    public Files (int idIn) {

          this.id = idIn;

    }

    public int getId() {
          return this.id;
    }
    public void setId(int idIn) {
          this.id = idIn;
    }

    public String getEmail() {
          return this.email;
    }
    public void setEmail(String emailIn) {
          this.email = emailIn;
    }

    public String getFname() {
          return this.fname;
    }
    public void setFname(String fnameIn) {
          this.fname = fnameIn;
    }

    public int getRevieved() {
          return this.revieved;
    }
    public void setRevieved(int revievedIn) {
          this.revieved = revievedIn;
    }

    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

    public void setAll(int idIn,
          String emailIn,
          String fnameIn,
          int revievedIn) {
          this.id = idIn;
          this.email = emailIn;
          this.fname = fnameIn;
          this.revieved = revievedIn;
    }


    /** 
     * hasEqualMapping-method will compare two Files instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(Files valueObject) {

          if (valueObject.getId() != this.id) {
                    return(false);
          }
          if (this.email == null) {
                    if (valueObject.getEmail() != null)
                           return(false);
          } else if (!this.email.equals(valueObject.getEmail())) {
                    return(false);
          }
          if (this.fname == null) {
                    if (valueObject.getFname() != null)
                           return(false);
          } else if (!this.fname.equals(valueObject.getFname())) {
                    return(false);
          }
          if (valueObject.getRevieved() != this.revieved) {
                    return(false);
          }

          return true;
    }

}

