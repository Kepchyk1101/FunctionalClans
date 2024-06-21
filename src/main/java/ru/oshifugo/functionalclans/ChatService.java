package ru.oshifugo.functionalclans;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {

    @NotNull BukkitAudiences audiences;
    @NotNull LegacyComponentSerializer legacy;

    @NotNull
    public Component colorize(@NotNull String text) {
        return legacy.deserialize(text);
    }

    public void sendAdminHelpMessage(@NotNull CommandSender receiver) {

        int i = 0;
        TextComponent.Builder message = Component.text()
                .append(colorizeLang(receiver, "help.fc_msg"))
                .appendNewline();

        if (receiver.hasPermission("fc.admin.update")) {
            message.append(
                    Component.text()
                            .append(colorizeLang(receiver, "fc.update.errors.e"))
                            .appendNewline()
                            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/fc update"))
                            .build()
            );
            i++;
        }

        if (receiver.hasPermission("fc.admin.verify")) {
            message.append(
                    Component.text()
                            .append(colorizeLang(receiver, "fc.verify.errors.e"))
                            .appendNewline()
                            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/fc verify"))
                            .build()
            );
            i++;
        }

        if (receiver.hasPermission("fc.admin.info")) {
            message.append(
                    Component.text()
                            .append(colorizeLang(receiver, "fc.info.errors.e"))
                            .appendNewline()
                            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/fc info"))
                            .build()
            );
            i++;
        }

        if (receiver.hasPermission("fc.admin.members")) {
            message.append(
                    Component.text()
                            .append(colorizeLang(receiver, "fc.members.errors.e"))
                            .appendNewline()
                            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/fc members"))
                            .build()
            );
            i++;
        }

        if (receiver.hasPermission("fc.admin.delete")) {
            message.append(
                    Component.text()
                            .append(colorizeLang(receiver, "fc.delete.errors.e"))
                            .appendNewline()
                            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/fc delete"))
                            .build()
            );
            i++;
        }

        if (receiver.hasPermission("fc.admin.leader")) {
            message.append(
                    Component.text()
                            .append(colorizeLang(receiver, "fc.leader.errors.e"))
                            .appendNewline()
                            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/fc leader"))
                            .build()
            );
            i++;
        }

        if (receiver.hasPermission("fc.admin.chest")) {
            message.append(
                    Component.text()
                            .append(colorizeLang(receiver, "fc.chest.errors.e"))
                            .appendNewline()
                            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/fc chest"))
                            .build()
            );
            i++;
        }

        if (i == 0) {
            message.append(colorizeLang(receiver, "common_errors.no_permission"));
        }

        sendMessage(receiver, message.build());

    }

    @NotNull
    private Component colorizeLang(@NotNull CommandSender sender, @NotNull String key) {
        return colorize(Utility.lang(sender, key));
    }

    private void sendMessage(@NotNull CommandSender receiver, @NotNull Component message) {
        audiences.sender(receiver).sendMessage(message);
    }

}
