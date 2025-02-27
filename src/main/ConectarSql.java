package main;import javax.xml.transform.Result;
import java.sql.*;

public class ConectarSql {

    static java.sql.Connection connection;

    public static java.sql.Connection getConnection(){
        String host = "jdbc:sqlite:src/main/resources/network.sqlite";
        if (connection == null) {
            try {
                connection = java.sql.DriverManager.getConnection(host);
            }catch (SQLException sql){
                System.out.println(sql.getMessage());
                System.exit(0);
            }
        }
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        java.sql.Connection con = getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");
        //stmt.executeUpdate("CREATE TABLE T1 (c1 varchar(50))");
        while (rs.next()){
            System.out.println(rs.getInt(1));
            System.out.println("\t" + rs.getString(2));
            System.out.println("\t" + rs.getString(3));
        }
        stmt.close();

        insertUser(); /*1*/
        //insertUserPreparedStatement(); /*2*/
        //deleteUserPreparedStatement(); /*3*/
    }

    //Añadimos el usuario
    public static void insertUser() throws SQLException{
        String sql = "INSERT INTO usuarios (nombre, apellidos) VALUES ('Janet', 'Espinosa')";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

    public static void insertUserPreparedStatement() throws SQLException{
        PreparedStatement st = null;
        String sql = "INSERT INTO usuarios (nombre, apellidos) VALUES (?, ?)";
        st = connection.prepareStatement(sql);
        st.setString(1, "Juan");
        st.setString(2, "Martínez");
        st.executeUpdate();
    }

     public static void deleteUserPreparedStatement() throws SQLException {
        PreparedStatement st = null;
        String sql = "DELETE FROM usuarios WHERE id = ?";
        st = connection.prepareStatement(sql);
        st.setInt(1, 5);
        st.executeUpdate();
     }

     public static void selectedPrepared() throws SQLException{
        PreparedStatement st = null;
        String sql = "SELECT FROM usuarios WHERE id= ?";
        st = connection.prepareStatement(sql);
        st.setInt(1, 5);
        ResultSet rs = st.executeQuery();
     }
}