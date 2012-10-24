package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityHelmetLight extends Entity
{
	/**
	 * Some simple parameters for the Entity.class extension. 
	 */
    public EntityHelmetLight(World world1)
    {
        super(world1);
        world = world1;
        setSize(0.0F, 0.0F);
    }

    /**
     * More parameters
     */
    public EntityHelmetLight(World world1, EntityPlayer entityplayer1)
    {
        this(world1);
        entityplayer = entityplayer1;
        i = (int)entityplayer1.posX;
        j = (int)entityplayer1.posY;
        k = (int)entityplayer1.posZ;
        setPosition(i, j, k);
    }

    protected void entityInit() {}

    /**
     * Returns if this entity triggers Block.onEntityWalking on the blocks they walk on. Used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking() {return false;}

    /**
     * Returns true if other Entities should be prevented 
     * from moving through this Entity
     */
    public boolean canBeCollidedWith() {return !isDead;}

    /**
     * What happens to the entity on the update of a tick
     * (Called to update the entity's position/logic)
     */
    public void onUpdate()
    {
        if(!mod_ClimbingGlove.isOn)
        {
        	mod_ClimbingGlove.isOn = false;
            setDead();
        }
        ItemStack itemstack = entityplayer.inventory.armorInventory[3];
        if(itemstack == null)
        {
            setDead();
        }
        if(itemstack.itemID != mod_ClimbingGlove.minersHelmet.shiftedIndex)
        {
            setDead();
        } else
        if(itemstack.itemID == mod_ClimbingGlove.minersHelmet.shiftedIndex)
        {
            update(entityplayer);
            if(world.getBlockId(i1, j1, k1) == 206) //Block 206 is the light block item ID in the mod_ file.
            {
                world.setBlockWithNotify(i1, j1, k1, 0);
            }
            if(world.getBlockId(i, j, k) == 0)
            {
                world.setBlockWithNotify(i, j, k, 206); //Block 206 is the light block item ID in the mod_ file.
            }
            i1 = (int)entityplayer.posX; //posX
            j1 = (int)entityplayer.posY;
            k1 = (int)entityplayer.posZ;
        } else
        {
            mod_ClimbingGlove.isOn = false;
            setDead();
        }
    }

    /**
     * Tells the game to update which initiates the methods
     */
    public void update(EntityPlayer entityplayer1)
    {
        i = (int)entityplayer1.posX;
        j = (int)entityplayer1.posY;
        k = (int)entityplayer1.posZ;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT
     */
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT
     */
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {}

    public float getShadowSize() {return 0.0F;}

    public World world;
    public EntityPlayer entityplayer;
    public int i;
    public int j;
    public int k;
    public int i1;
    public int j1;
    public int k1;
    public boolean helmet;
}
