package piman.wintermodjam2019;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import piman.wintermodjam2019.world.types.WorldTypeWinter;
import piman.wintermodjam2019.world.types.gen.structure.MapGenAbandonedVillage;
import piman.wintermodjam2019.world.types.gen.structure.MapGenElderHut;
import piman.wintermodjam2019.world.types.gen.structure.MapGenRuins1;
import piman.wintermodjam2019.world.types.gen.structure.MapGenWinterVillage;
import piman.wintermodjam2019.world.types.gen.structure.RuinsPieces;
import piman.wintermodjam2019.world.types.gen.structure.StructureAbandonedVillagePieces;
import piman.wintermodjam2019.world.types.gen.structure.StructureWinterVillagePieces;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {
	
	public static final String MODID = "piwintermodjam2019";
	public static final String NAME = "Winter ModJam 2019";
	public static final String VERSION = "0.1";
	
	private static WorldTypeWinter worldTypeWinter;
	
	@Instance
	public static Main instance;
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		MapGenStructureIO.registerStructure(MapGenWinterVillage.Start.class, MODID + ":WinterVillage");
		MapGenStructureIO.registerStructure(MapGenRuins1.Start.class, MODID + ":Ruins1");
		MapGenStructureIO.registerStructure(MapGenElderHut.Start.class, MODID + ":ElderHut");
		MapGenStructureIO.registerStructure(MapGenAbandonedVillage.Start.class, MODID + ":AbandonedVillage");
		RuinsPieces.registerPieces();
		StructureWinterVillagePieces.registerVillagePieces();
		StructureAbandonedVillagePieces.registerVillagePieces();
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		worldTypeWinter = new WorldTypeWinter();
	}
	
}
