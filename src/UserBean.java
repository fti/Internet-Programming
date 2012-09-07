import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public class UserBean implements Cloneable, Serializable {

	public static final String AUTHOR = "author";
	public static final String REVIEVER = "reviever";
	public static final String EDITOR = "editor";
	public static final String USER_TYPES[] = {AUTHOR,REVIEVER,EDITOR};
	
    private String email;
    private String name;
    private String surname;
    private String password;
    private String usertype;
    private int id;


    public UserBean () {

    }
    
    public UserBean(HttpServletRequest req) {
		this.name = req.getParameter("name");
		this.surname = req.getParameter("surname");
		this.email = req.getParameter("email");
		this.password = req.getParameter("password");
		this.usertype = req.getParameter("usertype");
	}


    public UserBean (String emailIn) {

          this.email = emailIn;

    }


    public UserBean(String emailIn, String pass, String usertype) {
    	this.email = emailIn;
    	this.password = pass;
    	this.usertype = usertype;
	}

	public String getEmail() {
          return this.email;
    }
    public void setEmail(String emailIn) {
          this.email = emailIn;
    }

    public String getName() {
          return this.name;
    }
    public void setName(String nameIn) {
          this.name = nameIn;
    }

    public String getSurname() {
          return this.surname;
    }
    public void setSurname(String surnameIn) {
          this.surname = surnameIn;
    }

    public String getPassword() {
          return this.password;
    }
    public void setPassword(String passwordIn) {
          this.password = passwordIn;
    }

    public String getUsertype() {
          return this.usertype;
    }
    public void setUsertype(String usertypeIn) {
          this.usertype = usertypeIn;
    }

    public int getId() {
          return this.id;
    }
    public void setId(int idIn) {
          this.id = idIn;
    }


    public void setAll(String emailIn,
          String nameIn,
          String surnameIn,
          String passwordIn,
          String usertypeIn,
          int idIn) {
          this.email = emailIn;
          this.name = nameIn;
          this.surname = surnameIn;
          this.password = passwordIn;
          this.usertype = usertypeIn;
          this.id = idIn;
    }

    public boolean hasEqualMapping(UserBean valueObject) {

          if (this.email == null) {
                    if (valueObject.getEmail() != null)
                           return(false);
          } else if (!this.email.equals(valueObject.getEmail())) {
                    return(false);
          }
          if (this.name == null) {
                    if (valueObject.getName() != null)
                           return(false);
          } else if (!this.name.equals(valueObject.getName())) {
                    return(false);
          }
          if (this.surname == null) {
                    if (valueObject.getSurname() != null)
                           return(false);
          } else if (!this.surname.equals(valueObject.getSurname())) {
                    return(false);
          }
          if (this.password == null) {
                    if (valueObject.getPassword() != null)
                           return(false);
          } else if (!this.password.equals(valueObject.getPassword())) {
                    return(false);
          }
          if (this.usertype == null) {
                    if (valueObject.getUsertype() != null)
                           return(false);
          } else if (!this.usertype.equals(valueObject.getUsertype())) {
                    return(false);
          }
          if (valueObject.getId() != this.id) {
                    return(false);
          }

          return true;
    }


    public Object clone() {
        UserBean cloned = new UserBean();

        if (this.email != null)
             cloned.setEmail(new String(this.email)); 
        if (this.name != null)
             cloned.setName(new String(this.name)); 
        if (this.surname != null)
             cloned.setSurname(new String(this.surname)); 
        if (this.password != null)
             cloned.setPassword(new String(this.password)); 
        if (this.usertype != null)
             cloned.setUsertype(new String(this.usertype)); 
        cloned.setId(this.id); 
        return cloned;
    }

}
