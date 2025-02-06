package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;
import java.util.Collection;
import java.util.ServiceLoader;
import static java.util.stream.Collectors.toList;

// Class updates asteroid movement and manages their behavior

public class EnemyProcessor implements IEntityProcessingService {
    private final Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            // Change direction occasionally
            if (random.nextDouble() < 0.2) {
                enemy.setRotation(enemy.getRotation() + (random.nextDouble() * 60 -30));
            }

            //Move forward
            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));

            enemy.setX(enemy.getX() + changeX * 0.5);
            enemy.setY(enemy.getY() + changeY * 0.5);

            //Wrap around screen
            if (enemy.getX() < 0) {
                enemy.setX(gameData.getDisplayWidth());
            }
            if (enemy.getX() > gameData.getDisplayWidth()) {
                enemy.setX(0);
            }
            if (enemy.getY() < 0) {
                enemy.setY(gameData.getDisplayHeight());
            }
            if (enemy.getY() > gameData.getDisplayHeight()) {
                enemy.setY(0);
            }
            if (random.nextDouble() < 0.02) {
                shootBullet(enemy, gameData, world);
            }
        }
    }

    private void shootBullet(Entity enemy, GameData gameData, World world) {
        getBulletSPIs().stream().findFirst().ifPresent(
                spi -> {
                    Entity bullet = spi.createBullet(enemy, gameData);
                    world.addEntity(bullet);
                    System.out.println("Enemy fired a bullet at (" + enemy.getX() + ", " + enemy.getY() + ")");
                }
        );
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
