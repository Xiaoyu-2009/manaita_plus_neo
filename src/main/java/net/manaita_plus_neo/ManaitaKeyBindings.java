package net.manaita_plus_neo;

import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = ManaitaPlusNeo.MOD_ID)
public class ManaitaKeyBindings {
    
    public static KeyMapping manaitaKey;
    public static KeyMapping manaitaArmorKey;

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        manaitaKey = new KeyMapping(
            "key.manaita", 
            org.lwjgl.glfw.GLFW.GLFW_KEY_X, 
            "key.categories.manaita_plus_neo"
        );
        event.register(manaitaKey);
        
        manaitaArmorKey = new KeyMapping(
            "key.manaita.armor", 
            org.lwjgl.glfw.GLFW.GLFW_KEY_Y, 
            "key.categories.manaita_plus_neo"
        );
        event.register(manaitaArmorKey);
    }
}