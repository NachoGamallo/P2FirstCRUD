package mainfolder.p2;

import java.sql.*;
import java.util.Date;

public class BBDD_Operations {

    public class Main {

        public static Connection conexion(){

            Connection conexion;
            String host = "jdbc:mariadb://localhost:3307/";
            String user = "root";
            String psw = "";
            String bd = "pactica2";
            System.out.println("Conectando...");

            try {
                conexion = (Connection) DriverManager.getConnection(host+bd,user,psw);
                System.out.println("Conexión realizada con éxito.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }

            return conexion;

        }

        public static void desconectar(Connection conexion){

            System.out.println("Desconectando...");

            try {
                conexion.close();
                System.out.println("Conexión finalizada.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public static void consulta (Connection conexion){

            String query = "SELECT * FROM estudiantes";

            //necesitamos dos variables de tipo Statement y ResultSet para realizar la consulta y guardar la respuesta
            Statement stmt;
            ResultSet respuesta;

            try {
                stmt = conexion.createStatement();
                respuesta = stmt.executeQuery(query);

                while (respuesta.next()){ //recorremos todas las filas existentes en la tabla y las imprimimos
                    int nia = respuesta.getInt("nia");
                    String nombre = respuesta.getString("nombre");
                    Date fecha_nacimiento = respuesta.getDate("fecha_nacimiento");
                    System.out.println("NIA: " + nia + " - Nombre: " + nombre + " - Fecha de nacimiento: " + fecha_nacimiento);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        public static void insertar(Connection conexion){

            String query = "INSERT INTO estudiantes (nia, nombre, fecha_nacimiento) VALUES (43214321, 'Patricia', '1900-04-19');";

            Statement stmt;

            try {
                stmt = conexion.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }


        }

        public static void modificar(Connection conexion){

            System.out.println("Modificando...");

            String query = "UPDATE estudiantes SET nombre = 'Patri' WHERE nombre = 'Patricia'";

            Statement stmt;

            try {
                stmt = conexion.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }

        }

        public static void borrar(Connection conexion){

            System.out.println("Borrando...");

            String query = "DELETE FROM estudiantes WHERE nombre = 'Patri'";

            Statement stmt;

            try {
                stmt = conexion.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }

        }
    }
}
