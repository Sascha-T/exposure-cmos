package de.saschat.cmos.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class CCUtil {
    private CCUtil() {}
    public static Map<Integer, Double> tizPos(BlockPos posy) {
        return tizPos(new Vec3(posy.getX(), posy.getY(), posy.getZ()));
    }

    public static Map<Integer, Double> tizPos(Vec3 data) {
        Map<Integer, Double> pos = new HashMap<>();
        pos.put(1, data.x);
        pos.put(2, data.y);
        pos.put(3, data.z);
        return pos;
    }
}
