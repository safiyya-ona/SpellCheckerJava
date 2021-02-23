import java.util.Arrays;

public class StringArray
{
    private String contents[];
    private int tail = 0; // this is used to add to the array more efficiently
    public StringArray()
    {
        contents = new String[100];
    }

    public  StringArray(StringArray a)
    {
        this.contents = a.contents.clone();
    }

    public int size()
    {
        return this.tail;
    }

    public String get(int index)
    {
        if (!isValidIndex(index)){return null;}
        return this.contents[index];
    }

    public boolean isEmpty()
    {
        for (String i: this.contents)
            if (i != null)
                return false;
        return true;
    }

    public void set(int index, String s)
    {
        if (isValidIndex(index)) return;
        this.contents[index]  = s;
    }

    public void add(String s)
    {
        if (this.isFull()) this.extend();
        this.contents[this.tail] = s;
        this.tail+=1;
    }

    public void insert(int index, String s)
    {
        if (!isValidIndex(index)) return;
        if (this.get(this.tail) != null) this.extend();
        for (int i = this.tail; i > index; i--)
            this.contents[i] = this.contents[i - 1];
        this.contents[index]  = s;
        this.tail+=1;
    }

    public void remove(int index)
    {
        if (!isValidIndex(index)) return;
        for (int i = index; i <= this.tail; i++)
            this.contents[i] = this.contents[i + 1];
        this.tail-=1;
    }

    public boolean contains(String s)
    {
        for (int i = 0; i < this.tail; i++)
        {
            if (this.contents[i] == null)
            {
                if (this.contents[i] == s) return true;
                continue;
            }
            String currentString = this.contents[i];
            if (currentString.compareToIgnoreCase(s)==0) return true;
        }
        return false;
    }

    public boolean containsMatchingCase(String s)
    {
        for (int i = 0; i < this.tail; i++)
        {
            if (this.contents[i] == null)
            {
                if (this.contents[i] == s) return true;
                continue;
            }
            if (this.contents[i].contentEquals(s)) return true;
        }
        return false;
    }

    public int indexOf(String s)
    {
        for (int i = 0; i < this.tail; i++)
        {
            if (this.contents[i] == null)
            {
                if (this.contents[i] == s) return i;
                continue;
            }
            String currentString = this.contents[i];
            if (currentString.compareToIgnoreCase(s)==0) return i;
        }
        return -1;
    }

    public int indexOfMatchingCase(String s)
    {
        for (int i = 0; i < this.tail; i++)
        {
            if (this.contents[i] == null)
            {
                if (this.contents[i] == s) return i;
                continue;
            }
            if (this.contents[i].contentEquals(s)) return i;
        }
        return -1;
    }


    private void setTail()
    {
        for (int i = this.contents.length-1; i >= 0; i--)
        {
            if (this.contents[i] != null)
            {   this.tail = i+1;
                return;
            }
        }
    }

    //ensures index is valid when entered
    public boolean isValidIndex(int index)
    {
        return (index >= 0 && index < this.tail);
    }

    private boolean isFull()
    {
        if (this.tail >= this.contents.length) return true;
        else return false;
    }

    private void extend()
    {
        this.contents = Arrays.copyOf(this.contents, (int) Math.ceil(this.contents.length * 1.5));
        this.setTail();
    }

    private void printStrings() //helped with debugging, and printing out the contents of the StringArray
    {
        for (int i = 0; i < this.size(); i++)
        {
            System.out.print(this.get(i) + " ");
        }
        System.out.println();
    }
}
