package org.peacockteam.similar;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConverterTest {

    public static void main(String[] args) throws IOException {

        ViewConverter converter = new ViewConverter();

        converter.load(new String[]{"Андрей Иванов", "В. Иванов", "Петров", "Петрм Петров"});

        converter.getProgress();
        converter.getResult();

      //  ConverterTest test = new ConverterTest();
      //  test.test("D:\\work\\proj\\data\\Officers.csv", "D:\\work\\proj\\data\\declarator\\dataset_persons.csv");
    }


    public void test(String path1, String path2) throws IOException {

        ViewConverter converter = new ViewConverter();

        List<String> objectStrings1,
                objectStrings2;

        Utils.step(0);
        objectStrings1 = this.read(path1);

        Utils.step(1);
        objectStrings2 = this.read(path2);


        List<ViewObject> objectList1,
            objectList2;

        Utils.step(2);
        objectList1 = this.transformPanama(objectStrings1);

        Utils.step(3);
        objectList2 = this.transformDeclarator(objectStrings2);

        objectList1.addAll(objectList2);

        Utils.step(4);
        converter.load(objectList1);

        System.out.println("Source 1: " + objectStrings1.size());
        System.out.println("Source 2: " + objectStrings2.size());
        System.out.println("Total FOUND: " + converter.getResult().length);
    }

    private List<String> read(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));

        List<String> commands = new ArrayList<String>();
        String line = br.readLine();

        while (line != null) {
            commands.add(line);
            line = br.readLine();
        }

        return commands;
    }

    private List<ViewObject> transformPanama(List<String> list){
        List<ViewObject> objectList = new ArrayList<ViewObject>();

        for (String key : list) {
            objectList.add(new ViewObject(key.split(",")[0]));
        }

        return objectList;
    }

    private List<ViewObject> transformDeclarator(List<String> list){
        List<ViewObject> objectList = new ArrayList<ViewObject>();

        String[] splitKey;

        for (String key : list) {
            splitKey = key.split(",");
            key = splitKey[1];

            if (splitKey.length > 2)
                key += " " + splitKey[2];

            if (splitKey.length > 3)
                key += " " + splitKey[3];

            objectList.add(new ViewObject(key));
        }

        return objectList;
    }
}
