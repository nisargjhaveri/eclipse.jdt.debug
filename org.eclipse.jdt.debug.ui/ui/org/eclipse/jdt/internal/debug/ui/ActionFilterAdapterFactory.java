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
package org.eclipse.jdt.internal.debug.ui;

 
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.debug.core.IJavaStackFrame;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.debug.core.IJavaVariable;
import org.eclipse.jdt.internal.debug.ui.display.JavaInspectExpression;
import org.eclipse.ui.IActionFilter;

/**
 * UI adapter factory for JDI Debug
 */
/*package*/ class ActionFilterAdapterFactory implements IAdapterFactory {

	/**
	 * @see IAdapterFactory#getAdapter(Object, Class)
	 */
	public Object getAdapter(Object obj, Class adapterType) {
		if (adapterType.isInstance(obj)) {
			return obj;
		}
		if (adapterType == IActionFilter.class) {
			if (obj instanceof IJavaThread) {
				return new JavaThreadActionFilter();
			} else if (obj instanceof IJavaStackFrame) {
				return new JavaStackFrameActionFilter();
			} else if (obj instanceof IJavaVariable) {
				return new JavaVariableActionFilter();
			} else if (obj instanceof IMethod) {
				return new MethodActionFilter();
			} else if (obj instanceof JavaInspectExpression) {
				return new JavaInspectExpressionActionFilter();
			} else if (obj instanceof JavaWatchExpression) {
				return new JavaWatchExpressionActionFilter();
			}
		}
		return null;
	}

	/**
	 * @see IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[] {
			IActionFilter.class 
		};
	}
}


