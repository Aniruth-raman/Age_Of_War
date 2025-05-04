package org.aniruth;

import java.util.Objects;

public class Platoon {
    String type;
    int soldiers;

    public Platoon(String type, int soldiers) {
        this.type = type;
        this.soldiers = soldiers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Platoon platoon = (Platoon) o;
        return soldiers == platoon.soldiers && Objects.equals(type, platoon.type);
    }

    @Override
    public String toString() {
        return type + "#" + soldiers + ";";
    }
}