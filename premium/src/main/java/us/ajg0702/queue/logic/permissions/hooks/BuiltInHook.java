package us.ajg0702.queue.logic.permissions.hooks;

import us.ajg0702.queue.api.players.AdaptedPlayer;
import us.ajg0702.queue.common.QueueMain;
import us.ajg0702.queue.api.premium.PermissionHook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

public class BuiltInHook implements PermissionHook {

    private final QueueMain main;
    public BuiltInHook(QueueMain main) {
        this.main = main;
    }

    @Override
    public String getName() {
        return "Built-In";
    }

    @Override
    public boolean canUse() {
        return true;
    }

    private final List<String> fallbackPriorityPermissions = new ArrayList<>();
    private final List<String> fallbackStayQueuedPermissions = new ArrayList<>();
    private String currentFallbackStayQueuedRaw = "";

    @Override
    public List<String> getPermissions(AdaptedPlayer player) {
        if(main.getConfig().getBoolean("plus-level-fallback")) {
            int fallbackPriorityLevels = main.getConfig().getInt("fallback-priority-levels");
            if(fallbackPriorityPermissions.size() != fallbackPriorityLevels) {
                fallbackPriorityPermissions.clear();
                for (int i = 1; i <= fallbackPriorityLevels; i++) {
                    fallbackPriorityPermissions.add("ajqueue.priority." + i);
                }
            }

            String fallbackStayQueuedRaw = main.getConfig().getString("fallback-stayqueued-levels");
            if(!currentFallbackStayQueuedRaw.equals(fallbackStayQueuedRaw)) {
                currentFallbackStayQueuedRaw = fallbackStayQueuedRaw;
                fallbackStayQueuedPermissions.clear();
                List<String> levels = Arrays.stream(fallbackStayQueuedRaw.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                for(String level : levels) {
                    fallbackStayQueuedPermissions.add("ajqueue.stayqueued." + level);
                }
            }

            List<String> hasPermissions = new ArrayList<>();

            ArrayList<String> fallbackPermissions = new ArrayList<>(fallbackPriorityPermissions.size() + fallbackStayQueuedPermissions.size());
            fallbackPermissions.addAll(fallbackPriorityPermissions);
            fallbackPermissions.addAll(fallbackStayQueuedPermissions);

            for (String fallbackPermission : fallbackPermissions) {
                if(player.hasPermission(fallbackPermission)) {
                    hasPermissions.add(fallbackPermission);
                }
            }
            if(!main.getPlatformMethods().getImplementationName().equals("velocity")) {
                hasPermissions.addAll(player.getPermissions());
            }
            return hasPermissions;
        }


        if(main.getPlatformMethods().getImplementationName().equals("velocity")) {
            return Collections.emptyList();
        }

        return player.getPermissions();
    }

    @Override
    public List<String> getPermissions(UUID uuid) {
        return Collections.emptyList();
    }
}