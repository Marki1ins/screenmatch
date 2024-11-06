package br.com.alura.screenmatch.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.ShowsData;
import br.com.alura.screenmatch.service.APIFetch;
import br.com.alura.screenmatch.service.ParseData;

public class Main {
    public void displayMenu() {
        APIFetch apiFetch = new APIFetch();
        ParseData parseData = new ParseData();
        Scanner read = new Scanner(System.in);
        final String URL = "https://www.omdbapi.com/?t=";
        final String API_KEY = "&apikey=6585022c";

        System.out.println("Digite o nome da série para buscar");
        var tvShowName = read.nextLine();
        var json = apiFetch.getDatas(URL + tvShowName.replace(" ", "+") + API_KEY);
        ShowsData tvshow = parseData.getDatas(json, ShowsData.class);
        System.out.println(tvshow);

        List<SeasonData> season = new ArrayList<>();

        System.out.println("Quantas temporadas você deseja visualizar?");
        var seasonNumber = read.nextInt();

        for (int i = 1; i <= seasonNumber; i++) {
            json = apiFetch.getDatas(URL + tvShowName.replace(" ", "+") + "&season=" + i + API_KEY);
            SeasonData seasons = parseData.getDatas(json, SeasonData.class);
            season.add(seasons);
        }
        season.forEach(System.out::println);

        season.forEach(t -> t.episodes().forEach(e -> System.out.println(e.title())));
    }
}
