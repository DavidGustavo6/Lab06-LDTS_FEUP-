package org.example.transformer;
import org.example.StringDrink;
import org.example.transformer.StringTransformer;

import java.lang.StringBuilder;
public class StringInverter implements StringTransformer {
    @Override
    public void execute(StringDrink drink) {
        String reversed = new StringBuilder(drink.getText())
                .reverse()
                .toString();
        drink.setText(reversed);
    }

    @Override
    public void undo(StringDrink drink) {
        execute(drink);
    }
}
