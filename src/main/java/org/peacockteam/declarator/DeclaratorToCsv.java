package org.peacockteam.declarator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.output.FileWriterWithEncoding;

import au.com.bytecode.opencsv.CSVWriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class DeclaratorToCsv {


	public static void main(String[] args) throws JsonProcessingException, IOException {


		ObjectMapper objectMapper = new ObjectMapper();

		File file = new File("C:\\git\\hackathon-spb\\declarator\\regions\\");

		Collection<File> files =
				FileUtils.listFiles(file, TrueFileFilter.TRUE, TrueFileFilter.TRUE);

		try (CSVWriter all = new CSVWriter(
				new FileWriterWithEncoding("simple_declaration_all.csv", Charset.forName("UTF-8")), ';')) {

			all.writeNext(new String[] {
					"Наименование",

					"Описание",

					"Дата",

					"Лицо",

					"Место",

					"Автомобиль",

					"Счет",
			});


			for (File oneFile : files) {

				JsonNode jsonNode = objectMapper.readTree(oneFile);

				Iterator<JsonNode> iterator = jsonNode.iterator();

				while (iterator.hasNext()) {

					JsonNode fullNode = iterator.next();

					JsonNode main = fullNode.get("main");

					Iterator<JsonNode> incomeIterator = fullNode.get("incomes").iterator();

					int numEntity = 0;

					String fullPersonName = null;

					final String personName =
							String.format("%s %s %s (%s)",
									main.get("person_firstName").asText(),
									main.get("person_lastName").asText(),
									main.get("person_ secondName").asText(),
									main.get("person_id").asText());

					String place = null, auto = null, check = null;


					while (incomeIterator.hasNext()) {

						JsonNode income = incomeIterator.next();

						JsonNode relativeName = income.get("relativeName");

						if (! relativeName.isNull()) {
							fullPersonName = personName + " " + relativeName.asText();
						} else {
							fullPersonName = personName;
						}

						check = String.valueOf(income.get("size").asLong());

						numEntity++;

						all.writeNext(new String[] {
//								"Наименование",

							String.format("%s %s %d",
									main.get("id").asText(),
									main.get("type").asText(),
									numEntity),

//								 "Описание"

							String.format("%s;%s",
									main.get("link").asText(),
									main.get("deklaratorLink").asText()),

//								"Дата",
							String.format("%s-01-01", main.get("year").asText()),

//								"Лицо",
							fullPersonName,

//								"Место",
							place,

//								"Автомобиль",
							auto,

//								"Счет",
							check

						});

					}


					Iterator<JsonNode> realEstateIterator = fullNode.get("real_estates").iterator();

					while (realEstateIterator.hasNext()) {
						JsonNode realEstate = realEstateIterator.next();

						auto = null;
						place = null;
						check = null;

						JsonNode relativeName = realEstate.get("relativeName");

						if (! relativeName.isNull()) {
							fullPersonName = personName + " " + relativeName.asText();
						} else {
							fullPersonName = personName;
						}


						place = String.format("%s %s %s %s %s",
								realEstate.get("country").asText(),
								realEstate.get("city").asText(),
								realEstate.get("type").asText(),
								realEstate.get("name").asText(),
								realEstate.get("square").asText());

						numEntity++;

						all.writeNext(new String[] {
//								"Наименование",

							String.format("%s %s %d",
									main.get("id").asText(),
									main.get("type").asText(),
									numEntity),

//								 "Описание"

							String.format("%s;%s",
									main.get("link").asText(),
									main.get("deklaratorLink").asText()),

//								"Дата",
							String.format("%s-01-01", main.get("year").asText()),

//								"Лицо",
							fullPersonName,

//								"Место",
							place,

//								"Автомобиль",
							auto,

//								"Счет",
							check

						});

					}



					Iterator<JsonNode> vehicleIterator = fullNode.get("vehicles").iterator();

					while (vehicleIterator.hasNext()) {
						JsonNode vehicle = vehicleIterator.next();

						auto = null;
						place = null;
						check = null;

						JsonNode relativeName = vehicle.get("relativeName");

						if (! relativeName.isNull()) {
							fullPersonName = personName + " " + relativeName.asText();
						} else {
							fullPersonName = personName;
						}


						if (vehicle.get("brand") != null) {

							auto = String.format("%s %s %s",
									vehicle.get("brand").get("parent_name").asText(),
									vehicle.get("brand").get("name").asText(),
									vehicle.get("name").asText());
						} else {

							auto = String.format("%s",
									vehicle.get("name").asText());

						}

						numEntity++;

						all.writeNext(new String[] {
//								"Наименование",

							String.format("%s %s %d",
									main.get("id").asText(),
									main.get("type").asText(),
									numEntity),

//								 "Описание"

							String.format("%s;%s",
									main.get("link").asText(),
									main.get("deklaratorLink").asText()),

//								"Дата",
							String.format("%s-01-01", main.get("year").asText()),

//								"Лицо",
							fullPersonName,

//								"Место",
							place,

//								"Автомобиль",
							auto,

//								"Счет",
							check

						});
					}


					Iterator<JsonNode> savingIterator = fullNode.get("savings").iterator();

					while (savingIterator.hasNext()) {
						JsonNode saving = savingIterator.next();

						auto = null;
						place = null;
						check = null;

						JsonNode relativeName = saving.get("relativeName");

						if (! relativeName.isNull()) {
							fullPersonName = personName + " " + relativeName.asText();
						} else {
							fullPersonName = personName;
						}


						check = String.format("%s",
								String.valueOf(saving.get("ammount").asLong()));

						numEntity++;

						all.writeNext(new String[] {
//								"Наименование",

							String.format("%s %s %d",
									main.get("id").asText(),
									main.get("type").asText(),
									numEntity),

//								 "Описание"

							String.format("%s;%s",
									main.get("link").asText(),
									main.get("deklaratorLink").asText()),

//								"Дата",
							String.format("%s-01-01", main.get("year").asText()),

//								"Лицо",
							fullPersonName,

//								"Место",
							place,

//								"Автомобиль",
							auto,

//								"Счет",
							check

						});
					}
				}
			}
		}
	}
}
