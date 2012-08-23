package org.eclipse.bpmn2.modeler.ui.editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.bpmn2.modeler.core.validation.BPMN2ValidationStatusLoader;
import org.eclipse.bpmn2.modeler.core.validation.ValidationStatusAdapter;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class BPMN2ResourceChangeListener implements IResourceChangeListener {
	
	BPMN2Editor editor;
	
	public BPMN2ResourceChangeListener(BPMN2Editor editor) {
		this.editor = editor;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
        final IResourceDelta modelFileDelta = event.getDelta().findMember(editor.getModelFile().getFullPath());
        if (modelFileDelta == null) {
            return;
        }
        final IMarkerDelta[] markerDeltas = modelFileDelta.getMarkerDeltas();
        if (markerDeltas == null || markerDeltas.length == 0) {
            return;
        }

        final List<IMarker> newMarkers = new ArrayList<IMarker>();
        final Set<String> deletedMarkers = new HashSet<String>();
        for (IMarkerDelta markerDelta : markerDeltas) {
            switch (markerDelta.getKind()) {
            case IResourceDelta.ADDED:
                newMarkers.add(markerDelta.getMarker());
                break;
            case IResourceDelta.CHANGED:
                newMarkers.add(markerDelta.getMarker());
                // fall through
            case IResourceDelta.REMOVED:
                final String uri = markerDelta.getAttribute(EValidator.URI_ATTRIBUTE, null);
                if (uri != null) {
                    deletedMarkers.add(uri);
                }
            }
        }

        final Set<EObject> updatedObjects = new LinkedHashSet<EObject>();
        for (String uri : deletedMarkers) {
            final EObject eobject = editor.getEditingDomain().getResourceSet().getEObject(URI.createURI(uri),
                    false);
            if (eobject == null) {
                continue;
            }
            final ValidationStatusAdapter adapter = (ValidationStatusAdapter) EcoreUtil
                    .getRegisteredAdapter(eobject, ValidationStatusAdapter.class);
            if (adapter == null) {
                continue;
            }
            adapter.clearValidationStatus();
            updatedObjects.add(eobject);
        }
        
        BPMN2ValidationStatusLoader vsl = new BPMN2ValidationStatusLoader(editor);
        updatedObjects.addAll(vsl.load(newMarkers));
        editor.getEditorSite().getShell().getDisplay().asyncExec(new Runnable() {
            public void run() {
                for (EObject eobject : updatedObjects) {
                    PictogramElement pe = editor.getDiagramTypeProvider().getFeatureProvider()
                            .getPictogramElementForBusinessObject(eobject);
                    if (pe != null) {
                    	editor.getRefreshBehavior().refreshRenderingDecorators(pe);
                    }
                }
            }
        });
    }

}
