package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Class spawns asteroids in the world

public class AsteroidPlugin implements IGamePluginService {
    @Override
    //Creates a new asteroid when the game starts
    public void start(GameData gameData, World world) {
        ArrayList<Entity> asteroids = createAsteroid(gameData);
        for (Entity asteroid : asteroids) {
            world.addEntity(asteroid);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        //Removes all asteroids from the game when stopping
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    private ArrayList<Entity> createAsteroid(GameData gameData) {
        ArrayList<Entity> asteroids = new ArrayList<>();
        Random rnd = new Random();

        for (int i = 0; i < 5; i++) {
            Entity asteroid = new Asteroid();

            int size = rnd.nextInt(20) + 15; // Larger asteroids
            asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);

            asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
            asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));
            asteroid.setRadius(size);
            asteroid.setRotation(rnd.nextInt(360));

            asteroids.add(asteroid);
        }
        return asteroids;
    }
}
