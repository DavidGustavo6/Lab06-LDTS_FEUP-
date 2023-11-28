package org.example.bar.client;

import org.example.StringDrink;
import org.example.StringRecipe;
import org.example.bar.BarObserver;
import org.example.bar.StringBar;

public interface Client extends BarObserver {
    void wants(StringDrink drink, StringRecipe recipe, StringBar bar);
}
