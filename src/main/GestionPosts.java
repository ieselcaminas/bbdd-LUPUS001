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
            System.out.println(AnsiColor.GREEN.getColor());
            System.out.print(" 1 - Nuevo Post | ");
            System.out.print(" 2 - Todos los Posts | ");
            System.out.print(" 3 - Mis Posts | ");
            System.out.print(AnsiColor.RED.getColor());
            System.out.println(" -1 - Salir");
            System.out.print(AnsiColor.RESET.getColor());

            opcion = sc.nextInt();

            if (opcion == 1){
                newPost();
            } else if (opcion == 2){
                listarTodosLosPostsConComentarios();
            } else if (opcion == 3){
                listarTodosMisPosts();
            }
        }
    }

    public static void listarTodosLosPosts() throws SQLException{//importante recordar el poner el throws SQLException por los datos con los que estamos trabajando
        Connection con = Main.connection;
        //Connection con -> representa una conexión a la base de datos
        //Main.connection -> una conexion que viene del main
        PreparedStatement st = con.prepareStatement(
                "SELECT p.id, p.texto, p.likes, p.fecha, u.nombre" +
                    "FROM posts AS p INNER JOIN usuarios AS u" +
                    "ON p.id_usuario = u.id"
                );
        ResultSet rs = st.executeQuery();

        while (rs.next()){//Mientras haya datos, seguirá ejecutando, de ahí el next
            //Podríamos poner el código aquí, pero mejor hacerlo en un método y así lo podemos reutilizar en el resto del código
            printPost(rs);
        }
    }

    public static void listarTodosLosPostsConComentarios() throws SQLException{
        java.sql.Connection con = Main.connection;
        PreparedStatement st = con.prepareStatement("SELECT * FROM posts");
        ResultSet rs = st.executeQuery();

        while (rs.next()){
            printPost(rs);
            //a diferencia del metodo de listarTodosLosPosts, como este método también incluye
            //los comentarios, añadimos también printComments()
            printComments(rs.getInt(1));
        }
    }

    public static void listarTodosMisPosts() throws SQLException{
        Connection con = Main.connection;
        PreparedStatement st = con.prepareStatement(
                "SELECT p.id, p.texto, p.likes, p.fecha, u.nombre" +
                        " FROM posts AS p INNER JOIN usuarios AS u" +
                        " ON p.id_usuario = u.id WHERE u.id = ?"
        );
        st.setInt(1, id_usuario);
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            printPost(rs);
            printComments(rs.getInt(1));
        }
    }

    public static void printPost(ResultSet rs) throws SQLException {
        System.out.println(rs.getInt(1) + " " + rs.getString(2) +
                " likes: " + rs.getInt(3) + " " + rs.getDate(4) + rs.getString(5));
    }

    public static void printComments(int idPost) throws SQLException{
        Connection con = Main.connection;

        PreparedStatement st = con.prepareStatement(
                "SELECT c.id, c.texto, c.fecha, u.nombre FROM comentarios as c" +
                    " INNER JOIN usuarios as u ON c.id_usuario = u.id" +
                    " INNER JOIN posts as p ON c.id_post = p.id" +
                    " WHERE p.id = ?"
        );
        st.setInt(1, idPost);
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            GestionComentarios.printComment(rs);
        }
    }


    //en este método le pediremos al usuario los datos que introduciremos al post, utilizando un Scanner
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
