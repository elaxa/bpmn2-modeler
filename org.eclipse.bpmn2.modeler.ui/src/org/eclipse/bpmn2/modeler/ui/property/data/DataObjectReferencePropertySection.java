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

package org.eclipse.bpmn2.modeler.ui.property.data;

import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.DataObjectReference;
import org.eclipse.bpmn2.DataState;
import org.eclipse.bpmn2.modeler.ui.property.AbstractBpmn2PropertiesComposite;
import org.eclipse.bpmn2.modeler.ui.property.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.ui.property.DefaultPropertiesComposite;
import org.eclipse.bpmn2.modeler.ui.property.DefaultPropertiesComposite.AbstractItemProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

/**
 * @author Bob Brodt
 *
 */
public class DataObjectReferencePropertySection extends AbstractBpmn2PropertySection {

	private AbstractItemProvider dataObjectReferenceItemProvider;
	private AbstractItemProvider dataStateItemProvider;

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.property.AbstractBpmn2PropertySection#createSectionRoot()
	 */
	@Override
	protected AbstractBpmn2PropertiesComposite createSectionRoot() {
		return new DataObjectReferencePropertiesComposite(this);
	}

	@Override
	protected EObject getBusinessObjectForPictogramElement(PictogramElement pe) {
		EObject bo = super.getBusinessObjectForPictogramElement(pe);
		if (bo instanceof DataObjectReference) {
			return bo;
		}		
		return null;
	}
	
	public class DataObjectReferencePropertiesComposite extends DefaultPropertiesComposite {

		/**
		 * @param section
		 */
		public DataObjectReferencePropertiesComposite(AbstractBpmn2PropertySection section) {
			super(section);
		}

		@Override
		public AbstractItemProvider getItemProvider(EObject object) {
			if (object instanceof DataState) {
				if (dataStateItemProvider == null) {
					dataStateItemProvider = new AbstractItemProvider(object) {
						String[] attributes = new String[] { "id", "name" };
						
						@Override
						public String[] getAttributes() {
							return attributes; 
						}
					};
				}
				return dataStateItemProvider;
			}
			else if (dataObjectReferenceItemProvider == null) {
				dataObjectReferenceItemProvider = new AbstractItemProvider(object) {
					String[] refs = new String[] { "dataObjectRef" };
					String[] children = new String[] { "dataState" };

					@Override
					public String[] getAttributes() {
						return refs; 
					}
					
					@Override
					public String[] getReferences() {
						return null;
					}
					
					@Override
					public String[] getChildren(String name) {
						return children;
					}
					
					@Override
					public boolean alwaysShowAdvancedProperties() {
						return true;
					}
				};
			}
			return dataObjectReferenceItemProvider;
		}
		
	}
}