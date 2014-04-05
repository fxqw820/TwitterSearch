/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package andrew.cmu.edu.project5.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Tian Fangzheng
 */
public class MySQLConnector {

    private String inputfilename = "part-00000";
    private String outputfilename = "output.csv";
    private Connection connect = null;

    public MySQLConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/twitter","root","rainforest");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
            Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeConnection(){
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public static void main(String[] args) {
//        MySQLConnector t = new MySQLConnector();
//        ArrayList<String> result = t.getTweetID("1200266719", "'2014-01-22+12:21:45'");
//        for (String tweet: result){
//            System.out.println(tweet);
//        }
//    }

    public ArrayList<String> getTweetID(String userid, String time) {
        ArrayList<String> tweetID = new ArrayList<String>();
        try {
            //String query = "SELECT tid FROM twitts WHERE uid='" + userid+"' AND time='" + time +"';";
            String query = "SELECT tid FROM twitts WHERE uid='" + userid 
                    + "' AND time=(SELECT DATE_FORMAT('" + time + "', '%a %b %d %T +0000 %Y'));";
            Statement statement = (Statement) connect.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                tweetID.add(result.getString("tid"));
            }
            Collections.sort(tweetID);
            result.close();
            statement.close();
            return tweetID;
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tweetID;
    }
    
        public ArrayList<String> getRetweetUserID(String userid) {
        ArrayList<String> userID = new ArrayList<String>();
        try {
            //String query = "SELECT tid FROM twitts WHERE uid='" + userid+"' AND time='" + time +"';";
            String query = "SELECT uid FROM twitts WHERE uid='" + userid;
            Statement statement = (Statement) connect.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                userID.add(result.getString("uid"));
            }
            Collections.sort(userID);
            result.close();
            statement.close();
            return userID;
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userID;
    }
}
