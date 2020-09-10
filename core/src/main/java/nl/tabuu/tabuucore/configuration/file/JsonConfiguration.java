package nl.tabuu.tabuucore.configuration.file;

import com.google.gson.*;
import nl.tabuu.tabuucore.configuration.IConfiguration;
import nl.tabuu.tabuucore.configuration.holder.JsonDataHolder;

import java.io.*;

public class JsonConfiguration extends JsonDataHolder implements IConfiguration {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private File _file;
    private JsonParser _parser;
    private InputStream _defaults;

    public JsonConfiguration(File file, InputStream defaults) {
        _file = file;
        _defaults = defaults;
        _parser = new JsonParser();
        writeDefaults();
    }

    @Override
    public InputStream getDefaults() {
        return _defaults;
    }

    @Override
    public File getFile() {
        return _file;
    }

    @Override
    public void reload() {
        try (Reader jsonReader = new FileReader(getFile())){
            JsonElement element = _parser.parse(jsonReader);
            if(element.isJsonObject()) setRoot(element.getAsJsonObject());
            else setRoot(createEmptyParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return GSON.toJson(getRoot());
    }
}