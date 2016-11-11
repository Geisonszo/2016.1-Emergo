package unlv.erc.emergo.helper;

import junit.framework.TestCase;

import helper.MaskHelper;

public class MaskHelperTest  extends TestCase {

  MaskHelper maskHelper = new MaskHelper();

  public void testIfStringChanges() {

    String name = "jujuba";
    String name1 = name;
    name1 = maskHelper.unmask(name);
    assertSame(name , name1);

  }

  public void testIfDotDisapear() {
    String string1 = "leo.leo";
    String string2 = "leoleo";
    string1 = maskHelper.unmask(string1);
    assertEquals(string1 , string2);
  }

  public void testIfReturnsVoid() {
    boolean passou = true;
    String isVoid = "";
    String symbols = "././";
    symbols = maskHelper.unmask(symbols);

    if(symbols.compareTo(isVoid) == 0){
      assertTrue(passou);
    }else{
      assertFalse(passou);
    }
  }

}
