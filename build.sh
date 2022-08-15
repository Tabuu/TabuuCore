rm -r target
mvn compiler:compile
mvn package
rm ~/Projects/01_Minecraft_Plugins/00_Plugin_Making/03_Test_Servers/test_plugins/TabuuCore-2.6.1.jar
cp target/TabuuCore-2.6.1.jar ~/Projects/01_Minecraft_Plugins/00_Plugin_Making/03_Test_Servers/test_plugins
