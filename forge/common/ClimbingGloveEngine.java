package glovemod.common; 

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import java.io.File;
import net.minecraft.src.*;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;

@NetworkMod(clientSideRequired = true, serverSideRequired = false)
@Mod(modid = "MrArcane111_ClimbingGlove", name = "Climbing Glove Mod", version = "0.8") 

public class ClimbingGloveEngine
{
	@Instance("MrArcane111_ClimbingGlove")
	public ClimbingGloveEngine instance;
	
	/** Integers/booleans **/
	public static float lightBrightness;
	public static boolean activateClimbing = false;
	private static boolean jumpKeyDown = false;
	public static boolean isOn;
	public static int keyDelay;
	public static boolean trig;
	
	/**
	 * Item IDs/Block IDs 
	 */
	public static int LightID;
	public static Block Light;
	public static int climbingGloveOnID;
	public static Item climbingGloveOn;
	public static int climbingGloveOffID;
	public static Item climbingGloveOff;
	public static int climbingHelmetID;
	public static Item climbingHelmet;
	public static int miningHelmetID;
	public static Item miningHelmet;

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			config.load();
			LightID = Integer.parseInt(config.getBlock("Light Block ID", 2000).value);
			climbingGloveOnID = Integer.parseInt(config.getItem("Active Climbing Glove ID", 2001).value);
			climbingGloveOffID = Integer.parseInt(config.getItem("Inactive Climbing Glove ID", 2002).value);
			climbingHelmetID = Integer.parseInt(config.getItem("Climbing Helmet ID", 2003).value);
			miningHelmetID = Integer.parseInt(config.getItem("Mining Helmet ID", 2004).value);
			lightBrightness = Float.parseFloat(config.get("Light Brightness", "lightBrightness", 1).value);
		} catch(Exception e)
		{
			e.printStackTrace();
		} finally
		{
			config.save();
		}
	}
	
	@Init
	public void init(FMLInitializationEvent event)
	{
		/** Integer values/booleans **/
		keyDelay = 0; 
		trig = false; 
		isOn = false;
		
		/** Items/Blocks **/
		Light = new Light(LightID, 4).setBlockName("Light");
		climbingGloveOn = new ClimbingGloveActive(climbingGloveOnID).setItemName("climbingGloveOn").setIconCoord(0, 0);
		climbingGloveOff = new ClimbingGloveInactive(climbingGloveOffID).setItemName("climbingGloveOff").setIconCoord(1, 0);
		climbingHelmet = new ClimbingHelmet(climbingHelmetID, EnumArmorMaterial.IRON, 3, 0).setItemName("climbingHelmet").setIconCoord(2, 0);
		miningHelmet = new MiningHelmet(miningHelmetID, EnumArmorMaterial.IRON, 3, 0).setItemName("miningHelmet").setIconCoord(3, 0);
		
		/** Names **/
		GameRegistry.registerBlock(Light);
		LanguageRegistry.addName(Light, "Light");
		LanguageRegistry.addName(climbingGloveOn, "Climbing Glove Active");
		LanguageRegistry.addName(climbingGloveOff, "Climbing Glove Inactive");
		LanguageRegistry.addName(climbingHelmet, "Climbing Helmet");
		LanguageRegistry.addName(miningHelmet, "Mining Helmet");
		
		/** Recipes **/
		GameRegistry.addRecipe(new ItemStack(climbingGloveOff, 1), new Object[] {"###", "#XX", "CCC", '#', Item.diamond, 'X', Item.redstone, 'C', Item.leather});
		GameRegistry.addRecipe(new ItemStack(climbingHelmet, 1), new Object[] {"XXX", "CVC", "ZZZ", 'X', Item.slimeBall, 'C', Item.ingotIron, 'V', Item.helmetSteel, 'Z', Item.redstone});
		GameRegistry.addRecipe(new ItemStack(miningHelmet, 1), new Object[] {"XCX", "XVX", 'X', Item.silk, 'C', Block.glowStone, 'V', Item.helmetSteel});
		
		/** Tick Handler **/
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
	}
}

