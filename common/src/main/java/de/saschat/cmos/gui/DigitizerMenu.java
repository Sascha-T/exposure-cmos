package de.saschat.cmos.gui;

import de.saschat.cmos.ExposureComputerMod;
import de.saschat.cmos.registry.GuiRegistry;
import io.github.mortuusars.exposure.Exposure;
import io.github.mortuusars.exposure.world.item.PhotographItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DigitizerMenu extends AbstractContainerMenu {
    private final Container inventory;
    public DigitizerMenu(int id, Inventory player) {
        this(id, player, new SimpleContainer(1));
    }
    public DigitizerMenu(int i, Inventory player, Container inventory) {
        super(GuiRegistry.DIGITIZER_MENU.get(), i);
        this.inventory = inventory;



        addSlot(new DigitizerSlot(this.inventory, 0, 8 + 4 * 18, 35));

        for (var y = 0; y < 3; y++) {
            for (var x = 0; x < 9; x++) {
                addSlot(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for (var x = 0; x < 9; x++) {
            addSlot(new Slot(player, x, 8 + x * 18, 142));
        }
    }

    public static class DigitizerSlot extends Slot {
        public DigitizerSlot(Container container, int i, int j, int k) {
            super(container, i, j, k);
        }

        @Override
        public boolean mayPlace(ItemStack itemStack) {
            return itemStack.getItem() instanceof PhotographItem;
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        var slot = slots.get(i);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        var existing = slot.getItem().copy();
        var result = existing.copy();
        if (i == 0) {
            // into ply
            if (!moveItemStackTo(existing, 1, 37, true)) return ItemStack.EMPTY;
        } else {
            // into dig
            if (!moveItemStackTo(existing, 0, 1, false)) return ItemStack.EMPTY;
        }

        if (existing.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (existing.getCount() == result.getCount()) return ItemStack.EMPTY;

        slot.onTake(player, existing);
        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return inventory.stillValid(player);
    }
}
