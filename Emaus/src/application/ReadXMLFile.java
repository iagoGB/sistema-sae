package application;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

//codigo --> https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
//lê o XML do arquivo de doações
public class ReadXMLFile {
	public static Doacoes getFile(String local) {
		Doacoes doador = null;
	    try {

		File fXmlFile = new File(local);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = (Document) dBuilder.parse(fXmlFile);
				
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		((Node) doc.getDocumentElement()).normalize();

		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				
		NodeList nList = ((org.w3c.dom.Document) doc).getElementsByTagName("doacao");
				
		System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {
	
				Node nNode = nList.item(temp);
						
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
					Element eElement = (Element) nNode;
	
					System.out.println("Nome : " + eElement.getElementsByTagName("nome").item(0).getTextContent());
					System.out.println("Instituicao : " + eElement.getElementsByTagName("instituicao").item(0).getTextContent());
					System.out.println("Email : " + eElement.getElementsByTagName("email").item(0).getTextContent());
					System.out.println("Telefone : " + eElement.getElementsByTagName("telefone").item(0).getTextContent());
					System.out.println("Endereco : " + eElement.getElementsByTagName("endereco").item(0).getTextContent());
					System.out.println("Referencia : " + eElement.getElementsByTagName("referencia").item(0).getTextContent());
					System.out.println("Bairro : " + eElement.getElementsByTagName("bairro").item(0).getTextContent());
					System.out.println("Condeudo : " + eElement.getElementsByTagName("conteudo").item(0).getTextContent());
					System.out.println("Conhece : " + eElement.getElementsByTagName("conhece").item(0).getTextContent());
					System.out.println("Complementares : " + eElement.getElementsByTagName("complementares").item(0).getTextContent());
					
					doador = new Doacoes(eElement.getElementsByTagName("nome").item(0).getTextContent(),
					eElement.getElementsByTagName("instituicao").item(0).getTextContent(),
					eElement.getElementsByTagName("email").item(0).getTextContent(),
					eElement.getElementsByTagName("telefone").item(0).getTextContent(),
					eElement.getElementsByTagName("endereco").item(0).getTextContent(),
					eElement.getElementsByTagName("referencia").item(0).getTextContent(),
					eElement.getElementsByTagName("bairro").item(0).getTextContent(),
					eElement.getElementsByTagName("conteudo").item(0).getTextContent(),
					eElement.getElementsByTagName("conhece").item(0).getTextContent(),
					eElement.getElementsByTagName("complementares").item(0).getTextContent(),
					"0","0");
					
				}
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return doador;
	  }
}
