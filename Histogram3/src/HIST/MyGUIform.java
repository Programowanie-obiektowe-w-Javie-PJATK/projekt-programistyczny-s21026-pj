package HIST;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class MyGUIform extends JFrame{
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;

    private JButton przyciskButton;
    private JTextArea textArea1;
    private JPanel rootPanel;
    private JTextArea textArea2;
    private JRadioButton zTekstuRadioButton;
    private JRadioButton zPlikuRadioButton;
    private JButton zapiszDoPlikuButton;
    private JTextField textField1;
    private String userinput;
    private String sourcechoice="Z tekstu";

    public MyGUIform(){
        add(rootPanel);
        setLocationByPlatform(true);
        setTitle("Histogram Maker v0.8");
        setSize(screenWidth/4,screenHeight/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(rootPane, "Za chwile zostanie uruchomiony program do tworzenia histogramu" +
                "\n Kolejność działań:" +
                "\n 1.Podaj tekst do przetworzenia lub nazwe pliku" +
                "\n 2.Wybierz czy podałeś tekst czy nazwe pliku" +
                "\n 3.Kliknij utwórz histogram" +
                "\n 4.Opcjonalnie możesz zapisać wynik do pliku");
        zapiszDoPlikuButton.setEnabled(false);
        textField1.setEnabled(false);
        przyciskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sourcechoice.equals("Z tekstu")){
                    textArea2.setText(makefromtext(textArea1.getText()));
            }else{
                    textArea2.setText(makefromfile(textArea1.getText()));
                }
                JOptionPane.showMessageDialog(rootPane, "Utworzono histogram teraz możesz go zapisać do pliku");
                zapiszDoPlikuButton.setEnabled(true);
                textField1.setEnabled(true);
            }
        });
        zTekstuRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sourcechoice="Z tekstu";
            }
        });
        zPlikuRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sourcechoice="Z pliku";
            }
        });

        zapiszDoPlikuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zapiszdopliku(textField1.getText(),textArea2.getText());
                JOptionPane.showMessageDialog(rootPane, "Zapisano do pliku: "+textField1.getText());
            }
        });
    }
    private String makefromtext(String input){
        String wynik="";
        ArrayList<String> inputFile = new ArrayList<String>();
        Scanner inputData = null;
        inputData = new Scanner(input);
        while (inputData.hasNextLine()) {
            String data = inputData.next();
            inputFile.add(data);
        }
        inputData.close();

        Map<String, Integer> wordsMap = new TreeMap<String, Integer>();
        for (int i = 0; i < inputFile.size(); i++) {
            String temp = inputFile.get(i);
            String word = temp;
            if (wordsMap.putIfAbsent(word, 1) != null) {   //Wprowadź obiekt do mapy jeżeli jeszcze go
                //niema i przypisz mu 1, jeśli jest
                wordsMap.put(word, (wordsMap.get(word) + 1)); //pobierz jego wartość
                //zwiększ o 1 i zaktualizuj
            }
        }
        System.out.printf("\n***************\nZestawienie ile jest poszczególnych słów:\n");
        for (Map.Entry<String, Integer> entry : wordsMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " " + value);
            wynik+=(key + ": " + value+"\n");
        }

        return wynik;
    }

    private String makefromfile(String input){
        String wynik="";
        File file = new File(input);
        if (!file.exists()){
            System.out.println("Nie ma takiego pliku");
            JOptionPane.showMessageDialog(rootPane, "Nie ma takiego pliku !!!\nProgram zostanie zamknięty :(");
            System.exit(0);
        }else {
            System.out.println("***Podany plik istnieje***");
            ArrayList<String> inputFile = new ArrayList<String>();
            Scanner inputData = null;
            try {
                inputData = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (inputData.hasNextLine()) {
                String data = inputData.next();
                inputFile.add(data);
            }
            inputData.close();

            Map<String, Integer> wordsMap = new TreeMap<String, Integer>();
            for (int i = 0; i < inputFile.size(); i++) {
                String temp = inputFile.get(i);
                String word = temp;
                if (wordsMap.putIfAbsent(word, 1) != null) {   //Wprowadź obiekt do mapy jeżeli jeszcze go
                    //niema i przypisz mu 1, jeśli jest
                    wordsMap.put(word, (wordsMap.get(word) + 1)); //pobierz jego wartość
                    //zwiększ o 1 i zaktualizuj
                }
            }
            System.out.printf("\n***************\nZestawienie ile jest poszczególnych słów:\n");
            for (Map.Entry<String, Integer> entry : wordsMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println(key + " " + value);
                wynik+=(key + ": " + value+"\n");
            }
        }
        return wynik;
}
    private void zapiszdopliku(String nazwapliku, String dane){


        PrintWriter output = null;
        try {
            output = new PrintWriter(nazwapliku);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
            Scanner input=new Scanner(dane);
            System.out.println("Podany plik istnieje");
            System.out.println("Podaj ciąg do zapisania w nim");
            while (input.hasNext()) {
                output.println(input.nextLine());
            }
            output.close();
        }
    }

