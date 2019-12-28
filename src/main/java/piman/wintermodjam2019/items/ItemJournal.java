package piman.wintermodjam2019.items;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import piman.wintermodjam2019.Main;
import piman.wintermodjam2019.client.gui.GuiScreenJournal;

public class ItemJournal extends ItemWrittenBook implements ICustomModelLocation {
	
	public ItemJournal() {
		super();
		this.setRegistryName(new ResourceLocation(Main.MODID, "journal")).setUnlocalizedName("journal").setCreativeTab(CreativeTabs.MISC);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (worldIn.isRemote) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiScreenJournal(playerIn, playerIn.getHeldItem(handIn), false));
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			if (!worldIn.isRemote) {
				NBTTagCompound playernbt = player.getEntityData();
				if (playernbt.hasKey("Part1")) {
					NBTTagCompound nbt = stack.getTagCompound();
					if (nbt == null) {
						nbt = new NBTTagCompound();
						stack.setTagCompound(nbt);
					}
					
					NBTTagList pages;
					if (nbt.hasKey("pages")) {
						pages = nbt.getTagList("pages", 8);
					}
					else {
						pages = new NBTTagList();
					}
					
					if (pages.tagCount() < 1) {
						Integer i = playernbt.getInteger("Part1");
						BlockPos pos = worldIn.findNearestStructure("ElderHut", BlockPos.ORIGIN, false);
						NBTTagString day1 = new NBTTagString(
								  "Day: " + i + "\n"
								+ "    This is it, today is the day I finally decide to find our ancient lost city.  I should be able to get some information from the City Elder at {" + pos.getX() + ", " + pos.getZ() + "} to help with my journey.  But first I am going to need to make a map to find him.");
						pages.appendTag(day1);
						if (!nbt.hasKey("author")) {
							nbt.setString("author", player.getName());
						}
						if (!nbt.hasKey("title")) {
							nbt.setString("title", "Journal");
						}
						nbt.setByte("resolved", (byte) 0);
					}
					if (playernbt.hasKey("Part2")) {
						if (pages.tagCount() < 2) {
							Integer i = playernbt.getInteger("Part1");
							BlockPos pos = worldIn.findNearestStructure("Ruins1", BlockPos.ORIGIN, false);
							NBTTagString day2 = new NBTTagString(
									  "Day: " + i + "\n"
									+ "    I managed to find my way to the Elder's Hut.  Now with his notes, I should be able to find our lost city if the clues still exist in the world.  The first place to check should be {" + pos.getX() + ", " + pos.getZ() + "}, though it may be burried.");
							pages.appendTag(day2);
							nbt.setByte("resolved", (byte) 0);
						}
						if (playernbt.hasKey("Part3")) {
							if (pages.tagCount() < 3) {
								Integer i = playernbt.getInteger("Part3");
								BlockPos pos = worldIn.findNearestStructure(Main.MODID + ":AbandonedVillage", BlockPos.ORIGIN, false);
								NBTTagString day3 = new NBTTagString(
										  "Day: " + i + "\n"
										+ "    Well I managed to find my first Ruins.  Judging by the stones and the Elder's notes, I think the next place I need to look is {" + pos.getX() + ", " + pos.getZ() + "}.");
								pages.appendTag(day3);
								nbt.setByte("resolved", (byte) 0);
							}
							if (playernbt.hasKey("Part4")) {
								if (pages.tagCount() < 4) {
									Integer i = playernbt.getInteger("Part4");
									BlockPos pos = worldIn.findNearestStructure(Main.MODID + ":BurnedVillage", BlockPos.ORIGIN, false);
									NBTTagString day4_1 = new NBTTagString(
											  "Day: " + i +"\n"
											+ "    I looks like the stones from the Ruins came from this Abandoned Village.  Unfortunately this Village is too small to be our Lost City.");
									pages.appendTag(day4_1);
									NBTTagString day4_2 = new NBTTagString(
											"    It seems that this VIllage was abandoned due to lack of resources, but by examining what is left and intact, I can conclude that the original setlers of this Village came from these coordinates:\n\n      {" + pos.getX() + ", " + pos.getZ() + "}");
									pages.appendTag(day4_2);
									nbt.setByte("resolved", (byte) 0);
								}
								if (playernbt.hasKey("Part5")) {
									if (pages.tagCount() < 6) {
										Integer i = playernbt.getInteger("Part5");
										NBTTagString day5_1 = new NBTTagString(
												  "Day: " + i + "\n"
												+ "    Oh $@#%, what the hell is goin on!  There's Ghasts everywhere, where did they come from?!\n"
												+ "Well this certainly seems to be our Lost City.  As to why there are so many Ghasts, it seems that our people tried to harness the power of the Nether.");
										NBTTagString day5_2 = new NBTTagString(
												"Something must have happened that released all these Ghasts who then attacked the city and left it in the state it's in now.");
										pages.appendTag(day5_1);
										pages.appendTag(day5_2);
									}
								}
							}
						}
					}
					nbt.setTag("pages", pages);
				}
			}
		}
		
	}
	
	private boolean checkAdvancement(EntityPlayerMP player, WorldServer world, String name) {
		try {
			return player.getAdvancements().getProgress(world.getAdvancementManager().getAdvancement(new ResourceLocation(Main.MODID, name))).isDone();
		}
		catch (NullPointerException e) {
			return false;
		}
	}

	@Override
	public ModelResourceLocation getLocation() {
		return new ModelResourceLocation(new ResourceLocation("minecraft", "written_book"), "inventory");
	}

}
