import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class UserController {

	public static List<String> regUserContr(HttpServletRequest req){
		List<String> errors = new ArrayList<String>();
		
		if (nullCont(req.getParameter("name"))){
			errors.add("Name cannot be null");
		}
		if (nullCont(req.getParameter("surname"))){
			errors.add("Surname cannot be null");
		}
		if (nullCont(req.getParameter("password"))){
			errors.add("Password cannot be null");
		}
		if (nullCont(req.getParameter("email"))){
			errors.add("Email cannot be null");
		}else if (!emailCont(req.getParameter("email"))){
			errors.add("Email is invalid. ex: fatih@gmail.com");
		}
		
		return errors;
	}
	public static List<String> loginContr(HttpServletRequest req){
		List<String> errors = new ArrayList<String>();
		
		if (nullCont(req.getParameter("password"))){
			errors.add("Password cannot be null");
		}
		if (nullCont(req.getParameter("usertype"))){
			errors.add("Select an usertype for login");
		}
		if (nullCont(req.getParameter("email"))){
			errors.add("Email cannot be null");
		}else if (!emailCont(req.getParameter("email"))){
			errors.add("Email is invalid. ex: fatih@gmail.com");
		}
		
		return errors;
	}
	private static boolean emailCont(String s) {
		
	    Matcher m = Pattern.compile(".+@.+\\.[a-z]+").matcher(s);

        if (m.matches())
          return true;
		
		return false;
	}
	public static boolean nullCont(String s){
		if (s == null || "".equals(s)) {
			return true;
		}
		return false;
	}
}
