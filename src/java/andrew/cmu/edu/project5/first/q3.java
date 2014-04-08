package andrew.cmu.edu.project5.first;

import andrew.cmu.edu.project5.model.MySQLConnector;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fxqw8_000
 */
public class q3 extends HttpServlet {

    private static final String teamID = "Rainforest";
    private static final String AWSID = "2422-0942-6899";
    private MySQLConnector mySql = new MySQLConnector();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(teamID  + ", " + AWSID);
            String userid = request.getParameter("userid");
            
            ArrayList<Long> userID = mySql.getRetweetUserID(userid);
            for (long user : userID){
                out.println(user);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void destroy() {
        mySql.closeConnection();
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
    }   
    
}
