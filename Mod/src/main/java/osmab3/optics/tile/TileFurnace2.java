package osmab3.optics.tile;

import net.minecraft.block.BlockFurnace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.MathHelper;
import osmab3.optics.block.BlockFurnace2;

public class TileFurnace2 extends TileEntityFurnace {



    private boolean canSmelt()
    {
        if (((ItemStack)this.getStackInSlot(0)).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.getStackInSlot(0));

            if (itemstack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = this.getStackInSlot(2);

                if (itemstack1.isEmpty())
                {
                    return true;
                }
                else if (!itemstack1.isItemEqual(itemstack))
                {
                    return false;
                }
                else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
                {
                    return true;
                }
                else
                {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }
    }


    @Override
    public void update() {
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning())
        {
            this.setField(0, this.getField(0));
        }

        if (!this.world.isRemote)
        {
            ItemStack itemstack = this.getStackInSlot(1);

            if (this.isBurning() || !itemstack.isEmpty() && !((ItemStack)this.getStackInSlot(1)).isEmpty())
            {
                if (!this.isBurning() && this.canSmelt())
                {
                    this.setField(0, getItemBurnTime(itemstack));
                    this.setField(1, this.getField(0));

                    if (this.isBurning())
                    {
                        flag1 = true;

                        if (!itemstack.isEmpty())
                        {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);

                            if (itemstack.isEmpty())
                            {
                                ItemStack item1 = item.getContainerItem(itemstack);

                                this.setInventorySlotContents(1, item1);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {
                    this.setField(2, this.getField(2)+1);

                    if (this.getField(2) == this.getField(3))
                    {
                        this.setField(2, 0);
                        this.setField(3,this.getCookTime(this.getStackInSlot(0)));
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else
                {
                    this.setField(2,0);
                }
            }
            else if (!this.isBurning() && this.getField(2) > 0)
            {
                this.setField(2,MathHelper.clamp(this.getField(2) - 2, 0, this.getField(3)));
            }

            if (flag != this.isBurning())
            {
                flag1 = true;
                BlockFurnace2.setState(this.isBurning(), this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }
}
