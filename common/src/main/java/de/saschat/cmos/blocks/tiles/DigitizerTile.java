package de.saschat.cmos.blocks.tiles;

import de.saschat.cmos.blocks.peripheral.DigitizerPeripheral;
import de.saschat.cmos.gui.DigitizerMenu;
import de.saschat.cmos.registry.GuiRegistry;
import de.saschat.cmos.registry.TileRegistry;
import de.saschat.cmos.util.WeakContainer;
import io.github.mortuusars.exposure.world.item.PhotographItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DigitizerTile extends BlockEntity implements Container, MenuProvider {
    public WeakContainer<DigitizerPeripheral> peripherals = new WeakContainer<>();

    public DigitizerTile(BlockPos blockPos, BlockState blockState) {
        super(TileRegistry.DIGITIZER_TILE.get(), blockPos, blockState);
        stack.add(ItemStack.EMPTY);
    }

    private List<ItemStack> stack = new ArrayList<>(1);

    @Nullable
    public ItemStack getPhotograph() {
        if (!stack.isEmpty() && stack.getFirst().getItem() instanceof PhotographItem) return stack.getFirst();
        return null;
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        if (stack.getFirst() != null && !stack.getFirst().isEmpty())
            compoundTag.put("Item", stack.getFirst().save(provider));
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        if (compoundTag.contains("Item")) {
            stack.clear();
            stack.add(ItemStack.parse(provider, compoundTag.get("Item")).orElse(null));
        }
        if (stack.isEmpty() || (stack.size() == 1 && (stack.getFirst() == null || stack.getFirst().isEmpty()))) {
            stack.clear();
            stack.add(ItemStack.EMPTY);
        }
    }

    ItemStack preChange = null;

    private void expectChange() {
        preChange = stack.getFirst();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (!level.isClientSide)
            if (stack.getFirst() != preChange) {
                if (stack.getFirst().isEmpty()) {
                    peripherals.operate(DigitizerPeripheral::informEmpty);
                } else {
                    peripherals.operate(DigitizerPeripheral::informNew);
                }
            }
        expectChange();
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return stack.getFirst().isEmpty();
    }

    @Override
    public ItemStack getItem(int i) {
        return stack.getFirst();
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        expectChange();
        ItemStack stack = ContainerHelper.removeItem(this.stack, slot, amount);
        this.setChanged();
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        expectChange();
        ItemStack stack = ContainerHelper.takeItem(this.stack, i);
        this.setChanged();
        return stack;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        expectChange();
        itemStack.limitSize(this.getMaxStackSize(itemStack));
        this.stack.set(0, itemStack);
        this.setChanged();
    }


    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        expectChange();
        stack.clear();
        stack.add(ItemStack.EMPTY);
        setChanged();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.digitizer");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new DigitizerMenu(i, inventory, this);
    }
}
