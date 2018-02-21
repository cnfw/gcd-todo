package uk.cw1998.gcd.todo.util;

import org.w3c.dom.*;
import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.items.ListTodo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class XMLHelper {

    private ArrayList<BaseTodo> todoArray;
    private File xmlFile;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;

    public XMLHelper(String filePath) {
        this();
        xmlFile = new File(filePath);
    }

    public XMLHelper(File xmlFile) {
        this();
        xmlFile = xmlFile;
    }

    private XMLHelper() {
        todoArray = new ArrayList<>();
        try {
            factory = DocumentBuilderFactory.newDefaultInstance();
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.err.println("Something went wrong loading your saved Todo's.");
            System.exit(1);
        }
    }

    public ArrayList<BaseTodo> getTodoArray() {
        todoArray.clear();
        try {
            Document document = builder.parse(xmlFile);

            NodeList nodeList = document.getDocumentElement().getChildNodes();
            traverseAndAddTodos(nodeList);

        } catch (Exception e) {
            System.err.println("Something went wrong loading your saved Todo's. Do you have the correct permissions?");
            System.exit(1);
        }

        return todoArray;
    }

    public void writeTodoArrayToFile(ArrayList<BaseTodo> todoArray) {
        try {
            Document document = builder.newDocument();

            Element root = document.createElement("gcd-todo");
            document.appendChild(root);

            for (BaseTodo todo : todoArray)
                root.appendChild(buildElement(document, todo));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);

            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            System.err.println("Something went wrong saving your Todo's. Do you have the correct permissions?");
            System.exit(1);
        }
    }

    private static Element buildElement(Document document, BaseTodo todo) {
        Element newTodo = document.createElement(todo.getTagName());

        newTodo.setAttribute("id", String.valueOf(todo.getId()));
        newTodo.setAttribute("title", todo.getTitle());
        newTodo.setAttribute("description", todo.getDescription());
        newTodo.setAttribute("completed", String.valueOf(todo.isCompleted()));
        newTodo.setAttribute("due", todo.getUKDate());

        if (todo instanceof ListTodo)
            for (BaseTodo checkListTodo : ((ListTodo) todo).getChecklist())
                newTodo.appendChild(buildElement(document, checkListTodo));

        return newTodo;
    }

    private void traverseAndAddTodos(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++)
            if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) // Ensure text nodes aren't added
                switch (nodeList.item(i).getNodeName()) {
                    case "BaseTodo":
                        todoArray.add(parseNormalTodo(nodeList.item(i)));
                        break;
                    case "ListTodo":
                        todoArray.add(parseListTodo(nodeList.item(i)));
                        break;
                    default:
                }
    }

    private static BaseTodo parseNormalTodo(Node node) {
        String title = node.getAttributes().getNamedItem("title").getNodeValue();
        String description = node.getAttributes().getNamedItem("description").getNodeValue();
        Boolean completed = Boolean.parseBoolean(node.getAttributes().getNamedItem("completed").getNodeValue());
        LocalDate dueDate = InputHelper.inputDate(node.getAttributes().getNamedItem("due").getNodeValue());

        return new BaseTodo(title, description, completed, dueDate);
    }

    private static ListTodo parseListTodo(Node node) {
        ListTodo newListTodo = new ListTodo(parseNormalTodo(node));

        NodeList checklistItems = node.getChildNodes();

        for (int i = 0; i < checklistItems.getLength(); i++) {
            Node checklistNode = checklistItems.item(i);
            if (checklistNode.getNodeType() == Node.ELEMENT_NODE)
                newListTodo.addToCheckList(parseNormalTodo(checklistNode));
        }

        return newListTodo;
    }
}
