/*
 * SonarQube Eclipse
 * Copyright (C) 2010-2015 SonarSource
 * sonarqube@googlegroups.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.ide.eclipse.core.internal.servers;

import java.util.Collection;
import javax.annotation.CheckForNull;

/**
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ISonarServersManager {

  Collection<SonarServer> getServers();

  Collection<SonarServer> reloadServers();

  void removeServer(SonarServer server);

  void addServer(SonarServer server);

  @CheckForNull
  SonarServer findServer(String idOrUrl);

  /**
   * Create a new ISonarServer without saving it in Eclipse preferences.
   * @param location
   * @param username
   * @param password
   * @return
   */
  SonarServer create(String id, String location, String username, String password);

}
