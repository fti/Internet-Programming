import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<String> errors = UserController.regUserContr(req);
		RequestDispatcher dispatcher   = req.getRequestDispatcher("/register.jsp");
		
		if(!errors.isEmpty()){
			req.setAttribute("errors", errors);
			dispatcher.forward(req, resp);
			//resp.sendRedirect("/InternetProgramming/register.jsp");
		}else{
		/*
		 * Control email is created before if not create else response error to user
		 */
		
		UserBean userBean = new UserBean(req);
		Connection conn = DaoCon.getConnection();
		UserBeanDao dao = new UserBeanDao();
		try {
			if(dao.create(conn, userBean)){
				req.setAttribute("info", "Successfuly registered, You can Login");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}else{
				errors.add("Email is used before");
				req.setAttribute("errors", errors);
				dispatcher.forward(req, resp);
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
			
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}
	
}
