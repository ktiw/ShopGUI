package kyrla;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            double price = 50.0;

            // 1. ПРОВЕРКА: Если денег НЕ хватает
            if (!ShopPlugin.econ.has(player, price)) {
                player.sendMessage("§cУ тебя недостаточно денег! Нужно " + price);
                return true;
            }

            // Снимаем деньги
            ShopPlugin.econ.withdrawPlayer(player, price);

            // Даем товар
            player.getInventory().addItem(new ItemStack(Material.DIAMOND));
            player.sendMessage("§aТы купил алмаз за §e" + price + "$");
        }
        return true;
    }
}