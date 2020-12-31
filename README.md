# TakhtJang
A custom game designed for servers that use a child of CraftBukkit as their JAR.

# Setup
1. Download the latest release from the releases page (to the right).
2. Move the plugin JAR from your downloads folder into your servers `plugins` folder.
3. Clone the repo and move one of the sample worlds from `sampleMaps` to your servers folder.
4. Go into your `server.properties` file and change the world name to match the sample world you downloaded.
5. Start the server.
6. Go into your `plugins/TakhtJang` folder and edit `config.yml` to match the world you downloaded. If you are using `TakhtJangSampleA` it is already configured for you.
7. Reload your server (Type `reload` into the console or `/reload` if you are an OP).

# Setup for non sample map
1. Follow steps 1-2 from the normal setup.
2. In your `server.properties` file specify a world name for your server.
3. Start your server.
4. Build the map. Make sure to include everything in the [required locations](sampleMaps/required_locations.md) file.
5. Add all the locations to the correct key in the `config.yml` file (make sure everything is a double).
6. Change the world name to reflect your worlds name.
7. Change `isSetup` to "yes" in the `config.yml`.
8. Reload your server (Type `reload` into the console or `/reload` if you are an OP).

## Direct Contact
I would prefer if you open an issue on the repo but if you really need direct contact
DM mine215#7786 on discord.
