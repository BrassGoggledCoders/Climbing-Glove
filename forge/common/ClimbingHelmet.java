package glovemod.common;

import net.minecraft.src.*;
import net.minecraftforge.common.IArmorTextureProvider;

public class ClimbingHelmet extends ItemArmor implements IArmorTextureProvider
{
	/**
	 * Some simple things to initialize when the game is started. All of these are
	 * needed since the item extends the ItemArmor.class.
	 */
	public ClimbingHelmet(int i, EnumArmorMaterial j, int k, int l)
	{
		super(i, j, k, l);
	}

	public String getTextureFile()
	{
		return "/glove/sprites/climbingglove_sprites.png";
	}

	public String getArmorTextureFile(ItemStack itemstack)
	{
		return "/glove/armor/climb_1.png";
	}
}

