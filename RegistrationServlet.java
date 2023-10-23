package com.halima.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; // Import java.sql.PreparedStatement
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user_name = request.getParameter("name");
        String user_email = request.getParameter("email");
        String user_pwd = request.getParameter("pass");
        String re_user_pwd = request.getParameter("re_pass");
        String user_mobile = request.getParameter("contact");
        RequestDispatcher dispatcher = null;
        Connection con = null;
        if(user_name==null || user_name.equals(""))
        {
        	request.setAttribute("status", "invalidName");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
        if(user_email==null || user_email.equals(""))
        {
        	request.setAttribute("status", "invalidEmail");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
        if(user_pwd==null || user_pwd.equals(""))
        {
        	request.setAttribute("status", "invalidpwd");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
        else if(!user_pwd.equals(re_user_pwd))
        {
        	request.setAttribute("status", "invalidconfirmpwd");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
        if(user_mobile==null || user_mobile.equals(""))
        {
        	request.setAttribute("status", "invalidmobilenumber");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
        else if(user_mobile.length() > 10)
        {
        	request.setAttribute("status", "invalidmobilelength");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?useSSL=false", "root", "Halima@2002");
            PreparedStatement pst = con.prepareStatement("insert into users(user_name,user_pwd,user_email,user_mobile) values(?,?,?,?)");
            pst.setString(1, user_name);
            pst.setString(2, user_pwd);
            pst.setString(3, user_email);
            pst.setString(4, user_mobile);

            int rowcount = pst.executeUpdate();
            dispatcher = request.getRequestDispatcher("registration.jsp");
            if (rowcount > 0)
            {
                request.setAttribute("status", "success");
            } else {
                request.setAttribute("status", "failed");
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
