package net.manaita_plus_neo;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue crafting_doubling = BUILDER
            .comment("CraftingDoubling")
            .defineInRange("crafting_doubling_value", 64, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue furnace_doubling = BUILDER
            .comment("FurnaceDoubling")
            .defineInRange("furnace_doubling_value", 64, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue brewing_doubling = BUILDER
            .comment("BrewingDoubling")
            .defineInRange("brewing_doubling_value", 64, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue destroy_doubling = BUILDER
            .comment("DestroyDoubling")
            .defineInRange("destroy_doubling_value", 4, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue source_doubling = BUILDER
            .comment("SourceDoubling")
            .defineInRange("source_doubling_value", 64, 1, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int crafting_doubling_value = 64;
    public static int furnace_doubling_value = 64;
    public static int brewing_doubling_value = 64;
    public static int destroy_doubling_value = 4;
    public static int source_doubling_value = 64;

    public static void onLoad() {
        if (crafting_doubling != null) crafting_doubling_value = crafting_doubling.get();
        if (furnace_doubling != null) furnace_doubling_value = furnace_doubling.get();
        if (brewing_doubling != null) brewing_doubling_value = brewing_doubling.get();
        if (destroy_doubling != null) destroy_doubling_value = destroy_doubling.get();
        if (source_doubling != null) source_doubling_value = source_doubling.get();
    }
}