package MrGise.build_kit.event;

import MrGise.build_kit.BuildKit;
import MrGise.build_kit.types.block.LayeredBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BuildKit.MODID)
public class BlockPlacementEvents {
    @SubscribeEvent
    public static void placeLayeredBlocks(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack held = event.getItemStack();
        Item heldItem = held.getItem();
        Player player = event.getEntity();

        if (block instanceof LayeredBlock) {
            int stateLevel = state.getValue(LayeredBlock.LEVEL);
            if (!held.isEmpty() && heldItem == block.asItem() && stateLevel < 16 && event.getFace() == Direction.UP
                    && level.getEntities((Entity) null, new AABB(pos), Entity::isAlive).isEmpty()) {
                event.setCanceled(true);
                event.setUseBlock(Event.Result.ALLOW);
                event.setCancellationResult(InteractionResult.CONSUME_PARTIAL);
                level.setBlockAndUpdate(pos, state.setValue(LayeredBlock.LEVEL, stateLevel + 1));

                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }
                InteractionHand hand = event.getHand();
                player.setItemInHand(hand, held);
                level.playSound(null, pos, state.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 0.8f);
                player.swing(hand);
            }
        }
    }
}
