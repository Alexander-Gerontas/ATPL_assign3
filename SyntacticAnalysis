import java.io.FileInputStream;
import java.io.IOException;
import java.util.Stack;

class SyntacticAnalysis
{
    private int[][] table = Global.table; // Συντακτικός Πίνακας Γραμματικής.
    private int[][][] rules = Global.rules; // Στον πίνακα rules είναι αποθηκευμένοι οι κανόνες και τα τερματικά της γραμματικής
    private int rulePos = 0;
    private Stack<int []> stack = new Stack<>(); // Αυτόματο στοίβας
    private History history = new History();
    private Lexer L; // Λεκτικός αναλυτής.
    private Token lookAhead;

    SyntacticAnalysis(String fileName) throws IOException
    {
        // Το FileInputStream του αρχείου δίνεται σαν όρισμα στον λεκτικό αναλυτή.
        FileInputStream fis = new FileInputStream(fileName);
        L = new Lexer(fis);

        // Ο λεκτικός αναλυτής επιστρέφει την πρώτη λέξη του αρχείου.
        lookAhead = L.next();

        // Φόρτωμα του πρώτου κανόνα, και του τερματικου Τ_ΕΝD στο stack.
        stack.push(new int[]{Global.Term, Global.T_END});
        stack.push(new int[]{Global.Rule, Global.TheatricalPlay});
    }

    void parse() throws IOException
    {
        while (stack.size() > 0)
        {
            // Aποθήκευση του κανόνα ή τερματικού που βρίσκεται στην κορυφή της στοίβας στον πίνακα temp.
            int[] temp = stack.peek();

            // Περίπτωση τερματικού
            if (temp[0] == Global.Term)
            {
                // Αν το τερματικό αναγνωριστεί, αυτό τυπώνεται σε νέα γραμμή
                if (temp[1] == lookAhead.getType())
                {
                    System.out.println("Token " + lookAhead.getAttribute() + " recognized");

                    lookAhead = L.next(); // Ο λεκτικός αναλυτής επιστρέφει την επόμενη λέξη.
                    rulePos = 0; // Το rulePos γίνεται 0.
                    stack.pop(); // Αφαίρεση του τερματικού απο την στοίβα.
                }

                // Στην περίπτωση λευκού χαρακτήρα, απλά αφαιρείται το token από την στοίβα.
                else if (temp[1] == Global.T_EPSILON) stack.pop();

                // Σε περίπτωση που το τερματικό δεν αναγνωριστεί και δεν μπορεί να γίνει οπισθοδρόμηση, εμφανίζεται μήνυμα αποτυχίας και το πρόγραμμα τερματίζει.
                else if (history.isEmpty())
                {
                    System.out.println("Current Token examined " + lookAhead.getAttribute());
                    if (lookAhead.getType() == Global.UNDEFINED)
                    {
                        System.out.println("Undefined Token");
                        System.exit(0);
                    }
                    System.out.println("Sorry! The theatrical PlayChecker structure is not recognizable!");
                    System.exit(0);
                }

                // Επαναφορά τιμών από την κλάση History (οπισθοδρόμηση).
                else
                {
                    stack = history.getHistoryStack();
                    L = history.getHistoryL();
                    rulePos = history.getHistoryRulePos() + 1; // Το rulePos αυξάνεται κατά 1.
                }
            }

            // Περίπτωση Έκφρασης.
            else if (temp[0] == Global.Rule)
            {
                // Εύρεση έκφρασης απο τον συντακτικό πίνακα.
                // Εάν μία έκφραση περιέχει παραπάνω απο μία κανονικές εκφράσεις (διαχωρισμένες με '|') φορτώνεται η πρώτη στην στοίβα.
                // Σε περίπτωση όπου αυτή δεν περιέχει το token που αναζητείται, φορτώνεται η επόμενη έκφραση ώσπου να βρεθεί αυτή με το αναζητούμενο token.
                // Η μεταβλητή rulePos αρχίζει από το την πρώτη έκφραση (0), αυξάνεται κατά 1 κάθε φορά που κάνουμε οπισθοδρόμηση και μηδενίζεται όταν βρεθεί το σωστό token.
                int rule = table[temp[1]][rulePos];
                int[][] loadingRule = rules[rule];

                // Aποθήκευση τωρινών τιμών στην κλάση History.
                if (table[temp[1]].length > rulePos + 1)
                    history.setHistory(stack, L, rulePos);

                stack.pop(); // Αφαίρεση παλιού κανόνα από την στοίβα.

                // Φόρτωμα νέου κανόνα απο τον πίνακα στην στοίβα.
                for (int i = loadingRule.length - 1; i >= 0; i--)
                    stack.push(loadingRule[i]);
            }


        }
    }
}
