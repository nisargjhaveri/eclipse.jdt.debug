/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.launching.environments;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.launching.IVMInstall;

/**
 * Manager for execution environments. The singleton manager is available
 * via <code>JavaRuntime.getExecutionEnvironmentsManager()</code>.
 * 
 * @since 3.2
 */
public interface IExecutionEnvironmentsManager {

	/**
	 * Returns all registered execution environments.
	 * 
	 * @return all registered execution environments
	 */
	public IExecutionEnvironment[] getExecutionEnvironments();
	
	/**
	 * Returns the execution environment associated with the given
	 * identifier or <code>null</code> if none.
	 * 
	 * @param id execution environment identifier 
	 * @return execution environment or <code>null</code>
	 */
	public IExecutionEnvironment getEnvironment(String id);
	
	/**
	 * Returns the exeuctuion environments associated with the specified
	 * vm install, possibly an empty collection.
	 * 
	 * @param vm vm install
	 * @return exeuctuion environments associated with the specified
	 * vm install, possibly an empty collection
	 */
	public IExecutionEnvironment[] getEnvironments(IVMInstall vm);

	/**
	 * Returns the vm installs that are compatible with the given 
	 * execution environment, possibly an empty collection.
	 * 
	 * @param environment execution environment
	 * @return vm installs that are compatible with the given 
	 * execution environment, possibly an empty collection
	 */
	public IVMInstall[] getVMInstalls(IExecutionEnvironment environment);
		
	/**
	 * Returns the vm install set as the default vm for the specified
	 * environment, or <code>null</code> if none.
	 * 
	 * @param environment execution environment
	 * @return default vm install for the environment
	 */
	public IVMInstall getDefaultVMInstall(IExecutionEnvironment environment);
	
	/**
	 * Sets the specified vm as the default vm for the given environment.
	 * The vm must be compatible with the specified environment. When the
	 * vm is <code>null</code> the default setting is removed.
	 * 
	 * @param environment execution environment
	 * @param vm default vm for the environment or <code>null</code>
	 * @throws CoreException if the given vm is not compatible with the
	 *  specified environment
	 */
	public void setDefaultVMInstall(IExecutionEnvironment environment, IVMInstall vm) throws CoreException;
}
