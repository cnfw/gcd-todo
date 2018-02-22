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
        this.xmlFile = xmlFile;
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

    /**
     * Get an {@link ArrayList} made of {@link BaseTodo} objects that are stored in the object's XML file
     * @return ArrayList of BaseTodo objects
     * @see #traverseAndAddTodos(NodeList)
     */
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

    /**
     * Stores the specified {@link ArrayList} of {@link BaseTodo} objects to the object's XML file
     * @param todoArray from the application - typically before closing
     * @see #buildElement(Document, BaseTodo)
     */
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

    /**
     * Parses a {@link BaseTodo} object into an Element suitable for storage in a {@link NodeList}
     * If {@link BaseTodo} is a {@link ListTodo} object, recursively call to add it's checklist
     * @param document document to create element with
     * @param todoItem the object to parse
     * @return resulting {@link Element}
     * @see #writeTodoArrayToFile(ArrayList)
     */
    private static Element buildElement(Document document, BaseTodo todoItem) {
        Element newTodo = document.createElement(todoItem.getTagName());

        newTodo.setAttribute("id", String.valueOf(todoItem.getId()));
        newTodo.setAttribute("title", todoItem.getTitle());
        newTodo.setAttribute("description", todoItem.getDescription());
        newTodo.setAttribute("completed", String.valueOf(todoItem.isCompleted()));
        newTodo.setAttribute("due", todoItem.getUKDate());

        if (todoItem instanceof ListTodo)
            for (BaseTodo checkListTodo : ((ListTodo) todoItem).getChecklist())
                newTodo.appendChild(buildElement(document, checkListTodo));

        return newTodo;
    }

    /**
     * Iterates through a provided {@link NodeList} and adds parsed {@link BaseTodo} objects to the object's
     * {@link XMLHelper#getTodoArray() todoList}
     * @param nodeList list to be added
     */
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

    /**
     * Parses a {@link Node} into a {@link BaseTodo} object
     * @param node item to parse
     * @return object
     */
    private static BaseTodo parseNormalTodo(Node node) {
        String title = node.getAttributes().getNamedItem("title").getNodeValue();
        String description = node.getAttributes().getNamedItem("description").getNodeValue();
        Boolean completed = Boolean.parseBoolean(node.getAttributes().getNamedItem("completed").getNodeValue());
        LocalDate dueDate = InputHelper.inputDate(node.getAttributes().getNamedItem("due").getNodeValue());

        return new BaseTodo(title, description, completed, dueDate);
    }

    /**
     * Parses a {@link Node} into a {@link ListTodo} object
     * @param node item to parse
     * @return object
     * @see #parseNormalTodo(Node) 
     */
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
