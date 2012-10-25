package climbingglove;

import net.minecraft.src.*;
import java.util.Random;

public class CG_Light extends Block
{
	/** 
	 * Some simple block bounds like i is the item ID and the material determines
	 * what sound to play when it is stepped on and such.						
	 */
	protected CG_Light(int i, int j)
    {
        super(i, j, Material.wood);
        
    }

	/** 
	 * This method allows the player to pass through the block because it removes the
	 * bounding box that is placed around every object. (i.e. flowers)			 
	 */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    /**
     * Is this block opaque and/or a full 1m cube? This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc. to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    /**
     * Determines whether the block will render as an ordinary block (i.e. signs, buttons, stairs, etc.)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    /** 
     * Determines the render type.
     */
    public int getRenderType()
    {
        return 2;
    }

    /**
     * Creates a vector around the player that places the "Light" block wherever the player moves.
     */
    public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3 vec3d, Vec3 vec3d1)
    {
        int l = world.getBlockMetadata(i, j, k) & 7;
        float f = 0.0F;
        if(l == 1)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F - f, f * 0.0F, 0.0F, 0.0F + f);
        } else
        if(l == 2)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F - f, f * 0.0F, 0.0F, 0.0F + f);
        } else
        if(l == 3)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F - f, f * 0.0F, 0.0F, 0.0F + f);
        } else
        if(l == 4)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F - f, f * 0.0F, 0.0F, 0.0F + f);
        } else
        {
            float f1 = 0.05F;
            setBlockBounds(0.0F, 0.0F, 0.0F - f, f * 0.0F, 0.0F, 0.0F + f);
        }
        
        return super.collisionRayTrace(world, i, j, k, vec3d, vec3d1);
    }
    
    /**
     * Usually called for a block like a torch, this method tells the game to update a tick and run
     * a certain set of parameters. (i.e. like the torch smoke) 1 Tick = 0.10 seconds. 
     */
    public void updateTick(World world, int i, int j, int k, Random random)
    {
		super.updateTick(world, i, j, k, random);
		removeIfLightOff(world, i, j, k);
    }
	
    /**
     * During any one of those ticks where the update tick was applicable, this tells the game 
     * to run a certain set of parameters. The parameters for this method is the method I had 
     * to code myself called "removeIfLightOff".
     */
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
		super.randomDisplayTick(world, i, j, k, random);
		removeIfLightOff(world, i, j, k);
    }
	
	/**
	 * It the 'R' key was pressed, disabling the light, then the "Light" block will be removed
	 * from its spot on the ground. A nifty little creation of mine!
	 */
	public void removeIfLightOff(World world, int i, int j, int k)
	{
		EntityPlayer player = ModLoader.getMinecraftInstance().thePlayer;
		if(!CG_ClimbingGloveEngine.isOn)
		{
			world.setBlockWithNotify(i, j, k, 0);
		}
	}
    
	/**
	 * If the block is broken it won't drop anything. In the mod, this block isn't to be used like
	 * all the other blocks, anyway.
	 */
    public int idDropped(int i, Random random)
    {
        return 0;
    }
}

