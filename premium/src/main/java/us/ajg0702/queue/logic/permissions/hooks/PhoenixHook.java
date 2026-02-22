package us.ajg0702.queue.logic.permissions.hooks;

import xyz.refinedev.phoenix.Phoenix;
import xyz.refinedev.phoenix.profile.IProfile;
import xyz.refinedev.phoenix.profile.permission.IProfilePermission;
import us.ajg0702.queue.api.players.AdaptedPlayer;
import us.ajg0702.queue.api.premium.PermissionHook;
import us.ajg0702.queue.common.QueueMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PhoenixHook implements PermissionHook {

    private final QueueMain main;
    public PhoenixHook(QueueMain main) {
        this.main = main;
    }

    @Override
    public String getName() {
        return "Phoenix";
    }

    @Override
    public boolean canUse() {
        if(!main.getPlatformMethods().hasPlugin("Phoenix")) return false;
        try {
            if(Phoenix.getInstance() == null) {
                main.getLogger().warn("Phoenix is installed, but its getInstance() returned null! Unable to hook into it.");
                return false;
            }
        } catch(NoClassDefFoundError e) {
            main.getLogger().warning("Phoenix seems to be installed, but its api doesnt seem to be!");
            return false;
        }
        return true;
    }

    @Override
    public List<String> getPermissions(AdaptedPlayer player) {
        return getPermissions(player.getUniqueId());
    }

    @Override
    public List<String> getPermissions(UUID uuid) {
        Phoenix phoenix = Phoenix.getInstance();

        IProfile profile = phoenix.getProfileHandler().getProfile(uuid);

        if(profile == null) {
            main.getLogger().warn("Phoenix doesnt seem to have data loaded for "+uuid+"! " +
                    "Because of this I can't load priority permissions. Acting like "+uuid+" doesnt have any.");
            return new ArrayList<>();
        }

        // Get all permissions from all active ranks/grants
        Set<IProfilePermission> profilePermissions = profile.getPermissions();
        List<String> permissions = new ArrayList<>();
        
        for(IProfilePermission perm : profilePermissions) {
            if(perm != null && perm.getPermission() != null && (perm.isPermanent() || !perm.hasExpired())) {
                permissions.add(perm.getPermission());
            }
        }
        
        return permissions;
    }
}