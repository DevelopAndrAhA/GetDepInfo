import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by 99670 on 03.11.2020.
 */
public class Main {
    public static void main(String[] args) {
        OkHttpClient client = new  OkHttpUtils().getInstance();

        //com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonToSend);
//        Request request = new Request.Builder()
//                //.url("https://nambataxi.kg/ru/map/#13.55/42.8758/74.5942")
//                .url("http://kenesh.kg/ru/deputy/show-from/120/fraction/3")
//                        //.post(body)
//                .build();
//        try{
//            Response response = client.newCall(request).execute();
//            String s = response.body().string();
//            System.out.println(s);
//        }catch (Exception e){e.printStackTrace();}



        try {
            Document doc = Jsoup.connect("http://kenesh.kg/ky/fraction/4/show/onuguu-progress-parlamenttik-fraktsiyasi").get();


            Elements divPersonImg = doc.select(".person-img");
            Elements personImg =  divPersonImg.select("img");


            Elements divpersonDetail = doc.select(".person-detail");
            Elements a = divpersonDetail.select("a");

            JSONArray jsonArray = new JSONArray();

            for(int i = 0;i<personImg.size();i++){
                Person person = new Person();

                Element element = personImg.get(i);
                Element element2 = a.get(i);

                Document doc2Detail = Jsoup.connect("http://kenesh.kg"+element2.attr("href")).get();
                Element biography = doc2Detail.getElementById("biography");
                Elements e = biography.getAllElements();
                String infoPerson ="";
                for(int k =0;k<e.size();k++){
                   // infoPerson =  e.get(k).text();
                    System.out.println(e.get(k).text());
                }

                person.setInfo(infoPerson);

                String personImgSrc = element.attr("src");
                person.setSrcImage("http://kenesh.kg"+personImgSrc);
                String nameUser  = element2.text();
                person.setFio(nameUser);


                JSONObject personJSON = new JSONObject();
                try{
                    personJSON.put("fio",person.getFio());
                    personJSON.put("srcImage",person.getSrcImage());
                    personJSON.put("info", person.getInfo());
                }catch (Exception ex){ex.printStackTrace();}
                jsonArray.put(i, personJSON);
            }
            System.out.println(jsonArray.toString());







        } catch (Exception ex) {
            //...
        }
    }
}
