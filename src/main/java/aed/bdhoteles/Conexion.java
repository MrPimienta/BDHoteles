package aed.bdhoteles;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {

	static public Connection conexion(int opcion) throws ClassNotFoundException, SQLException {

		Connection con = null;

		if (opcion == 1) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nueva_bdhoteles", "root", "");
				System.out.println("¡Conexion realizada (MySQL)!");
			} catch (SQLException e) {
				//e.printStackTrace();
				System.out.println("Conexion NO realizada (MySQL)");
				System.out.println("Revise en el codigo que la referencia a la base de datos este correcta.");
				System.exit(0);
			}
		} else if (opcion == 2) {
			try {
				con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver;DataBaseName=hoteles_SQLSERVER;user=sa;password=sa");
				System.out.println("¡Conexion realizada (SQLServer)!");
			}catch (SQLException e) {
				//e.printStackTrace();
				System.out.println("Conexion NO realizada (SQLServer)");
				System.out.println("Revise en el codigo que la referencia a la base de datos este correcta.");
				System.exit(0);
			}
		} else if (opcion == 3) {
			try {
				
				String strConnectionString = "jdbc:ucanaccess://nueva_bdhoteles.accdb";
				con = DriverManager.getConnection(strConnectionString);
				System.out.println("¡Conexion realizada (ACCESS)!");
			}catch (Exception e) {
				//e.printStackTrace();
				System.out.println("Conexión NO realizada (ACCESS)");
				System.out.println("Revise en el codigo que la referencia a la base de datos este correcta.");
				System.exit(0);
			}
		
		}

		return con;
	}

	static void cerrarConexion(Connection con) {
		try {
			con.close();
			System.out.println("Conexion cerrada");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Conexión NO cerrada");
		}
	}
}
