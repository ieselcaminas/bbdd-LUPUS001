package main.estudiar_codigoComentado;

import main.AnsiColor;
import main.GestionPosts;
import main.GestionUsuarios;
import main.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class GestionComentariosComentado {
    public static void gestionMenu() throws SQLException{
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        while (opcion != -1){
            System.out.print(AnsiColor.BLACK.getColor());//Usando ANSI cambiaremos los colores de la salida
            System.out.print("1 - Nuevo Comentario | ");//si aprieta el 1, llamará al metodo newComment...*1
            System.out.print(AnsiColor.RED.getColor());
            System.out.print("-1 Salir");
            System.out.println(AnsiColor.RESET.getColor());
            opcion = sc.nextInt();
            if (opcion == 1){//*1 y se creará un nuevo comentario
                newComment();//este método servirá para insertar comentarios
            }
        }
    }

    public static void newComment() throws SQLException{
        if (MainComentado.id_usuario == -1){ //significa que no hay usuario autenticado
            GestionUsuarios.gestionMenu();//llamamos a este metodo, para que el usuario inicie sesión
        }

        //cada vez que hacemos una consulta en la base de datos, necesitamos hacer una conexión (Connection)
        Connection con = MainComentado.connection;//básicamente se utiliza para ejecutar consultas SQL. Sin conexion, el código fallará

        //pedimos datos al usuario
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el id del post");
        int id_post = getPost();//pedimos el id, y llamamos a getPost()
        System.out.println("Introduce el texto");
        String texto = sc.nextLine(); //guardamos el texto introducido en esta variable


        /*
        new Date()                 → Fri Mar 14 10:30:45 GMT 2025
        new Date().getTime()       → 1741801845000  // Milisegundos desde 1970
        new java.sql.Date(...)     → 2025-03-14
        */
        java.sql.Date fecha = new java.sql.Date(new Date().getTime());
        /*
        new Date() -> creamos un objeto de la clase 'java.util.Date' con la fecha y hora actual
        .getTime() → Obtiene los milisegundos que han pasado desde 1970
        V1
        new java.sql.Date(...) -> convertimos el objeto creado de 'java.util.Date' a 'java.sql.Date'
        Para que sea un tipo de datos compatible con la base de datos SQL
        V2
        new java.sql.Date(...) → Convierte ese tiempo en un 'java.sql.Date',
        que solo almacena la fecha (sin la hora) y es compatible con bases de datos SQL.
        */

        //Aqui contenemos la consulta SQL preparada
        PreparedStatement st = con.prepareStatement("INSERT INTO comentarios (texto, fecha, id_usuario, id_post) VALUES(?, ?, ?, ?)");
        //con -> es la conexión a la base de datos ('con' recordemos que le hemos dado el valor de MainComentado.connection)
        //.prepareStatement es para hacer una consulta con mayor seguridad gracias a los parametros ? que impiden las injerciones SQL

        //con.prepareStatement en conjunto sirve para hacer esta consulta en la base de datos
        //se usa para preparar y ejecutar consultas SQL de forma segura, eficiente y dinámica.

        //st.setXXX() -> para asignar los valores a los parámetros
        st.setString(1, texto);  // Reemplaza el primer '?' por el valor de 'texto' (comentario).
        st.setDate(2, fecha);    // Reemplaza el segundo '?' por el valor de 'fecha'.
        st.setInt(3, MainComentado.id_usuario);  // Reemplaza el tercer '?' por el valor de 'id_usuario'.
        st.setInt(4, id_post);  // Reemplaza el cuarto '?' por el valor de 'id_post'.


        st.executeUpdate();//ejecutamos la consulta (INSERT en este caso)
        //Inserta el comentario en la tabla comentarios.
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
