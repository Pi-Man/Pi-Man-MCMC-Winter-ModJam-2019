package piman.wintermodjam2019.world.types.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.MapGenStructure;

public class MapGenStructureContainer {

	private List<MapGenStructure> generators = new ArrayList<>();
	private List<Boolean> blockSpawns = new ArrayList<>();
	
	public void addGenerator(MapGenStructure generator, boolean blockSpawn) {
		generators.add(generator);
		blockSpawns.add(blockSpawn);
	}
	
    public void generate(World worldIn, int x, int z, ChunkPrimer primer) {
		generators.forEach(generator -> generator.generate(worldIn, x, z, primer));
	}
    
    public synchronized boolean generateStructure(World worldIn, Random randomIn, ChunkPos chunkCoord) {
    	boolean flag = false;
    	for (int i = 0; i < generators.size(); i++) {
    		flag |= generators.get(i).generateStructure(worldIn, randomIn, chunkCoord) && blockSpawns.get(i);
    	}
    	return flag;
    }
    
    public boolean hasStructure(String structureName) {
    	for (MapGenStructure generator : generators) {
    		if (generator.getStructureName().equals(structureName)) {
    			return true;
    		}
    	}
    	return false;
	}
    
    public boolean isInsideStructure(String structureName, BlockPos pos) {
    	for (MapGenStructure generator : generators) {
    		if (generator.getStructureName().equals(structureName)) {
    			return generator.isInsideStructure(pos);
    		}
    	}
    	return false;
    }
    
	public BlockPos getNearestStructurePos(String structureName, World worldIn, BlockPos pos, boolean findUnexplored) {
		
    	for (MapGenStructure generator : generators) {
    		if (generator.getStructureName().equals(structureName)) {
    			return generator.getNearestStructurePos(worldIn, pos, findUnexplored);
    		}
    	}
    	
    	return null;
		
	}
	
}
