package fr.ChadOW.omegacore.job;

import fr.ChadOW.api.accounts.JobAccount;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Job;
import fr.ChadOW.cinventory.CContent.CInventory;
import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.economie.Eco;
import fr.ChadOW.omegacore.job.bukkit.CommandJob;
import fr.ChadOW.omegacore.job.bukkit.JobListener;
import fr.ChadOW.omegacore.utils.OmegaUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class JobManager {

    public static final String prefix = "§6[Métier] §f";
    private static HashMap<Job, CInventory> statisticsJobGUI;

    public JobManager(P i) {
        i.getCommand("job").setExecutor(new CommandJob());
        JobRecompenses.init();
        JobPayer.init();
        initStatistiquesGUI();
        i.getServer().getPluginManager().registerEvents(new JobListener(), i);
    }

    /**
     *
     * To create the Item of a specific Job
     * to be added later in a GUI
     *
     * @param job
     * @param material
     * @return item
     */
    private CItem createItemFromJob(Job job, Material material) {
        CItem item = new CItem(
                new ItemCreator(material, 0)
                        .setName(job.getName())
                        .setLores(Arrays.asList(
                                "§7",
                                "§c► §fClique §agauche §fpour voir les §bstatistiques§f.",
                                "§c► §fClique §adroit §fpour §brejoindre §fle métier."
                        ))
        );
        item.addEvent((cInventory, cItem, player, clickContext) -> {
            if (clickContext.getClickType().isRightClick()) {
                OmegaUtils.confirmBeforeExecute(
                        player,
                        "Rejoindre " + job.getName() + "§f.",
                        null,
                        Arrays.asList(
                                "§7",
                                "§cConséquences:",
                                "§f  - Vous quittez votre métier actuel",
                                "§f  - Vous revenez au niveau 0"
                        ),
                        null,
                        (cInventoryBis, cItemBis, playerBis, clickContextBis) -> {
                            playerBis.sendMessage(prefix + "Vous confirmez rejoindre le métier " + job.getName() + "§f...");
                            UserAccount.getAccount(playerBis.getUniqueId()).getJobAccount().setJob(job);
                            playerBis.sendMessage(prefix + "Vous avez rejoins le métier " + job.getName() + "§f.");
                        });
            } else if (clickContext.getClickType().isLeftClick()) {
                statisticsJobGUI.get(job).open(player);
            }
        });
        return item;
    }

    public void openMainGUI(Player player) {

        CInventory mainGUI = new CInventory(27, "§8§lMétiers");

        mainGUI.addElement(createItemFromJob(Job.MINOR, Material.IRON_PICKAXE).setSlot(0));
        mainGUI.addElement(createItemFromJob(Job.LUMBERJACK, Material.WOODEN_AXE).setSlot(1));
        mainGUI.addElement(createItemFromJob(Job.TERRAFORMER, Material.IRON_SHOVEL).setSlot(2));
        mainGUI.addElement(createItemFromJob(Job.HUNTER, Material.DIAMOND_SWORD).setSlot(3));
        mainGUI.addElement(createItemFromJob(Job.FARMER, Material.GOLDEN_HOE).setSlot(4));
        mainGUI.addElement(createItemFromJob(Job.COOK, Material.FURNACE).setSlot(5));
        mainGUI.addElement(createItemFromJob(Job.FISHER, Material.FISHING_ROD).setSlot(6));
        mainGUI.addElement(createItemFromJob(Job.BAKER, Material.BAKED_POTATO).setSlot(7));
        mainGUI.addElement(createItemFromJob(Job.ALCHEMIST, Material.BREWING_STAND).setSlot(8));

        JobAccount jobAccount = UserAccount.getAccount(player.getUniqueId()).getJobAccount();
        Job playerJob = jobAccount.getJob();

        CItem item = new CItem(
                new ItemCreator(Material.BARRIER, 0)
                        .setName("§cQuitter votre métier actuel")
                        .setLores(Arrays.asList(
                                "§7",
                                "§c► §fClique §adroit §fpour §bquitter §fle métier " + playerJob.getName() + "."
                        ))
        ).setSlot(21);
        item.addEvent((cInventory, cItem, playerEvent, clickContext) -> {
            if (clickContext.getClickType().isRightClick()) {
                OmegaUtils.confirmBeforeExecute(
                        playerEvent,
                        "Quitter votre métier.",
                        null,
                        Arrays.asList(
                                "§7",
                                "§cConséquences:",
                                "§f  - Vous quittez votre métier actuel",
                                "§f  - Vous revenez au niveau 0"
                        ),
                        null,
                        (cInventoryBis, cItemBis, playerEventBis, clickContextBis) -> {
                            playerEventBis.sendMessage(prefix + "Vous confirmez quitter votre métier...");
                            UserAccount.getAccount(playerEventBis.getUniqueId()).getJobAccount().setJob(Job.NONE);
                            playerEventBis.sendMessage(prefix + "Vous avez quitté votre métier.");
                        });
            }
        });
        mainGUI.addElement(item);

        item = new CItem(
                new ItemCreator(Material.ENDER_CHEST, 0)
                        .setName("§fVous êtes actuellement " + playerJob.getName() + "§f.")
                        .setLores(Arrays.asList(
                                "§7",
                                "§c► §eNiveau: §f" + jobAccount.getLevel(),
                                "§c► §eExpérience: §f" + jobAccount.getExp() + "/" + jobAccount.getExpNeededToNextLevel() + " (" + OmegaUtils.roundDouble(100*jobAccount.getExp()/jobAccount.getExpNeededToNextLevel()) + "%)"
                        ))
        ).setSlot(22);
        mainGUI.addElement(item);

        item = new CItem(
                new ItemCreator(Material.BOOK, 0)
                        .setName("§6Récompenses et statistiques")
                        .setLores(Arrays.asList(
                                "§7",
                                "§c► §fClique §agauche §fpour voir vos §brécompenses§f.",
                                "§c► §fClique §adroit §fpour voir vos §bstatistiques§f."
                        ))
        ).setSlot(23);
        item.addEvent((cInventory, cItem, playerEvent, clickContext) -> {
            if (clickContext.getClickType().isRightClick()) {
                cInventory.close(playerEvent);

                JobAccount jobAccountBis = UserAccount.getAccount(player.getUniqueId()).getJobAccount();
                double percentage = OmegaUtils.roundDouble(100*jobAccountBis.getExp()/jobAccountBis.getExpNeededToNextLevel());

                playerEvent.sendMessage(prefix + "Statistiques de votre métier.");
                playerEvent.sendMessage("§c► §fVous êtes un " + jobAccountBis.getJob().getName() + "§f de niveau §b" + jobAccountBis.getLevel() + "§f.");
                playerEvent.sendMessage("§c► §fExpérience actuelle: " + jobAccountBis.getExp() + "/" + jobAccountBis.getExpNeededToNextLevel() + " §b" + percentage + "%§f.");

                StringBuilder lastMessage = new StringBuilder("§bNiv." + jobAccountBis.getLevel() + " §a");
                char percentageCar = '⬛';
                for (int i = 1; i <= (int)(percentage/5); i++) {
                    lastMessage.append(percentageCar);
                }
                lastMessage.append("§f");
                for (int i = 20; i > (int)(percentage/5) && i > 0; i--) {
                    lastMessage.append(percentageCar);
                }
                lastMessage.append(" §bNiv.").append(jobAccountBis.getLevel() + 1);

                playerEvent.sendMessage(lastMessage.toString());
            } else if (clickContext.getClickType().isLeftClick()) {
                openStatistiqueGUI(playerEvent);
            }
        });
        mainGUI.addElement(item);

        mainGUI.open(player);
    }

    private void initStatistiquesGUI() {
        statisticsJobGUI = new HashMap<>();

        for (Job job : Job.values()) {
            Set<Object> list = JobRecompenses.getRecompense(job).getValues().keySet();
            CInventory inv = new CInventory((list.size()-1)/9*9 +9, "§8§lMétier §e§l" + job.getName());
            statisticsJobGUI.put(job, inv);

            int counter = 0;
            for (Object obj : list) {
                Material mat = Material.CHEST;
                if (obj instanceof Material) {
                    mat = (Material) obj;
                }
                inv.addElement(new CItem(new ItemCreator(mat, (byte) 0)
                        .setName("§6" + obj)
                        .setLores(Arrays.asList(
                                "§7",
                                "§c► §fExpérience: §b" + JobRecompenses.getRecompense(job).getValue(obj).getExp() + "xp",
                                "§c► §fRécompense: §e" + JobRecompenses.getRecompense(job).getValue(obj).getMoney() + Eco.devise
                        ))).setSlot(counter));
                counter++;
            }
        }
    }

    private void openStatistiqueGUI(Player player) {
        JobAccount jobAccount = UserAccount.getAccount(player.getUniqueId()).getJobAccount();
        Job job = jobAccount.getJob();

        Set<Object> list = JobRecompenses.getRecompense(job).getValues().keySet();
        CInventory inv = new CInventory((list.size()-1)/9*9 +9, "§8§lVos récompenses: " + job.getName());
        statisticsJobGUI.put(job, inv);

        int counter = 0;
        for (Object obj : list) {
            Material mat = Material.CHEST;
            if (obj instanceof Material) {
                mat = (Material) obj;
            }
            inv.addElement(new CItem(new ItemCreator(mat, (byte) 0)
                    .setName("§6" + obj)
                    .setLores(Arrays.asList(
                            "§7",
                            "§c► §fExpérience: §b" + JobRecompenses.getRecompense(job).getValue(obj).getExp() + "xp",
                            "§c► §fRécompense: §e" + jobAccount.applyMoneyBonus(JobRecompenses.getRecompense(job).getValue(obj).getMoney()) + Eco.devise
                    ))).setSlot(counter));
            counter++;
        }

        inv.open(player);
    }
}
