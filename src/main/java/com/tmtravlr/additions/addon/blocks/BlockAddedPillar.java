package com.tmtravlr.additions.addon.blocks;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.tmtravlr.additions.AdditionsMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Block that can face different directions
 * 
 * @author Tmtravlr (Rebeca Rey)
 * @since May 2019
 */
public class BlockAddedPillar extends BlockAddedSimple {
	
	public static final ResourceLocation TYPE = new ResourceLocation(AdditionsMod.MOD_ID, "pillar");
	
	public BlockAddedPillar() {
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLiquid.LEVEL, Integer.valueOf(0)).withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y));
	}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return this.rotateBoundingBox(super.getBoundingBox(state, source, pos), state);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
    	AxisAlignedBB boundingBox = super.getCollisionBoundingBox(state, world, pos);
    	
    	if (boundingBox != Block.NULL_AABB && !this.hasSameCollisionBoundingBox()) {
    		boundingBox = this.rotateBoundingBox(boundingBox, state);
    	}
    	return boundingBox;
    }
    
    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
    	AxisAlignedBB boundingBox = this.getBoundingBox(state, world, pos);
    	return boundingBox.maxY == 1 && boundingBox.minX <= 0.45 && boundingBox.minZ <= 0.45 && boundingBox.maxX >= 0.55 && boundingBox.maxZ >= 0.55;
    }
    
    @Override
    public boolean isTopSolid(IBlockState state) {
    	AxisAlignedBB boundingBox = this.rotateBoundingBox(new AxisAlignedBB(this.getBoundingBoxMinX(), this.getBoundingBoxMinY(), this.getBoundingBoxMinZ(), this.getBoundingBoxMaxX(), this.getBoundingBoxMaxY(), this.getBoundingBoxMaxZ()), state);
        return state.getMaterial().isOpaque() && boundingBox.maxY == 1 && boundingBox.minX == 0 && boundingBox.minZ == 0 && boundingBox.maxX == 1 && boundingBox.maxZ == 1;
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
    	if (!this.isNormalCube(state, world, pos)) {
    		return BlockFaceShape.UNDEFINED;
    	} else {
    		AxisAlignedBB boundingBox = this.getBoundingBox(state, world, pos);
	    	boolean solid = false;
	    	
	    	if (face == EnumFacing.UP) {
	    		solid = boundingBox.maxY == 1 && boundingBox.minX <= 0 && boundingBox.minZ <= 0 && boundingBox.maxX >= 1 && boundingBox.maxZ >= 1;
	    	} else if (face == EnumFacing.DOWN) {
	    		solid = boundingBox.minY == 0 && boundingBox.minX <= 0 && boundingBox.minZ <= 0 && boundingBox.maxX >= 1 && boundingBox.maxZ >= 1;
	    	} else if (face == EnumFacing.EAST) {
	    		solid = boundingBox.maxX == 1 && boundingBox.minY <= 0 && boundingBox.minZ <= 0 && boundingBox.maxY >= 1 && boundingBox.maxZ >= 1;
	    	} else if (face == EnumFacing.WEST) {
	    		solid = boundingBox.minX == 0 && boundingBox.minY <= 0 && boundingBox.minZ <= 0 && boundingBox.maxY >= 1 && boundingBox.maxZ >= 1;
	    	} else if (face == EnumFacing.SOUTH) {
	    		solid = boundingBox.maxZ == 1 && boundingBox.minY <= 0 && boundingBox.minX <= 0 && boundingBox.maxY >= 1 && boundingBox.maxX >= 1;
	    	} else if (face == EnumFacing.NORTH) {
	    		solid = boundingBox.minZ == 0 && boundingBox.minY <= 0 && boundingBox.minX <= 0 && boundingBox.maxY >= 1 && boundingBox.maxX >= 1;
	    	}
	    	return solid ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    	}
    }

    @Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(BlockRotatedPillar.AXIS, facing.getAxis());
    }
	
    @Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {BlockLiquid.LEVEL, BlockRotatedPillar.AXIS});
    }

    @Override
	public IBlockState getStateFromMeta(int meta) {
        EnumFacing.Axis axis = EnumFacing.Axis.Y;
        meta = meta & 12;

        if (meta == 4) {
            axis = EnumFacing.Axis.X;
        } else if (meta == 8) {
            axis = EnumFacing.Axis.Z;
        }

        return this.getDefaultState().withProperty(BlockRotatedPillar.AXIS, axis);
    }

    @Override
	public int getMetaFromState(IBlockState state) {
        int meta = 0;
        EnumFacing.Axis axis = state.getValue(BlockRotatedPillar.AXIS);

        if (axis == EnumFacing.Axis.X) {
            meta |= 4;
        } else if (axis == EnumFacing.Axis.Z) {
            meta |= 8;
        }

        return meta;
    }
    
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        switch (rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch (state.getValue(BlockRotatedPillar.AXIS)) {
                    case X:
                        return state.withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Z);
                    case Z:
                        return state.withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }

    @Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (this.isWood(world, pos) && world.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5))) {
            for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
                IBlockState iblockstate = world.getBlockState(blockpos);

                if (iblockstate.getBlock().isLeaves(iblockstate, world, blockpos)) {
                    iblockstate.getBlock().beginLeavesDecay(iblockstate, world, blockpos);
                }
            }
        }
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
    	return this.isWood(world, pos);
	}
    
    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
    	return this.blockMaterial == Material.WOOD;
    }
    
    private AxisAlignedBB rotateBoundingBox(AxisAlignedBB original, IBlockState state) {
    	switch(state.getValue(BlockRotatedPillar.AXIS)) {
    	case X:
    		return new AxisAlignedBB(original.minY, 1 - original.maxX, original.minZ, original.maxY, 1 - original.minX, original.maxZ);
    	case Z:
    		return new AxisAlignedBB(original.minX, 1 - original.maxZ, original.minY, original.maxX, 1 - original.minZ, original.maxY);
    	default:
    		return original;
    	}
    }
	
	public static class Serializer extends IBlockAdded.Serializer<BlockAddedPillar> {
		
		public Serializer() {
			super(TYPE, BlockAddedPillar.class);
		}
		
		@Override
		public JsonObject serialize(BlockAddedPillar blockAdded, JsonSerializationContext context) {
			JsonObject json = super.serialize(blockAdded, context);
			
			if (!blockAdded.hasCollisionBox()) {
				json.addProperty("has_collision_box", false);
			}
			
			if (!blockAdded.hasSameCollisionBoundingBox()) {
				json.addProperty("same_collision_bounding_box", false);
			}
			
			if (blockAdded.getBoundingBoxMinX() != 0) {
				json.addProperty("bounding_box_min_x", blockAdded.getBoundingBoxMinX());
			}
			
			if (blockAdded.getBoundingBoxMinY() != 0) {
				json.addProperty("bounding_box_min_y", blockAdded.getBoundingBoxMinY());
			}
			
			if (blockAdded.getBoundingBoxMinZ() != 0) {
				json.addProperty("bounding_box_min_z", blockAdded.getBoundingBoxMinZ());
			}
			
			if (blockAdded.getBoundingBoxMaxX() != 1) {
				json.addProperty("bounding_box_max_x", blockAdded.getBoundingBoxMaxX());
			}
			
			if (blockAdded.getBoundingBoxMaxY() != 1) {
				json.addProperty("bounding_box_max_y", blockAdded.getBoundingBoxMaxY());
			}
			
			if (blockAdded.getBoundingBoxMaxZ() != 1) {
				json.addProperty("bounding_box_max_z", blockAdded.getBoundingBoxMaxZ());
			}
			
			if (blockAdded.getCollisionBoxMinX() != 0) {
				json.addProperty("collision_box_min_x", blockAdded.getCollisionBoxMinX());
			}
			
			if (blockAdded.getCollisionBoxMinY() != 0) {
				json.addProperty("collision_box_min_y", blockAdded.getCollisionBoxMinY());
			}
			
			if (blockAdded.getCollisionBoxMinZ() != 0) {
				json.addProperty("collision_box_min_z", blockAdded.getCollisionBoxMinZ());
			}
			
			if (blockAdded.getCollisionBoxMaxX() != 1) {
				json.addProperty("collision_box_max_x", blockAdded.getCollisionBoxMaxX());
			}
			
			if (blockAdded.getCollisionBoxMaxY() != 1) {
				json.addProperty("collision_box_max_y", blockAdded.getCollisionBoxMaxY());
			}
			
			if (blockAdded.getCollisionBoxMaxZ() != 1) {
				json.addProperty("collision_box_max_z", blockAdded.getCollisionBoxMaxZ());
			}
			
			return json;
		}
		
		@Override
		public BlockAddedPillar deserialize(JsonObject json, JsonDeserializationContext context) {
			BlockAddedPillar blockAdded = new BlockAddedPillar();
			super.deserializeDefaults(json, context, blockAdded);
			
			blockAdded.setHasCollisionBox(JsonUtils.getBoolean(json, "has_collision_box", true));
			blockAdded.setHasSameCollisionBoundingBox(JsonUtils.getBoolean(json, "same_collision_bounding_box", true));
			blockAdded.setBoundingBoxMinX(JsonUtils.getFloat(json, "bounding_box_min_x", 0));
			blockAdded.setBoundingBoxMinY(JsonUtils.getFloat(json, "bounding_box_min_y", 0));
			blockAdded.setBoundingBoxMinZ(JsonUtils.getFloat(json, "bounding_box_min_z", 0));
			blockAdded.setBoundingBoxMaxX(Math.max(JsonUtils.getFloat(json, "bounding_box_max_x", 1), blockAdded.getBoundingBoxMinX()));
			blockAdded.setBoundingBoxMaxY(Math.max(JsonUtils.getFloat(json, "bounding_box_max_y", 1), blockAdded.getBoundingBoxMinY()));
			blockAdded.setBoundingBoxMaxZ(Math.max(JsonUtils.getFloat(json, "bounding_box_max_z", 1), blockAdded.getBoundingBoxMinZ()));
			blockAdded.setCollisionBoxMinX(JsonUtils.getFloat(json, "collision_box_min_x", 0));
			blockAdded.setCollisionBoxMinY(JsonUtils.getFloat(json, "collision_box_min_y", 0));
			blockAdded.setCollisionBoxMinZ(JsonUtils.getFloat(json, "collision_box_min_z", 0));
			blockAdded.setCollisionBoxMaxX(Math.max(JsonUtils.getFloat(json, "collision_box_max_x", 1), blockAdded.getCollisionBoxMinX()));
			blockAdded.setCollisionBoxMaxY(Math.max(JsonUtils.getFloat(json, "collision_box_max_y", 1), blockAdded.getCollisionBoxMinY()));
			blockAdded.setCollisionBoxMaxZ(Math.max(JsonUtils.getFloat(json, "collision_box_max_z", 1), blockAdded.getCollisionBoxMinZ()));
			
			return blockAdded;
		}
    }

}
