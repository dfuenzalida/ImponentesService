/**
 * ConsultaImponentesDiprecaBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.gob.aem.valida;

public class ConsultaImponentesDiprecaBindingSkeleton implements cl.gob.aem.valida.ConsultaImponentesDipreca, org.apache.axis.wsdl.Skeleton {
    private cl.gob.aem.valida.ConsultaImponentesDipreca impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://valida.aem.gob.cl", "sobre"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://valida.aem.gob.cl", ">sobre"), cl.gob.aem.valida.Sobre.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("consultaImponentesDipreca", _params, new javax.xml.namespace.QName("http://valida.aem.gob.cl", "sobre"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://valida.aem.gob.cl", ">sobre"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "ConsultaImponentesDipreca"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("consultaImponentesDipreca") == null) {
            _myOperations.put("consultaImponentesDipreca", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("consultaImponentesDipreca")).add(_oper);
    }

    public ConsultaImponentesDiprecaBindingSkeleton() {
        this.impl = new cl.gob.aem.valida.ConsultaImponentesDiprecaBindingImpl();
    }

    public ConsultaImponentesDiprecaBindingSkeleton(cl.gob.aem.valida.ConsultaImponentesDipreca impl) {
        this.impl = impl;
    }
    public cl.gob.aem.valida.Sobre consultaImponentesDipreca(cl.gob.aem.valida.Sobre request) throws java.rmi.RemoteException
    {
        cl.gob.aem.valida.Sobre ret = impl.consultaImponentesDipreca(request);
        return ret;
    }

}
