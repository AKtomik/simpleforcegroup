package io.github.aktomik.voicechatdictator.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.maxhenkel.voicechat.api.Group;
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

public class DictateinCommand extends BrigadierCommand {

	VoicechatCop cop;
	JavaPlugin plugin;
	public DictateinCommand(JavaPlugin plugin, VoicechatCop cop)
	{
		this.plugin = plugin;
		this.cop = cop;
	}

	@Override
	public String name() {
		return "dictatein";
	}

	@Override
	public String permission() {
		return null;
	}

	@Override
	public String description() {
		return "adding in players to a voicechat group";
	}

	@Override
	public List<String> aliases() {
		return List.of("groupjoin", "joingroup");
	}

	@Override
	public LiteralArgumentBuilder<CommandSourceStack> root() {
		return base()
		.then(Commands.argument("players", ArgumentTypes.players())
		.then(Commands.argument("group", StringArgumentType.word())
		.suggests((ctx, builder) -> {
			cop.getGroups().forEach(group -> builder.suggest(group.getName()));
			return builder.buildFuture();
		})
		.executes(exe)));
	}

	Command<CommandSourceStack> exe = ctx -> {
		final List<Player> players = BrigadierToolbox.resolvePlayers(ctx);
		final String groupName = ctx.getArgument("group", String.class);
		final CommandSender sender = ctx.getSource().getSender();

		if (!cop.isAvailable())
		{
			sender.sendRichMessage("<red>cop dictator plugin not ready, something went wrong in init.");
			return Command.SINGLE_SUCCESS;
		}

		Group group = cop.getGroup(groupName);
		if (group == null)
		{
			sender.sendRichMessage("<red>group <u><group></u> does not exist.", Placeholder.parsed("group", groupName));
			return Command.SINGLE_SUCCESS;
		}

		for (Player player : players)
		{
			sender.sendRichMessage("added in <b><target></b> to the group <u><group></u>.", Placeholder.parsed("target", player.getName()), Placeholder.parsed("group", group.getName()));
			cop.addPlayerToGroup(player, group);
		}

		return Command.SINGLE_SUCCESS;
	};
}
