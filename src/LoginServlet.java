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
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<String> errors = UserController.loginContr(req);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
		
		if(!errors.isEmpty()){
			req.setAttribute("errors", errors);
			dispatcher.forward(req, resp);
			return;
		}
		String usertype = req.getParameter("usertype");
		//UserBean userBean = new UserBean(req);
		Connection conn = DaoCon.getConnection();
		UserBeanDao dao = new UserBeanDao();
		UserBean user = new UserBean( req.getParameter("email"),req.getParameter("password"), req.getParameter("usertype"));
		try {
			user = dao.loadUser(conn, user);
			if(user == null){
				errors.add("Email/Password is invalid");
				req.setAttribute("errors", errors);
				dispatcher.forward(req, resp);
				return;
			}
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			if (UserBean.REVIEVER.equals(user.getUsertype()) && UserBean.REVIEVER.equals(usertype)) {
				req.getRequestDispatcher("/reviever").forward(req, resp);
			}else if (UserBean.EDITOR.equals(user.getUsertype()) && UserBean.EDITOR.equals(usertype)) {
				req.getRequestDispatcher("/editor").forward(req, resp);
			}else{
				req.getRequestDispatcher("/author.jsp").forward(req, resp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}
	
}
