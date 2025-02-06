package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        System.out.println("EnemyPlugin: Adding enemy...");
        enemy = createEnemyShip(gameData, world);
        world.addEntity(enemy);
        if (world.getEntities().contains(enemy)) {
            System.out.println("Enemy successfully added to world!");
        } else {
            System.out.println("Enemy was NOT added to world!");
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        System.out.println("⚠️ EnemyPlugin.stop() called! Removing enemy...");

        // Remove entities
        world.removeEntity(enemy);
    }

    private Entity createEnemyShip(GameData gameData, World world) {
        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(-5,-5,10,0,-5,5);

        double safeX, safeY;
        boolean isSafe;

        do {
            isSafe = true;
            safeX = Math.random() * gameData.getDisplayWidth();
            safeY = Math.random() * gameData.getDisplayHeight();

            // Check if the spawn location collides with an existing entity
            for (Entity e : world.getEntities()) {
                double dx = safeX - e.getX();
                double dy = safeY - e.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < e.getRadius() + 50) { // Ensure at least 50px distance
                    isSafe = false;
                    break;
                }
            }
        } while (!isSafe); // Keep trying until a safe position is found

        enemyShip.setX(gameData.getDisplayWidth()/2);
        enemyShip.setY(gameData.getDisplayHeight()/2);
        enemyShip.setRadius(8);

        System.out.println("🚀 Created Enemy at: (" + enemyShip.getX() + ", " + enemyShip.getY() + ")");
        return enemyShip;
    }
}
