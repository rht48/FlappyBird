package model.ia;

import graphics.TexturedModel;
import model.game.Game;
import model.game.IAGame;
import model.ia.neat.Neat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestNeat {
    private Game game;
    private Neat neatSingleton;

    @BeforeEach
    void initPopulation() {
        this.neatSingleton = Neat.getInstance();
        this.game = new IAGame(new TexturedModel(new PImage()), new TexturedModel(new PImage()));
    }

    @Test
    void testInitNeat() {
        assertNotNull(neatSingleton);
        assertNotNull(neatSingleton.getPopulationList());
    }

    @Test
    void testGeneratePopulation() {
        int nbGeneration = neatSingleton.getNbGeneration();
        assertEquals(nbGeneration, neatSingleton.getPopulationList().size());
        this.neatSingleton.init((IAGame) game);
        assertEquals(nbGeneration + 1, neatSingleton.getNbGeneration());
        assertEquals(nbGeneration + 1, neatSingleton.getPopulationList().size());
        assertEquals(300, neatSingleton.getPopulationList().get(nbGeneration).getSize());
    }
}
