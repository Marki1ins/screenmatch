package br.com.alura.screenmatch.Main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.EpisodeData;
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

                List<EpisodeData> episodes = season.stream().flatMap(s -> s.episodes().stream())
                                .collect(Collectors.toList());

                episodes.stream().filter(e -> e.imdbRating() != null && !e.imdbRating().contains("N/A"))
                                .peek(e -> System.out.println("Filtro " + e))
                                .sorted(Comparator.comparing(EpisodeData::imdbRating).reversed())
                                .peek(e -> System.out.println("Ordem " + e))
                                .limit(5)
                                .peek(e -> System.out.println("Limit " + e))
                                .forEach(System.out::println);

                List<Episode> episodesList = season.stream()
                                .flatMap(s -> s.episodes().stream().map(e -> new Episode(s.season(), e)))
                                .collect(Collectors.toList());

                episodesList.forEach(System.out::println);

                System.out.println("A partir de que ano você deseja ver os episódios?");
                var year = read.nextInt();
                read.nextLine();

                LocalDate dateSearch = LocalDate.of(year, 1, 1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                episodesList.stream().filter(e -> e.getReleased() != null && e.getReleased().isAfter(dateSearch))
                                .forEach(e -> System.out.println(
                                                "Season: " + e.getSeason() +
                                                                " - Episode: " + e.getEpisodeNumber() + " - "
                                                                + e.getTitle() +
                                                                " - Released: " + e.getReleased().format(formatter)));
        }
}
