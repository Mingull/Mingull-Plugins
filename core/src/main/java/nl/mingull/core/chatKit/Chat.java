package nl.mingull.core.chatKit;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import nl.mingull.core.utils.Messenger;

public class Chat implements Listener {
    private final Plugin plugin;
    private static final Map<UUID, ChatRequest> chatRequests = new ConcurrentHashMap<>();

    public Chat(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private record ChatRequest(Predicate<String> validator, Consumer<String> onInput,
            Runnable onCancel) {}

    /**
     * Request input from a player with Adventure Components.
     *
     * @param player The player to request input from.
     * @param prompt The message to show the player.
     * @param validator Custom validation function.
     * @param onInput Callback function to handle validated input.
     * @param onCancel Optional callback for canceled input.
     */
    public static void requestInput(Player player, Component prompt, Predicate<String> validator,
            Consumer<String> onInput, Runnable onCancel) {
        UUID playerId = player.getUniqueId();
        chatRequests.put(playerId, new ChatRequest(validator, onInput, onCancel));

        // Send Adventure-formatted prompt
        player.sendMessage(prompt);
        player.sendMessage(Component.text("Type 'cancel' to cancel."));
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (!chatRequests.containsKey(playerId))
            return;

        event.setCancelled(true);
        ChatRequest request = chatRequests.remove(playerId);

        // Convert Component message to plain text
        String input = Messenger.toString(event.message());

        // Handle cancellation
        if (input.equalsIgnoreCase("cancel")) {
            player.sendMessage(Messenger.format("<red>Input cancelled."));
            if (request.onCancel != null) {
                Bukkit.getScheduler().runTask(plugin, request.onCancel);
            }
            return;
        }

        // Validate input
        if (request.validator.test(input)) {
            Bukkit.getScheduler().runTask(plugin, () -> request.onInput.accept(input));
        } else {
            player.sendMessage(Messenger.format("<red>Invalid input. Try again."));
            chatRequests.put(playerId, request); // Keep waiting for valid input
        }
    }
}
