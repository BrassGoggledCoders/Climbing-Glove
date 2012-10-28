package glovemod.common;

import net.minecraft.src.*;

public class ClimbingGloveActive extends Item 
{
	public ClimbingGloveActive(int i) 
	{
		super(i);
		this.setMaxStackSize(1);
	}
	
	/** What happens when the item is right-clicked **/
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		entityplayer.addChatMessage("Climbing Glove Disabled"); // Chat message
		//mod_ClimbingGlove.activateClimbing = false; This method has been removed
		itemstack = new ItemStack(ClimbingGloveEngine.climbingGloveOff);
        return itemstack;
    }
	
	public String getTextureFile()
	{
		return "/glove/sprites/climbingglove_sprites.png";
	}
}
