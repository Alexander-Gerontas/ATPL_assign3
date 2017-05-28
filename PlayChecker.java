import java.io.IOException;

public class PlayChecker
{
    public static void main(String[] args) throws IOException
    {
        new SyntacticAnalysis(args[0]).parse();
    }
}