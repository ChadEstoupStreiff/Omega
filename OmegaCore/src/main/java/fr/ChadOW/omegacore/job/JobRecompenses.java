package fr.ChadOW.omegacore.job;

import fr.ChadOW.api.enums.Job;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.HashMap;

public class JobRecompenses {
    private static HashMap<Job, JobRecompenses> list;

    static void init() {
        list = new HashMap<>();
        Arrays.stream(Job.values()).forEach(JobRecompenses::new);
    }

    public static JobRecompenses getRecompense(Job job) {
        return list.get(job);
    }


    private final Job job;
    private final HashMap<Object, JobRecompensesValues> values;

    public JobRecompenses(Job job) {
        this.job = job;
        this.values = createHashMap(job);
        list.put(job, this);
    }

    private HashMap<Object, JobRecompensesValues> createHashMap(Job job) {

        final HashMap<Object, JobRecompensesValues> hashMap = new HashMap<>();

        switch (job) {
            case MINOR:
                hashMap.put(Material.COAL_ORE, new JobRecompensesValues(0.2f, 0.2f));
                hashMap.put(Material.IRON_ORE, new JobRecompensesValues(2, 7));
                hashMap.put(Material.GOLD_ORE, new JobRecompensesValues(30, 30));
                hashMap.put(Material.NETHER_GOLD_ORE, new JobRecompensesValues(30, 30));
                hashMap.put(Material.LAPIS_ORE, new JobRecompensesValues(10, 10));
                hashMap.put(Material.REDSTONE_ORE, new JobRecompensesValues(3, 3));
                hashMap.put(Material.DIAMOND_ORE, new JobRecompensesValues(20, 20));
                hashMap.put(Material.EMERALD_ORE, new JobRecompensesValues(50, 50));
                hashMap.put(Material.NETHER_QUARTZ_ORE, new JobRecompensesValues(10, 7));
                hashMap.put(Material.ANCIENT_DEBRIS, new JobRecompensesValues(75, 75));
                break;
            case LUMBERJACK:
                hashMap.put(Material.OAK_LOG, new JobRecompensesValues(1, 1));
                hashMap.put(Material.SPRUCE_LOG, new JobRecompensesValues(1, 1));
                hashMap.put(Material.BIRCH_LOG, new JobRecompensesValues(1, 1));
                hashMap.put(Material.JUNGLE_LOG, new JobRecompensesValues(1, 1));
                hashMap.put(Material.ACACIA_LOG, new JobRecompensesValues(1, 1));
                hashMap.put(Material.DARK_OAK_LOG, new JobRecompensesValues(1, 1));
                hashMap.put(Material.CRIMSON_HYPHAE, new JobRecompensesValues(5, 5));
                hashMap.put(Material.WARPED_HYPHAE, new JobRecompensesValues(5, 5));
                break;
            case TERRAFORMER:
                hashMap.put(Material.GRASS_BLOCK, new JobRecompensesValues(0.1f, 0.5f));
                hashMap.put(Material.DIRT, new JobRecompensesValues(0.01f, 0.01f));
                hashMap.put(Material.STONE, new JobRecompensesValues(0.2f, 0.1f));
                hashMap.put(Material.GRANITE, new JobRecompensesValues(1, 0.5f));
                hashMap.put(Material.DIORITE, new JobRecompensesValues(1, 0.5f));
                hashMap.put(Material.ANDESITE, new JobRecompensesValues(1, 0.5f));
                hashMap.put(Material.PACKED_ICE, new JobRecompensesValues(5, 2.5f));
                hashMap.put(Material.SAND, new JobRecompensesValues(0.1f, 0.05f));
                hashMap.put(Material.SANDSTONE, new JobRecompensesValues(0.4f, 0.2f));
                hashMap.put(Material.RED_SAND, new JobRecompensesValues(1, 0.5f));
                hashMap.put(Material.NETHERRACK, new JobRecompensesValues(0.1f, 0.05f));
                hashMap.put(Material.SNOW_BLOCK, new JobRecompensesValues(0.1f, 0.05f));
                hashMap.put(Material.CLAY, new JobRecompensesValues(2, 1));
                hashMap.put(Material.SOUL_SAND, new JobRecompensesValues(2, 1));
                hashMap.put(Material.END_STONE, new JobRecompensesValues(1, 0.5f));
                hashMap.put(Material.MYCELIUM, new JobRecompensesValues(3, 1.5f));
                hashMap.put(Material.TERRACOTTA, new JobRecompensesValues(1, 0.5f));
                hashMap.put(Material.RED_SANDSTONE, new JobRecompensesValues(0.25f, 0.25f));
                break;
            case HUNTER:
                hashMap.put(EntityType.CHICKEN, new JobRecompensesValues(5, 2));
                hashMap.put(EntityType.COD, new JobRecompensesValues(10, 7));
                hashMap.put(EntityType.MUSHROOM_COW, new JobRecompensesValues(7, 2));
                hashMap.put(EntityType.COW, new JobRecompensesValues(5, 2));
                hashMap.put(EntityType.PIG, new JobRecompensesValues(5, 2));
                hashMap.put(EntityType.SALMON, new JobRecompensesValues(10, 7));
                hashMap.put(EntityType.CAVE_SPIDER, new JobRecompensesValues(10, 4));
                hashMap.put(EntityType.ENDERMAN, new JobRecompensesValues(10, 4));
                hashMap.put(EntityType.SPIDER, new JobRecompensesValues(10, 4));
                hashMap.put(EntityType.PIGLIN, new JobRecompensesValues(10, 4));
                hashMap.put(EntityType.ZOMBIE, new JobRecompensesValues(10, 4));
                hashMap.put(EntityType.CREEPER, new JobRecompensesValues(10, 4));
                hashMap.put(EntityType.SKELETON, new JobRecompensesValues(10, 4));
                //hashMap.put(EntityType.PIGLIN_BRUTE, new JobRecompensesValues(15, 7));
                hashMap.put(EntityType.BLAZE, new JobRecompensesValues(15, 7));
                hashMap.put(EntityType.WITCH, new JobRecompensesValues(15, 7));
                hashMap.put(EntityType.HUSK, new JobRecompensesValues(15, 7));
                hashMap.put(EntityType.SLIME, new JobRecompensesValues(20, 10));
                hashMap.put(EntityType.DROWNED, new JobRecompensesValues(20, 10));
                hashMap.put(EntityType.GUARDIAN, new JobRecompensesValues(35, 17));
                hashMap.put(EntityType.HOGLIN, new JobRecompensesValues(50, 25));
                hashMap.put(EntityType.MAGMA_CUBE, new JobRecompensesValues(30, 20));
                hashMap.put(EntityType.PHANTOM, new JobRecompensesValues(30, 20));
                hashMap.put(EntityType.PILLAGER, new JobRecompensesValues(45, 30));
                hashMap.put(EntityType.RAVAGER, new JobRecompensesValues(100, 100));
                hashMap.put(EntityType.SHULKER, new JobRecompensesValues(60, 60));
                hashMap.put(EntityType.EVOKER, new JobRecompensesValues(45, 45));
                hashMap.put(EntityType.VINDICATOR, new JobRecompensesValues(45, 45));
                hashMap.put(EntityType.WITHER_SKELETON, new JobRecompensesValues(60, 60));
                hashMap.put(EntityType.ELDER_GUARDIAN, new JobRecompensesValues(100, 100));
                hashMap.put(EntityType.WITHER, new JobRecompensesValues(2000, 2000));
                hashMap.put(EntityType.ENDER_DRAGON, new JobRecompensesValues(10000, 10000));
                break;
        }
        return hashMap;
    }

    public JobRecompensesValues getValue(Object object) {
        return values.getOrDefault(object, null);
    }

    public HashMap<Object, JobRecompensesValues> getValues() {
        return values;
    }

    public Job getJob() {
        return job;
    }

    static class JobRecompensesValues {

        private final float exp;
        private final float money;

        /**
         *
         * Constructor
         *
         * @param exp
         * @param money
         */
        private JobRecompensesValues(float exp, float money) {
            this.exp = exp;
            this.money = money;
        }

        /**
         * Returns the EXP of a Job
         *
         * @return exp
         */
        public float getExp() {
            return exp;
        }

        /**
         * Returns the Money of a Job
         *
         * @return money
         */
        public float getMoney() {
            return money;
        }
    }
}
