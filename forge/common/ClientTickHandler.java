package glovemod.common;

import java.util.EnumSet;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler 
{
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{
		//Example of using onTick methods such as is used in ModLoader's implementation
		if (type.equals(EnumSet.of(TickType.RENDER)))
        {
            onRenderTick();
        }
        else if (type.equals(EnumSet.of(TickType.CLIENT)))
        {
        	Minecraft mc = Minecraft.getMinecraft();
            GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
            if (guiscreen != null)
            {
                onTickInGUI(mc, guiscreen);
            } else 
            {
                onTickInGame(mc);
            }
        }	
	}

	/**
	 *  Checks this code every tick that goes by in the game (1 tick = 0.1 seconds)
	 */
	public boolean onTickInGame(Minecraft minecraft)
	{	
		boolean collided = minecraft.thePlayer.isCollidedHorizontally;
		boolean collided1 = minecraft.thePlayer.isCollidedVertically;
		if(collided && ClimbingGloveEngine.activateClimbing) 
		{
			minecraft.thePlayer.motionY = 0.065555559;
		}
		if(ClimbingGloveEngine.activateClimbing && minecraft.thePlayer.isSneaking() && collided) 
		{
			minecraft.thePlayer.motionY = 0;
		}
		if(minecraft.thePlayer.getCurrentEquippedItem() != null && minecraft.thePlayer.getCurrentEquippedItem().itemID == ClimbingGloveEngine.climbingGloveOn.shiftedIndex)
		{
			ClimbingGloveEngine.activateClimbing = true;
		} else
		{
			ClimbingGloveEngine.activateClimbing = false;
		}

		/**
		 * Basically, this argument asks whether the player collides with the ceiling 
		 * while wearing the Climbing Helmet --if the player is, then the motion of the 
		 * player is a positive Y, effectively giving the 'stick to ceiling' effect
		 */
		if(minecraft.thePlayer.inventory.armorItemInSlot(3) != null)
		{
			ItemStack itemstack = minecraft.thePlayer.inventory.armorItemInSlot(3);
			if(itemstack.itemID == ClimbingGloveEngine.climbingHelmet.shiftedIndex)
			{
				int j1 = MathHelper.floor_double(minecraft.thePlayer.posX);
				int k3 = MathHelper.floor_double(minecraft.thePlayer.posY + 1.0D);
				int j5 = MathHelper.floor_double(minecraft.thePlayer.posZ);

				if(minecraft.theWorld.getBlockMaterial(j1, k3, j5).isSolid())
				{
					if(collided1)
					{
						minecraft.thePlayer.motionY = 1.0D;
					}
					if(collided1 && minecraft.thePlayer.isSneaking())
					{
						minecraft.thePlayer.motionY = -0.39999999999999998D;
					}

					minecraft.thePlayer.fallDistance = 0.0F;
				}
			}
		}

		/**
		 * When the player presses 'R', the HelmetLight entity is spawned = light!
		 */
		int i = (int)minecraft.thePlayer.posX;
		int j = (int)minecraft.thePlayer.posY;
		int k = (int)minecraft.thePlayer.posZ;
		if(ClimbingGloveEngine.trig)
		{
			ClimbingGloveEngine.keyDelay++; 
		}
		if(ClimbingGloveEngine.keyDelay > 100)
		{
			ClimbingGloveEngine.trig = false;
			ClimbingGloveEngine.keyDelay = 0; 
		}
		if(Keyboard.isKeyDown(19) && ClimbingGloveEngine.keyDelay == 0)
		{
			minecraft.theWorld.playSoundEffect((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.click", 1.0F, minecraft.theWorld.rand.nextFloat() * 0.1F + 0.9F);
			if(ClimbingGloveEngine.isOn)
			{
				ClimbingGloveEngine.isOn = false;
			} else
			{
				minecraft.theWorld.spawnEntityInWorld(new HelmetLight(minecraft.theWorld, minecraft.thePlayer));
				ClimbingGloveEngine.isOn = true; 
			}
			ClimbingGloveEngine.trig = true;
		}
		return true;
	}

	private void onTickInGUI(Minecraft minecraft, GuiScreen guiscreen) {}

	private void onRenderTick() {}

	@Override
	public EnumSet<TickType> ticks() 
	{
		//You must return an EnumSet.of();. You will pull an error if you return Null
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() 
	{
		return "ClimbingGloveEngine.ClientTickHandler";
	}

}
