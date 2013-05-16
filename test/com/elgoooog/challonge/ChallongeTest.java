package com.elgoooog.challonge;

import com.elgoooog.challonge.model.Participant;
import com.elgoooog.challonge.model.Tournament;
import com.elgoooog.challonge.model.TournamentState;
import com.elgoooog.challonge.model.TournamentType;
import com.elgoooog.challonge.request.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Nicholas Hauschild
 *         Date: 5/11/13
 *         Time: 11:53 PM
 */
public class ChallongeTest {
    private Challonge challonge;

    @Before
    public void setup() {
        challonge = new Challonge("");
    }

    @Test
    public void listTest() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -5);
        final ListTournamentRequest request = new ListTournamentRequest.Builder()
                .withState(TournamentState.AWAITING_REVIEW)
                .withType(TournamentType.SINGLE_ELIMINATION)
                .createdAfter(c.getTime())
                .build();
        final List<Tournament> tournaments = challonge.listTournaments(request);
        assertEquals(1, tournaments.size());
        assertEquals("ApiTest", tournaments.get(0).getName());
        assertEquals("frist", tournaments.get(0).getUrl());
        assertEquals(TournamentState.AWAITING_REVIEW, tournaments.get(0).getState());
        assertEquals(TournamentType.SINGLE_ELIMINATION, tournaments.get(0).getTournamentType());
    }

    @Test
    public void createTest() {
        final CreateTournamentRequest request = new CreateTournamentRequest.Builder("ApiTest2", TournamentType.ROUND_ROBIN, "sceond")
                .doAcceptAttachments(true)
                .build();
        final Tournament tournament = challonge.createTournament(request);
        assertEquals("ApiTest2", tournament.getName());
        assertEquals("sceond", tournament.getUrl());
    }

    @Test
    public void createParticipantsTest() {
        final CreateParticipantRequest request1 = new CreateParticipantRequest.Builder("sceond", "bob")
                .withSeed(1)
                .build();
        final CreateParticipantRequest request2 = new CreateParticipantRequest.Builder("sceond", "tom")
                .withSeed(2)
                .build();

        final Participant participant1 = challonge.createParticipant(request1);
        final Participant participant2 = challonge.createParticipant(request2);
        assertEquals("bob", participant1.getName());
        assertEquals("tom", participant2.getName());
    }

    @Test
    public void listParticipantsTest() {
        final ListParticipantRequest request = new ListParticipantRequest("sceond");
        final List<Participant> participants = challonge.listParticipants(request);
        assertEquals(2, participants.size());
    }

    @Test
    public void ggeettParticipantTest() {
        final GetParticipantRequest request = new GetParticipantRequest.Builder("sceond", 7530437)
                .build();
        final Participant participant = challonge.getParticipant(request);
        assertEquals("bob", participant.getName());
    }

    @Test
    public void startTest() {
        final StartTournamentRequest request = new StartTournamentRequest.Builder("sceond")
                .build();
        final Tournament tournament = challonge.startTournament(request);
        assertEquals("ApiTest2", tournament.getName());
        assertEquals("sceond", tournament.getUrl());
    }

    @Test
    public void resetTest() {
        final ResetTournamentRequest request = new ResetTournamentRequest.Builder("sceond")
                .build();
        final Tournament tournament = challonge.resetTournament(request);
        assertEquals("ApiTest2", tournament.getName());
        assertEquals("sceond", tournament.getUrl());
    }

    @Test
    public void ggeettTest() {
        final GetTournamentRequest request = new GetTournamentRequest.Builder("sceond")
                .doIncludeMatches(true)
                .doIncludeParticipants(true)
                .build();
        final Tournament tournament = challonge.getTournament(request);
        assertEquals("ApiTest", tournament.getName());
        assertEquals("frist", tournament.getUrl());
        assertEquals(TournamentState.AWAITING_REVIEW, tournament.getState());
        assertEquals(TournamentType.SINGLE_ELIMINATION, tournament.getTournamentType());
        assertEquals(16, tournament.getParticipants().size());
        assertEquals(16, tournament.getMatches().size());
    }

    @Test
    public void updateTest() {
        final UpdateTournamentRequest request = new UpdateTournamentRequest.Builder("sceond")
                .doAcceptAttachments(true)
                .doPrivate(true)
                .withUrlPath("sceond")
                .build();
        final Tournament tournament = challonge.updateTournament(request);
        assertEquals("ApiTest2", tournament.getName());
        assertEquals("sceond", tournament.getUrl());
    }

    @Test
    public void deleteTest() {
        final DeleteTournamentRequest request = new DeleteTournamentRequest("sceond");
        final Tournament tournament = challonge.deleteTournament(request);
        assertEquals("ApiTest2", tournament.getName());
        assertEquals("sceond", tournament.getUrl());
    }
}
