package descendedghasts;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.passive.HappyGhastEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.GhastEntity;
import org.lwjgl.glfw.GLFW;


public class DescendedGhastsClient implements ClientModInitializer {

    public static KeyBinding Descend_Key;
    private static final double Max_Descend_Speed = -0.5;
    private static final double Acceleration = -0.015;

    @Override
    public void onInitializeClient() {
        System.out.println("It's working! It's working!");

        Descend_Key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.descendedghasts.descend",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_CONTROL,
                KeyBinding.Category.MOVEMENT
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
                if (Descend_Key.isPressed() || InputUtil.isKeyPressed(client.getWindow(), GLFW.GLFW_KEY_LEFT_CONTROL)) {

                    if (client.player != null){
                        if (client.player.hasVehicle()) {
                            Entity vehicle = client.player.getVehicle();

                            if (vehicle instanceof HappyGhastEntity) {
                                double currentVy = vehicle.getVelocity().getY();
                                double newVy = currentVy + Acceleration;
                                newVy = Math.max(newVy, Max_Descend_Speed);

                                vehicle.setVelocity(
                                        vehicle.getVelocity().getX(),
                                        newVy,
                                        vehicle.getVelocity().getZ()
                                );
                            }
                        }
                    }
                }
        });

    }
}
