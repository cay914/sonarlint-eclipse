package org.sonarlint.eclipse.ui.internal.server.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.SelectionProviderAction;
import org.sonarlint.eclipse.core.internal.server.IServer;
import org.sonarlint.eclipse.ui.internal.Messages;
import org.sonarlint.eclipse.ui.internal.server.DeleteServerDialog;

public class GlobalDeleteAction extends SelectionProviderAction {
  private List<IServer> servers;
  private Shell shell;

  public GlobalDeleteAction(Shell shell, ISelectionProvider selectionProvider) {
    super(selectionProvider, Messages.actionDelete);
    this.shell = shell;
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
    setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
    setActionDefinitionId(IWorkbenchCommandConstants.EDIT_DELETE);
  }

  @Override
  public void selectionChanged(IStructuredSelection sel) {
    if (sel.isEmpty()) {
      setEnabled(false);
      return;
    }
    servers = new ArrayList<>();
    boolean enabled = false;
    Iterator iterator = sel.iterator();
    while (iterator.hasNext()) {
      Object obj = iterator.next();
      if (obj instanceof IServer) {
        IServer server = (IServer) obj;
        servers.add(server);
        enabled = true;
      } else {
        setEnabled(false);
        return;
      }
    }
    setEnabled(enabled);
  }

  @Override
  public void run() {
    // It is possible that the server is created and added to the server view on workbench
    // startup. As a result, when the user switches to the server view, the server is
    // selected, but the selectionChanged event is not called, which results in servers
    // being null. When servers is null the server will not be deleted and the error log
    // will have an IllegalArgumentException.
    //
    // To handle the case where servers is null, the selectionChanged method is called
    // to ensure servers will be populated.
    if (servers == null) {

      IStructuredSelection sel = getStructuredSelection();
      if (sel != null) {
        selectionChanged(sel);
      }
    }

    if (servers != null) {
      // No check is made for valid parameters at this point, since if there is a failure, it
      // should be output to the error log instead of failing silently.
      DeleteServerDialog dsd = new DeleteServerDialog(shell, servers);
      dsd.open();
    }
  }

}