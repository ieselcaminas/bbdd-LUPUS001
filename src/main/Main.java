package main;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static java.sql.Connection connection;
    static String usuario = "";
    static int id_usuario = -1;
    public static void main(String[] args) throws SQLException {
        connection = getConnection();
        Scanner sc = new Scanner(System.in);
        int option = 0;

        while (option != -1){
            if (!usuario.isEmpty()){
                System.out.println(usuario + " | ");
            }
            System.out.print(" 1 - Usuarios | ");
            System.out.print(" 2 - Posts | ");
            System.out.print(" 3 - Comentarios | ");
            System.out.println(" -1 - Salir");
            option = sc.nextInt();
            if (option == 1){
                GestionUsuarios.gestionMenu();
            }else if (option == 2){
                GestionPosts.gestionMenu();
            }else if (option == 3){
                GestionComentarios.gestionMenu();
            }
        }
    }

    public static java.sql.Connection getConnection(){
        String host = "jdbc:sqlite:src/main/resources/network.sqlite";

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
