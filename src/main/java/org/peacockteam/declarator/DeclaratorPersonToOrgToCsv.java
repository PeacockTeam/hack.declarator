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


public class DeclaratorPersonToOrgToCsv {


	public static void main(String[] args) throws JsonProcessingException, IOException {


		ObjectMapper objectMapper = new ObjectMapper();

		File file = new File("C:\\git\\hackathon-spb\\declarator\\regions\\");

		Collection<File> files =
				FileUtils.listFiles(file, TrueFileFilter.TRUE, TrueFileFilter.TRUE);


		try (CSVWriter all = new CSVWriter(
				new FileWriterWithEncoding("person_to_org_all.csv", Charset.forName("UTF-8")), ';')) {

			all.writeNext(new String[] {
					"Имя",

					"Описание",
			});


			for (File oneFile : files) {

				JsonNode jsonNode = objectMapper.readTree(oneFile);

				Iterator<JsonNode> iterator = jsonNode.iterator();

				while (iterator.hasNext()) {

					JsonNode fullNode = iterator.next();

					JsonNode main = fullNode.get("main");

					String personName =
							String.format("%s %s %s (%s)",
									main.get("person_firstName").asText(),
									main.get("person_lastName").asText(),
									main.get("person_ secondName").asText(),
									main.get("person_id").asText());

						all.writeNext(new String[] {
								// Имя

								personName,

								// Описание
								String.format("%s %s %s %s",
										main.get("office_region").isNull() ? "" : main.get("office_region"),
										main.get("office_name"),
										main.get("position"),
										main.get("year"))

						});
				}
			}
		}
	}
}
