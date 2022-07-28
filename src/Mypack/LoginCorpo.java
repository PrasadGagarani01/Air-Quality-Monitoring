package Mypack;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.*;

/**
 * Servlet implementation class LoginCorpo
 */
@WebServlet("/LoginCorpo")
public class LoginCorpo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCorpo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		System.out.println("*********");
		String email=request.getParameter("email");
		String pass=request.getParameter("password");
		try
			{
				Connection conn=DBconnect.getConnect();
				PreparedStatement ps = conn.prepareStatement("select * from  corpo where email=? and pass=?");
				ps.setString(1, email);
				ps.setString(2, pass);
				ResultSet rs = ps.executeQuery();
				if(rs.next())
				{
					System.out.println("Hiiiiiiiiii");
					response.sendRedirect("welcomeCorpo.jsp");
				}
				else
				{
					request.getSession().setAttribute("msg", "Wrong User Credentials..!!");
					response.sendRedirect("loginCorpo.jsp");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		
		
	}

}
