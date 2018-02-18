package uk.cw1998.gcd.todo.util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.items.NormalTodo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class XMLHelper {

    private static ArrayList<BaseTodo> todoArray = new ArrayList<>();

    public static ArrayList<BaseTodo> getTodoArray() {
        try {
            File xmlFile = new File("C:\\Users\\hp\\IdeaProjects\\gcd-todo\\gcd-todo.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            NodeList nodeList = document.getDocumentElement().getChildNodes();
            traverseAndAddTodos(nodeList);

        } catch (FileNotFoundException e) {
            // TODO Create new file and try again
        } catch (Exception e) {
            System.err.println("Something went wrong loading your saved Todo's. Do you have the correct permissions?");
            System.err.println(e);
            System.exit(1);
        }

        return todoArray;
    }

    private static void traverseAndAddTodos(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++)
            if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE)
                switch (nodeList.item(i).getNodeName()) {
                    case "NormalTodo":
                        todoArray.add(parseNormalTodo(nodeList.item(i)));
                        break;
                    case "ListTodo":
                        break;
                    default:
                }
    }

    private static NormalTodo parseNormalTodo(Node node) {
        String title = node.getAttributes().getNamedItem("title").getNodeValue();
        String description = node.getAttributes().getNamedItem("description").getNodeValue();
        Boolean completed = Boolean.parseBoolean(node.getAttributes().getNamedItem("completed").getNodeValue());

        return new NormalTodo(title, description, completed);
    }
}
