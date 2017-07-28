package com.tmtravlr.additions.gui;

import java.util.ArrayList;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiScrollingList;

import com.tmtravlr.additions.addon.AddonInfo;

public class GuiScrollingAddonList extends GuiScrollingList {
	
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation("additions:textures/gui/additions_gui_textures.png");

	private GuiAdditionsMainMenu parent;
	private ArrayList<AddonInfo> addons;
	
	public GuiScrollingAddonList(GuiAdditionsMainMenu parent, ArrayList<AddonInfo> addons, int listWidth, int listHeight, int minY, int slotHeight) {
        super(parent.mc, listWidth, listHeight, minY, minY + listHeight, (parent.width - listWidth) / 2, slotHeight, parent.width, parent.height);
        this.parent = parent;
        this.addons = addons;
	}
	
	@Override
	protected int getSize() {
		return addons.size();
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		this.parent.addonSelected(this.addons.get(index));
	}

	@Override
	protected boolean isSelected(int index) {
		return false;
	}

	@Override
	protected void drawBackground() {
		this.parent.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int index, int right, int top, int height, Tessellator tess) {
		top -= 2;
		int left = this.left + 2;
		boolean isHovering = mouseX >= left && mouseX <= right &&
                mouseY >= top && mouseY <= top + this.slotHeight;
		
        this.parent.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        if (isHovering) {
        	this.parent.drawTexturedModalRect(left, top, 0, 32, 256, 32);
        } else {
        	this.parent.drawTexturedModalRect(left, top, 0, 0, 256, 32);
        }
        
        AddonInfo addon = this.addons.get(index);
        
        if (addon == this.parent.createNew) {
        	this.parent.drawTexturedModalRect(left + 5, top + 6, 0, 65, 21, 21);
        	this.parent.drawString(this.parent.getFontRenderer(), I18n.format("gui.title.newAddon"), left + 42, top + 13, 0xFFFFFF);
        } else {
            RenderHelper.enableGUIStandardItemLighting();
        	this.parent.mc.getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase)null, addon.getLogoItem(), left + 7, top + 8);
        	this.parent.drawString(this.parent.getFontRenderer(), addon.name, left + 42, top + 8, 0xFFFFFF);
        	this.parent.drawString(this.parent.getFontRenderer(), "(" + addon.id + ")", left + 42, top + 20, 0xFFFFFF);
        }
	}

}