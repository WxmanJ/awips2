/**
 * This software was developed and / or modified by Raytheon Company,
 * pursuant to Contract DG133W-05-CQ-1067 with the US Government.
 * 
 * U.S. EXPORT CONTROLLED TECHNICAL DATA
 * This software product contains export-restricted data whose
 * export/transfer/disclosure is restricted by U.S. law. Dissemination
 * to non-U.S. persons whether in the United States or abroad requires
 * an export license or other authorization.
 * 
 * Contractor Name:        Raytheon Company
 * Contractor Address:     6825 Pine Street, Suite 340
 *                         Mail Stop B8
 *                         Omaha, NE 68106
 *                         402.291.0100
 * 
 * See the AWIPS II Master Rights File ("Master Rights File.pdf") for
 * further licensing information.
 **/
package com.raytheon.uf.viz.collaboration.ui.role;

import com.google.common.eventbus.Subscribe;
import com.raytheon.uf.common.status.IUFStatusHandler;
import com.raytheon.uf.common.status.UFStatus;
import com.raytheon.uf.viz.collaboration.comm.identity.ISharedDisplaySession;
import com.raytheon.uf.viz.collaboration.comm.identity.user.ParticipantRole;
import com.raytheon.uf.viz.collaboration.comm.provider.TransferRoleCommand;
import com.raytheon.uf.viz.collaboration.data.CollaborationDataManager;
import com.raytheon.uf.viz.collaboration.ui.editor.CollaborationEditor;
import com.raytheon.uf.viz.collaboration.ui.editor.EditorSetup;
import com.raytheon.uf.viz.collaboration.ui.editor.SharedEditor;
import com.raytheon.uf.viz.collaboration.ui.rsc.CollaborationResource;
import com.raytheon.uf.viz.collaboration.ui.rsc.CollaborationResourceData;
import com.raytheon.uf.viz.core.VizApp;
import com.raytheon.uf.viz.core.drawables.IDescriptor;
import com.raytheon.uf.viz.core.drawables.ResourcePair;

/**
 * Handles the events of a session that are specific to the Participant role.
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Mar 26, 2012            njensen     Initial creation
 * 
 * </pre>
 * 
 * @author njensen
 * @version 1.0
 */

public class ParticipantEventController extends AbstractRoleEventController {

    private static final transient IUFStatusHandler statusHandler = UFStatus
            .getHandler(ParticipantEventController.class);

    private CollaborationResource collabRsc;

    public ParticipantEventController(ISharedDisplaySession session) {
        super(session);
    }

    @Subscribe
    public void initDataArrived(final SharedEditor se) {
        // TODO need to detect if we already have a CollaborationEditor for
        // this session. If so, that implies DataProvider changed and we
        // should reuse the editor, reinitializing the descriptor and
        // renderable display but keeping the drawing and telestrator
        VizApp.runSync(new Runnable() {

            @Override
            public void run() {
                CollaborationEditor editor = EditorSetup.createEditor(se);
                initializeResources(editor.getActiveDisplayPane()
                        .getDescriptor());
                CollaborationDataManager.getInstance().editorCreated(
                        session.getSessionId(), editor);
            }
        });
        super.activateTelestrator(); // TODO should this be elsewhere?
    }

    private void initializeResources(IDescriptor desc) {
        CollaborationResourceData crd = new CollaborationResourceData();
        ResourcePair rp = ResourcePair.constructSystemResourcePair(crd);
        desc.getResourceList().add(rp);
        desc.getResourceList().instantiateResources(desc, true);
        collabRsc = (CollaborationResource) rp.getResource();
        this.session.registerEventHandler(collabRsc);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.raytheon.uf.viz.collaboration.ui.role.AbstractRoleEventController
     * #shutdown()
     */
    @Override
    public void shutdown() {
        super.shutdown();
        super.deactivateTelestrator(); // TODO should this be here?
        if (this.collabRsc != null) {
            this.session.unRegisterEventHandler(collabRsc);
        }
    }

    @Subscribe
    public void roleTransferred(TransferRoleCommand cmd) {
        if (cmd.getRole() == ParticipantRole.SESSION_LEADER) {
            System.out.println("Current session's username: "
                    + session.getUserID().getFQName());
            if (cmd.getUser().equals(session.getUserID().getFQName())) {
                // this cave should assume session leader control
                InputUtil.enableSessionLeaderInput(CollaborationDataManager
                        .getInstance().getEditor(session.getSessionId()));
            } else if (session.getCurrentSessionLeader().equals(
                    session.getUserID().getFQName())
                    && !session.getCurrentSessionLeader().getFQName()
                            .equals(cmd.getUser())) {
                // this cave should release session leader control
                InputUtil.disableSessionLeaderInput(CollaborationDataManager
                        .getInstance().getEditor(session.getSessionId()));
            }
        }
    }
}
