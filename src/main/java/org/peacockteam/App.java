package org.peacockteam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.peacockteam.converters.DateConverter;
import org.peacockteam.converters.ProductsConverter;
import org.peacockteam.model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Date;

public class App
{
    private static char DEL = '|';
    private static String ZAKUPKI_IN_DIR = "C:\\Projects\\Json2Csv\\zakupki\\";
    private static String ZAKUPKI_OUT_DIR = "C:\\Projects\\Json2Csv\\zakupki_csv\\";

    private static String COMPANIES_IN_DIR = "C:\\Projects\\Json2Csv\\companies\\";
    private static String COMPANIES_OUT_DIR = "C:\\Projects\\Json2Csv\\companies_csv\\";

    private static String ZAKUPKA = ZAKUPKI_IN_DIR + "contracts_94fz_201312-20160611.json";
    private static String DUMPS_DIR = "dumps\\";
    private static String CUSTOMERS = "C:\\Projects\\Json2Csv\\customers\\customers-20160611.json";
    private static String SUPPLIERS = "C:\\Projects\\Json2Csv\\suppliers\\suppliers-20160611.json";
    public static void main( String[] args )
    {
        jsonToCsv(ZAKUPKI_IN_DIR, ZAKUPKI_OUT_DIR);
//        jsonToCsv(COMPANIES_IN_DIR, COMPANIES_OUT_DIR);
//        createMiniDump(new File(SUPPLIERS), 10);
//        createMiniDump(new File(CUSTOMERS), 10);
//        createMiniDump(new File(ZAKUPKA), 10);
    }
    private static EntryProcessor<Zakupka> zakupkaEntryProcessor = new EntryProcessor<Zakupka>() {
        public String processEntry(Zakupka zakupka) {
            if (zakupka.getId() == null || (zakupka.getCustomer() == null && (zakupka.getSuppliers() == null || zakupka.getSuppliers().size() ==0))) {
                return null;
            }
            String suppliers = "";
            if (zakupka.getSuppliers() != null) {
                int j = 0;
                int size = zakupka.getSuppliers().size();
                for (Company supplier : zakupka.getSuppliers()) {
                    suppliers += supplier.getId();
                    if (++j < size) {
                        suppliers += '$';
                    }
                }
            }

            return zakupka.getId()
                    + DEL + zakupka.getFz()
                    + DEL + zakupka.getPrice()
                    + DEL + zakupka.getUrl()
                    + DEL + zakupka.getDate()
                    + DEL + (zakupka.getCustomer() != null ? zakupka.getCustomer().getId() : "")
                    + DEL + suppliers;
//                        + del + (zakupka.getProducts() != null ? zakupka.getProducts() : "")
        }
    };

    private static EntryProcessor<CompanyFull> customerEntryProcessor = new EntryProcessor<CompanyFull>() {
        public String processEntry(CompanyFull companyFull) {
            return companyFull.getInn()
                    + DEL + companyFull.getKpp()
                    + DEL + companyFull.getName()
                    + DEL + companyFull.getOgrn();
        }
    };

    public static void jsonToCsv(String inDir, String outDir) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Products.class, new ProductsConverter());
        builder.registerTypeAdapter(Date.class, new DateConverter());
        Gson gson = builder.create();
        new File(outDir).mkdirs();
        File dir = new File(inDir);
        File[] files = dir.listFiles();
        if (files != null) {
            int i = 0;
            for (File file : files) {
                if (FilenameUtils.getExtension(file.getName()).equals("json")) {
                    System.out.println("processing (" + ++i + '/' + files.length + "): " + file.getName() + " (" + FileUtils.byteCountToDisplaySize(file.length()) + ")");
                    long start = System.currentTimeMillis();
//                    String outFileName = outDir + FilenameUtils.getBaseName(file.getName()) + ".csv";
                    String outFileName = outDir + "All.csv";
                    Type type;
                    EntryProcessor entryProcessor;
                    if (file.getName().contains("contracts_44")) {
                        type = Zakupka44.class;
                        entryProcessor = zakupkaEntryProcessor;

                    } else if (file.getName().contains("contracts_223")) {
                        type = Zakupka223.class;
                        entryProcessor = zakupkaEntryProcessor;
                    } else if(file.getName().contains("contracts_94")) {
                        type = Zakupka94.class;
                        entryProcessor = zakupkaEntryProcessor;
                    } else if (file.getName().contains("customers")) {
                        type = CustomerFull.class;
                        entryProcessor = customerEntryProcessor;
                    } else if (file.getName().contains("suppliers")) {
                        type = SupplierFull.class;
                        entryProcessor = customerEntryProcessor;
                    } else {
                        System.out.println("Unknown file format: " + file.getName());
                        continue;
                    }
                    int failed = processFile(gson, type, file, outFileName, entryProcessor);
                    long end = System.currentTimeMillis();
                    System.out.println("finished: " + DurationFormatUtils.formatDurationHMS(end - start) + " strings failed: " + failed);
                }
            }
        }
    }


    public static int processFile(Gson gson, Type type, File inFile, String outFileName, EntryProcessor entryProcessor) {
        JsonReader reader = null;
        BufferedWriter writer = null;
        int failedStrings = 0;

        try {
            reader = new JsonReader(new FileReader(inFile));
            writer = new BufferedWriter(new FileWriter(outFileName, true));
            reader.beginArray();
            while (reader.hasNext()) {
                String entryString = entryProcessor.processEntry(gson.fromJson(reader, type));
                if (entryString == null) {
                    failedStrings++;
                } else {
                    writer.write(entryString);
                    writer.newLine();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return failedStrings;
    }

    public static void createMiniDump(File inFile, int count) {
        String outFileName = DUMPS_DIR + inFile.getName();
        JsonReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new JsonReader(new FileReader(inFile));
            writer = new BufferedWriter(new FileWriter(outFileName));

            JsonParser parser = new JsonParser();
            reader.beginArray();
            writer.write('[');
            while (--count >= 0) {
                JsonElement parse = parser.parse(reader);
                if (parse != null) {
                    String val = parse.getAsJsonObject().toString();
                    writer.write(val);
                    if (count > 0) {
                        writer.write(',');
                    }
                    System.out.println(val);
                }
            }
            writer.write(']');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    interface EntryProcessor<T> {
        String processEntry(T entry);
    }
}
