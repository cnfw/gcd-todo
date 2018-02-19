package uk.cw1998.gcd.todo.util;

import org.w3c.dom.*;
import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.items.NormalTodo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class XMLHelper {

    private static ArrayList<BaseTodo> todoArray;

    public static ArrayList<BaseTodo> getTodoArray() {
        todoArray = new ArrayList<>();
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
            System.exit(1);
        }

        return todoArray;
    }

    public static void writeTodoArrayToFile(ArrayList<BaseTodo> todoArray) {
        try {
            File xmlFile = new File("C:\\Users\\hp\\IdeaProjects\\gcd-todo\\gcd-todo.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("gcd-todo");
            document.appendChild(root);

            for (BaseTodo todo : todoArray) {
                Element newTodo = document.createElement("NormalTodo"); // Assume Normal until otherwise changed
                newTodo.setAttribute("id", String.valueOf(todo.getId()));
                newTodo.setAttribute("title", todo.getTitle());
                newTodo.setAttribute("description", todo.getDescription());
                newTodo.setAttribute("completed", String.valueOf(todo.isCompleted()));
                root.appendChild(newTodo);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);

            transformer.transform(domSource, streamResult);

        } catch (Exception e) {

        }
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
