package climbingglove;

import net.minecraft.src.*;

public class CG_ClimbingGloveActive extends Item 
{
	public CG_ClimbingGloveActive(int i) 
	{
		super(i);
		this.setMaxStackSize(1);
	}
	
	/** What happens when the item is right-clicked **/
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		entityplayer.addChatMessage("Climbing Glove Disabled"); // Chat message
		//mod_ClimbingGlove.activateClimbing = false; This method has been removed
		itemstack = new ItemStack(CG_ClimbingGloveEngine.climbingGloveInactive);
        return itemstack;
    }
}
