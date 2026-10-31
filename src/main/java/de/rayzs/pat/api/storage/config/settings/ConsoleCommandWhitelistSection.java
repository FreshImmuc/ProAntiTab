package de.rayzs.pat.api.storage.config.settings;

import de.rayzs.pat.utils.Reflection;
import de.rayzs.pat.api.storage.storages.ConfigStorage;
import de.rayzs.pat.utils.configuration.helper.ConfigSectionHelper;
import de.rayzs.pat.utils.configuration.helper.MultipleMessagesHelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConsoleCommandWhitelistSection extends ConfigStorage {

    public boolean ENABLED, INCLUDE_RCON;
    public String MESSAGE;
    public MultipleMessagesHelper COMMANDS;

    private Set<String> allowed;

    public ConsoleCommandWhitelistSection() {
        super("console-command-whitelist");
    }

    @Override
    public void load() {
        super.load();

        if (Reflection.isProxyServer()) {
            return;
        }

        ENABLED = new ConfigSectionHelper<Boolean>(this, "enabled", false).getOrSet();
        INCLUDE_RCON = new ConfigSectionHelper<Boolean>(this, "include-rcon", true).getOrSet();
        MESSAGE = new ConfigSectionHelper<String>(this, "message", "&cThe console is not allowed to execute this command! (%command%)").getOrSet();
        COMMANDS = new MultipleMessagesHelper(this, "commands", Arrays.asList(
                "stop",
                "restart",
                "save-all",
                "list",
                "tps",
                "mspt",
                "pat",
                "proantitab"
        ));

        Set<String> commands = new HashSet<>();
        COMMANDS.getLines().forEach(line -> commands.add(line.trim().toLowerCase()));
        allowed = commands;
    }

    public boolean isAllowed(String command) {
        command = command.trim();

        while (command.startsWith("/")) {
            command = command.substring(1);
        }

        int space = command.indexOf(' ');
        if (space != -1) {
            command = command.substring(0, space);
        }

        command = command.toLowerCase();

        if (allowed.contains(command)) {
            return true;
        }

        int colon = command.indexOf(':');
        return colon != -1 && allowed.contains(command.substring(colon + 1));
    }
}
