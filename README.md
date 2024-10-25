<!-- name-start -->
# Tips [![CurseForge Project](https://img.shields.io/curseforge/dt/306549?logo=curseforge&label=CurseForge&style=flat-square&labelColor=2D2D2D&color=555555)](https://www.curseforge.com/minecraft/mc-mods/tips) [![Modrinth Project](https://img.shields.io/modrinth/dt/AMCbgyVw?logo=modrinth&label=Modrinth&style=flat-square&labelColor=2D2D2D&color=555555)](https://modrinth.com/mod/tips) [![Maven Project](https://img.shields.io/maven-metadata/v?style=flat-square&logoColor=D31A38&labelColor=2D2D2D&color=555555&label=Latest&logo=gradle&metadataUrl=https%3A%2F%2Fmaven.blamejared.com%2Fnet%2Fdarkhax%2Ftips%2Ftipsmod-common-1.21.1%2Fmaven-metadata.xml)](https://maven.blamejared.com/net/darkhax/tips)
<!-- name-end -->
<!-- description-start -->
Adds useful tips to various loading screens in game. The documentation for this mod can be found [here](https://docs.darkhax.net/mods/tip-of-the-loom).
<!-- description-end -->

<!-- maven-start -->
## Maven Dependency

If you are using [Gradle](https://gradle.org) to manage your dependencies, add the following into your `build.gradle` file. Make sure to replace the version with the correct one. All versions can be viewed [here](https://maven.blamejared.com/net/darkhax/tips).

```gradle
repositories {
    maven { 
        url 'https://maven.blamejared.com'
    }
}

dependencies {
    // NeoForge
    implementation group: 'net.darkhax.tips', name: 'tipsmod-neoforge-1.21.1', version: '21.1.0'

    // Forge
    implementation group: 'net.darkhax.tips', name: 'tipsmod-forge-1.21.1', version: '21.1.0'

    // Fabric & Quilt
    modImplementation group: 'net.darkhax.tips', name: 'tipsmod-fabric-1.21.1', version: '21.1.0'

    // Common / MultiLoader / Vanilla
    compileOnly group: 'net.darkhax.tips', name: 'tipsmod-common-1.21.1', version: '21.1.0'
}
```
<!-- maven-end -->

<!-- sponsor-start -->
## Sponsors

[![](https://assets.blamejared.com/nodecraft/darkhax.jpg)](https://nodecraft.com/r/darkhax)    
Tips is sponsored by Nodecraft. Use code **[DARKHAX](https://nodecraft.com/r/darkhax)** for 30% of your first month of service!
<!-- sponsor-end -->