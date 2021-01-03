package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.data.Party;
import me.mine215.takhtjang.data.PartyData;
import me.mine215.takhtjang.methods.PlayerMethods;
import me.mine215.takhtjang.types.Team;
import me.rayzr522.jsonmessage.JSONMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PartyCommand implements CommandExecutor {

    static TakhtJang main;
    FileConfiguration config;

    public PartyCommand(FileConfiguration config, TakhtJang main) {
        this.main = main;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length >= 1) {
            String argument = args[0].toLowerCase();

            PartyData partyData = new PartyData();

            if (argument.equals("transfer") && args.length >= 2) {
                if (partyData.getPartyFromMember(player) != null) {
                    String playerToPromoteName = args[1].toString();
                    Player playerToPromote = player.getServer().getPlayer(playerToPromoteName);
                    if (playerToPromote != null) {
                        Party party = partyData.getPartyFromMember(player);
                        if (party.owner.equals(player)) {
                            partyData.transferParty(playerToPromote);
                            player.sendMessage(ChatColor.YELLOW + "The party has been transferred to " + party.owner.getDisplayName());
                        } else {
                            player.sendMessage(ChatColor.RED + "You are not the owner of the party.");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "That player is not in your party.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in a party.");
                }
            }  else if (argument.equals("warp")) {
                if (partyData.getPartyFromMember(player) != null) {
                    if (partyData.getPartyFromOwner(player) != null) {
                        for (Player playerInParty : partyData.getPartyFromOwner(player).getMembers()) {
                            if (new GameData().getPlayer(player) == null) {
                                if (!playerInParty.equals(player)) {
                                    new PlayerMethods().hub(playerInParty, config);
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You cannot warp party members into a game, please do /play to start a game.");
                            }
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You are not the owner of the party.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in a party.");
                }
            } else if (argument.equals("leave")) {
                Party party = partyData.getPartyFromMember(player);
                if (party != null) {
                    if (party.owner.equals(player)) {
                        party.kickFromParty(player);
                        partyData.transferParty(player);
                        player.sendMessage(ChatColor.YELLOW + "You have left the party, the new owner is " + party.owner.getDisplayName());
                    } else {
                        party.kickFromParty(player);
                        player.sendMessage(ChatColor.YELLOW + "You have left the party.");
                    }
                    party.kickFromParty(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in a party.");
                }
            } else if (argument.equals("disband")) {
                if (partyData.endParty(player)) {
                    player.sendMessage( ChatColor.YELLOW + "The party has been disbanded.");
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in a party.");
                }
            } else if (argument.equals("kick") && args.length >= 2) {
                String playerToKickName = args[1].toString();
                Player playerToKick = player.getServer().getPlayer(playerToKickName);
                if (partyData.getPartyFromMember(player) != null) {
                    if (playerToKick != null) {
                        if (partyData.getPartyFromOwner(player) != null) {
                            if (partyData.getPartyFromOwner(player).members.contains(playerToKick)) {
                                partyData.getPartyFromOwner(player).kickFromParty(playerToKick);
                                player.sendMessage(playerToKick.getDisplayName() + ChatColor.YELLOW + " has been kicked from the party.");
                            } else {
                                player.sendMessage(ChatColor.RED + "That player is not in your party.");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You are not the owner of the party.a");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "That player is not online.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in a party.");
                }
            } else if (argument.equals("list")) {
                if (partyData.getPartyFromMember(player) != null) {
                    StringBuilder playerListString = new StringBuilder();
                    player.sendMessage(ChatColor.YELLOW + "------------------ Party Members ------------------");
                    for (Player member : partyData.getPartyFromMember(player).getMembers()) {
                        playerListString.append(member.getDisplayName()).append(", ");
                    }
                    playerListString.deleteCharAt(playerListString.length() - 2);
                    player.sendMessage(playerListString.toString());
                    player.sendMessage(ChatColor.YELLOW + "-------------------------------------------------");
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in a party.");
                }
            } else if ((argument.equals("join") || argument.equals("accept")) && args.length >= 2) {
                String ownerName = args[1].toLowerCase();
                Player owner = player.getServer().getPlayer(ownerName);
                if (owner != null) {
                    Party party = partyData.getPartyFromOwner(owner);
                    if (party != null) {
                        if (party.joinParty(player)) {
                            player.sendMessage(ChatColor.YELLOW + "You joined " + owner.getDisplayName() + "'s " + ChatColor.YELLOW + "party!");
                        } else {
                            player.sendMessage(ChatColor.RED + "You don't have an invite to that players party.");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + ownerName + " does not have a party.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + ownerName + " is not online.");
                }
            } else {
                Party party = partyData.getPartyFromOwner(player);
                if (party == null) {
                    party = partyData.createParty(player);
                    if (party == null) {
                        player.sendMessage(ChatColor.RED + "Something went wrong. Please try again.");
                    }
                }
                Player targetPlayer = player.getServer().getPlayer(argument);
                if (party.invitePlayer(targetPlayer)) {
                    player.sendMessage(targetPlayer.getDisplayName() + ChatColor.YELLOW + " has been invited to the party.");
                    JSONMessage.create(player.getDisplayName() + ChatColor.YELLOW + " has invited you to their party, click ").then("HERE").runCommand("/party join " + player.getName()).color(ChatColor.GOLD).then(" to accept.").color(ChatColor.YELLOW).send(targetPlayer);
                } else {
                    player.sendMessage(ChatColor.RED + argument + " is not online.");
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
