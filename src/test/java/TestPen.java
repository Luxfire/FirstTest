
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;

public class TestPen extends Assert {


    @DataProvider(name = "isWorkData")
    public Object[][] createIsWorkData() {
        return new Object[][] {
                { new Pen(10), true },
                { new Pen(-1),false}
        };
    }
    @DataProvider(name = "getColorData")
    public Object[][] createGetColorData() {
        return new Object[][] {
                { new Pen(10),"BLUE" },
                { new Pen(10,2,"RED"),"RED"}
        };
    }
    @DataProvider(name = "writeData")
    public Object[][] createWriteData() {
        return new Object[][] {
                { new Pen(5), "qw","qw"},
                { new Pen(10), "Hello world","Hello world"},
                { new Pen(5), "qwwert","qwwer"},
                { new Pen(5,2), "qwwe","qw"},
                { new Pen(5,2), "qwwert","qw"}
        };
    }
    @DataProvider(name="constrData")
    public Object[][] createConstrData() {
        return new Object[][] {
                { new Pen(5), null},
                { new Pen(5,2), null},
                { new Pen(2,2,"RED"), null}
        };
    }
    @DataProvider(name="doSomethingData")
    public Object[][] createDoSomethingData(){
        return new Object[][] {
                { new Pen(5), "BLUE"},
                { new Pen(2,2,"RED"), "RED"}
        };
    }
    @DataProvider(name="inkIsOverData")
    public Object[][] createInkIsOverData(){
        return new Object[][] {
                { new Pen(5,1), "qwe",""},
                { new Pen(5,-1), "qwe",""}
        };
    }

    @Test(dataProvider = "isWorkData")
    public void testPenIsWork(Pen pen, Boolean isWork){
            assertEquals(pen.isWork(),isWork);
    }

    @Test(dataProvider = "getColorData")
    public void testPenGetColor_shouldGetColor(Pen pen, String color){
            assertEquals(pen.getColor(),color);
    }

    @Test(dataProvider = "writeData")
    public void testPenWrite_shouldPrintWord(Pen pen, String printWord,String expectedWord) {
            assertEquals(pen.write(printWord),expectedWord);
    }

    @Test(dataProvider = "constrData")
    public void testPenConstr_penShouldExist(Pen pen,Object doesntExist) {
        assertNotEquals(pen,doesntExist);
    }

    @Test(dataProvider = "doSomethingData")
    public void testPenDoSomething_shouldPrintColor(Pen pen, String string){
       PrintStream old = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        pen.doSomethingElse();
        if(System.getProperty("os.name").contains("Windows")) string+="\r\n";
        else string+="\n";

        assertEquals(outputStream.toString(),string);
        System.setOut(old);
    }

    @Test(dataProvider = "inkIsOverData")
    public void testPenWrite_inkShouldEnd(Pen pen, String word,String expected) throws NoSuchFieldException {
        int limitCount=0;
        while(pen.isWork()&&limitCount<15) {
            pen.write(word);
            limitCount++;
        }
        assertEquals(pen.write(word),expected);
    }
}
