package nl.tabuu.tabuucore.text;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import nl.tabuu.tabuucore.debug.Debug;
import nl.tabuu.tabuucore.nms.wrapper.INBTTagCompound;
import nl.tabuu.tabuucore.util.vector.Vector1f;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComponentBuilder {

    private Stack<BaseComponent> _components;
    private BaseComponent[] _current;

    private ComponentBuilder() {
        _components = new Stack<>();
    }

    /**
     * Creates a ComponentBuilder.
     * @return The created ComponentBuilder.
     */
    public static ComponentBuilder create() {
        return new ComponentBuilder();
    }

    private void push() {
        if (_current != null) forCurrent(_components::push);
        _current = null;
    }

    /**
     * Returns an array of BaseComponents based on this builder.
     * @return An array of BaseComponents based on this builder.
     */
    public BaseComponent[] build() {
        push();
        _components.removeIf(Objects::isNull);
        return _components.toArray(new BaseComponent[0]);
    }

    /**
     * Adds the specified based components to the builder.
     * @param baseComponents The components to add.
     * @return This builder.
     */
    public ComponentBuilder then(BaseComponent... baseComponents) {
        push();
        _current = baseComponents;
        return this;
    }

    /**
     * Adds the components of a builder.
     * @param builder The the builder to get the components from.
     * @return This builder.
     */
    public ComponentBuilder then(ComponentBuilder builder) {
        return then(builder.build());
    }

    /**
     * Adds the specified text to the builder.
     * @param text The text to add.
     * @return This builder.
     */
    public ComponentBuilder thenText(String text) {
        push();
        _current = TextComponent.fromLegacyText(text);
        return this;
    }

    /**
     * Adds the specified translatable text to the builder.
     * @param translatable The translatable text to add.
     * @return This builder.
     */
    public ComponentBuilder thenTranslatable(String translatable) {
        push();
        _current = new BaseComponent[]{new TranslatableComponent(translatable)};
        return this;
    }

    /**
     * Parses a with {@link #parse(String text)} and adds it's BaseComponents.
     * @param text The text to parse.
     * @return This builder.
     */
    public ComponentBuilder thenParse(String text) {
        push();
        _current = parse(text).build();
        return this;
    }

    /**
     * A foreach to edit the current (last added) BaseComponents.
     * @param edit The consumer to apply to the current BaseComponents.
     * @return This builder.
     */
    public ComponentBuilder forCurrent(Consumer<BaseComponent> edit) {
        if (_current == null) return this;
        for (BaseComponent component : _current)
            edit.accept(component);
        return this;
    }

    /**
     * Sets the color of the current BaseComponents.
     * @param color The color to set.
     * @return This builder.
     */
    public ComponentBuilder setColor(ChatColor color) {
        forCurrent(component -> component.setColor(color));
        return this;
    }

    /**
     * Adds formatting to the current BaseComponents.
     * @param formatting The formatting to add.
     * @return This builder.
     */
    public ComponentBuilder addFormatting(ChatColor... formatting) {
        for (ChatColor format : formatting) {
            switch (format) {
                case BOLD:
                    forCurrent(component -> component.setBold(true));
                    break;

                case ITALIC:
                    forCurrent(component -> component.setItalic(true));
                    break;

                case UNDERLINE:
                    forCurrent(component -> component.setUnderlined(true));
                    break;

                case STRIKETHROUGH:
                    forCurrent(component -> component.setStrikethrough(true));
                    break;

                case MAGIC:
                    forCurrent(component -> component.setObfuscated(true));
                    break;

                default:
                    setColor(format);
                    break;
            }
        }

        return this;
    }

    /**
     * Sets the click event of the current BaseComponents.
     * @param action The action on click.
     * @param value The value of the action.
     * @return This builder.
     */
    public ComponentBuilder setClickEvent(ClickEvent.Action action, String value) {
        ClickEvent clickEvent = new ClickEvent(action, value);
        forCurrent(current -> current.setClickEvent(clickEvent));
        return this;
    }

    /**
     * Sets the hover event of the current BaseComponents.
     * @param action The action on hover.
     * @param components The components of the hover.
     * @return This builder.
     */
    public ComponentBuilder setHoverEvent(HoverEvent.Action action, BaseComponent... components) {
        HoverEvent hoverEvent = new HoverEvent(action, components);
        forCurrent(current -> current.setHoverEvent(hoverEvent));
        return this;
    }

    /**
     * Sets the hover event of the current BaseComponents.
     * @param action The action on hover.
     * @param builder The the builder to get the components from.
     * @return This builder.
     */
    public ComponentBuilder setHoverEvent(HoverEvent.Action action, ComponentBuilder builder) {
        setHoverEvent(action, builder.build());
        return this;
    }

    /**
     * Sets the hover text of the current BaseComponents.
     * @param components The components of the hover.
     * @return This builder.
     */
    public ComponentBuilder setHoverText(BaseComponent... components) {
        setHoverEvent(HoverEvent.Action.SHOW_TEXT, components);
        return this;
    }

    /**
     * Sets the hover text of the current BaseComponents.
     * @param builder The the builder to get the components from.
     * @return This builder.
     */
    public ComponentBuilder setHoverText(ComponentBuilder builder) {
        setHoverText(builder.build());
        return this;
    }

    /**
     * Sets the hover text of the current BaseComponents.
     * @param text The text of the hover.
     * @return This builder.
     */
    public ComponentBuilder setHoverText(String text) {
        ComponentBuilder builder = ComponentBuilder.create().thenText(text);
        setHoverText(builder);
        return this;
    }

    /**
     * Sets the hover item of the current BaseComponents based on the given item.
     * @param item The item of the hover.
     * @return This builder.
     */
    public ComponentBuilder setHoverItem(ItemStack item) {
        INBTTagCompound tag = INBTTagCompound.get(item);
        String json = tag.toJson(item);
        TextComponent hoverItemComponent = new TextComponent(json);
        setHoverEvent(HoverEvent.Action.SHOW_ITEM, hoverItemComponent);
        return this;
    }

    /**
     * Formats the specified string into a builder containing the base components of that string.
     * @param string The string to format.
     * @return A builder based on the given string.
     * @see <a href="https://github.com/Tabuu/TabuuCore/wiki/ComponentBuilder#parsing">ComponentBuilder parsing info.</a>
     */
    public static ComponentBuilder parse(final String string) {
        Map<Vector2f, BaseComponent[]> components = new HashMap<>();

        Pattern hoverPattern = Pattern.compile("\\[(?<text>(?:[^]\\\\]|\\\\.)+?)]\\((?:(?<hfunc>(?:[^,)\\\\]|\\\\.)+?),)?(?<hval>(?:[^)\\\\]|\\\\.)*?)\\)(?:\\((?<cfunc>(?:[^)\\\\]|\\\\.)+?),(?<cval>(?:[^)\\\\]|\\\\.)+?)\\))?");
        Pattern translatablePattern = Pattern.compile("\\{(?<text>[^}]*)}");

        int formatChanges = 0;
        String plainText = string;

        // region Hover and click text formatting
        Matcher hoverMatch = hoverPattern.matcher(plainText);

        while (hoverMatch.find()) {
            String text = hoverMatch.group("text");
            text = text.replace("\\]", "]");


            String hoverValue = hoverMatch.group("hval");
            hoverValue = hoverValue.replace("\\)", ")");
            hoverValue = hoverValue.replace("\\,", ",");

            String hoverFunction = hoverMatch.group("hfunc");
            String clickFunction = hoverMatch.group("cfunc");

            String clickValue = hoverMatch.group("cval");
            if(clickValue != null) {
                clickValue = clickValue.replace("\\)", ")");
                clickValue = clickValue.replace("\\,", ",");
            }

            ComponentBuilder textBuilder = ComponentBuilder.parse(text);
            ComponentBuilder hoverBuilder = ComponentBuilder.create();

            if (clickFunction != null && clickValue != null) {
                try {
                    ClickEvent.Action clickAction = ClickEvent.Action.valueOf(clickFunction.toUpperCase());
                    textBuilder.setClickEvent(clickAction, clickValue);
                } catch (IllegalArgumentException ignore) {}
            }

            HoverEvent.Action hoverAction = null;
            if (hoverFunction != null) {
                try {
                    hoverAction = HoverEvent.Action.valueOf(hoverFunction.toUpperCase());
                } catch (IllegalArgumentException ignore) {}
            }

            if (hoverAction == null) hoverAction = HoverEvent.Action.SHOW_TEXT;

            if (hoverAction.equals(HoverEvent.Action.SHOW_TEXT)) hoverBuilder.thenParse(hoverValue);
            else hoverBuilder.thenText(hoverValue);

            textBuilder.setHoverEvent(hoverAction, hoverBuilder);

            components.put(new Vector2f(hoverMatch.start(), hoverMatch.end()), textBuilder.build());
            plainText = plainText.replace(hoverMatch.group(), "");
            formatChanges++;
        }
        // endregion

        // region Translatable
        Matcher translatableMatch = translatablePattern.matcher(plainText);

        while (translatableMatch.find()) {
            String text = translatableMatch.group("text");
            ComponentBuilder translatableBuilder = ComponentBuilder.create().thenTranslatable(text);

            components.put(new Vector2f(translatableMatch.start(), translatableMatch.end()), translatableBuilder.build());
            plainText = plainText.replace(translatableMatch.group(), "");
            formatChanges++;
        }

        if (formatChanges == 0)
            components.put(new Vector2f(0, string.length()), TextComponent.fromLegacyText(string));
        // endregion

        // region Sorting
        int offset = 0;
        Stack<BaseComponent[]> sorted = new Stack<>();
        List<Vector2f> positions = new ArrayList<>(components.keySet());

        positions.sort(Comparator.comparing(Vector1f::getIntX));

        for(Vector2f position : positions) {
            String substring = string.substring(offset, position.getIntX());
            if(!substring.isEmpty())
                sorted.add(TextComponent.fromLegacyText(substring));

            sorted.add(components.get(position));

            offset = position.getIntY();
        }

        Vector2f last = positions.get(positions.size() - 1);
        if(last.getIntY() < string.length())
            sorted.add(TextComponent.fromLegacyText(string.substring(last.getIntY())));
        // endregion

        ComponentBuilder builder = new ComponentBuilder();
        sorted.forEach(builder::then);
        return builder;
    }
}
