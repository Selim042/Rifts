package selim.enderrifts;

import net.minecraft.block.BlockContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

// TODO work the new location cache stuff into this
public class TeleporterDungeon extends Teleporter
{
	// the world the player is being teleported to
	protected BlockPos basePos;	// location of portal base in blockspace
    
    public TeleporterDungeon(WorldServer worldIn, BlockPos basePos)
    {
        super(worldIn);
        this.basePos = basePos;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
        if (!this.placeInExistingPortal(entityIn, rotationYaw))
        {
            this.makePortal(entityIn);
            this.placeInExistingPortal(entityIn, rotationYaw);
        }
    }

    // dungeon portals use exact locations
    @Override
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
    {
            double spawnX = (double)this.basePos.getX() + 0.5D;
            double spawnZ = (double)this.basePos.getZ() + 0.5D;
            double spawnY = (double)this.basePos.getY();
            
            if (entityIn instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP)entityIn).connection.setPlayerLocation(spawnX, spawnY, spawnZ, entityIn.rotationYaw, entityIn.rotationPitch);
            }
            else
            {
                entityIn.setLocationAndAngles(spawnX, spawnY, spawnZ, entityIn.rotationYaw, entityIn.rotationPitch);
            }

            return true;
    }

    // couldn't find an existing portal, so look for a reasonable place to create one and do that
    @Override
    public boolean makePortal(Entity entity)
    {
        //int i = 16;
        double candidateDistance = -1.0D;
        int playerX = MathHelper.floor(entity.posX);
        int playerY = MathHelper.floor(entity.posY);
        int playerZ = MathHelper.floor(entity.posZ);
        //int checkX = playerX;
        //int checkY = playerY;
        //int checkZ = playerZ;
        //int l1 = 0;
        //int i2 = this.random.nextInt(4);
        for (int xOff=-1; xOff<=1; xOff++)	//x offset
        {
        	for (int zOff=-1; zOff<=1; zOff++)	//y offset
        	{
        		if (xOff == 0 && zOff == 0)
        		{
        			world.setBlockState(this.basePos, Blocks.AIR.getDefaultState(), 3);
        		}
        		else
        		{
        			// remove blocks in this column
        			for (int yOff=-1; yOff<=1; yOff++)
        			{
        				BlockPos newPos = this.basePos.add(xOff, yOff, zOff);
        				// replace ANY block except tileentities
        				if (!(this.world.getBlockState(newPos) instanceof BlockContainer))
        				{
        					this.world.setBlockToAir(newPos);
        				}
        			}
        		}
        	}
        }

        return true;
    }
}
