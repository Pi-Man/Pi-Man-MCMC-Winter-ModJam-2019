package piman.wintermodjam2019.world.types.gen.structure;

import java.util.Random;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureComponent.BlockSelector;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class RuinsPieces {
	
	private static final IBlockState COBBLESTONE = Blocks.COBBLESTONE.getDefaultState();
	private static final IBlockState MOSSY_COBBLESTONE = Blocks.MOSSY_COBBLESTONE.getDefaultState();
	private static final IBlockState COBBLE_STAIRS = Blocks.STONE_STAIRS.getDefaultState();
	private static final IBlockState COBBLE_SLAB = Blocks.STONE_SLAB.getStateFromMeta(3);
	
	private static final BlockSelector COBBLEMOSS5050 = new BlockSelector() {
		
		private final IBlockState[] states = new IBlockState[] {COBBLESTONE, MOSSY_COBBLESTONE};

		@Override
		public void selectBlocks(Random rand, int x, int y, int z, boolean wall) {
			this.blockstate = states[rand.nextInt(2)];
		}
		
	};
	
	public static void registerPieces() {
		MapGenStructureIO.registerStructureComponent(ElderHut.class, "EH");
		MapGenStructureIO.registerStructureComponent(Ruins1.class, "R1");
	}
	
	public static class ElderHut extends StructureComponent {
		
		public ElderHut() {}

		@SuppressWarnings("incomplete-switch")
		public ElderHut(World worldIn, Random rand, int chunkX, int chunkZ) {
			setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));
			
			int x = (chunkX << 4) + 8;
			int z = (chunkZ << 4) + 8;
			
			int y = 0;
			
			int sizeX = 8;
			int sizeY = 8;
			int sizeZ = 7;
			
            this.setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));
            this.setCoordBaseMode(EnumFacing.WEST);
            
            switch(this.getCoordBaseMode()) {
            case NORTH:
            	this.boundingBox = new StructureBoundingBox(x, y, z - 1, x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1);
            	break;
            case EAST:
            	this.boundingBox = new StructureBoundingBox(x - 1, y, z, x + sizeZ - 1, y + sizeY - 1, z + sizeX - 1);
            	break;
            case SOUTH:
            	this.boundingBox = new StructureBoundingBox(x, y, z + 1, x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1);
            	break;
            case WEST:
            	this.boundingBox = new StructureBoundingBox(x + 1, y, z, x + sizeZ - 1, y + sizeY - 1, z + sizeX - 1);
            	break;
            }
		}
		
		@Override
		protected void writeStructureToNBT(NBTTagCompound tagCompound) {
			
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
			
		}

		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {

			BlockPos floor = worldIn.getTopSolidOrLiquidBlock(new BlockPos(getXWithOffset(1, -1), 0, getZWithOffset(1, -1)));
			
			this.offset(0, floor.getY() - this.boundingBox.minY - 1, 0);
			
            IBlockState iblockstate = Blocks.COBBLESTONE.getDefaultState();
            IBlockState iblockstate1 = Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH);
            IBlockState iblockstate2 = Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH);
            IBlockState iblockstate3 = Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST);
            IBlockState iblockstate4 = Blocks.PLANKS.getDefaultState();
            IBlockState iblockstate5 = Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH);
            IBlockState iblockstate6 = Blocks.OAK_FENCE.getDefaultState();
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 0, 5, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 8, 5, 5, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 1, 8, 6, 4, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 7, 2, 8, 7, 3, iblockstate, iblockstate, false);

            for (int i = -1; i <= 2; ++i)
            {
                for (int j = 0; j <= 8; ++j)
                {
                    this.setBlockState(worldIn, iblockstate1, j, 6 + i, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate2, j, 6 + i, 5 - i, structureBoundingBoxIn);
                }
            }

            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 5, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 8, 1, 5, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 0, 8, 1, 4, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 7, 1, 0, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 4, 0, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 4, 5, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 5, 8, 4, 5, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 0, 8, 4, 0, iblockstate, iblockstate, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 4, 4, iblockstate4, iblockstate4, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 4, 5, iblockstate4, iblockstate4, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 1, 8, 4, 4, iblockstate4, iblockstate4, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 4, 0, iblockstate4, iblockstate4, false);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 3, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 3, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 3, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 1, iblockstate4, iblockstate4, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 4, 7, 4, 4, iblockstate4, iblockstate4, false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 7, 3, 4, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
            this.setBlockState(worldIn, iblockstate4, 7, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate3, 7, 1, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 6, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 5, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 4, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1, 3, 1, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate6, 6, 1, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate6, 4, 1, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 4, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.CRAFTING_TABLE.getDefaultState(), 7, 1, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.EAST), 1, 4, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.WEST), 7, 4, 3, structureBoundingBoxIn);
            this.generateDoor(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.NORTH, Blocks.OAK_DOOR);

            if (this.getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
            {
                this.setBlockState(worldIn, iblockstate5, 1, 0, -1, structureBoundingBoxIn);

                if (this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
                {
                    this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 1, -1, -1, structureBoundingBoxIn);
                }
            }

            for (int l = 0; l < 6; ++l)
            {
                for (int k = 0; k < 9; ++k)
                {
                    this.clearCurrentPositionBlocksUpwards(worldIn, k, 9, l, structureBoundingBoxIn);
                    this.replaceAirAndLiquidDownwards(worldIn, iblockstate, k, -1, l, structureBoundingBoxIn);
                }
            }
            
            int j = this.getXWithOffset(2, 2);
            int k = this.getYWithOffset(1);
            int l = this.getZWithOffset(2, 2);

            EntityVillager entityvillager = new EntityVillager(worldIn);
            entityvillager.setLocationAndAngles((double)j + 0.5D, (double)k, (double)l + 0.5D, 0.0F, 0.0F);
            entityvillager.setProfession(1);
            entityvillager.finalizeMobSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityvillager)), (IEntityLivingData)null, false);
            worldIn.spawnEntity(entityvillager);
            
            return true;
        }
		
	}

	public static class Ruins1 extends StructureComponent {
		
		public Ruins1() {}
		
		public Ruins1(Random rand, int chunkX, int chunkZ) {
			
			setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));
			
			int x = (chunkX << 4) + 8;
			int z = (chunkZ << 4) + 8;
									
			int y = 64;
			
			int sizeX = 5;
			int sizeY = 4;
			int sizeZ = 5;
			
            this.setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));

            if (this.getCoordBaseMode().getAxis() == EnumFacing.Axis.Z)
            {
                this.boundingBox = new StructureBoundingBox(x, y, z, x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1);
            }
            else
            {
                this.boundingBox = new StructureBoundingBox(x, y, z, x + sizeZ - 1, y + sizeY - 1, z + sizeX - 1);
            }
			
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound tagCompound) {
			
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
			
		}

		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
						
			this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, false, randomIn, COBBLEMOSS5050);
			
			this.setBlockState(worldIn, COBBLE_STAIRS.withProperty(BlockStairs.FACING, EnumFacing.NORTH), 1, 1, 1, structureBoundingBoxIn);
			this.setBlockState(worldIn, MOSSY_COBBLESTONE, 2, 1, 1, structureBoundingBoxIn);
			this.setBlockState(worldIn, MOSSY_COBBLESTONE, 3, 1, 1, structureBoundingBoxIn);
			this.setBlockState(worldIn, COBBLE_STAIRS.withProperty(BlockStairs.FACING, EnumFacing.EAST), 1, 1, 2, structureBoundingBoxIn);
			this.setBlockState(worldIn, MOSSY_COBBLESTONE, 2, 1, 2, structureBoundingBoxIn);
			this.setBlockState(worldIn, MOSSY_COBBLESTONE, 3, 1, 2, structureBoundingBoxIn);
			this.setBlockState(worldIn, COBBLE_SLAB, 1, 1, 3, structureBoundingBoxIn);
			this.setBlockState(worldIn, MOSSY_COBBLESTONE, 2, 1, 3, structureBoundingBoxIn);
			this.setBlockState(worldIn, COBBLE_SLAB, 3, 1, 3, structureBoundingBoxIn);
			
			this.setBlockState(worldIn, MOSSY_COBBLESTONE, 2, 2, 1, structureBoundingBoxIn);
			this.setBlockState(worldIn, MOSSY_COBBLESTONE, 2, 2, 2, structureBoundingBoxIn);
			this.setBlockState(worldIn, COBBLE_SLAB, 3, 2, 2, structureBoundingBoxIn);
			
			this.setBlockState(worldIn, MOSSY_COBBLESTONE, 2, 3, 2, structureBoundingBoxIn);
			
			return true;
		}
		
	}

}
