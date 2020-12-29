package me.mine215.takhtjang.data;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PartyData {
    static List<Party> parties = new ArrayList<>();

    public List<Party> getParties() {
        return parties;
    }

    public Party getPartyFromOwner(Player owner) {
        for (Party party : parties) {
            if (party.owner.equals(owner)) {
                return party;
            }
        }
        return null;
    }

    public Party getPartyFromMember(Player member) {
        for (Party party : parties) {
            if (party.getMembers().contains(member)) {
                return party;
            }
        }
        return null;
    }

    public Party createParty(Player owner) {
        if (getPartyFromOwner(owner) == null) {
            parties.add(new Party(owner));
            return getPartyFromOwner(owner);
        }
        return null;
    }

    public boolean endParty(Player owner) {
        if (getPartyFromOwner(owner) != null) {
            parties.remove(getPartyFromOwner(owner));
            return true;
        }
        return false;
    }

    public boolean transferParty(Player newOwner) {
        Party party = getPartyFromMember(newOwner);
        if (party != null) {
            party.owner = newOwner;
        }
        return false;
    }
}
