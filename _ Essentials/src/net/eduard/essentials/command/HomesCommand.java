package net.eduard.essentials.command;

import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.config.Config;
import net.eduard.api.lib.manager.CommandManager;

public class HomesCommand extends CommandManager {
	public HomesCommand() {
		super("homes");

	}
	public Config config = new Config("homes.yml");
	public String message = "�6Suas homes: �e$homes";
	public String messageError = "�cVoc� n�o possui nenhuma home!";
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;

			Set<String> list = config.getKeys(p.getUniqueId().toString());
			if (list.size() == 0) {
				sender.sendMessage( messageError);
			} else {
				StringBuilder builder = new StringBuilder();
				int id = 0;
				for (String sec : list) {
					if (id != 0) {
						builder.append(", ");
					}
					id++;
					builder.append(sec);
				}
				sender.sendMessage( message.replace("$homes", builder.toString()));
			}

		}

		return true;
	}
}
