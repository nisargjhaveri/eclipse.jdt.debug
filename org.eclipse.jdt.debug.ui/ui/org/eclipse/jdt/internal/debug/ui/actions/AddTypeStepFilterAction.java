/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.internal.debug.ui.actions;


import org.eclipse.debug.core.DebugException;
import org.eclipse.jdt.debug.core.IJavaStackFrame;

/**
 * Adds a Java type to the set of active step filters.
 */
public class AddTypeStepFilterAction extends AbstractAddStepFilterAction {

	/**
	 * @see org.eclipse.jdt.internal.debug.ui.actions.AbstractAddStepFilterAction#generateStepFilterPattern(org.eclipse.jdt.debug.core.IJavaStackFrame)
	 */
	protected String generateStepFilterPattern(IJavaStackFrame frame) {
		try {
			return frame.getReceivingTypeName();
		} catch (DebugException de) {
			return null;
		}
	}

}
