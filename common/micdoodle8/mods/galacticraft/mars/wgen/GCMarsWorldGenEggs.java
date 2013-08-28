package micdoodle8.mods.galacticraft.mars.wgen;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GCMarsWorldGenEggs extends WorldGenerator
{
    private int eggBlockID;
    private int metadata;

    public GCMarsWorldGenEggs(int eggID, int metadata)
    {
        this.eggBlockID = eggID;
        this.metadata = metadata;
    }

    public boolean generate(World par1World, Random par2Random, int x, int y, int z)
    {
        int i1 = x + par2Random.nextInt(8) - par2Random.nextInt(8);
        int j1 = y + par2Random.nextInt(4) - par2Random.nextInt(4);
        int k1 = z + par2Random.nextInt(8) - par2Random.nextInt(8);

        if (par1World.isAirBlock(i1, j1, k1) && (!par1World.provider.hasNoSky || j1 < 127) && Block.blocksList[this.eggBlockID].canBlockStay(par1World, i1, j1, k1))
        {
            par1World.setBlock(i1, j1, k1, this.eggBlockID, this.metadata, 2);
        }

        return true;
    }
}
