package glovemod.common;

import net.minecraft.src.*;
import net.minecraftforge.common.IArmorTextureProvider;

public class MiningHelmet extends ItemArmor implements IArmorTextureProvider
{
	public EntityPlayer entityplayer;
	public static boolean isOn;

	/**
	 * Some simple things to initialize when the game is started. All of these are
	 * needed since the item extends the ItemArmor.class.
	 */
	public MiningHelmet(int i, EnumArmorMaterial j, int k, int l)
	{
		super(i, j, k, l);
		isOn = false; // A boolean that, by default, is false until the player presses 'R'
	}
	
	public String getTextureFile()
	{
		return "/glove/sprites/climbingglove_sprites.png";
	}
	
	public String getArmorTextureFile(ItemStack itemstack)
	{
		return "/glove/armor/mine_1.png";
	}
}

