package org.example.bar.client;

import org.example.StringDrink;
import org.example.StringRecipe;
import org.example.bar.Bar;
import org.example.bar.strategy.OrderingStrategy;
import org.example.bar.StringBar;

public class HumanClient implements Client {
    private OrderingStrategy strategy;

    public HumanClient(OrderingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void happyHourStarted(Bar bar) {
        if (bar instanceof StringBar)
            strategy.happyHourStarted((StringBar) bar);
    }

    @Override
    public void happyHourEnded(Bar bar) {
        if (bar instanceof StringBar) {
            strategy.happyHourStarted((StringBar) bar);
        }
    }

    @Override
    public void wants(StringDrink drink, StringRecipe recipe, StringBar bar) {
        strategy.wants(drink, recipe, bar);
    }

}
