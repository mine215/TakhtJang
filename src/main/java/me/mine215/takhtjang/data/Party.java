package me.mine215.takhtjang.data;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Party {
    public Player owner;
    public final List<Player> members = new ArrayList<>();
    public final List<Player> invites = new ArrayList<>();

    public Party(Player ownerIn) {
        owner = ownerIn;
        members.add(ownerIn);
    }

    public List<Player> getMembers() {
        return members;
    }

    public boolean invitePlayer(Player target) {
        if (target != null) {
            invites.add(target);
            return true;
        } else {
            return false;
        }
    }

    public boolean joinParty(Player target) {
        if (invites.contains(target)) {
            if (new PartyData().getPartyFromMember(target) != null) {
                new PartyData().getPartyFromMember(target).kickFromParty(target);
            }
            invites.remove(target);
            members.add(target);
            return true;
        } else {
            return false;
        }
    }

    public void kickFromParty(Player target) {
        System.out.println(members);
        members.remove(target);
        System.out.println(members);
    }
}
