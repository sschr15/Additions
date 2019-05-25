package com.tmtravlr.additions.gui.view.edit.block;

import com.tmtravlr.additions.addon.Addon;
import com.tmtravlr.additions.addon.blocks.IBlockAdded;
import com.tmtravlr.additions.addon.blocks.IBlockAddedModifiableBoundingBox;
import com.tmtravlr.additions.gui.view.components.input.GuiComponentBooleanInput;
import com.tmtravlr.additions.gui.view.components.input.GuiComponentFloatInput;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Page for adding a block with a modifiable bounding box or editing an existing one.
 * 
 * @author Tmtravlr (Rebeca Rey)
 * @since May 2019
 */
public abstract class GuiEditBlockModifiableBoundingBox<T extends IBlockAdded & IBlockAddedModifiableBoundingBox> extends GuiEditBlock<T> {

	protected GuiComponentBooleanInput blockHasCollisionBoxInput;
	protected GuiComponentBooleanInput blockSameCollisionBoundingBoxInput;
	protected GuiComponentFloatInput blockBoundingBoxMinXInput;
	protected GuiComponentFloatInput blockBoundingBoxMinYInput;
	protected GuiComponentFloatInput blockBoundingBoxMinZInput;
	protected GuiComponentFloatInput blockBoundingBoxMaxXInput;
	protected GuiComponentFloatInput blockBoundingBoxMaxYInput;
	protected GuiComponentFloatInput blockBoundingBoxMaxZInput;
	protected GuiComponentFloatInput blockCollisionBoxMinXInput;
	protected GuiComponentFloatInput blockCollisionBoxMinYInput;
	protected GuiComponentFloatInput blockCollisionBoxMinZInput;
	protected GuiComponentFloatInput blockCollisionBoxMaxXInput;
	protected GuiComponentFloatInput blockCollisionBoxMaxYInput;
	protected GuiComponentFloatInput blockCollisionBoxMaxZInput;
    
	public GuiEditBlockModifiableBoundingBox(GuiScreen parentScreen, String title, Addon addon) {
		super(parentScreen, title, addon);
	}

	@Override
	public void initComponents() {
		super.initComponents();
		
		this.blockBoundingBoxMinXInput = new GuiComponentFloatInput(I18n.format("gui.edit.block.boundingBoxMinX.label"), this, false);
		this.blockBoundingBoxMinXInput.setMinimum(0);
		this.blockBoundingBoxMinXInput.setMaximum(1);
		this.blockBoundingBoxMinXInput.setDefaultFloat(this.block.getBoundingBoxMinX());
		
		this.blockBoundingBoxMinYInput = new GuiComponentFloatInput(I18n.format("gui.edit.block.boundingBoxMinY.label"), this, false);
		this.blockBoundingBoxMinYInput.setMinimum(0);
		this.blockBoundingBoxMinYInput.setMaximum(1);
		this.blockBoundingBoxMinYInput.setDefaultFloat(this.block.getBoundingBoxMinY());
		
		this.blockBoundingBoxMinZInput = new GuiComponentFloatInput(I18n.format("gui.edit.block.boundingBoxMinZ.label"), this, false);
		this.blockBoundingBoxMinZInput.setMinimum(0);
		this.blockBoundingBoxMinZInput.setMaximum(1);
		this.blockBoundingBoxMinZInput.setDefaultFloat(this.block.getBoundingBoxMinZ());
		
		this.blockBoundingBoxMaxXInput = new GuiComponentFloatInput(I18n.format("gui.edit.block.boundingBoxMaxX.label"), this, false);
		this.blockBoundingBoxMaxXInput.setMinimum(0);
		this.blockBoundingBoxMaxXInput.setMaximum(1);
		this.blockBoundingBoxMaxXInput.setDefaultFloat(this.block.getBoundingBoxMaxX());
		
		this.blockBoundingBoxMaxYInput = new GuiComponentFloatInput(I18n.format("gui.edit.block.boundingBoxMaxY.label"), this, false);
		this.blockBoundingBoxMaxYInput.setMinimum(0);
		this.blockBoundingBoxMaxYInput.setMaximum(1);
		this.blockBoundingBoxMaxYInput.setDefaultFloat(this.block.getBoundingBoxMaxY());
		
		this.blockBoundingBoxMaxZInput = new GuiComponentFloatInput(I18n.format("gui.edit.block.boundingBoxMaxZ.label"), this, false);
		this.blockBoundingBoxMaxZInput.setMinimum(0);
		this.blockBoundingBoxMaxZInput.setMaximum(1);
		this.blockBoundingBoxMaxZInput.setDefaultFloat(this.block.getBoundingBoxMaxZ());
	    
		this.blockCollisionBoxMinXInput = new GuiComponentFloatInput(I18n.format("gui.edit.block.collisionBoxMinX.label"), this, false);
		this.blockCollisionBoxMinXInput.setMinimum(0);
		this.blockCollisionBoxMinXInput.setMaximum(1);
		this.blockCollisionBoxMinXInput.setDefaultFloat(this.block.getCollisionBoxMinX());
	    
		this.blockCollisionBoxMinYInput = new GuiComponentFloatInput(I18n.format("gui.edit.block.collisionBoxMinY.label"), this, false);
		this.blockCollisionBoxMinYInput.setMinimum(0);
		this.blockCollisionBoxMinYInput.setMaximum(1);
		this.blockCollisionBoxMinYInput.setDefaultFloat(this.block.getCollisionBoxMinY());
	    
		this.blockCollisionBoxMinZInput = new GuiComponentFloatInput(I18n.format("gui.edit.block.collisionBoxMinZ.label"), this, false);
		this.blockCollisionBoxMinZInput.setMinimum(0);
		this.blockCollisionBoxMinZInput.setMaximum(1);
		this.blockCollisionBoxMinZInput.setDefaultFloat(this.block.getCollisionBoxMinZ());
	    
		this.blockCollisionBoxMaxXInput = new GuiComponentFloatInput(I18n.format("gui.edit.block.collisionBoxMaxX.label"), this, false);
		this.blockCollisionBoxMaxXInput.setMinimum(0);
		this.blockCollisionBoxMaxXInput.setMaximum(1);
		this.blockCollisionBoxMaxXInput.setDefaultFloat(this.block.getCollisionBoxMaxX());
	    
		this.blockCollisionBoxMaxYInput = new GuiComponentFloatInput(I18n.format("gui.edit.block.collisionBoxMaxY.label"), this, false);
		this.blockCollisionBoxMaxYInput.setMinimum(0);
		this.blockCollisionBoxMaxYInput.setMaximum(1);
		this.blockCollisionBoxMaxYInput.setDefaultFloat(this.block.getCollisionBoxMaxY());
	    
		this.blockCollisionBoxMaxZInput = new GuiComponentFloatInput(I18n.format("gui.edit.block.collisionBoxMaxZ.label"), this, false);
		this.blockCollisionBoxMaxZInput.setMinimum(0);
		this.blockCollisionBoxMaxZInput.setMaximum(1);
		this.blockCollisionBoxMaxZInput.setDefaultFloat(this.block.getCollisionBoxMaxZ());
		
		this.blockHasCollisionBoxInput = new GuiComponentBooleanInput(I18n.format("gui.edit.block.hasCollisionBox.label"), this) {
			
			@Override
			public void setDefaultBoolean(boolean input) {
				super.setDefaultBoolean(input);
				GuiEditBlockModifiableBoundingBox.this.blockCollisionBoxMinXInput.setHidden(!input);
				GuiEditBlockModifiableBoundingBox.this.blockCollisionBoxMinYInput.setHidden(!input);
				GuiEditBlockModifiableBoundingBox.this.blockCollisionBoxMinZInput.setHidden(!input);
				GuiEditBlockModifiableBoundingBox.this.blockCollisionBoxMaxXInput.setHidden(!input);
				GuiEditBlockModifiableBoundingBox.this.blockCollisionBoxMaxYInput.setHidden(!input);
				GuiEditBlockModifiableBoundingBox.this.blockCollisionBoxMaxZInput.setHidden(!input);
			}
			
		};
		this.blockHasCollisionBoxInput.setDefaultBoolean(this.block.hasCollisionBox());
		
		this.blockSameCollisionBoundingBoxInput = new GuiComponentBooleanInput(I18n.format("gui.edit.block.sameCollisionBoundingBox.label"), this) {
			
			@Override
			public void setDefaultBoolean(boolean input) {
				super.setDefaultBoolean(input);
				if (input) {
					GuiEditBlockModifiableBoundingBox.this.blockHasCollisionBoxInput.setHidden(true);
					GuiEditBlockModifiableBoundingBox.this.blockCollisionBoxMinXInput.setHidden(true);
					GuiEditBlockModifiableBoundingBox.this.blockCollisionBoxMinYInput.setHidden(true);
					GuiEditBlockModifiableBoundingBox.this.blockCollisionBoxMinZInput.setHidden(true);
					GuiEditBlockModifiableBoundingBox.this.blockCollisionBoxMaxXInput.setHidden(true);
					GuiEditBlockModifiableBoundingBox.this.blockCollisionBoxMaxYInput.setHidden(true);
					GuiEditBlockModifiableBoundingBox.this.blockCollisionBoxMaxZInput.setHidden(true);
				} else {
					GuiEditBlockModifiableBoundingBox.this.blockHasCollisionBoxInput.setHidden(false);
					GuiEditBlockModifiableBoundingBox.this.blockHasCollisionBoxInput.setDefaultBoolean(GuiEditBlockModifiableBoundingBox.this.blockHasCollisionBoxInput.getBoolean());
				}
			}
			
		};
		this.blockSameCollisionBoundingBoxInput.setInfo(new TextComponentTranslation("gui.edit.block.sameCollisionBoundingBox.info"));
		this.blockSameCollisionBoundingBoxInput.setDefaultBoolean(this.block.hasSameCollisionBoundingBox());
	}
	
	@Override
	public void onToggleShowAdvanced(boolean showAdvanced) {
		if (showAdvanced) {
			this.blockHasCollisionBoxInput.setDefaultBoolean(this.blockHasCollisionBoxInput.getBoolean());
			this.blockSameCollisionBoundingBoxInput.setDefaultBoolean(this.blockSameCollisionBoundingBoxInput.getBoolean());
		}
	}
	
	@Override
	public void saveObject() {
		this.block.setHasCollisionBox(this.blockHasCollisionBoxInput.getBoolean());
		this.block.setHasSameCollisionBoundingBox(this.blockSameCollisionBoundingBoxInput.getBoolean());
		this.block.setBoundingBoxMinX(this.blockBoundingBoxMinXInput.getFloat());
		this.block.setBoundingBoxMinY(this.blockBoundingBoxMinYInput.getFloat());
		this.block.setBoundingBoxMinZ(this.blockBoundingBoxMinZInput.getFloat());
		this.block.setBoundingBoxMaxX(Math.max(this.blockBoundingBoxMaxXInput.getFloat(), this.block.getBoundingBoxMinX()));
		this.block.setBoundingBoxMaxY(Math.max(this.blockBoundingBoxMaxYInput.getFloat(), this.block.getBoundingBoxMinY()));
		this.block.setBoundingBoxMaxZ(Math.max(this.blockBoundingBoxMaxZInput.getFloat(), this.block.getBoundingBoxMinZ()));
		this.block.setCollisionBoxMinX(this.blockCollisionBoxMinXInput.getFloat());
		this.block.setCollisionBoxMinY(this.blockCollisionBoxMinYInput.getFloat());
		this.block.setCollisionBoxMinZ(this.blockCollisionBoxMinZInput.getFloat());
		this.block.setCollisionBoxMaxX(Math.max(this.blockCollisionBoxMaxXInput.getFloat(), this.block.getCollisionBoxMinX()));
		this.block.setCollisionBoxMaxY(Math.max(this.blockCollisionBoxMaxYInput.getFloat(), this.block.getCollisionBoxMinY()));
		this.block.setCollisionBoxMaxZ(Math.max(this.blockCollisionBoxMaxZInput.getFloat(), this.block.getCollisionBoxMinZ()));

		super.saveObject();
	}
	
	@Override
	protected void copyFromOther() {
	    this.blockHasCollisionBoxInput.setDefaultBoolean(this.copyFrom.hasCollisionBox());
	    this.blockSameCollisionBoundingBoxInput.setDefaultBoolean(this.copyFrom.hasSameCollisionBoundingBox());
	    this.blockBoundingBoxMinXInput.setDefaultFloat(this.copyFrom.getBoundingBoxMinX());
	    this.blockBoundingBoxMinYInput.setDefaultFloat(this.copyFrom.getBoundingBoxMinY());
	    this.blockBoundingBoxMinZInput.setDefaultFloat(this.copyFrom.getBoundingBoxMinZ());
	    this.blockBoundingBoxMinXInput.setDefaultFloat(this.copyFrom.getBoundingBoxMinX());
	    this.blockBoundingBoxMaxYInput.setDefaultFloat(this.copyFrom.getBoundingBoxMaxY());
	    this.blockBoundingBoxMaxZInput.setDefaultFloat(this.copyFrom.getBoundingBoxMaxZ());
	    this.blockCollisionBoxMinXInput.setDefaultFloat(this.copyFrom.getCollisionBoxMinX());
	    this.blockCollisionBoxMinYInput.setDefaultFloat(this.copyFrom.getCollisionBoxMinY());
	    this.blockCollisionBoxMinZInput.setDefaultFloat(this.copyFrom.getCollisionBoxMinZ());
	    this.blockCollisionBoxMinXInput.setDefaultFloat(this.copyFrom.getCollisionBoxMinX());
	    this.blockCollisionBoxMaxYInput.setDefaultFloat(this.copyFrom.getCollisionBoxMaxY());
	    this.blockCollisionBoxMaxZInput.setDefaultFloat(this.copyFrom.getCollisionBoxMaxZ());
		
		super.copyFromOther();
	}
}
