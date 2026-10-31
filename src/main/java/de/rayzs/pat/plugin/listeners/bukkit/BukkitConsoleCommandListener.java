package de.rayzs.pat.plugin.listeners.bukkit;

import de.rayzs.pat.api.storage.Storage;
import de.rayzs.pat.api.storage.config.settings.ConsoleCommandWhitelistSection;
import de.rayzs.pat.utils.message.MessageHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class BukkitConsoleCommandListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onServerCommand(ServerCommandEvent event) {
        final ConsoleCommandWhitelistSection section = Storage.ConfigSections.Settings.CONSOLE_COMMAND_WHITELIST;

        if (!section.ENABLED) {
            return;
        }

        final CommandSender sender = event.getSender();
        final boolean console = sender instanceof ConsoleCommandSender,
                rcon = sender instanceof RemoteConsoleCommandSender;

        if (!console && !(rcon && section.INCLUDE_RCON)) {
            return;
        }

        final String command = event.getCommand();

        if (section.isAllowed(command)) {
            return;
        }

        event.setCancelled(true);
        MessageHelper.sendMessage(de.rayzs.pat.utils.sender.CommandSender.from(sender), section.MESSAGE, "%command%", command.trim());
    }
}
