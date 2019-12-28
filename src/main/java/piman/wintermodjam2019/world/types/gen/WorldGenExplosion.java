package piman.wintermodjam2019.world.types.gen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class WorldGenExplosion extends Explosion {

	private World world;
	private boolean causesFire;
	private float size;
	private double x;
	private double y;
	private double z;
	private boolean damagesTerrain;
	private Random random;
	private Map<BlockPos, Float> blastMap = new HashMap<>();

	public WorldGenExplosion(World worldIn, double x, double y, double z, float size, boolean flaming, boolean damagesTerrain) {
		super(worldIn, null, x, y, z, size, flaming, damagesTerrain);
        this.random = new Random();
        this.world = worldIn;
        this.size = size;
        this.x = x;
        this.y = y;
        this.z = z;
        this.causesFire = flaming;
        this.damagesTerrain = damagesTerrain;
	}
	
    public void doExplosionA(StructureBoundingBox structureBoundingBoxIn)
    {
        Set<BlockPos> set = Sets.<BlockPos>newHashSet();
        int i = 16;

        for (int j = 0; j < 16; ++j)
        {
            for (int k = 0; k < 16; ++k)
            {
                for (int l = 0; l < 16; ++l)
                {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15)
                    {
                        double d0 = (double)((float)j / 15.0F * 2.0F - 1.0F);
                        double d1 = (double)((float)k / 15.0F * 2.0F - 1.0F);
                        double d2 = (double)((float)l / 15.0F * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 = d0 / d3;
                        d1 = d1 / d3;
                        d2 = d2 / d3;
                        float f = this.size * (0.7F + this.world.rand.nextFloat() * 0.6F);
                        double d4 = this.x;
                        double d6 = this.y;
                        double d8 = this.z;

                        for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F)
                        {
                            BlockPos blockpos = new BlockPos(d4, d6, d8);
                            
                            boolean flag = true;
                            
                            if (structureBoundingBoxIn.isVecInside(blockpos) || blastMap.containsKey(blockpos)) {
                            	if (structureBoundingBoxIn.isVecInside(blockpos)) {
                            		IBlockState iblockstate = this.world.getBlockState(blockpos);
		                            if (iblockstate.getMaterial() != Material.AIR)
		                            {
		                                float f2 = iblockstate.getBlock().getExplosionResistance(world, blockpos, (Entity)null, this);
		                                blastMap.put(blockpos, f2);
		                                f -= (f2 + 0.3F) * 0.3F;
		                            }
                            	}
                            	else {
                            		if (blastMap.get(blockpos) > 0) {
		                                float f2 = blastMap.get(blockpos);
		                                f -= (f2 + 0.3F) * 0.3F;
                            		}
                            	}
	
	                            if (f > 0.0F && flag)
	                            {
	                                set.add(blockpos);
	                            }
	
	                            d4 += d0 * 0.30000001192092896D;
	                            d6 += d1 * 0.30000001192092896D;
	                            d8 += d2 * 0.30000001192092896D;
                            }
                            else {
                            	flag = false;
                            }
                        }
                    }
                }
            }
        }

        this.getAffectedBlockPositions().addAll(set);
    }

    public void doExplosionB(StructureBoundingBox structureBoundingBoxIn)
    {
    	synchronized(this.getAffectedBlockPositions()) {
	        if (this.damagesTerrain)
	        {
	            for (BlockPos blockpos : this.getAffectedBlockPositions())
	            {
	                IBlockState iblockstate = this.world.getBlockState(blockpos);
	
	                if (iblockstate.getMaterial() != Material.AIR)
	                {
	                	if (structureBoundingBoxIn.isVecInside(blockpos)) {
	                		world.setBlockToAir(blockpos);
	                	}
	                }
	            }
	        }
	
	        if (this.causesFire)
	        {
	            for (BlockPos blockpos1 : this.getAffectedBlockPositions())
	            {
	                if (this.world.getBlockState(blockpos1).getMaterial() == Material.AIR && this.world.getBlockState(blockpos1.down()).isFullBlock() && this.random.nextInt(3) == 0)
	                {
	                    this.world.setBlockState(blockpos1, Blocks.FIRE.getDefaultState());
	                }
	            }
	        }
    	}
    }
	
}
