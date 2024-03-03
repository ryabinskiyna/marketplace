package ru.inno.market;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.Order;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Order order;
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client(1, "Test Client");
        order = new Order(1, client);
    }

    @Test
    void testAddItem() {
        Item item = new Item(1, "Test Item", null, 100);
        order.addItem(item);
        Map<Item, Integer> cart = order.getCart();
        assertTrue(cart.containsKey(item));
        assertEquals(1, (int) cart.get(item));
    }

    @Test
    void testApplyDiscount() {
        double initialTotalPrice = 200;
        double discount = 0.1;
        order = new Order(1, client);
        order.addItem(new Item(1, "Test Item 1", null, 100));
        order.addItem(new Item(2, "Test Item 2", null, 100));
        order.applyDiscount(discount);
        assertEquals(initialTotalPrice * (1 - discount), order.getTotalPrice());
    }

    @Test
    void testGetClient() {
        assertEquals(client, order.getClient());
    }

    @Test
    void testGetTotalPrice() {
        assertEquals(0, order.getTotalPrice());
        order.addItem(new Item(1, "Test Item", null, 100));
        assertEquals(100, order.getTotalPrice());
    }

    @Test
    void testIsDiscountApplied() {
        assertFalse(order.isDiscountApplied());
        order.applyDiscount(0.1);
        assertTrue(order.isDiscountApplied());
    }
}
