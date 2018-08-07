package another;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/upLoad1")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class Upload extends HttpServlet {

	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "anne";
	private static final String PASSWORD = "g122003744";
	private static final String SQL = "INSERT INTO CLUB(ID, NAME, PIC)" + "VALUES(CLUB_SEQ, ?, ?)";
	private static final long serialVersionUID = 1L;

	Connection con;
	PreparedStatement pstm;
	InputStream input;

	public Upload() {
		super();
	}

	// 上傳用POST
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("big5");
		res.setContentType("text/html;charset=big5 ");

		PrintWriter out = res.getWriter();

		// ServletContext context = getServletContext();

		/////////////////////////// 尚未實作自增主鍵值//////////////////////////////////////////////////

		Collection<Part> parts = req.getParts();

		out.println("<form method=get>");

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String[] cols = { "ID" };
			pstm = con.prepareStatement(SQL, cols);

			for (Part part : parts) {
				if (getFileNameFromPart(part) != null && part.getContentType() != null) {
					out.println("<pre>");

					// test show the item buffer length
					input = part.getInputStream();
					// SQL = "INSERT INTO CLUB(ID, NAME, PIC)" + "VALUES(?, ?,
					// ?)";

					pstm.setString(1, getFileNameFromPart(part));
					Blob blob = con.createBlob();
					byte[] imgArray = getPictureByteArray(input);
					blob.setBytes(1, imgArray);
					pstm.setBlob(2, blob);

					pstm.executeUpdate();
					pstm.clearParameters();

					out.println("</pre>");
				}
			}

			out.println("<input type=\"submit\" value=\"刪除\" >");
			out.println("</form>");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	}

	// 取出上傳檔案名稱
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header);
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println(filename);
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

	public static byte[] getPictureByteArray(InputStream input) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buffer = new byte[8 * 1024];
		int i;
		while ((i = input.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		input.close();

		return baos.toByteArray();
	}

}

// String filename = getFileNameFromPart(part);

// File(File parent, String child) -> 前者拿到檔案前面路徑，後者拿到主要檔案名稱。
// f 會取得此檔的完整路徑

// int dop = filename.indexOf(".");
// String fno = filename.substring(0, dop);

// "INSERT INTO CLUB(ID, NAME, PIC)" + "VALUES(?, ?, ?)"
// pstm = con.prepareStatement(SQL);
//
// pstm.setInt(1, 1);
// pstm.setString(2, fno);
// Blob blob = con.createBlob();
// byte[] pic2 = getPictureByteArray(filename);
// blob.setBytes(1, pic2);
// pstm.setBlob(3, blob);
// pstm.executeUpdate();

// A convenience method to write this uploaded item to disk
// part.write(f.toString());
