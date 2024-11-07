package br.com.alura.screenmatch.Challenge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import br.com.alura.screenmatch.model.Data;
import br.com.alura.screenmatch.model.Model;
import br.com.alura.screenmatch.model.Vehicle;
import br.com.alura.screenmatch.service.APIFetch;
import br.com.alura.screenmatch.service.ParseData;

public class Challenge {
    public void displayMenu() {
        APIFetch apiFetch = new APIFetch();
        ParseData parseData = new ParseData();
        Scanner read = new Scanner(System.in);

        final String URL = "https://parallelum.com.br/fipe/api/v1/";
        final String BRANDS = "/marcas";
        final String MODELS = "/modelos";
        final String YEAR = "/anos";

        System.out.println("**** Opções ****");
        System.out.println("Carros");
        System.out.println("Motos");
        System.out.println("Caminhões");
        System.out.println(" ");

        System.out.println("Digite o tipo de veículo: ");
        var option = read.nextLine();
        String TYPE;
        String ADDRESS;

        if (option.toLowerCase().contains("carr")) {
            TYPE = "carros";
        } else if (option.toLowerCase().contains("mot")) {
            TYPE = "motos";
        } else {
            TYPE = "caminhoes";
        }

        ADDRESS = URL + TYPE + BRANDS;
        var json = apiFetch.getDatas(ADDRESS);
        var brands = parseData.getDatasList(json, Data.class);
        brands.stream().sorted(Comparator.comparing(Data::codigo)).forEach(System.out::println);

        System.out.println("Digite o codigo da marca: ");
        var BRAND_CODE = read.nextLine();

        ADDRESS = ADDRESS + "/" + BRAND_CODE + MODELS;
        json = apiFetch.getDatas(ADDRESS);
        var models = parseData.getDatas(json, Model.class);
        System.out.println("\nModelos dessa marca: ");

        models.modelos().stream().sorted(Comparator.comparing(Data::nome)).forEach(System.out::println);

        System.out.println("Digite o código do modelo: ");
        var MODEL_CODE = read.nextLine();

        ADDRESS = ADDRESS + "/" + MODEL_CODE + YEAR;
        json = apiFetch.getDatas(ADDRESS);
        List<Data> years = parseData.getDatasList(json, Data.class);
        List<Vehicle> vehicles = new ArrayList<>();

        for (int i = 0; i < years.size(); i++) {
            json = apiFetch.getDatas(ADDRESS + "/" + years.get(i).codigo());
            Vehicle vehicle = parseData.getDatas(json, Vehicle.class);
            vehicles.add(vehicle);
        }

        System.out.println("\nTods os veículos filtrados com avaliações por ano: ");
    }
}
