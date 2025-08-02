# Atom CMS - Website Balance Plugin

[![GitHub release (latest by date)](https://img.shields.io/github/v/release/Puff-in/Website-Balance-Plugin)](https://github.com/Puff-in/Website-Balance-Plugin/releases)
[![GitHub stars](https://img.shields.io/github/stars/Puff-in/Website-Balance-Plugin?style=social)](https://github.com/Puff-in/Website-Balance-Plugin/stargazers)
[![GitHub license](https://img.shields.io/github/license/Puff-in/Website-Balance-Plugin)](https://github.com/Puff-in/Website-Balance-Plugin/blob/main/LICENSE)

## 📖 Introduction

The **Atom CMS Website Balance Plugin** is a simple and useful tool to update a user's Shop Balance on Atom CMS directly from your Habbo Retro Client. It's designed specifically for those using Atom CMS with Arcturus Morningstar.

## 🚀 Installation

1.  **Download the Plugin:** Download the `WebsiteBalanceCommand.jar` file from the [releases page](https://github.com/Puff-in/Website-Balance-Plugin/releases/tag/v1.0.0).

2.  **Place in your "plugins" folder:** Copy the `.jar` file and place it inside the `plugins` folder of your emulator folder.

3.  **Reboot your Emulator:** Restart your emulator to load the new plugin.

## 🛠️ Usage

Once the plugin is running, you can update a user's Shop Balance with a simple command.

1.  **Update Permissions:**
    -   Go to your `permissions` table in the database.
    -   Find the `cmd_addwebsitebalance` permission and set its value to `1` for the ranks you want to have access to this command.
    -   Run the `:update_permissions` command in your client to apply the changes.

2.  **Command Execution:**
    -   To use the command, simply type `:addbalance <username> <amount>` in your client.
    -   `<username>` is the username of the user you want to edit.
    -   `<amount>` is the value to add or subtract. This can be a positive or negative number. For example, use `-50` to subtract 50 USD.

3.  **Balance Updated:**
    -   A message will appear in the client confirming the success or failure of the command.

Enjoy this simple yet powerful plugin!
