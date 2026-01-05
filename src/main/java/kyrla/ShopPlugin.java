package kyrla;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShopPlugin extends JavaPlugin {

    public static Economy econ = null;

    private static ShopPlugin instance;

    @Override
    public void onEnable() {

        instance = this;
        saveDefaultConfig();

        if (!setupEconomy()) {
            //Vaut
            getLogger().severe("ОШИБКА: Не найден Vault или плагин экономики (Essentials)!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("Экономика подключена, можно торговать!");
        // Команды
        getCommand("mymoney").setExecutor(new MoneyCommand());
        getCommand("buydiamond").setExecutor(new BuyCommand());
        getCommand("shop").setExecutor(new ShopCommand());

        getServer().getPluginManager().registerEvents(new ShopListener(), this);
    }

    // Метод, чтобы другие классы могли получить наш плагин
    public static ShopPlugin getInstance() {
        return instance;
    }

    //Vault
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}