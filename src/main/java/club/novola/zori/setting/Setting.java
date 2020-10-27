package club.novola.zori.setting;

import club.novola.zori.module.Module;

public class Setting<T> {
    private final String name;
    private final Module parent;
    private T value;
    private T min;
    private T max;

    public Setting(String name, T value, Module parent){
        this.name = name;
        this.value = value;
        this.parent = parent;
    }

    public Setting(String name, T value, T min, T max, Module parent){
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Module getParent(){
        return parent;
    }

    public T getValue() {
        return value;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public T setValue(T value){
        this.value = value;
        return value;
    }
}
