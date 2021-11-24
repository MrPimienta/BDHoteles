package aed.bdhoteles;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.security.auth.callback.TextOutputCallback;

import com.mysql.cj.protocol.Resultset;

public class Main {
	
	
	
	
	static Connection con;

	public static void main(String[] args) {
		Connection con = null;
		Scanner in = new Scanner(System.in);
		int opcion = 0;
		
		try {
		// MENU PARA ELEGIR EL TIPO DE CONEXION
		System.out.println("ACCESO A DATOS DESDE JAVA - DANIEL PÉREZ PIMIENTA");
		System.out.println("--TIPO DE CONEXION-------------------------------");
		System.out.println("1. MySQL                                         |");
		System.out.println("2. SQL SERVER                                    |");
		System.out.println("3. Access                                        |");
		System.out.println("4. Salir                                         |");
		System.out.println("-------------------------------------------------");
		System.out.println("Introduzca el numero del tipo de conexion que desea: ");
		opcion = in.nextInt();
		}catch (InputMismatchException e) {
			System.out.println("Debe introducir un numero válido.");
			System.out.println("Saliendo del programa...");
		}

		switch (opcion) {
		
		case 1: //CONEXION MYSQL
			try {
				con = Conexion.conexion(opcion);
			} catch (ClassNotFoundException e) {
				System.out.println("Ocurrio un error. No se pudo realizar la conexión.");
				e.printStackTrace();
				System.exit(0);
			} catch (SQLException e) {
				System.out.println("Ocurrio un error. No se pudo realizar la conexión.");
				e.printStackTrace();
				System.exit(0);
			}
			// LLAMADA AL MENU
			menu(con,opcion);
			break;

		case 2: //CONEXION SQL SERVER
			try {
				con = Conexion.conexion(opcion);
			} catch (ClassNotFoundException e) {
				System.out.println("Ocurrio un error. No se pudo realizar la conexión.");
				e.printStackTrace();
				System.exit(0);
			} catch (SQLException e) {
				System.out.println("Ocurrio un error. No se puedo realizar la conexión.");
				e.printStackTrace();
				System.exit(0);
			}
			// LLAMADA AL MENU
			menu(con,opcion);
			break;

		case 3: //CONEXION ACCESS
			try {
				con = Conexion.conexion(opcion);
			} catch (ClassNotFoundException e) {
				System.out.println("Ocurrio un error. No se puedo realizar la conexión.");
				e.printStackTrace();
				System.exit(0);
			} catch (SQLException e) {
				System.out.println("Ocurrio un error. No se puedo realizar la conexión.");
				e.printStackTrace();
				System.exit(0);
			}
			// LLAMADA AL MENU
			menu(con,opcion);
			break;

		case 4:
			System.exit(0);
			break;
		}
		

	}

	private static void menu(Connection con, int n_conexion) {
		Scanner in = new Scanner(System.in);
		boolean salir = false;
		int opcion;

		while (!salir) {
			System.out.println("-----------------------------------------------------------");
			System.out.println("1. Listar Habitaciones            			   |");
			System.out.println("2. Insertar Habitaciones          			   |");
			System.out.println("3. Eliminar Habitaciones          		       	   |");
			System.out.println("4. Modificar Habitaciones         			   |");
			
			if(n_conexion != 3) {
			System.out.println("5. Insertar Habitacion (Proc.)    			   |");
			System.out.println("6. Visualizar Habitaciones (Proc.)			   |");
			System.out.println("7. Cantidad Habitaciones por Precio/Dia (Proc.)            |");
			System.out.println("8. Suma Total Pagada por Cliente (Func.)	           |");
			}
			System.out.println("9. Salir                     				   |");
			System.out.println("-----------------------------------------------------------");

			try {
				System.out.print("Inserte el número de la opción que desee: ");
				opcion = in.nextInt();
				System.out.println();

				switch (opcion) {
				case 1:
					listarHabitaciones(con, n_conexion);
					break;
				case 2:
					insertarHabitaciones(con, n_conexion);
					break;
				case 3:
					eliminarHabitacion(con, n_conexion);
					break;
				case 4:
					modificarHabitacion(con, n_conexion);
					break;
				case 5:
					insertarHabitacionProc(con, n_conexion);
					break;
				case 6:
					visualizarHabitacionesProc(con, n_conexion);
					break;
				case 7:
					cantidadHabitacionesProc(con, n_conexion);
					break;
				case 8:
					sumaPagadaClienteFunc(con, n_conexion);
					break;
				case 9:
					salir = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("¡Debes insertar un numero correspondiente a la opción!");
				in.next();
				e.printStackTrace();
			}
		}

	}

	private static void listarHabitaciones(Connection con, int n_conexion) {
		// Visualiza todos los datos de la tabla habitaciones visualizando también el
		// nombre del hotel.
		try {
			ResultSet resultado = null;
			
			if(n_conexion == 2 || n_conexion == 1) {
				PreparedStatement ps = con.prepareStatement("SELECT *, hoteles.nomHotel as nomhotel FROM habitaciones "
						+ "INNER JOIN hoteles ON hoteles.codHotel = habitaciones.codHotel");
				resultado = ps.executeQuery();
			} else if(n_conexion == 3) {
				PreparedStatement ps = con.prepareStatement("SELECT codHotel,numHabitacion,capacidad,preciodia,activa, hoteles.nomHotel as nomhotel FROM habitaciones "
						+ "INNER JOIN hoteles ON hoteles.codHotel = habitaciones.codHotel");
				resultado = ps.executeQuery();
			}

			// NOMBRE DE LAS COLUMNAS
			System.out.printf("%s %15s %10s %10s %10s %20s\n", "Cod.Hotel", "Num.Habitación", "Capac.", "Precio/d",
					"Activa", "Nom.Hotel");
			System.out.println(
					"-------------------------------------------------------------------------------------------");
			while (resultado.next()) {
				String codHotel_ = resultado.getString("codHotel");
				String numHabitacion_ = resultado.getString("numHabitacion");
				int capacidad_ = resultado.getInt("capacidad");
				int preciodia_ = resultado.getInt("preciodia");
				int activa_ = resultado.getInt("activa");
				String activa_string;

				if (activa_ == 1) {
					activa_string = "Activa";
				} else {
					activa_string = "No Activa";
				}

				String nomHotel_ = resultado.getString("nomHotel");

				System.out.printf("%s %10s %14d %10d %14s %25s\n", codHotel_, numHabitacion_, capacidad_, preciodia_,
						activa_string, nomHotel_);
				System.out.println(
						"-------------------------------------------------------------------------------------------");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void insertarHabitaciones(Connection con, int n_conexion) {
		// Inserta habitaciones. Haz un combo con los posibles hoteles en presentación
		// visual. Comprueba que los datos a introducir son correctos.

		String in_codhotel;
		String in_numhabitacion;
		int in_capacidad;
		int in_preciodia;
		int in_activa;
		Scanner in = new Scanner(System.in);

		System.out.println("Introduzca el Cod.Hotel de la habitación: ");
		in_codhotel = in.nextLine();
		System.out.println("Introduzca el numero de la habitación: ");
		in_numhabitacion = in.nextLine();
		System.out.println("Introduzca la capacidad de la habitación: ");
		in_capacidad = in.nextInt();
		System.out.println("Introduzca el precio/día de la habitación");
		in_preciodia = in.nextInt();
		System.out.println("Introduzca 1 o 0 (habitacion activa/no activa)");
		in_activa = in.nextInt();

		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO habitaciones VALUES (?,?,?,?,?)");

			ps.setString(1, in_codhotel);
			ps.setString(2, in_numhabitacion);
			ps.setInt(3, in_capacidad);
			ps.setInt(4, in_preciodia);
			ps.setInt(5, in_activa);
			// EJECUTAMOS EL INSERT CON EL executeUpdate
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void eliminarHabitacion(Connection con, int n_conexion) {
		// TODO FALTA PEDIR SI TAMBIEN SE QUIEREN ELIMINAR LAS ESTANCIAS

		// Elimina una habitación, si la habitación a eliminar tiene estancias
		// preguntaremos si queremos eliminarlas todas (habitaciones y todos sus
		// estancias) o no.
		
		String in_codhotel;
		String in_numhabitacion;

		Scanner in = new Scanner(System.in);
		char opcion;
		System.out.println("Introduce el Cod.Hotel de la habitación que quieres eliminar: ");
		in_codhotel = in.nextLine();
		System.out.println("Introduce el Num.Habitación de la habitación que quieres eliminar: ");
		in_numhabitacion = in.next();

		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM habitaciones WHERE codHotel = ? AND numHabitacion = ?");
			ps.setString(1, in_codhotel);
			ps.setString(2, in_numhabitacion);

			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				String codHotel_ = resultado.getString("codHotel");
				String numHabitacion_ = resultado.getString("numHabitacion");
				int capacidad_ = resultado.getInt("capacidad");
				int preciodia_ = resultado.getInt("preciodia");
				int activa_ = resultado.getInt("activa");
				String activa_string;

				if (activa_ == 1) {
					activa_string = "Activa";
				} else {
					activa_string = "No Activa";
				}

				System.out.println("VA A ELIMINAR EL SIGUIENTE REGISTRO");
				System.out.printf("%5s %5s %5d %5d %5s\n", codHotel_, numHabitacion_, capacidad_, preciodia_,
						activa_string);
			}

			System.out.print("¿QUIERES BORRARLO? (S\\N):");
			opcion = in.next().charAt(0); // CAPTURAMOS LA OPCION
			if (opcion == 'S') {
				eliminarHabitacionFinal(con, in_codhotel, in_numhabitacion);
			} else {
				System.out.println("OPCION INTRODUCIDA INCORRECTA");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void eliminarHabitacionFinal(Connection con, String codHotel, String numHabitacion) {
		String codHotel_ = codHotel;
		String numHabitacion_ = numHabitacion;

		// System.out.println(codHotel_);
		// System.out.println(numHabitacion_);

		try {
			PreparedStatement ps = con
					.prepareStatement("DELETE FROM habitaciones WHERE codHotel = ? AND numHabitacion = ?");
			ps.setString(1, codHotel_);
			ps.setString(2, numHabitacion_);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void modificarHabitacion(Connection con, int n_conexion) {
		// Modifica todos los datos de las habitaciones, el código de la habitación u
		// hotel no se podrá modificar.Deberás visualizar los datos que ya tiene y solo
		// escribir los que quieras modificar.

		String in_codhotel;
		String in_numhabitacion;

		Scanner in = new Scanner(System.in);
		char opcion;
		System.out.println("Introduce el Cod.Hotel de la habitación que quieres modificar: ");
		in_codhotel = in.nextLine();
		System.out.println("Introduce el Num.Habitación de la habitación que quieres modificar: ");
		in_numhabitacion = in.next();

		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM habitaciones WHERE codHotel = ? AND numHabitacion = ?");
			ps.setString(1, in_codhotel);
			ps.setString(2, in_numhabitacion);

			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				String codHotel_ = resultado.getString("codHotel");
				String numHabitacion_ = resultado.getString("numHabitacion");
				int capacidad_ = resultado.getInt("capacidad");
				int preciodia_ = resultado.getInt("preciodia");
				int activa_ = resultado.getInt("activa");
				String activa_string;

				if (activa_ == 1) {
					activa_string = "Activa";
				} else {
					activa_string = "No Activa";
				}

				System.out.println();
				System.out.println("VA A MODIFICAR EL SIGUIENTE REGISTRO");
				System.out.println("-----------------------------------------------------------------------------");
				System.out.printf("%s %15s %10s %10s %10s\n", "Cod.Hotel", "Num.Habitación", "Capac.", "Precio/d",
						"Activa");
				System.out.printf("%s %10s %14d %10d %14s\n", codHotel_, numHabitacion_, capacidad_, preciodia_,
						activa_string);
				System.out.println("-----------------------------------------------------------------------------");
				System.out.println();
			}

			System.out.print("¿QUIERES MODIFICARLO? (S\\N):");
			opcion = in.next().charAt(0); // CAPTURAMOS LA OPCION
			if (opcion == 'S') {
				int mod_capacidad;
				int mod_preciodia;
				int mod_activa;

				System.out.println("Introduzca la nueva capacidad de la habitación: ");
				mod_capacidad = in.nextInt();
				System.out.println("Introduzca el nuevo precio por día de la habitación: ");
				mod_preciodia = in.nextInt();
				System.out.println("Introduzca si la habitacion es activa o no (0/1)");
				mod_activa = in.nextInt();
				// llamamos a la funcion que realiza el update en la base de datos
				modificarHabitacionFinal(con, in_codhotel, in_numhabitacion, mod_capacidad, mod_preciodia, mod_activa);
			} else {
				System.out.println("OPCION INTRODUCIDA INCORRECTA");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void modificarHabitacionFinal(Connection con, String codhotel, String numhabitacion, int capacidad,
			int preciodia, int activa) {

		String codHotel_ = codhotel;
		String numHabitacion_ = numhabitacion;
		int capacidad_ = capacidad;
		int preciodia_ = preciodia;
		int activa_ = activa;

		// System.out.println(codHotel_);
		// System.out.println(numHabitacion_);

		try {
			PreparedStatement ps = con.prepareStatement("UPDATE habitaciones SET capacidad=?, preciodia=?, activa=? "
					+ "WHERE codHotel=? AND numHabitacion=?");
			ps.setInt(1, capacidad_);
			ps.setInt(2, preciodia_);
			ps.setInt(3, activa_);
			ps.setString(4, codHotel_);
			ps.setString(5, numHabitacion_);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void insertarHabitacionProc(Connection con, int n_conexion) {
		System.out.println("***PROCEDIMIENTOS ALMACENADOS - INSERTAR HABITACIÓN***");
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("Cod.Hotel de la habitación a insertar: ");
			String codHotel_ = in.nextLine();
			System.out.println("Num.Habitación de la habitación a insertar: ");
			String numHabitacion_ = in.nextLine();
			System.out.println("Capacidad de la habitación a insertar: ");
			int capacidad_ = in.nextInt();
			System.out.println("Precio de la habitación a insertar: ");
			int preciodia_ = in.nextInt();
			System.out.println("¿Es activa la habitación? (0/1): ");
			int activa_ = in.nextInt();
			in.close(); // COMPROBAR
			System.out.println("*******************************************************");

			if(n_conexion == 1) { //MYSQL
				CallableStatement consulta = con.prepareCall("CALL sp_insercion(?,?,?,?,?,?,?)");

				consulta.setString(1, codHotel_);

				consulta.setString(2, numHabitacion_);

				consulta.setInt(3, capacidad_);
			
				consulta.setInt(4, preciodia_);

				consulta.setInt(5, activa_);

				consulta.registerOutParameter(6, Types.BOOLEAN);
				consulta.registerOutParameter(7, Types.BOOLEAN);

				consulta.executeUpdate(); // USAMOS EXECUTE POR NO DEVOLVER REGISTROS
				System.out.println("Hotel existe: " + consulta.getBoolean(6));
				System.out.println("Inserción realizada:" + consulta.getBoolean(7));
				//menu(con, n_conexion);
				
			} else if (n_conexion ==2) { //SQLSERVER
				CallableStatement consulta = con.prepareCall("{CALL dbo.insertarHabitacion(?,?,?,?,?,?,?)}");
				consulta.setString(1, codHotel_);

				consulta.setString(2, numHabitacion_);

				consulta.setInt(3, capacidad_);
			
				consulta.setInt(4, preciodia_);

				consulta.setInt(5, activa_);

				consulta.registerOutParameter(6, Types.BOOLEAN);
				consulta.registerOutParameter(7, Types.BOOLEAN);

				consulta.executeUpdate(); // USAMOS EXECUTE POR NO DEVOLVER REGISTROS
				System.out.println("Hotel existe: " + consulta.getBoolean(6));
				System.out.println("Inserción realizada:" + consulta.getBoolean(7));
				menu(con, n_conexion);
			}

		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Ocurrió un error");
			//menu(con, n_conexion);
		} 

	}

	public static void visualizarHabitacionesProc(Connection con, int n_conexion) {
		// Procedimiento almacenado que liste todas las habitaciones de cierto hotel
		// pasando por
		// parámetro de entrada el nombre del hotel, ordenados por preciodia y capacidad
		// en orden ascendente.
		// Los datos a visualizar serán: número de habitacion, capacidad, precio y
		// activo.

		System.out.println("***PROCEDIMIENTOS ALMACENADOS - VISUALIZAR HABITACIÓN***");
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("Introduzca el nombre del hotel para ver sus habitaciones:");
			String nomHotel_ = in.nextLine();
			ResultSet resultado = null;
			
			if(n_conexion==1) { //MYSQL
				CallableStatement consulta = con.prepareCall("CALL sp_habitaciones(?)");
				consulta.setString(1, nomHotel_);

				resultado = consulta.executeQuery();
			
			} else if (n_conexion==2) { //SQL SERVER
				CallableStatement consulta = con.prepareCall("{CALL dbo.listarHabitacionesHotel(?)}");
				consulta.setString(1, nomHotel_);

				resultado = consulta.executeQuery();
			}
			
			if(resultado==null) {
				System.out.println("Ese hotel no esta registrado.");
			}
			
			while (resultado.next()) {
				String numHabitacion_ = resultado.getString("numHabitacion");
				int capacidad_ = resultado.getInt("capacidad");
				int preciodia_ = resultado.getInt("preciodia");
				boolean activa_ = resultado.getBoolean("activa");

				System.out.println();
				System.out.println("--------------------------");
				System.out.println("Num.Habitación: " + numHabitacion_);
				System.out.println("Capacidad: " + capacidad_);
				System.out.println("Precio/Día: " + preciodia_);
				System.out.println("Activa: " + activa_);
				System.out.println("--------------------------");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void cantidadHabitacionesProc(Connection con, int n_conexion) {

		// #Crear un procedimiento almacenado que indicándole un nombre de hotel y un
		// preciodia, devuelva dos parámetros.
		// #En un parámetro de salida la cantidad de habitaciones total que tiene ese
		// hotel. En otro parámetro de salida
		// #la cantidad de habitaciones por debajo del preciodia y sean activas.

		System.out.println("***PROCEDIMIENTOS ALMACENADOS - CANTIDAD DE HABITACIONES SEGUN PRECIO***");
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("Indique el nombre del hotel: ");
			String nomHotel_ = in.nextLine();
			System.out.println("Indique un precio/día: ");
			int preciodia_ = in.nextInt();
			
			//CONTROLAMOS LA INSTRUCCION DEPENDIENDO DE SI SE ACCEDE POR SQLSERVER O POR MYSQL
			CallableStatement consulta;
			
			if(n_conexion==2) { //SQL SERVER
				 consulta = con.prepareCall("{CALL dbo.cantidadHabitaciones (?, ?, ?, ?)}");
			} else { //MYSQL
				 consulta = con.prepareCall("CALL cantidadHabitaciones(?,?,?,?)");
			}
			
			consulta.setString(1, nomHotel_);
			consulta.setInt(2, preciodia_);
			consulta.registerOutParameter(3, Types.INTEGER);
			consulta.registerOutParameter(4, Types.INTEGER);
			consulta.execute();
			System.out.println("Cantidad de habitaciones totales del hotel introducido: " + consulta.getInt(3));
			System.out.println("Cantidad de habitaciones totales de ese hotel, " + "por debajo del precio introducido: "
				+ consulta.getInt(4));

			// IMPRIMIMOS LAS VARIABLES DE SALIDA POR LA CONSOLA
			
			menu(con, n_conexion);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Ocurrió un error");
			menu(con, n_conexion);
		}
	}

	public static void sumaPagadaClienteFunc(Connection con, int n_conexion) {
		// Crear una función que dándole un dni o nie de un cliente como parámetro de
		// entrada nos devuelva la
		// suma total pagada por dicho cliente.

		System.out.println("***FUNCIONES - SUMA TOTAL PAGADA POR EL CLIENTE");

		try {
			ResultSet resultado;
			Scanner in = new Scanner(System.in);
			System.out.println("Indique el DNI o NIE del cliente: ");
			String dnionie_ = in.nextLine();
			
			//CONTROLAMOS EL SELECT DE LA FUNCION DEPENDIENDO DE SI SE ACCEDE POR SQLSERVER O POR MYSQL
			if(n_conexion==2) {
				PreparedStatement consulta = con.prepareStatement("SELECT dbo.sumaPagadaCliente(?)"); //TODO HAY QUE PONER A LOS PROC. Y FUNCIONES EL dbo. para que funcione en sqlserver
				consulta.setString(1, dnionie_);
				resultado = consulta.executeQuery();
			}else {
				PreparedStatement consulta = con.prepareStatement("SELECT sumaPagadaCliente(?)"); 
				consulta.setString(1, dnionie_);
				resultado = consulta.executeQuery();
			}

			while (resultado.next()) {
				System.out.println("Suma total pagada por el cliente introducido: " + resultado.getInt(1) + " euros");
			}

			menu(con, n_conexion);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
