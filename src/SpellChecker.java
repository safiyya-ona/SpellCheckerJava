import java.util.ArrayList; //this is not used for strings, just for the integers
public class SpellChecker
{
    private StringArray dictWords = new StringArray();
    private StringArray wordsToCheck  = new StringArray();
    private StringArray wordsToCorrect = new StringArray();
    private Integer choice;
    private ArrayList<Integer> incorrectWordIndexes = new ArrayList<Integer>();

    public void initDict()
    {
        try (FileInput input = new FileInput("words")) //change to words2.txt for handling with plurals
        {
            while (input.hasNextLine())
            {
                String s = input.nextLine();
                this.dictWords.add(s);

            }
        }
    }

    public StringArray getWordsToCheck()
    {
        return wordsToCheck;
    }

    public ArrayList<Integer> getIncorrectWordIndexes()
    {
        return incorrectWordIndexes;
    }

    public StringArray getWordsToCorrect()
    {
        return wordsToCorrect;
    }

    public StringArray getDictWords()
    {
        return dictWords;
    }

    public Integer getChoice()
    {
        return choice;
    }

    public StringArray removeDelimiters(StringArray userInput) //removes all delimiters and numbers
    {
        StringArray removed = new StringArray();
        for (int i = 0; i < userInput.size(); i++)
        { removed.add(userInput.get(i).replaceAll("[^a-zA-Z\\s]", "")); }
        return removed;
    }

    public StringArray initWordsToCheck(StringArray input)
    {
        StringArray userInput = this.removeDelimiters(input);
        StringArray pureWords = new StringArray();
        for (int i = 0; i < userInput.size(); i++)
            for (String j : userInput.get(i).split("\\s"))
            {
                if (j == "") continue;
                pureWords.add(j);
            }
        return pureWords;
    }

    public void fromFile()
    {
        Input input1 = new Input();
        System.out.print("Enter filename: ");
        String filename = this.getFromConsole(input1);
        StringArray userInput = new StringArray();
        FileInput input = new FileInput(filename);
        while (input.hasNextLine())
        {
            String s = input.nextLine();
            userInput.add(s);
        }
        this.wordsToCheck = this.initWordsToCheck(userInput);
    }

    public void fromConsole()
    {
        Input input = new Input();
        System.out.print("Enter your desired text: ");
        String s = this.getFromConsole(input);
        StringArray userInput = new StringArray();
        userInput.add(s);
        this.wordsToCheck = this.initWordsToCheck(userInput);
    }

    private int getNewChoice()
    {
        Input input = new Input();
        System.out.print("Enter number for option:\n 1. Get text from console;\n 2. Get text from file;\n 0 or anything else. Quit: ");
        Integer newChoice = input.nextInt();
        return newChoice;
    }

    private String getFromConsole(Input input)
    {
        String s = input.nextLine();
        return s;
    }

    public void checkWords()
    {
        this.wordsToCorrect = new StringArray();
        this.incorrectWordIndexes = new ArrayList<Integer>();
        for (int i = 0; i < this.wordsToCheck.size(); i++)
        {
            if (!binarySearch(0, this.dictWords.size(), this.wordsToCheck.get(i))) //if word is in dictionary
                this.wordsToCorrect.add(this.wordsToCheck.get(i));
                this.incorrectWordIndexes.add(i);
        }
    }

    public boolean binarySearch(int start, int end,String target)
    {
        Integer mid;
        while(start <= end && start < this.dictWords.size() && end <= this.dictWords.size())
        {
            mid = (start + end) /2;
            if (this.dictWords.get(mid)==null) return false;
            if (this.dictWords.get(mid).compareToIgnoreCase(target) == 0) return true;
            else if (this.dictWords.get(mid).compareToIgnoreCase(target) < 0 ) // if word is above in the dictionary
                start = mid + 1;
            else if (this.dictWords.get(mid).compareToIgnoreCase(target) > 0) // if word is below in the dictionary
                end = mid - 1;

        }
        return false;
    }

    public void printIncorrectWords()
    {
        if (this.wordsToCorrect.isEmpty())
        { System.out.println("No words spelt incorrectly!"); return; }

        System.out.println("These are the words that need correcting:");
        for (int i = 0; i < this.wordsToCorrect.size(); i++)
        {
            System.out.println(this.wordsToCorrect.get(i));
        }
    }

    public void processChoice(int choice)
    {
        switch (choice)
        {
            case 1:
                fromConsole(); break;
            case 2:
                fromFile(); break;
            case 0:
                System.out.println("Program terminating..."); break;
            default:
                System.out.println("Invalid option."); break;
        }
    }

    public void runSP()
    {
        this.choice = this.getNewChoice();
        if (choice == 0) { System.out.println("Program terminating..."); return; }
        this.processChoice(choice);
        this.checkWords();
        this.printIncorrectWords();
    }

}
