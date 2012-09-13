/*******************************************************************************
 * Copyright (c) 2011 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.ui.adapters.properties;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.CorrelationKey;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Bob Brodt
 *
 */
public class CorrelationKeyPropertiesAdapter extends ExtendedPropertiesAdapter<CorrelationKey> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public CorrelationKeyPropertiesAdapter(AdapterFactory adapterFactory, CorrelationKey object) {
		super(adapterFactory, object);
		
    	final EStructuralFeature ref = Bpmn2Package.eINSTANCE.getCorrelationKey_CorrelationPropertyRef();
    	setFeatureDescriptor(ref, new RootElementRefFeatureDescriptor<CorrelationKey>(adapterFactory,object,ref));
	}

}
