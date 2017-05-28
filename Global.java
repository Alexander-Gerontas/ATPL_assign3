class Global
{
    static int Term = 0;
    static int Rule = 1;

    // Terminals
    static final int BREAK = 1;
    static final int DIMLIGHTS = 2;
    static final int STRONGLIGHTS = 3;
    static final int OPENCURTAIN = 4;
    static final int CLOSECURTAIN = 5;
    static final int TALK = 6;
    static final int MOVE = 7;
    static final int ENTRY = 8;
    static final int EXIT = 9;
    static final int DEATH = 10;
    static final int T_EPSILON = 11;
    static final int UNDEFINED = 12;
    static final int T_END = 14;

    // Non-terminals
    static final int TheatricalPlay = 0;
    static final int Act = 1;
    static final int Scene = 2;
    static final int SceneContinue = 3;
    static final int Precursor = 4;
    static final int MoreActions = 5;
    static final int Action = 6;

    // Parse Table

    static int[][] table = new int[][]{
            {0}, // TheatricalPlay
            {1}, // Act
            {2}, // Scene
            {3,11}, // SceneContinue
            {4,11}, // Precursor
            {5,11}, // MoreActions
            {6,7,8,9,10}  // Action
    };

    // Rule Table

    static int[][][] rules = new int[][][]{
            {{Rule, Act}, {Rule, Act},{Rule, Act}, {Term, BREAK}, {Rule, Act}, {Rule, Act}}, // TheatricalPlay
            {{Term, DIMLIGHTS}, {Rule, Scene}, {Rule, SceneContinue}, {Term, STRONGLIGHTS}}, // Act
            {{Term, OPENCURTAIN}, {Rule, Precursor}, {Term, ENTRY}, {Rule, MoreActions}, {Term, CLOSECURTAIN}}, // Scene
            {{Rule, Scene}, {Rule, SceneContinue}}, //SceneContinue
            {{Term, TALK}, {Rule, Precursor}}, //Precursor
            {{Rule, Action}, {Rule, MoreActions}}, // MoreActions
            {{Term, TALK}}, // Action
            {{Term, MOVE}},
            {{Term, ENTRY}},
            {{Term, EXIT}},
            {{Term, DEATH}},
            {{Term, T_EPSILON}} // epsilon rule
    };
}