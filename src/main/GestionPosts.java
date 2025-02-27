package main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import static main.Main.id_usuario;

public class GestionPosts {
    public static void gestionMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        String usuario = "";

        while (opcion != -1){
            System.out.print(" 1 - Nuevo Post | ");
            System.out.print(" -1 - Salir");

            opcion = sc.nextInt();

            if (opcion == 1){
                newPost();
            } else if (opcion == 2){
                listarTodosLosPosts();
            }
        }
    }

    private static void listarTodosLosPosts() throws SQLException{//importante recordar el poner el throws SQLException por los datos con los que estamos trabajando
        Connection con = Main.connection;
        //Connection con -> representa una conexión a la base de datos
        //Main.connection ->
        PreparedStatement st = con.prepareStatement("SELECT * FROM posts");
        ResultSet rs = st.executeQuery();

        while (rs.next()){

        }
    }

    private static void listarTodosLosPosts2() throws SQLException{
        java.sql.Connection con = Main.connection;
        PreparedStatement st = con.prepareStatement("SELECT * FROM posts");
        ResultSet rs = st.executeQuery();

        while (rs.next()){

        }
    }


    public static void newPost() throws SQLException{
        if (Main.id_usuario == -1){
            GestionUsuarios.gestionMenu();
            return;
        }

        Connection con = Main.connection;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el texto");
        String texto = sc.nextLine();
        java.sql.Date fecha = new java.sql.Date(new Date().getTime());
        PreparedStatement st = con.prepareStatement("INSERT INTO posts(texto, likes, fecha, id_usuario) VALUES (?, ?, ?, ?)");
        st.setString(1, texto);
        st.setInt(2, 0);
        st.setDate(3, fecha);
        st.setInt(4, Main.id_usuario);
        st.executeUpdate();
    }



}
