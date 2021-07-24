package fr.ChadOW.omegacore.job;

import fr.ChadOW.api.accounts.BankAccount;
import fr.ChadOW.api.accounts.JobAccount;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Job;
import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.economie.Eco;
import fr.ChadOW.omegacore.utils.OmegaUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class JobPayer {

    private static HashMap<Player, ArrayList<Object>> events;

    public static void addEvent(Player player, Object object) {
        if (!events.containsKey(player))
            events.put(player, new ArrayList<>());
        events.get(player).add(object);
    }

    static void init() {
        events = new HashMap<>();
        Bukkit.getScheduler().runTaskTimer(P.getInstance(), () -> {
            if (events.size() > 0) {
                for (Player player : events.keySet()) {
                    UserAccount userAccount = UserAccount.getAccount(player.getUniqueId());
                    JobAccount jobAccount = userAccount.getJobAccount();
                    BankAccount bankAccount = userAccount.getBankAccount();
                    Job job = jobAccount.getJob();

                    double sommeExp = 0d;
                    double sommeMoney = 0d;

                    for (Object obj : events.get(player)) {
                        JobRecompenses.JobRecompensesValues recompensesValues = JobRecompenses.getRecompense(job).getValue(obj);
                        if (recompensesValues != null) {
                            sommeExp += recompensesValues.getExp();
                            sommeMoney += jobAccount.applyMoneyBonus(recompensesValues.getMoney());
                        }
                    }

                    if (sommeExp > 0 || sommeMoney > 0) {
                        OmegaUtils.sendBarMessage(player, JobManager.prefix + "Expérience: §b" + sommeExp + "xp§f, récompense: §e" + sommeMoney + Eco.devise);

                        jobAccount.addExp(sommeExp);
                        bankAccount.addAmount(sommeMoney);
                    }
                }
                events = new HashMap<>();
            }
        }, 20, 20);
    }
}
