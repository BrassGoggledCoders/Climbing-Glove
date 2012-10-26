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
	private static Minecraft minecraft = Minecraft.getMinecraft();
	public static boolean activateClimbing = false;
	private static boolean jumpKeyDown = false;
	public static boolean isOn;
	public int keyDelay;
	public boolean trig;

	//Populates this Proxy field with an instance of either the Server Proxy or the Client Proxy based on what side the code is being run on
	@SidedProxy(clientSide = "climbingglove.ClientProxy", serverSide = "climbingglove.CommonProxy")
	public static CommonProxy proxy;
	
	//Populates this instance field with an instance of your Mod File
	@Instance("CG")
	public CG_ClimbingGloveEngine INSTANCE;

	/**
	 * Items/Block(s)
	 */
	public static final Item climbingGloveInactive = new CG_ClimbingGloveInactive(3000).setItemName("climbingGlove").setIconIndex(1).setCreativeTab(CreativeTabs.tabTools);
	public static final Item climbingGloveActive = new CG_ClimbingGloveActive(3001).setItemName("climbingGloveActive").setIconIndex(2);
	public static final Item climbingHelmet = (new ItemArmor(692, EnumArmorMaterial.CLOTH, 0)).setIconIndex(3).setItemName("climbingHelmet");
	public static final Item minersHelmet = (new CG_MinerHelmet(693, EnumArmorMaterial.CLOTH, 0)).setItemName("minersHelmet").setIconIndex(4);
	public static final Block light = (new CG_Light(206, 5)).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setBlockName("light").setLightValue(1.0F);

	public static CG_ClimbingGloveEngine getModInstance()
	{
		return INSTANCE;
	}

	@Init
	public void InitiateCG_ClimbingGloveEngine(FMLInitializationEvent initEvent)
	{
		INSTANCE = this;		

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
		//Get rid of this hook method completely, we don't need these with Forge. 


		//Registers the tick handlers with the TickRegistry HashSet for iteration and invocation later
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
		TickRegistry.registerTickHandler(new ServerTickHandler(), Side.SERVER);
	}
	
	/**
	 *  Checks this code every tick that goes by in the game (1 tick = 0.1 seconds)
	 */

	/* We're going to be slowly converting this to the new Forge methods over the next few days. 
	public boolean onTickInGame(float f, Minecraft minecraft)
	{	
		boolean collided = minecraft.thePlayer.isCollidedHorizontally;
		boolean collided1 = minecraft.thePlayer.isCollidedVertically;

		if(collided && activateClimbing) {minecraft.thePlayer.motionY = 0.065555559;}
		if(activateClimbing && minecraft.thePlayer.isSneaking() && collided) {minecraft.thePlayer.motionY = 0;}
		EntityPlayer Player = minecraft.thePlayer;
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
		/*
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
	}*/
}

