# TakhtJang
A custom game designed for servers that use a child of CraftBukkit as their JAR. Tested on Spigot.

# Database setup
Do this before setting anything else up.
1. Install MongoDB.
2. Start your MongoDB server.

Make sure to start your MongoDB server every time you want to use the plugin.

# Setup
1. Download the latest release from the releases page (to the right).
2. Move the plugin JAR from your downloads folder into your servers `plugins` folder.
3. Clone the repo and move one of the sample worlds (they are folders) from `samples` to your servers folder.
4. Go into your `server.properties` file and change the world name to match the sample world you downloaded.
5. Start the server.
6. Go into your `plugins/TakhtJang` folder and edit `config.yml` to match the world you downloaded. Sample configs for the sample maps are available in the `sampleMaps` folder.
7. Reload your server (Type `reload` into the console or `/reload` if you are an OP).

# Setup for non sample map
1. Follow steps 1-2 from the normal setup.
2. In your `server.properties` file specify a world name for your server.
3. Start your server.
4. Build the map. Make sure to include all the required locations in the [config tutorial](samples/config_tutorial.md) file.
5. Use the [config tutorial](samples/config_tutorial.md) to set up the world.
6. Reload your server (Type `reload` into the console or `/reload` if you are an OP).

## Direct Contact
I would prefer if you open an issue on the repo but if you really need direct contact
DM mine215#7786 on discord.
