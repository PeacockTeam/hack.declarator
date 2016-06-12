package org.peacockteam.similar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;


public class MergeSimilarPersonsJobFactory {

	@Autowired JobBuilderFactory jobBuilderFactory;

	@Autowired StepBuilderFactory stepBuilderFactory;

	@Autowired Neo4jTemplate neo4jTemplate;


	public Job getJob() {

		return
			jobBuilderFactory.get("MergeSimiliarPersonsJob")
							 .incrementer(new RunIdIncrementer())
							 .flow(buildStep())
							 .end()
							 .build();
	}

	protected Step buildStep() {

		return stepBuilderFactory.get("MergeSimiliarPersonStep")
								 .tasklet(new MergeSimilarPersonTasklet())
								 .build();
	}


	class MergeSimilarPersonTasklet implements Tasklet {

		@Override
		public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

			// 1. Получаем все имена лиц

			Result<Map<String, Object>> queryResult =
				neo4jTemplate.query(
						"START n=node(*)" +
						" MATCH (n:`Лицо`) " +
						" RETURN n.`Имя` as name", null);

			List<String> names = new ArrayList<>();

			for (Map<String, Object> row : queryResult) {

				names.add((String) row.get("name"));
			}


			// 2. Запускаем похожесть

			ViewConverter similarMatcher = new ViewConverter();

	        similarMatcher.load(names.toArray(new String[names.size()]));

	        String[] similarMatcherResult = similarMatcher.getResult();


			// 3. Создаем связь между похожими именами

	        Map<String, Object> params = new HashMap<>();

	        for (String similarNames : similarMatcherResult) {

	        	String[] similarNamesArray = similarNames.split(";");

	        	params.put("node1", similarNamesArray[0]);
	        	params.put("node2", similarNamesArray[1]);

	        	neo4jTemplate.query(
	        			"MATCH (a:`Лицо` {`Имя`: {node1}})," +
	        			"      (b:`Лицо` {`Имя`: {node2}}) " +
	        			" MERGE (a)-[r:`РОДСТВЕННАЯ_СВЯЗЬ`]->(b)", params);
	        }

			return RepeatStatus.FINISHED;
		}
	}
}
