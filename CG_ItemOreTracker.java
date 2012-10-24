package net.minecraft.src;

public class CG_ItemOreTracker extends Item
{	
	public CG_ItemOreTracker(int i) 
	{
		super(i);
		setMaxStackSize(1);
	}

	/** What happens when the item is right-clicked **/
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		int i = 0;
		int j = 0;
		int k = 0;
		int oreCheck = oreCheck(world, i, j, k);
		entityplayer.addChatMessage(oreCheck >= 6 ? "Finding Ores" : "Heating");
		return itemstack;
	}

	private int oreCheck(World world, int i, int j, int k)
	{
		int counter = 0;
		for(int i1 = i - 1; i1 <= i + 1; i1++)
		{
			for(int j1 = j - 1; j1 <= j+1; j1++)
			{
				for(int k1 = k - 1; k1 <= k + 1; k1++)
				{
					int id = world.getBlockId(i1, j1, k1);
					int meta = world.getBlockMetadata(i1, j1, k1);
					if((id == 11 || id == 10) && meta == 0)
					{
						if((i1 == i && j1 == j) || (j1 == j && k1 == k) || (k1 == k && i1 == i))
						{
							counter += 3;
						}
						else
						{
							counter++;
						}
					} else if(id == 9 || id == 8)
					{
						return -1;
					}
				}
			}
		}
		return counter;
	}
}
