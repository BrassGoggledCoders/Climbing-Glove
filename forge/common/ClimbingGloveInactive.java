package glovemod.common;

import net.minecraft.src.*;

public class ClimbingGloveInactive extends Item 
{	
	public ClimbingGloveInactive(int i) 
	{
		super(i);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
	/** What happens when the item is right-clicked **/
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		entityplayer.addChatMessage("Climbing Glove Enabled"); // Chat message
		//mod_ClimbingGlove.activateClimbing = true; This method has been removed
		itemstack = new ItemStack(ClimbingGloveEngine.climbingGloveOn);
        return itemstack;
    }
	
	public String getTextureFile()
	{
		return "/glove/sprites/climbingglove_sprites.png";
	}
}
