package br.com.alura.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.screenmatch.model.ShowsData;
import br.com.alura.screenmatch.service.APIFetch;
import br.com.alura.screenmatch.service.ParseData;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var apiFetch = new APIFetch();
		var json = apiFetch.getDatas("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);
		ParseData parseData = new ParseData();
		ShowsData datas = parseData.getDatas(json, ShowsData.class);
		System.out.println(datas);
	}
}
