package com.halima.registration;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Signin")
public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_email=request.getParameter("username");
		String user_pwd=request.getParameter("password");
        RequestDispatcher dispatcher = null;
        Connection con = null;
        HttpSession session=request.getSession();
        
        if(user_email==null || user_email.equals(""))
        {
        	request.setAttribute("status", "invalid email");
            dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
        if(user_pwd==null || user_pwd.equals(""))
        {
        	request.setAttribute("status", "invalid pwd");
            dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
        
		try
		{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?useSSL=false", "root", "Halima@2002");
            PreparedStatement pst = con.prepareStatement("select * from users where user_email=? and user_pwd=?");
            pst.setString(1, user_email);
            pst.setString(2, user_pwd);
            
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
            	session.setAttribute("name", rs.getString("user_name"));
                dispatcher = request.getRequestDispatcher("index.jsp");
            }
            else
            {
            	request.setAttribute("status","failed");
                dispatcher = request.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(request, response);
		}
		catch(Exception e) {
            e.printStackTrace();			
		}
	}

}
