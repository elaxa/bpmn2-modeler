package org.eclipse.bpmn2.modeler.core.features.choreography;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.core.features.choreography.messages"; //$NON-NLS-1$
	public static String UpdateChoreographyNameFeature_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
