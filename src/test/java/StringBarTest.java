import org.example.StringDrink;
import org.example.StringRecipe;
import org.example.bar.Bar;
import org.example.bar.StringBar;
import org.example.bar.client.FerengiClient;
import org.example.bar.client.HumanClient;
import org.example.bar.client.RomulanClient;
import org.example.bar.strategy.ImpatientStrategy;
import org.example.bar.strategy.SmartStrategy;
import org.example.transformer.StringCaseChanger;
import org.example.transformer.StringInverter;
import org.example.transformer.StringReplacer;
import org.example.transformer.StringTransformer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StringBarTest {
    @Test
    public void happyHour() {
        Bar bar = new StringBar();
        assertFalse(bar.isHappyHour());

        bar.startHappyHour();
        assertTrue(bar.isHappyHour());

        bar.endHappyHour();
        assertFalse(bar.isHappyHour());
    }

    @Test
    public void addObserver() {
        Bar bar = new StringBar();

        HumanClient clientMock = Mockito.mock(HumanClient.class);
        bar.addObserver(clientMock);

        Mockito.verify(clientMock, Mockito.never()).happyHourStarted(bar);
        Mockito.verify(clientMock, Mockito.never()).happyHourEnded(bar);

        bar.startHappyHour();
        Mockito.verify(clientMock, Mockito.times(1)).happyHourStarted(bar);
        Mockito.verify(clientMock, Mockito.never()).happyHourEnded(bar);

        bar.endHappyHour();
        Mockito.verify(clientMock, Mockito.times(1)).happyHourStarted(bar);
        Mockito.verify(clientMock, Mockito.times(1)).happyHourEnded(bar);
    }

    @Test
    public void removeObserver() {
        Bar bar = new StringBar();

        HumanClient clientMock = Mockito.mock(HumanClient.class);
        bar.addObserver(clientMock);
        bar.removeObserver(clientMock);

        bar.startHappyHour();
        bar.endHappyHour();

        Mockito.verify(clientMock, Mockito.never()).happyHourStarted(bar);
        Mockito.verify(clientMock, Mockito.never()).happyHourEnded(bar);
    }

    private StringRecipe getRecipe() {
        StringInverter si = new StringInverter();
        StringCaseChanger cc = new StringCaseChanger();
        StringReplacer sr = new StringReplacer('A', 'X');

        List<StringTransformer> transformers = new ArrayList<>();
        transformers.add(si);
        transformers.add(cc);
        transformers.add(sr);

        StringRecipe recipe = new StringRecipe(transformers);
        return recipe;
    }

    @Test
    public void orderStringRecipe() {
        StringBar stringBar = new StringBar();
        StringDrink drink = new StringDrink("AbCd-aBcD");
        StringRecipe recipe = getRecipe();

        stringBar.order(drink, recipe);
        assertEquals("dCbX-DcBa", drink.getText());
    }

    @Test
    public void impatientStrategy() {
        StringBar stringBar = new StringBar();
        StringDrink drink = new StringDrink("AbCd-aBcD");
        StringRecipe recipe = getRecipe();

        ImpatientStrategy strategy = new ImpatientStrategy();
        HumanClient client = new HumanClient(strategy);

        // Recipe is ordered immediately
        client.wants(drink, recipe, stringBar);
        assertEquals("dCbX-DcBa", drink.getText());
    }

    @Test
    public void smartStrategyStartOpened() {
        StringBar stringBar = new StringBar();
        StringDrink drink = new StringDrink("AbCd-aBcD");
        StringRecipe recipe = getRecipe();

        SmartStrategy strategy = new SmartStrategy();
        HumanClient client = new HumanClient(strategy);

        // Recipe is ordered immediately as happy hour was already under way
        stringBar.startHappyHour();
        client.wants(drink, recipe, stringBar);
        assertEquals("dCbX-DcBa", drink.getText());
    }

    @Test
    public void smartStrategyStartClosed() {
        StringBar stringBar = new StringBar();
        StringDrink drink = new StringDrink("AbCd-aBcD");
        StringRecipe recipe = getRecipe();

        SmartStrategy strategy = new SmartStrategy();
        HumanClient client = new HumanClient(strategy);
        stringBar.addObserver(client); // this is important!

        client.wants(drink, recipe, stringBar);
        assertEquals("AbCd-aBcD", drink.getText());

        // Recipe is only ordered here
        stringBar.startHappyHour();
        assertEquals("dCbX-DcBa", drink.getText());
    }

    @Test
    public void ferengiAlreadyOpened() {
        StringBar stringBar = new StringBar();
        StringDrink drink = new StringDrink("AbCd-aBcD");
        StringRecipe recipe = getRecipe();

        FerengiClient client = new FerengiClient();

        // Recipe is ordered immediately
        stringBar.startHappyHour();
        client.wants(drink, recipe, stringBar);
        assertEquals("dCbX-DcBa", drink.getText());
    }

    @Test
    public void ferengiStartClosed() {
        StringBar stringBar = new StringBar();
        StringDrink drink = new StringDrink("AbCd-aBcD");
        StringRecipe recipe = getRecipe();

        FerengiClient client = new FerengiClient();
        stringBar.addObserver(client); // this is important!

        client.wants(drink, recipe, stringBar);
        assertEquals("AbCd-aBcD", drink.getText());

        // Recipe is only ordered here
        stringBar.startHappyHour();
        assertEquals("dCbX-DcBa", drink.getText());
    }

    @Test
    public void romulan() {
        StringBar stringBar = new StringBar();
        StringDrink drink = new StringDrink("AbCd-aBcD");
        StringRecipe recipe = getRecipe();

        RomulanClient client = new RomulanClient();

        // Recipe is ordered immediately
        client.wants(drink, recipe, stringBar);
        assertEquals("dCbX-DcBa", drink.getText());
    }
}
