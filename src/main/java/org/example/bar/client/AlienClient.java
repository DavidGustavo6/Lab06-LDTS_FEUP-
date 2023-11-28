package org.example.bar.client;

import org.example.StringDrink;
import org.example.StringRecipe;
import org.example.bar.Bar;
import org.example.bar.strategy.OrderingStrategy;
import org.example.bar.StringBar;

public abstract class AlienClient implements Client {
    private OrderingStrategy strategy;

    public AlienClient() {
        this.strategy = createOrderingStrategy();
    }

    @Override
    public void happyHourStarted(Bar bar) {
        strategy.happyHourStarted((StringBar) bar);
    }

    @Override
    public void happyHourEnded(Bar bar) {
        strategy.happyHourEnded((StringBar) bar);
    }

    @Override
    public void wants(StringDrink drink, StringRecipe recipe, StringBar bar) {
        strategy.wants(drink, recipe, bar);
    }

    protected abstract  OrderingStrategy createOrderingStrategy();
}
