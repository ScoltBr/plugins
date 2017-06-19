package br.com.piracraft.lobby2.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.piracraft.api.util.MySQL;
import br.com.piracraft.lobby2.Main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;

/**
 * Api relacionado a coisas que nao usamos mais
 * @author Eduard-PC
 *
 */
public class Falhas {
	
	

	public static void spawnNpcs() {
		try {
			setarNpcs();
			new BukkitRunnable() {
				@Override
				public void run() {
					new SpawnNPC(npc.get(NPC.Factions).getLocation(),
							npc.get(NPC.Factions).getNome(),
							npc.get(NPC.Factions).getSkin()).Spawn();
					new SpawnNPC(npc.get(NPC.HardCore).getLocation(),
							npc.get(NPC.HardCore).getNome(),
							npc.get(NPC.HardCore).getSkin()).Spawn();
					new SpawnNPC(npc.get(NPC.KitPvp).getLocation(),
							npc.get(NPC.KitPvp).getNome(),
							npc.get(NPC.KitPvp).getSkin()).Spawn();
					new SpawnNPC(npc.get(NPC.SeteDias).getLocation(),
							npc.get(NPC.SeteDias).getNome(),
							npc.get(NPC.SeteDias).getSkin()).Spawn();
					new SpawnNPC(npc.get(NPC.SkyWars).getLocation(),
							npc.get(NPC.SkyWars).getNome(),
							npc.get(NPC.SkyWars).getSkin()).Spawn();
					LobbyAPI.enviarAlertaConsole("Aviso", "Spawnando npcs.",
							Cor.Verde);

				}
			}.runTaskTimer(Main.getPlugin(), 20, 5 * 60 * 20);
		} catch (Exception e) {
			LobbyAPI.enviarAlertaConsole("ERRO", e.getMessage(), Cor.Vermelho);
		}
	}
	public static void setarNpcs() {
		try {
			npc.put(NPC.Factions, new MySQLNPC(NPC.Factions));
			npc.put(NPC.HardCore, new MySQLNPC(NPC.HardCore));
			npc.put(NPC.KitPvp, new MySQLNPC(NPC.KitPvp));
			npc.put(NPC.SeteDias, new MySQLNPC(NPC.SeteDias));
			npc.put(NPC.SkyWars, new MySQLNPC(NPC.SkyWars));
		} catch (Exception e) {
			LobbyAPI.enviarAlertaConsole("ERRO", e.getMessage(), Cor.Vermelho);
		}
	}
	public static class MySQLNPC {

		private Location location;
		private String nome;
		private String skin;

		public MySQLNPC(NPC npc) {
			new BukkitRunnable() {
				@Override
				public void run() {
					try {
						ResultSet resultSet = MySQL.conectar().createStatement()
								.executeQuery(
										"SELECT * FROM `V_NPC_LOCALIZACAO` WHERE `NOMEMINIGAME` = '"
												+ String.valueOf(npc) + "'");
						if (resultSet.next()) {
							setLocation(new Location(Bukkit.getWorld("world"),
									Double.valueOf(resultSet.getString("COORDENADA")
											.split(",")[0]),
									Double.valueOf(resultSet.getString("COORDENADA")
											.split(",")[1]),
									Double.valueOf(resultSet.getString("COORDENADA")
											.split(",")[2]),
									Float.valueOf(resultSet.getString("COORDENADA")
											.split(",")[3]),
									Float.valueOf(resultSet.getString("COORDENADA")
											.split(",")[4])));
							setNome(resultSet.getString("NOME"));
							setSkin(resultSet.getString("SKIN"));
						}
						resultSet.getStatement().getConnection().close();

					} catch (SQLException e) {
						LobbyAPI.enviarAlertaConsole("Mysq", e.getMessage(),
								Cor.Vermelho);
					}
				}
			}.runTaskAsynchronously(Main.getPlugin());
		}

	
		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getSkin() {
			return skin;
		}

		public void setSkin(String skin) {
			this.skin = skin;
		}

	}
	public static enum NPC {
		SkyWars, Factions, KitPvp, HardCore, SeteDias;
	}
	public static HashMap<NPC, MySQLNPC> npc = new HashMap<>();
	public static class SpawnNPC {

		private Location loc;
		private String nick;
		private String skin;

		public SpawnNPC(Location loc, String nick, String skin) {
			setLoc(loc);
			setNick(nick);
			setSkin(skin);
		}

		public void Spawn() {
			NPCRegistry registry = CitizensAPI.getNPCRegistry();
			net.citizensnpcs.api.npc.NPC npc = registry.createNPC(EntityType.PLAYER, getNick());
			npc.setName(getNick());
			npc.data().set(net.citizensnpcs.api.npc.NPC.PLAYER_SKIN_UUID_METADATA, getSkin());
			npc.spawn(getLoc());
		}

		public Location getLoc() {
			return loc;
		}

		public void setLoc(Location loc) {
			this.loc = loc;
		}

		public String getNick() {
			return nick;
		}

		public void setNick(String nick) {
			this.nick = nick;
		}

		public String getSkin() {
			return skin;
		}

		public void setSkin(String skin) {
			this.skin = skin;
		}

	}
}