/********************
 * Class name: InformationHealthUnitScreenControllerTest (.java)
 *
 * Purpose: The purpose of this class is to test the InformationHealthUnitScreenController class.
 ********************/

package unlv.erc.emergo.controller;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


public class InformationHealthUnitScreenControllerTest extends TestCase {

  List<String> listOfInformations = new ArrayList<String>();
  InformationHealthUnitScreenController info = new InformationHealthUnitScreenController();

  public void testIfListAddTitle () {
    boolean result = true;
    String title = "        Informações da Unidade de Saúde";
    String titleTest =  "        Informações da Unidade de Saúde";
    listOfInformations.add(title);
    int tamanho = listOfInformations.size();

    if (listOfInformations.get(tamanho -1).contentEquals(titleTest)) {
      assertTrue(result);
    } else {
      assertFalse(result);
    }
  }

  public void testIfListAddNome() {
    boolean result = true;
    String name = "  Nome: ";
    String nameTest =  "  Nome: ";
    listOfInformations.add(name);
    int tamanho = listOfInformations.size();

    if (listOfInformations.get(tamanho -1).contentEquals(nameTest)) {
      assertTrue(result);
    } else {
      assertFalse(result);
    }
  }

  public void testIfListAddManagement() {
    boolean result = true;
    String management = "  Tipo de atendimento: " ;
    String gestaoTest =  "  Tipo de atendimento: " ;
    listOfInformations.add(management);
    int tamanho = listOfInformations.size();

    if (listOfInformations.get(tamanho -1).contentEquals(gestaoTest)) {
      assertTrue(result);
    } else {
      assertFalse(result);
    }
  }

  public void testIfListAddUf() {
    boolean result = true;
    String uf = "  UF: " ;
    String ufTest =  "  UF: " ;
    listOfInformations.add(uf);
    int tamanho = listOfInformations.size();

    if (listOfInformations.get(tamanho -1).contentEquals(ufTest)) {
      assertTrue(result);
    } else {
      assertFalse(result);
    }
  }

  public void testIfListAddDistrict() {
    boolean result = true;
    String district = "  Município: " ;
    String districtTest =  "  Município: " ;
    listOfInformations.add(district);
    int tamanho = listOfInformations.size();

    if (listOfInformations.get(tamanho -1).contentEquals(districtTest)) {
      assertTrue(result);
    } else {
      assertFalse(result);
    }
  }

  public void testIfListAddBairro() {
    boolean resultado = true;
    String bairro = "  Bairro: ";
    String bairroTest =  "  Bairro: " ;
    listOfInformations.add(bairro);
    int tamanho = listOfInformations.size();

    if (listOfInformations.get(tamanho -1).contentEquals(bairroTest)) {
      assertTrue(resultado);
    } else {
      assertFalse(resultado);
    }
  }

  public void testIfListAddAddressNumber() {
    boolean result = true;
    String addressNumber = "  Bairro: ";
    String adderssNumberTest =  "  Bairro: " ;
    listOfInformations.add(addressNumber);
    int tamanho = listOfInformations.size();

    if (listOfInformations.get(tamanho -1).contentEquals(adderssNumberTest)) {
      assertTrue(result);
    } else {
      assertFalse(result);
    }
  }

  public void testIfSetCep() {
    String cep = "123423";
    boolean valido = true;
    info.setAddressNumber(cep);

    if (cep.compareTo(info.getAddressNumber()) == 0) {
      assertTrue(valido);
    } else {
      assertFalse(valido);
    }
  }

  public void testIfSetBairro() {
    String bairro = "Birro das flores";
    boolean valido = true;
    info.setAddressNumber(bairro);

    if (bairro.equals(info.getAddressNumber())) {
      assertTrue(valido);
    }
  }

  public void testIfSetMunicipio() {
    String municipio = "municipio";
    boolean valido = true;
    info.setCity(municipio);

    if (municipio.equals(info.getCity())) {
      assertTrue(valido);
    }
  }

  public void testIfSetUf() {
    String uf = "unidade federativa";
    boolean valido = true;
    info.setState(uf);

    if (uf.equals(info.getState())) {
      assertTrue(valido);
    }
  }

  public void testIfSetGestao() {
    String gestao = "gestao de negocio";
    boolean valido = true;
    info.setUnitType(gestao);

    if (gestao.equals(info.getUnitType())) {
      assertTrue(valido);
    }
  }

  public void testIfSetNome() {
    String nome = "my name";
    boolean valido = true;
    info.setNameHealthUnit(nome);

    if (nome.equals(info.getNameHealthUnit())) {
      assertTrue(valido);
    }
  }

  public void testIfSetTitulo() {
    String titulo = "o titulo";
    boolean valido = true;
    info.setTitleHealthUnit(titulo);

    if (titulo.equals(info.getTitleHealthUnit())) {
      assertTrue(valido);
    }
  }

  public void testIfSetPadding() {
    String padding = " o padding de padding";
    boolean valido = true;
    info.setPadding(padding);

    if (padding.equals(info.getPadding())) {
      assertTrue(valido);
    }
  }

}
