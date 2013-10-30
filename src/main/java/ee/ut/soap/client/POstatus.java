
package ee.ut.soap.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pOstatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="pOstatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PENDING_CONFIRMATION"/>
 *     &lt;enumeration value="PENDING_UPDATE"/>
 *     &lt;enumeration value="UPDATE_REJECTED"/>
 *     &lt;enumeration value="OPEN"/>
 *     &lt;enumeration value="RECIEVED"/>
 *     &lt;enumeration value="REJECTED"/>
 *     &lt;enumeration value="CLOSED"/>
 *     &lt;enumeration value="CANCELLATION_REQUESTED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "pOstatus")
@XmlEnum
public enum POstatus {

    PENDING_CONFIRMATION,
    PENDING_UPDATE,
    UPDATE_REJECTED,
    OPEN,
    RECIEVED,
    REJECTED,
    CLOSED,
    CANCELLATION_REQUESTED;

    public String value() {
        return name();
    }

    public static POstatus fromValue(String v) {
        return valueOf(v);
    }

}
