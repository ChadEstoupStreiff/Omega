package fr.ChadOW.api.accounts.group;

import com.google.gson.Gson;
import fr.ChadOW.api.accounts.BankAccount;
import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.api.managers.SQLManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class Group {
    private final String ID;
    private String name;
    private final String bankID;
    private Member chef;
    private ArrayList<Member> members;

    public Group(String ID, String name, String bankID, Member chef, ArrayList<Member> members) {
        this.ID = ID;
        this.name = name;
        this.bankID = bankID;
        this.chef = chef;
        this.members = members;
    }

    public void promoteMember(Member member) {
        if (members.contains(member)) {
            member.setHierarchy(member.getHierarchy().promote());
        }
        sendToRedis();
    }

    public void demoteMember(Member member) {
        if (members.contains(member)) {
            member.setHierarchy(member.getHierarchy().demote());
        }
        sendToRedis();
    }

    public boolean containMember(UUID uuid) {
        for (Member member : members) {
            if (member.getUuid().equals(uuid))
                return true;
        }
        return false;
    }

    public void addMember(Member member) {
        if (!containMember(member.getUuid())) {
            members.add(member);
            sendToRedis();
        }
    }

    public void removeMember(Member member) {
        if (containMember(member.getUuid())) {
            members.remove(member);
            sendToRedis();
        }
    }

    public Member getMember(UUID uuid) {
        for (Member member : members)
            if (member.getUuid().equals(uuid))
                return member;
        return null;
    }

    public List<Member> getMembers() {
        return members;
    }

    public Member getChef() {
        return chef;
    }

    public String getBankID() {
        return bankID;
    }

    public BankAccount getBankAccount() {
        return BankAccount.getAccount(getBankID());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sendToRedis();
    }

    public String getID() {
        return ID;
    }

    //ALL DATABASE FCT
    private Group fixGSon() {
        members = JedisManager.getGson().fromJson(String.valueOf(members), ArrayList.class);
        return this;
    }

    public void sendToRedis() {
        JedisManager.getInstance().setValue("Group:" + getID(), JedisManager.getGson().toJson(this));
    }

    public void sendToDb() {
        Gson gson = JedisManager.getGson();
        SQLManager.getInstance().update("UPDATE GROUPS SET name='" + getName() + "', bankID='" + getBankID() + "', chef='" + gson.toJson(getChef()) + "', members='" + gson.toJson(getMembers()) + "' WHERE groupID='" + getID() + "'");
    }

    public static Group getGroup(String id) {
        return getGroupFromRedis(id);
    }

    private static Group getGroupFromRedis(String id) {
        String value = JedisManager.getInstance().getValue("Group:" + id);

        if (value != null)
            return JedisManager.getGson().fromJson(value, Group.class).fixGSon();
        else
            return getGroupFromDb(id);
    }

    public static Group getGroupFromDb(String id) {
        AtomicReference<Group> group = new AtomicReference<>();

        SQLManager sql = SQLManager.getInstance();

        sql.query("SELECT * FROM GROUPS WHERE groupID='" + id + "'", rs -> {
            try {
                if (rs.next()) {
                    Gson gson = JedisManager.getGson();
                    group.set(new Group(
                            rs.getString("groupID"),
                            rs.getString("name"),
                            rs.getString("bankID"),
                            gson.fromJson(rs.getString("chef"), Member.class),
                            gson.fromJson(rs.getString("members"), ArrayList.class)
                    ).fixGSon());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return group.get();
    }

    private static Group createAccount(String id, String name, Member chef) {
        Group group = new Group(id, name, "G" + id, chef, new ArrayList<>());
        group.initInDb();
        group.getBankAccount().setName("Compte de groupe: " + name);
        System.out.println("[OmegaAPI] Group> group " + id + " created");
        return group;
    }

    private void initInDb() {
        Gson gson = JedisManager.getGson();
        SQLManager.getInstance().update("INSERT INTO GROUPS (groupID, name, bankID, chef, members) VALUES ('" + getID() + "', '" + getName() + "', '" + getBankID() + "', '" + gson.toJson(getChef()) + "', '" + gson.toJson(getMembers()) + "')");
    }
}
