package piman.wintermodjam2019.world.types;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.layer.GenLayer;
import piman.wintermodjam2019.world.types.gen.ChunkGeneratorWinter;
import piman.wintermodjam2019.world.types.gen.layer.GenLayerWinterBiomes;

public class WorldTypeWinter extends WorldType {
	
	public WorldTypeWinter() {
		super("winter");
	}
	
	@Override
	public boolean canBeCreated() {
		return true;
	}
	
	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new ChunkGeneratorWinter(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
	}
	
	@Override
	public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer, ChunkGeneratorSettings chunkSettings) {
		return new GenLayerWinterBiomes(worldSeed, parentLayer, this, chunkSettings);
	}
	
}
