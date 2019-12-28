package piman.wintermodjam2019.eventhandlers;

import java.util.ArrayList;
import java.util.List;

import ibxm.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import piman.wintermodjam2019.Main;
import piman.wintermodjam2019.init.ModItems;

@EventBusSubscriber
public class EntityEventHandler {

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		
		if (!player.world.isRemote) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			
			WorldServer world = (WorldServer)player.world;
			
			updateAdvancements(playerMP, world);	
			
			updateMaps(playerMP, world);
		}
	}
	
	@SubscribeEvent
	public static void onPlayerLogOn(PlayerLoggedInEvent event) {
		if (!event.player.getEntityData().getBoolean("HasJournal")) {
			event.player.addItemStackToInventory(new ItemStack(ModItems.JOURNAL));
			event.player.getEntityData().setBoolean("HasJournal", true);
		}
	}
	
	private static void updateMaps(EntityPlayer player, World world) {
		List<Integer> maps = findMaps(player);
		
		for (Integer i : maps) {
			ItemStack map = player.inventory.getStackInSlot(i);
			
			NBTTagCompound nbt = map.getTagCompound();
			
			if (nbt == null) {
				nbt = new NBTTagCompound();
				map.setTagCompound(nbt);
			}
			
			NBTTagList list;
			if (nbt.hasKey("Decorations")) {
				list = nbt.getTagList("Decorations", 10);
			}
			else {
				list = new NBTTagList();
				nbt.setTag("Decorations", list);
			}
			
			if (player.getEntityData().hasKey("Part1")) addDecoration(world, list, "ElderHut", (byte) MapDecoration.Type.MANSION.ordinal());
			if (player.getEntityData().hasKey("Part2")) addDecoration(world, list, "Ruins1", (byte) MapDecoration.Type.TARGET_X.ordinal());
			if (player.getEntityData().hasKey("Part3")) addDecoration(world, list, Main.MODID + ":AbandonedVillage", (byte) MapDecoration.Type.TARGET_X.ordinal());
			if (player.getEntityData().hasKey("Part4")) addDecoration(world, list, Main.MODID + ":BurnedVillage", (byte) MapDecoration.Type.TARGET_X.ordinal());

		}
	}
	
	private static void addDecoration(World world, NBTTagList list, String name, byte type) {
		boolean flag = true;
		
        for (int j = 0; j < list.tagCount(); ++j)
        {
            NBTTagCompound nbttagcompound = list.getCompoundTagAt(j);

            if (nbttagcompound.getString("id").equals(name))
            {
                flag = false;
            }
        }
        
        if (flag) {
        	NBTTagCompound decoration = new NBTTagCompound();
        	BlockPos pos = world.findNearestStructure(name, BlockPos.ORIGIN, false);
        	if (pos != null) {
	        	decoration.setString("id", name);
	        	decoration.setByte("type", type);
	        	decoration.setDouble("x", pos.getX());
	        	decoration.setDouble("z", pos.getZ());
	        	decoration.setDouble("rot", 180);
	        	list.appendTag(decoration);
        	}
        }
	}
	
	private static List<Integer> findMaps(EntityPlayer player) {
		
		List<Integer> list = new ArrayList<>();
		
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if (stack.getItem() == Items.FILLED_MAP) {
				list.add(i);
			}
		}
		
		return list;
		
	}
	
	private static void updateAdvancements(EntityPlayerMP player, WorldServer world) {
		NBTTagCompound nbt = player.getEntityData();
		
		if (!nbt.hasKey("Part1") && checkAdvancement(player, world, "winter/root")) {
			nbt.setInteger("Part1", (int) (world.getWorldTime() / 24000 + 1));
		}
		
		if (!nbt.hasKey("Part2") && checkAdvancement(player, world, "winter/elder_hut")) {
			nbt.setInteger("Part2", (int) (world.getWorldTime() / 24000 + 1));
		}
		
		if (!nbt.hasKey("Part3") && checkAdvancement(player, world, "winter/ruins1")) {
			nbt.setInteger("Part3", (int) (world.getWorldTime() / 24000 + 1));
		}
		
		if (!nbt.hasKey("Part4") && checkAdvancement(player, world, "winter/abandoned_village")) {
			nbt.setInteger("Part4", (int) (world.getWorldTime() / 24000 + 1));
		}
		
		if (!nbt.hasKey("Part5") && checkAdvancement(player, world, "winter/burned_village")) {
			nbt.setInteger("Part5", (int) (world.getWorldTime() / 24000 + 1));
		}
	}
	
	private static boolean checkAdvancement(EntityPlayerMP player, WorldServer world, String name) {
		try {
			return player.getAdvancements().getProgress(world.getAdvancementManager().getAdvancement(new ResourceLocation(Main.MODID, name))).isDone();
		}
		catch (NullPointerException e) {
			return false;
		}
	}
}
