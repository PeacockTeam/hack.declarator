package org.peacockteam.similar;

import java.util.ArrayList;
import java.util.List;


public class ViewConverter {

    private List<ViewObject> objectList = new ArrayList<ViewObject>();

    private List<String> objectsResult = new ArrayList<String>();

    private int progress = 0;

    public void load(String[] objects){

        for (int i = 0; i < objects.length; i++){
            this.objectList.add(new ViewObject(objects[i]));
        }

        this.convert();
    }

    public void load(List<ViewObject> objects){
        this.objectList = objects;
        this.convert();
    }

    private void convert(){
        int counter = 0, size = this.objectList.size();

        for (int i = 0; i < size; i++){
            counter++;

            for (int j = i + 1; j < size; j++){
                if (counter % 1000 == 0){
                    progress =  counter * 100 / size;
               //     System.out.println("Progress: " + progress);
                }

                this.compare(this.objectList.get(i), this.objectList.get(j));
            }
        }
    }

    private void compare(ViewObject object1, ViewObject object2){
        String[] split1 = object1.converted,
                split2 = object2.converted;

        int size = (object1.convertedSize + object2.convertedSize) / 2,
                compareVal = 0;

        for (int i = 0; i < split1.length; i++){

            for (int j = 0; j < split2.length; j++){

                if (split1[i].equals(split2[j])){
                    compareVal++;
                }
            }
        }

        if (compareVal == size && size > 0){
            this.objectsResult.add(object1.name + ";" + object2.name);

       //     System.out.println("Found pair [" + object1.name + "]  +  [" + object2.name + "]");
        }
    }

    public int getProgress(){
        return progress;
    }

    public String[] getResult(){
        String[] stockArr = new String[this.objectsResult.size()];
        return this.objectsResult.toArray(stockArr);
    }
}
