package net.minecraft.src;

public class CG_ItemClimbingGloveInactive extends Item 
{	
	public CG_ItemClimbingGloveInactive(int i) 
	{
		super(i);
		this.setMaxStackSize(1);
	}
	
	/** What happens when the item is right-clicked **/
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		entityplayer.addChatMessage("Climbing Glove Enabled"); // Chat message
		//mod_ClimbingGlove.activateClimbing = true; This method has been removed
		itemstack = new ItemStack(mod_ClimbingGlove.climbingGloveActive);
        return itemstack;
    }
}
