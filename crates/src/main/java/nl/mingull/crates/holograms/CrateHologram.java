package nl.mingull.crates.holograms;

import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import io.github.retrooper.packetevents.util.SpigotReflectionUtil;
import nl.mingull.crates.models.Crate;

public class CrateHologram {
	private final Location location;
	private final Crate crate;
	private int entityId;

	public CrateHologram(Location location, Crate crate) {
		this.location = location.clone().add(.5, .5, .5);
		this.crate = crate;
		this.entityId = SpigotReflectionUtil.generateEntityId();
	}

	public void show(Player player) {
		User user = PacketEvents.getAPI().getPlayerManager().getUser(player);
		if (user == null)
			return;

		WrapperPlayServerSpawnEntity spawnPacket = new WrapperPlayServerSpawnEntity(this.entityId,
				UUID.randomUUID(), EntityTypes.ARMOR_STAND,
				SpigotConversionUtil.fromBukkitLocation(location), 0, 0, null);

		user.sendPacket(spawnPacket);
	}

	public void hide(Player player) {
		User user = PacketEvents.getAPI().getPlayerManager().getUser(player);
		if (user == null)
			return;

		WrapperPlayServerDestroyEntities destroyPacket =
				new WrapperPlayServerDestroyEntities(this.entityId);

		user.sendPacket(destroyPacket);
	}
}
