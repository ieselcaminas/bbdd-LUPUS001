package main;

import java.sql.*;
import java.util.Scanner;

public class GestionUsuarios {
    public static void gestionMenu() throws SQLException{
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        String usuario = "";

        while (opcion != -1){
            System.out.print(" 1 - Logearse | ");
            System.out.print(" 2 - Nuevo usuario | ");
            System.out.print(" -1 - Salir");

            opcion = sc.nextInt();

            if (opcion == 1){
                usuario = existeUsuario();
                if (!usuario.isEmpty()) {
                    Main.usuario = usuario;
                    System.out.println("Bienvenido ");
                    break;
                } else {
                    System.out.println("USER NOT FOUND");
                }

            } else if (opcion == 2){
                //método insertar usuario
                usuario = insertarUsuario();
                Main.usuario = usuario;
                break;
            }
        }
    }

    public static String existeUsuario() throws SQLException {
        java.sql.Connection con = Main.connection;
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce tu usuario:");
        String nombre = sc.nextLine();
        System.out.println("Introduce tu contraseña:");
        String contrasenya = sc.nextLine();

        PreparedStatement st = con.prepareStatement("SELECT * FROM usuarios WHERE nombre = ? AND contrasenya = ?");
        st.setString(1, nombre);
        st.setString(2, contrasenya);

        ResultSet rs = st.executeQuery();

        if (rs.next()){
            Main.id_usuario = rs.getInt(1);
            return rs.getString(2);
        }
        return "";
    }

    public static String insertarUsuario() throws SQLException {
        Connection con = Main.connection;

        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce tu nombre");
        String nombre = sc.nextLine();
        System.out.println("Introduce tus apellidos");
        String apelldos = sc.nextLine();
        System.out.println("Introduce tu contraseña");
        String contrasenya = sc.nextLine();

        PreparedStatement st = con.prepareStatement("INSERT INTO usuarios (nombre, apellidos, contrasenya) VALUES(?, ?, ?)");
        st.setString(1, nombre);
        st.setString(2, apelldos);
        st.setString(3, contrasenya);

        st.executeUpdate();
        return "";
    }
}
