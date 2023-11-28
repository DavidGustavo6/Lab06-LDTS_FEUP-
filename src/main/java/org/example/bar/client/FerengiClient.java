package org.example.bar.client;

import org.example.bar.strategy.OrderingStrategy;
import org.example.bar.strategy.SmartStrategy;

public class FerengiClient extends AlienClient {
    @Override
    protected OrderingStrategy createOrderingStrategy() {
        return new SmartStrategy();
    }
}
