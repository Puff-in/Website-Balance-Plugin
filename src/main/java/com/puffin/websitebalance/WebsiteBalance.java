package com.puffin.websitebalance;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.puffin.websitebalance.commands.AddWebsiteBalanceCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class WebsiteBalance extends HabboPlugin implements EventListener {
    public static WebsiteBalance INSTANCE = null;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Emulator.getPluginManager().registerEvents(this, this);

        if (Emulator.isReady) {
            this.checkDatabase();
        }

        Emulator.getLogging().logStart("[WebsiteBalance] Started Website Balance Plugin!");
    }

    @Override
    public void onDisable() {
        Emulator.getLogging().logShutdownLine("[WebsiteBalance] Stopped Website Balance Plugin!");
    }

    @EventHandler
    public static void onEmulatorLoaded(EmulatorLoadedEvent event) {
        INSTANCE.checkDatabase();
    }

    @Override
    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }

    private void checkDatabase() {
        boolean reloadPermissions = false;

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("ALTER TABLE  `emulator_texts` CHANGE  `value`  `value` VARCHAR( 4096 ) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL");
        } catch (SQLException e) {
        }

        updateWebsiteBalanceTable();
        
        Emulator.getTexts().register("commands.keys.cmd_addwebsitebalance", "addbalance;balance");
        Emulator.getTexts().register("commands.description.cmd_addwebsitebalance", ":addbalance <username> <amount>");
        Emulator.getTexts().register("commands.error.cmd_addwebsitebalance.missing_amount", "Please specify a dollar amount to add");
        Emulator.getTexts().register("commands.error.cmd_addwebsitebalance.invalid_amount", "The amount must be a number");
        Emulator.getTexts().register("commands.error.cmd_addwebsitebalance.positive_amount", "The amount must be a positive number");
        Emulator.getTexts().register("commands.error.cmd_addwebsitebalance.user_not_found", "Could not find %username%");
        Emulator.getTexts().register("commands.succes.cmd_addwebsitebalance.text", "%username%'s website balance has been updated with %amount% USD.");
        
        reloadPermissions = this.registerPermission("cmd_addwebsitebalance", "'0', '1'", "0", reloadPermissions);
        
        CommandHandler.addCommand(new AddWebsiteBalanceCommand("cmd_addwebsitebalance", Emulator.getTexts().getValue("commands.keys.cmd_addwebsitebalance").split(";")));

        if (reloadPermissions) {
            Emulator.getGameEnvironment().getPermissionsManager().reload();
        }
    }

    private boolean registerPermission(String name, String options, String defaultValue, boolean defaultReturn) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("ALTER TABLE  `permissions` ADD  `" + name + "` ENUM(  " + options + " ) NOT NULL DEFAULT  '" + defaultValue + "'")) {
                statement.execute();
                return true;
            }
        } catch (SQLException e) {
        }

        return defaultReturn;
    }

    private void updateWebsiteBalanceTable() {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("ALTER TABLE `USERS` MODIFY `website_balance` BIGINT NOT NULL DEFAULT 0")) {
            statement.execute();
            Emulator.getLogging().logDebugLine("[WebsiteBalance] Ensured 'website_balance column is BIGINT.");
        } catch (SQLException e) {
        }
    }

    public static void main(String[] args) {
        System.out.println("Don't run this seperately");
    }
}