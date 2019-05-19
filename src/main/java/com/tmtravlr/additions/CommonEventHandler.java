package com.tmtravlr.additions;

import java.util.List;
import java.util.Map.Entry;

import com.tmtravlr.additions.addon.effects.Effect;
import com.tmtravlr.additions.addon.effects.cause.EffectCauseItemInHand;
import com.tmtravlr.additions.addon.entities.ai.RangedAttackReplacer;
import com.tmtravlr.additions.addon.structures.AddonStructureManager;
import com.tmtravlr.additions.type.AdditionTypeEffect;
import com.tmtravlr.additions.type.AdditionTypeLootTable;
import com.tmtravlr.additions.type.AdditionTypeRecipe;
import com.tmtravlr.lootoverhaul.loot.ExtraLootManager.LoadLootTableExtrasEvent;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

@Mod.EventBusSubscriber(modid = AdditionsMod.MOD_ID)
public class CommonEventHandler {

	@SubscribeEvent
	public static void onWorldLoad(WorldEvent.Load event) {
		if (ConfigLoader.replaceManagers.getBoolean(true) && event.getWorld() instanceof WorldServer) {
			AddonStructureManager.replaceStructureManager((WorldServer) event.getWorld());
		}
	}
	
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().ticksExisted % 11 == 0 && event.getEntityLiving() instanceof IRangedAttackMob && event.getEntityLiving() instanceof EntityLiving && !event.getEntityLiving().isDead) {
			RangedAttackReplacer.checkIfAINeedsReplacing((EntityLiving & IRangedAttackMob) event.getEntityLiving(), event.getEntityLiving().getHeldItem(EnumHand.MAIN_HAND));
		}
		
		if (!AdditionTypeEffect.INSTANCE.getInHandEffects().isEmpty()) {
			for (Entry<EffectCauseItemInHand, List<Effect>> entry : AdditionTypeEffect.INSTANCE.getInHandEffects().entrySet()) {
				if (entry.getKey().applies(event.getEntityLiving())) {
					entry.getValue().forEach(effect -> effect.applyEffect(event.getEntityLiving(), event.getEntityLiving()));
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onServerTick(ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			RangedAttackReplacer.cleanupDeadMobs();
		}
	}
	
	@SubscribeEvent
	public static void onInteractWithCauldron(PlayerInteractEvent.RightClickBlock event) {
		IBlockState state = event.getWorld().getBlockState(event.getPos());
		ItemStack stack = event.getItemStack();

		if (!event.getWorld().isRemote && state.getBlock() == Blocks.CAULDRON && state.getValue(BlockCauldron.LEVEL).intValue() > 0 && !stack.isEmpty() && stack.hasTagCompound()) {
			AdditionTypeRecipe.cauldronWashingRecipes.stream().filter(recipe -> stack.getItem() == recipe.itemToDye).findFirst().ifPresent(washRecipe -> {
				if (washRecipe.hasColor(stack)) {
					
		            ItemStack washedStack = stack.copy();
		            washedStack.setCount(1);
		            washRecipe.removeColor(washedStack);
		
		            if (!event.getEntityPlayer().capabilities.isCreativeMode) {
		                stack.shrink(1);
		                Blocks.CAULDRON.setWaterLevel(event.getWorld(), event.getPos(), state, state.getValue(BlockCauldron.LEVEL).intValue() - 1);
		            }
		
		            if (stack.isEmpty()) {
		                event.getEntityPlayer().setHeldItem(event.getHand(), washedStack);
		            } else if (!event.getEntityPlayer().inventory.addItemStackToInventory(washedStack)) {
		            	event.getEntityPlayer().dropItem(washedStack, false);
		            } else if (event.getEntityPlayer() instanceof EntityPlayerMP) {
		                ((EntityPlayerMP)event.getEntityPlayer()).sendContainerToPlayer(event.getEntityPlayer().inventoryContainer);
		            }
		            
		            event.getWorld().playSound(null, event.getPos(), SoundEventLoader.BLOCK_CAULDRON_WASH, SoundCategory.BLOCKS, 1.0f, 1.0f);
		            event.setCanceled(true);
		            event.setCancellationResult(EnumActionResult.SUCCESS);
				}
			});
		}
	}
	
	@SubscribeEvent
	public static void onLootTableExtrasLoad(LoadLootTableExtrasEvent event) {
		AdditionTypeLootTable.INSTANCE.getLootTableExtras(event.getLootTableLocation(), event.getLootTableManager()).forEach(event::addExtraLootTable);
	}
}
