package kr.enak.plugintemplate.models;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class StorableItem implements StorableData {
    private ItemStack stack;

    public StorableItem(ItemStack stack) {
        this.stack = stack;
    }

    public StorableItem(Map<String, Object> map) {
        this.stack = ((ItemStack) map.get("item")).clone();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("item", this.stack);
        return map;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack.clone();
    }
}
