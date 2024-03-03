package ru.inno.market;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.inno.market.core.MarketService;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.Order;
import ru.inno.market.model.PromoCodes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MarketServiceTest {
    private MarketService marketService;
    private Client client;

    @BeforeEach
    void setUp() {
        marketService = new MarketService();
        client = new Client(1, "Test Client");
    }

    @Test
    void testCreateOrderFor() {
        int orderId = marketService.createOrderFor(client);
        assertNotNull(marketService.getOrderInfo(orderId));
    }

    @Test
    void testAddItemToOrder() {
        int orderId = marketService.createOrderFor(client);
        Item item = new Item(1, "Test Item", null, 100);
        marketService.addItemToOrder(item, orderId);
        Order order = marketService.getOrderInfo(orderId);
        assertEquals(1, order.getCart().size());
        assertEquals(100, order.getTotalPrice());
    }

    @Test
    void testApplyDiscountForOrder() {
        int orderId = marketService.createOrderFor(client);
        Item item = new Item(1, "Test Item", null, 100);
        marketService.addItemToOrder(item, orderId);
        double totalPriceBeforeDiscount = marketService.applyDiscountForOrder(orderId, PromoCodes.FIRST_ORDER);
        assertEquals(80, totalPriceBeforeDiscount); // 20% discount for the first order
    }

    @Test
    void testGetOrderInfo() {
        int orderId = marketService.createOrderFor(client);
        assertNotNull(marketService.getOrderInfo(orderId));
    }
}
