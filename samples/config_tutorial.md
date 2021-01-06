# Required Locations
This is a list of all the required locations you need to build your own TakhtJang map.

1. 4 Bases (Blue, Green, Yellow, Red)
2. 1 Hub
3. 4 Shops
4. 6 Teleport Pads 

The 6 teleport pads are optional, and you can choose to not add them if you toggle `doPortals` to false in the `config`.

# How to configure your world
All this is to be done in the `config.yml` file. We *highly* recommend copying one of the sample configs and modifying it to match your world for the most reliable results.

1. Get coordinates for all the required locations.
2. Input the coordinates to the config file.
3. Enable or disable portals and modify the gen speed to balance to your map.
4. Change the `worldName` to match the name of your world on your server.
5. Change `isSetup` to true.

Make sure to define the genMultiplier to balance to the amount of bridging on your map. The more bridging the higher the gen multiplier should be. A 1 gen multiplier means 1 iron spawns every 4 seconds and 1 gold every 10 seconds. Your gen multiplier will multiply to the base speeds to get the speed of items being spawned.

All coordinates will be defined in the `config.yml` file.