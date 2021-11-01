DamnedMinecraftTweaks

A mod made exclusively for the DamnedMinecraft modpack.

Requires the following mods to be installed: 

· Better Smithing

· Crafttweaker

· MineAndSlash

· ToroHealth

· Scaling Health

Current features include:

- Adding compatibility betwen MineAndSlash health values and ToroHealth. (PR?)
- Sync Scaling Health's difficulty values to the client and expose them to Crafttweaker. `MCLivingEntity.getSHDifficulty() as float`
- Fix a bug with BetterSmithings way of registering a tag (Report to them)
- Allow for a way to identify souls before using them through Crafttweaker. `IItemStack.identifySoul() as void`
