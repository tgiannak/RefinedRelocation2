package net.blay09.mods.refinedrelocation.client.gui.base.element;

import net.blay09.mods.refinedrelocation.client.ClientProxy;
import net.blay09.mods.refinedrelocation.client.gui.base.IParentScreen;
import net.blay09.mods.refinedrelocation.client.util.TextureAtlasRegion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;

public class GuiImageButton extends GuiElement {

	private TextureAtlasRegion background;
	private TextureAtlasRegion backgroundHover;
	private TextureAtlasRegion backgroundDisabled;
	private boolean enabled = true;

	public GuiImageButton(int x, int y, String textureName) {
		setPosition(x, y);
		setButtonTexture(textureName);
		setSize(background.getIconWidth(), background.getIconHeight());
	}

	public void setButtonTexture(String textureName) {
		background = ClientProxy.TEXTURE_ATLAS.getSprite("refinedrelocation:" + textureName);
		backgroundHover = ClientProxy.TEXTURE_ATLAS.getSprite("refinedrelocation:" + textureName + "_hover");
		backgroundDisabled = ClientProxy.TEXTURE_ATLAS.getSprite("refinedrelocation:" + textureName + "_disabled");
	}

	@Override
	public final boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1f));
		actionPerformed();
		return true;
	}

	@Override
	public void drawForeground(IParentScreen parentScreen, int mouseX, int mouseY) {
		super.drawForeground(parentScreen, mouseX, mouseY);
		if (isVisible()) {
			GlStateManager.color(1f, 1f, 1f, 1f);
			if(!enabled) {
				backgroundDisabled.draw(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight(), zLevel);
			} else {
				if(isInside(mouseX, mouseY)) {
					backgroundHover.draw(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight(), zLevel);
				} else {
					background.draw(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight(), zLevel);
				}
			}
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void actionPerformed() {

	}

}
