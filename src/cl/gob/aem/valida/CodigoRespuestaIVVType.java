/**
 * CodigoRespuestaIVVType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.gob.aem.valida;

public class CodigoRespuestaIVVType  implements java.io.Serializable {
    private cl.gob.aem.valida.CodigoRespuestaIVVTypeCodigo codigo;

    public CodigoRespuestaIVVType() {
    }

    public CodigoRespuestaIVVType(
           cl.gob.aem.valida.CodigoRespuestaIVVTypeCodigo codigo) {
           this.codigo = codigo;
    }


    /**
     * Gets the codigo value for this CodigoRespuestaIVVType.
     * 
     * @return codigo
     */
    public cl.gob.aem.valida.CodigoRespuestaIVVTypeCodigo getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this CodigoRespuestaIVVType.
     * 
     * @param codigo
     */
    public void setCodigo(cl.gob.aem.valida.CodigoRespuestaIVVTypeCodigo codigo) {
        this.codigo = codigo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CodigoRespuestaIVVType)) return false;
        CodigoRespuestaIVVType other = (CodigoRespuestaIVVType) obj;
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
              this.codigo.equals(other.getCodigo())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CodigoRespuestaIVVType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://valida.aem.gob.cl", "CodigoRespuestaIVVType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valida.aem.gob.cl", "Codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://valida.aem.gob.cl", ">CodigoRespuestaIVVType>Codigo"));
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
