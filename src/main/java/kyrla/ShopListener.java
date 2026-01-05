package kyrla;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    // Метод для отправки красивых сообщений из конфига
    private void sendConfigMessage(Player player, String key, double price) {
        // Читаем строку из конфига (например, "messages.success")
        String message = ShopPlugin.getInstance().getConfig().getString(key);

        if (message == null) return; // Если в конфиге пусто - ничего не пишем

        // Меняем  %price% на реальную цифру
        message = message.replace("%price%", String.valueOf(price));

        //  Красим текст (меняем & на цветовой код Minecraft)
        message = ChatColor.translateAlternateColorCodes('&', message);

        // Отправляем
        player.sendMessage(message);
    }

    private boolean tryBuy(Player player, double cost) {
        if (ShopPlugin.econ.has(player, cost)) {
            ShopPlugin.econ.withdrawPlayer(player, cost);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {

        if (e.getInventory().getHolder() == null || !(e.getInventory().getHolder() instanceof ShopHolder)) {
            return;
        }

        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;

        Player player = (Player) e.getWhoClicked();
        Material type = e.getCurrentItem().getType();

        // Определяем цену в зависимости от предмета
        double price = 0.0;
        if (type == Material.DIAMOND) {
            price = ShopPlugin.getInstance().getConfig().getDouble("prices.diamond");
        } else if (type == Material.GOLD_INGOT) {
            price = ShopPlugin.getInstance().getConfig().getDouble("prices.gold");
        } else {
            return; // Если кликнули на что-то другое
        }

        // Попытка покупки метод наш
        if (tryBuy(player, price)) {
            player.getInventory().addItem(new ItemStack(type));

            sendConfigMessage(player, "messages.success", price);
        } else {
            sendConfigMessage(player, "messages.no-money", price);
        }
    }
}