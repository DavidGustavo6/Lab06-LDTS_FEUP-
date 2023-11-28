package org.example.bar.client;

import org.example.bar.strategy.ImpatientStrategy;
import org.example.bar.strategy.OrderingStrategy;

public class RomulanClient extends AlienClient {
    @Override
    protected OrderingStrategy createOrderingStrategy() {

        return new ImpatientStrategy();
    }
}
