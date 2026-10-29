package de.rayzs.pat.api.storage.config.settings;

import de.rayzs.pat.api.storage.storages.ConfigStorage;
import de.rayzs.pat.utils.configuration.helper.ConfigSectionHelper;

public class BypassHandlingSection extends ConfigStorage {

    public boolean OPERATORS, WILDCARDS;

    public BypassHandlingSection() {
        super("bypass-handling");
    }

    @Override
    public void load() {
        super.load();

        OPERATORS = new ConfigSectionHelper<Boolean>(this, "operators", true).getOrSet();
        WILDCARDS = new ConfigSectionHelper<Boolean>(this, "wildcards", true).getOrSet();
    }
}
