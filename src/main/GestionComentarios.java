package main;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class GestionComentarios {
    public static void gestionMenu() throws SQLException{
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        while (opcion != -1){
            System.out.print(AnsiColor.BLACK.getColor());
            System.out.print("1 - Nuevo Comentario | ");
            System.out.print(AnsiColor.RED.getColor());
            System.out.print("-1 Salir");
            System.out.println(AnsiColor.RESET.getColor());
            opcion = sc.nextInt();
            if (opcion == 1){
                newComment();//este método servirá para insertar posts
            }
        }
    }

    public static void newComment() throws SQLException{
        if (Main.id_usuario == -1){
            GestionUsuarios.gestionMenu();
        }

        Connection con = Main.connection;

        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el id del post");
        int id_post = getPost();
        System.out.println("Introduce el texto");
        String texto = sc.nextLine();

        java.sql.Date fecha = new java.sql.Date(new Date().getTime());
        PreparedStatement st = con.prepareStatement("INSERT INTO comentarios (texto, fecha, id_usuario, id_post) VALUES(?, ?, ?, ?)");
        st.setString(1, texto);
        st.setDate(2, fecha);
        st.setInt(3, Main.id_usuario);
        st.setInt(4, id_post);

        st.executeUpdate();
    }

    private static int getPost() throws SQLException{
        GestionPosts.listarTodosLosPosts();
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static void printComment(ResultSet rs) throws SQLException{
        System.out.println("\t\t\t" + rs.getString(2) +
                " - " + rs.getDate(3) +
                " - " + rs.getString(4));
    }
}
