import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String link = req.getParameter("link");
		RequestDispatcher disp = req.getRequestDispatcher("/reviever");
		if (UserController.nullCont(link)) {
			req.setAttribute("info", "sorry for this but an error has occured" );
		}else {
			File f = new File(req.getServletContext().getRealPath(link));
	        int  length   = 0;
	        ServletOutputStream op       = resp.getOutputStream();
	        ServletContext  context  = getServletConfig().getServletContext();
	        String  mimetype = context.getMimeType( link );

	        resp.setContentType( (mimetype != null) ? mimetype : "application/pdf" );
	        resp.setContentLength( (int)f.length() );
	        resp.setHeader( "Content-Disposition", "attachment; filename=\"" + link + "\"" );

	        byte[] bbuf = new byte[1024];
	        DataInputStream in = new DataInputStream(new FileInputStream(f));

	        while ((in != null) && ((length = in.read(bbuf)) != -1))
	        {
	            op.write(bbuf,0,length);
	        }

	        in.close();
	        op.flush();
	        op.close();

		}
		disp.forward(req, resp);
	}
}
