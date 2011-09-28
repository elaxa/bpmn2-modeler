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


package org.eclipse.bpmn2.modeler.ui.property;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;

/**
 * @author Bob Brodt
 *
 */
public class TrackingFormToolkit extends FormToolkit {

	private final ArrayList<Widget> widgets = new ArrayList<Widget>() {

		@Override
		public boolean add(Widget widget) {
			if (!contains(widget))
				return super.add(widget);
			return false;
		}
		
	};
    private Font descriptionFont = null;

	/**
	 * @param display
	 */
	public TrackingFormToolkit(Display display) {
		super(display);
	}

	public void dispose() {
		super.dispose();
		if (descriptionFont!=null)
			descriptionFont.dispose();
		disposeWidgets();
	}

	public void track(Composite composite) {
		super.adapt(composite);
		widgets.add(composite);
	}

	public void disposeWidgets() {
		for (Widget w : widgets) {
			w.dispose();
		}
		widgets.clear();
	}
	
	public Label createLabel(Composite parent, String text) {
		Label label = super.createLabel(parent, text);
		widgets.add(label);
		return label;
	}
	
	public Label createLabel(Composite parent, String text, int style) {
		Label label = super.createLabel(parent, text, style);
		widgets.add(label);
		return label;
	}
	
	public Text createText(Composite parent, String value) {
		Text text = super.createText(parent, value);
		widgets.add(text);
		return text;
	}
	
	public Text createText(Composite parent, String value, int style) {
		Text text = super.createText(parent, value, style);
		widgets.add(text);
		return text;
	}
	
	public Button createButton(Composite parent, String text, int style) {
		Button button = super.createButton(parent, text, style);
		widgets.add(button);
		return button;
	}
	
	public Button createPushButton(Composite parent, String text) {
		return createButton(parent,text,SWT.PUSH);
	}
	
	public Button createCheckboxButton(Composite parent, String text) {
		return createButton(parent,text,SWT.CHECK);
	}
	
	public ComboViewer createComboViewer(Composite parent, AdapterFactoryLabelProvider labelProvider, int style) {
		ComboViewer comboViewer = new ComboViewer(parent, style);
		comboViewer.setLabelProvider(labelProvider);

		Combo combo = comboViewer.getCombo();
		adapt(combo);
		
		widgets.add(combo);
		return comboViewer;
	}

	public Composite createComposite(Composite parent) {
		Composite composite = super.createComposite(parent);
		widgets.add(composite);
		return composite;
	}

	public Composite createComposite(Composite parent, int style) {
		Composite composite = super.createComposite(parent, style);
		widgets.add(composite);
		return composite;
	}
	
	public Table createTable(Composite parent, int style) {
		Table table = super.createTable(parent, style);
		widgets.add(table);
		return table;
	}
	
	public SashForm createSashForm(Composite parent, int style) {
		SashForm sashForm = new SashForm(parent, SWT.NONE);
		sashForm.setSashWidth(5);
		adapt(sashForm);
		paintBordersFor(sashForm);
		widgets.add(sashForm);
		return sashForm;
	}
	
	public Section createSection(Composite parent, String title) {
		Section section = createSection(parent,
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED |
				ExpandableComposite.TITLE_BAR);
		paintBordersFor(section);
		section.setText(title);
		widgets.add(section);
		return section;
	}
	
	public Section createSection(Composite parent, String title, int style) {
		Section section = createSection(parent,style);
		paintBordersFor(section);
		section.setText(title);
		widgets.add(section);
		return section;
	}

	protected StyledText createDescription(Composite parent, String description) {
		Display display = Display.getCurrent();
		final StyledText styledText = new StyledText(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		styledText.setText(description);
		
		adapt(styledText);
		widgets.add(styledText);

	    styledText.setFont(getDescriptionFont());
		
		styledText.setBackground(display.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
		styledText.setForeground(display.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
		
		return styledText;
	}

	/**
	 * @return
	 */
	public Font getDescriptionFont() {
		if (descriptionFont==null) {
			Display display = Display.getCurrent();
		    FontData data = display.getSystemFont().getFontData()[0];
		    descriptionFont = new Font(display, data.getName(), data.getHeight() + 1, SWT.NONE);
		}
		return descriptionFont;
	}
}