package us.ajg0702.queue.api.premium;

import us.ajg0702.queue.api.players.AdaptedPlayer;

import java.util.UUID;

public interface PermissionGetter {
    PermissionHook getSelected();

    int getMaxOfflineTime(AdaptedPlayer player);

    int getPriority(AdaptedPlayer player);

    int getServerPriotity(String server, AdaptedPlayer player);

    boolean hasContextBypass(AdaptedPlayer player, String server);

    boolean hasUniqueFullBypass(AdaptedPlayer player, String server);

    int getMaxOfflineTime(UUID uuid);

    int getPriority(UUID uuid);

    int getServerPriority(String server, UUID uuid);
}
