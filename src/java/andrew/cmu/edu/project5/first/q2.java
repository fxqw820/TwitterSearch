/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package andrew.cmu.edu.project5.first;

import andrew.cmu.edu.project5.model.MySQLConnector;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fxqw8_000
 */
public class q2 extends HttpServlet {

    private static String teamID;
    private static String AWSID;
    private MySQLConnector mySql;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); //To change body of generated methods, choose Tools | Templates.
        teamID = "Rainforest";
        AWSID = "2422-0942-6899";
        mySql = new MySQLConnector();
    }
    
    

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
            out.print(teamID  + ", " + AWSID + "\n");
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //out.println(dateFormat.format(new Date()));
            String userid = request.getParameter("userid");
            String time = request.getParameter("tweet_time");
            
            ArrayList<String> tweetID = mySql.getTweetID(userid, time);
            for (String tweet : tweetID){
                out.print(tweet + "\n");
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
