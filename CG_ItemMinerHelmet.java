package net.minecraft.src;

public class CG_ItemMinerHelmet extends ItemArmor
{
	/**
	 * Some simple things to initialize when the game is started. All of these are
	 * needed since the item extends the ItemArmor.class.
	 */
    public CG_ItemMinerHelmet(int i, EnumArmorMaterial j, int k, int l)
    {
        super(i, j, k, l);
        isOn = false; // A Boolean that, by default, is false until the player presses 'R'.
    }

    public EntityPlayer entityplayer;
    public static boolean isOn;
}
