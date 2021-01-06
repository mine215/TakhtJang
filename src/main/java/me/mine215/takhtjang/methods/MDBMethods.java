package me.mine215.takhtjang.methods;

import com.mongodb.*;
import me.mine215.takhtjang.data.PlayerStats;
import me.mine215.takhtjang.types.Stats;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MDBMethods {
    public List<Map<String, String>> getManagedPlayers() {
        List<Map<String, String>> players = new ArrayList<>();

        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB dataDB = mongoClient.getDB("PermissionsDatabase");
            if (dataDB != null) {
                DBCollection col = dataDB.getCollection("PlayerData");
                if (col != null) {
                    Cursor cursor = col.find();
                    while (cursor.hasNext()) {
                        Map<String, String> info = new HashMap<>();
                        info.put(cursor.next().get("_id").toString(), cursor.next().get("name").toString());
                        players.add(info);
                    }
                }
            }
        }
        return players;
    }

    public void addManagedPlayer(Player player) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB dataDB = mongoClient.getDB("PermissionsDatabase");
            if (dataDB != null) {
                DBCollection col = dataDB.getCollection("PlayerData");

                DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                DBCursor cursor = col.find(query);
                DBObject playerStatsFromMDB = cursor.one();

                DBObject data = null;

                if (playerStatsFromMDB != null) {

                    BasicDBObject queryData = new BasicDBObject();
                    queryData.put("_id", player.getUniqueId().toString());

                    BasicDBObject newDocument = new BasicDBObject();
                    newDocument.put("name", player.getName());

                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", newDocument);

                    col.update(queryData, updateObject);
                } else {
                    data = new BasicDBObject("_id", player.getUniqueId().toString())
                            .append("name", player.getName());
                    col.insert(data);
                }
            }
        }
    }

    public void removedManagedPlayer(Player player) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB dataDB = mongoClient.getDB("PermissionsDatabase");
            if (dataDB != null) {
                DBCollection col = dataDB.getCollection("PlayerData");

                DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                DBCursor cursor = col.find(query);
                DBObject playerStatsFromMDB = cursor.one();

                if (playerStatsFromMDB != null) {
                    cursor.remove();
                }
            }
        }
    }

    public boolean isPlayerManaged(Player player) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB dataDB = mongoClient.getDB("PermissionsDatabase");
            if (dataDB != null) {
                DBCollection col = dataDB.getCollection("PlayerData");
                if (col != null) {
                    DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                    DBCursor cursor = col.find(query);
                    DBObject playerStatsFromMDB = cursor.one();

                    if (playerStatsFromMDB != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<String> getManagedPlayerPermission(Player player) {
        List<String> permissions = new ArrayList<>();

        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB dataDB = mongoClient.getDB("PermissionsDatabase");
            if (dataDB != null) {
                DBCollection col = dataDB.getCollection("PlayerData");

                DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                DBCursor cursor = col.find(query);
                DBObject playerStatsFromMDB = cursor.one();

                DBObject data = null;

                if (playerStatsFromMDB != null) {
                    if (playerStatsFromMDB.get("permissions") != null) {
                        permissions = (List<String>) playerStatsFromMDB.get("permissions");
                    }
                }
            }
        }
        return permissions;
    }

    public void addManagedPlayerPermission(Player player, String permission) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB dataDB = mongoClient.getDB("PermissionsDatabase");
            if (dataDB != null) {
                DBCollection col = dataDB.getCollection("PlayerData");

                DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                DBCursor cursor = col.find(query);
                DBObject playerStatsFromMDB = cursor.one();

                DBObject data = null;

                if (playerStatsFromMDB != null) {

                    BasicDBObject queryData = new BasicDBObject();
                    queryData.put("_id", player.getUniqueId().toString());

                    BasicDBObject newDocument = new BasicDBObject();
                    newDocument.put("name", player.getName());

                    if (playerStatsFromMDB.get("permissions") == null) {
                        List<String> permList = new ArrayList<>();
                        permList.add(permission);
                        newDocument.put("permissions", permList);
                    } else {
                        List<String> permList = (List<String>) playerStatsFromMDB.get("permissions");
                        permList.add(permission);
                        newDocument.put("permissions", permList);
                    }

                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", newDocument);

                    col.update(queryData, updateObject);
                } else {
                    data = new BasicDBObject("_id", player.getUniqueId().toString())
                            .append("name", player.getName());
                    col.insert(data);
                }
            }
        }
    }

    public void removeManagedPlayerPermission(Player player, String permission) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB dataDB = mongoClient.getDB("PermissionsDatabase");
            if (dataDB != null) {
                DBCollection col = dataDB.getCollection("PlayerData");

                DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                DBCursor cursor = col.find(query);
                DBObject playerStatsFromMDB = cursor.one();

                DBObject data = null;

                if (playerStatsFromMDB != null) {

                    BasicDBObject queryData = new BasicDBObject();
                    queryData.put("_id", player.getUniqueId().toString());

                    BasicDBObject newDocument = new BasicDBObject();
                    newDocument.put("name", player.getName());

                    if (playerStatsFromMDB.get("permissions") != null) {
                        List<String> permList = (List<String>) playerStatsFromMDB.get("permissions");
                        permList.remove(permission);
                        newDocument.put("permissions", permList);
                    }

                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", newDocument);

                    col.update(queryData, updateObject);
                } else {
                    data = new BasicDBObject("_id", player.getUniqueId().toString())
                            .append("name", player.getName());
                    col.insert(data);
                }
            }
        }
    }

    public String getRankName(Player player) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB dataDB = mongoClient.getDB("GeneralInfoDatabase");
            if (dataDB != null) {
                DBCollection col = dataDB.getCollection("PlayerData");
                if (col != null) {
                    DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                    DBCursor cursor = col.find(query);
                    DBObject playerStatsFromMDB = cursor.one();
                    if (playerStatsFromMDB != null) {
                        if (playerStatsFromMDB.get("rank") != null) {
                            DB statsDB = mongoClient.getDB("GeneralInfoDatabase");
                            if (statsDB != null) {
                                return (String) playerStatsFromMDB.get("rank");
                            }
                        }
                    }
                }
            }
        }
        return "NONE";
    }

    public double getStatDouble(Player player, Stats statType) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB statsDB = mongoClient.getDB("StatsDatabase");
            if (statsDB != null) {
                DBCollection col = statsDB.getCollection("PlayerStats");
                if (col != null) {
                    DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                    DBCursor cursor = col.find(query);
                    DBObject playerStatsFromMDB = cursor.one();
                    if (playerStatsFromMDB != null) {
                        if (playerStatsFromMDB.get(statType.toString()) != null) {
                            return (double) playerStatsFromMDB.get(statType.toString());
                        }
                    }
                }
            }
        }
        return 0;
    }

    public int getStatInt(Player player, Stats statType) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB statsDB = mongoClient.getDB("StatsDatabase");
            if (statsDB != null) {
                DBCollection col = statsDB.getCollection("PlayerStats");
                if (col != null) {
                    DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                    DBCursor cursor = col.find(query);
                    DBObject playerStatsFromMDB = cursor.one();
                    if (playerStatsFromMDB != null) {
                        if (playerStatsFromMDB.get(statType.toString()) != null) {
                            return (int) playerStatsFromMDB.get(statType.toString());
                        }
                    }
                }
            }
        }
        return 0;
    }

    public int getTempStatInt(Player player, Stats statType) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB statsDB = mongoClient.getDB("TempStatsDatabase");
            if (statsDB != null) {
                DBCollection col = statsDB.getCollection("PlayerStats");
                if (col != null) {
                    DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                    DBCursor cursor = col.find(query);
                    DBObject playerStatsFromMDB = cursor.one();
                    if (playerStatsFromMDB != null) {
                        if (playerStatsFromMDB.get(statType.toString()) != null) {
                            return (int) playerStatsFromMDB.get(statType.toString());
                        }
                    }
                }
            }
        }
        return 0;
    }

    public void resetTempStats(Player player) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            // Update temp
            DB tempDB = mongoClient.getDB("TempStatsDatabase");
            if (tempDB != null) {
                DBCollection col = tempDB.getCollection("PlayerStats");

                DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                col.remove(query);
            }
        }
    }

    public void updateRank(Player player, String rankName) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB statsDB = mongoClient.getDB("GeneralInfoDatabase");
            if (statsDB != null) {
                DBCollection col = statsDB.getCollection("PlayerData");

                DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                DBCursor cursor = col.find(query);
                DBObject playerStatsFromMDB = cursor.one();

                DBObject data = null;

                if (playerStatsFromMDB != null) {

                    BasicDBObject queryData = new BasicDBObject();
                    queryData.put("_id", player.getUniqueId().toString());

                    BasicDBObject newDocument = new BasicDBObject();
                    newDocument.put("rank", rankName);

                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", newDocument);

                    col.update(queryData, updateObject);
                } else {
                    data = new BasicDBObject("_id", player.getUniqueId().toString())
                            .append("rank", rankName);
                    col.insert(data);
                }
            }
        }
    }

    public void updateStatsInt(Player player, Stats statType, int statToAdd) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB statsDB = mongoClient.getDB("StatsDatabase");
            if (statsDB != null) {
                DBCollection col = statsDB.getCollection("PlayerStats");

                DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                DBCursor cursor = col.find(query);
                DBObject playerStatsFromMDB = cursor.one();

                PlayerStats playerStats = new PlayerStats(player.getUniqueId(), player.getName());
                DBObject data = null;

                int statBase;
                if (playerStatsFromMDB != null) {
                    if (playerStatsFromMDB.get(statType.toString()) == null) {
                        statBase = 0;
                    } else {
                        statBase = (int) playerStatsFromMDB.get(statType.toString());
                    }
                    

                    BasicDBObject queryData = new BasicDBObject();
                    queryData.put("_id", playerStats.uuid);

                    BasicDBObject newDocument = new BasicDBObject();
                    newDocument.put(statType.toString(), statBase + statToAdd);

                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", newDocument);

                    col.update(queryData, updateObject);
                } else {
                    statBase = 0;

                    data = new BasicDBObject("_id", playerStats.uuid)
                            .append(statType.toString(), statBase + statToAdd);
                    col.insert(data);
                }
            }

            // Update temp
            DB tempDB = mongoClient.getDB("TempStatsDatabase");
            if (tempDB != null) {
                DBCollection col = tempDB.getCollection("PlayerStats");

                DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                DBCursor cursor = col.find(query);
                DBObject playerStatsFromMDB = cursor.one();

                PlayerStats playerStats = new PlayerStats(player.getUniqueId(), player.getName());
                DBObject data = null;

                int statBase;
                if (playerStatsFromMDB != null) {
                    if (playerStatsFromMDB.get(statType.toString()) == null) {
                        statBase = 0;
                    } else {
                        statBase = (int) playerStatsFromMDB.get(statType.toString());
                    }

                    BasicDBObject queryData = new BasicDBObject();
                    queryData.put("_id", playerStats.uuid);

                    BasicDBObject newDocument = new BasicDBObject();
                    newDocument.put(statType.toString(), statBase + statToAdd);

                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", newDocument);

                    col.update(queryData, updateObject);
                } else {
                    statBase = 0;

                    data = new BasicDBObject("_id", playerStats.uuid)
                            .append(statType.toString(), statBase + statToAdd);
                    col.insert(data);
                }
            }
        }
    }

    public boolean updateStatsDouble(Player player, Stats statType, double statToAdd) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB statsDB = mongoClient.getDB("StatsDatabase");
            if (statsDB != null) {
                DBCollection col = statsDB.getCollection("PlayerStats");

                DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                DBCursor cursor = col.find(query);
                DBObject playerStatsFromMDB = cursor.one();

                PlayerStats playerStats = new PlayerStats(player.getUniqueId(), player.getName());
                DBObject data = null;

                double statBase;
                if (playerStatsFromMDB != null) {
                    if (playerStatsFromMDB.get(statType.toString()) == null) {
                        statBase = 0;
                    } else {
                        statBase = (double) playerStatsFromMDB.get(statType.toString());
                    }

                    BasicDBObject queryData = new BasicDBObject();
                    queryData.put("_id", playerStats.uuid);

                    BasicDBObject newDocument = new BasicDBObject();
                    newDocument.put(statType.toString(), statBase + statToAdd);

                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", newDocument);

                    col.update(queryData, updateObject);
                } else {
                    statBase = 0;

                    data = new BasicDBObject("_id", playerStats.uuid)
                            .append(statType.toString(), statBase + statToAdd);
                    col.insert(data);
                }
                return true;
            }
        }
        return false;
    }
}
