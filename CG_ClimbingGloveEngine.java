package climbingglove; 

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import cpw.mods.fml.common.Mod; 
import cpw.mods.fml.common.Mod.Init; 
import cpw.mods.fml.common.event.FMLInitializationEvent; 
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@NetworkMod(clientSideRequired = true, serverSideRequired = false)
@Mod(modid = "CG", name = "Climbing Glove Mod", version = "0.8") 

public class CG_ClimbingGloveEngine
{
	/**
	 * Variables/Booleans
	 */
	private static Minecraft minecraft = ModLoader.getMinecraftInstance();
	public static boolean activateClimbing = false;
	private static boolean jumpKeyDown = false;
	public static boolean isOn;
	public int keyDelay;
	public boolean trig;

	/**
	 * Items/Block(s)
	 */
	public static final Item climbingGloveInactive = new CG_ClimbingGloveInactive(3000).setItemName("climbingGlove").setIconIndex(ModLoader.addOverride("/gui/items.png", "/mrarcane/climbingGloveInactive.png")).setCreativeTab(CreativeTabs.tabTools);
	public static final Item climbingGloveActive = new CG_ClimbingGloveActive(3001).setItemName("climbingGloveActive").setIconIndex(ModLoader.addOverride("/gui/items.png", "/mrarcane/climbingGloveActive.png"));
	public static final Item climbingHelmet = (new ItemArmor(692, EnumArmorMaterial.CLOTH, ModLoader.addArmor("climb"), 0)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/mrarcane/climbingHelmet.png")).setItemName("climbingHelmet");
	public static final Item minersHelmet = (new CG_MinerHelmet(693, EnumArmorMaterial.CLOTH, ModLoader.addArmor("mine"), 0)).setItemName("minersHelmet").setIconIndex(ModLoader.addOverride("/gui/items.png", "/mrarcane/minerHelmet.png"));
	public static final Block light = (new CG_Light(206, ModLoader.addOverride("/terrain.png", "/mrarcane/light.png"))).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setBlockName("light").setLightValue(1.0F);

	@Init
	public void InitiateCG_ClimbingGloveEngine(FMLInitializationEvent initEvent)
	{
		/** Integer values/booleans **/
		keyDelay = 0; 
		trig = false; 
		isOn = false;

		/** Name registry **/
		LanguageRegistry.addName(climbingGloveInactive, "Climbing Glove Inactive");
		LanguageRegistry.addName(climbingGloveActive, "Climbing Glove Active");
		LanguageRegistry.addName(climbingHelmet, "Climbing Helmet");
		LanguageRegistry.addName(minersHelmet, "Mining Helmet");
		LanguageRegistry.addName(light, "Light");

		/** Block registry **/
		GameRegistry.registerBlock(light);

		/** Recipes **/
		GameRegistry.addRecipe(new ItemStack(climbingGloveInactive, 1), new Object[] {"###", "#XX", "CCC", '#', Item.diamond, 'X', Item.redstone, 'C', Item.leather});
		GameRegistry.addRecipe(new ItemStack(climbingHelmet, 1), new Object[] {"XXX", "CVC", "ZZZ", 'X', Item.slimeBall, 'C', Item.ingotIron, 'V', Item.helmetLeather, 'Z', Item.redstone});
		GameRegistry.addRecipe(new ItemStack(minersHelmet, 1), new Object[] {"XCX", "XVX", 'X', Item.silk, 'C', Block.glowStone, 'V', Item.helmetLeather});
		//ModLoader.setInGameHook(this, true, true);
	}
	
	/**
	 *  Checks this code every tick that goes by in the game (1 tick = 0.1 seconds)
	 */
	public boolean onTickInGame(float f, Minecraft minecraft)
	{	
		boolean collided = minecraft.thePlayer.isCollidedHorizontally;
		boolean collided1 = minecraft.thePlayer.isCollidedVertically;

		if(collided && activateClimbing) {minecraft.thePlayer.motionY = 0.065555559;}
		if(activateClimbing && minecraft.thePlayer.isSneaking() && collided) {minecraft.thePlayer.motionY = 0;}
		EntityPlayer Player = ModLoader.getMinecraftInstance().thePlayer;
		if(Player.getCurrentEquippedItem() != null && Player.getCurrentEquippedItem().itemID == CG_ClimbingGloveEngine.climbingGloveActive.shiftedIndex)
		{
			activateClimbing = true;
		}else
		{
			activateClimbing = false;
		}

		/**
		 * Basically, this argument asks whether the player collides with the ceiling 
		 * while wearing the Climbing Helmet --if the player is, then the motion of the 
		 * player is a positive Y, effectively giving the 'stick to ceiling' effect
		 */
		if(minecraft.thePlayer.inventory.armorItemInSlot(3) != null)
		{
			ItemStack itemstack = minecraft.thePlayer.inventory.armorItemInSlot(3);
			if(itemstack.itemID == climbingHelmet.shiftedIndex)
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
		 * When the player presses 'R', the EntityHelmetLight is spawned = light!
		 */
		int i = (int)minecraft.thePlayer.posX;
		int j = (int)minecraft.thePlayer.posY;
		int k = (int)minecraft.thePlayer.posZ;
		if(trig)
		{
			keyDelay++; 
		}
		if(keyDelay > 100)
		{
			trig = false;
			keyDelay = 0; 
		}
		if(Keyboard.isKeyDown(19) && keyDelay == 0)
		{
			minecraft.theWorld.playSoundEffect((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.click", 1.0F, minecraft.theWorld.rand.nextFloat() * 0.1F + 0.9F);
			if(isOn)
			{
				isOn = false;
			} else
			{
				minecraft.theWorld.spawnEntityInWorld(new CG_HelmetLightEngine(minecraft.theWorld, minecraft.thePlayer));
				isOn = true; 
			}
			trig = true;
		}
		return true;
	}
}

