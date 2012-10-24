package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;

public class mod_ClimbingGlove extends BaseMod
{
	/**
	 * Variables/Booleans
	 */
	//public static final Achievement climb = new Achievement(5000, "climb", 4, 9, mod_ClimbingGlove.climbingGloveActive, null).registerAchievement();
	private static Minecraft minecraft = ModLoader.getMinecraftInstance();
	public static boolean activateClimbing = false;
	private static boolean jumpKeyDown = false;
	public static boolean isOn;
	public int keyDelay;
	public boolean trig;

	/**
	 * Items 
	 */
	public static final Item climbingGloveInactive = new CG_ItemClimbingGloveInactive(3000).setItemName("climbingGlove").setIconIndex(ModLoader.addOverride("/gui/items.png", "/mrarcane/climbingGloveInactive.png")).setTabToDisplayOn(CreativeTabs.tabTools);
	public static final Item climbingGloveActive = new CG_ItemClimbingGloveActive(3001).setItemName("climbingGloveActive").setIconIndex(ModLoader.addOverride("/gui/items.png", "/mrarcane/climbingGloveActive.png"));
	public static final Item climbingHelmet = (new ItemArmor(692, EnumArmorMaterial.CLOTH, ModLoader.addArmor("climb"), 0)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/mrarcane/climbingHelmet.png")).setItemName("climbingHelmet");
	public static final Item minersHelmet = (new CG_ItemMinerHelmet(693, EnumArmorMaterial.CLOTH, ModLoader.addArmor("mine"), 0)).setItemName("minersHelmet").setIconIndex(ModLoader.addOverride("/gui/items.png", "/mrarcane/minerHelmet.png"));
	//public static final Item diamondTracker = new CG_ItemOreTracker(3002).setItemName("diamomdTracker").setIconIndex(ModLoader.addOverride("/gui/items.png", "/mrarcane/tracker.png")).setTabToDisplayOn(CreativeTabs.tabTools);

	/** 
	 * Block(s)
	 */
	public static final Block light = (new CG_BlockLight(206, ModLoader.addOverride("/terrain.png", "/mrarcane/light.png"))).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setBlockName("light").setLightValue(1.0F);

	public void load()
	{
		keyDelay = 0; 
		trig = false; 
		isOn = false;
		addName();
		addRecipes();
		ModLoader.setInGameHook(this, true, true);
		ModLoader.registerBlock(light);
		//ModLoader.addAchievementDesc(climb, "Crafted Climbing Glove", "You're Spider-Man!");
	}

	public void takenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory)
	{
		if(itemstack.itemID == mod_ClimbingGlove.climbingGloveInactive.shiftedIndex)
		{
			//entityplayer.addStat(climb, 1);
		}
	}

	/**
	 * The in-game names of the items
	 */
	private static void addName()
	{
		ModLoader.addName(climbingGloveInactive, "Climbing Glove Inactive");
		ModLoader.addName(climbingGloveActive, "Climbing Glove Active");
		ModLoader.addName(climbingHelmet, "Climbing Helmet");
		ModLoader.addName(minersHelmet, "Mining Helmet");
		ModLoader.addName(light, "Light");
		//ModLoader.addName(diamondTracker, "Diamond Tracking Device");
	}

	/**
	 * The item crafting recipes
	 */
	private static void addRecipes() 
	{
		ModLoader.addRecipe(new ItemStack(climbingGloveInactive, 1), new Object[] {"###", "#XX", "CCC", '#', Item.diamond, 'X', Item.redstone, 'C', Item.leather});
		ModLoader.addRecipe(new ItemStack(climbingHelmet, 1), new Object[] {"XXX", "CVC", "ZZZ", 'X', Item.slimeBall, 'C', Item.ingotIron, 'V', Item.helmetLeather, 'Z', Item.redstone});
		ModLoader.addRecipe(new ItemStack(minersHelmet, 1), new Object[] {"XCX", "XVX", 'X', Item.silk, 'C', Block.glowStone, 'V', Item.helmetLeather});
		//ModLoader.addRecipe(new ItemStack(diamondTracker, 1), new Object[] {"XXX", "CCC", 'X', Item.redstone, 'C', Item.ingotIron});
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
		if(Player.getCurrentEquippedItem() != null && Player.getCurrentEquippedItem().itemID == mod_ClimbingGlove.climbingGloveActive.shiftedIndex)
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
				minecraft.theWorld.spawnEntityInWorld(new EntityHelmetLight(minecraft.theWorld, minecraft.thePlayer));
				isOn = true; 
			}
			trig = true;
		}
		return true;
	}

	/**
	 * Just something ModLoader uses to identify the version of the mod
	 */
	public String getVersion()
	{
		return "1.3.2";
	}

	/** 
	 * The Author identifier (it's not really necessary, but I like putting it anyway) 
	 */
	public String getAuthor()
	{
		return "MrArcane111 of course!";
	}
}
