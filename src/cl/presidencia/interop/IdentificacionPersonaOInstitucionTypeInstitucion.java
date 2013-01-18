/**
 * IdentificacionPersonaOInstitucionTypeInstitucion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.presidencia.interop;

public class IdentificacionPersonaOInstitucionTypeInstitucion  implements java.io.Serializable {
    private java.lang.Object codigo;

    private java.lang.Object nombreInstitucion;

    public IdentificacionPersonaOInstitucionTypeInstitucion() {
    }

    public IdentificacionPersonaOInstitucionTypeInstitucion(
           java.lang.Object codigo,
           java.lang.Object nombreInstitucion) {
           this.codigo = codigo;
           this.nombreInstitucion = nombreInstitucion;
    }


    /**
     * Gets the codigo value for this IdentificacionPersonaOInstitucionTypeInstitucion.
     * 
     * @return codigo
     */
    public java.lang.Object getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this IdentificacionPersonaOInstitucionTypeInstitucion.
     * 
     * @param codigo
     */
    public void setCodigo(java.lang.Object codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the nombreInstitucion value for this IdentificacionPersonaOInstitucionTypeInstitucion.
     * 
     * @return nombreInstitucion
     */
    public java.lang.Object getNombreInstitucion() {
        return nombreInstitucion;
    }


    /**
     * Sets the nombreInstitucion value for this IdentificacionPersonaOInstitucionTypeInstitucion.
     * 
     * @param nombreInstitucion
     */
    public void setNombreInstitucion(java.lang.Object nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IdentificacionPersonaOInstitucionTypeInstitucion)) return false;
        IdentificacionPersonaOInstitucionTypeInstitucion other = (IdentificacionPersonaOInstitucionTypeInstitucion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigo==null && other.getCodigo()==null) || 
             (this.codigo!=null &&
              this.codigo.equals(other.getCodigo()))) &&
            ((this.nombreInstitucion==null && other.getNombreInstitucion()==null) || 
             (this.nombreInstitucion!=null &&
              this.nombreInstitucion.equals(other.getNombreInstitucion())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCodigo() != null) {
            _hashCode += getCodigo().hashCode();
        }
        if (getNombreInstitucion() != null) {
            _hashCode += getNombreInstitucion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IdentificacionPersonaOInstitucionTypeInstitucion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://interop.presidencia.cl", ">IdentificacionPersonaOInstitucionType>Institucion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://interop.presidencia.cl", "Codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreInstitucion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://interop.presidencia.cl", "NombreInstitucion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
