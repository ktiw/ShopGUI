package kyrla;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // для проверки экономики
            if (ShopPlugin.econ == null) {
               player.sendMessage("§cПлагин Essentials не найден");
               return true;
            }
            // Присваеваем значение
            double balance = ShopPlugin.econ.getBalance(player);

            player.sendMessage("§aТвой баланс: §e" + balance + "$");
        }
        return true;
    }
}
