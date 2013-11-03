
package ee.ut.soap.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createPlant complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createPlant">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://web.soap.ut.ee/}plant" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createPlant", propOrder = {
    "plant"
})
public class CreatePlant {

    @XmlElement(namespace = "http://web.soap.ut.ee/")
    protected PlantResource plant;

    /**
     * Gets the value of the plant property.
     * 
     * @return
     *     possible object is
     *     {@link PlantResource }
     *     
     */
    public PlantResource getPlant() {
        return plant;
    }

    /**
     * Sets the value of the plant property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlantResource }
     *     
     */
    public void setPlant(PlantResource value) {
        this.plant = value;
    }

}
