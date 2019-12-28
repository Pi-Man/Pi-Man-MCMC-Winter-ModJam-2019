package piman.wintermodjam2019.world.types.gen.structure;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomeRiver;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenElderHut extends MapGenSingelSpawn {

	@Override
	public String getStructureName() {
		return "ElderHut";
	}
	
	@Override
	protected ChunkPos getSpawnLocation() {
		
		Random rand = new Random(world.getSeed());
		
		int x = rand.nextInt(31) - 15;
		int z = rand.nextInt(31) - 15;
		
		while (Math.abs(x) < 10 && Math.abs(z) < 10 || world.getBiome(new BlockPos((x << 4) + 8, 0, (z << 4) + 8)) instanceof BiomeOcean || world.getBiome(new BlockPos((x << 4) + 8, 64, (z << 4) + 8)) instanceof BiomeBeach || world.getBiome(new BlockPos((x << 4) + 8, 64, (z << 4) + 8)) instanceof BiomeRiver) {
			x = rand.nextInt(31) - 15;
			z = rand.nextInt(31) - 15;
		}
		
		return new ChunkPos(x, z);
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new Start(this.world, this.rand, chunkX, chunkZ);
	}
	
	public static class Start extends StructureStart {
		
		public Start() {}
		
		public Start(World world, Random rand, int chunkX, int chunkZ) {
			super(chunkX, chunkZ);
			this.components.add(new RuinsPieces.ElderHut(world, rand, chunkX, chunkZ));
			this.updateBoundingBox();
		}
		
		@Override
		public StructureBoundingBox getBoundingBox() {
			this.updateBoundingBox();
			return this.boundingBox;
		}
		
	}

}
