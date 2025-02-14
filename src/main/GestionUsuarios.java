import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionUsuarios {
    public static void gestionMenu() throws SQLException{
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 3){
            System.out.println("1 - Logearse");
            System.out.println("2 - Nuevo usuario");
            System.out.println("3 - Salir");
            opcion = sc.nextInt();
            if (opcion == 1){
                //método loguearse
                boolean logeado = existeUsuario();
                if (logeado) break;
            } else if (opcion == 2){
                //método insertar usuario
            }
        }
    }

    public static boolean existeUsuario() throws SQLException {
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
        return rs.next();
    }

    public static void
}
