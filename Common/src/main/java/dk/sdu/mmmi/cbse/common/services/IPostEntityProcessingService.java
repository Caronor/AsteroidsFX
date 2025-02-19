package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * The {@code IPostEntityProcessingService} interface defines a contract for processing
 * game entities after the main update cycle. This is typically used for logic that needs
 * to occur after all entities have been updated, such as collision detection, score calculation,
 * or applying final transformations.
 *
 * <p>Implementations of this interface are called once per game tick after all entities
 * have been processed by the main entity processing services.</p>
 */
public interface IPostEntityProcessingService {
    /**
     * Processes game entities after the main update cycle. This method is called once per
     * game tick and is typically used for logic that requires entities to be in their final
     * updated state.
     *
     * <p><strong>Preconditions:</strong></p>
     * <ul>
     *   <li>{@code gameData} must not be {@code null}.</li>
     *   <li>{@code world} must not be {@code null}.</li>
     * </ul>
     *
     * <p><strong>Postconditions:</strong></p>
     * <ul>
     *   <li>Entities in the {@code world} may be updated or modified.</li>
     *   <li>No new entities are typically added during this phase.</li>
     *   <li>The {@code gameData} object may be modified (e.g., score updates).</li>
     * </ul>
     *
     * @param gameData The game data containing input states, display settings, and other information.
     * @param world The game world containing all entities.
     */
    void process(GameData gameData, World world);
}
