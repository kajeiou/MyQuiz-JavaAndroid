package my.quiz;;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    Connection con;
    private static final String url = "jdbc:mysql://176.182.249.180:1234/myquiz";
    private static final String user = "root";
    private static final String password = "root";

    public static Statement Connexion() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            return st;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean DbTestConnection() {
        ResultSet résultats = null;
        String requete = "SELECT * FROM utilisateurs";
        try {
            Statement stmt = con.createStatement();
            résultats = stmt.executeQuery(requete);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
