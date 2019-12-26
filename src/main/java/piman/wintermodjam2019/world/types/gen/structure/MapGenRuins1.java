package piman.wintermodjam2019.world.types.gen.structure;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenRuins1 extends MapGenSingelSpawn {

	@Override
	public String getStructureName() {
		return "Ruins1";
	}

	@Override
	protected ChunkPos getSpawnLocation() {
		Random rand = new Random(world.getSeed());
		
		int x = rand.nextInt(45) - 22;
		int z = rand.nextInt(45) - 22;
		
		while (Math.abs(x) < 10 && Math.abs(z) < 10 || world.getBiome(new BlockPos((x << 4) + 8, 64, (z << 4) + 8)) instanceof BiomeOcean || world.getBiome(new BlockPos((x << 4) + 8, 64, (z << 4) + 8)) instanceof BiomeBeach) {
			x = rand.nextInt(45) - 22;
			z = rand.nextInt(45) - 22;
		}
		
		return new ChunkPos(x, z);
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new Start(rand, chunkX, chunkZ);
	}
	
	public class Start extends StructureStart {
		
		public Start(Random rand, int chunkX, int chunkZ) {
			super(chunkX, chunkZ);
			this.components.add(new RuinsPieces.Ruins1(rand, chunkX, chunkZ));
			this.updateBoundingBox();
		}
		
		@Override
		public void generateStructure(World worldIn, java.util.Random rand, StructureBoundingBox structurebb) {
			super.generateStructure(worldIn, rand, structurebb);
		}
	}

}
