# What is CorpseImmortal?

**Version**: 1.19  
**Dependency**: [Citizens](https://ci.citizensnpcs.co/job/citizens2/)  

CorpseImmortal is a Bukkit plugin that spawns lootable corpses whenever a player dies.


## How to compile

You need [maven](https://maven.apache.org/install.html) installed in order to compile CorpseImmortal.
Plugin dependencies should be installed in the local maven repository using 'mvn install' in the same directory as pom.xml.

### Note
I do **not** recommend compiling Citizens from source. Instead, [do this](https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html). You can find the appropriate groupId, artifactId, and version in this repo's pom.xml.