package descendedghasts;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.passive.HappyGhastEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;


public class DescendedGhastsClient implements ClientModInitializer {

    public static KeyBinding Descend_Key;
    private static final double Max_Descend_Speed = -0.1;
    private static final double Acceleration = -0.015;

    //public static final Identifier Descending_Packet = Identifier.of("descendedghasts", "descend");

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

            if (client.player != null && Descend_Key != null) {
                boolean isPressingDescend = Descend_Key.isPressed() ||
                        InputUtil.isKeyPressed(client.getWindow(), GLFW.GLFW_KEY_LEFT_CONTROL);

                if (isPressingDescend && client.player.getVehicle() instanceof HappyGhastEntity vehicle) {
                    //ClientPlayNetworking.send(new DescendPayload());

                    if (vehicle.isOnGround()) {
                        Vec3d currentVel = vehicle.getVelocity();
                        vehicle.setVelocity(currentVel.x, 0, currentVel.z);
                    } else {
                        Vec3d currentVel = vehicle.getVelocity();
                        double newVy = Math.max(currentVel.y + Acceleration, Max_Descend_Speed);
                        vehicle.setVelocity(currentVel.x, newVy, currentVel.z);
                    }
                    vehicle.velocityModified = true;
                }
            }
        });

    }
}
