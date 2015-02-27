package com.iconmaster.aec2.item;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

/**
 *
 * @author iconmaster
 */
public abstract class ItemForgedTool extends AetherCraftItem {

	public Set worksOn;

	public static final Set pickGoodOn = Sets.newHashSet(new Block[]{Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});
	public static final Set shovelGoodOn = Sets.newHashSet(new Block[]{Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium});
	public static final Set axeGoodOn = Sets.newHashSet(new Block[]{Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin});

	public ItemForgedTool(String name, ItemTextures textures, String toolClass, Set worksOn) {
		super(name, textures);

		this.maxStackSize = 1;
		this.toolClass = toolClass;
		this.worksOn = worksOn;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase other, EntityLivingBase player) {
		stack.damageItem(2, player);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase player) {
		if ((double) block.getBlockHardness(world, x, y, z) != 0.0D) {
			stack.damageItem(1, player);
		}

		return true;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", getAttackPower(stack), 0));
		return multimap;
	}

	private String toolClass;

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		int level = super.getHarvestLevel(stack, toolClass);
		if (level == -1 && toolClass != null && toolClass.equals(this.toolClass)) {
			return getHarvestLevel(stack);
		} else {
			return level;
		}
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		return toolClass != null ? ImmutableSet.of(toolClass) : super.getToolClasses(stack);
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		if (ForgeHooks.isToolEffective(stack, block, meta)) {
			return getDigSpeed(stack);
		}
		return super.getDigSpeed(stack, block, meta);
	}

	@Override
	public float func_150893_a(ItemStack stack, Block block) {
		return worksOn.contains(block) ? getDigSpeed(stack) : 1.0F;
	}

	@Override
	public abstract int getMaxDamage(ItemStack stack);

	public abstract double getAttackPower(ItemStack stack);

	public abstract float getDigSpeed(ItemStack stack);

	public abstract int getHarvestLevel(ItemStack stack);

	@Override
	public abstract int getItemEnchantability(ItemStack stack);

	@Override
	public IIcon getIcon(ItemStack stack, int pass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		return textures.getTexture(pass);
	}

	@Override
	public IIcon getIconFromDamageForRenderPass(int stack, int pass) {
		return textures.getTexture(pass);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return textures.getTexture(pass);
	}

	@Override
	public IIcon getIconIndex(ItemStack stack) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		return pass == 0 ? 0x00ff00 : 0xff0000;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
}
