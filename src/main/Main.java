import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static java.sql.Connection connection;

    public static void main(String[] args) throws SQLException {
        connection = getConnection();
        Scanner sc = new Scanner(System.in);
        int option = 0;
        while (option != -1){
            System.out.println("1 - Usuarios");
            System.out.println("2 - Posts");
            System.out.println("3 - Comentarios");
            option = sc.nextInt();
            if (option == 1){
                GestionUsuarios.gestionMenu();
            }
        }
    }

    public static java.sql.Connection getConnection(){
        String host = "jbdc:sqlite:src/main/resources/network.sqlite";

        if (connection == null){
            try {
                connection = java.sql.DriverManager.getConnection(host);
            } catch (SQLException sql){
                System.out.println(sql.getMessage());
                System.exit(0);
            }
        }
        return connection;
    }
}
