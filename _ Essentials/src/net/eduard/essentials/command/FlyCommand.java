
package net.eduard.essentials.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class FlyCommand extends CommandManager {
	public String messageOn = "�6Voce pode voar!";
	public String messageOff = "�6Voce n�o pode mais voar!";
	public String messageTarget = "�6Voce troco o modo de voo do jogador $player";

	public FlyCommand() {
		super("fly");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (Mine.onlyPlayer(sender)) {
				Player p = (Player) sender;
				if (p.getAllowFlight()) {
					p.setFlying(false);
					p.setAllowFlight(false);
					p.sendMessage(messageOff);

				} else {
					p.setAllowFlight(true);
					sender.sendMessage(messageOn);
				}
			}
		} else {
			String player = args[0];
			if (Mine.existsPlayer(sender, player)) {
				Player target = Bukkit.getPlayer(player);
				sender.sendMessage(messageTarget.replace("$player", target.getName()));

			}
		}

		return true;
	}

}
