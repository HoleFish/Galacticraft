package micdoodle8.mods.galacticraft.core.command;

import micdoodle8.mods.galacticraft.core.dimension.SpaceStationWorldData;
import micdoodle8.mods.galacticraft.core.entities.player.GCEntityPlayerMP;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CommandSpaceStationAddOwner extends CommandBase
{
    @Override
    public String getCommandUsage(ICommandSender var1)
    {
        return "/" + this.getCommandName() + " [player]";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return true;
    }

    @Override
    public String getCommandName()
    {
        return "ssinvite";
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        String var3 = null;
        EntityPlayerMP playerBase = null;

        if (astring.length > 0)
        {
            var3 = astring[0];

            try
            {
                playerBase = PlayerUtil.getPlayerBaseServerFromPlayerUsername(icommandsender.getCommandSenderName(), true);

                if (playerBase != null)
                {
                    GCPlayerStats stats = GCEntityPlayerMP.getPlayerStats(playerBase);

                    if (stats.spaceStationDimensionID <= 0)
                    {
                        throw new WrongUsageException("Could not find space station for your username, you need to travel there first!", new Object[0]);
                    }
                    else
                    {
                        final SpaceStationWorldData data = SpaceStationWorldData.getStationData(playerBase.worldObj, stats.spaceStationDimensionID, playerBase);

                        if (!data.getAllowedPlayers().contains(var3.toLowerCase()))
                        {
                            data.getAllowedPlayers().add(var3.toLowerCase());
                            data.markDirty();
                        }
                    }

                    final EntityPlayerMP playerToAdd = PlayerUtil.getPlayerBaseServerFromPlayerUsername(var3, true);

                    if (playerToAdd != null)
                    {
                        playerToAdd.addChatMessage(new ChatComponentText("You've been added to " + playerBase.getGameProfile().getName() + "\'s Space Station accepted players!"));
                    }
                }
            }
            catch (final Exception var6)
            {
                throw new CommandException(var6.getMessage(), new Object[0]);
            }

        }
        else
        {
            throw new WrongUsageException("Not enough command arguments! Usage: " + this.getCommandUsage(icommandsender), new Object[0]);
        }

        if (playerBase != null)
        {
            playerBase.addChatMessage(new ChatComponentText("Successfully added " + var3 + " to Space Station list!"));
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getPlayers()) : null;
    }

    protected String[] getPlayers()
    {
        return MinecraftServer.getServer().getAllUsernames();
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }
}
