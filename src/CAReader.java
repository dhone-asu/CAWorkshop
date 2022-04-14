import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
            System.out.printf("Exception: %s %s\n", e.getMessage(), e.getCause());
            return false;
        }
    }

    private void handleBlockSize(String keyword, String args)
    {
        Pattern pattern = Pattern.compile("\\s*(?<size>\\d+)\\s*.*");
        Matcher matcher = pattern.matcher(args);

        if (matcher.matches())
        {
            int blockSize = Integer.parseInt(matcher.group("size"));
            ca.setBlockSize(blockSize);
        }
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
        Pattern pattern = Pattern.compile("\\s*(?<x>\\d+)\\s+(?<y>\\d+)\\s+(?<color>\\w+)\\s*");
        Matcher matcher = pattern.matcher(args);

        if (matcher.matches()) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            Color color = Color.valueOf(matcher.group("color"));
            ca.getInitialPixels().add(new Pixel(x, y, color));
        }
    }


    // [black black black] = [_ white _]
    private void handleRules(List<String> rules)
    {
        Pattern rulePattern = Pattern.compile("\\s*\\[(?<left>.*)\\]\\s*=\\s*\\[(?<right>.*)\\]\\s*");
        //Pattern blockPattern = Pattern.compile("\\s*(?<color>\\S+)");

        for (String ruleStr : rules) {
            Matcher ruleMatcher = rulePattern.matcher(ruleStr);
            if (ruleMatcher.matches())
            {
                System.out.printf("%s, %s\n", ruleMatcher.group("left"), ruleMatcher.group("right"));

                if (ruleMatcher.matches()) {
                    String left = ruleMatcher.group("left");
                    String right = ruleMatcher.group("right");
                    Block leftBlock = Block.parseBlock(left);
                    Block rightBlock = Block.parseBlock(right);
                    Rule rule = new Rule(leftBlock, rightBlock);
                    ca.getRules().add(rule);
                }
            }
        }
    }

    private static interface ParseAction
    {
        public void doAction(String keyword, String args);
    }
}
