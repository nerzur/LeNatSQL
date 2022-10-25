package cu.edu.unah.lenatsql.utils;

import java.io.*;
import java.util.ArrayList;

import static com.sun.org.apache.bcel.internal.util.SecuritySupport.getResourceAsStream;

public class Translator {

    static ArrayList<String> questions = new ArrayList<>();
    static ArrayList<String> answers = new ArrayList<>();

    public static boolean loadData(InputStream questionsStream, InputStream answersStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(questionsStream));
        String quest = br.readLine();
        while (quest != null && !quest.equals("")) {
            questions.add(quest);
            quest = br.readLine();
        }
        br.close();

        br = new BufferedReader(new InputStreamReader(answersStream));
        String answer = br.readLine();
        while (answer != null && !answer.equals("")) {
            answers.add(answer);
            answer = br.readLine();
        }
        br.close();
        return (questions.size() == answers.size() && questions.size() > 0);
    }

    public String translateNL2SQL(String sentence) {
        String result = "";
        for (int i = 0; i < questions.size(); i++) {
            String query = normalizeQuery(sentence);
            if (query.contains(normalizeQuery(questions.get(i)))){
                if(query.contains(" entre "))
                {
                    double[] numbers = findNumbersRange(query.split(" entre ")[1]);
                    result = insertNumbersRangeInSQLQuery(numbers, answers.get(i));
                }
                else
                    result = answers.get(i);
            }
        }
        if (result.equals(""))
            result = "La pregunta introducida no es correcta.\nNo es posible establecer una consulta SQL válida";
        return result;
    }

    /**
     * Esta función permite normalizar un texto, eliminando así los caracteres especiales.
     * @param query Nombre del fichero a normalizar.
     * @return Nombre del fichero normalizado
     */
    private static String normalizeQuery (String query){
        return query.toLowerCase().replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u")
                .replace("¿", "")
                .replace("?","");
    }

    private static double[] findNumbersRange(String query){
        String q = query.replace(" ","");
        String[] numbers = q.split("y");
        return new double[] {Double.parseDouble(numbers[0]),Double.parseDouble(numbers[1])};
    }

    private static String insertNumbersRangeInSQLQuery(double[] numbers, String answer){
        StringBuilder newQuery = new StringBuilder();
        int cont = 0;
        for (int i = 0; i < answer.length(); i++) {
            if(answer.charAt(i)!='#')
                newQuery.append(answer.charAt(i));
            else newQuery.append(numbers[cont++]);
        }
        return newQuery.toString();
    }
}
