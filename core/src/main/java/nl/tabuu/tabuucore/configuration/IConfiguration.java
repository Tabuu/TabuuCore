package nl.tabuu.tabuucore.configuration;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.Objects;

public interface IConfiguration extends IDataHolder {

    /**
     * Returns the file to which this configuration is linked.
     * @return The file to which this configuration is linked.
     */
    File getFile();

    InputStream getDefaults();

    @SuppressWarnings("all")
    default boolean writeDefaults() {
        if(!getFile().exists())
            getFile().getParentFile().mkdirs();

        try {
            if(!getFile().exists()) {
                if(Objects.nonNull(getDefaults())) {
                    InputStream in = getDefaults();
                    OutputStream out = new FileOutputStream(getFile());
                    byte[] buf = new byte[in.available()];
                    int len;
                    while ((len = in.read(buf)) > 0)
                        out.write(buf, 0, len);
                    in.close();
                    out.close();
                } else getFile().createNewFile();
            }

            reload();
        } catch (IOException ex) {
            Bukkit.getLogger().severe(String.format("Plugin unable to write configuration file %s!", getFile().getName()));
            return false;
        }

        return true;
    }

    /**
     * Saves the configuration to file.
     */
    @SuppressWarnings("all")
    default void save() {
        try {
            Files.createParentDirs(getFile());
            String data = toString();

            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(getFile()), Charsets.UTF_8)) {
                writer.write(data);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Reloads the configuration from file.
     */
    void reload();
}
