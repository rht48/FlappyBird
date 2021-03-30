package model.ia;

import model.ia.neat.Neat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestNeatConfig {
    private Neat neat;

    @Test
    void testInitNeatConfig() {
        assertNull(neat);
        Neat.load("src/test/resources/neat_config_test.json");
        neat = Neat.getInstance();
        assertNotNull(neat);
        assertEquals(3, neat.inputs);
    }
}
