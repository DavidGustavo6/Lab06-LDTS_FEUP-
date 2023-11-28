package org.example.transformer;

import org.example.StringDrink;

public interface StringTransformer {
    void execute(StringDrink drink);
    void undo(StringDrink drink);
}
