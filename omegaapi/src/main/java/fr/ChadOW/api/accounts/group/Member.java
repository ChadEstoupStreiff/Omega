package fr.ChadOW.api.accounts.group;

import java.util.UUID;

public class Member {
    private final UUID uuid;
    private HIERARCHY hierarchy;

    public Member(UUID uuid) {
        this(uuid, HIERARCHY.VISITOR);
    }

    public Member(UUID uuid, HIERARCHY hierarchy) {
        this.uuid = uuid;
        this.hierarchy = hierarchy;
    }

    public UUID getUuid() {
        return uuid;
    }

    public HIERARCHY getHierarchy() {
        return hierarchy;
    }

    void setHierarchy(HIERARCHY hierarchy) {
        this.hierarchy = hierarchy;
    }

    public enum HIERARCHY {
        LEADER(),
        ADMIN(),
        CONFIRMED(),
        VISITOR();

        public HIERARCHY promote() {
            switch (this) {
                case ADMIN:
                    return LEADER;
                case CONFIRMED:
                    return ADMIN;
                case VISITOR:
                    return CONFIRMED;
                default:
                    return null;
            }
        }

        public HIERARCHY demote() {

            switch (this) {
                case LEADER:
                    return ADMIN;
                case ADMIN:
                    return CONFIRMED;
                case CONFIRMED:
                    return VISITOR;
                default:
                    return null;
            }
        }
    }
}
