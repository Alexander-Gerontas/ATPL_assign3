class Lexer
{
    private final int YY_BUFFER_SIZE = 512;
    private final int YY_F = -1;
    private final int YY_NO_STATE = -1;
    private final int YY_NOT_ACCEPT = 0;
    private final int YY_START = 1;
    private final int YY_END = 2;
    private final int YY_NO_ANCHOR = 4;
    private final int YY_BOL = 128;
    private final int YY_EOF = 129;
    private java.io.BufferedReader yy_reader;
    private int yy_buffer_index;
    private int yy_buffer_read;
    private int yy_buffer_start;
    private int yy_buffer_end;
    private char yy_buffer[];
    private boolean yy_at_bol;
    private int yy_lexical_state;

    Lexer(java.io.Reader reader) {
        this ();
        if (null == reader) {
            throw (new Error("Error: Bad input stream initializer."));
        }
        yy_reader = new java.io.BufferedReader(reader);
    }

    Lexer(java.io.InputStream instream) {
        this ();
        if (null == instream) {
            throw (new Error("Error: Bad input stream initializer."));
        }
        yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
    }

    private Lexer() {
        yy_buffer = new char[YY_BUFFER_SIZE];
        yy_buffer_read = 0;
        yy_buffer_index = 0;
        yy_buffer_start = 0;
        yy_buffer_end = 0;
        yy_at_bol = true;
        yy_lexical_state = YYINITIAL;
    }

    private boolean yy_eof_done = false;
    private final int YYINITIAL = 0;
    private final int yy_state_dtrans[] = {
            0
    };
    private void yybegin (int state) {
        yy_lexical_state = state;
    }
    private int yy_advance ()
            throws java.io.IOException {
        int next_read;
        int i;
        int j;

        if (yy_buffer_index < yy_buffer_read) {
            return yy_buffer[yy_buffer_index++];
        }

        if (0 != yy_buffer_start) {
            i = yy_buffer_start;
            j = 0;
            while (i < yy_buffer_read) {
                yy_buffer[j] = yy_buffer[i];
                ++i;
                ++j;
            }
            yy_buffer_end = yy_buffer_end - yy_buffer_start;
            yy_buffer_start = 0;
            yy_buffer_read = j;
            yy_buffer_index = j;
            next_read = yy_reader.read(yy_buffer,
                    yy_buffer_read,
                    yy_buffer.length - yy_buffer_read);
            if (-1 == next_read) {
                return YY_EOF;
            }
            yy_buffer_read = yy_buffer_read + next_read;
        }

        while (yy_buffer_index >= yy_buffer_read) {
            if (yy_buffer_index >= yy_buffer.length) {
                yy_buffer = yy_double(yy_buffer);
            }
            next_read = yy_reader.read(yy_buffer,
                    yy_buffer_read,
                    yy_buffer.length - yy_buffer_read);
            if (-1 == next_read) {
                return YY_EOF;
            }
            yy_buffer_read = yy_buffer_read + next_read;
        }
        return yy_buffer[yy_buffer_index++];
    }
    private void yy_move_end () {
        if (yy_buffer_end > yy_buffer_start &&
                '\n' == yy_buffer[yy_buffer_end-1])
            yy_buffer_end--;
        if (yy_buffer_end > yy_buffer_start &&
                '\r' == yy_buffer[yy_buffer_end-1])
            yy_buffer_end--;
    }
    private boolean yy_last_was_cr=false;
    private void yy_mark_start () {
        yy_buffer_start = yy_buffer_index;
    }
    private void yy_mark_end () {
        yy_buffer_end = yy_buffer_index;
    }
    private void yy_to_mark () {
        yy_buffer_index = yy_buffer_end;
        yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
                ('\r' == yy_buffer[yy_buffer_end-1] ||
                        '\n' == yy_buffer[yy_buffer_end-1] ||
                        2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
                        2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
    }
    private java.lang.String yytext () {
        return (new java.lang.String(yy_buffer,
                yy_buffer_start,
                yy_buffer_end - yy_buffer_start));
    }
    private int yylength () {
        return yy_buffer_end - yy_buffer_start;
    }
    private char[] yy_double (char buf[]) {
        int i;
        char newbuf[];
        newbuf = new char[2*buf.length];
        for (i = 0; i < buf.length; ++i) {
            newbuf[i] = buf[i];
        }
        return newbuf;
    }
    private final int YY_E_INTERNAL = 0;
    private final int YY_E_MATCH = 1;
    private java.lang.String yy_error_string[] = {
            "Error: Internal error.\n",
            "Error: Unmatched input.\n"
    };
    private void yy_error (int code,boolean fatal) {
        java.lang.System.out.print(yy_error_string[code]);
        java.lang.System.out.flush();
        if (fatal) {
            throw new Error("Fatal Error.\n");
        }
    }
    private int[][] unpackFromString(int size1, int size2, String st) {
        int colonIndex = -1;
        String lengthString;
        int sequenceLength = 0;
        int sequenceInteger = 0;

        int commaIndex;
        String workString;

        int res[][] = new int[size1][size2];
        for (int i= 0; i < size1; i++) {
            for (int j= 0; j < size2; j++) {
                if (sequenceLength != 0) {
                    res[i][j] = sequenceInteger;
                    sequenceLength--;
                    continue;
                }
                commaIndex = st.indexOf(',');
                workString = (commaIndex==-1) ? st :
                        st.substring(0, commaIndex);
                st = st.substring(commaIndex+1);
                colonIndex = workString.indexOf(':');
                if (colonIndex == -1) {
                    res[i][j]=Integer.parseInt(workString);
                    continue;
                }
                lengthString =
                        workString.substring(colonIndex+1);
                sequenceLength=Integer.parseInt(lengthString);
                workString=workString.substring(0,colonIndex);
                sequenceInteger=Integer.parseInt(workString);
                res[i][j] = sequenceInteger;
                sequenceLength--;
            }
        }
        return res;
    }
    private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NOT_ACCEPT,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NOT_ACCEPT,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NOT_ACCEPT,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NOT_ACCEPT,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NOT_ACCEPT,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NOT_ACCEPT,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NOT_ACCEPT,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NOT_ACCEPT,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NOT_ACCEPT,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NOT_ACCEPT,
		/* 34 */ YY_NOT_ACCEPT,
		/* 35 */ YY_NOT_ACCEPT,
		/* 36 */ YY_NOT_ACCEPT,
		/* 37 */ YY_NOT_ACCEPT,
		/* 38 */ YY_NOT_ACCEPT,
		/* 39 */ YY_NOT_ACCEPT,
		/* 40 */ YY_NOT_ACCEPT,
		/* 41 */ YY_NOT_ACCEPT,
		/* 42 */ YY_NOT_ACCEPT,
		/* 43 */ YY_NOT_ACCEPT,
		/* 44 */ YY_NOT_ACCEPT,
		/* 45 */ YY_NOT_ACCEPT,
		/* 46 */ YY_NOT_ACCEPT,
		/* 47 */ YY_NOT_ACCEPT,
		/* 48 */ YY_NOT_ACCEPT,
		/* 49 */ YY_NOT_ACCEPT,
		/* 50 */ YY_NOT_ACCEPT,
		/* 51 */ YY_NOT_ACCEPT,
		/* 52 */ YY_NOT_ACCEPT,
		/* 53 */ YY_NOT_ACCEPT,
		/* 54 */ YY_NOT_ACCEPT,
		/* 55 */ YY_NOT_ACCEPT,
		/* 56 */ YY_NOT_ACCEPT,
		/* 57 */ YY_NOT_ACCEPT,
		/* 58 */ YY_NOT_ACCEPT,
		/* 59 */ YY_NOT_ACCEPT,
		/* 60 */ YY_NOT_ACCEPT,
		/* 61 */ YY_NOT_ACCEPT,
		/* 62 */ YY_NOT_ACCEPT,
		/* 63 */ YY_NOT_ACCEPT,
		/* 64 */ YY_NOT_ACCEPT,
		/* 65 */ YY_NOT_ACCEPT,
		/* 66 */ YY_NOT_ACCEPT,
		/* 67 */ YY_NOT_ACCEPT,
		/* 68 */ YY_NOT_ACCEPT,
		/* 69 */ YY_NOT_ACCEPT,
		/* 70 */ YY_NOT_ACCEPT,
		/* 71 */ YY_NOT_ACCEPT,
		/* 72 */ YY_NOT_ACCEPT,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NOT_ACCEPT
    };
    private int yy_cmap[] = unpackFromString(1,130,
            "3:8,4:2,1,3,4,1,3:18,4,3:14,2,3:17,8,5,21,10,7,3,14,15,11,3,9,13,12,19,18,2" +
                    "0,3,6,17,16,22,25,3,24,23,3:7,8,5,21,10,7,3,14,15,11,3,9,13,12,19,18,20,3,6" +
                    ",17,16,22,25,3,24,23,3:6,0:2")[0];

    private int yy_rmap[] = unpackFromString(1,75,
            "0,1:2,2,3,1:10,4,1,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,2" +
                    "5,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,5" +
                    "0,51,52,53,54,55,56,57,58,59,60,61,62")[0];

    private int yy_nxt[][] = unpackFromString(63,26,
            "1,2,3,16,2,18,16,20,16:2,22,16,24,16:3,26,28,30,16:2,32,16:4,-1:28,4,-1:25," +
                    "4:24,-1:7,68,-1:34,70,-1:15,15,-1:30,33,-1:33,17,-1:4,19,-1:9,34,-1:24,21,-" +
                    "1:3,23,-1:26,69,-1:31,25,-1:32,35,-1:8,27,-1:30,36,-1:28,29,-1:15,71,-1:39," +
                    "67,-1:23,38,-1:20,31,-1:28,5,-1:25,41,-1:16,6,-1:27,7,-1:35,43,-1:23,44,-1:" +
                    "17,8,-1:39,9,-1:17,10,-1:21,45,-1:35,46,-1:11,47,-1:32,48,-1:33,50,-1:24,51" +
                    ",-1:19,52,-1:23,53,-1:18,74,-1:41,54,-1:19,55,-1:20,56,-1:20,58,-1:36,11,-1" +
                    ":22,59,-1:19,60,-1:33,61,-1:24,62,-1:21,63,-1:22,64,-1:33,65,-1:28,12,-1:17" +
                    ",66,-1:31,13,-1:27,14,-1:13,37,-1:26,39,-1:30,42,-1:18,40,-1:37,72,-1:26,73" +
                    ",-1:20,49,-1:27,57,-1:9");

    public Token next ()
            throws java.io.IOException {
        int yy_lookahead;
        int yy_anchor = YY_NO_ANCHOR;
        int yy_state = yy_state_dtrans[yy_lexical_state];
        int yy_next_state = YY_NO_STATE;
        int yy_last_accept_state = YY_NO_STATE;
        boolean yy_initial = true;
        int yy_this_accept;

        yy_mark_start();
        yy_this_accept = yy_acpt[yy_state];
        if (YY_NOT_ACCEPT != yy_this_accept) {
            yy_last_accept_state = yy_state;
            yy_mark_end();
        }
        while (true) {
            if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
            else yy_lookahead = yy_advance();
            yy_next_state = YY_F;
            yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
            if (YY_EOF == yy_lookahead && true == yy_initial) {

                return new Token(Global.T_END, "EOF");
            }
            if (YY_F != yy_next_state) {
                yy_state = yy_next_state;
                yy_initial = false;
                yy_this_accept = yy_acpt[yy_state];
                if (YY_NOT_ACCEPT != yy_this_accept) {
                    yy_last_accept_state = yy_state;
                    yy_mark_end();
                }
            }
            else {
                if (YY_NO_STATE == yy_last_accept_state) {
                    throw (new Error("Lexical Error: Unmatched Input."));
                }
                else {
                    yy_anchor = yy_acpt[yy_last_accept_state];
                    if (0 != (YY_END & yy_anchor)) {
                        yy_move_end();
                    }
                    yy_to_mark();
                    switch (yy_last_accept_state) {
                        case 1:

                        case -2:
                            break;
                        case 2:
                        {}
                        case -3:
                            break;
                        case 3:
                        { return new Token(Global.UNDEFINED, yytext()); }
                        case -4:
                            break;
                        case 4:
                        {}
                        case -5:
                            break;
                        case 5:
                        { return new Token(Global.EXIT, "EXIT"); }
                        case -6:
                            break;
                        case 6:
                        { return new Token(Global.MOVE, "MOVE"); }
                        case -7:
                            break;
                        case 7:
                        { return new Token(Global.TALK, "TALK"); }
                        case -8:
                            break;
                        case 8:
                        { return new Token(Global.BREAK, "BREAK"); }
                        case -9:
                            break;
                        case 9:
                        { return new Token(Global.ENTRY, "ENTRY"); }
                        case -10:
                            break;
                        case 10:
                        { return new Token(Global.DEATH, "DEATH"); }
                        case -11:
                            break;
                        case 11:
                        { return new Token(Global.DIMLIGHTS, "DIMLIGHTS"); }
                        case -12:
                            break;
                        case 12:
                        { return new Token(Global.OPENCURTAIN, "OPENCURTAIN"); }
                        case -13:
                            break;
                        case 13:
                        { return new Token(Global.STRONGLIGHTS, "STRONGLIGHTS"); }
                        case -14:
                            break;
                        case 14:
                        { return new Token(Global.CLOSECURTAIN, "CLOSECURTAIN"); }
                        case -15:
                            break;
                        case 16:
                        { return new Token(Global.UNDEFINED, yytext()); }
                        case -16:
                            break;
                        case 18:
                        { return new Token(Global.UNDEFINED, yytext()); }
                        case -17:
                            break;
                        case 20:
                        { return new Token(Global.UNDEFINED, yytext()); }
                        case -18:
                            break;
                        case 22:
                        { return new Token(Global.UNDEFINED, yytext()); }
                        case -19:
                            break;
                        case 24:
                        { return new Token(Global.UNDEFINED, yytext()); }
                        case -20:
                            break;
                        case 26:
                        { return new Token(Global.UNDEFINED, yytext()); }
                        case -21:
                            break;
                        case 28:
                        { return new Token(Global.UNDEFINED, yytext()); }
                        case -22:
                            break;
                        case 30:
                        { return new Token(Global.UNDEFINED, yytext()); }
                        case -23:
                            break;
                        case 32:
                        { return new Token(Global.UNDEFINED, yytext()); }
                        case -24:
                            break;
                        default:
                            yy_error(YY_E_INTERNAL,false);
                        case -1:
                    }
                    yy_initial = true;
                    yy_state = yy_state_dtrans[yy_lexical_state];
                    yy_next_state = YY_NO_STATE;
                    yy_last_accept_state = YY_NO_STATE;
                    yy_mark_start();
                    yy_this_accept = yy_acpt[yy_state];
                    if (YY_NOT_ACCEPT != yy_this_accept) {
                        yy_last_accept_state = yy_state;
                        yy_mark_end();
                    }
                }
            }
        }
    }
}