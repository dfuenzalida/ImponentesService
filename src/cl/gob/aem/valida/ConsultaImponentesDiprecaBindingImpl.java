/**
 * ConsultaImponentesDiprecaBindingImpl.java
 *
 * @author denis.fuenzalida@gmail.com
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.gob.aem.valida;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.Text;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.Token;

import cl.dipreca.servicios.ipsV01.ConsultaImponentesDiprecaDao;
import cl.dipreca.servicios.ipsV01.TipoEntrada;
import cl.dipreca.servicios.ipsV01.TipoRespuesta;
import cl.dipreca.servicios.ipsV01.TipoRespuestaSituacionIndividuo;
import cl.dipreca.servicios.ipsV01.TipoRespuestaTipoPension;

@SuppressWarnings("rawtypes")
public class ConsultaImponentesDiprecaBindingImpl implements cl.gob.aem.valida.ConsultaImponentesDipreca{
    
	public cl.gob.aem.valida.Sobre consultaImponentesDipreca(cl.gob.aem.valida.Sobre request) throws java.rmi.RemoteException {
    	
		Calendar ahoraCalendar = Calendar.getInstance();
		ahoraCalendar.setTime(new Date());

		/*
		 * Validación del XML de entrada
		 * 
		 * Si el sobre de entrada no presenta algún nodo obligatorio ya sea en alguno de los siguientes campos:
		 * Encabezado: - idSobre - fechaHora
		 * Cuerpo: - RutIPS - numero - dv 
		 * 
		 */
		
    	try {    		
    		// Valido el ID Sobre
    		request.getEncabezado().getIdSobre().toString();
    		
    		// Valido la fecha y hora
    		request.getEncabezado().getFechaHora().toString();
    		
    		// Valido el RutIPS
        	DocumentoType documento = request.getCuerpo()[0].getDocumento();
        	MessageElement messageElement = documento.get_any()[0];
        	MessageElement rutIpsElement = (MessageElement) messageElement.getChildren().get(0);        	
        	rutIpsElement.toString();
        	
        	/*
        	 * Si no vienen los 2 elementos, RUT y DV, veo cual viene, completo el faltante y arrojo una excepción
        	 */
        	if (rutIpsElement.getChildren().size() < 2){
        		MessageElement unicoElement = (MessageElement) rutIpsElement.getChildren().get(0);
        		String numero = "";
        		String dv = "";
        		
        		if ("NUMERO".equals(unicoElement.getName().toUpperCase())){
        			numero = ((Text) unicoElement.getChildren().get(0)).getData();        			        			
        		} else {
        			dv = ((Text) unicoElement.getChildren().get(0)).getData();
        		}
        		
    			TipoEntrada parametrosConsulta = new TipoEntrada();
    			RunType runTypeIPS = new RunType();
    			if (numero != null) runTypeIPS.setNumero( new Token(numero) ); 
    			if (dv != null) runTypeIPS.setDv( new Token(dv) );
    			
    			// parametrosConsulta.setRutIPS(new RunType(new PositiveInteger( numero ), new Token( dv )));
    			parametrosConsulta.setRutIPS( runTypeIPS );
    			
    			DocumentoType documentoCuerpo = new DocumentoType( new MessageElement[]{
    				new MessageElement(new QName("http://servicios.dipreca.cl/ipsV01", "EntradaDatosImponente"), parametrosConsulta)	
    			});
    			
    			request.getCuerpo()[0].setDocumento(documentoCuerpo);
        		throw new Exception("Reparado sobre sin RUT o DV");
        	}
        	
        	// Valido que los tag de Numero y DV existan, pero no necesariamente sus contenidos
        	MessageElement numeroElement = (MessageElement) rutIpsElement.getChildren().get(0);
        	MessageElement dvElement = (MessageElement) rutIpsElement.getChildren().get(1);
        	
        	numeroElement.toString();
        	dvElement.toString();
        	
        	/*
        	String numero = ((Text) numeroElement.getChildren().get(0)).getData();
        	String dv = ((Text) dvElement.getChildren().get(0)).getData();

        	boolean throwEx = false;
        	if (numero == null || numero.trim().length() == 0) {
        		((Text) numeroElement.getChildren().get(0)).setData("");
        		throwEx = true;
        		// throw new Exception("Error numero RUT");
        	}
        	
        	if (dv == null || dv.trim().length() == 0){
        		((Text) dvElement.getChildren().get(0)).setData("");
        		throwEx = true;
        		// throw new Exception("Error DV RUT");
        	}
        	
        	if (throwEx){ throw new Exception("Error en RUT o DV"); }
        	*/
        	
        	/*
        	 * 3) Cuando el sobre de entrada no tiene algunos de los nodos estadoSobre, glosaSobre o metadataOperacional,
        	 *  el servicio informa el codigo de error PISEE  PISEE 03: ERROR EN PARAMETROS DE ENTRADA. 
        	 *  Debe informar el código de error PISEE 04: ERROR EN XML DE ENTRADA 
        	 */
        	
        	// Valido el nodo metadataOperacional
        	request.getEncabezado().getMetadataOperacional().toString();
        	
    		// Valido el EstadoSobre
    		String estadoSobre = request.getEncabezado().getMetadataOperacional().getEstadoSobre().getValue();
    		
    		// VALIDO EL TAG, NO EL CONTENIDO
    		// if (estadoSobre == null || estadoSobre.trim().length() < 1 || new Integer(estadoSobre) < 0) throw new Exception("Error estadoSobre");
    		
    		// Valido la GlosaSobre
    		String glosaSobre = request.getEncabezado().getMetadataOperacional().getGlosaSobre();
    		if (glosaSobre == null) throw new Exception("Error glosaSobre");
    		// if (glosaSobre == null || glosaSobre.trim().length() < 1) throw new Exception("Error glosaSobre");
        	
        	
    	} catch (Exception ex){
    		// En caso de cualquier excepción, envío el mismo request, modificado, indicando el error
    		
    		/*
    		 * 2) Cuando el sobre de entrada no tiene el nodo idSobre, el servicio no entrega sobre de salida. 
    		 * Debe entregar como respuesta el sobre de entrada incluyendo el nodo idsobre faltante 
    		 * y el codigo de error PISEE 04: ERROR EN XML DE ENTRADA
    		 *  
    		 */
    		if (request.getEncabezado().getIdSobre() == null){
    			request.getEncabezado().setIdSobre("");
    		}
    		
    		request.getEncabezado().setFechaHoraReq( request.getEncabezado().getFechaHora() );
    		request.getEncabezado().setFechaHora( ahoraCalendar );
    		
    		request.getEncabezado().getProveedor().getServicios().setRespuestaServicio(
    				new EstadoGlosaType[]{ new EstadoGlosaType("NO", "ERROR") });
    		
    		request.getEncabezado().setEmisor("DIPRECA");
    		request.getEncabezado().setMetadataOperacional(
    				new EstadoGlosaSobreType(new EstadoSobreType("04"), "ERROR EN XML DE ENTRADA"));
    		
    		return request;
    	}
		
		
		
		
    	/*
    	 * Validacion de los *datos de consulta*
    	 * 
    	 * Encabezado: idSobre - fechaHora - estadoSobre - glosaSobre
    	 * Cuerpo: RutIPS - numero - dv
    	 * 
    	 * de lo contrario, es un error en los parametros de entrada
    	 * 
    	 */
    	try {
    		// Valido el ID Sobre
    		double idSobre = new Double( request.getEncabezado().getIdSobre() );
    		if (idSobre < 1) throw new Exception("Error idSobre");
    		
    		// Valido la fecha y hora
    		Date fechaRequest = request.getEncabezado().getFechaHora().getTime();
    		if (fechaRequest == null) throw new Exception("Error fechaRequest");
    		
    		// Valido el EstadoSobre
    		String estadoSobre = request.getEncabezado().getMetadataOperacional().getEstadoSobre().getValue();
    		if (estadoSobre == null || estadoSobre.trim().length() < 1 || new Integer(estadoSobre) < 0) throw new Exception("Error estadoSobre");
    		
    		// Valido la GlosaSobre
    		String glosaSobre = request.getEncabezado().getMetadataOperacional().getGlosaSobre();
    		if (glosaSobre == null || glosaSobre.trim().length() < 1) throw new Exception("Error glosaSobre");
    		
    		// Valido el RutIPS
        	DocumentoType documento = request.getCuerpo()[0].getDocumento();
        	MessageElement messageElement = documento.get_any()[0];
        	MessageElement rutIpsElement = (MessageElement) messageElement.getChildren().get(0);        	
        	rutIpsElement.toString();
        	
        	// Valido que el RUT y el DV sean no nulos
        	MessageElement numeroElement = (MessageElement) rutIpsElement.getChildren().get(0);
        	MessageElement dvElement = (MessageElement) rutIpsElement.getChildren().get(1);
        	
        	String numero = ((Text) numeroElement.getChildren().get(0)).getData();
        	String dv = ((Text) dvElement.getChildren().get(0)).getData();

        	if (numero == null || numero.trim().length() == 0) throw new Exception("Error numero RUT");
        	if (dv == null || dv.trim().length() == 0) throw new Exception("Error DV RUT");
        	
        	
    	} catch (Exception ex){
    		// En caso de cualquier excepción, envío el mismo request, modificado, indicando el error
    		
    		request.getEncabezado().setFechaHoraReq( request.getEncabezado().getFechaHora() );
    		request.getEncabezado().setFechaHora( ahoraCalendar );
    		
    		request.getEncabezado().getProveedor().getServicios().setRespuestaServicio(
    				new EstadoGlosaType[]{ new EstadoGlosaType("NO", "ERROR") });
    		
    		request.getEncabezado().setEmisor("DIPRECA");
    		request.getEncabezado().setMetadataOperacional(
    				new EstadoGlosaSobreType(new EstadoSobreType("03"), "ERROR EN PARAMETROS DE ENTRADA"));
    		
    		return request;
    	}
    	
    	    	
    	try {
    		
        	/*
        	 * Obtencion de los datos de la consulta
        	 */
        	
        	DocumentoType documento = request.getCuerpo()[0].getDocumento();
        	MessageElement messageElement = documento.get_any()[0];
        	MessageElement rutIpsElement = (MessageElement) messageElement.getChildren().get(0);
        	MessageElement numeroElement = (MessageElement) rutIpsElement.getChildren().get(0);
        	MessageElement dvElement = (MessageElement) rutIpsElement.getChildren().get(1);
        	
        	String numero = ((Text) numeroElement.getChildren().get(0)).getData();
        	String dv = ((Text) dvElement.getChildren().get(0)).getData();
        	
        	/*
        	 * Realizo la consulta
        	 */
        	
        	ConsultaImponentesDiprecaDao dao = new ConsultaImponentesDiprecaDao();
        	
        	Map resultado = dao.getDatosImponentePorRut(new Integer(numero), dv);
        	
    		Sobre response = request;        	
    		request.getEncabezado().setFechaHoraReq( request.getEncabezado().getFechaHora() );
    		request.getEncabezado().setFechaHora( ahoraCalendar );
        	
        	try {

        		if (!resultado.containsKey("codigoError")){
        			
            		/*
            		 * Construcción del TipoRespuesta con los datos del resultado
            		 */
            		
            		TipoRespuesta respuesta = new TipoRespuesta();
            		respuesta.setMensajeWebServices( (String) resultado.get("mensaje"));
            		respuesta.setFechaInforme(new Date());
            		// respuesta.setRun( new RunType(new PositiveInteger(numero), new Token(dv)) );
            		respuesta.setRun( new RunType(new Token(numero), new Token(dv)) );
            		respuesta.setNombreCompletoBeneficiario(new NombreType(
            				(String) resultado.get("nombres"), 
            				(String) resultado.get("apellidoPaterno"), 
            				(String) resultado.get("apellidoMaterno")));
            		respuesta.setFechaNacimiento((Date) resultado.get("fechaNacimiento"));
            		respuesta.setSexo(SexoValores.fromString( (String) resultado.get("sexo")));
            		respuesta.setSituacionIndividuo(TipoRespuestaSituacionIndividuo.fromString( (String)  resultado.get("situacionIndividuo") ));
            		respuesta.setFechaPension( (Date) resultado.get("fechaPension") );
            		respuesta.setTipoPension(TipoRespuestaTipoPension.fromString( (String) resultado.get("tipoPension") ));
            		
            		/*
            		 * Colocacion del TipoRespuesta en el sobre como el tag "DatosImponente"
            		 */
            		
            		DocumentoType documentoCuerpo = new DocumentoType();
            		documentoCuerpo.set_any(new MessageElement[]{
            			new MessageElement(new QName("http://servicios.dipreca.cl/ipsV01", "DatosImponente"), respuesta)	
            		});
            		
            		response.getCuerpo()[0].setDocumento(documentoCuerpo);        			
        			
        		}
        		

        		
        		/*
        		 * Codigo de respuesta y emisor en el encabezado
        		 */
        		
        		if (resultado.containsKey("codigoError")){
        			
            		response.getEncabezado().getProveedor().getServicios().setRespuestaServicio(
            				new EstadoGlosaType[]{ new EstadoGlosaType("NO", "ERROR") });
            		
            		response.getEncabezado().setEmisor("DIPRECA");
            		response.getEncabezado().setMetadataOperacional(
            				new EstadoGlosaSobreType(new EstadoSobreType( (String) resultado.get("codigoError") ), (String) resultado.get("mensaje")));
            		
        		} else {
        			
            		response.getEncabezado().getProveedor().getServicios().setRespuestaServicio(
            				new EstadoGlosaType[]{ new EstadoGlosaType("SI", "RESPUESTA EXITOSA") });
            		
            		response.getEncabezado().setEmisor("DIPRECA");
            		response.getEncabezado().setMetadataOperacional(new EstadoGlosaSobreType(new EstadoSobreType("00"), "TRANSACCION EXITOSA"));
            		
        		}
        		
        		
        		return response;
        		
        	} catch (Exception e) {
				e.printStackTrace();
			}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Si llega hasta este punto, ha ocurrido alguna excepción no recuperable 
		 * producto de problemas en el XML de entrada
		 * 
		 */
    	
		request.getEncabezado().setFechaHoraReq( request.getEncabezado().getFechaHora() );
		request.getEncabezado().setFechaHora( ahoraCalendar );
		
		request.getEncabezado().getProveedor().getServicios().setRespuestaServicio(
				new EstadoGlosaType[]{ new EstadoGlosaType("NO", "ERROR") });
		
		request.getEncabezado().setEmisor("DIPRECA");
		request.getEncabezado().setMetadataOperacional(
				new EstadoGlosaSobreType(new EstadoSobreType("04"), "ERROR EN XML DE ENTRADA"));
		
		return request;
    }

}
