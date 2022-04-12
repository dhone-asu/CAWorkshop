import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CAReader {
    CellularAutomaton ca;
    HashMap<String, ParseAction> keywordActions;

    public CAReader() {
        super();
        ca = new CellularAutomaton();
        initKeywordActions();
    }

    public CAReader(CellularAutomaton ca) {
        super();
        this.ca = ca;
        initKeywordActions();
    }
/*
blocksize 3
background white
colors black white
dim 800 500
setpixel 400 0 black
 */
    private void initKeywordActions()
    {
        keywordActions = new HashMap<>();

        keywordActions.put("blocksize", (k, a) -> handleBlockSize(k, a));
        keywordActions.put("background", (k, a) -> handleBackground(k, a));
        keywordActions.put("colors", (k,a)->handleColors(k,a));
        keywordActions.put("dim", (k,a)->handleDim(k,a));
        keywordActions.put("setpixel", (k,a)->handleSetPixel(k,a));
    }

    public boolean read(InputStream is)
    {
        try {
            Pattern keywordPattern = Pattern.compile("^\\s*(?<keyword>\\w+)\\s*(?<args>.*)$");
            String file = new String(is.readAllBytes());
            List<String> lines = file.lines().toList();

            for (int i = 0; i < lines.size(); i++)
            {
                String str = lines.get(i);
                Matcher matcher = keywordPattern.matcher(str);

                if (matcher.matches())
                {
                    String keyword = matcher.group("keyword");
                    String args = matcher.group("args");

                    ParseAction parseAction = keywordActions.get(keyword);

                    if (keyword.equals("rules"))
                    {
                        int start = i + 1;
                        int end = i + 1;
                        int j = start;
                        for (j = start; j < lines.size(); j++)
                        {
                            if (lines.get(j).matches("^\\s*endrules\\s*$"))
                                end = j-1;
                        }
                        if (end >= start)
                        {
                            List<String> rules = lines.subList(start,end);
                            handleRules(rules);
                        }
                    }
                    else if (parseAction != null)
                        parseAction.doAction(keyword, args);

                }
            }
            return true;
        }
        catch (Exception e)
        {
            System.out.printf("Exception: %s\n", e.getMessage());
            return false;
        }
    }

    private void handleBlockSize(String keyword, String args)
    {
        System.out.printf("Handling: %s %s\n", keyword,args);
    }


    private void handleBackground(String keyword, String args)
    {
        Pattern pattern = Pattern.compile("\\s*(?<color>\\w+)\\s*.*");
        Matcher matcher = pattern.matcher(args);
        if (matcher.matches())
        {
            Color color = Color.valueOf(matcher.group("color"));
            ca.setBackground(color);
        }
    }
    private void handleColors(String keyword, String args)
    {
        Pattern pattern = Pattern.compile("\\s*(?<color>\\w+)");
        Matcher matcher = pattern.matcher(args);

        while (matcher.find())
        {
            Color color =  Color.valueOf(matcher.group("color"));
            ca.getColors().add(color);
        }
    }

    private void handleDim(String keyword, String args)
    {
        Pattern pattern = Pattern.compile("\\s*(?<width>\\d+)\\s+(?<height>\\d+)\\s*");
        Matcher matcher = pattern.matcher(args);

        if (matcher.matches()) {
            int width = Integer.parseInt(matcher.group("width"));
            int height = Integer.parseInt(matcher.group("height"));
            ca.setWidth(width);
            ca.setHeight(height);
        }
    }

    private void handleSetPixel(String keyword, String args)
    {

    }


    // [black black black] = [_ white _]
    private void handleRules(List<String> rules)
    {
        Pattern rulePattern = Pattern.compile("\\s*\\[(?<left>.*)\\]\\s*=\\s*\\[(?<right>.*)\\]\\s*");

        for (String rule : rules) {
            Matcher matcher = rulePattern.matcher(rule);
            if (matcher.matches())
            {
                System.out.printf("%s, %s\n", matcher.group("left"), matcher.group("right"));
            }
        }
    }

    private static interface ParseAction
    {
        public void doAction(String keyword, String args);
    }
}
