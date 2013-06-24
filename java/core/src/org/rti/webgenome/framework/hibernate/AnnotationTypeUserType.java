/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.framework.hibernate;

import org.rti.bioinfo.util.framework.hibernate.EnumUserType;
import org.rti.webgenome.domain.AnnotationType;


/**
 * User type for persisting the <code>AnnotationType</code>
 * enumeration.
 * @author dhall
 *
 */
public class AnnotationTypeUserType extends EnumUserType<AnnotationType> {

	/**
	 * Constructor.
	 */
	public AnnotationTypeUserType() {
		super(AnnotationType.class);
	}
}
