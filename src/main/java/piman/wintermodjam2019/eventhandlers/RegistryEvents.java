package piman.wintermodjam2019.eventhandlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import piman.wintermodjam2019.init.ModItems;
import piman.wintermodjam2019.items.ICustomModelLocation;
import piman.wintermodjam2019.items.ItemJournal;

@EventBusSubscriber
public class RegistryEvents {
	
	public static List<Item> items = new ArrayList<>();
	
	@SubscribeEvent
	public static void ItemRegister(RegistryEvent.Register<Item> event) {
				
		items.add(new ItemJournal());
		
		event.getRegistry().registerAll(items.toArray(new Item[0]));
		
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void ModelRegister(ModelRegistryEvent event) {

		for (Item item : items) {
			ModelResourceLocation location;
			if (item instanceof ICustomModelLocation) {
				location = ((ICustomModelLocation) item).getLocation();
			}
			else {
				location = new ModelResourceLocation(item.getRegistryName(), "inventory");
			}
			
			
			ModelLoader.setCustomModelResourceLocation(item, 0, location);
		}
		
	}
	
}
