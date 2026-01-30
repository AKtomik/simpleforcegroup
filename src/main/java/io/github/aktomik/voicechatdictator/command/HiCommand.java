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
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class HiCommand extends BrigadierCommand {

	VoicechatCop chat;
	JavaPlugin plugin;
	public HiCommand(JavaPlugin plugin, VoicechatCop voiceChatPlugin)
	{
		this.plugin = plugin;
		chat = voiceChatPlugin;
	}

	@Override
	public String name() {
		return "hi";
	}

	@Override
	public String permission() {
		return null;
	}

	@Override
	public String description() {
		return "say hi";
	}

	@Override
	public List<String> aliases() {
		return List.of();
	}

	@Override
	public LiteralArgumentBuilder<CommandSourceStack> root() {
		return base()
		.then(Commands.argument("players", ArgumentTypes.players())
		.executes(exe));
	}

	Command<CommandSourceStack> exe = (ctx) -> {
		final List<Player> players = BrigadierToolbox.resolvePlayers(ctx);

		for (Player player : players)
		{
			player.sendRichMessage("<yellow>hi <you>! <red>kicked out of your group.", Placeholder.parsed("you", player.getName()));
			chat.removePlayerGroup(player);
		}

		return Command.SINGLE_SUCCESS;
	};
}
