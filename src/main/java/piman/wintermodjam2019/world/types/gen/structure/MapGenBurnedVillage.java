package piman.wintermodjam2019.world.types.gen.structure;

import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeOcean;
import piman.wintermodjam2019.Main;

public class MapGenBurnedVillage extends MapGenAbandonedVillage {

	public MapGenBurnedVillage() {}
	
	public MapGenBurnedVillage(Map<String, String> map) {
        this();

        for (Entry<String, String> entry : map.entrySet())
        {
            if (((String)entry.getKey()).equals("size"))
            {
                this.size = MathHelper.getInt(entry.getValue(), this.size, 0);
            }
            
            if (entry.getKey().equals("charred")) {
            	if (entry.getValue().equals("false")) {
            		this.charred = false;
            	}
            	else if (entry.getValue().equals("true")) {
            		this.charred = true;
            	}
            }
        }
	}
	
	@Override
	public String getStructureName() {
		return Main.MODID + ":BurnedVillage";
	}
	
	@Override
	protected ChunkPos getSpawnLocation() {
    	Random rand = new Random(world.getSeed() + 1);
    	
    	BlockPos pos;
    	
    	int x, z;
    	
    	do {
        	x = rand.nextInt(101) - 50;
        	z = rand.nextInt(101) - 50;
        	
        	pos = world.findNearestStructure(Main.MODID + ":AbandonedVillage", BlockPos.ORIGIN, false);
        	
        	pos = pos.add(pos).add(pos).add(pos);
        	
        	pos = pos.add((x << 4) + 8, 0, (z << 4) + 8);
    	} while (world.getBiome(pos) instanceof BiomeOcean || world.getBiome(pos) instanceof BiomeBeach);
    	    	
    	return new ChunkPos(pos);
	}
	
}
