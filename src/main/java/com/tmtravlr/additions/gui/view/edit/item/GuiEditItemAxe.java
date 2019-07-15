package com.tmtravlr.additions.gui.view.edit.item;

import com.tmtravlr.additions.addon.Addon;
import com.tmtravlr.additions.addon.items.ItemAddedAxe;

import net.minecraft.client.gui.GuiScreen;

/**
 * Page for adding a new axe or editing an existing one.
 * 
 * @author Tmtravlr (Rebeca Rey)
 * @since November 2017
 */
public class GuiEditItemAxe extends GuiEditItemTool<ItemAddedAxe> {
    
	public GuiEditItemAxe(GuiScreen parentScreen, String title, Addon addon, ItemAddedAxe item) {
		super(parentScreen, title, addon);
		
		this.isNew = item == null;
		
		if (this.isNew) {
			this.item = new ItemAddedAxe();
		} else {
			this.item = item;
		}
	}

	@Override
	public void initComponents() {
		super.initComponents();
		
		if (this.copyFrom != null) {
			this.copyFromOther();
		}
		
		this.components.add(this.itemIdInput);
		this.components.add(this.itemNameInput);
		this.components.add(this.itemShinesInput);
		this.components.add(this.itemMaterialInput);
		if (!this.isNew) {
			this.components.add(this.itemTextureButton);
		}
		
		this.advancedComponents.add(this.itemTooltipInput);
		this.advancedComponents.add(this.itemOreDictInput);
		this.advancedComponents.add(this.itemBurnTimeInput);
		this.advancedComponents.add(this.itemIsBeaconPaymentInput);
		this.advancedComponents.add(this.itemContainerInput);
		this.advancedComponents.add(this.itemApplyVanillaAttributesInput);
		this.advancedComponents.add(this.itemAttributesInput);
	}
}
