
package ee.ut.soap.client;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "NotFoundException", targetNamespace = "http://web.soap.ut.ee/")
public class NotFoundException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private NotFoundException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public NotFoundException_Exception(String message, NotFoundException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public NotFoundException_Exception(String message, NotFoundException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: ee.ut.soap.client.NotFoundException
     */
    public NotFoundException getFaultInfo() {
        return faultInfo;
    }

}
