//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.01.10 at 10:39:12 AM CST 
//


package org.isotc211._2005.gmx;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import net.opengis.gml._3.IdentifiedObjectType;
import org.isotc211._2005.gmd.PTLocalePropertyType;


/**
 * XML attributes contraints: - 1) Id is mandatory - 2) codeSpace (type xsd:anyURI) is mandatory
 * 
 * <p>Java class for EllipsoidAlt_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EllipsoidAlt_Type">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml/3.2}IdentifiedObjectType">
 *       &lt;sequence>
 *         &lt;element name="locale" type="{http://www.isotc211.org/2005/gmd}PT_Locale_PropertyType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="codeSpace" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EllipsoidAlt_Type", propOrder = {
    "locale"
})
public class EllipsoidAltType
    extends IdentifiedObjectType
{

    @XmlElement(required = true)
    protected PTLocalePropertyType locale;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String codeSpace;

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link PTLocalePropertyType }
     *     
     */
    public PTLocalePropertyType getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link PTLocalePropertyType }
     *     
     */
    public void setLocale(PTLocalePropertyType value) {
        this.locale = value;
    }

    /**
     * Gets the value of the codeSpace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeSpace() {
        return codeSpace;
    }

    /**
     * Sets the value of the codeSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeSpace(String value) {
        this.codeSpace = value;
    }

}
