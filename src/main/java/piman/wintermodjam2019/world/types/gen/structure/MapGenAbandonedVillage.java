package piman.wintermodjam2019.world.types.gen.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import piman.wintermodjam2019.Main;

public class MapGenAbandonedVillage extends MapGenSingelSpawn
{
    /** A list of all the biomes villages can spawn in. */
    /** None */
    private int size;

    public MapGenAbandonedVillage()
    {
    }

    public MapGenAbandonedVillage(Map<String, String> map)
    {
        this();

        for (Entry<String, String> entry : map.entrySet())
        {
            if (((String)entry.getKey()).equals("size"))
            {
                this.size = MathHelper.getInt(entry.getValue(), this.size, 0);
            }
        }
    }

    public String getStructureName()
    {
        return Main.MODID + ":AbandonedVillage";
    }
    
    @Override
    protected ChunkPos getSpawnLocation() {
    	
    	Random rand = new Random(world.getSeed());
    	
    	int x = rand.nextInt(31) - 15;
    	int z = rand.nextInt(31) - 15;
    	
    	BlockPos pos = world.findNearestStructure("Ruins1", BlockPos.ORIGIN, false);
    	
    	pos = pos.add(pos);
    	
    	pos = pos.add((x << 4) + 8, 0, (z << 4) + 8);
    	
    	while (world.getBiome(pos) instanceof BiomeOcean || world.getBiome(pos) instanceof BiomeBeach) {
        	x = rand.nextInt(31) - 15;
        	z = rand.nextInt(31) - 15;
        	
        	pos = world.findNearestStructure("Ruins1", BlockPos.ORIGIN, false);
        	
        	pos = pos.add(pos);
        	
        	pos = pos.add((x << 4) + 8, 0, (z << 4) + 8);
    	}
    	
    	return new ChunkPos(pos);
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new MapGenAbandonedVillage.Start(this.world, this.rand, chunkX, chunkZ, this.size);
    }

    public static class Start extends StructureStart
        {
            /** well ... thats what it does */
            private boolean hasMoreThanTwoComponents;

            public Start()
            {
            }

            public Start(World worldIn, Random rand, int x, int z, int size)
            {
                super(x, z);
                List<StructureAbandonedVillagePieces.PieceWeight> list = StructureAbandonedVillagePieces.getStructureVillageWeightedPieceList(rand, size);
                StructureAbandonedVillagePieces.Start structurevillagepieces$start = new StructureAbandonedVillagePieces.Start(worldIn.getBiomeProvider(), 0, rand, (x << 4) + 2, (z << 4) + 2, list, size);
                this.components.add(structurevillagepieces$start);
                structurevillagepieces$start.buildComponent(structurevillagepieces$start, this.components, rand);
                List<StructureComponent> list1 = structurevillagepieces$start.pendingRoads;
                List<StructureComponent> list2 = structurevillagepieces$start.pendingHouses;

                while (!list1.isEmpty() || !list2.isEmpty())
                {
                    if (list1.isEmpty())
                    {
                        int i = rand.nextInt(list2.size());
                        StructureComponent structurecomponent = list2.remove(i);
                        structurecomponent.buildComponent(structurevillagepieces$start, this.components, rand);
                    }
                    else
                    {
                        int j = rand.nextInt(list1.size());
                        StructureComponent structurecomponent2 = list1.remove(j);
                        structurecomponent2.buildComponent(structurevillagepieces$start, this.components, rand);
                    }
                }

                this.updateBoundingBox();
                int k = 0;

                for (StructureComponent structurecomponent1 : this.components)
                {
                    if (!(structurecomponent1 instanceof StructureVillagePieces.Road))
                    {
                        ++k;
                    }
                }

                this.hasMoreThanTwoComponents = k > 2;
            }

            /**
             * currently only defined for Villages, returns true if Village has more than 2 non-road components
             */
            public boolean isSizeableStructure()
            {
                return this.hasMoreThanTwoComponents;
            }

            public void writeToNBT(NBTTagCompound tagCompound)
            {
                super.writeToNBT(tagCompound);
                tagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
            }

            public void readFromNBT(NBTTagCompound tagCompound)
            {
                super.readFromNBT(tagCompound);
                this.hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
            }
        }
}