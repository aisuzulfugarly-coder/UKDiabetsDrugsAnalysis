package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DiabetesAnalyzer {

    public static void main(String[] args) {
      String csvFile = "/Users/aysu/Desktop/diabetes_prescriptions_fake.csv";

        try {
            analyzeDiabetesDrugs(csvFile);
        } catch (IOException e) {
            System.out.println("ERROR: CSV faylı oxuna bilmədi - " + e.getMessage());
            System.out.println("CSV faylın yerini yoxla: " + csvFile);
        }
    }

    public static void analyzeDiabetesDrugs(String csvFile) throws IOException {
        System.out.println("=== UK DIABETES DRUGS ANALYSIS ===\n");

        // Step 1: Read CSV and collect data
        Map<String, DrugData> drugMap = new HashMap<>();
        Map<String, Map<String, Integer>> monthlyTrends = new HashMap<>();

        FileReader reader = new FileReader(csvFile);
        CSVParser parser = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(reader);

        for (CSVRecord record : parser) {
            String drugName = record.get("CHEMICAL_SUBSTANCE");
            String bnfCode = record.get("BNF_CODE");
            int items = Integer.parseInt(record.get("ITEMS"));
            String month = record.get("YEAR_MONTH");

            // Collect total items per drug
            drugMap.putIfAbsent(drugName, new DrugData(drugName, bnfCode));
            drugMap.get(drugName).addItems(items);

            // Collect monthly data
            monthlyTrends.putIfAbsent(drugName, new LinkedHashMap<>());
            monthlyTrends.get(drugName).put(month, items);
        }

        parser.close();
        reader.close();

        // Step 2: Answer - Diabetes dərmanları hansılardır?
        System.out.println("📊 DIABETES DƏRMANLARI:\n");
        drugMap.values().forEach(drug ->
                System.out.println("  • " + drug.name + " (BNF: " + drug.bnfCode + ")")
        );

        // Step 3: Answer - Hansı daha çox yazılıb?
        System.out.println("\n\n🏆 TOP 5 ƏN ÇOX YAZILAN DƏRMANLAR:\n");

        List<DrugData> sortedDrugs = new ArrayList<>(drugMap.values());
        sortedDrugs.sort((a, b) -> Integer.compare(b.totalItems, a.totalItems));

        for (int i = 0; i < Math.min(5, sortedDrugs.size()); i++) {
            DrugData drug = sortedDrugs.get(i);
            System.out.printf("  %d. %s - %,d items\n",
                    i + 1, drug.name, drug.totalItems);
        }

        // Step 4: Answer - 12 ayda artım/azalma varmı?
        System.out.println("\n\n📈 12 AYLIK TREND (2024):\n");

        for (DrugData drug : sortedDrugs) {
            Map<String, Integer> trend = monthlyTrends.get(drug.name);

            if (trend != null && trend.size() >= 2) {
                List<String> months = new ArrayList<>(trend.keySet());
                String firstMonth = months.get(0);
                String lastMonth = months.get(months.size() - 1);

                int firstValue = trend.get(firstMonth);
                int lastValue = trend.get(lastMonth);
                int change = lastValue - firstValue;
                double changePercent = ((double) change / firstValue) * 100;

                String arrow = change > 0 ? "📈" : change < 0 ? "📉" : "➡️";

                System.out.printf("  %s %s\n", arrow, drug.name);
                System.out.printf("     %s: %,d → %s: %,d (%+.1f%%)\n\n",
                        firstMonth, firstValue, lastMonth, lastValue, changePercent);
            }
        }

        System.out.println("=== ANALYSIS COMPLETE ===");
    }

    // Helper class to store drug data
    static class DrugData {
        String name;
        String bnfCode;
        int totalItems = 0;

        DrugData(String name, String bnfCode) {
            this.name = name;
            this.bnfCode = bnfCode;
        }

        void addItems(int items) {
            this.totalItems += items;
        }
    }
}