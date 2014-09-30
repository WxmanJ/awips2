/**
 * 
 */
package gov.noaa.nws.ncep.edex.common.metparameters;


import javax.measure.quantity.Dimensionless;
import javax.measure.unit.Unit;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.raytheon.uf.common.serialization.ISerializableObject;
import com.raytheon.uf.common.serialization.annotations.DynamicSerialize;
import com.raytheon.uf.common.units.UnitAdapter;

/**
 * Maps to the GEMPAK parameter PZ12
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@DynamicSerialize

 public class CondProbOf12HrFreezingPrecip extends AbstractMetParameter implements
		Dimensionless, ISerializableObject {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -5752791443181533431L;

	public CondProbOf12HrFreezingPrecip()throws Exception {
		 super( new UnitAdapter().marshal(UNIT) );
	}
 }