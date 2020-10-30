package club.novola.zori.util;

import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatus {
    private List<String> friends;
    private List<String> enemies;

    public PlayerStatus(){
        friends = new ArrayList<>();
        enemies = new ArrayList<>();
    }

    public List<String> getFriends() {
        return friends;
    }

    public List<String> getEnemies() {
        return enemies;
    }

    public void addFriend(String name){
        friends.add(name);
    }

    public void addEnemy(String name){
        enemies.add(name);
    }

    public void delFriend(String name){
        friends.remove(name);
    }

    public void delEnemy(String name){
        enemies.remove(name);
    }

    public boolean isEnemyInRange(double range){
            for(EntityPlayer p : Wrapper.getWorld().playerEntities){
                if(Wrapper.getPlayer().getDistance(p) > range) continue;
                if(enemies.contains(p.getName())) return true;
            }
        return false;
    }

    /**
     * @param name - name of the target player
     * @return 1 = friend, -1 = enemy, 0 = neutral
     */
    public int getStatus(String name){
        if(friends.contains(name)) return 1;
        else if(enemies.contains(name)) return -1;
        else return 0;
    }
}
