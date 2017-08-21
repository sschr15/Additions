package com.tmtravlr.additions.gui.edit.components;

import java.util.List;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

import com.tmtravlr.additions.gui.edit.GuiEdit;
import com.tmtravlr.additions.gui.edit.IGuiEditComponent;

/**
 * Input field specifically for nbt, that tells you if the nbt is not valid.
 * 
 * @author Rebeca Rey (Tmtravlr)
 * @date August 2017
 */
public class GuiNBTInput extends Gui implements IGuiEditComponent {

	private static final ResourceLocation GUI_TEXTURES = new ResourceLocation("additions:textures/gui/additions_gui_textures.png");
	
	private GuiEdit parent;
	private GuiTextField nbtText;
	private String label = "";
	private boolean required = false;
	NBTTagCompound tag = null;
	private String prevNBT = "{}";
	private String error = "";

	public GuiNBTInput(String label, GuiEdit parent) {
		this.parent = parent;
		this.label = label;
		this.nbtText = new GuiTextField(0, this.parent.getFontRenderer(), 0, 0, 0, 20);
		this.nbtText.setMaxStringLength(Integer.MAX_VALUE);
		this.setDefaultText(prevNBT);
	}
	
	public void setDefaultText(String text) {
		this.nbtText.setText(text);
		this.nbtText.setCursorPositionZero();
		this.parseNBT(false);
	}
	
	public void setRequired() {
		this.required = true;
	}
	
	public NBTTagCompound getTag() {
		this.parseNBT(false);
		if (this.error.isEmpty()) {
			return this.tag;
		} else {
			return null;
		}
	}
	
	public String getTagText() {
		return this.nbtText.getText();
	}
	
	@Override
	public int getHeight(int left, int right) {
		return 40;
	}
	
	@Override
	public String getLabel() {
		return this.label;
	}
	
	@Override
	public boolean isRequired() {
		return this.required;
	}
	
	@Override
	public void drawInList(int x, int y, int right, int mouseX, int mouseY) {
		
		this.nbtText.x = x;
		this.nbtText.y = y + 10;
		this.nbtText.width = right - 60 - x;
		
		this.nbtText.drawTextBox();
	
		this.parseNBT(true);
		
		this.parent.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        GlStateManager.color(255.0F, 255.0F, 255.0F, 255.0F);
		
		int errorX = this.nbtText.x + this.nbtText.width + 4;
		int errorY = this.nbtText.y + (this.nbtText.height / 2 - 6);

		if (!this.error.isEmpty()) {
			this.parent.drawTexturedModalRect(errorX, errorY, 86, 64, 13, 13);
			
			if (mouseX > errorX && mouseX < errorX + 13 && mouseY > errorY && mouseY < errorY + 13) {
				List<String> errorSplit = this.parent.getFontRenderer().listFormattedStringToWidth(this.error, 200);
				GuiUtils.drawHoveringText(errorSplit, mouseX, mouseY, this.parent.width, this.parent.height, -1, this.parent.getFontRenderer());
	            RenderHelper.disableStandardItemLighting();
			}
		} else {
			this.parent.drawTexturedModalRect(errorX, errorY, 99, 64, 13, 13);
		}
	}
	
	@Override
	public void onKeyTyped(char keyTyped, int keyCode) {
		this.nbtText.textboxKeyTyped(keyTyped, keyCode);
	}
	
	@Override
	public void onMouseClicked(int mouseX, int mouseY, int mouseButton) {
		this.nbtText.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public boolean onHandleMouseInput(int mouseX, int mouseY) {
		return false;
	}
	
	private void parseNBT(boolean checkForChanges) {
		String currentNBT = this.nbtText.getText();
		if (!currentNBT.equals(this.prevNBT)) {
			if (checkForChanges) {
				this.parent.setHasUnsavedChanges();
			}
			this.prevNBT = currentNBT;
			this.error = "";
			
			try {
				this.tag = JsonToNBT.getTagFromJson(currentNBT);
			} catch (NBTException e) {
				this.error = e.getMessage();
			}
		}
	}
}
