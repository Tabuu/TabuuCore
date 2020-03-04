package nl.tabuu.tabuucore.nms;

import org.bukkit.Bukkit;

public class NMSUtil {

    private static NMSVersion _nmsVersion;

    /**
     * Returns the {@link NMSVersion} this server is running on, or {@link NMSVersion#UNKNOWN} if an unknown version was found.
     * @return the {@link NMSVersion} this server is running on, or {@link NMSVersion#UNKNOWN} if an unknown version was found.
     */
    public static NMSVersion getVersion(){
        if(_nmsVersion == null){
            String versionName = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
            try {
                _nmsVersion = NMSVersion.valueOf(versionName);
            } catch (IllegalArgumentException exception){
                _nmsVersion = NMSVersion.UNKNOWN;
            }
        }
        return _nmsVersion;
    }

    /**
     * Returns a NMS wrapper class based on its name and {@link NMSVersion}.
     * @param className the name of the NMS wrapper class.
     * @param version the {@link NMSVersion} of the NMS wrapper class.
     * @return a NMS wrapper class based on its name and {@link NMSVersion}.
     * @see nl.tabuu.tabuucore.nms.wrapper
     */
    public static Class<?> getWrapperClass(String className, NMSVersion version){
        try {
            return Class.forName("nl.tabuu.tabuucore.nms." + version.name() + "." + className);
        }
        catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(
                    String.format("Class with name %s was not found for NMS version '%s'!", className, version.name()));
        }
    }

    /**
     * Returns a NMS wrapper class based on its name and {@link NMSVersion} returned by {@link NMSUtil#getVersion()}.
     * @param className the name of the NMS wrapper class.
     * @return a NMS wrapper class based on its name and {@link NMSVersion} returned by {@link NMSUtil#getVersion()}.
     * @see nl.tabuu.tabuucore.nms.wrapper
     */
    public static Class<?> getWrapperClass(String className){
        try {
            return getWrapperClass(className, getVersion());
        }
        catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException(
                    String.format("Class with name %s was not found for NMS version '%s'!", className, getVersion().name()));
        }
    }

    /**
     * Returns a NMS class based on its name and {@link NMSVersion}.
     * @param className the name of the wrapper class.
     * @param version the {@link NMSVersion} of the NMS class.
     * @return a NMS class based on its name and {@link NMSVersion}.
     */
    public static Class<?> getNMSClass(String className, NMSVersion version){
        try{
            return Class.forName("net.minecraft.server." + version.name() + "." + className);
        }
        catch(ClassNotFoundException e){
            throw new UnsupportedOperationException("Class with name '" + className + "' was not found for your NMS version!");
        }
    }

    /**
     * Returns a NMS class based on its name and {@link NMSVersion} returned by {@link NMSUtil#getVersion()}.
     * @param className the name of the NMS class.
     * @return a NMS class based on its name and {@link NMSVersion} returned by {@link NMSUtil#getVersion()}.
     */
    public static Class<?> getNMSClass(String className){
        return getNMSClass(className, getVersion());
    }

}
