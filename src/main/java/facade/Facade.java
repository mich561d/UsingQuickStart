package facade;

import exceptions.InputException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Facade {

    String[] companies = {"avis", "hertz", "europcar", "budget", "alamo"};
    String baseURL = "http://localhost:3333/availableCars?";

    class FetchAPI implements Callable<String> {

        String week, company, address;

        FetchAPI(String week, String company, String address) {
            this.week = week;
            this.company = company;
            this.address = address;
        }

        @Override
        public String call() throws Exception {
            return getCarDataFromCompany(week, company, address);
        }
    }

    public String getAllCarData(String week, String address) throws InputException, InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < companies.length; i++) {
            FetchAPI fetcher = new FetchAPI(week, companies[i], address);
            Future future = executor.submit(fetcher);
            futures.add(future);
        }
        List<String> results = new ArrayList();
        for (Future<String> f : futures) {
            String result = f.get(5, TimeUnit.SECONDS);
            results.add(result);
        }
        executor.shutdown();
        String str = "";
        for (String result : results) {
            str = str.concat(result);
        }
        str = str.replace("]\n[", ",");
        
        if (str.length() < 10) {
            throw new InputException();
        }
        return str;
    }

    private String getCarDataFromCompany(String week, String company, String address) throws MalformedURLException, IOException, InputException {
        URL url = new URL(baseURL + "week=" + week + "&comp=" + company + "&addr=" + address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("User-Agent", "server");
        Scanner scan = new Scanner(con.getInputStream());
        String jsonString = "";
        while (scan.hasNext()) {
            jsonString += scan.nextLine() + "\n";
        }
        scan.close();
        return jsonString;
    }

}
