package kyrla;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopCommand implements CommandExecutor {

    private ItemStack createItem(Material material, String name, String[] lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        // --- ВСТАВЛЯЕМ ЭТО СЮДА ---
        // Если массив lore не пустой (не null), мы превращаем его в список и лепим на предмет
        if (lore != null) {
            meta.setLore(java.util.Arrays.asList(lore));
        }

        item.setItemMeta(meta);
        return item;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory gui = Bukkit.createInventory(new ShopHolder(), 9, "Магазин ресурсов");

            //  Читаем цены из конфига
            double diamondPrice = ShopPlugin.getInstance().getConfig().getDouble("prices.diamond");
            double goldPrice = ShopPlugin.getInstance().getConfig().getDouble("prices.gold");

            //  Вставляем переменную цены прямо в текст (Lore)
            gui.setItem(4, createItem(Material.DIAMOND, "§bАлмаз!",
                    new String[]{"§7Цена: " + diamondPrice + "$", "§eНажми, чтобы купить!"}));

            gui.setItem(6, createItem(Material.GOLD_INGOT, "§bЗолото!",
                    new String[]{"§7Цена: " + goldPrice + "$", "§eНажми, чтобы купить!"}));

            player.openInventory(gui);
        }
        return true;
    }
}
