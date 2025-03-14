package main.estudiar_codigoComentado;

import java.sql.*;

//este código nos permite conectar con una base de datos SQLite y realizar operaciones sobre la tabla usuarios
public class ConectarSqlComentado {

    static Connection connection;

    //conectarse a la base de datos
    public static Connection getConnection(){
        //establecera una conexión con la base de datos siempre que este ubicada en:
        String host = "jdbc:sqlite:src/main/resources/network.sqlite";

        //si la conecion aún no existe
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(host);
            }catch (SQLException sql){
                System.out.println(sql.getMessage());
                System.exit(0);
            }
        }
        return connection;
    }

    //leer y mostrar los datos de la tabla usuarios en la consola
    public static void main(String[] args) throws SQLException {
        Connection con = getConnection();//llamamos a getConnection para obtener la conexión a la base de datos
        Statement stmt = con.createStatement();//creamos un Statement para ejecutar consultas SQL
        ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");//Ejecutamos la consulta (la cuál obtendrá todos los registros de la tabla usuario / toda su información)
        //stmt.executeUpdate("CREATE TABLE T1 (c1 varchar(50))");

        //recorremos todos los datos de la tabla usuarios
        while (rs.next()){ //rs.next -> avanza a la siguiente fila en el resultado

            //rs.getInt(1) -> obtiene le valor de la primera columna (se asume que será el id)
            System.out.println(rs.getInt(1));

            //rs.getString(2) y rs.getString(3)-> obtienen los valores de las columnas nombre y apellidos
            System.out.println("\t" + rs.getString(2));
            System.out.println("\t" + rs.getString(3));
        }
        stmt.close();//una vez terminado el Statement, se cierra

        insertUser(); /*1*/ //para agregar un usuario directamente en SQL
        //insertUserPreparedStatement(); /*2*/
        //deleteUserPreparedStatement(); /*3*/
    }

    /*Insertar datos en la tabla o mejor dicho ->
      insertar un nuevo usuario(Janet Espinosa en este caso) en la tabla usuarios */
    public static void insertUser() throws SQLException{
        String sql = "INSERT INTO usuarios (nombre, apellidos) VALUES ('Janet', 'Espinosa')";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }//usa statement, cos que no es seguro contra una inyección SQL (es un tipo de ataque cibernetico que inyecta codigo SQL malicioso)

    /*Insertar datos en la tabla o mejor dichob ->
      insertamos el usuario (Juan Martínez) usando PreparedStatement*/
    public static void insertUserPreparedStatement() throws SQLException{
        PreparedStatement st = null;
        String sql = "INSERT INTO usuarios (nombre, apellidos) VALUES (?, ?)";
        st = connection.prepareStatement(sql);
        st.setString(1, "Juan");
        st.setString(2, "Martínez");
        st.executeUpdate();
    }//forma más segura de insertar datos, porque al usar ? en lugar de concatenar datos directamente, evitamos las inyecciones SQL

    //Eliminar registros
     public static void deleteUserPreparedStatement() throws SQLException {
      /* Como es un método 'static', se puede llamar sin necesidad de instanciar la clase.
          Ejemplo de llamada directa:
            deleteUserPreparedStatement();

          Si el método NO fuera 'static', habría que instanciar la clase antes de llamarlo:
            ConectarSqlComentado obj = new ConectarSqlComentado();
            obj.deleteUserPreparedStatement();  // Correcto si el método NO fuera 'static'. */

         PreparedStatement st = null;
        String sql = "DELETE FROM usuarios WHERE id = ?";
        st = connection.prepareStatement(sql);
        st.setInt(1, 5); //borra un usuario con un 'id' igual a 5 (id=5)
        st.executeUpdate();
     }//Para mayor seguridad utilizamos el PreparedStatement

     //Hacer consultas con PreparedStatement
     //este método realiza una consulta en la base de datos para obtener el usuario cuyo id sea 5.
     public static void selectedPrepared() throws SQLException{
        PreparedStatement st = null;//almacenará la consulta preparada y/o el PreparedStatement

        String sql = "SELECT * FROM usuarios WHERE id= ?";//definimos la consulta SQL que se ejecutará
         //el ? representa el valor que luego en st.setInt le añadiremos a id


         /* ponemos connection porque prepareStament es un método de la clase Connection
            connection -> es una instancia de Connection, que representa la conexión a la base de datos.
            prepareStatement(sql) -> es un método de Connection que prepara la consulta SQL antes de ejecutarla. */
         st = connection.prepareStatement(sql);
         //se asigna / añade la consulta a st
         //prepareStatement(sql) convierte la consulta en una lista para ejecutarse

         //sustituir el ? por un valor real
         st.setInt(1, 5);//reemplaza el primer '?' (y en este caso también único) por el valor 5
         //El '1' indica la posición del '?', si hubiera más parámetros (?), usaríamos st.setString(2, "nombre"), etc.

         //ejecutar la consulta y obtener los resultados
         ResultSet rs = st.executeQuery();
         //executeQuery() ejecuta la consulta SQL en la base de datos.
         //Devuelve un ResultSet, que es un objeto que contiene los resultados obtenidos.
     }
     //Ejemplo con múltiples parámetros:
    /*  String sql = "SELECT * FROM usuarios WHERE id= ? AND nombre= ?";
        st = connection.prepareStatement(sql);
        st.setInt(1, 5);
        st.setString(2, "Juan");*/
}