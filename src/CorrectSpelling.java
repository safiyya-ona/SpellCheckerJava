import java.util.Arrays;

public class CorrectSpelling
{
    private StringArray wordsToCorrect = new StringArray();
    private StringArray correctWords = new StringArray();
    private SpellChecker sp = new SpellChecker();
    private Integer minSuggestionLength = 5;

    // i used the Levenshtein Distance pseudocode for suggestions
    // https://en.wikipedia.org/wiki/Levenshtein_distance
    // https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance
    public int lDistance(String word1, String word2)
    {
        Integer[][] matrix = new Integer[word1.length()+1][word2.length()+1];
        Integer subCost, tempMin;
        for (Integer[] i:matrix) Arrays.fill(i, 0);
        for (int i = 1; i < word1.length()+1; i++) matrix[i][0] = i;
        for (int i = 1; i < word2.length()+1; i++) matrix[0][i] = i;
        for (int j = 1; j < word2.length()+1; j++)
        {
            for (int i = 1; i < word1.length()+1; i++)
            {
                if (word1.charAt(i-1) == word2.charAt(j-1))  subCost = 0;
                else subCost = 1;
                tempMin = Math.min(matrix[i][j-1] + 1, matrix[i-1][j-1] + subCost);
                matrix[i][j] = Math.min(matrix[i-1][j]+1,tempMin);
                if (i>1 && j>1 && (word1.charAt(i-1) == word2.charAt(j-2)) && (word1.charAt(i-2) == word2.charAt(j-1)) )
                    matrix[i][j] = Math.min(matrix[i][j],matrix[i-2][j-2]+1);
            }
        }

        return matrix[word1.length()][word2.length()];
    }

    public StringArray runLD (String incorrectWord,int diff)
    {
        StringArray suggestions = new StringArray();
        for (int i = 0; i < this.sp.getDictWords().size(); i++)
        {   // diff is the number of changes to get a word in the dictionary
            if (this.lDistance(this.sp.getDictWords().get(i),incorrectWord) <= diff)
                suggestions.add(this.sp.getDictWords().get(i));
        }
        return suggestions;
    }

    public StringArray runCorrector()
    {
        StringArray suggestions = new StringArray();
        StringArray newCorrectWords = new StringArray();
        Integer diff;
        for (int i = 0; i < this.wordsToCorrect.size(); i++)
        {
            diff=1;
            suggestions = this.runLD(wordsToCorrect.get(i).toLowerCase(),diff);
            while (suggestions.size() < minSuggestionLength && diff < 1)
            {
                diff += 1;
                suggestions = this.runLD(wordsToCorrect.get(i), diff);
            }
            while (suggestions.size() == 0)
            {
                diff += 1;
                suggestions = this.runLD(wordsToCorrect.get(i), diff);
            }
            suggestions.insert(0,wordsToCorrect.get(i));
            String chosenWord = this.chooseWord(suggestions);
            newCorrectWords.add(chosenWord);
        }
        return newCorrectWords;
    }

    public String chooseWord(StringArray suggestions)
    {
        System.out.println("Original word is <" + suggestions.get(0) + ">");
        for (int i = 0; i < suggestions.size(); i++)
        {
            System.out.print(i + ". " + suggestions.get(i) + " ");
            if (i % 5 == 0 && i > 0) System.out.print("\n");
        }

        Integer wordChoice = getWordChoice();
        while (!suggestions.isValidIndex(wordChoice))
        {
            System.out.println("Invalid index entered");
            wordChoice = getWordChoice(); //ensures valid index entered
        }

        return suggestions.get(wordChoice);
    }

    private int getWordChoice()
    {
        Input input = new Input();
        System.out.println("\nSelect which word you want.");
        Integer newChoice = input.nextInt();
        return newChoice;
    }

    public void fileCorrectText() //creates the file with the desired words
    {
        for (Integer i: sp.getIncorrectWordIndexes())
        {
            sp.getWordsToCheck().set(i,this.correctWords.get(i));
        }
        Input input1 = new Input();
        System.out.print("Enter filename: ");
        String filename = this.inputString(input1);
        this.writeFile(filename);
    }

    private String inputString(Input input)
    {
        String s = input.nextLine();
        return s;
    }

    public void writeFile(String name)
    {
        FileOutput output = new FileOutput(name);
        for (int i = 0; i < sp.getWordsToCheck().size(); i++)
        {
            output.writeString(sp.getWordsToCheck().get(i));
            output.writeEndOfLine();
        }
        output.close();
    }

    public void runCorrectSpelling()
    {
        sp.initDict();
        while (true)
        {
            sp.runSP();
            if (sp.getChoice() == 0) break;

            this.wordsToCorrect = sp.getWordsToCorrect();
            if (this.wordsToCorrect.size() > 0) //if there are words present then correct words
            {
                System.out.println("\nWe are now going to correct these words ...");
                this.correctWords = this.runCorrector();
                this.fileCorrectText();
            }
        }
    }
}
