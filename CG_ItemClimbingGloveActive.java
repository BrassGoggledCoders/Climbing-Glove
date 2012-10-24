package net.minecraft.src;

public class CG_ItemClimbingGloveActive extends Item 
{
	public CG_ItemClimbingGloveActive(int i) 
	{
		super(i);
		this.setMaxStackSize(1);
	}
	
	/** What happens when the item is right-clicked **/
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		entityplayer.addChatMessage("Climbing Glove Disabled"); // Chat message
		//mod_ClimbingGlove.activateClimbing = false; This method has been removed
		itemstack = new ItemStack(mod_ClimbingGlove.climbingGloveInactive);
        return itemstack;
    }
}
