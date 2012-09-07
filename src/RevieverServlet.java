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

@WebServlet("/reviever")
public class RevieverServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.getAttribute("user") == null) {
			resp.sendRedirect(req.getContextPath()+"/");
			return;
		}
		
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null) {
			resp.sendRedirect(req.getContextPath()+"/");
			return;
		}
		FilesDao dao = new FilesDao();
		try {
			List<EditorBean> pageList = dao.getRevievers(DaoCon.getConnection(), userBean.getEmail());
			req.setAttribute("pageList", pageList);
			req.getRequestDispatcher("/reviever.jsp").forward(req, resp);
		} catch (SQLException e) {
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
