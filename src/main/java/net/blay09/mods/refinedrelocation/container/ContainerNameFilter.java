package net.blay09.mods.refinedrelocation.container;

import net.blay09.mods.refinedrelocation.api.RefinedRelocationAPI;
import net.blay09.mods.refinedrelocation.api.TileOrMultipart;
import net.blay09.mods.refinedrelocation.api.container.IContainerMessage;
import net.blay09.mods.refinedrelocation.filter.NameFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ContainerNameFilter extends ContainerMod {

	public static final String KEY_VALUE = "Value";

	private final EntityPlayer player;
	private final TileOrMultipart tileEntity;
	private final NameFilter filter;

	private String lastValue = "";

	private boolean guiNeedsUpdate;

	public ContainerNameFilter(EntityPlayer player, TileOrMultipart tileEntity, NameFilter filter) {
		this.player = player;
		this.tileEntity = tileEntity;
		this.filter = filter;

		addPlayerInventory(player, 128);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		if (!lastValue.equals(filter.getValue())) {
			RefinedRelocationAPI.syncContainerValue(KEY_VALUE, filter.getValue(), listeners);
			RefinedRelocationAPI.updateFilterPreview(player, tileEntity, filter);
			lastValue = filter.getValue();
		}
	}

	@Override
	public void receivedMessageClient(IContainerMessage message) {
		if(message.getKey().equals(KEY_VALUE)) {
			filter.setValue(message.getStringValue());
			markGuiNeedsUpdate(true);
		}
	}

	@Override
	public void receivedMessageServer(IContainerMessage message) {
		if(message.getKey().equals(KEY_VALUE)) {
			filter.setValue(message.getStringValue());
			tileEntity.markDirty();
			lastValue = filter.getValue();
			RefinedRelocationAPI.updateFilterPreview(player, tileEntity, filter);
		}
	}

	@Nullable
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		ItemStack itemStack = super.slotClick(slotId, dragType, clickTypeIn, player);
		RefinedRelocationAPI.updateFilterPreview(player, tileEntity, filter);
		return itemStack;
	}

	public void sendValueToServer(String value) {
		RefinedRelocationAPI.sendContainerMessageToServer(KEY_VALUE, value);
	}

	public String getValue() {
		return filter.getValue();
	}

	public void markGuiNeedsUpdate(boolean dirty) {
		this.guiNeedsUpdate = dirty;
	}

	public boolean doesGuiNeedUpdate() {
		return guiNeedsUpdate;
	}

	public TileOrMultipart getTileEntity() {
		return tileEntity;
	}
}
