/**
 * ConsultaImponentesDiprecaDao
 * 
 * @author denis.fuenzalida@gmail.com
 * 
 */


package cl.dipreca.servicios.ipsV01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ConsultaImponentesDiprecaDao {
	
	private static String CODIGO_ERROR_PARAMETROS = "03";
	private static String CODIGO_ERROR_NO_HAY_DATOS = "05";
	private static String CODIGO_ERROR_NO_DISPONIBLE = "07";
			
	public ConsultaImponentesDiprecaDao(){
		
		try {
			// Cargo el driver de Informix
			Class.forName("com.informix.jdbc.IfxDriver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}		

	}
	
	/**
	 * Retorna un Map con los datos requeridos para armar la respuesta (nombres, apellidoPaterno, sexo, etc.)
	 * o un Map con un codigoError y un error para las consultas que causan problemas
	 * 
	 * @param rut el RUT a consultar
	 * @param dv el Digito verificador del RUT a consultar
	 * @return Map con datos para armar una respuesta
	 */
	public Map getDatosImponentePorRut(int rut, String dv){
		
		
		/*
		 * Conexion a base de datos y busqueda de resultados
		 */
		
		Connection con = null;
		
		try {			
	        con = DriverManager.getConnection(
	        		"jdbc:informix-sqli://172.16.1.10:1546/bub:informixserver=llr_ip",
	        		"infochi","x22er21");
	        
			/*
			 * Si el RUT no es valido devolver un Map con una entrada "ERROR"
			 *  que sirva para armar el sobre con mensaje de error
			 * 
			 */
			
			if (!esRutValido(rut, dv)){
				Map errorDatos = new HashMap();
				errorDatos.put("rut", rut);
				errorDatos.put("dv", dv);
				errorDatos.put("codigoError", CODIGO_ERROR_PARAMETROS);
				errorDatos.put("mensaje", "ERROR EN PARAMETROS DE ENTRADA");
				errorDatos.put("estado", "M");
				registrarConsulta(con, errorDatos);
				return errorDatos;
			}
	        
	        
	        PreparedStatement stmt;
	        ResultSet rs;
	        
	        // RUT+DV en 10 caracteres completados con ceros a la izquierda (9 numeros + 1 DV)
	        String rutDvConCeros = completarIzquierda("000000000", "" + rut) + dv;
	        int contImpo = 0;
	        
	        stmt = con.prepareStatement("" +
	        		"SELECT COUNT(*) FROM pensiones:beneficiario " +
	        		"WHERE numero_rut = ? AND estado_benefic <> 3");
	        stmt.setString(1, rutDvConCeros);
	        rs = stmt.executeQuery();
	        while (rs.next()){ contImpo = rs.getInt(1); }
	        stmt.close();
	        
	        /**
	         * Si no tiene pensión vigente, retorno un error
	         */
	        if (contImpo == 0){
	        	
	        	// Datos para la respuesta
	        	Map noExistenDatos = new HashMap();
	        	noExistenDatos.put("rut", rut);
	        	noExistenDatos.put("dv", dv);
	        	noExistenDatos.put("codigoError", CODIGO_ERROR_NO_HAY_DATOS);
	        	noExistenDatos.put("mensaje", "NO EXISTEN DATOS PARA RESPONDER LA CONSULTA");
	        	noExistenDatos.put("estadoWsdl", "M");
	        	registrarConsulta(con, noExistenDatos);
	        	return noExistenDatos;
	        }
	        
	        /*
	         * Busco el único (o último) numero de pensión
	         */
	        
	        int numeroPension = 0;
	        stmt = con.prepareStatement("" +
	        		"SELECT MAX(numero_pension) FROM pensiones:beneficiario " +
	        		"WHERE numero_rut = ? AND estado_benefic <> 3");
	        stmt.setString(1, rutDvConCeros);
	        rs = stmt.executeQuery();
	        while (rs.next()){ numeroPension = rs.getInt(1); }
	        stmt.close();
	        
	        /*
	         * Busco el Código de Reparticion y Fecha de inicio de pension
	         */
	        
	        int codigoReparticion = 0;
	        Date fechaInicio = new Date();
	        
	        stmt = con.prepareStatement("" +
	        		"SELECT tipo_pension, fecha_inicio FROM pensiones:beneficiario " +
	        		"WHERE numero_rut = ? AND numero_pension = ? AND estado_benefic <> 3");
	        stmt.setString(1, rutDvConCeros);
	        stmt.setInt(2, numeroPension);
	        rs = stmt.executeQuery();
	        while (rs.next()){
	        	codigoReparticion = rs.getInt(1);
	        	fechaInicio = rs.getDate(2);
	        }
	        stmt.close();
	        
	        // Determino el tipo de Pension en base al codigo de reparticion
	        String tipoPension = "S";
	        if (codigoReparticion == 7) { tipoPension = "T"; }

	        /*
	         * Busco el Nombre completo, Fecha de nacimiento y Sexo
	         */
	        stmt = con.prepareStatement(
	        		"SELECT nombres, ape_pat, ape_mat, fec_nac, sexo " +
	        		"FROM beneficiario WHERE rut_bene = ? AND dgv_bene = ?");
	        stmt.setInt(1, rut);
	        stmt.setString(2, dv);
	        rs = stmt.executeQuery();
	        boolean hayResultado = false;
	        Map resultado = new HashMap();
	        
	        while (rs.next()){
	        	hayResultado = true;
	        	resultado.put("rut", rut);
	        	resultado.put("dv", dv);
	        	resultado.put("estadoWsdl", "P");
	    		resultado.put("nombres", rs.getString("nombres"));
	    		resultado.put("apellidoPaterno", rs.getString("ape_pat"));
	    		resultado.put("apellidoMaterno", rs.getString("ape_mat"));
	    		resultado.put("fechaNacimiento", rs.getDate("fec_nac"));
	    		resultado.put("sexo", rs.getString("sexo"));
	    		resultado.put("situacionIndividuo", "P");
	    		resultado.put("fechaPension", fechaInicio);	    		
	    		resultado.put("tipoPension", tipoPension);
	    		resultado.put("mensaje", "R.U.T. VIGENTE EN DIPRECA");
	        }	    		
	        stmt.close();
	        
	        /*
	         * Logging de la consulta
	         */
	        
	        registrarConsulta(con, resultado);
	        
	        /*
	         * Siempre intentar cerrar la conexión a la base de datos
	         */
	        try { con.close(); } catch (Exception e2) { }
	        
	        if (hayResultado){
	        	return resultado;
	        } else {
				Map noHayDatos = new HashMap();
				noHayDatos.put("rut", rut);
	        	noHayDatos.put("dv", dv);
	        	noHayDatos.put("estadoWsdl", "M");
				noHayDatos.put("codigoError", CODIGO_ERROR_NO_HAY_DATOS);
				noHayDatos.put("mensaje", "RUT NO ENCONTRADO");
				registrarConsulta(con, noHayDatos);
				return noHayDatos;
	        }
			
		} catch (Exception e) {
			Map datosNoDisponibles = new HashMap();
			datosNoDisponibles.put("rut", rut);
			datosNoDisponibles.put("dv", dv);
			datosNoDisponibles.put("estadoWsdl", "M");
			datosNoDisponibles.put("codigoError", CODIGO_ERROR_NO_DISPONIBLE);
			datosNoDisponibles.put("mensaje", "DATOS NO DISPONIBLES");
        	registrarConsulta(con, datosNoDisponibles);
        	
	        /*
	         * Aunque haya una excepcion, siempre intentar cerrar la conexión a la base de datos
	         */
			try { con.close(); } catch (Exception e2) { }
			
			return datosNoDisponibles;
		}
		
	}

	/**
	 * Valida si el digito verificador corresponde con el RUT
	 * @param rut
	 * @param dv
	 * @return true si y solo si el digito verificador es valido para el rut
	 */
	public boolean esRutValido(int rut, String dv){
		if (dv == null || dv.trim().length() != 1) return false;
		
		return (getDigitoVerificador(rut).equals(dv));
	}

	/**
	 * Calcula el digito verificador de un RUT
	 * @param RUT
	 * @return digito verificador
	 */
	public String getDigitoVerificador(int rut){
		
		int M = 0;
		int S = 1;
		int T = rut;
		for( ;T!=0; T/=10) { S = (S+T%10*(9-M++%6))%11; }		
		String[] dv = {"K","0","1","2","3","4","5","6","7","8","9"};
		
		return dv[S];
	}
	
	/**
	 *  Toma una mascara "000000" y un valor "12" y retorna "000012".
	 *  Usa los primeros caracteres de la mascara y agrega el valor para formar un string del mismo largo que la mascara
	 *   
	 * @param mascara Un string con tantos ceros como se requiere
	 * @param valor El valor a completar con ceros a la izquierda
	 * @return El valor completado con ceros a la izquierda y el valor a la derecha
	 */
	
	public static String completarIzquierda(String mascara, String valor){
	
		
		// si el valor es más largo que la mascara, retorno el valor
		if (valor.length() >= mascara.length()) return valor;
		
		String prefijo = mascara.substring(0, mascara.length() - valor.length());
		
		return prefijo+valor;
	}
	
	/**
	 * Ingresa los datos que resultaron de la consulta en la tabla BUB:WSDL_IPS
	 * 
	 * @param con Conexión a la base de datos
	 * @param datosConsulta Datos que resultaron de la consulta. Admite algunos nulos segun la definicion de BUB:WSDL_IPS 
	 */
	private void registrarConsulta(Connection con, Map datosConsulta){
		try {
			
			PreparedStatement stmt = con.prepareStatement("" +
        			"INSERT INTO wsdl_ips (id_wsdl, rut_usuario, estado_wsdl, glosa_estado, rut_persona, dv_persona, " +
        			" situacion, fecha_informe, apellido_paterno, apellido_materno, nombres, fecha_nacimiento, sexo, " +
        			" fecha_vigencia, tipo_pension, fecha_audi) " +
        			"VALUES ( 0, 61979440, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT)");
			
			stmt.setString(1, (String) datosConsulta.get("estadoWsdl"));
			stmt.setString(2, (String) datosConsulta.get("mensaje"));
			stmt.setInt(3, (Integer) datosConsulta.get("rut"));		
			stmt.setString(4, (String) datosConsulta.get("dv"));
			stmt.setString(5, (String) datosConsulta.get("situacionIndividuo"));			
			stmt.setDate(6, new java.sql.Date(new Date().getTime()));			
			stmt.setString(7, (String) datosConsulta.get("apellidoPaterno"));
			stmt.setString(8, (String) datosConsulta.get("apellidoMaterno"));
			stmt.setString(9, (String) datosConsulta.get("nombres"));
			
			if (datosConsulta.containsKey("fechaNacimiento")){
				stmt.setDate(10, new java.sql.Date( ((Date) datosConsulta.get("fechaNacimiento")).getTime()) );
			} else {
				stmt.setDate(10, null);
			}

			stmt.setString(11, (String) datosConsulta.get("sexo"));

			if (datosConsulta.containsKey("fechaPension")){
				stmt.setDate(12, new java.sql.Date( ((Date) datosConsulta.get("fechaPension")).getTime()) );
			} else {
				stmt.setDate(12, null);
			}
			
			stmt.setString(13, (String) datosConsulta.get("tipoPension"));
			
			stmt.executeUpdate();
			stmt.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
