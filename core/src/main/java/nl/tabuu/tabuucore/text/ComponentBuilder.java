package nl.tabuu.tabuucore.text;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import nl.tabuu.tabuucore.nms.wrapper.INBTTagCompound;
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

    public static ComponentBuilder create() {
        return new ComponentBuilder();
    }

    private void push() {
        if (_current != null) forCurrent(_components::push);
        _current = null;
    }

    public BaseComponent[] build() {
        push();
        _components.removeIf(Objects::isNull);
        return _components.toArray(new BaseComponent[0]);
    }

    public ComponentBuilder then(BaseComponent... baseComponents) {
        push();
        _current = baseComponents;
        return this;
    }

    public ComponentBuilder thenText(String text) {
        push();
        _current = TextComponent.fromLegacyText(text);
        return this;
    }

    public ComponentBuilder thenTranslatable(String translatable) {
        push();
        _current = new BaseComponent[]{new TranslatableComponent(translatable)};
        return this;
    }

    public ComponentBuilder thenParse(String text) {
        push();
        _current = parse(text).build();
        return this;
    }

    public ComponentBuilder forCurrent(Consumer<BaseComponent> edit) {
        if (_current == null) return this;
        for (BaseComponent component : _current)
            edit.accept(component);
        return this;
    }

    public ComponentBuilder setColor(ChatColor color) {
        forCurrent(component -> component.setColor(color));
        return this;
    }

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

    public ComponentBuilder setClickEvent(ClickEvent.Action action, String value) {
        ClickEvent clickEvent = new ClickEvent(action, value);
        forCurrent(current -> current.setClickEvent(clickEvent));
        return this;
    }

    public ComponentBuilder setHoverEvent(HoverEvent.Action action, BaseComponent... components) {
        HoverEvent hoverEvent = new HoverEvent(action, components);
        forCurrent(current -> current.setHoverEvent(hoverEvent));
        return this;
    }

    public ComponentBuilder setHoverEvent(HoverEvent.Action action, ComponentBuilder builder) {
        setHoverEvent(action, builder.build());
        return this;
    }

    public ComponentBuilder setHoverText(BaseComponent... components) {
        setHoverEvent(HoverEvent.Action.SHOW_TEXT, components);
        return this;
    }

    public ComponentBuilder setHoverText(ComponentBuilder builder) {
        setHoverText(builder.build());
        return this;
    }

    public ComponentBuilder setHoverText(String text) {
        ComponentBuilder builder = ComponentBuilder.create().thenText(text);
        setHoverText(builder);
        return this;
    }

    public ComponentBuilder setHoverItem(ItemStack item) {
        INBTTagCompound tag = INBTTagCompound.get(item);
        String json = tag.toJson(item);
        TextComponent hoverItemComponent = new TextComponent(json);
        setHoverEvent(HoverEvent.Action.SHOW_ITEM, hoverItemComponent);
        return this;
    }

    public static ComponentBuilder parse(final String string) {
        Map<String, BaseComponent[]> components = new HashMap<>();

        Pattern hoverPattern = Pattern.compile("\\[(?<text>[^]]*)]\\((?:(?<hfunc>[^,)]*),)?(?<hval>[^)]*)\\)(?:\\((?<cfunc>[^)]*),(?<cval>[^)]*)\\))?");
        Pattern translatablePattern = Pattern.compile("\\{(?<text>[^}]*)}");

        int formatChanges = 0;
        String plainText = string;

        // region Hover and click text formatting
        Matcher hoverMatch = hoverPattern.matcher(plainText);

        while (hoverMatch.find()) {
            String text = hoverMatch.group("text");

            String hoverValue = hoverMatch.group("hval");
            String hoverFunction = hoverMatch.group("hfunc");

            String clickFunction = hoverMatch.group("cfunc");
            String clickValue = hoverMatch.group("cval");

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

            components.put(hoverMatch.group(), textBuilder.build());
            plainText = plainText.replace(hoverMatch.group(), "");
            formatChanges++;
        }
        // endregion

        // region Translatable
        Matcher translatableMatch = translatablePattern.matcher(plainText);

        while (translatableMatch.find()) {
            String text = translatableMatch.group("text");
            ComponentBuilder translatableBuilder = ComponentBuilder.create().thenTranslatable(text);

            components.put(translatableMatch.group(), translatableBuilder.build());
            plainText = plainText.replace(translatableMatch.group(), "");
            formatChanges++;
        }

        if (formatChanges == 0)
            components.put(string, new BaseComponent[]{new TextComponent(string)});
        // endregion

        // region Sorting
        Stack<BaseComponent[]> sorted = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (char character : string.toCharArray()) {
            stringBuilder.append(character);
            String current = stringBuilder.toString();

            if (components.containsKey(current)) {
                sorted.add(components.get(current));
                stringBuilder = new StringBuilder();
            } else if (components.keySet().stream().noneMatch(key -> key.startsWith(current))) {
                stringBuilder = new StringBuilder();
                sorted.add(new BaseComponent[]{new TextComponent(current)});
            }
        }
        // endregion

        ComponentBuilder builder = new ComponentBuilder();
        sorted.forEach(builder::then);
        return builder;
    }
}
