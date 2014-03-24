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
    private Statement statement = null;
    private ResultSet result = null;

    public MySQLConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/twitter","root","rainforest");
            statement = (Statement) connect.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
            Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toCSV() {
        try {
            BufferedReader input = new BufferedReader(new FileReader(new File(inputfilename)));
            FileWriter output = new FileWriter(new File(outputfilename));
            output.write("uid, time, tid\n");
            String inputLine = null;
            while ((inputLine = input.readLine()) != null) {
                JSONObject twitter = new JSONObject(inputLine);
                String uid = twitter.getJSONObject("user").getString("id_str");
                String time = twitter.getString("created_at");
                String tid = twitter.getString("id_str");
                output.write(uid + ", " + time + ", " + tid + "\n");
            }
            output.close();
        } catch (Exception ex) {
        }
    }

    public void connectWithMySQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/twitter?" + "user=root&password=rainforest");
            statement = (Statement) connect.createStatement();
            statement.executeUpdate("CREATE TABLE twitts (uid VARCHAR(10) NOT NULL, time VARCHAR(40), tid VARCHAR(20) NOT NULL, PRIMARY KEY (uid, tid));");

            BufferedReader input = new BufferedReader(new FileReader(new File(inputfilename)));
            String inputLine = null;
            while ((inputLine = input.readLine()) != null) {
                JSONObject twitter = new JSONObject(inputLine);
                String uid = twitter.getJSONObject("user").getString("id_str");
                String time = twitter.getString("created_at");
                String tid = twitter.getString("id_str");
                statement.executeUpdate("INSERT INTO twitts (uid, time, tid) "
                        + "VALUE ('" + uid + "', '" + time + "', '" + tid + "');");
            }

            result = statement.executeQuery("select * from twitts limit 5;");
            while (result.next()) {
                System.out.print(result.getString("uid") + "\t");
                System.out.print(result.getString("time") + "\t");
                System.out.println(result.getString("tid"));
            }
            connect.close();
        } catch (Exception ex) {
            Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        MySQLConnector t = new MySQLConnector();
        ArrayList<String> result = t.getTweetID("1200266719", "'2014-01-22+12:21:45'");
        for (String tweet: result){
            System.out.println(tweet);
        }
    }

    public ArrayList<String> getTweetID(String userid, String time) {
        ArrayList<String> tweetID = new ArrayList<String>();
        try {
            //String query = "SELECT tid FROM twitts WHERE uid='" + userid+"' AND time='" + time +"';";
            String query = "SELECT tid FROM twitts WHERE uid='" + userid 
                    + "' AND time=(SELECT DATE_FORMAT('" + time + "', '%a %b %d %T +0000 %Y'));";
            result = statement.executeQuery(query);
            while(result.next()){
                tweetID.add(result.getString("tid"));
            }
            Collections.sort(tweetID);
            return tweetID;
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tweetID;
    }
}
