import java.util.List;

public class CAWriter {

    public static String generateRuleBlock(int blockSize, List<String> stringList)
    {
        StringBuilder stringBuilder = new StringBuilder();
        int base = stringList.size();
        int n = (int)Math.ceil(Math.pow(base, blockSize));
        stringBuilder.append("rules\n");

        for (int i = 0; i < n; i++)
        {
            StringBuilder leftStringBuilder = new StringBuilder();
            StringBuilder rightStringBuilder = new StringBuilder();

            int x = i;
            int index;
            for (int j = 0; j < blockSize; j++)
            {
                index = x % base;
                x /= base;

                leftStringBuilder.insert(0, String.format("%s ", stringList.get(index)));
                rightStringBuilder.append("_ ");
            }
            String leftBlock = leftStringBuilder.toString().trim();
            String rightBlock = rightStringBuilder.toString().trim();
            stringBuilder.append(String.format("\t[%s] = [%s]\n", leftBlock, rightBlock));

        }
        stringBuilder.append("endrules\n");
        return stringBuilder.toString();
    }
}
