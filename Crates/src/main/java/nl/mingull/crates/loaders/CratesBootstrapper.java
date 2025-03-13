package nl.mingull.crates.loaders;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;

public class CratesBootstrapper implements PluginBootstrap {
	@Override
	public void bootstrap(@NotNull BootstrapContext bootstrapContext) {

	}

	@Override
	public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
		return PluginBootstrap.super.createPlugin(context);
	}
}
