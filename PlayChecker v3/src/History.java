import java.util.Stack;

class History
{
    private Stack<Stack> historyStack = new Stack<>();
    private Lexer L;
    private Stack<Integer> historyRulePos = new Stack<>();

    void setHistory(Stack stack, Lexer L, int rule)
    {
        historyStack.push((Stack) stack.clone());
        this.L = L;
        historyRulePos.push(rule);
    }

    Stack getHistoryStack()
    {
        return historyStack.pop();
    }

    Lexer getHistoryL()
    {
        return L;
    }

    int getHistoryRulePos() {return historyRulePos.pop(); }

    boolean isEmpty() {return historyStack.isEmpty(); }
}