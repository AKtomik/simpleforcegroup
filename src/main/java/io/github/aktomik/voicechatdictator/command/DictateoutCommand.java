package io.github.aktomik.voicechatdictator.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.aktomik.voicechatdictator.VoicechatCop;
import io.github.aktomik.voicechatdictator.brigadier.BrigadierCommand;
import io.github.aktomik.voicechatdictator.brigadier.BrigadierToolbox;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class DictateoutCommand extends BrigadierCommand {

	VoicechatCop cop;
	JavaPlugin plugin;
	public DictateoutCommand(JavaPlugin plugin, VoicechatCop cop)
	{
		this.plugin = plugin;
		this.cop = cop;
	}

	@Override
	public String name() {
		return "dictateout";
	}

	@Override
	public String permission() {
		return null;
	}

	@Override
	public String description() {
		return "kicking out players from their voicechat group";
	}

	@Override
	public List<String> aliases() {
		return List.of("groupkick", "kickgroup");
	}

	@Override
	public LiteralArgumentBuilder<CommandSourceStack> root() {
		return base()
		.then(Commands.argument("players", ArgumentTypes.players())
		.executes(exe));
	}

	Command<CommandSourceStack> exe = ctx -> {
		final List<Player> players = BrigadierToolbox.resolvePlayers(ctx);
		final CommandSender sender = ctx.getSource().getSender();

		if (!cop.isAvailable())
		{
			sender.sendRichMessage("<red>cop dictator plugin not ready, something went wrong in init.");
			return Command.SINGLE_SUCCESS;
		}

		for (Player player : players)
		{
			sender.sendRichMessage("kicked out <b><target></b> from their group.", Placeholder.parsed("target", player.getName()));
			cop.removePlayerGroup(player);
		}

		return Command.SINGLE_SUCCESS;
	};
}
