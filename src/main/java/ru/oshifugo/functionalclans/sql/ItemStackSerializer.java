package ru.oshifugo.functionalclans.sql;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@UtilityClass
public class ItemStackSerializer {

    @SneakyThrows
    @NotNull
    public String toBase64(@NotNull ItemStack itemStack) {
        @Cleanup ByteArrayOutputStream out = new ByteArrayOutputStream();
        @Cleanup BukkitObjectOutputStream objOut = new BukkitObjectOutputStream(out);
        objOut.writeObject(itemStack);
        return Base64Coder.encodeLines(out.toByteArray());
    }
    @SneakyThrows
    @NotNull
    public ItemStack fromBase64(@NotNull String base64) {
        @Cleanup ByteArrayInputStream in = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
        @Cleanup BukkitObjectInputStream objIn = new BukkitObjectInputStream(in);
        return (ItemStack) objIn.readObject();

    }

}
