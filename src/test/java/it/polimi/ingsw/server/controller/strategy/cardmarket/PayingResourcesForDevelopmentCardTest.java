package it.polimi.ingsw.server.controller.strategy.cardmarket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.network.jsonutils.CommonGsonAdapters;
import it.polimi.ingsw.network.jsonutils.JsonUtility;
import it.polimi.ingsw.network.messages.clienttoserver.events.Event;
import it.polimi.ingsw.server.messages.clienttoserver.events.Validable;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.states.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PayingResourcesForDevelopmentCardTest {


    @Test
    public void executeToRESOURCE() {

        List<Integer> choices=new ArrayList<>();
        choices.add(-8);
        choices.add(-7);
        choices.add(-6);
        choices.add(-5);

        Event clientEvent = new it.polimi.ingsw.network.messages.clienttoserver.events.cardshopevent.ChooseResourceForCardShopEvent(choices);

        String serializedEvent = JsonUtility.serialize(clientEvent);

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(CommonGsonAdapters.gsonEventMessageAdapter).create();

        Validable serverEvent = JsonUtility.deserializeFromString(serializedEvent, it.polimi.ingsw.server.messages.clienttoserver.events.cardshopevent.ChooseResourceForCardShopEvent.class, gson);
        Map<Integer, String> players = new HashMap<>();
        players.put(0,"testPlayer1");
        List<Integer> onlineUsers = new ArrayList<>(players.keySet());

        GameModel gamemodel = new GameModel(players, true ,null, onlineUsers);
        gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().addResources(new int[]{10,10,10,10});

        assertEquals(new PayingResourcesForDevelopmentCard().execute(gamemodel, serverEvent).getKey(), State.CHOOSING_POSITION_FOR_DEVCARD);
        assertEquals(gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().getNumberOf(Resource.GOLD),9);
        assertEquals(gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().getNumberOf(Resource.SERVANT),9);
        assertEquals(gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().getNumberOf(Resource.SHIELD),9);
        assertEquals(gamemodel.getCurrentPlayer().getPersonalBoard().getStrongBox().getNumberOf(Resource.STONE),9);
    }
}