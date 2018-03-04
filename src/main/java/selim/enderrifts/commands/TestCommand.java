package selim.enderrifts.commands;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import selim.enderrifts.ModInfo;
import selim.enderrifts.world.TeleporterCreative;

public class TestCommand extends CommandBase {

	public TestCommand() {
		aliases = Lists.newArrayList(ModInfo.ID, "test");
	}

	private final List<String> aliases;

	@Override
	@Nonnull
	public String getName() {
		return "test";
	}

	@Override
	@Nonnull
	public String getUsage(@Nonnull ICommandSender sender) {
		return "test";
	}

	@Override
	@Nonnull
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender,
			@Nonnull String[] args) throws CommandException {
		if (!(sender instanceof EntityPlayerMP))
			return;
		EntityPlayerMP player = (EntityPlayerMP) sender;
		sender.sendMessage(new TextComponentString("dim: " + player.dimension));
		if (args.length == 2 && args[0].equals("dim")) {
			TeleporterCreative.changeDimension(player, Integer.valueOf(args[1]));
			// CustomTeleporter.teleportToDimension(player,
			// Integer.valueOf(args[1]), player.getPosition());
			sender.sendMessage(
					new TextComponentString("dim2: " + Minecraft.getMinecraft().player.dimension));
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	@Nonnull
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			@Nullable BlockPos targetPos) {
		return Collections.emptyList();
	}
}