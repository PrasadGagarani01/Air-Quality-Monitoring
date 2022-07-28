package Mypack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.DBconnect;

/**
 * Servlet implementation class AddDevice
 */
@WebServlet("/AddDevice")
public class AddDevice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddDevice() {
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
		
		String loc=request.getParameter("locTxt");
		String area=request.getParameter("areaTxt");
		String temp = "0";
		String hum = "0";
		String mq2 = "0";
		String mq3 = "0";
		String mq135 = "0";
		String addedDate;
		String addedTime;
		String sql = null;
		PreparedStatement ps1=null;
		
		HttpSession session = request.getSession(true); // reuse existing
		session.setAttribute("user",area);

		Connection conn = DBconnect.getConnect();
		sql="select id from airquuality where id='"+id+"'";
		try {
			ps1=conn.prepareStatement("insert into device values(?,?,?,?,?,?,?,?)");
			
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
				DateTimeFormatter dtf1=DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime now1=LocalDateTime.now();
				DateTimeFormatter dtf2=DateTimeFormatter.ofPattern("HH-mm-ss");
				LocalDateTime now2=LocalDateTime.now();
				addedDate=dtf1.format(now1);
				addedTime=dtf2.format(now2);
				
				
				ps1.setInt(1, id);
				ps1.setString(2, loc);
				ps1.setString(3, area);
				ps1.setString(4, temp);
				ps1.setString(5, hum);
				ps1.setString(6, mq2);
				ps1.setString(7, mq3);
				ps1.setString(8, mq135);
				
			
				int n= ps1.executeUpdate();
				System.out.println("Recort inserted");
				if(n>=1)
				{
					request.getSession().setAttribute("msg", "Record Inserted Successfully..!!");
					response.sendRedirect("welcomeCorpo.jsp"); 
				}
				else
				{
					
					request.setAttribute("msg", "Record Failed To Insert..!!");
					response.sendRedirect("welcomeCorpo.jsp"); 
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	}

}
