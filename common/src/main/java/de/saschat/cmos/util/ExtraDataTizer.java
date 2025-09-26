package de.saschat.cmos.util;

import io.github.mortuusars.exposure.util.ExtraData;
import io.github.mortuusars.exposure.world.camera.ColorChannel;
import io.github.mortuusars.exposure.world.camera.component.ShutterSpeed;
import io.github.mortuusars.exposure.world.camera.frame.Frame;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ExtraDataTizer {
    PROJECTED(Frame.PROJECTED, (d, t) -> d.get(t).orElse(null)),
    CHROMATIC(Frame.CHROMATIC, (d, t) -> d.get(t).orElse(null)),
    COLOR_CHANNEL(Frame.COLOR_CHANNEL, ExtraDataTizer::custom_colorChannel),
    SHUTTER_SPEED(Frame.SHUTTER_SPEED, ExtraDataTizer::custom_shutterSpeed),
    TIMESTAMP(Frame.TIMESTAMP, (d, t) -> d.get(t).orElse(null)),
    FOCAL_LENGTH(Frame.FOCAL_LENGTH, (d, t) -> d.get(t).orElse(null)),
    FLASH(Frame.FLASH, (d, t) -> d.get(t).orElse(null)),
    SELFIE(Frame.SELFIE, (d, t) -> d.get(t).orElse(null)),
    ON_STAND(Frame.ON_STAND, (d, t) -> d.get(t).orElse(null)),
    POSITION(Frame.POSITION, ExtraDataTizer::custom_position),
    YAW(Frame.YAW, ExtraDataTizer::custom_float),
    LIGHT_LEVEL(Frame.LIGHT_LEVEL, (d, t) -> d.get(t).orElse(null)),
    DAY_TIME(Frame.DAY_TIME, (d, t) -> d.get(t).orElse(null)),
    DIMENSION(Frame.DIMENSION, ExtraDataTizer::custom_resourceLocation),
    BIOME(Frame.BIOME, ExtraDataTizer::custom_resourceLocation),
    WEATHER(Frame.WEATHER, (d, t) -> d.get(t).orElse(null)),
    IN_CAVE(Frame.IN_CAVE, (d, t) -> d.get(t).orElse(null)),
    UNDERWATER(Frame.UNDERWATER, (d, t) -> d.get(t).orElse(null)),
    STRUCTURES(Frame.STRUCTURES, ExtraDataTizer::custom_structures);

    public static Map<String, Object> tizerize(Frame frame) {
        Map<String, Object> ret = new HashMap<>();
        ExtraData data = frame.extraData();
        for (ExtraDataTizer value : values()) {
            Object subret = value.tizer(data);
            if(subret != null) ret.put(value.name(), subret);
        }
        return ret;
    }

    private static Object custom_structures(ExtraData extraData, ExtraData.Type<List<ResourceLocation>> listType) {
        List<ResourceLocation> data;
        if((data = extraData.get(listType).orElse(null)) == null) return null;

        Map<Integer, String> ret = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            ret.put(i + 1, data.get(i).toString());
        }
        return ret;
    }

    private static Object custom_resourceLocation(ExtraData extraData, ExtraData.Type<ResourceLocation> resourceLocationType) {
        ResourceLocation data;
        if((data = extraData.get(resourceLocationType).orElse(null)) == null) return null;
        return data.getNamespace();
    }

    private static Object custom_float(ExtraData extraData, ExtraData.Type<Float> floatType) {
        Float data;
        if((data = extraData.get(floatType).orElse(null)) == null) return null;
        return Double.valueOf(data.doubleValue());
    }

    private static Object custom_position(ExtraData extraData, ExtraData.Type<Vec3> vec3Type) {
        Vec3 data;
        if((data = extraData.get(vec3Type).orElse(null)) == null) return null;
        return CCUtil.tizPos(data);
    }

    private static Object custom_shutterSpeed(ExtraData extraData, ExtraData.Type<ShutterSpeed> shutterSpeedType) {
        ShutterSpeed data;
        if((data = extraData.get(shutterSpeedType).orElse(null)) == null) return null;

        Map<String, Object> ret = new HashMap<>();
        ret.put("name", data.getSerializedName());
        ret.put("brightness", data.getBrightness());
        ret.put("duration_t", data.getDurationTicks());
        ret.put("duration_ms", data.getDurationMilliseconds());
        ret.put("notation", data.getNotation());
        ret.put("stops", data.getStops());
        ret.put("ticks", data.shouldCauseTickingSound());

        return ret;
    }

    private static Object custom_colorChannel(ExtraData extraData, ExtraData.Type<ColorChannel> colorChannelType) {
        ColorChannel data;
        if((data = extraData.get(colorChannelType).orElse(null)) == null) return null;

        Map<String, Object> ret = new HashMap<>();
        ret.put("color", data.getRepresentationColor());
        ret.put("name", data.getSerializedName());
        ret.put("shader", data.getShader().toString());

        return ret;
    }

    Converter<Object> converter;
    ExtraData.Type<Object> type;

    <T> ExtraDataTizer(ExtraData.Type<T> type, Converter<T> converter) {
        this.converter = (Converter<Object>) converter;
        this.type = (ExtraData.Type<Object>) type;
    }

    Object tizer(ExtraData extraData) {
        return converter.convert(extraData, type);
    }


    interface Converter<T> {
        Object convert(ExtraData data, ExtraData.Type<T> type);
    }
}
