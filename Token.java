// H κλάση Token αντιστοιχεί τα τερματικά σε ακεραίους και συμβολοσειρές.

class Token {
    public int type;
    public String attribute;
    public Token(int t) {
        type=t;
    }
    public Token(int t, String s) {
        type=t; attribute = s;
    }
    public int getType() {
        return type;
    }
    public String getAttribute() {
        return attribute;
    }
    public static String spellingOf(int t) {
        switch (t) {
            case Global.BREAK : return "BREAK";
            case Global.DIMLIGHTS : return "DIMLIGHTS";
            case Global.STRONGLIGHTS : return "STRONGLIGHTS";
            case Global.OPENCURTAIN : return "OPENCURTAIN";
            case Global.CLOSECURTAIN : return "CLOSECURTAIN";
            case Global.TALK : return "TALK";
            case Global.ENTRY : return "ENTRY";
            case Global.EXIT : return "EXIT";
            case Global.MOVE : return "MOVE";
            case Global.DEATH : return "DEATH";
            case Global.UNDEFINED : return "Undefinied token type";
            default : return "";
        }
    }
    public String toString() {
        switch (type) {
            case Global.BREAK :
            case Global.DIMLIGHTS :
            case Global.STRONGLIGHTS :
            case Global.OPENCURTAIN :
            case Global.CLOSECURTAIN :
            case Global.TALK :
            case Global.ENTRY :
            case Global.EXIT :
            case Global.MOVE :
            case Global.DEATH :
                return spellingOf(type) + ", " + attribute;
            default:
                return spellingOf(type);
        }
    }
};