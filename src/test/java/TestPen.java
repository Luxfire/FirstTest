
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.*;

public class TestPen extends Assert {
    //inkValue expectedResult
    @DataProvider(name="constrInkData")
    public Object[][] createConstrInkData() {
        return new Object[][] {
                { 3,true},
                {-2,false}
        };
    }
    //inkValue sizeLetter expectedResult
    @DataProvider(name="constrInkLetterData")
    public Object[][] createConstrInkLetterData() {
        return new Object[][] {
                { 3, 2,true},
                {3,-1,false},
                {-3,2,false}
        };
    }
    //inkValue sizeLetter color expectedResult
    @DataProvider(name="constrColorData")
    public Object[][] createConstrColorData() {
        return new Object[][] {
                { 3, 2,"RED",true},
                {-1,2,"RED",false},
                {1,-2,"RED",false},
                {1,2,"",false}
        };
    }
    //inkValue expectedResult
    @DataProvider(name = "isWorkData")
    public Object[][] createIsWorkData() {
        return new Object[][] {
                { 10, true },
                { -1,false}
        };
    }
    //color
    @DataProvider(name = "getColorDoSomethingData")
    public Object[][] createGetColorDoSomethingData() {
        return new Object[][] {
                { "BLUE"},
                { "RED"}
        };
    }
    //sizeLetter printedWord expectedWord
    @DataProvider(name = "writeData")
    public Object[][] createWriteData() {
        return new Object[][] {
                {1, "qw","qw"},
                {1, "Hel lo","Hel lo"},
                {1, "qwwert","qwwer"},
                {2, "qwwe","qw"},
                {2, "qwwert","qw"}
        };
    }
    //sizeLetter printedWord
    @DataProvider(name="inkIsOverData")
    public Object[][] createInkIsOverData(){
        return new Object[][] {
                { 1, "qwe"},
                { -1, "qwe"}
        };
    }

    //Тест конструктора ручки только с чернилами
    @Test(dataProvider = "constrInkData")
    public void testPenConstrInk_penShouldExist(int inkValue, boolean penExist) {
        assertEquals(new Pen(inkValue).equals(null),!penExist);
    }
    //Тест конструктора ручки с чернилами и размером символов
    @Test(dataProvider = "constrInkLetterData")
    public void testPenConstrInkLetter_penShouldExist(int inkValue, int sizeLetter, boolean penExist) {
        assertEquals(new Pen(inkValue,sizeLetter).equals(null),!penExist);
    }
    //Тест конструктора ручки с чернилами, размером символов и цветом
    @Test(dataProvider = "constrColorData")
    public void testPenConstrColor_penShouldExist(int inkValue, int sizeLetter, String color, boolean penExist) {
        assertEquals(new Pen(inkValue,sizeLetter,color).equals(null),!penExist);
    }
    //Тест проверки наличия чернил в ручке
    @Test(dataProvider = "isWorkData")
    public void testPenIsWork(int inkValue, Boolean isWork){
            assertEquals(new Pen(inkValue).isWork(),isWork);
    }
    //Тест получения цвера ручки
    @Test(dataProvider = "getColorDoSomethingData")
    public void testPenGetColor_shouldGetColor(String color){
        assertEquals(new Pen(5,5,color).getColor(),color);
    }
    //Тeст печати слова или части слова в зависимости от кол-ва чернил
    @Test(dataProvider = "writeData")
    public void testPenWrite_shouldPrintWord(int sizeLetter,String printedWord,String expectedWord) {
        try {
            assertEquals(new Pen(5, sizeLetter).write(printedWord), expectedWord);
        }catch (IndexOutOfBoundsException e)
        {
            fail();
        }
    }
    //Тест проверки печати цвета ручки
    @Test(dataProvider = "getColorDoSomethingData")
    public void testPenDoSomething_shouldPrintColor(String color){
       PrintStream old = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        new Pen(5,5,color).doSomethingElse();
        assertEquals(outputStream.toString(),color+getEndOfLine());
        System.setOut(old);
    }
    //Тест на конец чернил в ручке
    @Test(dataProvider = "inkIsOverData")
    public void testPenWrite_inkShouldEnd(int sizeLetter,String printedWord){
        int limitCount=0;
        Pen pen = new Pen(5,sizeLetter);
        while(pen.isWork()&&limitCount<10) {
            pen.write(printedWord);
            limitCount++;
        }
        assertEquals(pen.isWork(),new Boolean(false));
    }

    public String getEndOfLine(){
        if(System.getProperty("os.name").contains("Windows")) return "\r\n";
        return "\n";
    }
}
