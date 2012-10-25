package climbingglove;

import net.minecraft.src.*;

public class CG_MinerHelmet extends ItemArmor
{
	/**
	 * Some simple things to initialize when the game is started. All of these are
	 * needed since the item extends the ItemArmor.class.
	 */
    public CG_MinerHelmet(int i, EnumArmorMaterial j, int k, int l)
    {
        super(i, j, k, l);
        isOn = false; // A boolean that, by default, is false until the player presses 'R'
    }

    public EntityPlayer entityplayer;
    public static boolean isOn;
}

