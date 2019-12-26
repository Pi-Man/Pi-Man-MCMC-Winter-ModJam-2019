package piman.wintermodjam2019.world.types.gen.structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public abstract class MapGenSingelSpawn extends MapGenStructure {
	
	private ChunkPos cachedPos = null;

	@Override
	public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
		if (cachedPos == null) {
			cachedPos = getSpawnLocation();
		}
		
		return findUnexplored && !worldIn.isChunkGeneratedAt(cachedPos.x, cachedPos.z) ? null : new BlockPos((cachedPos.x << 4) + 8, 64, (cachedPos.z << 4) + 8);
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		if (cachedPos == null) {
			cachedPos = getSpawnLocation();
		}
		
		return chunkX == cachedPos.x && chunkZ == cachedPos.z;
	}
	
	protected abstract ChunkPos getSpawnLocation();

}
