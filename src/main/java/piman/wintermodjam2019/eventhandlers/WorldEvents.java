package piman.wintermodjam2019.eventhandlers;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.WorldEvent.CreateSpawnPosition;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class WorldEvents {

	@SubscribeEvent
	public static void onSpawnCreate(CreateSpawnPosition event) {
		event.getWorld().setSpawnPoint(event.getWorld().getTopSolidOrLiquidBlock(new BlockPos(8, 0, 8)));
		event.setCanceled(true);
	}
	
}
