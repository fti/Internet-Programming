import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

/*
 * This servlet provides uploading pdf files to the server by author
 * 
 * All controlls provided by us. Only pdf files can be uploaded.
 * */
@WebServlet("/author")
public class AuthorServlet extends HttpServlet{

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		String title = "";
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (isMultipart) {
		
			ServletFileUpload upload = new ServletFileUpload();
	
			try {
				FileItemIterator iter=upload.getItemIterator(req);
				while (iter.hasNext()) {
				    FileItemStream item = iter.next();
				    String name = item.getFieldName();
					InputStream stream = item.openStream();
					    if (item.isFormField()) {
					        title = Streams.asString(stream);
					        if (title == null || "".equals(title)) {
					        	req.setAttribute("info", "Title cannot be null");
					        	req.getRequestDispatcher("/author.jsp").forward(req, resp);
					        	return;
							}
					    } else {
					        System.out.println("File field " + name + " with file name "
					            + item.getName() + " detected.");
					        if (!"application/pdf".equals(item.getContentType())) {
								req.setAttribute("info", "Sorry only pdf files you can send. The project tutorial says ");
								break;
							}
					        File f = new File(req.getServletContext().getRealPath("/files/"+item.getName()));
					       OutputStream outputStream = new FileOutputStream(f);
					       byte buf[]=new byte[1024];
					       int len;
					       while((len=stream.read(buf))>0)
					       outputStream.write(buf,0,len);
					       outputStream.close();
					       stream.close();
					       saveOnDb(user.getEmail(),"/files/"+item.getName(), title );
					       req.setAttribute("info", "Successfully uploaded");
					    }
					}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		req.getRequestDispatcher("/author.jsp").forward(req, resp);
		
	}

	private void saveOnDb(String email, String path, String title) {
		FilesDao dao = new FilesDao();
		Connection conn = DaoCon.getConnection();
		try {
			dao.create(conn, new Files(email, path, title));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void processUploadedFile(FileItem item) {
		// Process a file upload
		if (true) {
		    File uploadedFile = new File("/files/"+item.getName());
		    try {
				item.write(uploadedFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
//		    InputStream uploadedStream = item.getInputStream();
//		    uploadedStream.close();
		}
		
	}

	private void processFormField(FileItem item) {
		if (!item.isFormField()) {
		    String fieldName = item.getFieldName();
		    String fileName = item.getName();
		    String contentType = item.getContentType();
		    boolean isInMemory = item.isInMemory();
		    long sizeInBytes = item.getSize();
		}
		
	}

}
