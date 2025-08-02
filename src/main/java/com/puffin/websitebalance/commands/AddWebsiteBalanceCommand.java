package com.puffin.websitebalance.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.habbohotel.users.HabboManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddWebsiteBalanceCommand extends Command {

    public AddWebsiteBalanceCommand(String permission, String[] keys) {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        if (strings.length != 3) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_addwebsitebalance.missing_amount"));
            return true;
        }

        HabboInfo habboInfo = HabboManager.getOfflineHabboInfo(strings[1]);

        if (habboInfo == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_addwebsitebalance.user_not_found").replace("%username%", strings[1]));
            return true;
        }

        int amountToAdd;
        try {
            amountToAdd = Integer.parseInt(strings[2]);
        } catch (NumberFormatException e) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_addwebsitebalance.invalid_amount"));
            return true;
        }
        
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET website_balance = website_balance + ? WHERE id = ?")) {
            statement.setInt(1, amountToAdd);
            statement.setInt(2, habboInfo.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            Emulator.getLogging().logSQLException(e);
            return true;
        }

        gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.succes.cmd_addwebsitebalance.text").replace("%username%", habboInfo.getUsername()).replace("%amount%", String.valueOf(amountToAdd)));
        
        return true;
    }
}