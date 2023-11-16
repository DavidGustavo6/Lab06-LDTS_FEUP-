package org.example;

public class StringReplacer implements StringTransformer {

    private char old,nov;
    public StringReplacer(char a, char x) {
        old=a;
        nov=x;
    }

    @Override
    public void execute(StringDrink drink) {
        String text=drink.getText();
        String replacedText = text.replace(old, nov);
        drink.setText(replacedText);
    }
}
