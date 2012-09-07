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

@WebServlet("/editor")
public class EditorServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if (req.getSession().getAttribute("user") == null) {
			resp.sendRedirect(req.getContextPath()+"/");
			return;
		}
		Connection conn = DaoCon.getConnection();
		FilesDao dao = new FilesDao();
		if ("assign".equals(req.getParameter("method"))) {
			String fid = req.getParameter("fid");
			if (req.getParameter("rev1").equals(req.getParameter("rev2"))) {
				req.setAttribute("info", "Reviever 1 and 2 cannot be the same");
			}else if (!"".equals(fid) && fid != null) {
				int fidi = Integer.parseInt(fid);
				try {
					dao.insertfilerev(conn, fidi, req.getParameter("rev1"));
					dao.insertfilerev(conn, fidi, req.getParameter("rev2"));
					dao.save(conn, fidi);
					req.setAttribute("info", "Succesfully Assigned");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
		UserBeanDao userDao = new UserBeanDao();
		
		try {
			List<EditorBean> fileList = dao.loadFiles(conn);
			List<UserBean> revList = userDao.loadRevievers(conn);
			req.setAttribute("users", revList);
			req.setAttribute("filelist", fileList);
			req.getRequestDispatcher("/editor.jsp").forward(req, resp);
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
