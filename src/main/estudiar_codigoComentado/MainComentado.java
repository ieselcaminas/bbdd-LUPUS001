package main.estudiar_codigoComentado;
import main.AnsiColor;
import main.GestionComentarios;
import main.GestionPosts;
import main.GestionUsuarios;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MainComentado {
    static Connection connection;
    static String usuario = "";
    static int id_usuario = -1;

    public static void main(String[] args) throws SQLException {
        printBanner();

        connection = getConnection();

        gestionMenu();
    }

    private static void gestionMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int option = 0;

        while (option != -1) {
            if (!usuario.isEmpty()) {
                System.out.println(usuario + " | ");
            }
            System.out.println(AnsiColor.GREEN.getColor());
            System.out.print(" 1 - Usuarios | ");
            System.out.print(" 2 - Posts | ");
            System.out.print(" 3 - Comentarios | ");
            System.out.print(AnsiColor.RED.getColor());
            System.out.println(" -1 - Salir");
            System.out.println(AnsiColor.RESET.getColor());
            option = sc.nextInt();
            if (option == 1) {
                GestionUsuarios.gestionMenu();
            } else if (option == 2) {
                GestionPosts.gestionMenu();
            } else if (option == 3) {
                GestionComentarios.gestionMenu();
            }
        }
    }

    //asci art
    private static void printBanner(){
        System.out.println(AnsiColor.BLACK.getColor());
        System.out.println("\n" +
                "\n" +
                " _   _      _ _        __        __         _     _ _ \n" +
                "| | | | ___| | | ___   \\ \\      / /__  _ __| | __| | |\n" +
                "| |_| |/ _ \\ | |/ _ \\   \\ \\ /\\ / / _ \\| '__| |/ _` | |\n" +
                "|  _  |  __/ | | (_) |   \\ V  V / (_) | |  | | (_| |_|\n" +
                "|_| |_|\\___|_|_|\\___/     \\_/\\_/ \\___/|_|  |_|\\__,_(_)\n" +
                "\n");
        System.out.println(AnsiColor.BLUE.getColor());
    }

    public static Connection getConnection(){
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
