package Mypack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.*;

/**
 * Servlet implementation class AddCorp
 */
@WebServlet("/AddCorp")
public class AddCorp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCorp() {
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

		PrintWriter out = response.getWriter();
		{
			//System.out.println("hiiiiiiiiiiiii");
		int id=0;
		
		String name=request.getParameter("nameTxt");
		String email=request.getParameter("emailTxt");
		String password=request.getParameter("passwordTxt");
		String contact=request.getParameter("ContactTxt");
		String city=request.getParameter("cityTxt");
		
		String sql=null;
		PreparedStatement ps1=null;
		
		HttpSession session = request.getSession(true); // reuse existing
		session.setAttribute("user",contact);

		Connection conn = DBconnect.getConnect();
		sql="select id from corpo where id='"+id+"'";
		try {
			ps1=conn.prepareStatement("insert into corpo values(?,?,?,?,?,?)");
			
			//HttpSession session = request.getSession(true); // reuse existing
			
			//session.setAttribute("user",id);
		
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet r= ps.executeQuery();
		
			if(r.next())
			{
				request.getSession().setAttribute("msg", "Duplicate ID, Records Already Exist..!!");
        		response.sendRedirect("welcomeAdmin.jsp"); 
			}else
			{
				ps1.setInt(1, id);
				ps1.setString(2, name);
				ps1.setString(3, email);
				ps1.setString(4, password);
				ps1.setString(5, contact);
				ps1.setString(6, city);
				
			
				int n= ps1.executeUpdate();
				System.out.println("Recort inserted");
				if(n>=1)
				{
					request.getSession().setAttribute("msg", "Record Inserted Successfully..!!");
					response.sendRedirect("welcomeAdmin.jsp"); 
				}
				else
				{
					
					request.setAttribute("msg", "Record Failed To Insert..!!");
					response.sendRedirect("welcomeAdmin.jsp"); 
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		
		
	}

}
