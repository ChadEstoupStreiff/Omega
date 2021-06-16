package fr.ChadOW.omegacore.economie.commands;

import fr.ChadOW.api.accounts.BankAccount;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.cinventory.CContent.CInventory;
import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.ItemCreator;
import fr.ChadOW.cinventory.events.clickContent.ClickContext;
import fr.ChadOW.omegacore.utils.OmegaUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandRank implements CommandExecutor {
    private final CInventory rankInv;

    public CommandRank() {
        rankInv = new CInventory(27, "§6Grades");

        rankInv.addElement(new CItem(new ItemCreator(Material.BLAZE_ROD, 0)
                .setName(Rank.OLD.getPrefix())
                .setLores(Arrays.asList(
                        "§fPrix : §a500 crédits §fou §a100 000$§f.",
                        "§f",
                        "§bAvantages :",
                        "§b- §fCooldown du RTP mondes libres : §d5 minutes",
                        "§b- §fCooldown du RTP mondes ressources : §d30s",
                        "§b- §fNombre de claims : §d20",
                        "§b- §fNombre de personnes max dans un claim : §d6",
                        "§b- §fNombre de personnes max dans un groupe : §d6",
                        "§f",
                        "§e§o► Clic gauche pour acheter le grade avec des crédits.",
                        "§e§o► Clic droit pour acheter le grade avec des $."
        ))).addEvent((inventoryRepresentation, itemRepresentation, player, clickContext) -> buyGrade(player, Rank.OLD, 500, 100000, clickContext)).setSlot(11));

        rankInv.addElement(new CItem(new ItemCreator(Material.GLISTERING_MELON_SLICE, 0)
                .setName(Rank.LEGEND.getPrefix())
                .setLores(Arrays.asList(
                        "§fPrix : §a1000 crédits §fou §a500 000$§f.",
                        "§f",
                        "§bAvantages :",
                        "§b- §fCooldown du RTP mondes libres : §d2 minutes",
                        "§b- §fCooldown du RTP mondes ressources : §d15s",
                        "§b- §fNombre de claims : §d30",
                        "§b- §fNombre de personnes max dans un claim : §d11",
                        "§b- §fNombre de personnes max dans un groupe : §d11",
                        "§f",
                        "§e§o► Clic gauche pour acheter le grade avec des crédits.",
                        "§e§o► Clic droit pour acheter le grade avec des $."
                ))).addEvent((inventoryRepresentation, itemRepresentation, player, clickContext) -> buyGrade(player, Rank.LEGEND, 1000, 500000, clickContext)).setSlot(13));

        rankInv.addElement(new CItem(new ItemCreator(Material.GOLDEN_APPLE, 0)
                .setName(Rank.MYTH.getPrefix())
                .setLores(Arrays.asList(
                        "§fPrix : §a2000 crédits §fou §a2 000 000$§f.",
                        "§f",
                        "§bAvantages :",
                        "§b- §fCooldown du RTP mondes libres : §d30s",
                        "§b- §fCooldown du RTP mondes ressources : §d5s",
                        "§b- §fNombre de claims : §d50",
                        "§b- §fNombre de personnes max dans un claim : §d26",
                        "§b- §fNombre de personnes max dans un groupe : §d26",
                        "§f",
                        "§e§o► Clic gauche pour acheter le grade avec des crédits.",
                        "§e§o► Clic droit pour acheter le grade avec des $."
                ))).addEvent((inventoryRepresentation, itemRepresentation, player, clickContext) -> buyGrade(player, Rank.MYTH, 2000, 2000000, clickContext)).setSlot(15));
    }

    private void buyGrade(Player player, Rank rank, int creditPrice, int coinPrice, ClickContext clickContext) {
        UserAccount account = UserAccount.getAccount(player.getUniqueId());
        if (account.getRank().getPower() < rank.getPower() && account.getRank().getStaffPower() <= 0) {
            if (clickContext.getClickType().isLeftClick() && UserAccount.getAccount(player.getUniqueId()).getCredits() >= creditPrice) {
                OmegaUtils.confirmBeforeExecute(player, "§aConfirmer l'achat (" + creditPrice + " crédits) ?", null, null, null, (inventoryRepresentation1, itemRepresentation1, player1, clickContext1) -> {
                    UserAccount userAccount = UserAccount.getAccount(player.getUniqueId());
                    userAccount.setRank(rank);
                    userAccount.setCredits(userAccount.getCredits() - creditPrice);
                    player.sendMessage("§6[Boutique]§a Félicitations !§r vous êtes désormais " + rank.getPrefix() + "§r.");
                    OmegaUtils.broadcastServerMessage("§6[Annonce] §d" + player.getName() + "§r vient d'acheter le grade " + rank.getPrefix() + "§r ! ★★★");
                });
            } else if (clickContext.getClickType().isRightClick() && UserAccount.getAccount(player.getUniqueId()).getBankAccount().getAmount() >= coinPrice) {
                OmegaUtils.confirmBeforeExecute(player, "§aConfirmer l'achat (" + coinPrice + "$) ?", null, null, null, (inventoryRepresentation1, itemRepresentation1, player1, clickContext1) -> {
                    UserAccount userAccount = UserAccount.getAccount(player.getUniqueId());
                    BankAccount bankAccount = userAccount.getBankAccount();
                    userAccount.setRank(rank);
                    bankAccount.setAmount(bankAccount.getAmount() - coinPrice);
                    player.sendMessage("§6[Boutique]§a Félicitations !§r vous êtes désormais " + rank.getPrefix() + "§r.");
                    OmegaUtils.broadcastServerMessage("§6[Annonce] §d" + player.getName() + "§r vient d'acheter le grade " + rank.getPrefix() + "§r ! ★★★");
                });
            } else {
                player.sendMessage("§6[Boutique]§c Désolé !§r vous n'avez pas assez d'argent pour acheter le grade " + rank.getPrefix() + ".");
            }
        } else {
            player.sendMessage("§6[Boutique]§c Désolé !§r vous possédez déjà le grade " + rank.getPrefix() + "§r.");
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player)
            rankInv.open((Player) sender);
        return true;
    }
}
