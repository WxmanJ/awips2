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
package com.raytheon.uf.viz.collaboration.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXB;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.presence.IPresence;
import org.eclipse.ecf.presence.IPresence.Mode;
import org.eclipse.ecf.presence.roster.IRoster;
import org.eclipse.ecf.presence.roster.IRosterEntry;
import org.eclipse.ecf.presence.roster.IRosterGroup;
import org.eclipse.ecf.presence.roster.RosterEntry;
import org.eclipse.swt.graphics.Image;

import com.raytheon.uf.common.localization.IPathManager;
import com.raytheon.uf.common.localization.LocalizationContext;
import com.raytheon.uf.common.localization.LocalizationContext.LocalizationLevel;
import com.raytheon.uf.common.localization.LocalizationContext.LocalizationType;
import com.raytheon.uf.common.localization.LocalizationFile;
import com.raytheon.uf.common.localization.PathManagerFactory;
import com.raytheon.uf.common.localization.exception.LocalizationOpFailedException;
import com.raytheon.uf.common.status.IUFStatusHandler;
import com.raytheon.uf.common.status.UFStatus;
import com.raytheon.uf.common.status.UFStatus.Priority;
import com.raytheon.uf.viz.collaboration.comm.provider.user.IDConverter;
import com.raytheon.uf.viz.collaboration.comm.provider.user.UserId;
import com.raytheon.uf.viz.collaboration.comm.provider.user.UserIdWrapper;
import com.raytheon.uf.viz.collaboration.data.CollaborationDataManager;
import com.raytheon.uf.viz.core.icon.IconUtil;

/**
 * Methods for sending, receiving messages
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Feb 24, 2012            mnash     Initial creation
 * 
 * </pre>
 * 
 * @author mnash
 * @version 1.0
 */

public class CollaborationUtils {

    private static final transient IUFStatusHandler statusHandler = UFStatus
            .getHandler(CollaborationUtils.class);

    public static final org.eclipse.ecf.presence.IPresence.Mode[] statusModes = {
            Mode.AVAILABLE, Mode.DND, Mode.AWAY };

    private static final String PREFIX_CONFERENCE = "conference.";

    /**
     * Get an image associated with the node.
     * 
     * @param node
     * @return image
     */
    public static Image getNodeImage(String name) {
        return IconUtil.getImageDescriptor(Activator.getDefault().getBundle(),
                name.toLowerCase() + ".gif").createImage();
    }

    public static void readAliases() {
        UserId[] ids = getIds();
        IRoster roster = CollaborationDataManager.getInstance()
                .getCollaborationConnection().getRosterManager().getRoster();
        List<IRosterEntry> entries = new ArrayList<IRosterEntry>();
        for (Object ob : roster.getItems()) {
            if (ob instanceof IRosterGroup) {
                IRosterGroup group = (IRosterGroup) ob;
                for (Object entryOb : group.getEntries()) {
                    entries.add((IRosterEntry) entryOb);
                }
            } else if (ob instanceof IRosterEntry) {
                entries.add((IRosterEntry) ob);
            }
        }
        for (IRosterEntry entry : entries) {
            for (UserId id : ids) {
                UserId sId = IDConverter.convertFrom(entry.getUser());
                if (id.equals(sId)) {
                    sId.setAlias(id.getAlias());
                    entry = new RosterEntry(entry.getParent(), sId,
                            entry.getPresence());
                }
            }
        }
    }

    public static UserId[] getIds() {
        IPathManager pm = PathManagerFactory.getPathManager();
        LocalizationContext context = pm.getContext(
                LocalizationType.CAVE_STATIC, LocalizationLevel.USER);
        LocalizationFile file = PathManagerFactory.getPathManager()
                .getLocalizationFile(
                        context,
                        "collaboration" + File.separator
                                + "collaborationAliases.xml");
        if (file.exists()) {
            UserIdWrapper ids = (UserIdWrapper) JAXB.unmarshal(file.getFile(),
                    UserIdWrapper.class);
            if (ids.getUserIds() == null) {
                return new UserId[0];
            }
            return ids.getUserIds();
        }
        return new UserId[0];
    }

    public static void addAlias() {
        Job job = new Job("Saving aliases to file") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                IPathManager pm = PathManagerFactory.getPathManager();
                LocalizationContext context = pm.getContext(
                        LocalizationType.CAVE_STATIC, LocalizationLevel.USER);
                LocalizationFile file = pm.getLocalizationFile(context,
                        "collaboration" + File.separator
                                + "collaborationAliases.xml");
                IRoster roster = CollaborationDataManager.getInstance()
                        .getCollaborationConnection().getRosterManager()
                        .getRoster();
                Set<UserId> ids = new HashSet<UserId>();

                // get the entries that are alone
                for (Object ob : roster.getItems()) {
                    if (ob instanceof IRosterEntry) {
                        IRosterEntry entry = (IRosterEntry) ob;
                        UserId id = (UserId) entry.getUser();
                        if (id.getAlias() != null && !id.getAlias().isEmpty()) {
                            ids.add(id);
                        }
                    } else if (ob instanceof IRosterGroup) {
                        for (Object entryOb : ((IRosterGroup) ob).getEntries()) {
                            IRosterEntry entry = (IRosterEntry) entryOb;
                            UserId id = IDConverter
                                    .convertFrom(entry.getUser());
                            if (id.getAlias() != null
                                    && !id.getAlias().isEmpty()) {
                                ids.add(id);
                            }
                        }
                    }
                }

                // a wrapper for JAXB
                UserIdWrapper wrapper = new UserIdWrapper();
                wrapper.setUserIds(ids.toArray(new UserId[0]));
                try {
                    JAXB.marshal(wrapper, file.getFile());
                    file.save();
                    return Status.OK_STATUS;
                } catch (LocalizationOpFailedException e) {
                    statusHandler.handle(Priority.PROBLEM,
                            e.getLocalizedMessage(), e);
                    return Status.CANCEL_STATUS;
                }
            }
        };
        job.schedule();
    }

    public static void sendChatMessage(List<String> ids, String message) {
        // TODO transform Strings to IDS
        System.err.println("sendChatMessage: " + message);
        // if (CollaborationData.getInstance().hasChat(null)) {
        // IChat chat = CollaborationData.getInstance().getOpenChat(null);
        // try {
        // chat.sendChatMessage(message);
        // } catch (ECFException e) {
        // statusHandler.handle(Priority.PROBLEM, e.getLocalizedMessage(),
        // e);
        // }
        // }
    }

    public static void createChat(List<ID> users) {
        System.err.println("createChat: " + users);
        // IPresenceContainerAdapter presence = CollaborationData.getInstance()
        // .getPresence();
        // try {
        // presence.getChatManager().createChat(users.get(0),
        // new IIMMessageListener() {
        // @Override
        // public void handleMessageEvent(
        // IIMMessageEvent messageEvent) {
        // if (messageEvent instanceof ChatMessageEvent) {
        // ChatMessageEvent event = (ChatMessageEvent) messageEvent;
        // System.out.println(event.getChatMessage()
        // .getBody());
        // }
        // }
        // });
        // } catch (ECFException e) {
        // statusHandler.handle(Priority.PROBLEM, e.getLocalizedMessage(), e);
        // }
    }

    public static void changeStatus(IPresence.Mode statusMode) {
        System.out.println("Changing mode...");
        // IPresenceContainerAdapter presence = CollaborationData.getInstance()
        // .getPresence();
        //
        // IPresence pres = new Presence(IPresence.Type.AVAILABLE, "AVAILABLE",
        // IPresence.Mode.fromString(type.toString()));
        // try {
        // presence.getRosterManager()
        // .getPresenceSender()
        // .sendPresenceUpdate(
        // CollaborationData.getInstance().getClient().getID(),
        // pres);
        // } catch (ECFException e) {
        // statusHandler.handle(Priority.PROBLEM, e.getLocalizedMessage(), e);
        // }
        // presence.getAccountManager();
    }
}
